package com.binasystems.mtimereporter.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.api.ReportRequestManager;
import com.binasystems.mtimereporter.api.ReportRequestManager.Callback;
import com.binasystems.mtimereporter.utils.Utils;

public class ForgotpasswordActivity extends BaseActivity {
	public static String EXTRA_COMPANY = "extra_company";
	
	EditText edt_company;
	EditText edt_email;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgotpassword);		
		mReportManager = new ReportRequestManager(this);
		
		setupUI();
	}
	
	private void setupUI(){
		edt_company = (EditText) findViewById(R.id.edt_organisation);
		edt_email = (EditText) findViewById(R.id.edt_email);
		
		String company = getIntent().getStringExtra(EXTRA_COMPANY);
		if(company != null){
			edt_company.setText(company);
		}
		
		edt_email.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if(actionId == EditorInfo.IME_ACTION_DONE ||
						actionId == EditorInfo.IME_ACTION_NEXT){					
					submitRequest();					
					return true;
				}
				return false;
			}
		});
		
		findViewById(R.id.btn_submit).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				submitRequest();
			}
		});
	}
	
	private boolean isValidForm(){	
				
		if(edt_company.getText().toString().isEmpty()){
			Utils.showErrorDialog(this, "Error", "All fields must be completed");
			return false;
		}
		
		String email = edt_email.getText().toString();  
		if(email.isEmpty()){			
			Utils.showErrorDialog(this, "Error", "All fields must be completed");
			return false;
		}
		
		if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
			com.binasystems.mtimereporter.utils.Utils.showErrorDialog(this, "Error", "Wrong email address");
			return false;			
		}
				
		return true;
	}
	
	private void submitRequest(){
		if(isValidForm()){
			showProgress();
			mReportManager.requestForgotPassword(edt_company.getText().toString(), edt_email.getText().toString(), new Callback<String>() {

				@Override
				public void onSuccess(String result) {
					hideProgress();					
					Toast.makeText(ForgotpasswordActivity.this, "Please check your email", Toast.LENGTH_LONG).show();										
					finish();
				}

				@Override
				public void onError(Exception error) {
					hideProgress();
					Utils.showErrorDialog(ForgotpasswordActivity.this, "Error", error.getMessage());					
				}
			});
		}
	}
}
