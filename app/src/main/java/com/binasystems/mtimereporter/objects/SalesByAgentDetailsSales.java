package com.binasystems.mtimereporter.objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SalesByAgentDetailsSales {
	Boolean HasMoreRows;
	Integer TotalRows;

    // Table
	@SerializedName("Table")
    List<AgentInfoSale> agentListInfo;

	public SalesByAgentDetailsSales(){
	}
	
	public static SalesByAgentDetailsSales parse(String json) throws Exception{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.fromJson(json, SalesByAgentDetailsSales.class);
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



	public List<AgentInfoSale> getAgentListInfo() {
		return agentListInfo;
	}

	public void setAgentListInfo(List<AgentInfoSale> depListInfo) {
		this.agentListInfo = agentListInfo;
	}


    public static class AgentInfoSale implements Serializable{
		private static final long serialVersionUID = -3411924830447781760L;
		public Double Scm;
        public String DateDoc;
        public String DayDateDoc;
		public Double Total_ScmM;

		@Override
		public String toString() {
			return "AgentInfoSale{" +
					"Scm=" + Scm +
					", DateDoc='" + DateDoc + '\'' +
					", DayDateDoc='" + DayDateDoc + '\'' +
					", Total_ScmM=" + Total_ScmM +
					'}';
		}
	}
}
