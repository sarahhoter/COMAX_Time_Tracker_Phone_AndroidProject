package com.binasystems.mtimereporter.fragment.sales;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

import com.binasystems.mtimereporter.dialog.Add_action.AddActionDateDialog;

import java.util.Calendar;

/**
 * 
 * @author Bostanica Ion
 * @since 2013-05-23
 * 
 */
public class TimePickerFragment extends DialogFragment implements
		TimePickerDialog.OnTimeSetListener,DialogInterface.OnDismissListener {

	private TextView container = null;
	private Boolean aBoolean=null;
	private Dialog context=null;
	private int sug;



	public void setContainer(TextView container, Boolean auto, Dialog context,int sug){

		this.container = container;
		this.context=context;
		this.aBoolean=auto;
		this.sug=sug;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		return new TimePickerDialog(getActivity(), this, hour, minute, true);
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

		container.setText(((hourOfDay < 10)?"0":"") + hourOfDay + ":" + ((minute < 10)?"0":"") + minute);
		if(aBoolean==true){
			((AddActionDateDialog)context).auto(sug);

		}
	}
	
}