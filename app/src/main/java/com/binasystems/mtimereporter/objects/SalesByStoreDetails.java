package com.binasystems.mtimereporter.objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SalesByStoreDetails {	
	Boolean HasMoreRows;
	Integer TotalRows;	

	// Params
	@SerializedName("Params")
	Params salesInfo;
	
    // Table
	@SerializedName("Table")
    List<StoreInfo> storeListInfo;
	
	public SalesByStoreDetails(){		
	}
	
	public static SalesByStoreDetails parse(String json) throws Exception{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.fromJson(json, SalesByStoreDetails.class);
	}

    @Override
	public String toString() {
		return "SalesByStoreDetails [HasMoreRows=" + HasMoreRows
				+ ", TotalRows=" + TotalRows + ", salesInfo=" + salesInfo
				+ ", storeListInfo=" + storeListInfo + "]";
	}
        
    public Boolean getHasMoreRows() {
		return HasMoreRows;
	}

	public void setHasMoreRows(Boolean hasMoreRows) {
		HasMoreRows = hasMoreRows;
	}

	public Integer getTotalRows() {
		return TotalRows;
	}

	public void setTotalRows(Integer totalRows) {
		TotalRows = totalRows;
	}

	public Params getSalesInfo() {
		return salesInfo;
	}

	public void setSalesInfo(Params salesInfo) {
		this.salesInfo = salesInfo;
	}

	public List<StoreInfo> getStoreListInfo() {
		return storeListInfo;
	}

	public void setStoreListInfo(List<StoreInfo> storeListInfo) {
		this.storeListInfo = storeListInfo;
	}

	// Classes
	public static class Params implements Serializable{
		private static final long serialVersionUID = 1072255468572370180L;
		public Double TotalHufsha;
        public Double TotalAvoda;
        public Double TotalMahala;
        public Double TotalOther;
        public Double TotalHours;
		public Long TransactionCounter;
		public Double AvgTransaction;
		@Override
		public String toString() {
			final StringBuffer sb = new StringBuffer("Params{");
			sb.append("TotalHufsha=").append(TotalHufsha);
			sb.append(", TotalAvoda=").append(TotalAvoda);
			sb.append(", TotalMahala=").append(TotalMahala);
			sb.append(", TotalOther=").append(TotalOther);
			sb.append(", TotalHours=").append(TotalHours);
			sb.append(", TransactionCounter=").append(TransactionCounter);
			sb.append(", AvgTransaction=").append(AvgTransaction);

			sb.append('}');
			return sb.toString();
		}
	}
        
    public static class StoreInfo implements Serializable{
		private static final long serialVersionUID = -3411924830447781760L;
		public Double Scm;
        public String D;

		@Override
		public String toString() {
			return "StoreInfo [Scm=" + Scm + ", D=" + D + "]";
		}        
    }	
}
