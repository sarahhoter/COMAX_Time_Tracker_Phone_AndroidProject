package com.binasystems.mtimereporter.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class Product {

	private String code = null;
	private String name = null;
	private String line = null;
	private String kod = null;
	private String bar_code = null;
	private boolean sw_pic = false;
	private String acz_dis = null;
	private String Mhr = null; // Price
	private String min_mhr = null;
	private String money = null;
	private Double quantity = null;
	private String mida = null;
	private String cmtAmr = null;
	private String midaAmrIn = null;
	private String midaAmr = null;
	private String remark = null;
	private Long bonus = null;
	private String alternateId ;
	private Double balance = null;
	private Double orderFrom = null;
	private Double orderByCustomer = null;
	private Double totalbalance = null;
	private Double cmt = null;
	private String prtName=null;
	private double prtKod;

	public Product(JSONObject product) throws JSONException {

		try {

			setCode(product.getString("C"));
		} catch (Exception e) {
		}
		
		try {

			setPrtName(product.getString("PrtNm"));
		} catch (Exception e) {
		}
		
		try {

			setPrtKod(product.getDouble("PrtKod"));
		} catch (Exception e) {
		}

		try {
			setLine(product.getString("Line"));
		} catch (Exception e) {
		}

		try {
			remark = product.getString("Remark");
		} catch (Exception e) {
		}
		try {
			setAlternateId(product.getString("AlternateId"));
		} catch (Exception e) {
		}

		try {
			bonus = product.getLong("Bonus");
		} catch (Exception e) {
		}

		try {
			setName(product.getString("Nm"));
			name = name.trim().replaceAll(" +", " ");
		} catch (Exception e) {
		}

		try {
			setKod(product.getString("Kod"));
		} catch (Exception e) {
		}

		try {
			setBar_code(String.format("%.0f", product.getDouble("BarKod")));
		} catch (Exception e) {
		}

		try {
			setSw_pic(product.getBoolean("SwPic"));
		} catch (Exception e) {
		}

		try {
			setAcz_dis(String.format("%.2f", product.getDouble("AczDis")));
		} catch (Exception e) {
			try {
				setAcz_dis(String.format("%.2f", product.getDouble("Discount")));
			} catch (Exception e1) {
			}

		}

		try {
			setMhr(String.format("%.2f", product.getDouble("Mhr")));
		} catch (Exception e) {
			try {
				setMhr(String.format("%.2f", product.getDouble("Price")));
			} catch (Exception e1) {
			}
		}

		try {
			setMin_mhr(String.format("%.2f", product.getDouble("MinMhr")));
		} catch (Exception e) {
		}

		try {

			setMidaAmrIn(product.getString("MidaAmrIn"));

		} catch (Exception e) {
		}

		try {

			setMidaAmr(product.getString("MidaAmr"));

		} catch (Exception e) {
		}

		try {

			setMida(product.getString("Mida"));

		} catch (Exception e) {
		}

		try {

			setCmtAmr(String.format("%.2f", product.getDouble("CmtAmr")));

		} catch (Exception e) {
		}

		try {
			quantity = product.getDouble("Quantity");
		} catch (Exception e1) {
			setQuantity(Double.valueOf(0));
		}

		try {
			setMoney(String.format("%.2f", product.getDouble("Money")));
		} catch (Exception e1) {
			try {
				setMoney(String.format("%.2f", product.getDouble("Total")));
			} catch (Exception e2) {
			}
		}
		try {
			setCmt(product.getDouble("Cmt"));
		} catch (Exception e2) {
		}
	}

	

	public Product(String code, String name, String kod, String bar_code,
			boolean sw_pic, String acz_dis, String mhr, String min_mhr,
			String money, Double quantity) {
		super();
		this.code = code;
		this.name = name;
		this.kod = kod;
		this.bar_code = bar_code;
		this.sw_pic = sw_pic;
		this.acz_dis = acz_dis;
		Mhr = mhr;
		this.min_mhr = min_mhr;
		this.money = money;
		this.quantity = quantity;
	}

	
	public Product(Product source) {

		super();
		this.code = source.code;
		this.name = source.name;
		this.kod = source.kod;
		this.bar_code = source.bar_code;
		this.sw_pic = source.sw_pic;
		this.acz_dis = source.acz_dis;
		this.Mhr = source.Mhr;
		this.min_mhr = source.min_mhr;
		this.money = source.money;
		this.quantity = source.quantity;
		this.cmtAmr = source.cmtAmr;
		this.remark = source.remark;
		this.bonus = source.bonus;

	}

	public Double getBalance() {
		return balance;
	}

	public String getAlternateId() {
		return alternateId;
	}
	
	public void setAlternateId(String alternateId) {
		this.alternateId=alternateId;
	}
	
	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getOrderFrom() {
		return orderFrom;
	}

	public void setOrderFrom(Double orderFrom) {
		this.orderFrom = orderFrom;
	}

	public Double getOrderByCustomer() {
		return orderByCustomer;
	}

	public void setOrderByCustomer(Double orderByCustomer) {
		this.orderByCustomer = orderByCustomer;
	}

	public Double getTotalbalance() {
		return totalbalance;
	}

	public void setTotalbalance(Double totalbalance) {
		this.totalbalance = totalbalance;
	}

	public String getMida() {
		return mida;
	}

	public void setMida(String mida) {
		this.mida = mida;
	}

	public String getCmtAmr() {
		return cmtAmr;
	}

	public void setCmtAmr(String cmtAmr) {
		this.cmtAmr = cmtAmr;
	}

	public String getMidaAmrIn() {
		return midaAmrIn;
	}

	public void setMidaAmrIn(String midaAmrIn) {
		this.midaAmrIn = midaAmrIn;
	}

	public String getMidaAmr() {
		return midaAmr;
	}

	public void setMidaAmr(String midaAmr) {
		this.midaAmr = midaAmr;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
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

	public String getKod() {
		return kod;
	}

	public void setKod(String kod) {
		this.kod = kod;
	}

	public String getBar_code() {
		return bar_code;
	}

	public void setBar_code(String bar_code) {
		this.bar_code = bar_code;
	}

	public boolean isSw_pic() {
		return sw_pic;
	}

	public void setSw_pic(boolean sw_pic) {
		this.sw_pic = sw_pic;
	}

	public String getAcz_dis() {
		return acz_dis;
	}

	public void setAcz_dis(String acz_dis) {
		this.acz_dis = acz_dis;
	}

	public String getMhr() {
		return Mhr;
	}

	public void setMhr(String mhr) {
		Mhr = mhr;
	}

	public String getMin_mhr() {
		return min_mhr;
	}

	public void setMin_mhr(String min_mhr) {
		this.min_mhr = min_mhr;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getBonus() {
		return bonus;
	}

	public void setBonus(Long bonus) {
		this.bonus = bonus;
	}

	public JSONObject toJSON() throws JSONException {

		JSONObject object = new JSONObject();

		object = new JSONObject();
		object.put("C", getCode());
		object.put("BarKod", getBar_code());
		object.put("Kod", getKod());
		object.put("SwPic", isSw_pic());
		object.put("Quantity", getQuantity());
		object.put("Mhr", getMhr());
		object.put("AczDis", getAcz_dis());
		object.put("Bonus", getBonus());
		object.put("Remark", getRemark());
		object.put("Nm", getName());
		object.put("Money", getMoney());
		object.put("CmtAmr", getCmtAmr());

		return object;

	}



	public Double getCmt() {
		return cmt;
	}



	public void setCmt(Double cmt) {
		this.cmt = cmt;
	}



	public String getPrtName() {
		return prtName;
	}



	public void setPrtName(String prtName) {
		this.prtName = prtName;
	}



	public double getPrtKod() {
		return prtKod;
	}



	public void setPrtKod(double prtKod) {
		this.prtKod = prtKod;
	}


    @Override
    public String toString() {
        return "Product{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", line='" + line + '\'' +
                ", kod='" + kod + '\'' +
                ", bar_code='" + bar_code + '\'' +
                ", sw_pic=" + sw_pic +
                ", acz_dis='" + acz_dis + '\'' +
                ", Mhr='" + Mhr + '\'' +
                ", min_mhr='" + min_mhr + '\'' +
                ", money='" + money + '\'' +
                ", quantity=" + quantity +
                ", mida='" + mida + '\'' +
                ", cmtAmr='" + cmtAmr + '\'' +
                ", midaAmrIn='" + midaAmrIn + '\'' +
                ", midaAmr='" + midaAmr + '\'' +
                ", remark='" + remark + '\'' +
                ", bonus=" + bonus +
                ", alternateId='" + alternateId + '\'' +
                ", balance=" + balance +
                ", orderFrom=" + orderFrom +
                ", orderByCustomer=" + orderByCustomer +
                ", totalbalance=" + totalbalance +
                ", cmt=" + cmt +
                ", prtName='" + prtName + '\'' +
                ", prtKod=" + prtKod +
                '}';
    }
}
