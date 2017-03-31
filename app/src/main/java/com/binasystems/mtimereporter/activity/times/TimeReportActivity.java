package com.binasystems.mtimereporter.activity.times;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.activity.BaseActivity;
import com.binasystems.mtimereporter.api.Callback;
import com.binasystems.mtimereporter.api.requests.UniRequest;
import com.binasystems.mtimereporter.utils.AppSettings;
import com.binasystems.mtimereporter.utils.BSLocationManager;
import com.binasystems.mtimereporter.utils.UserCredintails;
import com.binasystems.mtimereporter.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

@SuppressLint("SimpleDateFormat")
public class TimeReportActivity extends BaseActivity implements OnClickListener{
	public static String EXTRA_BRANCH = "extra_branch";
	ArrayList<String> ActionsList;
	Button btn_in;
	Button btn_out;
	private ActionListAdapter list_adapter = null;
	TextView txt_lastAction;
	TextView Name;
	ListView list;
	TextView cancel;
	Context context=null;
	private BSLocationManager mLocationManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setTitle(UniRequest.UserName);
		setContentView(R.layout.activity_time_report);
		mLocationManager = new BSLocationManager(this);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.view_action_bar);
		View v = getActionBar().getCustomView();
		TextView titleTxtView = (TextView) v.findViewById(R.id.action_bar_textView_title);
		cancel=(TextView)v.findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
		titleTxtView.setText(TimeTrackerApplication.getInstance().getBranch().getName());
		UserCredintails userCredintails = UserCredintails.getInstance(TimeTrackerApplication.getInstance());
		ActionsList=new ArrayList<String>();
		list=(ListView) findViewById(R.id.action);
		initUI();
		Name.setText(UniRequest.UserName);
		loadEmployee();
		loadLastActionText(true);
		context=this;
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
				
	private void initUI(){
		btn_in = (Button) findViewById(R.id.btn_in);
		btn_out = (Button) findViewById(R.id.btn_out);
		txt_lastAction = (TextView) findViewById(R.id.txt_lastAction);
		Name=(TextView) findViewById(R.id.name);
		btn_in.setOnClickListener(mOnClickListener);
		btn_out.setOnClickListener(mOnClickListener);
	}

	public String formatServerTitleResponse(String serverTitleResponse){
	    if(serverTitleResponse != null){
	        String[] chunks = serverTitleResponse.split(": : ");
	        if(chunks.length > 0){
	            if(chunks.length== 1){
	                return chunks[0];
	            } else{
	            	String[] dataTimeChunks = chunks[1].split(" ");
	                if(dataTimeChunks.length > 0){
	                    if(dataTimeChunks.length > 1){
	                        return String.format("%s\n%s %s", chunks[0], dataTimeChunks[1], dataTimeChunks[0]);
	                        
	                    } else{
	                    	return String.format("%s\n%s", chunks[0], dataTimeChunks[0]);
	                    }
	                } else{
	                    return chunks[0];
	                }
	            }
	        } else {
	            return serverTitleResponse;
	        }		
	    }
	    return "";
	}
	
	private void updateVisibleActionButtonsByServerResponse(String serverResponseString){
		if(serverResponseString != null){			
			LastActionState lastActionState = LastActionState.parseActionByServerResponse(serverResponseString);
			if(lastActionState != null){
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String currentDateString = formatter.format(Calendar.getInstance().getTime());
				if(currentDateString.equalsIgnoreCase(lastActionState.lastActionDate)){
					if(lastActionState.lastAction == LastActionState.STATE_IN){
						btn_in.setVisibility(View.INVISIBLE);
						btn_out.setVisibility(View.VISIBLE);
						
					} else if(lastActionState.lastAction == LastActionState.STATE_OUT){
						btn_in.setVisibility(View.VISIBLE);
						btn_out.setVisibility(View.INVISIBLE);
					}
				}
			}			
		}
	}
	private void loadEmployee(){
		showProgress();

		mReportManager.requestReportEmployee(new Callback<String>() {
			@Override
			public void onSuccess(String result) {
				String str=result;
				String msg=result;
				if(str.contains("-")) {
					int iend = str.indexOf("-");
					msg= str.substring(0, iend);
				}
				txt_lastAction.setText(msg);
				hideProgress();
			}

			@Override
			public void onError(Exception error) {
				hideProgress();
			}
		});

	}
	private void loadLastActionText(final boolean updateButtonStates){
		showProgress();
		
		mReportManager.requestReportLastAction(new Callback<String>() {
			@Override
			public void onSuccess(String result) {
				String str=result;
				int iend = str.indexOf("-");
				String msg= str.substring(0,iend);


				if(formatServerTitleResponse(msg).equals("לא קיים דיווח להיום ")){
					txt_lastAction.setVisibility(View.VISIBLE);
					txt_lastAction.setText(formatServerTitleResponse(msg));}

				else {
					txt_lastAction.setVisibility(View.GONE);
					ActionsList.add(formatServerTitleResponse(msg));
					list_adapter = new ActionListAdapter(context, R.id.category_item_name,
							ActionsList);
					list.setAdapter(list_adapter);
				}
				if(updateButtonStates){
					updateVisibleActionButtonsByServerResponse(result);
				}
				
				hideProgress();
			}

			@Override
			public void onError(Exception error) {
				hideProgress();
			}
		});
		
	}
	
	private boolean checkIfLocationIsEnabled(){
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if(!lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER) &&
				!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			
			AlertDialog.Builder builder = new Builder(this);
			builder.setTitle("Location service");
			builder.setMessage("Location service is disabled.\nDo you want to enable it?");
			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent viewIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		            startActivity(viewIntent);					
				}
			});
			builder.setNegativeButton("No",null);
			builder.show();			
			return false;
		}
		
		return true;
	}
	
	private void sendLocation(final boolean inRequestFlag){		
		if(!checkIfLocationIsEnabled()){
			return;
		}
		
		showProgress();
		mLocationManager.getCurrentLocation(new BSLocationManager.LocationCallback() {
			
			@Override
			public void onLocationResult(Location location) {
				if(location != null){
					
					if(inRequestFlag){
						showProgress();
						// in action
						mReportManager.requestReportInLocation(location, new Callback<Object>() {

							@Override
							public void onSuccess(Object result) {
								btn_in.setVisibility(View.INVISIBLE);
								btn_out.setVisibility(View.VISIBLE);
								
								loadLastActionText(false);
							}

							@Override
							public void onError(Exception error) {
								Utils.showErrorDialog(TimeReportActivity.this, "Error", error.getMessage());
								hideProgress();
							}
						});
						
					} else{
						// out account
						showProgress();
						mReportManager.requestReportOutLocation(location, new Callback<Object>() {

							@Override
							public void onSuccess(Object result) {
								btn_in.setVisibility(View.VISIBLE);
								btn_out.setVisibility(View.INVISIBLE);
								
								loadLastActionText(false);
							}

							@Override
							public void onError(Exception error) {
								Utils.showErrorDialog(TimeReportActivity.this, "Error", error.getMessage());
								hideProgress();
							}
						});		
					}		
					
				} else{
					hideProgress();
					Utils.showErrorDialog(TimeReportActivity.this, "Error", "Can't get current location");	
				}						
			}
		});
	}
	
	private OnClickListener mOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.btn_in:
				sendLocation(true);
				break;
				
			case R.id.btn_out:
				sendLocation(false);
				break;
				

				case R.id.cancel: {

					onBackPressed();

				}
			}


			
		}
	};
	
	private void logout(boolean withConfirmation){
		if(withConfirmation){
			Builder builder = new Builder(this);
			//builder.setTitle("");
			builder.setMessage("האם אתה בטוח שברצונך להתנתק?");
			builder.setPositiveButton("כן", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
			builder.setNegativeButton("לא", null);
			builder.show();
			
		} else{
			finish();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.cancel: {

				onBackPressed();

			}
		}
	}


	static class LastActionState{
		public static int STATE_UNKNOWN = 0;				
		public static int STATE_IN  = 1;
		public static int STATE_OUT = 2;
		
		public int lastAction;		
		public String lastActionDate;
		
		public static LastActionState parseActionByServerResponse(String serverTitleResponse){
		    LastActionState result = null;
			if(serverTitleResponse != null){
		        String[] chunks = serverTitleResponse.split(": : ");
		        if(chunks.length > 1){
		        	result = new LastActionState();
		        	
		        	// set action date in result 
		        	String[] dataTimeChunks = chunks[1].split(" ");
	                if(dataTimeChunks.length > 1){
	                	result.lastActionDate = dataTimeChunks[1];
	                }
	                
	                // set last action
	                String[] labelTitleChunks = chunks[0].split(" ");
	                if(labelTitleChunks.length > 1){
	                	result.lastAction = STATE_UNKNOWN;

	                	// last action is in
	                	if("כניסה".equals(labelTitleChunks[0])){
	                		result.lastAction = STATE_IN;	
	                	}
	                	
	                	// last action is out
	                	if("יציאה".equals(labelTitleChunks[0])){
	                		result.lastAction = STATE_OUT;	
	                	}	                	
	                }
		        }
		    }
			
			return result;
		}
	}
	public class ActionListAdapter extends SimpleAdapter {
		private ArrayList<String> list = null;
		LayoutInflater layout_inflater;

		private class ViewHolder {


			TextView name;

		}

		public ActionListAdapter(Context context, int textViewResourceId,
								 ArrayList<String> objects) {
			super(context, null, textViewResourceId, null, null);
			layout_inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			list = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			View list_item = null;
			if (convertView != null) {
				list_item = convertView;
			} else {
				list_item = layout_inflater.inflate(R.layout.action_list_item,
						parent, false);
				holder = new ViewHolder();

				holder.name = (TextView) list_item
						.findViewById(R.id.category_item_name);

				list_item.setTag(holder);
			}

			holder = (ViewHolder)list_item.getTag();

			holder.name.setText(list.get(position));


			return list_item;
		}

		public void clear() {

			list.clear();
			notifyDataSetChanged();

		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
	}
}
