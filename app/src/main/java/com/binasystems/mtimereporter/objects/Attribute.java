package com.binasystems.mtimereporter.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class Attribute {

	private String name = null;
	private Long code = null;

	public Attribute(JSONObject attribute) throws JSONException{
		
		code = attribute.getLong("C");
		name = attribute.getString("Nm");
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

}
