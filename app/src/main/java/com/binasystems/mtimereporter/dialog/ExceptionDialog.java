package com.binasystems.mtimereporter.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.binasystems.mtimereporter.R;


public class ExceptionDialog extends Dialog {
	/*
	 * UI Elements
	 */
	private TextView message = null;
	private TextView title = null;
	private Button ok = null;
	
	/**
	 *  Access for OK Button
	 */
	public Button getOkButton() {
		return ok;
	}

	public ExceptionDialog(Context context, CharSequence message,
			CharSequence title,CharSequence button) {
		super(context, android.R.style.Theme_Holo_Light_Dialog);
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		setCancelable(false);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

		setContentView(R.layout.dialog_exception);
		/*
		 * Load UI elements
		 */
		this.message = (TextView) findViewById(R.id.exception_message);
		this.title = (TextView) findViewById(R.id.title);
		ok = (Button) findViewById(R.id.exception_ok);

		/*
		 * Set Listeners
		 */
		ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				dismiss();
			}
		});

		/*
		 * Set Data
		 */
		this.title.setText(title);
		this.message.setText(message);

		if(button!=null&&!button.equals(""))
			ok.setText(button);
		//this.setCustomSize(420, 0);
	}

	/**
	 * Setting new size for exception Dialog
	 * @param width
	 * @param height
	 */
	public void setCustomSize(int width, int height) {
		/*
		 * than height = 0 we take default height
		 */
		if (height == 0)
			height = this.getWindow().getAttributes().height;
		this.getWindow().setLayout(width, height);
	}	
}
