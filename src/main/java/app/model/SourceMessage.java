package app.model;

/**
 * @author adarshsinghal
 *
 */
public class SourceMessage {

	private int sourceauthLevel;
	private int groupId;
	private String message;
	private String globalTxnId;

	public SourceMessage(int sourceauthLevel, int groupId, String message) {
		super();
		this.sourceauthLevel = sourceauthLevel;
		this.groupId = groupId;
		this.message = message;
	}

	public int getSourceauthLevel() {
		return sourceauthLevel;
	}

	public void setSourceauthLevel(int sourceauthLevel) {
		this.sourceauthLevel = sourceauthLevel;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getGlobalTxnId() {
		return globalTxnId;
	}

	public void setGlobalTxnId(String globalTxnId) {
		this.globalTxnId = globalTxnId;
	}

	@Override
	public String toString() {
		return "SourceMessage [sourceauthLevel=" + sourceauthLevel + ", groupId=" + groupId + ", message=" + message
				+ ", globalTxnId=" + globalTxnId + "]";
	}

}
