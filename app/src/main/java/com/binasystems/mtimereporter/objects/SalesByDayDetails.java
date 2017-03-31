package com.binasystems.mtimereporter.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class SalesByDayDetails {
	private Double totalAvoda;
	private Double totalMahala;
	private Double totalHufsha;
	private Double totalHours;

	private Double totalAvoda_w;
	private Double totalMahala_w;
	private Double totalHufsha_w;
	private Double totalHours_w;

	private Double totalAvoda_m;
	private Double totalMahala_m;
	private Double totalHufsha_m;
	private Double totalHours_m;

	private Double totalAvoda_y;
	private Double totalMahala_y;
	private Double totalHufsha_y;
	private Double totalHours_y;
	
	public SalesByDayDetails(){		
	}
	
	public SalesByDayDetails(JSONObject jsonObject){
		if(jsonObject != null){
			try {
				if(!jsonObject.isNull("TotalAvoda"))
					setTotalAvoda(jsonObject.getDouble("TotalAvoda"));					
				if(!jsonObject.isNull("TotalMahala"))
					setTotalMahala(jsonObject.getDouble("TotalMahala"));
				if(!jsonObject.isNull("TotalHufsha"))
					setTotalHufsha(jsonObject.getDouble("TotalHufsha"));
				if(!jsonObject.isNull("TotalHours"))
					setTotalHours(jsonObject.getDouble("TotalHours"));

				
				if(!jsonObject.isNull("TotalAvoda_W"))
					setTotalAvoda_w(jsonObject.getDouble("TotalAvoda_W"));				
				if(!jsonObject.isNull("TotalMahala_W"))
					setTotalMahala_w(jsonObject.getDouble("TotalMahala_W"));				
				if(!jsonObject.isNull("TotalHufsha_W"))
					setTotalHufsha_w(jsonObject.getDouble("TotalHufsha_W"));
				if(!jsonObject.isNull("TotalHours_W"))
					setTotalHours_w(jsonObject.getDouble("TotalHours_W"));
				

				if(!jsonObject.isNull("TotalAvoda_M"))
					setTotalAvoda_m(jsonObject.getDouble("TotalAvoda_M"));				
				if(!jsonObject.isNull("TotalMahala_M"))
					setTotalMahala_m(jsonObject.getDouble("TotalMahala_M"));								
				if(!jsonObject.isNull("TotalHufsha_M"))
					setTotalHufsha_m(jsonObject.getDouble("TotalHufsha_M"));
				if(!jsonObject.isNull("TotalHours_M"))
					setTotalHours_m(jsonObject.getDouble("TotalHours_M"));


				if(!jsonObject.isNull("TotalAvoda_Y"))
					setTotalAvoda_y(jsonObject.getDouble("TotalAvoda_Y"));				
				if(!jsonObject.isNull("TotalMahala_Y"))
					setTotalMahala_y(jsonObject.getDouble("TotalMahala_Y"));								
				if(!jsonObject.isNull("TotalHufsha_Y"))
					setTotalHufsha_y(jsonObject.getDouble("TotalHufsha_Y"));
				if(!jsonObject.isNull("TotalHours_Y"))
					setTotalHours_y(jsonObject.getDouble("TotalHours_Y"));
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public Double getTotalAvoda() {
		return totalAvoda;
	}

	public void setTotalAvoda(Double totalAvoda) {
		this.totalAvoda = totalAvoda;
	}

	public Double getTotalMahala() {
		return totalMahala;
	}

	public void setTotalMahala(Double totalMahala) {
		this.totalMahala = totalMahala;
	}

	public Double getTotalHufsha() {
		return totalHufsha;
	}

	public void setTotalHufsha(Double totalHufsha) {
		this.totalHufsha = totalHufsha;
	}

	public Double getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(Double totalHours) {
		this.totalHours = totalHours;
	}

	public Double getTotalAvoda_w() {
		return totalAvoda_w;
	}

	public void setTotalAvoda_w(Double totalAvoda_w) {
		this.totalAvoda_w = totalAvoda_w;
	}

	public Double getTotalMahala_w() {
		return totalMahala_w;
	}

	public void setTotalMahala_w(Double totalMahala_w) {
		this.totalMahala_w = totalMahala_w;
	}

	public Double getTotalHufsha_w() {
		return totalHufsha_w;
	}

	public void setTotalHufsha_w(Double totalHufsha_w) {
		this.totalHufsha_w = totalHufsha_w;
	}

	public Double getTotalHours_w() {
		return totalHours_w;
	}

	public void setTotalHours_w(Double totalHours_w) {
		this.totalHours_w = totalHours_w;
	}

	public Double getTotalAvoda_m() {
		return totalAvoda_m;
	}

	public void setTotalAvoda_m(Double totalAvoda_m) {
		this.totalAvoda_m = totalAvoda_m;
	}

	public Double getTotalMahala_m() {
		return totalMahala_m;
	}

	public void setTotalMahala_m(Double totalMahala_m) {
		this.totalMahala_m = totalMahala_m;
	}

	public Double getTotalHufsha_m() {
		return totalHufsha_m;
	}

	public void setTotalHufsha_m(Double totalHufsha_m) {
		this.totalHufsha_m = totalHufsha_m;
	}

	public Double getTotalHours_m() {
		return totalHours_m;
	}

	public void setTotalHours_m(Double totalHours_m) {
		this.totalHours_m = totalHours_m;
	}

	public Double getTotalAvoda_y() {
		return totalAvoda_y;
	}

	public void setTotalAvoda_y(Double totalAvoda_y) {
		this.totalAvoda_y = totalAvoda_y;
	}

	public Double getTotalMahala_y() {
		return totalMahala_y;
	}

	public void setTotalMahala_y(Double totalMahala_y) {
		this.totalMahala_y = totalMahala_y;
	}

	public Double getTotalHufsha_y() {
		return totalHufsha_y;
	}

	public void setTotalHufsha_y(Double totalHufsha_y) {
		this.totalHufsha_y = totalHufsha_y;
	}

	public Double getTotalHours_y() {
		return totalHours_y;
	}

	public void setTotalHours_y(Double totalHours_y) {
		this.totalHours_y = totalHours_y;
	}
}
