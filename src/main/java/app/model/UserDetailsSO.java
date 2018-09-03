package app.model;

/**
 * @author AmolPol
 *
 */
public class UserDetailsSO {

	private String userId;
	private String userName;
	private String emailId;
	private String mobileNumber;
	private String faxNumber;
	private String emailFlag;
	private String smsFlag;
	private String faxFlag;
	private String phoneCallFlag;
	private String groupId;
	
	

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getPhoneCallFlag() {
		return phoneCallFlag;
	}

	public void setPhoneCallFlag(String phoneCallFlag) {
		this.phoneCallFlag = phoneCallFlag;
	}

	public String getEmailFlag() {
		return emailFlag;
	}

	public void setEmailFlag(String emailFlag) {
		this.emailFlag = emailFlag;
	}

	public String getSmsFlag() {
		return smsFlag;
	}

	public void setSmsFlag(String smsFlag) {
		this.smsFlag = smsFlag;
	}

	public String getFaxFlag() {
		return faxFlag;
	}

	public void setFaxFlag(String faxFlag) {
		this.faxFlag = faxFlag;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	@Override
	public String toString() {
		return "UserDetailsSO [userId=" + userId + ", userName=" + userName + ", emailId=" + emailId + ", mobileNumber="
				+ mobileNumber + ", faxNumber=" + faxNumber + ", emailFlag=" + emailFlag + ", smsFlag=" + smsFlag
				+ ", faxFlag=" + faxFlag + ", phoneCallFlag=" + phoneCallFlag + "]";
	}

}