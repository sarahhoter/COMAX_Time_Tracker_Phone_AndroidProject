package com.binasystems.mtimereporter.activity.sales;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.binasystems.mtimereporter.fragment.sales.SalesByAgentDetailsFragment;
import com.binasystems.mtimereporter.fragment.sales.SalesByStoreFragment;
import com.binasystems.mtimereporter.objects.SalesByAgentDetails;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SaleByAgentDetailsActivity extends BaseActivity implements View.OnClickListener{
	TextView cancel;

	public static List<SalesByAgentDetails.AgentInfo> storeData2;
	public static void startActivity(Context caller, String storeCode, Date date, ComaxApiManager.DateViewType dateViewType){
		Intent intent = new Intent(caller, SaleByAgentDetailsActivity.class);
		intent.putExtra(SalesByAgentDetailsFragment.EXTRA_STORE_CODE, storeCode);
		intent.putExtra(SalesByAgentDetailsFragment.EXTRA_CURRENT_DATE, date);
		intent.putExtra(SalesByAgentDetailsFragment.EXTRA_DATE_VIEW_TYPE, dateViewType);
		caller.startActivity(intent);
	}
	
	public static void startActivity(Context caller, int sotorePosition, List<SalesByAgentDetails.AgentInfo> storeData, Date date, ComaxApiManager.DateViewType dateViewType){
		Intent intent = new Intent(caller, SaleByAgentDetailsActivity.class);
		if(storeData.size()>500) {
			storeData2=storeData;
		}
		else
		intent.putExtra(SalesByAgentDetailsFragment.EXTRA_ARRAY_DATA, (Serializable) storeData);
		intent.putExtra(SalesByAgentDetailsFragment.EXTRA_ARRAY_POSITION, sotorePosition);
		intent.putExtra(SalesByAgentDetailsFragment.EXTRA_CURRENT_DATE, date);
		intent.putExtra(SalesByAgentDetailsFragment.EXTRA_DATE_VIEW_TYPE, dateViewType);
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

		titleTxtView.setText(TimeTrackerApplication.getInstance().getBranch().getName());
		RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
		llp.setMargins(180, 0,0, 0);
		titleTxtView.setLayoutParams(llp);
		titleTxtView.setGravity(Gravity.CENTER);
		if (savedInstanceState == null) {

			Fragment fragment = new SalesByAgentDetailsFragment();
			if(storeData2!=null)
			((SalesByAgentDetailsFragment)fragment).setParms(storeData2);
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
		MenuInflater awesome = getMenuInflater();
		awesome.inflate(R.menu.sales, menu);

		for (int i = 0; i < menu.size(); i++) {
			MenuItem item = menu.getItem(i);
			SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
			int end = spanString.length();
			spanString.setSpan(new RelativeSizeSpan(1.5f), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			item.setTitle(spanString);


		}
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.cancel:
				finish();


		}
	}

}
