package com.binasystems.mtimereporter.api.requests;

import android.content.Context;

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.dialog.Add_action.AddActionProjectDialog;
import com.binasystems.mtimereporter.objects.Project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProjectsLoadTask extends BaseAsyncTask<String>{

	private AddActionProjectDialog.ProjectsListAdapter adapter = null;
	private Project last_project = null;
	private boolean running = false;
	Context context;

	public ProjectsLoadTask(Context context, AddActionProjectDialog.ProjectsListAdapter adapter) {
		super(context);
		this.context=context;
		this.adapter = adapter;

	}

	@Override
	protected void onPreExecute() {

		running = true;

	};

	@Override
	protected String doInBackground(String... params) {

		try {
			UniRequest ur = new UniRequest("Server/Combo/Idx_Projec.aspx",
					"table");
			ur.addLine("Lk",UniRequest.LkC);
			ur.addLine("SwSQL", UniRequest.SwSQL);
			ur.addLine("UserC", UniRequest.UserC);
			ur.addLine("Company", TimeTrackerApplication.getInstance().getBranch().getC());
			ur.addLine("Sort", "Nm");
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
	
				final ArrayList<Project> project_object_list = new ArrayList<Project>();
	
				for (int i = 0; i < project_list.length(); i++) {
	
					project = project_list.getJSONObject(i);
	
					project_object_list.add(new Project(project));
	
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
