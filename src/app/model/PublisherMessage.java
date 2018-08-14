package app.model;

public class PublisherMessage {

	private String messageId;
	private String message;
	private String topicName;
	private String publishTime;
	private String globalTransactionId;

	/**
	 * @param messageId
	 * @param message
	 * @param topicName
	 * @param publishTime
	 * @param globalTxnId
	 */
	public PublisherMessage(String messageId, String message, String topicName, String publishTime) {
		this(messageId, message, topicName);
		this.publishTime = publishTime;
	}
	
	public PublisherMessage(String messageId, String message, String topicName) {
		this.messageId = messageId;
		this.message = message;
		this.topicName = topicName;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getGlobalTransactionId() {
		return globalTransactionId;
	}

	public void setGlobalTransactionId(String globalTransactionId) {
		this.globalTransactionId = globalTransactionId;
	}

}
