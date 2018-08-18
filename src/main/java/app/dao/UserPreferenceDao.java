package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.model.UserDetailsSO;

/**
 * CRUD operation on UserPreference table
 * 
 * @author AdarshSinghal, AmolPol
 *
 */
public class UserPreferenceDao {

	private Connection connection;
	Logger logger = LoggerFactory.getLogger(UserPreferenceDao.class);

	public UserPreferenceDao() throws SQLException {
		DBConnectionProvider connProvider = new DBConnectionProvider();
		this.connection = connProvider.getConnection();
	}

	/**
	 * @param userId
	 * @return UserDetailsSO
	 */
	public UserDetailsSO getUserPreferenceDetails(String userId) {
		UserDetailsSO userSo = null;
		final String sql = "SELECT email_prefered,sms_prefered,fax_prefered,phone FROM User_Preferences where user_id=? ";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, userId);

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				userSo = getUserDetails(rs);
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return userSo;
	}

	private UserDetailsSO getUserDetails(ResultSet rs) throws SQLException {
		UserDetailsSO userSo;
		userSo = new UserDetailsSO();

		userSo.setEmailFlag(rs.getString("email_prefered"));
		userSo.setSmsFlag(rs.getString("sms_prefered"));
		userSo.setFaxFlag(rs.getString("fax_prefered"));
		userSo.setPhoneCallFlag(rs.getString("phone"));
		return userSo;
	}

}
