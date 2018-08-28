package app.service;

import java.io.IOException;
import java.util.List;

import app.model.InspectResult;

public class NotifyService {

	public List<String> publishMessage(List<String> topics, String gbTxnId, String message) {

		MessagePublisher publisher = new MessagePublisher();
		List<String> messageIds = publisher.publishMessages(topics, gbTxnId, message);
		return messageIds;
	}

	public List<InspectResult> inspect(String inputMessage) throws IOException {
		DLPService dlpService = new DLPService();
		List<InspectResult> inspectResults = dlpService.getInspectionResult(inputMessage);
		return inspectResults;
	}

	public String deIdentify(String inputMsg) throws IOException {
		DLPService dlpService = new DLPService();
		String deIdentifiedStr = dlpService.getDeIdentifiedString(inputMsg);
		return deIdentifiedStr;
	}

}
