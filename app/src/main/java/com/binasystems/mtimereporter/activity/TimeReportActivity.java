package com.binasystems.mtimereporter.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
import android.text.Layout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.binasystems.mtimereporter.ActionListAdapter;
import com.binasystems.mtimereporter.CategoriesListAdapter;
import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.api.ReportRequestManager.Callback;
import com.binasystems.mtimereporter.objects.Actions;
import com.binasystems.mtimereporter.utils.AppSettings;
import com.binasystems.mtimereporter.utils.BSLocationManager;
import com.binasystems.mtimereporter.utils.BSLocationManager.LocationCallback;
import com.binasystems.mtimereporter.utils.UserCredintails;
import com.binasystems.mtimereporter.utils.Utils;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.StringUtils;

@SuppressLint("SimpleDateFormat")
public class TimeReportActivity extends BaseActivity {	
	public static String EXTRA_BRANCH = "extra_branch";
ArrayList<String> ActionsList;
	Button btn_in;
	Button btn_out;
	private ActionListAdapter list_adapter = null;
	Button btn_logout;
//	TextView txt_Name;
	TextView txt_lastAction;
	TextView Name;
	ActionBar actionBar;
	ListView list;
	View l;
	String txt;
	Context context=null;
	private BSLocationManager mLocationManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setTitle(AppSettings.getCurrentUser(this));
		setContentView(R.layout.activity_time_report);
		mLocationManager = new BSLocationManager(this);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.view_action_bar);
		UserCredintails userCredintails = UserCredintails.getInstance(TimeTrackerApplication.getInstace());
		ActionsList=new ArrayList<String>();
		list=(ListView) findViewById(R.id.action);
		View v = getActionBar().getCustomView();
		TextView titleTxtView = (TextView) v.findViewById(R.id.action_bar_textView_title);
		titleTxtView.setText(userCredintails.StoreN);
		context=this;



		ActionBar.LayoutParams p = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

		p.gravity = Gravity.CENTER;

		initUI();
		Name.setText(AppSettings.getCurrentUser(context));
		loadEmployee();

		loadLastActionText(true);
	}
	
	@Override
	public void onBackPressed() {
		logout(true);
	}
				
	private void initUI(){
		btn_in = (Button) findViewById(R.id.btn_in);
		btn_out = (Button) findViewById(R.id.btn_out);
	    btn_logout = (Button) findViewById(R.id.btn_logout);
		txt_lastAction = (TextView) findViewById(R.id.txt_lastAction);
		Name=(TextView) findViewById(R.id.name);
		btn_in.setOnClickListener(mOnClickListener);
		btn_out.setOnClickListener(mOnClickListener);
		btn_logout.setOnClickListener(mOnClickListener);
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
////		getMenuInflater().inflate(R.menu.time_report, menu);
////        MenuItem logoutMenuItem = menu.findItem(R.id.action_logout);
////        logoutMenuItem.setActionView(R.layout.action_bar_logout_button);
////        logoutMenuItem.getActionView().findViewById(R.id.btn_logout).setOnClickListener(mOnClickListener);
////        return super.onCreateOptionsMenu(menu);
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		int id = item.getItemId();
//		if (id == R.id.action_logout) {
//            logout(true);
//			return true;
//		}
//
//		return super.onOptionsItemSelected(item);
//	}
	
	public String formatServerTitleResponse(String serverTitleResponse){
	    if(serverTitleResponse != null){
	        String [] chunks = serverTitleResponse.split(": : ");
	        if(chunks.length > 0){
	            if(chunks.length== 1){
	                return chunks[0];
	            } else{
	            	String [] dataTimeChunks = chunks[1].split(" ");
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
				int iend = str.indexOf("-");
				String msg= str.substring(0,iend);
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
					txt_lastAction.setText(formatServerTitleResponse(msg));
					txt_lastAction.setVisibility(View.VISIBLE);}

				else {
					txt_lastAction.setVisibility(View.GONE);
					ActionsList.add(formatServerTitleResponse(msg));
					list_adapter = new ActionListAdapter(context, R.id.category_item_name,
							ActionsList);
					list.setAdapter(list_adapter);
					//txt_lastAction.setText(formatServerTitleResponse(msg));
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
		mLocationManager.getCurrentLocation(new LocationCallback() {
			
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
				
			case R.id.btn_logout:
				logout(true);
				
				break;
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
	
	
	static class LastActionState{
		public static int STATE_UNKNOWN = 0;				
		public static int STATE_IN  = 1;
		public static int STATE_OUT = 2;
		
		public int lastAction;		
		public String lastActionDate;		
		
		public static LastActionState parseActionByServerResponse(String serverTitleResponse){
		    LastActionState result = null;
			if(serverTitleResponse != null){
		        String [] chunks = serverTitleResponse.split(": : ");
		        if(chunks.length > 1){
		        	result = new LastActionState();
		        	
		        	// set action date in result 
		        	String [] dataTimeChunks = chunks[1].split(" ");
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
	
}
