package com.binasystems.mtimereporter.api;

import org.json.JSONObject;

import android.content.Context;
import android.location.Location;
import android.os.Handler;

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.api.requests.PostRequest;
import com.binasystems.mtimereporter.api.requests.UniRequest;
import com.binasystems.mtimereporter.utils.LoggerFacade;
import com.binasystems.mtimereporter.utils.UserCredintails;
import com.binasystems.mtimereporter.utils.Utils;

public class ReportRequestManager {	
	Handler mHandler = new Handler();
	Context mContext;
	
	public ReportRequestManager(Context context){
		this.mContext = context;
	}
	
	public void requestReportOutLocation(final Location location, @SuppressWarnings("rawtypes") final Callback callback) {
		if(Utils.checkInternetConnection(mContext)){
			new Thread(new Runnable() {				
				@Override
				public void run() {
					UniRequest ur = null;
					ur = new UniRequest("/TimeReport/Attendance.aspx", "execute");
					ur.addLine("UserC", UniRequest.UserC);
					ur.addLine("SwSQL", UniRequest.SwSQL);
					ur.addLine("Lk", UniRequest.LkC);
					ur.addLine("Company", TimeTrackerApplication.getInstance().getBranch().getC());
					ur.addLine("Mode", "Logout");
					ur.addLine("Lng", String.format("%f", location.getLongitude()));
					ur.addLine("Lat", String.format("%f", location.getLatitude()));

					try {
						JSONObject result = PostRequest.executeRequestAsJsonResult(ur);
						String message = result.getString("Result");
						if("success".equalsIgnoreCase(message)){
							handleSuccesInMainThread(message, callback);
							
						} else{
							Exception error = new Exception(result.toString());
							handleErrorInMainThread(error, callback);	
						}
					} catch (Exception e) {
						Exception error = new Exception("Invalid server response");
						handleErrorInMainThread(error, callback);
					}
				}
			}).start();			
			
		} else {
			handleErrorInMainThread(new Exception("Internet connection is offline"), callback);
		}
	}
	public void requestReportEmployee(final Callback<String> callback) {

		if(Utils.checkInternetConnection(mContext)){
			final UserCredintails userCredintails = UserCredintails.getInstance(TimeTrackerApplication.getInstance());
			new Thread(new Runnable() {

				@Override
				public void run() {
					UniRequest ur = null;
					ur = new UniRequest("/TimeReport/Attendance.aspx", "item");
					ur.addLine("UserC", UniRequest.UserC);
					ur.addLine("SwSQL", UniRequest.SwSQL);
					ur.addLine("Lk", UniRequest.LkC);
					ur.addLine("Company", TimeTrackerApplication.getInstance().getBranch().getC());


					try {
						JSONObject result = PostRequest.executeRequestAsJsonResult(ur);
						LoggerFacade.leaveBreadcrumb("ReportRequestManager::requestReportLastAction: response=" + result);

						String message = result.getString("Result");
						handleSuccesInMainThread(message, callback);
					} catch (Exception e) {
						LoggerFacade.leaveBreadcrumb("ReportRequestManager::requestReportLastAction error=" + Utils.getStackTraceAsText(e));
						Exception error = new Exception("Invalid server response");
						handleErrorInMainThread(error, callback);
					}
				}
			}).start();

		} else {
			handleErrorInMainThread(new Exception("Internet connection is offline"), callback);
		}
	}

