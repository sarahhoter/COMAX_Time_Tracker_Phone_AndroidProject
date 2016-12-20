package com.binasystems.mtimereporter.api.requests;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.utils.LoggerFacade;
import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.activity.CategorySelectActivity;
import com.binasystems.mtimereporter.dialog.WaitDialog;
import com.binasystems.mtimereporter.objects.Category;
import com.binasystems.mtimereporter.utils.UserCredintails;

/**
 * 
 * @author Bostanica Ion
 * @since 2013-05-21
 * 
 */
public class LoadCategoriesTask extends BaseAsyncTask<String> {

	private WaitDialog loading = null;
	private Context context = null;
	UserCredintails userCredintails;

	public LoadCategoriesTask(Context context) {

		this.context = context;

	}

	@Override
	protected void onPreExecute() {
		userCredintails = UserCredintails.getInstance(TimeTrackerApplication.getInstace());
		loading = new WaitDialog(context);
		loading.show();

	};

	@Override
	protected String doInBackground(String... params) {

		UniRequest ur = null;
		String query = null;
		if(params.length > 0){
			query = params[0];
		}
		ur = new UniRequest("Organization/Companies.aspx", "table");
		ur.addLine("Lk", userCredintails.LkC);
		ur.addLine("SwSQL", userCredintails.SwSQL);
		ur.addLine("UserC", userCredintails.UserC);
		ur.addLine("Sort", "Kod");
		if(query != null){
			ur.addLine("q", query);
		}
        LoggerFacade.leaveBreadcrumb("LoadCategoriesTask: request=" + ur);
		String data;
		try {
			data = PostRequest.executeRequestAsStringResult(ur);
            LoggerFacade.leaveBreadcrumb("LoadCategoriesTask: response=" + data);
			postDataBackgroundHandleResult(data);
			return data;
		} catch (Exception e) {
			onError(context, e);
		}
		
		return null;
	}
	
	@Override
	protected void postDataBackgroundHandleResult(String result) {
		if (result == null) {

			this.publishProgress(new Runnable() {
				
				@Override
				public void run() {
					Toast.makeText(
							context,
							context.getResources().getString(
									R.string.load_categories_fail), Toast.LENGTH_SHORT)
							.show();
				}
			});

		} else {

			JSONObject response = null;

			try {

				response = new JSONObject(result);

				JSONArray categories_list = response.getJSONArray("Table");

				JSONObject category = null;

				final ArrayList<Category> categories_object_list = new ArrayList<Category>();

				for (int i = 0; i < categories_list.length(); i++) {

					category = categories_list.getJSONObject(i);

					categories_object_list.add(new Category(category));
				}

				publishProgress(new Runnable() {
					@Override
					public void run() {
						((CategorySelectActivity) context)
						.setCategoriesList(categories_object_list);
					}
				});

			} catch (JSONException e) {

				publishProgress(new Runnable() {
					
					@Override
					public void run() {
						((Activity) context).finish();
					}
				});
			}
		}
	}

	@SuppressLint("ShowToast")
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		try{
			loading.dismiss();
		} catch(Exception e){						
		}
	}

}