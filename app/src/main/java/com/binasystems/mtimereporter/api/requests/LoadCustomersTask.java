package com.binasystems.mtimereporter.api.requests;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.activity.BaseFragment;
import com.binasystems.mtimereporter.adapter.CustomersListAdapter;
import com.binasystems.mtimereporter.dialog.WaitDialog;
import com.binasystems.mtimereporter.fragment.sales.customers.CustomerFragment;
import com.binasystems.mtimereporter.objects.Customer;

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
public class LoadCustomersTask extends BaseAsyncTask<String> {

	private WaitDialog loading = null;
	private Context context = null;
	private CustomersListAdapter adapter = null;
	private Customer last_customer = null;
	private BaseFragment baseFragment=null;
	private boolean running = false;
	private String param=null;

	public LoadCustomersTask(Context context, CustomersListAdapter customersListAdapter, BaseFragment baseFragment) {
		super(context);
		this.context = context;
		this.adapter=customersListAdapter;
		this.baseFragment=baseFragment;

	}
	public LoadCustomersTask(Context context) {
		super(context);
		this.context = context;


	}
	@Override
	protected void onPreExecute() {
		loading = new WaitDialog(context);
		loading.show();

	};

	@Override
	protected String doInBackground(String... params) {


		UniRequest ur = new UniRequest("Lk/Lk.aspx", "table");
		ur.addLine("Lk", UniRequest.LkC);
		ur.addLine("SwSQL",  UniRequest.SwSQL);
		ur.addLine("UserC", UniRequest.UserC);
		ur.addLine("Company", TimeTrackerApplication.getInstance().getBranch().getC());
		ur.addLine("Sort", "Kod");
		if(params[0]!=null) {
			ur.addLine("q", params[0]);
			param=params[0];

		}
		if(params[1]!=null)
			ur.addLine("TableRows", params[1]);
		if (last_customer != null) {

			ur.addLine("lastC", last_customer.getCode());
			ur.addLine("SortValue", last_customer.getKod().toString());

		}

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


				JSONObject customer = null;

				final ArrayList<Customer> customer_object_list = new ArrayList<Customer>();

				for (int i = 0; i < categories_list.length(); i++) {

					customer = categories_list.getJSONObject(i);

					customer_object_list.add(new Customer(customer));

				}

				final boolean hasMoreRows = response.getBoolean("HasMoreRows");


				publishProgress(new Runnable() {
					@Override
					public void run() {
						if(param==null) {
							if (baseFragment instanceof CustomerFragment)
								((CustomerFragment) baseFragment).setList(customer_object_list,false);
						}
						else {
							if (baseFragment instanceof CustomerFragment)
								((CustomerFragment) baseFragment).setList(customer_object_list,true);
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

		this.last_customer = (Customer) last;

	}
	public boolean isRunning(){

		return running;

	}
}
