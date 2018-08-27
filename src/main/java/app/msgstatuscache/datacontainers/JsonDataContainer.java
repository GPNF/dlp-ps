package app.msgstatuscache.datacontainers;

/**
 * @author Aniruddha
 *
 */
public class JsonDataContainer {

	private String messageData;
	private String deliveryFlag;
	private String messageId;

	public String getMessageData() {
		return messageData;
	}

	public void setMessageData(String messageData) {
		this.messageData = messageData;
	}

	public String getDeliveryFlag() {
		return deliveryFlag;
	}

	public void setDeliveryFlag(String deliveryFlag) {
		this.deliveryFlag = deliveryFlag;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	@Override
	public String toString() {
		return "JsonDataContainer [messageData=" + messageData + ", deliveryFlag=" + deliveryFlag + ", messageId="
				+ messageId + "]";
	}

}