	public void requestReportLastAction(final Callback<String> callback) {

		if(Utils.checkInternetConnection(mContext)){
			final UserCredintails userCredintails = UserCredintails.getInstance(TimeTrackerApplication.getInstance());
			new Thread(new Runnable() {

				@Override
				public void run() {
					UniRequest ur = null;
					ur = new UniRequest("/TimeReport/Attendance.aspx", "item");
					ur.addLine("UserC", UniRequest.UserC);
					ur.addLine("SwSQL", UniRequest.SwSQL);
					ur.addLine("Lk", UniRequest.LkC);
					ur.addLine("Company", TimeTrackerApplication.getInstance().getBranch().getC());

					LoggerFacade.leaveBreadcrumb("ReportRequestManager::requestReportLastAction: request=" + ur);
					try {
						JSONObject result = PostRequest.executeRequestAsJsonResult(ur);
						LoggerFacade.leaveBreadcrumb("ReportRequestManager::requestReportLastAction: response=" + result);

						String message = result.getString("Result");
						handleSuccesInMainThread(message, callback);
					} catch (Exception e) {
						LoggerFacade.leaveBreadcrumb("ReportRequestManager::requestReportLastAction error=" + Utils.getStackTraceAsText(e));
						Exception error = new Exception("Invalid server response");
						handleErrorInMainThread(error, callback);
					}
				}
			}).start();

		} else {
			handleErrorInMainThread(new Exception("Internet connection is offline"), callback);
		}
	}
	public void requestReportInLocation(final Location location, @SuppressWarnings("rawtypes") final Callback callback) {
		
		if(Utils.checkInternetConnection(mContext)){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					UniRequest ur = null;
					ur = new UniRequest("/TimeReport/Attendance.aspx", "execute");
					ur.addLine("UserC", UniRequest.UserC);
					ur.addLine("SwSQL", UniRequest.SwSQL);
					ur.addLine("Lk", UniRequest.LkC);
					ur.addLine("Company", TimeTrackerApplication.getInstance().getBranch().getC());
					ur.addLine("Mode", "Login");
					ur.addLine("Lng", String.format("%f", location.getLongitude()));
					ur.addLine("Lat", String.format("%f", location.getLatitude()));

					try {
						JSONObject result = PostRequest.executeRequestAsJsonResult(ur);
						String message = result.getString("Result");
						if("success".equalsIgnoreCase(message)){
							handleSuccesInMainThread(message, callback);
							
						} else{
							Exception error = new Exception(result.toString());
							handleErrorInMainThread(error, callback);	
						}
					} catch (Exception e) {
						Exception error = new Exception("Invalid server response");
						handleErrorInMainThread(error, callback);
					}
				}
			}).start();			
			
		} else {
			handleErrorInMainThread(new Exception("Internet connection is offline"), callback);
		}
	}

//	public void requestReportLastAction(final Callback<String> callback) {
//
//		if(Utils.checkInternetConnection(mContext)){
//			new Thread(new Runnable() {
//
//				@Override
//				public void run() {
//					UniRequest ur = null;
//					ur = new UniRequest("/TimeReport/Attendance.aspx", "item");
//					ur.addLine("UserC", UniRequest.UserC);
//					ur.addLine("SwSQL", UniRequest.SwSQL);
//					ur.addLine("Lk", UniRequest.LkC);
//					ur.addLine("Company", TimeTrackerApplication.getInstance().getBranch().getC());
//
//					try {
//						JSONObject result = PostRequest.executeRequestAsJsonResult(ur);
//						String message = result.getString("Result");
//						handleSuccesInMainThread(message, callback);
//					} catch (Exception e) {
//						Exception error = new Exception("Invalid server response");
//						handleErrorInMainThread(error, callback);
//					}
//				}
//			}).start();
//
//		} else {
//			handleErrorInMainThread(new Exception("Internet connection is offline"), callback);
//		}
//	}
	
	public void requestForgotPassword(final String company,final String email,final Callback<String> callback){
		if(Utils.checkInternetConnection(mContext)){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					UniRequest ur = null;
					ur = new UniRequest("/POS/Organization/PasswordRecovery.aspx", "getpwd");
					ur.addLine("Mail", email);
					ur.addLine("Organization", company);
					
					try {
						JSONObject result = PostRequest.executeRequestAsJsonResult(ur);
						String message = result.getString("Result");
						if("OK".equalsIgnoreCase(message)){
							handleSuccesInMainThread(message, callback);
						} else{
							Exception error = new Exception(message);
							handleErrorInMainThread(error, callback);							
						}											
					} catch (Exception e) {
						Exception error = new Exception("Invalid server response");
						handleErrorInMainThread(error, callback);
					}
				}
			}).start();			
			
		} else {
			handleErrorInMainThread(new Exception("Internet connection is offline"), callback);
		}		
	}
	
	private void handleSuccesInMainThread(final Object objcet, @SuppressWarnings("rawtypes") final Callback callback){
		if(callback != null){
			mHandler.post(new Runnable() {							
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					callback.onSuccess(objcet);
				}
			});								
		}		
	}
	
	private void handleErrorInMainThread(final Exception e, @SuppressWarnings("rawtypes") final Callback callback){
		if(callback != null){
			mHandler.post(new Runnable() {							
				@Override
				public void run() {
					callback.onError(e);
				}
			});								
		}
	}	
}
