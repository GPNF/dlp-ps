package app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.model.UserDetailsSO;

/**
 * CRUD operation on UserDetails table
 * 
 * @author AdarshSinghal, AmolPol
 *
 */
public class UserDetailsDao {

	private Connection connection;
	Logger logger = LoggerFactory.getLogger(UserDetailsDao.class);

	public UserDetailsDao() throws SQLException {
		DBConnectionProvider connProvider = new DBConnectionProvider();
		this.connection = connProvider.getConnection();
	}

	/**
	 * @return List of UserDetailsSO
	 */
	public List<UserDetailsSO> getAllUserDetails() {
		List<UserDetailsSO> userList = new ArrayList<>();
		UserDetailsSO userSo = null;
		final String allUsers = "SELECT user_id,user_name,user_email_id,user_mobile_number,user_fax_number FROM User_Details";
		try (ResultSet rs = connection.prepareStatement(allUsers).executeQuery()) {

			while (rs.next()) {
				userSo = getUserDetails(rs);
				userList.add(userSo);
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return userList;
	}

	private UserDetailsSO getUserDetails(ResultSet rs) throws SQLException {
		UserDetailsSO userSo;
		UserPreferenceDao userPreferenceDao = new UserPreferenceDao();
		userSo = userPreferenceDao.getUserPreferenceDetails(String.valueOf(rs.getInt("user_id")));
		userSo.setMobileNumber(rs.getString("user_mobile_number"));
		userSo.setEmailId(rs.getString("user_email_id"));
		userSo.setUserId(String.valueOf(rs.getInt("user_id")));
		userSo.setUserName(rs.getString("user_name"));
		return userSo;
	}

}
