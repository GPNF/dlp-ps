package app.model;

/**
 * @author AmolPol, Aniruddha
 *
 */
public class MessageStatus {

	private String messageData;
	private String messageId;
	private String deliveryFlag;

	public String getDeliveryFlag() {
		return deliveryFlag;
	}

	public void setDeliveryFlag(String deliveryFlag) {
		this.deliveryFlag = deliveryFlag;
	}

	public String getMessageData() {
		return messageData;
	}

	public void setMessageData(String messageData) {
		this.messageData = messageData;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	@Override
	public String toString() {
		return "RequestMapper [messageData=" + messageData + ", messageId=" + messageId + ", deliveryFlag="
				+ deliveryFlag + "]";
	}

}
