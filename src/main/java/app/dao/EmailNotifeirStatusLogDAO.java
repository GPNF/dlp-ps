package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import app.model.DeliveryStatus;
import app.util.DateUtility;

public class EmailNotifeirStatusLogDAO {

	private Connection connection;

	public EmailNotifeirStatusLogDAO() throws SQLException, ClassNotFoundException {
		DBConnectionProvider connProvider = new DBConnectionProvider();
		this.connection = connProvider.getConnection();
	}

	public void insertWebhookDetails(DeliveryStatus message) throws SQLException {
		String sql = "INSERT INTO Email_Status_Details "+
				"(email,timestamp,event,sg_message_id,response,attempt,useragent,ip,url,reason,status) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setString(1, message.getEmail());
			ps.setLong(2, message.getTimestamp());
			ps.setString(3, message.getEvent());
			ps.setString(4, message.getSg_message_id());
			ps.setString(5, message.getResponse());
			ps.setString(6, message.getAttempt());
			ps.setString(7, message.getUseragent());
			ps.setString(8, message.getIp());
			ps.setString(9, message.getUrl());
			ps.setString(10, message.getReason());
			ps.setString(11, message.getStatus());
			ps.executeUpdate();
		}
	}

}
