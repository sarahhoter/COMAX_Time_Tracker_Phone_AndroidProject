package com.binasystems.mtimereporter.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.binasystems.mtimereporter.R;


public class SelectDateDialog extends Dialog {
	/*
	 * UI Elements
	 */
	private TextView date = null;
	private TextView month = null;
	private TextView year = null;

	public SelectDateDialog(Context context) {
		super(context, android.R.style.Theme_Holo_Light_Dialog);


			requestWindowFeature(getWindow().FEATURE_NO_TITLE);



		setCancelable(false);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

		setContentView(R.layout.daialog_select_date);
		/*
		 * Load UI elements
		 */
		this.date = (TextView) findViewById(R.id.date);
		this.month = (TextView) findViewById(R.id.month);
		this.year = (TextView) findViewById(R.id.year);
		/*
		 * Set Listeners
		 */
		date.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		month.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		year.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});


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
