package com.binasystems.mtimereporter.api;

import android.annotation.SuppressLint;
import android.os.Handler;

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.api.requests.PostRequest;
import com.binasystems.mtimereporter.api.requests.UniRequest;
import com.binasystems.mtimereporter.objects.SalesByAgentDetails;
import com.binasystems.mtimereporter.objects.SalesByAgentDetailsSales;
import com.binasystems.mtimereporter.objects.SalesByDayDetails;
import com.binasystems.mtimereporter.objects.SalesByDepDetails;
import com.binasystems.mtimereporter.objects.SalesByStoreDetails;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class ComaxApiManager {

	public static enum DateViewType {
		VIEW_BY_DAY("ByDay"),
		VIEW_BY_WEEK("ByWeek"),
		VIEW_BY_MONTH("ByMonth"),
		VIEW_BY_YEAR("ByYear");
		private final String value;
		DateViewType(String value){
			this.value = value;
		}

		public String toString(){
			return value;
		}
	}

	private static SimpleDateFormat sDateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	public static ComaxApiManager newInstance(){
		return new ComaxApiManager();
	}
	
	Handler mHandler = new Handler();
	
	public void requestSaleDetailsByDay(final Date date, final Callback<SalesByDayDetails> callback){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				UniRequest ur = null;
				ur = new UniRequest("TimeReport/AttendanceReport.aspx", "item");
				ur.addLine("UserC", UniRequest.UserC);
				ur.addLine("SwSQL", UniRequest.SwSQL);
				ur.addLine("Lk", UniRequest.LkC);
				ur.addLine("Company", TimeTrackerApplication.getInstance().getBranch().getC());
				ur.addLine("ByDate", sDateFormat.format(date));
				
				try {
					final JSONObject response = PostRequest.executeRequestAsJsonResult(ur);
					
					JSONArray jsonArray = response.getJSONArray("Table");
					JSONObject jsonObject = jsonArray.getJSONObject(0);
					
					final SalesByDayDetails result = new SalesByDayDetails(jsonObject);
					
					if(callback != null){
						mHandler.post(new Runnable() {
							
							@Override
							public void run() {
								callback.onSuccess(result);								
							}
						});
					}
					
				} catch (Exception e) {
					final Exception error = new Exception("Invalid server response");				
					if(callback != null){
						mHandler.post(new Runnable() {							
							@Override
							public void run() {
								callback.onError(error);														
							}
						});
					}
				}
			}
		}).start();		
	}	
	
	public void requestSaleByStore(final String IsSortDesc ,final String Sort,final Date date, final DateViewType viewType, final String lastCode, final Callback<SalesByStoreDetails> callback){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				UniRequest ur = null;
				ur = new UniRequest("TimeReport/AttendanceReport.aspx", "table");
				DateViewType _viewType = viewType != null ? viewType : DateViewType.VIEW_BY_DAY;
				ur.addLine("UserC", UniRequest.UserC);
				ur.addLine("SwSQL", UniRequest.SwSQL);
				ur.addLine("Lk", UniRequest.LkC);
				ur.addLine("Company", TimeTrackerApplication.getInstance().getBranch().getC());
				ur.addLine("ByDate", sDateFormat.format(date));
				ur.addLine("ViewType", _viewType.toString());
				ur.addLine("Sort", Sort);
				ur.addLine("IsSortDesc", IsSortDesc);
				if(UniRequest.store!=null)
					ur.addLine("StoreC", UniRequest.store.getC());
				if(lastCode != null){
					ur.addLine("lastC", lastCode);
				}
				try {

					final String response = PostRequest.executeRequestAsStringResult(ur);
					final SalesByStoreDetails result = SalesByStoreDetails.parse(response);
					
					if(callback != null){
						mHandler.post(new Runnable() {
							
							@Override
							public void run() {
  								callback.onSuccess(result);
							}
						});
					}					
				} catch (Exception e) {
					// TODO string
					final Exception error = new Exception("Invalid server response");				
					if(callback != null){
						mHandler.post(new Runnable() {							
							@Override
							public void run() {
								callback.onError(error);														
							}
						});
					}
				}				
			}
		}).start();		
	}
	public void requestSaleByAgentStore(final String Desc,final String Sort,final Date date, final DateViewType viewType, final String lastCode, final Callback<SalesByAgentDetails> callback){
		new Thread(new Runnable() {

			@Override
			public void run() {


				UniRequest ur = null;
				ur = new UniRequest("Sales/SalesSocenReport.aspx", "table");
				DateViewType _viewType = viewType != null ? viewType : DateViewType.VIEW_BY_DAY;
				ur.addLine("UserC", UniRequest.UserC);
				ur.addLine("SwSQL", UniRequest.SwSQL);
				ur.addLine("Lk", UniRequest.LkC);
				ur.addLine("Company", TimeTrackerApplication.getInstance().getBranch().getC());
				ur.addLine("ByDate", sDateFormat.format(date));
				ur.addLine("ViewType", _viewType.toString());
				ur.addLine("Sort", Sort);
				ur.addLine("IsSortDesc", Desc);
				ur.addLine("StoreC", TimeTrackerApplication.getInstance().getStore().getC());
				try {
					final String response = PostRequest.executeRequestAsStringResult(ur);
					final SalesByAgentDetails result = SalesByAgentDetails.parse(response);

					if(callback != null){
						mHandler.post(new Runnable() {

							@Override
							public void run() {
								callback.onSuccess(result);
							}
						});
					}
				} catch (Exception e) {
					// TODO string
					final Exception error = new Exception("Invalid server response");
					if(callback != null){
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								callback.onError(error);
							}
						});
					}
				}
			}
		}).start();
	}
	public void requestSaleByDepStore(final String Desc,final String Sort,final Date date, final DateViewType viewType, final String lastCode, final Callback<SalesByDepDetails> callback){
		new Thread(new Runnable() {

			@Override
			public void run() {


				UniRequest ur = null;
				ur = new UniRequest("Sales/SalesDepReport.aspx", "table");
				DateViewType _viewType = viewType != null ? viewType : DateViewType.VIEW_BY_DAY;
				ur.addLine("UserC", UniRequest.UserC);
				ur.addLine("SwSQL", UniRequest.SwSQL);
				ur.addLine("Lk", UniRequest.LkC);
				ur.addLine("Company", TimeTrackerApplication.getInstance().getBranch().getC());
				ur.addLine("ByDate", sDateFormat.format(date));
				ur.addLine("ViewType", _viewType.toString());
				ur.addLine("Sort", Sort);
				ur.addLine("IsSortDesc", Desc);
				ur.addLine("StoreC", TimeTrackerApplication.getInstance().getStore().getC());
				try {
					final String response = PostRequest.executeRequestAsStringResult(ur);
					final SalesByDepDetails result = SalesByDepDetails.parse(response);

					if(callback != null){
						mHandler.post(new Runnable() {

							@Override
							public void run() {
								callback.onSuccess(result);
							}
						});
					}
				} catch (Exception e) {
					// TODO string
					final Exception error = new Exception("Invalid server response");
					if(callback != null){
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								callback.onError(error);
							}
						});
					}
				}
			}
		}).start();
	}
	public void 	requestSaleByDep(final String Desc,final String Sort,final Date date, final DateViewType viewType, final String lastCode, final Callback<SalesByDepDetails> callback){
		new Thread(new Runnable() {

			@Override
			public void run() {


				UniRequest ur = null;
				ur = new UniRequest("Sales/SalesDepReport.aspx", "table");
				DateViewType _viewType = viewType != null ? viewType : DateViewType.VIEW_BY_DAY;
				ur.addLine("UserC", UniRequest.UserC);
				ur.addLine("SwSQL", UniRequest.SwSQL);
				ur.addLine("Lk", UniRequest.LkC);
				ur.addLine("Company", TimeTrackerApplication.getInstance().getBranch().getC());

				ur.addLine("ByDate", sDateFormat.format(date));
				ur.addLine("ViewType", _viewType.toString());
				ur.addLine("Sort", Sort);
				ur.addLine("IsSortDesc", Desc);

				try {
					final String response = PostRequest.executeRequestAsStringResult(ur);
					final SalesByDepDetails result = SalesByDepDetails.parse(response);

					if(callback != null){
						mHandler.post(new Runnable() {

							@Override
							public void run() {
								callback.onSuccess(result);
							}
						});
					}
				} catch (Exception e) {
					// TODO string
					final Exception error = new Exception("Invalid server response");
					if(callback != null){
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								callback.onError(error);
							}
						});
					}
				}
			}
		}).start();
	}
	public void requestSaleByAgent(final String Desc,final String Sort,final Date date, final DateViewType viewType, final String lastCode, final Callback<SalesByAgentDetails> callback){
		new Thread(new Runnable() {

			@Override
			public void run() {


				UniRequest ur = null;

				ur = new UniRequest("Sales/SalesSocenReport.aspx", "table");
				DateViewType _viewType = viewType != null ? viewType : DateViewType.VIEW_BY_DAY;
				ur.addLine("UserC", UniRequest.UserC);
				ur.addLine("SwSQL", UniRequest.SwSQL);
				ur.addLine("Lk", UniRequest.LkC);
				ur.addLine("Company", TimeTrackerApplication.getInstance().getBranch().getC());
				ur.addLine("ByDate", sDateFormat.format(date));
				ur.addLine("ViewType", _viewType.toString());
				ur.addLine("Sort", Sort);
				ur.addLine("IsSortDesc", Desc);

				try {
					final String response = PostRequest.executeRequestAsStringResult(ur);
					final SalesByAgentDetails result = SalesByAgentDetails.parse(response);

					if(callback != null){
						mHandler.post(new Runnable() {

							@Override
							public void run() {
								callback.onSuccess(result);
							}
						});
					}
				} catch (Exception e) {
					// TODO string
					final Exception error = new Exception("Invalid server response");
					if(callback != null){
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								callback.onError(error);
							}
						});
					}
				}
			}
		})
				.start();
	}
	public void requestSaleByStoreDetails(final Date date, final DateViewType viewType, final String storeC, final Callback<SalesByStoreDetails> callback){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				UniRequest ur = null;
				DateViewType _viewType = viewType != null ? viewType : DateViewType.VIEW_BY_DAY;
				ur = new UniRequest("TimeReport/AttendanceReport.aspx", "table");
				ur.addLine("UserC", UniRequest.UserC);
				ur.addLine("SwSQL", UniRequest.SwSQL);
				ur.addLine("Lk", UniRequest.LkC);
				ur.addLine("Company", TimeTrackerApplication.getInstance().getBranch().getC());
				ur.addLine("ByDate", sDateFormat.format(date));
				ur.addLine("ViewType", _viewType.toString());
				ur.addLine("StoreC", storeC);
				try {
					final String response = PostRequest.executeRequestAsStringResult(ur);
					final SalesByStoreDetails result = SalesByStoreDetails.parse(response);
										
					if(callback != null){
						mHandler.post(new Runnable() {							
							@Override
							public void run() {
								callback.onSuccess(result);								
							}
						});
					}					
				} catch (Exception e) {
					// TODO string
					final Exception error = new Exception("Invalid server response");				
					if(callback != null){
						mHandler.post(new Runnable() {							
							@Override
							public void run() {
								callback.onError(error);														
							}
						});
					}
				}				
			}
		}).start();
	}
		public void requestStoreByDep(final String IsSortDesc ,final String Sort,final Date date, final DateViewType viewType, final String lastCode, final String depNm,final Callback<SalesByStoreDetails> callback){
			new Thread(new Runnable() {

				@Override
				public void run() {
					UniRequest ur = null;
					ur = new UniRequest("TimeReport/AttendanceReport.aspx", "table");
					DateViewType _viewType = viewType != null ? viewType : DateViewType.VIEW_BY_DAY;
					ur.addLine("UserC", UniRequest.UserC);
					ur.addLine("SwSQL", UniRequest.SwSQL);
					ur.addLine("Lk", UniRequest.LkC);
					ur.addLine("Company", TimeTrackerApplication.getInstance().getBranch().getC());
					ur.addLine("ByDate", sDateFormat.format(date));
					ur.addLine("ViewType", _viewType.toString());
					ur.addLine("Sort", Sort);
					ur.addLine("IsSortDesc", IsSortDesc);
					if (depNm!=null)
						ur.addLine("Ind_DepC",depNm);
					else
						ur.addLine("Ind_DepC", UniRequest.dep);

					if(lastCode != null){
						ur.addLine("lastC", lastCode);
					}
					if(TimeTrackerApplication.getInstance().getStore()!=null)
						ur.addLine("StoreC", TimeTrackerApplication.getInstance().getStore().getC());
					try {

						final String response = PostRequest.executeRequestAsStringResult(ur);
						final SalesByStoreDetails result = SalesByStoreDetails.parse(response);

						if(callback != null){
							mHandler.post(new Runnable() {

								@Override
								public void run() {
									callback.onSuccess(result);
								}
							});
						}
					} catch (Exception e) {
						// TODO string
						final Exception error = new Exception("Invalid server response");
						if(callback != null){
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									callback.onError(error);
								}
							});
						}
					}
				}
			}).start();
		}

	public void requestSaleByAgentDetails(final String IsSortDesc ,final String Sort,final Date date, final DateViewType viewType, final String lastCode, final String agentNm,final Callback<SalesByAgentDetailsSales> callback){
		new Thread(new Runnable() {

			@Override
			public void run() {
				UniRequest ur = null;
				ur = new UniRequest("Sales/SalesSocenReport.aspx", "item");
				DateViewType _viewType = viewType != null ? viewType : DateViewType.VIEW_BY_DAY;
				ur.addLine("UserC", UniRequest.UserC);
				ur.addLine("SwSQL", UniRequest.SwSQL);
				ur.addLine("Lk", UniRequest.LkC);
				ur.addLine("Company", TimeTrackerApplication.getInstance().getBranch().getC());
				if(agentNm!=null) ur.addLine("SochenC", agentNm);
				else ur.addLine("SochenC", UniRequest.agent);
				ur.addLine("ByDate", sDateFormat.format(date));
				ur.addLine("Sort", Sort);
				ur.addLine("IsSortDesc", IsSortDesc);

				try {

					final String response = PostRequest.executeRequestAsStringResult(ur);
					final SalesByAgentDetailsSales result = SalesByAgentDetailsSales.parse(response);


					if(callback != null){
						mHandler.post(new Runnable() {

							@Override
							public void run() {
								callback.onSuccess(result);
							}
						});
					}
				} catch (Exception e) {
					// TODO string
					final Exception error = new Exception("Invalid server response");
					if(callback != null){
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								callback.onError(error);
							}
						});
					}
				}
			}
		}).start();
	}

}
