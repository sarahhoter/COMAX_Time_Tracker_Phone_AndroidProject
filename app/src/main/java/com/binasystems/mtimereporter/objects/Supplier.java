package com.binasystems.mtimereporter.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class Supplier {

	private Long code = null;
	private String name = null;
	private Long kod = null;
	private String group = null;
	private Long groupCode = null;
	private String mobile = null;
	private String telephone = null;
	private String fax = null;
	private String address = null;
	private String city = null;
	
	
	public Supplier(Long code, String name) {
		super();
		this.code = code;
		this.name = name;
	}
	

	public Supplier(JSONObject supplier) throws JSONException {

		try {
			code = supplier.getLong("C");
		} catch (Exception e) {

		}
		try {

			name = supplier.getString("Nm");
		} catch (Exception e) {

			try {

				name = supplier.getString("KodNm");
			} catch (Exception e1) {

			}

		}

		try {

			kod = supplier.getLong("Kod");
		} catch (Exception e) {

		}

		try {

			group = supplier.getString("Grp_Nm");
		} catch (Exception e) {

		}
		try {

			groupCode = supplier.getLong("GrpC");
		} catch (Exception e) {

		}
		try {

			mobile = supplier.getString("Pelefon");
		} catch (Exception e) {

		}
		try {

			telephone = supplier.getString("Tel");
		} catch (Exception e) {

		}
		try {

			fax = supplier.getString("Fax");
		} catch (Exception e) {

		}
		try {

			address = supplier.getString("Address");
		} catch (Exception e) {

		}
		try {

			city = supplier.getString("City");
		} catch (Exception e) {

		}

	}

	public Long getKod() {
		return kod;
	}

	public void setKod(Long kod) {
		this.kod = kod;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Long getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(Long goupCode) {
		this.groupCode = goupCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

    @Override
    public String toString() {
        return "Supplier{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", kod=" + kod +
                ", group='" + group + '\'' +
                ", groupCode=" + groupCode +
                ", mobile='" + mobile + '\'' +
                ", telephone='" + telephone + '\'' +
                ", fax='" + fax + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
