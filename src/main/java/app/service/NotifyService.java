package app.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;

import app.exception.ExternalUserNotAllowedException;
import app.exception.InsufficientAuthorizationException;
import app.exception.NoSuchGroupException;
import app.exception.PANDataFoundSecurityViolationException;
import app.model.InspectResult;
import app.model.SourceMessage;
import app.service.dlp.DLPService;
import app.util.ExternalProperties;

/**
 * Responsible for Publishing messages for PubSub Layer 1 and also delegates DLP
 * specific task to DLPService.
 * 
 * @author adarshsinghal
 *
 */
public class NotifyService {

	private DLPService dlpService;
	private AuthorizationService authService;

	public NotifyService() {
		authService = new AuthorizationService();
		dlpService = new DLPService();
	}

	/**
	 * Publish messages on provided list of topics.
	 * 
	 * @param topics
	 * @param gbTxnId
	 * @param message
	 * @return list of message ids
	 */
	public List<String> publishMessage(List<String> topics, PubsubMessage pubsubMessage) {

		NotifySvcMsgPublisher publisher = new NotifySvcMsgPublisher();
		List<String> messageIds = publisher.publishMessage(topics, pubsubMessage);
		return messageIds;
	}

	/**
	 * Uses DLPService to Inspect the given string
	 * 
	 * @param inputMessage
	 * @return list of inspection results
	 * @throws IOException
	 */
	public List<InspectResult> inspect(String inputMessage) throws IOException {
		List<InspectResult> inspectResults = dlpService.getInspectionResult(inputMessage);
		return inspectResults;
	}

	/**
	 * Uses DLP Service to de-identify the given string
	 * 
	 * @param inputMsg
	 * @return de-identified String
	 * @throws IOException
	 */
	public String deIdentify(String inputMsg) throws IOException {
		String deIdentifiedStr = dlpService.getDeIdentifiedString(inputMsg);
		return deIdentifiedStr;
	}

	/**
	 * @param srcMessage
	 * @return messageIds
	 * @throws SQLException
	 * @throws ExternalUserNotAllowedException
	 * @throws NoSuchGroupException
	 * @throws InsufficientAuthorizationException
	 * @throws IOException
	 * @throws PANDataFoundSecurityViolationException
	 */
	public List<String> notify(SourceMessage srcMessage)
			throws SQLException, ExternalUserNotAllowedException, NoSuchGroupException,
			InsufficientAuthorizationException, IOException, PANDataFoundSecurityViolationException {
		String message = srcMessage.getMessage();
		String topicNames = ExternalProperties.getAppConfig("app.gc.pubsub.topic.layer1");
		
		authService.checkSourceAuthorization(srcMessage);
		dlpService.checkForSensitiveData(message);

		String deIdentifiedStr = dlpService.getDeIdentifiedString(message);
		
		ByteString data = ByteString.copyFromUtf8(deIdentifiedStr);
		String srcAuthLvlStr = String.valueOf(srcMessage.getSourceauthLevel());
		String groupIdStr = String.valueOf(srcMessage.getGroupId());
		PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data)
				.putAttributes("globalTransactionId", srcMessage.getGlobalTxnId())
				.putAttributes("srcAuthLevel", srcAuthLvlStr)
				.putAttributes("destGroupId", groupIdStr).build();
		
		NotifySvcMsgPublisher publisher = new NotifySvcMsgPublisher();
		List<String> messageIds = publisher.publishMessage(topicNames, pubsubMessage);
		return messageIds;
	}

}
