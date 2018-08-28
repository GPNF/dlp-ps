package app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.model.MessageStatusCacheField;
import app.msgstatuscache.utils.ConfigParams;
import app.msgstatuscache.utils.PropertyParserAndConfigAdapter;

public class MessageStatusCacheDAO {
	private ConfigParams params;
	private PropertyParserAndConfigAdapter adapter;

	public MessageStatusCacheDAO() {
		super();
		this.adapter = new PropertyParserAndConfigAdapter("logging_configuration.properties");
		this.params = this.adapter.readPropertiesAndSetParameters();
	}

	public List<MessageStatusCacheField> getAllFieldDetails() {
		MessageStatusCacheField mscfObject;
		List<MessageStatusCacheField> messageEntryList = new ArrayList();
		final String sql = "SELECT glo_tran_id, dlv_rprt FROM message_status_cache_db";
		try (ResultSet rs = this.params.getConn().prepareStatement(sql).executeQuery()) {
			while (rs.next()) {
				mscfObject = getTableDetails(rs);
				messageEntryList.add(mscfObject);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return messageEntryList;
	}

	public MessageStatusCacheField getTableDetails(ResultSet rs) throws SQLException {
		return new MessageStatusCacheField.MessageStatusCacheFieldBuilder().setDeliveryReport(rs.getString("dlv_rprt"))
				.setGlobalTransactionId(rs.getString("glo_tran_id")).build();

	}
}
