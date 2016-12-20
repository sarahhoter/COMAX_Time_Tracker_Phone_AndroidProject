package com.binasystems.mtimereporter.api.requests;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.utils.LoggerFacade;
import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.dialog.ExceptionDialog;
import com.binasystems.mtimereporter.dialog.WaitDialog;
import com.binasystems.mtimereporter.utils.AppSettings;
import com.binasystems.mtimereporter.utils.UserCredintails;

public class LoginTask extends BaseAsyncTask<JSONObject> {

	private Context context = null;
	private WaitDialog loading = null;

	private UserCredintails userCredintails;
	/*
	 * Constants
	 */
	public static final short LOGIN = 1;
	public static final short DEMO = 2;

	private short task_type;

	public LoginTask(Context context) {
		this.context = context;
		this.task_type = LOGIN;
	}

	public LoginTask(Context context, short task_type) {
		this.context = context;
		this.task_type = task_type;

	}

	protected void onPreExecute() {
		userCredintails = UserCredintails.getInstance(TimeTrackerApplication.getInstace());

		loading = new WaitDialog(context);
		loading.show();
	}
	
	@Override
	protected JSONObject doInBackground(String... params) {
		try {
			UniRequest ur = null;
			if (task_type == DEMO) {
				ur = new UniRequest("Organization/Login.aspx", "FreeTrial");
				Log.d("task_type == DEMO", "DEMO ENTER  \n PAGE: " + "Organization/Login.aspx");
				Log.d("task_type == DEMO", "cmd = " + "FreeTrial");
				ur.addLine("SwLang", "1");
				Log.d("task_type == DEMO", "SwLang = " + "1");
				
			} else {
				ur = new UniRequest("Organization/Login.aspx", "login");
				Log.d("task_type == LOGIN", "LOGIN ENTER \nPAGE: " + "Organization/Login.aspx");
				Log.d("task_type == LOGIN", "cmd = " + "login");
				ur.addLine("org", params[0]);
				Log.d("task_type == LOGIN", "org = " + params[0]);
				ur.addLine("username", params[1]);
				Log.d("task_type == LOGIN", "username = " + params[1]);
				ur.addLine("password", params[2]);
				Log.d("task_type == LOGIN", "password = " + params[2]);

			}
            LoggerFacade.leaveBreadcrumb("LoginTask: request=" + ur);
			JSONObject result = PostRequest.executeRequestAsJsonResult(ur);
            LoggerFacade.leaveBreadcrumb("LoginTask: response=" + result);
			postDataBackgroundHandleResult(result);
			return result;
		} catch (Exception e) {
			Log.d("atf", "eror_comax utils_login");
			return null;
		}
	}
	
	
	
	@Override
	protected void postDataBackgroundHandleResult(JSONObject result) {
		//
		JSONObject json = null;
		try {

			if (result != null) {
				json = result;  
				if (json.get("ErrMessage").equals("Wrong input")) {

					publishProgress(new Runnable() {
						
						@Override
						public void run() {
							ExceptionDialog alert = new ExceptionDialog(
									context,
									context.getString(R.string.login_incorect),
									context.getString(R.string.error));
							alert.show();
						}
					});
					
					this.mError = new Exception();

				} else {
					userCredintails.LkC = json.getString("LkC");
					userCredintails.SwSQL = json.getString("SwSQL");
					userCredintails.UserC = json.getString("UserC");
					userCredintails.LogC = json.getString("LogC");
					userCredintails.saveState(TimeTrackerApplication.getInstace());

					AppSettings.setCurrentUser(context, json.getString("UserName"));

					publishProgress(new Runnable() {
						
						@Override
						public void run() {
							loading.dismiss();
						}
					});


				}
			} else {
				publishProgress(new Runnable() {
					
					@Override
					public void run() {
						ExceptionDialog alert = new ExceptionDialog(context,
								"No internet connection",
								context.getString(R.string.error));
						alert.show();
					}
				});
				this.mError = new Exception();
			}

		} catch (JSONException e) {
			e.printStackTrace();
			this.mError = new Exception();
		}
	}

	@Override
	protected void onPostExecute(JSONObject result) {	
		loading.dismiss();
		super.onPostExecute(result);
	}
}