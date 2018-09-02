package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.exception.NoSuchGroupException;

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

}
