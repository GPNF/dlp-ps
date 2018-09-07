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
 * Responsible for Publishing messages for PubSub Layer 1 which contains three
 * topics. <br>
 * <br>
 * Delegates DLP specific responsibility to DLPService. <br>
 * Delegates Authorization specific responsibility to AuthorizationService. <br>
 * <br>
 * Service class should be 'As Simple As Possible'. If you're modifying this
 * class, add operation &amp; delegate logics to other class <br>
 * <br>
 * 
 * @author AdarshSinghal
 *
 */
public class NotifyService {

	// TODO Remove dependencies from here. Use HTTPClient API to hit POST URL. Check similar implementation for reference at
	// updateDelConfirmation() of NotifyUtility
	private DLPService dlpService;
	private AuthorizationService authService;

	public NotifyService() {
		authService = new AuthorizationService();
		dlpService = new DLPService();
	}

	/**
	 * Publish PubsubMessage on provided list of topics.
	 * 
	 * @param topics
	 * @param pubsubMessage 
	 * @return list of message ids
	 */
	public List<String> publishMessage(List<String> topics, PubsubMessage pubsubMessage) {

		NotifyServiceMessagePublisher publisher = new NotifyServiceMessagePublisher();
		List<String> messageIds = publisher.publishMessage(topics, pubsubMessage);
		return messageIds;
	}

	/**
	 * Uses DLPService to perform DLP Inspection on the given string
	 * 
	 * @param inputMessage
	 * @return list of inspection results
	 * @throws IOException
	 */
	public List<InspectResult> getInspectionResult(String inputMessage) throws IOException {
		return dlpService.getInspectionResult(inputMessage);
	}

	/**
	 * Uses DLP Service to perform DLP Deidentification on the given string
	 * 
	 * @param inputMsg
	 * @return de-identified String
	 * @throws IOException
	 */
	public String getDeidentifiedString(String inputMsg) throws IOException {
		return dlpService.getDeidentifiedString(inputMsg);
	}

	/**
	 * @param sourceMessage
	 * @return messageIds
	 * @throws SQLException
	 * @throws ExternalUserNotAllowedException
	 * @throws NoSuchGroupException
	 * @throws InsufficientAuthorizationException
	 * @throws IOException
	 * @throws PANDataFoundSecurityViolationException
	 */
	public List<String> notify(SourceMessage sourceMessage)
			throws SQLException, ExternalUserNotAllowedException, NoSuchGroupException,
			InsufficientAuthorizationException, IOException, PANDataFoundSecurityViolationException {
		String message = sourceMessage.getMessage();

		// Application level Source authorization against Target Group
		authService.checkSourceAuthorization(sourceMessage);

		// Inspection & termination on violation
		dlpService.checkForSensitiveData(message);

		// DeIdentification - Redact/Mask PIIs.
		String deidentifiedStr = dlpService.getDeidentifiedString(sourceMessage.getMessage());

		PubsubMessage pubsubMessage = SourceToPubSubMessageConverter.convert(sourceMessage, deidentifiedStr);

		String topicNames = ExternalProperties.getAppConfig("app.gc.pubsub.topic.layer1");
		NotifyServiceMessagePublisher publisher = new NotifyServiceMessagePublisher();
		List<String> messageIds = publisher.publishMessage(topicNames, pubsubMessage);
		return messageIds;
	}

	/**
	 * This class is responsible for creating new PubSubMessage 
	 * with deidentified string as messsage data. PubsubMessage is immutable,
	 *  hence, we're handling it here. Creating new Pubsub message will 
	 *  change messageId<br>
	 * 
	 * @param sourceMessage
	 * @param deidentifiedStr
	 * @return
	 * @throws IOException
	 * 
	 * @author AdarshSinghal
	 */
	private static class SourceToPubSubMessageConverter {

		private static PubsubMessage convert(SourceMessage sourceMessage, String deidentifiedStr) throws IOException {
			ByteString data = ByteString.copyFromUtf8(deidentifiedStr);
			String srcAuthLvlStr = String.valueOf(sourceMessage.getSourceauthLevel());
			String groupIdStr = String.valueOf(sourceMessage.getGroupId());

			PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data)
					.putAttributes("globalTransactionId", sourceMessage.getGlobalTxnId())
					.putAttributes("srcAuthLevel", srcAuthLvlStr).putAttributes("destGroupId", groupIdStr).build();
			return pubsubMessage;
		}
	}

}
