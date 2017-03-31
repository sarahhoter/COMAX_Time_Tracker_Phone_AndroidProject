package com.binasystems.mtimereporter.api.requests;

import android.content.Context;
import android.content.DialogInterface;

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.adapter.ChanceListAdapter;
import com.binasystems.mtimereporter.dialog.Add_action.AddActionChanceDialog;
import com.binasystems.mtimereporter.dialog.Add_action.AddActionStatusChanceDialog;
import com.binasystems.mtimereporter.objects.Combo;
import com.binasystems.mtimereporter.objects.Project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StatusChanceLoadTask extends BaseAsyncTask<String>{

	private ChanceListAdapter adapter = null;
	private Project last_project = null;
	private boolean running = false;
	Context context;
	DialogInterface dialogInterface;
	public StatusChanceLoadTask(Context context, ChanceListAdapter adapter, DialogInterface dialogInterface) {
		super(context);
		this.context=context;
		this.adapter = adapter;
		this.dialogInterface=dialogInterface;
	}

	@Override
	protected void onPreExecute() {

		running = true;

	};

	@Override
	protected String doInBackground(String... params) {

		try {
			UniRequest ur = new UniRequest("Server/Combo/PipeLine_Status.aspx",
					"table");
			ur.addLine("Lk",UniRequest.LkC);
			ur.addLine("SwSQL", UniRequest.SwSQL);
			ur.addLine("UserC", UniRequest.UserC);
			ur.addLine("Company", TimeTrackerApplication.getInstance().getBranch().getC());
			ur.addLine("IdxC",  UniRequest.customer.getCode());

			/*
			 * If search query received
			 */
			if (params != null && params.length > 0)
				ur.addLine("q", params[0]);

			/*
			 * If there is last item
			 */
			if (last_project != null) {

				ur.addLine("lastC", String.valueOf(last_project.getCode()));
				ur.addLine("SortValue", last_project.getName());

			}

			String data = PostRequest.executeRequestAsStringResult(ur);
			postDataBackgroundHandleResult(data);
			return data;
			
		} catch (Exception e) {
			e.printStackTrace();
			onError(context, e);
			if(dialogInterface instanceof AddActionStatusChanceDialog)
				((AddActionStatusChanceDialog) dialogInterface).noItem();

			return null;
		}
			/*
			 
			HttpPost post = ur.getPost();
			client = new DefaultHttpClient();
			HttpResponse r = client.execute(post);
			int status = r.getStatusLine().getStatusCode();

			if (status == 200) {
				HttpEntity e = r.getEntity();
				data = EntityUtils.toString(e, "UTF-8");
			} else {

				data = "{ \"Message\": " + r.getStatusLine().getReasonPhrase()
						+ "}";

			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		loading.setCancelable(false);
		return Encrypter.decrypt(data);
		
		
		*/
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
	
				JSONArray project_list = response.getJSONArray("Table");
	
				JSONObject project = null;
	
				final ArrayList<Combo> project_object_list = new ArrayList<Combo>();
	
				for (int i = 0; i < project_list.length(); i++) {
	
					project = project_list.getJSONObject(i);
	
					project_object_list.add(new Combo(project));
	
				}

//				ComaxApplication.getInstance().getSalesProjects().clear();
//				ComaxApplication.getInstance().getSalesProjects().addAll(project_object_list);
	
				// The update adapter was commented in initial code
				final boolean hasMoreRows = response.getBoolean("HasMoreRows");
				publishProgress(new Runnable() {
					
					@Override
					public void run() {
						adapter.addItems(project_object_list);
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
		//dismissLoadingDialog();
		running = false;
	}

	public void setLastItem(Object last) {

		this.last_project = (Project) last;

	}

	public boolean isRunning() {

		return running;

	}

}
