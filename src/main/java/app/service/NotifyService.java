package app.service;

import java.io.IOException;
import java.util.List;

import app.model.InspectResult;

/**
 * Responsible for Publishing messages for PubSub Layer 1 and also delegates DLP
 * specific task to DLPService.
 * 
 * @author adarshsinghal
 *
 */
public class NotifyService {

	/**
	 * Publish messages on provided list of topics.
	 * 
	 * @param topics
	 * @param gbTxnId
	 * @param message
	 * @return list of message ids
	 */
	public List<String> publishMessage(List<String> topics, String gbTxnId, String message) {

		MessagePublisher publisher = new MessagePublisher();
		List<String> messageIds = publisher.publishMessages(topics, gbTxnId, message);
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
		DLPService dlpService = new DLPService();
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
		DLPService dlpService = new DLPService();
		String deIdentifiedStr = dlpService.getDeIdentifiedString(inputMsg);
		return deIdentifiedStr;
	}

}
