package app.model;
/**
 * 
 * @author Aniruddha Sinha
 * @description: The logging model consists of the fields which are respective
 *               fields/columns in the Activity_logging table
 *
 */
public class LoggingModel {
	private String autoIncrId;
	private String messageId;
	private String messageData;
	private String subscriptionName;
	private String publishedTimestamp;
	private String globalTransactionId;
	private String topicName;

	private LoggingModel(LoggingBuilder builder) {
		this.autoIncrId = builder.autoIncrId;
		this.messageId = builder.messageId;
		this.messageData = builder.messageData;
		this.subscriptionName = builder.subscriptionName;
		this.publishedTimestamp = builder.publishedTimestamp;
		this.globalTransactionId = builder.globalTransactionId;
		this.topicName = builder.topicName;
	}

	public String getAutoIncrId() {
		return autoIncrId;
	}

	public String getMessageId() {
		return messageId;
	}

	public String getMessageData() {
		return messageData;
	}

	public String getSubscriptionName() {
		return subscriptionName;
	}

	public String getPublishedTimestamp() {
		return publishedTimestamp;
	}

	public String getGlobalTransactionId() {
		return globalTransactionId;
	}

	public String getTopicName() {
		return topicName;
	}

	public static class LoggingBuilder {
		private String autoIncrId;
		private String messageId;
		private String messageData;
		private String subscriptionName;
		private String publishedTimestamp;
		private String globalTransactionId;
		private String topicName;

		public LoggingBuilder() {
		}

		public LoggingBuilder setAutoIncrId(String autoIncrId) {
			this.autoIncrId = autoIncrId;
			return this;
		}

		public LoggingBuilder setMessageId(String messageId) {
			this.messageId = messageId;
			return this;
		}

		public LoggingBuilder setMessageData(String messageData) {
			this.messageData = messageData;
			return this;
		}

		public LoggingBuilder setSubscriptionName(String subsName) {
			this.subscriptionName = subsName;
			return this;
		}

		public LoggingBuilder setPublishedTimestamp(String timeStamp) {
			this.publishedTimestamp = timeStamp;
			return this;
		}

		public LoggingBuilder setGlobalTransactionId(String gtid) {
			this.globalTransactionId = gtid;
			return this;
		}

		public LoggingBuilder setTopicName(String topicName) {
			this.topicName = topicName;
			return this;
		}

		// object creator
		public LoggingModel createInstance() {
			return new LoggingModel(this);
		}
	}
}
