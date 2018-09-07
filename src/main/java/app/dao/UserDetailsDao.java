package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.model.UserDetailsSO;

/**
 * CRUD operation on UserDetails table
 * 
 * @author AdarshSinghal, AmolPol
 *
 */
public class UserDetailsDao {

	private Connection connection;

	public UserDetailsDao() throws SQLException {
		DBConnectionProvider connProvider = new DBConnectionProvider();
		this.connection = connProvider.getConnection();
	}

	@Override
	protected void finalize() throws Throwable {
		if (connection != null)
			connection.close();
	}

	/**
	 * 
	 * This method is called when destination group is not provided so returns
	 * entire user details irrespective of group
	 * 
	 * @return List of UserDetailsSO
	 * @throws SQLException
	 */
	public List<UserDetailsSO> getAllUserDetails() throws SQLException {

		final String allUsers = "SELECT ud.user_id ,ud.user_name, ud.user_email_id,ud.user_mobile_number,up.email_prefered,up.sms_prefered,gd.group_id \r\n"
				+ "FROM user_group_details gd JOIN group_membership gm on gm.group_id=gd.group_id \r\n"
				+ "JOIN User_Details ud on gm.user_id = ud.user_id\r\n"
				+ "JOIN User_Preferences up on up.user_id=ud.user_id ";

		List<UserDetailsSO> userList;

		try (ResultSet rs = connection.prepareStatement(allUsers).executeQuery()) {
			userList = getUserDetails(rs);
		}
		return userList;
	}

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private List<UserDetailsSO> getUserDetails(ResultSet rs) throws SQLException {
		List<UserDetailsSO> userList = new ArrayList<>();
		while (rs.next()) {
			UserDetailsSO userSo = new UserDetailsSO();
			userSo.setMobileNumber(rs.getString("user_mobile_number"));
			userSo.setEmailId(rs.getString("user_email_id"));
			userSo.setUserId(String.valueOf(rs.getInt("user_id")));
			userSo.setUserName(rs.getString("user_name"));
			userSo.setGroupId(String.valueOf(rs.getInt("group_id")));
			userSo.setEmailFlag(rs.getString("email_prefered"));
			userSo.setSmsFlag(rs.getString("sms_prefered"));

			userList.add(userSo);
		}
		return userList;
	}

	/**
	 * | this method is called when destination group id is provided by source
	 * to get user details of selected group
	 * 
	 * @param groupId
	 * @return
	 * @throws SQLException
	 */
	public List<UserDetailsSO> getAllUserDetails(String groupId) throws SQLException {
		List<UserDetailsSO> userList;

		final String user_membership = "SELECT ud.user_id ,ud.user_name, ud.user_email_id,ud.user_mobile_number,up.email_prefered,up.sms_prefered,gd.group_id \r\n"
				+ "FROM user_group_details gd JOIN group_membership gm on gm.group_id=gd.group_id \r\n"
				+ "JOIN User_Details ud on gm.user_id = ud.user_id\r\n"
				+ "JOIN User_Preferences up on up.user_id=ud.user_id and gd.group_id=?";

		try (PreparedStatement groupMember = connection.prepareStatement(user_membership)) {

			groupMember.setString(1, groupId);

			ResultSet rs = groupMember.executeQuery();

			userList = getUserDetails(rs);

			return userList;
		}
	}
}
