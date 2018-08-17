package app.model;

public class SubscriberMessage {

	private String id;
	private String messageId;
	private String message;
	private String subscriptionId;
	private String publishTime;
	private String pullTime;
	private String globalTransactionId;
	private String ackId;

	public SubscriberMessage(String messageId, String message, String publishTime, String ackId, String globalTxnId) {
		super();
		this.messageId = messageId;
		this.message = message;
		this.publishTime = publishTime;
		this.ackId = ackId;
		this.globalTransactionId = globalTxnId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getAckId() {
		return ackId;
	}

	public void setAckId(String ackId) {
		this.ackId = ackId;
	}


	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public String getPullTime() {
		return pullTime;
	}

	public void setPullTime(String pullTime) {
		this.pullTime = pullTime;
	}

	public String getGlobalTransactionId() {
		return globalTransactionId;
	}

	public void setGlobalTransactionId(String globalTransactionId) {
		this.globalTransactionId = globalTransactionId;
	}

	@Override
	public String toString() {
		return "SubscriberMessage [id=" + id + ", messageId=" + messageId + ", message=" + message + ", subscriptionId="
				+ subscriptionId + ", publishTime=" + publishTime + ", pullTime=" + pullTime + ", globalTransactionId="
				+ globalTransactionId + ", ackId=" + ackId + "]";
	}


}
