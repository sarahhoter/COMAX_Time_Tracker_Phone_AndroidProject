package com.binasystems.mtimereporter.api.requests;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.activity.MainMenuActivity;
import com.binasystems.mtimereporter.dialog.ExceptionDialog;
import com.binasystems.mtimereporter.objects.Project;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ObligationPsishaLoadTask extends BaseAsyncTask<String>{


	private Project last_project = null;
	private boolean running = false;
	Context context;
	Activity activity;
	Dialog my_dialog;

	public ObligationPsishaLoadTask(Context context, Activity activity,Dialog dialog) {
		super(context);
		this.activity=activity;
		this.context=context;
		this.my_dialog=dialog;
	}

	@Override
	protected void onPreExecute() {
		running = true;

	};

	@Override
	protected String doInBackground(String... params) {

			UniRequest ur = new UniRequest("Divor/IdxPgisha.aspx", "execute");
			ur.addLine("Lk",UniRequest.LkC);
			ur.addLine("SwSQL",  UniRequest.SwSQL);
			ur.addLine("UserC", UniRequest.UserC);
			ur.addLine("Company", TimeTrackerApplication.getInstance().getBranch().getC());

			try {

				JSONObject js = new JSONObject();
//				if(TimeTrackerApplication.getInstance().getDate().equals(""))
//					js.put("D", null);
//				else
				js.put("D", TimeTrackerApplication.getInstance().getDate());
//				if(TimeTrackerApplication.getInstance().getHour().equals(""))
//					js.put("Hour", null);
//				else
				js.put("Hour", TimeTrackerApplication.getInstance().getHour());
				js.put("ToHour", TimeTrackerApplication.getInstance().getUntil_hour());
				if(UniRequest.project!=null)
					js.put("IdxProj",UniRequest.project.getCode().toString());
				else js.put("IdxProj","0");

//				if(TimeTrackerApplication.getInstance().getDuring().equals(""))
//					js.put("Long", null);
//				else
				js.put("Long", TimeTrackerApplication.getInstance().getDuring());
//				if(TimeTrackerApplication.getInstance().getSwtype().equals(""))
//					js.put("SugPeilot", null);
//				else
				js.put("SugPeilot", TimeTrackerApplication.getInstance().getSwtype());
//				if(TimeTrackerApplication.getInstance().getSwstatus().equals(""))
//					js.put("SwStatusPgisha", null);
//				else
				js.put("SwStatusPgisha", TimeTrackerApplication.getInstance().getSwstatus());
//				if( TimeTrackerApplication.getInstance().getLocation().equals(""))
//					js.put("Mikom", null);
//				else
				js.put("Mikom", TimeTrackerApplication.getInstance().getLocation());
//				if(TimeTrackerApplication.getInstance().getPresent().equals(""))
//					js.put("Nachecho", null);
//				else
				js.put("Nachecho", TimeTrackerApplication.getInstance().getPresent());
//				if(TimeTrackerApplication.getInstance().getDescription().equals(""))
//					js.put("TeurPgisha", null);
//				else
				js.put("TeurPgisha", TimeTrackerApplication.getInstance().getDescription());
//				if(TimeTrackerApplication.getInstance().getSum().equals(""))
//					js.put("Cikom", null);
//				else
				js.put("Cikom", TimeTrackerApplication.getInstance().getSum());
//				if(TimeTrackerApplication.getInstance().getTreatmentDate().equals(""))
//					js.put("Dact", null);
//				else
				js.put("Dact", TimeTrackerApplication.getInstance().getTreatmentDate());
//				if( TimeTrackerApplication.getInstance().getTreatmentHour().equals(""))
//					js.put("Hact", null);
//				else
				js.put("Hact", TimeTrackerApplication.getInstance().getTreatmentHour());
//				if(UniRequest.customer==null)
//					js.put("IdxC", null);
//				else
				js.put("IdxC",UniRequest.customer.getCode());



				//js.put("IdxC", UniRequest.customer.getCode());// for AllMeetings must be set earlier in

					js.put("PgishaC", "0");

				js.put("Mode", "ADD");
				js.put("SwSendMail", TimeTrackerApplication.getInstance().getSendEnail());

				JSONArray jsa = new JSONArray();
				jsa.put(js);
				js = new JSONObject();
				js.put("Table", jsa);
				js.put("HasMoreRows", false);

				Log.d("atf_Meeting", "Sended Json= " + js.toString());

				ur.addLine("Data", js.toString());

				try {
					return PostRequest.executeRequestAsStringResult(ur);
				} catch (Exception e) {
					e.printStackTrace();
					onError(context, e);
				}

			} catch (JSONException e) {
				Log.d("atf", "Eror i_meetings_add, class: CKLlistener, v==done");
			}

			return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);


		Dialog alert = null;

		if (result==null || result.trim().isEmpty()) {


		} else {

			Log.d("result", result);
			alert = new ExceptionDialog(context, context.getResources()
					.getString(R.string.make),
					null,"ok");
			alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
				@Override
				public void onDismiss(DialogInterface dialog) {
					TimeTrackerApplication.getInstance().setClose(true);
					my_dialog.dismiss();

				}
			});
			alert.show();



		}


		running = false;
	}

	public void setLastItem(Object last) {

		this.last_project = (Project) last;

	}

	public boolean isRunning() {

		return running;

	}

}
