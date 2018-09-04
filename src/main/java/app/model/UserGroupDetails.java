package app.model;

/**
 * @author adarshsinghal
 *
 */
public class UserGroupDetails {

	int groupId;
	String groupName;
	int groupAuthLevel;

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getGroupAuthLevel() {
		return groupAuthLevel;
	}

	public void setGroupAuthLevel(int groupAuthLevel) {
		this.groupAuthLevel = groupAuthLevel;
	}

	@Override
	public String toString() {
		return "UserGroupDetails [groupId=" + groupId + ", groupName=" + groupName + ", groupAuthLevel="
				+ groupAuthLevel + "]";
	}

}
