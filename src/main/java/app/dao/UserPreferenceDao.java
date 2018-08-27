package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.model.UpdateUserPreferenceModel;
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

	public int updateUserPreference(UpdateUserPreferenceModel preference) throws SQLException {

		String type = "";
		String prefType = preference.getType();

		switch (prefType) {
		case "email":
			type = "email_prefered";
			break;
		case "sms":
			type = "sms_prefered";
			break;
		case "call":
			type = "call_prefered";
			break;
		}

		String value = preference.getValue().equals("true") ? "Yes" : "No";

		String sql = "UPDATE User_Preferences SET " + type + " = ? WHERE id= ?";

		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, value);
		ps.setInt(2, preference.getId());
		int rowModified = ps.executeUpdate();

		return rowModified;
	}

}
