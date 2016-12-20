package com.binasystems.mtimereporter.objects;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;

public class OrderDetailParams {
	
	private String StoreC = null;
	private String SwLinkedItems = null;
	private String StoreNm = null;
	private String MhrC = null;
	private String MhrComp = null;
	private String MhrMin = null;
	private String SwBlock = null;
	private String AczDis = null;
	private String AczMaam = null;
	private String DateDoc = null;
	private String DateAsp = null;
	private String details = null;
	private String remark = null;
	private String ScmBeforeDis = null;
	private String ScmNetto = null;
	private String ScmNoNetto = null;
	private String Scm_Dis = null;
	private String ScmBeforeMaam = null;
	private String Scm_Maam = null;
	private String Scm = null;
	private String projectCode = null;
	private String projectName = null;
	private Bitmap signature = null;
	private String mhrBuyLast = null;
	
	private String MhrComp_C;
	private String MhrComp_Kod;
	private String MhrComp_Nm;
	private String MhrKod;
	private String MhrNm;
	private String ToStoreC;
	private String ToStoreNm;
	
	public OrderDetailParams(JSONObject params) throws JSONException {
		
		if(params.has("MhrComp_C")){
			MhrComp_C = params.getString("MhrComp_C");
		}
		
		if(params.has("MhrComp_Kod")){
			MhrComp_Kod = params.getString("MhrComp_Kod");
		}
		
		if(params.has("MhrComp_Nm")){
			MhrComp_Nm = params.getString("MhrComp_Nm");
		}
		
		if(params.has("MhrKod")){
			MhrKod = params.getString("MhrKod");
		}
		
		if(params.has("MhrNm")){
			MhrNm = params.getString("MhrNm");
		}
		
		if(params.has("ToStoreC")){
			ToStoreC = params.getString("ToStoreC");
		}
		
		if(params.has("ToStoreNm")){
			ToStoreNm = params.getString("ToStoreNm");
		}
		
		try {
			setStoreC(params.getString("StoreC"));
		} catch (Exception e1) {
		}
		
		try {
		setSwLinkedItems(params.getString("SwLinkedItems"));
		} catch (Exception e) {
		}
		
		try {
			setMhrC(params.getString("MhrC"));
		} catch (Exception e2) {
		}

		try {
			setMhrComp(params.getString("MhrComp"));
		} catch (Exception e1) {
		}

		try {
			setMhrMin(params.getString("MhrMin"));
		} catch (Exception e) {
		}
		try {
			setSwBlock(params.getString("SwBlock"));
		} catch (Exception e) {
		}
		
		if(params.has("AczDis"))
			setAczDis(params.getString("AczDis"));
		
		if(params.has("AczMaam"))
			setAczMaam(params.getString("AczMaam"));
		
		if(params.has("DateDoc"))
			setDateDoc(params.getString("DateDoc"));
		
		if(params.has("DateDoc"))
			setDateAsp(params.getString("DateDoc"));
		
		try {
			setStoreNm(params.getString("StoreNm"));
		} catch (Exception e) {
		}
		try {
			setRemark(params.getString("Remarks"));
		} catch (Exception e) {
		}
		try {
			setDetails(params.getString("Details"));
		} catch (Exception e) {
			setDetails("");
		}
		try {
			setProjectCode(params.getString("IdxProj"));
		} catch (Exception e) {
			setProjectCode("");
		}
		try {
			setProjectName(params.getString("ProjNm"));
		} catch (Exception e) {
			setProjectName("");
		}

	}
	
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
		
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getScmBeforeDis() {
		return ScmBeforeDis;
	}

	public void setScmBeforeDis(String scmBeforeDis) {
		ScmBeforeDis = scmBeforeDis;
	}

	public String getScmNetto() {
		return ScmNetto;
	}

	public void setScmNetto(String scmNetto) {
		ScmNetto = scmNetto;
	}

	public String getScmNoNetto() {
		return ScmNoNetto;
	}

	public void setScmNoNetto(String scmNoNetto) {
		ScmNoNetto = scmNoNetto;
	}

	public String getScm_Dis() {
		return Scm_Dis;
	}

	public void setScm_Dis(String scm_Dis) {
		Scm_Dis = scm_Dis;
	}

	public String getScmBeforeMaam() {
		return ScmBeforeMaam;
	}

	public void setScmBeforeMaam(String scmBeforeMaam) {
		ScmBeforeMaam = scmBeforeMaam;
	}

	public String getScm_Maam() {
		return Scm_Maam;
	}

	public void setScm_Maam(String scm_Maam) {
		Scm_Maam = scm_Maam;
	}

	public String getScm() {
		return Scm;
	}

