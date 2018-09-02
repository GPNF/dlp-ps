package app.service;

import java.io.IOException;
import java.util.List;

import com.google.pubsub.v1.PubsubMessage;

import app.model.InspectResult;
import app.service.dlp.DLPService;

/**
 * Responsible for Publishing messages for PubSub Layer 1 and also delegates DLP
 * specific task to DLPService.
 * 
 * @author adarshsinghal
 *
 */
public class NotifyService {

	private DLPService dlpService;
	//private AuthorizationService authService;

	public NotifyService() {
		//authService = new AuthorizationService();
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

}
