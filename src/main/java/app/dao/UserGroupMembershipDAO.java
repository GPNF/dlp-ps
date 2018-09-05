package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.model.UserGroupModel;

public class UserGroupMembershipDAO {

	private Connection connection;

	public UserGroupMembershipDAO() throws SQLException {
		DBConnectionProvider connProvider = new DBConnectionProvider();
		connection = connProvider.getConnection();
	}

	public List<UserGroupModel> getUserGroupMembershipDetails() throws SQLException {
		List<UserGroupModel> userGroupDetailsList = new ArrayList<>();
		String sql = "SELECT gd.group_id,gd.group_name,gd.group_auth_level,gm.user_id,ud.user_name from user_group_details gd JOIN group_membership gm on gm.group_id=gd.group_id JOIN User_Details ud on gm.user_id = ud.user_id";

		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			UserGroupModel userGroupModel = new UserGroupModel();
			userGroupModel.setGroupId(String.valueOf(rs.getInt("group_id")));
			userGroupModel.setGroupName(rs.getString("group_name"));
			userGroupModel.setGroupAuthLevel(String.valueOf(rs.getInt("group_auth_level")));
			userGroupModel.setUserId(String.valueOf(rs.getInt("user_id")));
			userGroupModel.setUserName(rs.getString("user_name"));
			userGroupDetailsList.add(userGroupModel);
		}
		System.out.println(userGroupDetailsList);
		return userGroupDetailsList;
	}

}
