package app.model;


import java.sql.Timestamp;

public class PubsubSO {
private String msg_id;
private String sub_id;
private String topic_id;
private String message ;
private Timestamp sub_tmstmp;
private Timestamp ack_rcvd_tmstmp;
private String ack_id;




public PubsubSO(String msg_id, String sub_id, String topic_id, String message, Timestamp sub_tmstmp,
		Timestamp ack_rcvd_tmstmp, String ack_id) {
	super();
	this.msg_id = msg_id;
	this.sub_id = sub_id;
	this.topic_id = topic_id;
	this.message = message;
	this.sub_tmstmp = sub_tmstmp;
	this.ack_rcvd_tmstmp = ack_rcvd_tmstmp;
	this.ack_id = ack_id;
}

public PubsubSO() {
	// TODO Auto-generated constructor stub
}
public PubsubSO(String msg_id, String sub_id) {
	// TODO Auto-generated constructor stub
	this.msg_id = msg_id;
	this.sub_id = sub_id;
}
public String getMsg_id() {
	return msg_id;
}
public void setMsg_id(String msg_id) {
	this.msg_id = msg_id;
}
public String getSub_id() {
	return sub_id;
}
public void setSub_id(String sub_id) {
	this.sub_id = sub_id;
}
public String getTopic_id() {
	return topic_id;
}
public void setTopic_id(String topic_id) {
	this.topic_id = topic_id;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public Timestamp getSub_tmstmp() {
	return sub_tmstmp;
}
public void setSub_tmstmp(Timestamp sub_tmstmp) {
	this.sub_tmstmp = sub_tmstmp;
}
public Timestamp getAck_rcvd_tmstmp() {
	return ack_rcvd_tmstmp;
}
public void setAck_rcvd_tmstmp(Timestamp ack_rcvd_tmstmp) {
	this.ack_rcvd_tmstmp = ack_rcvd_tmstmp;
}
public String getAck_id() {
	return ack_id;
}
public void setAck_id(String ack_id) {
	this.ack_id = ack_id;
}
@Override
public String toString() {
	return "PubsubSO [msg_id=" + msg_id + ", sub_id=" + sub_id + ", topic_id=" + topic_id + ", message=" + message
			+ ", sub_tmstmp=" + sub_tmstmp + ", ack_rcvd_tmstmp=" + ack_rcvd_tmstmp + ", ack_id=" + ack_id + "]";
}


}