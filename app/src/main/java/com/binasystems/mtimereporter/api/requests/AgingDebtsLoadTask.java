package com.binasystems.mtimereporter.api.requests;

import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.dialog.ExceptionDialog;
import com.binasystems.mtimereporter.dialog.WaitDialog;
import com.binasystems.mtimereporter.dialog.customers.CustomerDetailsProjectDialog;
import com.binasystems.mtimereporter.utils.Formatter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.LinkedHashMap;


public class AgingDebtsLoadTask extends ListAsyncTaskNew<JSONObject> {
	private CustomerDetailsProjectDialog window = null;
	private WaitDialog loading = null;
	private boolean running = false;
	private int sug;
	private Double balance;
	private Double sum;
	private String pay_type;
	private String pay_day;
	public AgingDebtsLoadTask(Context context, CustomerDetailsProjectDialog window) {

		super(context);
		this.window = window;
	}

	@Override
	protected void onPreExecute() {
		running = true;
		loading = new WaitDialog(context);
		loading.show();
	};

	@Override
	protected JSONObject doInBackground(String... params) {
		UniRequest ur = new UniRequest("/Lk/GiulHovot.aspx", "table");
		ur.addLine("Lk", UniRequest.LkC);
		ur.addLine("SwSQL", UniRequest.SwSQL);
		ur.addLine("UserC", UniRequest.UserC);
		ur.addLine("Company", TimeTrackerApplication.getInstance().getBranch().getC());

		Calendar c = Calendar.getInstance();

		ur.addLine("Y", String.valueOf(c.get(Calendar.YEAR)));
		ur.addLine("Sug", params[0]);
		ur.addLine("idxC", UniRequest.customer.getCode());
		sug = Integer.parseInt(params[0]);
		try {
			JSONObject result = PostRequest.executeRequestAsJsonResult(ur);
			handlePostResult(result);
			return result;

		} catch (Exception e) {
			onError(context, e);
		}

		return null;
	}

	private void handlePostResult(JSONObject result){
		if (result == null) {
			publishProgress(new Runnable() {
				@Override
				public void run() {
					Dialog alert = new ExceptionDialog(context, context.getResources()
							.getString(R.string.request_fail),
							context.getString(R.string.error),"");
					alert.show();					
				}
			});

		} else {
			JSONObject response = null;
			
			try {
				response = result;

				JSONArray debt_list = response.getJSONArray("Table");

				JSONObject debt = null;
				try {
					debt = debt_list.getJSONObject(0);

				} catch (Exception e) {
					publishProgress(new Runnable() {
						
						@Override
						public void run() {
							Toast.makeText(context.getApplicationContext(),
									context.getString(R.string.no_item),
									Toast.LENGTH_SHORT).show();
							window.update(null,0, null,null,null,null);

							loading.dismiss();
							running = false;							
						}
					});

					return;
				}

				final LinkedHashMap<String, Double> debts = new LinkedHashMap<String, Double>();
				
				try{
					
					int i = 1;
					
					while(true){
						debts.put(debt.getString("M" + i), debt.getDouble("Scm" + i));
						
						i++;
					}
					
				} catch (JSONException e) {
				}
				balance=debt.getDouble("Yitra");
				sum=debt.getDouble("TotScm");
				pay_type=debt.getString("PayType");
				pay_day=debt.getString("PlusDays");

				publishProgress(new Runnable() {

					@Override
					public void run() {
						window.update(debts,sug, balance,sum,pay_type,pay_day);
					}
				});

			} catch (JSONException e) {
				e.printStackTrace();

				publishProgress(new Runnable() {
					@Override
					public void run() {
						Dialog alert = new ExceptionDialog(context,
								context.getString(R.string.request_fail),
								context.getString(R.string.error),"");

						alert.show();
					}
				});
			} 
		}
	}
	
	@Override
	protected void onPostExecute(JSONObject result) {
		super.onPostExecute(result);
		loading.dismiss();
		running = false;
	}

	public void setLastItem(Object last) {}

	public boolean isRunning() {
		return running;
	}

}