package com.binasystems.mtimereporter.fragment.sales;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;


import com.binasystems.mtimereporter.R;

import java.util.Calendar;

/**
 * 
 * @author Bostanica Ion
 * @since 2013-05-23
 * 
 */
@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener, OnDismissListener, OnClickListener {

	/*
	 * 
	 */
	public static final short DD_MM_YYYY = 1;
	public static final short MM_DD_YYYY = 2;
	public static final short MM_YYYY = 3;

	private boolean ok = false;
	private short format = DD_MM_YYYY;
	private int year = 0;
	private int month = 0;
	private int day = 0;
	
	DatePickerDialog dpd;

	private TextView container = null;

	private boolean nullify = false;

	public DatePickerFragment(boolean nullify) {

		this.nullify = nullify;

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		 dpd = new DatePickerDialog(getActivity(), this, year,
				month, day);
		dpd.setButton(DialogInterface.BUTTON_NEGATIVE, getResources().getString(R.string.cancel), this);
		dpd.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.done), this);

		return dpd;
	}

	public void setContainer(TextView container, short format) {

		this.container = container;
		this.format = format;

	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
						  int dayOfMonth) {

		day = dayOfMonth;
		month = monthOfYear + 1;
		this.year = year;

	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);

		if (ok) {

			if (format == DD_MM_YYYY)
				container.setText(((day < 10) ? "0" : "") + day + "/"
						+ ((month < 10) ? "0" : "") + month + "/" + year);
			else if (format == MM_DD_YYYY)
				container.setText(((month < 10) ? "0" : "") + month + "/"
						+ ((day < 10) ? "0" : "") + day + "/" + year);
			else
				container.setText(((month < 10) ? "0" : "") + month + "/"
						+ year);
			
		} else if (nullify) {

			//container.setText("");

		}

	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		
		
		day = dpd.getDatePicker().getDayOfMonth();
		month = dpd.getDatePicker().getMonth()+1;
		this.year = dpd.getDatePicker().getYear();
		

		if (which == DialogInterface.BUTTON_NEGATIVE)
			ok = false;
		else if (which == DialogInterface.BUTTON_POSITIVE)
			ok = true;

	}
}