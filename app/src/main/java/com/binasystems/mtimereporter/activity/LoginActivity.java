package com.binasystems.mtimereporter.activity;

import org.json.JSONObject;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.api.requests.LoginTask;
import com.binasystems.mtimereporter.utils.AppSettings;
import com.binasystems.mtimereporter.utils.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginActivity extends ActionBarActivity {

	EditText edit_organisation;
	EditText edit_login;
	EditText edit_password;
	CheckBox chbx_rememberMe;
	CheckBox chbx_rememberpass;
	TextView ver_name;
	TextView date;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		initUI();
	}

	private void initUI() {
		edit_organisation = (EditText) findViewById(R.id.edt_organisation);
		edit_login = (EditText) findViewById(R.id.edt_login);
		edit_password = (EditText) findViewById(R.id.edt_password);
		chbx_rememberMe = (CheckBox) findViewById(R.id.chx_rememberMe);
		chbx_rememberpass=(CheckBox)findViewById(R.id.chx_rememberpass);
		ver_name = (TextView) findViewById(R.id.var_name);
		ver_name.setText(Utils.getVersionName(TimeTrackerApplication.getInstace()));
		date = (TextView) findViewById(R.id.date);
		date.setText(Utils.currentDate());

		// remeber me
		if(AppSettings.isLoginRememberMe(this)){


			chbx_rememberMe.setChecked(true);
			edit_organisation.setText(AppSettings.getLofinCompany(this));
			edit_login.setText(AppSettings.getLofinLogin(this));
		}
		if(AppSettings.isLoginRememberPass(this)){
			chbx_rememberpass.setChecked(true);
			edit_organisation.setText(AppSettings.getLofinCompany(this));
			edit_login.setText(AppSettings.getLofinLogin(this));
			edit_password.setText(AppSettings.getLoginPassword(this));
		}
		chbx_rememberMe.setOnCheckedChangeListener(new OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(!isChecked){
					//edit_password.setText("");
					AppSettings.setLoginRememberMe(LoginActivity.this, false);
					AppSettings.saveLoginData(LoginActivity.this, "", "", "");
				}
			}
		});
		chbx_rememberpass.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					//edit_password.setText("");
					AppSettings.setLoginRememberPass(LoginActivity.this, false);
					AppSettings.saveLoginData(LoginActivity.this, "", "", "");
				}
			}
		});
		edit_password.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView arg0, int actionId, KeyEvent event) {
			    if (actionId == EditorInfo.IME_ACTION_SEARCH ||
		                actionId == EditorInfo.IME_ACTION_DONE ||
		                event.getAction() == KeyEvent.ACTION_DOWN &&
		                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
			    	
		            doLoginRequest();
		            return true;
		        }				
				return false;
			}
		});
		
		findViewById(R.id.btn_login).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				doLoginRequest();
			}
		});
		
//		findViewById(R.id.login_forgot_pass).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				openForgotPasswordActivity();
//			}
//		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();		
		if(edit_organisation.getText().toString().isEmpty()){
			edit_organisation.requestFocus();
			
		} else if(edit_login.getText().toString().isEmpty()){
			edit_login.requestFocus();	
			
		} else if(edit_password.getText().toString().isEmpty()){
			edit_password.requestFocus();
		}
	}

	private boolean isValid() {

		if (edit_organisation.getText().toString().isEmpty()) {
			Utils.showErrorDialog(this, "Error", "All fields must be completed");
			return false;
		}

		if (edit_login.getText().toString().isEmpty()) {
			Utils.showErrorDialog(this, "Error", "All fields must be completed");
			return false;
		}

		if (edit_password.getText().toString().isEmpty()) {
			Utils.showErrorDialog(this, "Error", "All fields must be completed");
			return false;
		}

		return true;
	}

	private void cleanLoginForm() {
		edit_login.setText("");
		edit_organisation.setText("");
		edit_password.setText("");		
	}
	
	private void openForgotPasswordActivity(){
		Intent intent = new Intent(this, ForgotpasswordActivity.class);
		intent.putExtra(ForgotpasswordActivity.EXTRA_COMPANY, edit_organisation.getText().toString());
		startActivity(intent);
	}

	private void doLoginRequest() {

		if (isValid()) {
			LoginTask loginTask = new LoginTask(this) {
				@Override
				protected void onPostExecute(JSONObject result) {
					super.onPostExecute(result);					
					if (!hasError()) {
						//edit_password.setText("");
						if (chbx_rememberMe.isChecked()) {							
							AppSettings.saveLoginData(LoginActivity.this,
									edit_organisation.getText().toString(), edit_login.getText().toString(),
									edit_password.getText().toString());
							AppSettings.setLoginRememberMe(LoginActivity.this, true);
//							if(!chbx_rememberpass.isChecked())
//								edit_password.setText("");
							if(!chbx_rememberpass.isChecked())
								edit_password.setText("");
							else{
								AppSettings.saveLoginData(LoginActivity.this,
										edit_organisation.getText().toString(), edit_login.getText().toString(),
										edit_password.getText().toString());
								AppSettings.setLoginRememberPass(LoginActivity.this, true);
							}
						} else {
							cleanLoginForm();
						}
						
						if(result != null){
							Intent intent = new Intent(LoginActivity.this,
									CategorySelectActivity.class);
							LoginActivity.this.startActivity(intent);
														
							LoginActivity.this.overridePendingTransition(
									R.anim.right_slide_in,
									R.anim.left_slide_out);			
						}						
					}					
				}
			};
			
			loginTask.execute(edit_organisation.getText().toString(),
					edit_login.getText().toString(), edit_password.getText()
							.toString());
		}
	}
}
