package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.model.UserGroupMembership;

public class UserGroupMembershipDAO {

	
	private Connection connection;
	
	public UserGroupMembershipDAO() throws SQLException {
		DBConnectionProvider connProvider = new DBConnectionProvider();
		connection = connProvider.getConnection();
	}
	
	public List<UserGroupMembership> getMembershipTableData() throws SQLException{
		
		String sql = "SELECT *FROM group_membership";
		
		PreparedStatement ps = connection.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		
		List<UserGroupMembership> userGrpMembershipList = new ArrayList<>();
		
		while(rs.next()) {
			UserGroupMembership grpMembership = new UserGroupMembership();
			grpMembership.setGroupId(rs.getInt("user_id"));
			grpMembership.setUserId(rs.getInt("group_id"));
			userGrpMembershipList.add(grpMembership);
		}
		
		return userGrpMembershipList;
		
	}
	
	
}
