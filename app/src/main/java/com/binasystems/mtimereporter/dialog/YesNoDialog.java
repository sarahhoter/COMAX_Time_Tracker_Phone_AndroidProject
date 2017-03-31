package com.binasystems.mtimereporter.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.binasystems.mtimereporter.R;


public class YesNoDialog extends ComaxDialog implements
		android.view.View.OnClickListener {

	/*
	 * UI Elements
	 */
	private TextView message = null;
	private Button ok = null;
	private Button cancel = null;
	private boolean answer = false;

	public YesNoDialog(Context context, CharSequence message, CharSequence title, CharSequence okText, CharSequence cancelText, double height) {
		super(context, R.layout.dialog_yes_no, true, 0.6, height);


		/*
		 * Load UI elements
		 */
		this.message = (TextView) findViewById(R.id.message);
		ok = (Button) findViewById(R.id.ok);
		cancel = (Button) findViewById(R.id.cancel);
		ok.setText(okText);
		cancel.setText(cancelText);

		/*
		 * Set Listeners
		 */
		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);

		/*
		 * Set Data
		 */
		this.message.setText(message);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.ok:

			answer = true;

			break;
		case R.id.cancel:

			answer = false;

			break;

		default:
			break;
		}

		dismiss();

	}

	public boolean isPossitive() {

		return answer;

	}
	
	public void disableTitle(){
		
		this.requestWindowFeature(getWindow().FEATURE_NO_TITLE); 
		
	}

}
