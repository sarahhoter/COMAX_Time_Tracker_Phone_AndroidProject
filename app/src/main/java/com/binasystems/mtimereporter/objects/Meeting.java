package com.binasystems.mtimereporter.objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author Bostanica Ion
 * @since 2013-05-22
 * 
 */
public class Meeting {

	private String code = null;
	private String date = null;
	private String hour = null;
	private String type = null;
	private String remark = null;
	private String long_ = null;
	private String state_id = null;
	private String time = null;
	private String follow_up_at = null;
	private String who = null;
	private String where = null;
	private String summary = null;
	private String customer = null;
	private String employee = null;


	public Meeting() {
	}

	public Meeting(JSONObject meeting) throws JSONException {


		setCode(meeting.getString("C"));
		setDate(meeting.getString("Date"));
		setHour(meeting.getString("Hour"));
		setType(meeting.getString("Type"));
		setCustomer(meeting.getString("CustomerNm"));
		setRemark(meeting.getString("Remark"));
		setEmployee(meeting.getString("UserNm"));
		setState_id(meeting.getString("SwStatusPgisha"));
		setFollow_up_at(meeting.getString("Dact"));
		setWhere(meeting.getString("Mikom"));
		setWho(meeting.getString("Nachecho"));
		setSummary(meeting.getString("Cikom"));
		//setType(meeting.getString("SugPeilot"));

	}

	public void fill(JSONObject meeting) throws JSONException {

		setCode(meeting.getString("C"));
		setDate(meeting.getString("D"));
		setHour(meeting.getString("Hour"));
		setRemark(meeting.getString("TeurPgisha"));
		setLong_(meeting.getString("Long"));
		setType(meeting.getString("SugPeilot"));
		setState_id(meeting.getString("SwStatusPgisha"));
		setWhere(meeting.getString("Mikom"));
		setWho(meeting.getString("Nachecho"));
		setTime(meeting.getString("Hact"));
		setFollow_up_at(meeting.getString("Dact"));
		setSummary(meeting.getString("Cikom"));

		if (who.trim().compareToIgnoreCase("null") == 0) {
			setWho("");
		}
		if (where.trim().compareToIgnoreCase("null") == 0) {
			setWhere("");
		}
		if (remark.trim().compareToIgnoreCase("null") == 0) {
			setRemark("");
		}
		if (summary.trim().compareToIgnoreCase("null") == 0) {
			setSummary("");
		}

	}

	public String getLong_() {
		return long_;
	}

	public void setLong_(String long_) {
		this.long_ = long_;
	}

	public String getState_id() {
		return state_id;
	}

	public void setState_id(String state_id) {
		this.state_id = state_id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getFollow_up_at() {
		return follow_up_at;
	}

	public void setFollow_up_at(String follofollow_up_at) {
		this.follow_up_at = follofollow_up_at;
	}

	public String getWho() {
		return who;
	}

	public void setWho(String who) {
		this.who = who;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Meeting(String code) {

		setCode(code);

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}
}
