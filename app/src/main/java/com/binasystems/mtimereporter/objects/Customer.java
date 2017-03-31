package com.binasystems.mtimereporter.objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author Bostanica Ion
 * @since 2013-05-21
 * 
 */
public class Customer {

	private String code = null;
	private String name = null;
	private Long kod = null;
	private Long sochen = null;
	private String sochenNm = null;
	private String tel = null;
	private String pelefon = null;
	private String address = null;
	private Long cityC = null;
	private String city = null;
	private String remIdx = null;
	private String nmV = null;
	private Long loginKod = null;
	private Long logInPassword = null;
	private Long userIdx = null;
	private Double aczDis = null;
	private Long mhrC = null;
	private String mhrNm = null;
	private Long osek = null;
	private Long idxGrp = null;
	private String groupNm = null;

	private String remark = null;
	private String email = null;

	public Customer() {

		setCode("");
		setName("");
		setKod(new Long(0));
		setSochen(new Long(0));
		setSochenNm("");
		setTel("");
		setPelefon("");
		setAddress("");
		setCityC(new Long(0));
		setCity("");
		setRemIdx("");
		setNmV("");
		setUserIdx(new Long(0));
		setAczDis(new Double(0));
		setMhrC(new Long(0));
		setMhrNm("");
		setIdxGrp(new Long(0));
		setGroupNm("");
		setEmail("");
		setOsek(new Long(0));
		setLoginKod(new Long(0));
		setLogInPassword(new Long(0));
	}

	public Customer(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}

	public Customer(Customer customer) {
		super();
		this.code = customer.code;
		this.name = customer.name;
		this.kod = customer.kod;
		this.sochen = customer.sochen;
		this.sochenNm = customer.sochenNm;
		this.tel = customer.tel;
		this.pelefon = customer.pelefon;
		this.address = customer.address;
		this.cityC = customer.cityC;
		this.city = customer.city;
		this.remIdx = customer.remIdx;
		this.nmV = customer.nmV;
		this.loginKod = customer.loginKod;
		this.logInPassword = customer.logInPassword;
		this.userIdx = customer.userIdx;
		this.aczDis = customer.aczDis;
		this.mhrC = customer.mhrC;
		this.mhrNm = customer.mhrNm;
		this.osek = customer.osek;
		this.idxGrp = customer.idxGrp;
		this.groupNm = customer.groupNm;
		this.email=customer.email;
	}

	public Customer(JSONObject customer) throws JSONException {

		setCode(customer.getString("C"));
		setName(customer.getString("Nm"));

		try {setKod(customer.getLong("Kod"));} catch (Exception e) {}
		try {setSochen(customer.getLong("Sochen"));} catch (Exception e) {}
		try {setSochenNm(customer.getString("Sochen_Nm"));} catch (Exception e) {}
		try {setTel(customer.getString("Tel"));} catch (Exception e) {}
		try {setPelefon(customer.getString("Pelefon"));} catch (Exception e) {}
		try {setAddress(customer.getString("Address"));} catch (Exception e) {}
		try {setCityC(customer.getLong("CityC"));} catch (Exception e) {}
		try {setCity(customer.getString("City"));} catch (Exception e) {}
		try {setRemIdx(customer.getString("RemIdx"));} catch (Exception e) {}
		try {setNmV(customer.getString("NmV"));} catch (Exception e) {}
		try {setUserIdx(customer.getLong("UserIdx"));} catch (Exception e) {}
		try {setAczDis(customer.getDouble("AczDis"));} catch (Exception e) {}
		try {setMhrC(customer.getLong("MhrC"));} catch (Exception e) {}
		try {setMhrNm(customer.getString("MhrNm"));} catch (Exception e) {}
		try {setIdxGrp(customer.getLong("Idx_Grp"));} catch (Exception e) {}
		try {setGroupNm(customer.getString("Group_Nm"));} catch (Exception e) {}
		try {setEmail(customer.getString("Email"));} catch (Exception e) {}
		try {setOsek(customer.getLong("Osek"));} catch (Exception e) {}
		try {setLoginKod(customer.getLong("LoginKod"));} catch (Exception e) {}
		try {setLogInPassword(customer.getLong("LogInPassword"));} catch (Exception e) {}
		try {if (osek == null) osek = new Long(0);} catch (Exception e) {}



	}

	public Long getKod() {
		return kod;
	}

	public void setKod(Long kod) {
		this.kod = kod;
	}

	public Long getSochen() {
		return sochen;
	}

	public void setSochen(Long sochen) {
		this.sochen = sochen;
	}

	public String getSochenNm() {
		return sochenNm;
	}

	public void setSochenNm(String sochenNm) {
		this.sochenNm = sochenNm;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPelefon() {
		return pelefon;
	}

	public void setPelefon(String pelefon) {
		this.pelefon = pelefon;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getCityC() {
		return cityC;
	}

	public void setCityC(Long cityC) {
		this.cityC = cityC;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRemIdx() {
		return remIdx;
	}

	public void setRemIdx(String remIdx) {
		this.remIdx = remIdx;
	}

	public String getNmV() {
		return nmV;
	}

	public void setNmV(String nmV) {
		this.nmV = nmV;
	}

	public Long getLoginKod() {
		return loginKod;
	}

	public void setLoginKod(Long loginKod) {
		this.loginKod = loginKod;
	}

	public Long getLogInPassword() {
		return logInPassword;
	}

	public void setLogInPassword(Long logInPassword) {
		this.logInPassword = logInPassword;
	}

	public Long getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(Long userIdx) {
		this.userIdx = userIdx;
	}

	public Double getAczDis() {
		return aczDis;
	}

	public void setAczDis(Double aczDis) {
		this.aczDis = aczDis;
	}

	public Long getMhrC() {
		return mhrC;
	}

	public void setMhrC(Long mhrC) {
		this.mhrC = mhrC;
	}

	public String getMhrNm() {
		return mhrNm;
	}

	public void setMhrNm(String mhrNm) {
		this.mhrNm = mhrNm;
	}

	public Long getOsek() {
		return osek;
	}

	public void setOsek(Long osek) {
		this.osek = osek;
	}

	public Long getIdxGrp() {
		return idxGrp;
	}

	public void setIdxGrp(Long idxGrp) {
		this.idxGrp = idxGrp;
	}

	public String getGroupNm() {
		return groupNm;
	}

	public void setGroupNm(String groupNm) {
		this.groupNm = groupNm;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
