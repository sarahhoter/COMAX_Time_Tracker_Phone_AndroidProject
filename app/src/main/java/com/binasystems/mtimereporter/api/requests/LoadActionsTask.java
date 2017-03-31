package com.binasystems.mtimereporter.api.requests;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.activity.BaseFragment;
import com.binasystems.mtimereporter.adapter.AddActionsListAdapter;
import com.binasystems.mtimereporter.adapter.CustomersListAdapter;
import com.binasystems.mtimereporter.dialog.WaitDialog;
import com.binasystems.mtimereporter.fragment.sales.AddAction.AddActionsFragment;
import com.binasystems.mtimereporter.fragment.sales.customers.CustomerFragment;
import com.binasystems.mtimereporter.objects.Customer;
import com.binasystems.mtimereporter.objects.Meeting;

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
public class LoadActionsTask extends BaseAsyncTask<String> {

	private WaitDialog loading = null;
	private Context context = null;
	private AddActionsListAdapter adapter = null;
	private Meeting last_customer = null;
	private BaseFragment baseFragment=null;
	private boolean running = false;
	private String param=null;

	public LoadActionsTask(Context context, AddActionsListAdapter customersListAdapter, BaseFragment baseFragment) {
		super(context);
		this.context = context;
		this.adapter=customersListAdapter;
		this.baseFragment=baseFragment;

	}

	@Override
	protected void onPreExecute() {
		loading = new WaitDialog(context);
		loading.show();

	};

	@Override
	protected String doInBackground(String... params) {
		UniRequest ur = new UniRequest("Divor/IdxPgisha.aspx", "table");
		ur.addLine("Lk",UniRequest.LkC);
		ur.addLine("SwSQL", UniRequest.SwSQL);
		ur.addLine("UserC", UniRequest.UserC);
		ur.addLine("Company", TimeTrackerApplication.getInstance().getBranch().getC());
		ur.addLine("Language","he");
		ur.addLine("lastC", last_customer != null ? last_customer.getCode() : "");
		ur.addLine("Sort", "Date");
		ur.addLine("SortValue", last_customer != null ? last_customer.getDate() : "");
		ur.addLine("IsSortDesc", "true");
		if(params[0]!=null)
			ur.addLine("q",params[0]);
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


				JSONObject meeting = null;

				final ArrayList<Meeting> customer_object_list = new ArrayList<Meeting>();

				for (int i = 0; i < categories_list.length(); i++) {

					meeting = categories_list.getJSONObject(i);

					customer_object_list.add(new Meeting(meeting));

				}

				final boolean hasMoreRows = response.getBoolean("HasMoreRows");


				publishProgress(new Runnable() {
					@Override
					public void run() {
						if(param==null) {
							if (baseFragment instanceof AddActionsFragment)
								((AddActionsFragment) baseFragment).setList(customer_object_list);
						}
						else {
							if (baseFragment instanceof AddActionsFragment)
								((AddActionsFragment) baseFragment).setList(null);
						}
						adapter.addItems(customer_object_list);

						adapter.setHasMoreRows(hasMoreRows);
					}
				});

			} catch (JSONException e) {

				publishProgress(new Runnable() {
					
					@Override
					public void run() {
						//TODO??? copy/pasted from inital code. In initial code, not show toast
						Toast.makeText(
								context,
								context.getResources().getString(
										R.string.load_categories_fail),
								Toast.LENGTH_SHORT);
						
						((Activity) context).finish();
					}
				});
			}finally {
				last_customer = null;
			}

		}
	}

	@SuppressLint("ShowToast")
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		loading.dismiss();
	}
	public void setLastItem(Object last){

		this.last_customer = (Meeting) last;

	}
	public boolean isRunning(){

		return running;

	}
}
