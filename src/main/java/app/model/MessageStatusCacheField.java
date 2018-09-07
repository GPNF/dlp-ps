package app.model;

/**
 * 
 * @author Aniruddha Sinha
 * @Description :This table contains the fields which are in message status
 *              cache database
 *
 */
public class MessageStatusCacheField {
	private String globalTransactionId;
	private String deliveryReport;

	private MessageStatusCacheField(MessageStatusCacheFieldBuilder builder) {
		this.globalTransactionId = builder.globalTransactionId;
		this.deliveryReport = builder.deliveryReport;
	}

	public String getGlobalTransactionId() {
		return globalTransactionId;
	}

	public void setGlobalTransactionId(String globalTransactionId) {
		this.globalTransactionId = globalTransactionId;
	}

	public String getDeliveryReport() {
		return deliveryReport;
	}

	public void setDeliveryReport(String deliveryReport) {
		this.deliveryReport = deliveryReport;
	}

	public static class MessageStatusCacheFieldBuilder {
		private String globalTransactionId;
		private String deliveryReport;

		public MessageStatusCacheFieldBuilder() {

		}

		public MessageStatusCacheFieldBuilder setGlobalTransactionId(String gtId) {
			this.globalTransactionId = gtId;
			return this;
		}

		public MessageStatusCacheFieldBuilder setDeliveryReport(String dlvRprt) {
			this.deliveryReport = dlvRprt;
			return this;
		}

		public MessageStatusCacheField build() {
			return new MessageStatusCacheField(this);
		}
	}
}
