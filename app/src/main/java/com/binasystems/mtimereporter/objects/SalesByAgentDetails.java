package com.binasystems.mtimereporter.objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SalesByAgentDetails {
	Boolean HasMoreRows;
	Integer TotalRows;

	// Params
	@SerializedName("Params")
	Params salesInfo;

    // Table
	@SerializedName("Table")
    List<AgentInfo> agentListInfo;

	public SalesByAgentDetails(){
	}
	
	public static SalesByAgentDetails parse(String json) throws Exception{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.fromJson(json, SalesByAgentDetails.class);
	}

    @Override
	public String toString() {
		return "SalesByAgentDetails [HasMoreRows=" + HasMoreRows
				+ ", TotalRows=" + TotalRows
				+ ", agentListInfo=" + agentListInfo + "]";
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

	public List<AgentInfo> getAgentListInfo() {
		return agentListInfo;
	}

	public void setAgentListInfo(List<AgentInfo> depListInfo) {
		this.agentListInfo = agentListInfo;
	}

	// Classes
	public static class Params implements Serializable{
		private static final long serialVersionUID = 1072255468572370180L;
		public Double TotalAshrai;
        public Double TotalCash;
        public Double TotalCheck;
        public Double TotalOther;
        public Double TotalSales;
		public Double Cmt;
		public Long TransactionCounter;
		public Double AvgTransaction;
		@Override
		public String toString() {
			final StringBuffer sb = new StringBuffer("Params{");
			sb.append("TotalAshrai=").append(TotalAshrai);
			sb.append(", TotalCash=").append(TotalCash);
			sb.append(", TotalCheck=").append(TotalCheck);
			sb.append(", TotalOther=").append(TotalOther);
			sb.append(", TotalSales=").append(TotalSales);
			sb.append(", TransactionCounter=").append(TransactionCounter);
			sb.append(", AvgTransaction=").append(AvgTransaction);
			sb.append('}');
			return sb.toString();

		}
	}

    public static class AgentInfo implements Serializable{
		private static final long serialVersionUID = -3411924830447781760L;
		public Double Scm;
        public Integer SochenC;
        public Integer SochenKod;
        public String SochenNm;
		public Double Total_ScmM;

		@Override
		public String toString() {
			return "AgentInfo{" +
					"Scm=" + Scm +
					", SochenC=" + SochenC +
					", SochenKod=" + SochenKod +
					", SochenNm='" + SochenNm + '\'' +
					", Total_ScmM='" + Total_ScmM + '\'' +
					'}';
		}
	}
}
