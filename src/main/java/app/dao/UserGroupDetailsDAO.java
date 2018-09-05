package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.exception.NoSuchGroupException;
import app.model.UserDetailsSO;
import app.model.UserGroupDetails;
import app.model.UserGroupModel;

/**
 * @author adarshsinghal
 *
 */
public class UserGroupDetailsDAO {

	private Connection connection;

	public UserGroupDetailsDAO() throws SQLException {
		DBConnectionProvider connProvider = new DBConnectionProvider();
		connection = connProvider.getConnection();
	}

	/**
	 * Determine Group authorization level for the group.
	 * 
	 * @param groupId
	 * @return group auth level : int
	 * @throws SQLException
	 * @throws NoSuchGroupException
	 */
	public int getAuthLevel(int groupId) throws SQLException, NoSuchGroupException {
		String sql = "SELECT group_auth_level FROM user_group_details WHERE group_id = ?";

		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, groupId);

		ResultSet rs = ps.executeQuery();
		int grpAuthLevel = -1;

		if (rs.next()) {
			grpAuthLevel = rs.getInt("group_auth_level");
		} else {
			throw new NoSuchGroupException();
		}
		rs.close();
		ps.close();
		return grpAuthLevel;
	}

	/**
	 * Execute select query on user_group_details table
	 * 
	 * @return List&lt;UserGroupDetails&gt;
	 * @throws SQLException
	 */
	public List<UserGroupDetails> getUserGroupDetails() throws SQLException {
		List<UserGroupDetails> userGroupDetailsList = new ArrayList<>();

		String sql = "SELECT *FROM user_group_details";

		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			UserGroupDetails userGroupDetails = getUserGroupDetailsFromDb(rs);
			userGroupDetailsList.add(userGroupDetails);
		}

		return userGroupDetailsList;
	}

	/**
	 * Populate values into POJO from resultset
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private UserGroupDetails getUserGroupDetailsFromDb(ResultSet rs) throws SQLException {

		UserGroupDetails userGroupDetails = new UserGroupDetails();

		userGroupDetails.setGroupId(rs.getInt("group_id"));
		userGroupDetails.setGroupName(rs.getString("group_name"));
		userGroupDetails.setGroupAuthLevel(rs.getInt("group_auth_level"));

		return userGroupDetails;
	}
	
	public List<UserGroupModel> getUserGroupMembershipDetails() throws SQLException
	{
		List<UserGroupModel> userGroupDetailsList = new ArrayList<>();
		UserGroupModel userGroupModel = null;
		List<UserDetailsSO> userList = null;
		UserDetailsDao userDetailsDao = new UserDetailsDao();
		String sql = "SELECT * FROM user_group_details";

		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			userList = userDetailsDao.getAllUserDetails(String.valueOf(rs.getInt("group_id")));
			if (!userList.isEmpty()) {
				for (UserDetailsSO userDet : userList) {

					userGroupModel = new UserGroupModel();
					userGroupModel.setGroupId(String.valueOf(rs.getInt("group_id")));
					userGroupModel.setGroupAuthLevel(String.valueOf(rs.getInt("group_auth_level")));
					userGroupModel.setGroupName(rs.getString("group_name"));
					userGroupModel.setUserId(String.valueOf(userDet.getUserId()));
					userGroupModel.setUserName(userDet.getUserName());
					userGroupDetailsList.add(userGroupModel);

				}
			}
		}

		return userGroupDetailsList;
	}

}
