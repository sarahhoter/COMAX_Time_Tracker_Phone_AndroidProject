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
import com.binasystems.mtimereporter.fragment.sales.SalesByDateFragment;


public class SaleByDateActivity extends BaseActivity implements View.OnClickListener{
	TextView cancel;

	public static void startActivity(Context caller){
		Intent intent = new Intent(caller, SaleByDateActivity.class);
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
		RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		llp.setMargins(180, 0, 0, 0);
		titleTxtView.setLayoutParams(llp);
		titleTxtView.setGravity(Gravity.CENTER);
		titleTxtView.setText(TimeTrackerApplication.getInstance().getBranch().getName());

		if (savedInstanceState == null) {
			Fragment fragment = new SalesByDateFragment();
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
				case R.id.cancel: {

					onBackPressed();

				}
				break;


			}
		}


}
