package app.model;

public class Publisher {

	private String messageId;
	private String message;
	private String topicName;
	private String publishTime;

	public Publisher(String messageId, String message, String topicName, String publishTime) {
		this.messageId = messageId;
		this.message = message;
		this.topicName = topicName;
		this.publishTime = publishTime;
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

}
