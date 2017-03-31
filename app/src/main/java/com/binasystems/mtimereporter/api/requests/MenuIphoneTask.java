package com.binasystems.mtimereporter.api.requests;

import android.annotation.SuppressLint;
import android.content.Context;

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.dialog.WaitDialog;
import com.binasystems.mtimereporter.objects.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 
 * @author Bostanica Ion
 * @since 2013-05-21
 * 
 */
public class MenuIphoneTask extends BaseAsyncTask<String> {

	private WaitDialog loading = null;
	private Context context = null;
	private boolean running = false;

	public MenuIphoneTask(Context context) {
		super(context);
		this.context = context;


	}

	@Override
	protected void onPreExecute() {
		loading = new WaitDialog(context);
		loading.show();

	}


	@Override
	protected String doInBackground(String... params) {

		UniRequest ur = null;
		String query = null;
		if (params.length > 0) {
			query = params[0];
		}


		ur = new UniRequest("Menu/Menu_Iphone.aspx", "table");
		ur.addLine("Lk", UniRequest.LkC);
		ur.addLine("SwSQL", UniRequest.SwSQL);
		ur.addLine("UserC", UniRequest.UserC);
		ur.addLine("Company", TimeTrackerApplication.getInstance().getBranch().getC());

		String data;
		try {
			data = PostRequest.executeRequestAsStringResult(ur);
			postDataBackgroundHandleResult(data);
			return data;
		} catch (Exception e) {
			onError(context, e);
		}
		return null;
	}


	@Override
	protected void postDataBackgroundHandleResult(String result) {
		super.postDataBackgroundHandleResult(result);

		if (result == null || result.trim().isEmpty()) {

			showRequestFailExceptionDialog(context);

		} else {

			JSONObject response = null;

			try {

				response = new JSONObject(result);

				JSONArray chance_list = response.getJSONArray("Table");

				JSONObject project = null;

				final ArrayList<Product> chance_object_list = new ArrayList<Product>();

				for (int i = 0; i < chance_list.length(); i++) {

					project = chance_list.getJSONObject(i);

					chance_object_list.add(new Product(project));

				}


				// The update adapter was commented in initial code
				final boolean hasMoreRows = response.getBoolean("HasMoreRows");
				publishProgress(new Runnable() {

					@Override
					public void run() {


					}
				});

			} catch (JSONException e) {

				e.printStackTrace();

				showRequestFailExceptionDialog(context);
			}
		}
	}
		@SuppressLint("ShowToast")
		@Override
		protected void onPostExecute (String result){
			super.onPostExecute(result);
			loading.dismiss();
		}

	public boolean isRunning(){

		return running;

	}


}
