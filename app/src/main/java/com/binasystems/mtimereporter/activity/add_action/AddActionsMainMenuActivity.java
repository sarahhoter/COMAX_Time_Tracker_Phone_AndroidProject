package com.binasystems.mtimereporter.activity.add_action;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.activity.BaseActivity;
import com.binasystems.mtimereporter.fragment.sales.AddAction.AddActionCustomerFragment;
import com.binasystems.mtimereporter.fragment.sales.AddAction.AddActionsFragment;
import com.binasystems.mtimereporter.fragment.sales.customers.CustomerFragment;

public class AddActionsMainMenuActivity extends BaseActivity implements View.OnClickListener{
TextView cancel;
TextView next;
	TextView titleTxtView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.view_action_bar);
		View v = getActionBar().getCustomView();
		titleTxtView = (TextView) v.findViewById(R.id.action_bar_textView_title);
		cancel=(TextView)v.findViewById(R.id.cancel);
		next=(TextView)v.findViewById(R.id.actionBarNext);
		next.setOnClickListener(this);
		cancel.setOnClickListener(this);
		titleTxtView.setText(R.string.action);
		next.setVisibility(View.VISIBLE);
		next.setText("+");
		TimeTrackerApplication.getInstance().setCurrentActivity(this);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new AddActionsFragment()).commit();
		}
	}

	public void addFragmentToStack(Fragment fragment) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.container, fragment).addToBackStack("_").commit();
	}

	@Override
	public void onBackPressed() {

		if (getSupportFragmentManager().getBackStackEntryCount() > 1) {			
			getSupportFragmentManager().popBackStack();
			return;
		}

		super.onBackPressed();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.cancel: finish();break;
			case R.id.actionBarNext:{
				next.setVisibility(View.GONE);
				titleTxtView.setText(R.string.add_action);
				getSupportFragmentManager().beginTransaction()
						.add(R.id.container, new AddActionCustomerFragment()).commit();
			}break;
		}
	}
}
