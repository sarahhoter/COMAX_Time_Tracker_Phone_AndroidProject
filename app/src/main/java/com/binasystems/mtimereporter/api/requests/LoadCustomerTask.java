package com.binasystems.mtimereporter.api.requests;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.adapter.CustomersListAdapter;
import com.binasystems.mtimereporter.dialog.WaitDialog;
import com.binasystems.mtimereporter.dialog.customers.CustomerDetailsProjectDialog;
import com.binasystems.mtimereporter.objects.Customer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author Bostanica Ion
 * @since 2013-05-21
 * 
 */
public class LoadCustomerTask extends BaseAsyncTask<String> {

	private WaitDialog loading = null;
	private Context context = null;
	private CustomersListAdapter adapter = null;
	private Customer last_customer = null;
	private Dialog dialog=null;
	Boolean flag;
	private boolean running = false;
	private String param=null;
	JSONObject customer = null;
	public LoadCustomerTask(Context context, Dialog dialog) {
		super(context);
		this.context = context;

		this.dialog=dialog;

	}
	public LoadCustomerTask(Context context) {
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


		UniRequest ur = new UniRequest("Lk/Lk.aspx", params[0]);
		ur.addLine("Lk", UniRequest.LkC);
		ur.addLine("SwSQL",  UniRequest.SwSQL);
		ur.addLine("UserC", UniRequest.UserC);
		ur.addLine("Company", TimeTrackerApplication.getInstance().getBranch().getC ());
		ur.addLine("Sort", "Kod");
		ur.addLine("C", params[1]);
		ur.addLine("SortValue", params[2]);

		if(params.toString().equals("prev_item"))
			flag=true;
		else flag=false;
		String data;
		try {
			data = PostRequest.executeRequestAsStringResult(ur);
			if (data == null) {

				Toast.makeText(
						context,
						context.getResources().getString(
								R.string.load_categories_fail), Toast.LENGTH_SHORT)
						.show();



			} else {

				JSONObject response = null;

				try {

					response = new JSONObject(data);

					JSONArray customer_list = response.getJSONArray("Table");

					customer = null;
					try {
						customer = customer_list.getJSONObject(0);

						/*if(dialog instanceof CustomerNewInvoices) {

							((CustomerNewInvoices) dialog).setCustomer(new Customer(customer));


							//((CustomerNewInvoices) dialog).setCustomer(null);

						}*/if(dialog instanceof CustomerDetailsProjectDialog) {

							UniRequest.customer=new Customer(customer);


							//((CustomerNewInvoices) dialog).setCustomer(null);

						}

					} catch (Exception e) {
						Toast.makeText(context.getApplicationContext(),
								context.getString(R.string.no_item),
								Toast.LENGTH_SHORT).show();
						loading.dismiss();

					}

				} catch (JSONException e) {

					//TODO??? copy/pasted from inital code. In initial code, not show toast
					Toast.makeText(
							context,
							context.getResources().getString(
									R.string.load_categories_fail),
							Toast.LENGTH_SHORT);
					loading.dismiss();
					//		((Activity) context).finish();


				}finally {last_customer = null;}
			}
			loading.dismiss();
			//postDataBackgroundHandleResult(data);
			return data;
		} catch (Exception e) {
			//onError(context, e);
//			Handler handler = new Handler(Looper.getMainLooper());
//			handler.post(new Runnable() {
//					@Override
//					public void run() {
//					((CustomerNewInvoices) dialog).setCustomer(null);
//				}
//			});

		}
		
		return null;
	}


	@SuppressLint("ShowToast")
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		loading.dismiss();
		//((CustomerNewInvoices) dialog).setCustomer(new Customer(customer));
//		if (result == null) {
//
//					Toast.makeText(
//							context,
//							context.getResources().getString(
//									R.string.load_categories_fail), Toast.LENGTH_SHORT)
//							.show();
//
//
//
//		} else {
//
//			JSONObject response = null;
//
//			try {
//
//				response = new JSONObject(result);
//
//				JSONArray customer_list = response.getJSONArray("Table");
//
//				customer = null;
//				try {
//					customer = customer_list.getJSONObject(0);
//
//					if(dialog instanceof CustomerNewInvoices) {
//
//						((CustomerNewInvoices) dialog).setCustomer(new Customer(customer));
//
//
//						//((CustomerNewInvoices) dialog).setCustomer(null);
//
//					}
//
//				} catch (Exception e) {
//					Toast.makeText(context.getApplicationContext(),
//							context.getString(R.string.no_item),
//							Toast.LENGTH_SHORT).show();
//					loading.dismiss();
//					return;
//				}
//
//
//
//
//
//
//			} catch (JSONException e) {
//
//
//						//TODO??? copy/pasted from inital code. In initial code, not show toast
//						Toast.makeText(
//								context,
//								context.getResources().getString(
//										R.string.load_categories_fail),
//								Toast.LENGTH_SHORT);
//
//						//		((Activity) context).finish();
//
//
//			}finally {
//				last_customer = null;
//			}
//
//		}
//		loading.dismiss();
	}
	public void setLastItem(Object last){

		this.last_customer = (Customer) last;

	}
	public boolean isRunning(){

		return running;

	}
}