	public void setScm(String scm) {
		Scm = scm;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStoreNm() {
		return StoreNm;
	}

	public void setStoreNm(String storeNm) {
		StoreNm = storeNm;
	}

	public String getStoreC() {
		return StoreC;
	}

	public void setStoreC(String storeC) {
		StoreC = storeC;
	}
	
	
	public String getSwLinkedItems() {
		return SwLinkedItems;
	}

	public void setSwLinkedItems(String swLinkedItems) {
		SwLinkedItems = swLinkedItems;
	}
	

	public String getMhrC() {
		return MhrC;
	}

	public void setMhrC(String mhrC) {
		MhrC = mhrC;
	}

	public String getMhrComp() {
		return MhrComp;
	}

	public void setMhrComp(String mhrComp) {
		MhrComp = mhrComp;
	}

	public String getMhrMin() {
		return MhrMin;
	}

	public void setMhrMin(String mhrMin) {
		MhrMin = mhrMin;
	}

	public String getSwBlock() {
		return SwBlock;
	}

	public void setSwBlock(String swBlock) {
		SwBlock = swBlock;
	}

	public String getAczDis() {
		return AczDis;
	}

	public void setAczDis(String aczDis) {
		AczDis = aczDis;
	}

	public String getAczMaam() {
		return AczMaam;
	}

	public void setAczMaam(String aczMaam) {
		AczMaam = aczMaam;
	}

	public String getDateDoc() {
		return DateDoc;
	}

	public void setDateDoc(String dateDoc) {
		DateDoc = dateDoc;
	}

	public String getDateAsp() {
		return DateAsp;
	}

	public void setDateAsp(String dateAsp) {
		DateAsp = dateAsp;
	}

	public JSONObject toJSON() throws JSONException {

		JSONObject param = new JSONObject();

		param.put("StoreC", getStoreC());
		param.put("SwLinkedItems", getSwLinkedItems());
		param.put("StoreNm", getStoreNm());
		param.put("MhrC", getMhrC());
		param.put("Reference", "");
		param.put("DateDoc", getDateDoc());
		param.put("DateAsp", getDateAsp());
		param.put("ScmBeforeDis", getScmBeforeDis());
		param.put("ScmNeto", "0");
		param.put("ScmNoNeto", getScmBeforeDis());
		param.put("AczDis", getAczDis());
		param.put("Scm_Dis", getScm_Dis());
		param.put("ScmBeforeMaam", getScmBeforeMaam());
		param.put("AczMaam", getAczMaam());
		param.put("Scm_Maam", getScm_Maam());
		param.put("Scm", getScm());
		param.put("Details", getDetails());
		param.put("Remarks", getRemark());
		param.put("MhrComp", getMhrComp());
		param.put("IdxProj", getProjectCode());
		param.put("ProjNm", getProjectName());
			
		param.put("MhrComp_C", getMhrComp_C());
		param.put("MhrComp_Kod", MhrComp_Kod);
		param.put("MhrComp_Nm", MhrComp_Nm);
		param.put("MhrKod", MhrKod);
		param.put("MhrNm", MhrNm);
		param.put("ToStoreC", ToStoreC);
		param.put("ToStoreNm", ToStoreNm);

		return param;

	}

	public Bitmap getSignature() {
		return signature;
	}

	public void setSignature(Bitmap signature) {
		this.signature = signature;
	}

	public String getMhrBuyLast() {
		return mhrBuyLast;
	}

	public void setMhrBuyLast(String mhrBuyLast) {
		this.mhrBuyLast = mhrBuyLast;
	}

	public String getMhrComp_C() {
		return MhrComp_C;
	}

	public void setMhrComp_C(String mhrComp_C) {
		MhrComp_C = mhrComp_C;
	}

	public String getMhrComp_Kod() {
		return MhrComp_Kod;
	}

	public void setMhrComp_Kod(String mhrComp_Kod) {
		MhrComp_Kod = mhrComp_Kod;
	}

	public String getMhrComp_Nm() {
		return MhrComp_Nm;
	}

	public void setMhrComp_Nm(String mhrComp_Nm) {
		MhrComp_Nm = mhrComp_Nm;
	}

	public String getMhrKod() {
		return MhrKod;
	}

	public void setMhrKod(String mhrKod) {
		MhrKod = mhrKod;
	}

	public String getMhrNm() {
		return MhrNm;
	}

	public void setMhrNm(String mhrNm) {
		MhrNm = mhrNm;
	}

	public String getToStoreC() {
		return ToStoreC;
	}

	public void setToStoreC(String toStoreC) {
		ToStoreC = toStoreC;
	}

	public String getToStoreNm() {
		return ToStoreNm;
	}

	public void setToStoreNm(String toStoreNm) {
		ToStoreNm = toStoreNm;
	}

    @Override
    public String toString() {
        return "OrderDetailParams{" +
                "StoreC='" + StoreC + '\'' +
                ", SwLinkedItems='" + SwLinkedItems + '\'' +
                ", StoreNm='" + StoreNm + '\'' +
                ", MhrC='" + MhrC + '\'' +
                ", MhrComp='" + MhrComp + '\'' +
                ", MhrMin='" + MhrMin + '\'' +
                ", SwBlock='" + SwBlock + '\'' +
                ", AczDis='" + AczDis + '\'' +
                ", AczMaam='" + AczMaam + '\'' +
                ", DateDoc='" + DateDoc + '\'' +
                ", DateAsp='" + DateAsp + '\'' +
                ", details='" + details + '\'' +
                ", remark='" + remark + '\'' +
                ", ScmBeforeDis='" + ScmBeforeDis + '\'' +
                ", ScmNetto='" + ScmNetto + '\'' +
                ", ScmNoNetto='" + ScmNoNetto + '\'' +
                ", Scm_Dis='" + Scm_Dis + '\'' +
                ", ScmBeforeMaam='" + ScmBeforeMaam + '\'' +
                ", Scm_Maam='" + Scm_Maam + '\'' +
                ", Scm='" + Scm + '\'' +
                ", projectCode='" + projectCode + '\'' +
                ", projectName='" + projectName + '\'' +
                ", signature=" + signature +
                ", mhrBuyLast='" + mhrBuyLast + '\'' +
                ", MhrComp_C='" + MhrComp_C + '\'' +
                ", MhrComp_Kod='" + MhrComp_Kod + '\'' +
                ", MhrComp_Nm='" + MhrComp_Nm + '\'' +
                ", MhrKod='" + MhrKod + '\'' +
                ", MhrNm='" + MhrNm + '\'' +
                ", ToStoreC='" + ToStoreC + '\'' +
                ", ToStoreNm='" + ToStoreNm + '\'' +
                '}';
    }
}
