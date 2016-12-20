package com.binasystems.mtimereporter.activity;

import com.binasystems.mtimereporter.utils.LoggerFacade;
import com.binasystems.mtimereporter.api.ReportRequestManager;
import com.binasystems.mtimereporter.dialog.WaitDialog;
import com.splunk.mint.Mint;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class BaseActivity extends ActionBarActivity{

	protected ReportRequestManager mReportManager;
	protected WaitDialog mProgressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Mint.initAndStartSession(this, "69bcb2e5");

        LoggerFacade.leaveBreadcrumb("call method onCreate, class: " + this.getClass());
		
		mReportManager = new ReportRequestManager(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LoggerFacade.leaveBreadcrumb("call method onDestroy, class: " + this.getClass());
	}

	protected void showProgress(){
        LoggerFacade.leaveBreadcrumb("call method showProgress, class: " + this.getClass());

		try{
			if(mProgressDialog == null){
				mProgressDialog = new WaitDialog(this);			
			}
			
			if(!mProgressDialog.isShowing()){
				mProgressDialog.show();
			}			
		} catch(Exception e){
			
		}
	}
	
	protected void hideProgress(){
        LoggerFacade.leaveBreadcrumb("call method hideProgress(), class: " + this.getClass());
		try{
			if(mProgressDialog != null){
				if(mProgressDialog.isShowing()){
					mProgressDialog.dismiss();
					mProgressDialog = null;				
				}
			}			
		} catch(Exception e){
			
		}
	}
}
