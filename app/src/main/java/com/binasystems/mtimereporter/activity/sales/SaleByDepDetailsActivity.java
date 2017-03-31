package com.binasystems.mtimereporter.activity.sales;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.activity.BaseActivity;
import com.binasystems.mtimereporter.activity.BaseFragment;
import com.binasystems.mtimereporter.api.ComaxApiManager;
import com.binasystems.mtimereporter.fragment.sales.SalesByDepartDetailsFragment;
import com.binasystems.mtimereporter.fragment.sales.SalesByStoreFragment;
import com.binasystems.mtimereporter.objects.SalesByDepDetails;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SaleByDepDetailsActivity extends BaseActivity implements View.OnClickListener{
	TextView cancel;

	public static void startActivity(Context caller, String storeCode, Date date, ComaxApiManager.DateViewType dateViewType){
		Intent intent = new Intent(caller, SaleByDepDetailsActivity.class);
		intent.putExtra(SalesByDepartDetailsFragment.EXTRA_STORE_CODE, storeCode);
		intent.putExtra(SalesByDepartDetailsFragment.EXTRA_CURRENT_DATE, date);
		intent.putExtra(SalesByDepartDetailsFragment.EXTRA_DATE_VIEW_TYPE, dateViewType);
		caller.startActivity(intent);		
	}
	
	public static void startActivity(Context caller, int sotorePosition, List<SalesByDepDetails.DepInfo> storeData, Date date, ComaxApiManager.DateViewType dateViewType){
		Intent intent = new Intent(caller, SaleByDepDetailsActivity.class);
		intent.putExtra(SalesByDepartDetailsFragment.EXTRA_ARRAY_DATA, (Serializable)storeData);
		intent.putExtra(SalesByDepartDetailsFragment.EXTRA_ARRAY_POSITION, sotorePosition);
		intent.putExtra(SalesByDepartDetailsFragment.EXTRA_CURRENT_DATE, date);
		intent.putExtra(SalesByDepartDetailsFragment.EXTRA_DATE_VIEW_TYPE, dateViewType);
		if(date != null){
			intent.putExtra(SalesByStoreFragment.EXTRA_DATE, date);
		}
		if(dateViewType != null){
			intent.putExtra(BaseFragment.EXTRA_DATE_VIEW_TYPE, dateViewType);
		}
		caller.startActivity(intent);
	}	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		getActionBar().setDisplayOptions(android.app.ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.view_action_bar);
		View v = getActionBar().getCustomView();
		TextView titleTxtView = (TextView) v.findViewById(R.id.action_bar_textView_title);
		cancel=(TextView)v.findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
		RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
		llp.setMargins(180, 0,0, 0);
		titleTxtView.setLayoutParams(llp);
		titleTxtView.setGravity(Gravity.CENTER);
		titleTxtView.setText(TimeTrackerApplication.getInstance().getBranch().getName());
		if (savedInstanceState == null) {

			Fragment fragment = new SalesByDepartDetailsFragment();
			fragment.setArguments(getIntent().getExtras());
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, fragment).commit();
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {		
		if(item.getItemId() == android.R.id.home){
			finish();
			return true;
		}		
		return super.onOptionsItemSelected(item);
	}
			
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sales, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.cancel: {

				onBackPressed();

			}
			break;

		}
	}
}
