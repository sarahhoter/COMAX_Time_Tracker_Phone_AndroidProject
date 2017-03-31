package com.binasystems.mtimereporter.objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author Bosatnica Ion
 * @since 2013-05-16
 * 
 */
public class Category {

	private String name = null;
	private String code = null;
	private String C = null;
	private String prt_company = null;
	private String tel=null;
	private String street=null;
	private String streetNo=null;
	private String cityNm=null;
	private double registeredBusiness;
	
	public Category() {

	}

	public Category(JSONObject category) throws JSONException {

		setName(category.getString("Nm"));
		setCode(category.getString("Kod"));
		setC(category.getString("C"));
		setPrt_company(category.getString("Prt_Company"));
		setTel(category.getString("Tel"));
		setStreet(category.getString("Street"));
		setStreetNo(category.getString("StreetNo"));
		setCityNm(category.getString("CityNm"));
		setRegisteredBusiness(category.getDouble("RegisteredBusiness"));
	}

	public String getC() {
		return C;
	}

	public void setC(String c) {
		C = c;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPrt_company() {
		return prt_company;
	}

	public void setPrt_company(String prt_company) {
		this.prt_company = prt_company;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCityNm() {
		return cityNm;
	}

	public void setCityNm(String cityNm) {
		this.cityNm = cityNm;
	}

	public String getStreetNo() {
		return streetNo;
	}

	public void setStreetNo(String streetNo) {
		this.streetNo = streetNo;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public double getRegisteredBusiness() {
		return registeredBusiness;
	}

	public void setRegisteredBusiness(double registeredBusiness) {
		this.registeredBusiness = registeredBusiness;
	}

}
