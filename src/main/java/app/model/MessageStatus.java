package app.model;

/**
 * @author AmolPol
 * @Description; to be used by the User Service
 * contains the information regarding the message content
 *
 */
public class MessageStatus {

	private String messageData;
	private String messageId;
	private String deliveryFlag;
	private String destGroupId;

	
	
	public String getDestGroupId() {
		return destGroupId;
	}

	public void setDestGroupId(String destGroupId) {
		this.destGroupId = destGroupId;
	}

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
