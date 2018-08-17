package app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;

import app.dao.DBConnectionProvider;
import app.model.PubsubSO;
import app.model.UserDetailsSO;

public class DBHelper {
	Connection conn;
	Properties prop;
	private Connection connection;

	public DBHelper() throws SQLException {

		DBConnectionProvider connProvider = new DBConnectionProvider();
		this.connection = connProvider.getConnection();
	}

	public String addPuSubDetails(PubsubSO pubsubSO) throws ServletException {
		final String createPubsubSql = "INSERT INTO PubSubData (msg_id,sub_id,topic_id,msg_data) VALUES (?,?,?,?)";

		try (PreparedStatement statementCreateVisit = conn.prepareStatement(createPubsubSql)) {

			statementCreateVisit.setString(1, pubsubSO.getMsg_id());
			statementCreateVisit.setString(2, pubsubSO.getSub_id());
			statementCreateVisit.setString(3, pubsubSO.getTopic_id());
			statementCreateVisit.setString(4, pubsubSO.getMessage());
			// statementCreateVisit.setTimestamp(5, new Timestamp(new
			// Date().getTime()));
			/*
			 * statementCreateVisit.setTimestamp(6, new Timestamp(new
			 * Date().getTime())); statementCreateVisit.setString(7,
			 * "transaction id is ghfghf");
			 */
			// statementCreateVisit.q
			statementCreateVisit.executeUpdate();
		} catch (SQLException e) {
			throw new ServletException("SQL error", e);
		}
		return "success";

	}

	public String updateAckDetails(PubsubSO pubsubSO) throws ServletException {
		final String updatePubsubSql = "update PubSubData set ack_rcvd_tmstmp=? , ack_id=? where msg_id =? and sub_id=?";
		try (PreparedStatement statementCreateVisit = conn.prepareStatement(updatePubsubSql)) {

			statementCreateVisit.setTimestamp(1, new Timestamp(new Date().getTime()));
			statementCreateVisit.setString(2, "acknowledgement message id recieved");
			statementCreateVisit.setString(3, pubsubSO.getMsg_id());
			statementCreateVisit.setString(4, pubsubSO.getSub_id());

			statementCreateVisit.executeUpdate();
		} catch (SQLException e) {
			throw new ServletException("SQL error", e);
		}

		return "success";

	}

	public UserDetailsSO getUserPreferenceDetails(String userId) throws ServletException {
		UserDetailsSO userSo = null;
		final String userPreferById = "SELECT email_prefered,sms_prefered,fax_prefered,phone FROM User_Preferences where user_id=? ";
		try (PreparedStatement statement = connection.prepareStatement(userPreferById)) {

			statement.setString(1, userId);

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				userSo = new UserDetailsSO();

				userSo.setEmailFlag(rs.getString("email_prefered"));
				userSo.setSmsFlag(rs.getString("sms_prefered"));
				userSo.setFaxFlag(rs.getString("fax_prefered"));
				userSo.setPhoneCallFlag(rs.getString("phone"));
			}

		} catch (SQLException e) {
			throw new ServletException("SQL error", e);
		}
		return userSo;
	}

	public List<UserDetailsSO> getAllUserDetails() throws ServletException {
		// DBHelper helper = new DBHelper();
		List<UserDetailsSO> userList = new ArrayList<>();
		UserDetailsSO userSo = null;
		final String allUsers = "SELECT user_id,user_name,user_email_id,user_mobile_number,user_fax_number FROM User_Details";
		try (ResultSet rs = connection.prepareStatement(allUsers).executeQuery()) {

			while (rs.next()) {

				userSo = getUserPreferenceDetails(String.valueOf(rs.getInt("user_id")));
				userSo.setMobileNumber(rs.getString("user_mobile_number"));
				userSo.setEmailId(rs.getString("user_email_id"));
				userSo.setUserId(String.valueOf(rs.getInt("user_id")));
				userSo.setUserName(rs.getString("user_name"));
				userList.add(userSo);
			}

		} catch (SQLException e) {
			throw new ServletException("SQL error", e);
		}
		return userList;
	}

}