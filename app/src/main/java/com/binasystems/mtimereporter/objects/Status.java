package com.binasystems.mtimereporter.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class Status {

	private Long code = null;
	private String name = null;
	private Long c = null;

	public Status(Long code, String name) {
		super();
		this.code = code;
		this.name = name;
	}

	public Status(JSONObject status) throws JSONException {

		try {
			code = status.getLong("Kod");
		} catch (Exception e) {
		}

		try {
			name = status.getString("Nm");

		} catch (Exception e) {
		}
		
		try {
			c = status.getLong("C");
		} catch (Exception e) {
		}
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getC() {
		return c;
	}

	public void setC(Long c) {
		this.c = c;
	}

}
