package com.binasystems.mtimereporter.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.binasystems.mtimereporter.api.ReportRequestManager;
import com.binasystems.mtimereporter.dialog.WaitDialog;

public class BaseActivity extends ActionBarActivity implements View.OnTouchListener{

	protected ReportRequestManager mReportManager;
	protected WaitDialog mProgressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mReportManager = new ReportRequestManager(this);

		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayUseLogoEnabled(false);
			actionBar.setDisplayHomeAsUpEnabled(true);
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
	
	public void showProgress(){
		if(mProgressDialog == null){
			mProgressDialog = new WaitDialog(this);			
		}
		
		if(!mProgressDialog.isShowing()){
			mProgressDialog.show();
		}
	}
	
	public void hideProgress(){
		if(mProgressDialog != null){
			if(mProgressDialog.isShowing()){
				mProgressDialog.dismiss();
				mProgressDialog = null;				
			}
		}
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		return false;
	}


}
