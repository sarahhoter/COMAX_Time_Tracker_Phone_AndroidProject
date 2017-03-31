package com.binasystems.mtimereporter.objects;

import org.json.JSONObject;

public class Combo {

	private Long code = null;
	private Long kod = null;
	private String name = null;

	public Combo(){

	}

	public Combo(JSONObject project) {

		try {

			code = project.getLong("C");

		} catch (Exception e) {
		}

		try {

			kod = project.getLong("Kod");

		} catch (Exception e) {
		}

		try {

			name = project.getString("Nm");

		} catch (Exception e) {
		}

		
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public Long getKod() {
		return kod;
	}

	public void setKod(Long kod) {
		this.kod = kod;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
