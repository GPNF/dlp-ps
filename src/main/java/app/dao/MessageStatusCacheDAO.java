package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.model.MessageStatusCacheField;

public class MessageStatusCacheDAO {

	private Connection connection;

	public MessageStatusCacheDAO() throws SQLException {
		super();

		DBConnectionProvider connProvider = new DBConnectionProvider();
		connection = connProvider.getConnection();
	}

	public List<MessageStatusCacheField> getAllFieldDetails() throws SQLException {
		MessageStatusCacheField mscfObject;
		List<MessageStatusCacheField> messageEntryList = new ArrayList<>();
		final String sql = "SELECT glo_tran_id, dlv_rprt FROM message_status_cache_db";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			mscfObject = getTableDetails(rs);
			messageEntryList.add(mscfObject);
		}

		return messageEntryList;
	}

	public MessageStatusCacheField getTableDetails(ResultSet rs) throws SQLException {
		return new MessageStatusCacheField.MessageStatusCacheFieldBuilder()
				.setDeliveryReport(rs.getString("dlv_rprt"))
				.setGlobalTransactionId(rs.getString("glo_tran_id")).build();

	}
}
