package com.binasystems.mtimereporter.activity.sales;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.activity.BaseActivity;
import com.binasystems.mtimereporter.fragment.sales.SalesMenuFragment;

public class SalesMainMenuActivity extends BaseActivity implements View.OnClickListener{
	TextView cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.view_action_bar);
		View v = getActionBar().getCustomView();
		TextView titleTxtView = (TextView) v.findViewById(R.id.action_bar_textView_title);
		cancel=(TextView)v.findViewById(R.id.cancel);
		cancel.setOnClickListener(this);

		titleTxtView.setText(TimeTrackerApplication.getInstance().getBranch().getName());

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new SalesMenuFragment()).commit();
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
		switch (v.getId()) {
			case R.id.cancel: {

				onBackPressed();

			}
			break;

		}
	}
}
