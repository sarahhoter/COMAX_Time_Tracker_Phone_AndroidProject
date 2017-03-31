package com.binasystems.mtimereporter.api.requests;

import android.content.Context;
import android.content.DialogInterface;

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.activity.add_action.AddMainMenuActivity;
import com.binasystems.mtimereporter.adapter.ChanceListAdapter;
import com.binasystems.mtimereporter.dialog.Add_action.AddActionChanceDialog;
import com.binasystems.mtimereporter.objects.Combo;
import com.binasystems.mtimereporter.objects.Project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChanceLoadTask extends BaseAsyncTask<String>{

	private ChanceListAdapter adapter = null;
	private Project last_project = null;
	private boolean running = false;
	Context context;
	DialogInterface dialogInterface;
	public ChanceLoadTask(Context context, ChanceListAdapter chanceListAdapter, DialogInterface dialogInterface) {
		super(context);
		this.context=context;
		this.adapter = chanceListAdapter;
		this.dialogInterface=dialogInterface;
	}

	@Override
	protected void onPreExecute() {
		running = true;
	}

	@Override
	protected String doInBackground(String... params) {

		try {
			UniRequest ur = new UniRequest("Server/Combo/PipeLine.aspx", "table");
			ur.addLine("Lk",UniRequest.LkC);
			ur.addLine("SwSQL", UniRequest.SwSQL);
			ur.addLine("UserC", UniRequest.UserC);
			ur.addLine("Company", TimeTrackerApplication.getInstance().getBranch().getC());

			/*
			 * If search query received
			 */
			if (params != null && params.length > 0)
				ur.addLine("q", params[0]);


			String data = PostRequest.executeRequestAsStringResult(ur);
			postDataBackgroundHandleResult(data);
			return data;
			
		} catch (Exception e) {
			e.printStackTrace();
			onError(context, e);
			if(dialogInterface instanceof AddActionChanceDialog)
				((AddActionChanceDialog) dialogInterface).noItem();
			return null;
		}

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
	
				final ArrayList<Combo> chance_object_list = new ArrayList<Combo>();
	
				for (int i = 0; i < chance_list.length(); i++) {
	
					project = chance_list.getJSONObject(i);

					chance_object_list.add(new Combo(project));
	
				}

				// The update adapter was commented in initial code
				final boolean hasMoreRows = response.getBoolean("HasMoreRows");
				publishProgress(new Runnable() {
					
					@Override
					public void run() {
						adapter.addItems(chance_object_list);
						adapter.setHasMoreRows(hasMoreRows);
					}
				});
	
			} catch (JSONException e) {
	
				e.printStackTrace();
				
				showRequestFailExceptionDialog(context);
				
			} finally {
				last_project = null;
			}
		}
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		running = false;
	}

	public void setLastItem(Object last) {this.last_project = (Project) last;}

	public boolean isRunning() {return running;}

}
