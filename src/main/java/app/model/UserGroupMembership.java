package app.model;

public class UserGroupMembership {

	private int groupId;
	private int userId;
	
	public UserGroupMembership() {
	}
	
	public UserGroupMembership(int groupId, int userId) {
		super();
		this.groupId = groupId;
		this.userId = userId;
	}
	
	
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	
}
