package com.binasystems.mtimereporter.dialog.Add_action;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.api.requests.UniRequest;
import com.binasystems.mtimereporter.fragment.sales.DatePickerFragment;
import com.binasystems.mtimereporter.fragment.sales.TimePickerFragment;
import com.binasystems.mtimereporter.objects.Product;
import com.binasystems.mtimereporter.utils.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This is sales select menu fragment
 * @author binasystems
 *
 */
public class AddActionDateDialog extends Dialog implements OnItemClickListener,View.OnClickListener,DialogInterface.OnDismissListener{

	View action_bar;
	TextView cancel;
	TextView name;
	TextView date;
	TextView hour;
	TextView hour_s;
	TextView until;
	TextView location;

	TextView present;
	TextView description;
	TextView sum;
	TextView date_treatment;
	TextView hour_treatment;
	TextView sumClose;
	TextView presentClose;
	TextView next;
	Dialog my_dialog;
	DialogFragment newFragment;
	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
	Context context;
	public AddActionDateDialog(Context context, int theme) {
		super(context, theme);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		my_dialog=this;
		setContentView(theme);
		this.context=context;
		action_bar=findViewById(R.id.action_bar);
		((TextView)findViewById(R.id.action_bar_textView_title)).setText(R.string.add_action);
		cancel=(TextView)findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
		date=(TextView)findViewById(R.id.select_date);
		name=(TextView)findViewById(R.id.name);
		sumClose=(TextView)findViewById(R.id.sumClose);
		presentClose=(TextView)findViewById(R.id.presentClose);
		hour=(TextView) findViewById(R.id.hour);
		hour_s=(TextView)findViewById(R.id.hour_s);
		until=(TextView)findViewById(R.id.until);
		location=(TextView)findViewById(R.id.location);

		next=(Button)findViewById(R.id.next);
		present = (TextView)findViewById(R.id.present);
		description = (TextView)findViewById(R.id.description);
		sum = (TextView)findViewById(R.id.sum);
		date_treatment=(TextView)findViewById(R.id.treatment_date);
		hour_treatment=(TextView)findViewById(R.id.hour_treatment);
		date_treatment.setOnClickListener(this);
		hour_treatment.setOnClickListener(this);
		date.setOnClickListener(this);
		hour.setOnClickListener(this);
		until.setOnClickListener(this);
		next=(TextView)action_bar. findViewById(R.id.actionBarNext);
		next.setVisibility(View.VISIBLE);
		next.setOnClickListener(this);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String currentDateString = formatter.format(Calendar.getInstance().getTime());
		date.setText(currentDateString);
		name.setText(UniRequest.customer.getName().trim());
		hour_s.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {

				if(!hour_s.getText().toString().equals(""))
				{
					Date date = new Date();
					if (!hour.getText().toString().equals("") || !until.getText().toString().equals("")) {
						if(until.getText().toString().equals("") ) {
							String untilTxt = until.getText().toString();
							try {
								date = simpleDateFormat.parse(untilTxt);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else {
							String txt = hour.getText().toString();

							try {
								date = simpleDateFormat.parse(txt);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(date);
						double hh=Double.valueOf((hour_s.getText().toString()));
						double ss=(Double.valueOf(hour_s.getText().toString()));
						ss=ss/100*60;
						ss=ss-(int)hh;
						ss=(double) Math.round(ss * 100) / 100;
						//ss = (ss*100)/60;

						ss=ss*100;
					//	ss = (ss*100)/60;
						calendar.add(Calendar.HOUR,(int) hh);
						calendar.add(Calendar.MINUTE,(int)ss);
						String d="";
						try {
							d=simpleDateFormat.format(calendar.getTime());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					//	until.setText(d);
					}
				}
			}
		});

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


	}

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {

		if(item.getItemId() == android.R.id.home){
			dismiss();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Product product=(Product) parent.getItemAtPosition(position);
		UniRequest.product=product;


	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.cancel: {
				dismiss();
			}break;
			case R.id.select_date: {
				 newFragment = new DatePickerFragment(true);
				((DatePickerFragment) newFragment).setContainer(date,
						DatePickerFragment.DD_MM_YYYY);
				newFragment.show(TimeTrackerApplication.getInstance().getCurrentActivity().getFragmentManager(),
						"datePicker");
			}break;
			case R.id.hour: {

				newFragment = new TimePickerFragment();
				((TimePickerFragment) newFragment).setContainer(hour,true,this,1);
				newFragment.show(TimeTrackerApplication.getInstance().getCurrentActivity().getFragmentManager(), "timePicker");

			}break;
			case R.id.until: {
				 newFragment = new TimePickerFragment();
				((TimePickerFragment) newFragment).setContainer(until,true,this,2);
				newFragment.show(TimeTrackerApplication.getInstance().getCurrentActivity().getFragmentManager(), "timePicker");

			}break;
			case R.id.actionBarNext: {
				if(isValid()) {
					TimeTrackerApplication.getInstance().setSumClose(sumClose.getText().toString());
					TimeTrackerApplication.getInstance().setPresentClose(presentClose.getText().toString());
					TimeTrackerApplication.getInstance().setTreatmentDate(date_treatment.getText().toString());
					TimeTrackerApplication.getInstance().setTreatmentHour(hour_treatment.getText().toString());
					TimeTrackerApplication.getInstance().setDate(date.getText().toString());
					TimeTrackerApplication.getInstance().setHour(hour.getText().toString());
					TimeTrackerApplication.getInstance().setDuring(hour_s.getText().toString().trim());
					TimeTrackerApplication.getInstance().setUntil_hour(until.getText().toString());
					TimeTrackerApplication.getInstance().setLocation(location.getText().toString());
					TimeTrackerApplication.getInstance().setPresent(present.getText().toString());
					TimeTrackerApplication.getInstance().setDescription(description.getText().toString());
					TimeTrackerApplication.getInstance().setSum(sum.getText().toString());
					AddActionTypeDialog addActionTypeDialog=new AddActionTypeDialog(context,R.layout.dialog_add_action_select_type);
					addActionTypeDialog.setOnDismissListener(new OnDismissListener() {
						@Override
						public void onDismiss(DialogInterface dialog) {
							if(TimeTrackerApplication.getInstance().getClose())
								my_dialog.dismiss();
						}
					});
					addActionTypeDialog.show();
				}
			}break;
			case R.id.treatment_date: {
				DialogFragment newFragment = new DatePickerFragment(true);
				((DatePickerFragment) newFragment).setContainer(date_treatment,
						DatePickerFragment.DD_MM_YYYY);
				newFragment.show(TimeTrackerApplication.getInstance().getCurrentActivity().getFragmentManager(),
						"datePicker");
			}break;
			case R.id.hour_treatment: {

				DialogFragment newFragment = new TimePickerFragment();
				((TimePickerFragment) newFragment).setContainer(hour_treatment,false,null,0);
				newFragment.show(TimeTrackerApplication.getInstance().getCurrentActivity().getFragmentManager(), "timePicker");

			}break;
		}
	}
		public  boolean isValid(){

			date.setBackgroundResource(R.drawable.bg_border_bottom_medium_gray);
			hour.setBackgroundResource(R.drawable.bg_border_bottom_medium_gray);
			if(!date.getText().toString().equals("")&&!hour.getText().toString().equals("")) {

				return true;
			}
			else {
				date.setBackgroundResource(R.drawable.bg_border_bottom_medium_error);
				hour.setBackgroundResource(R.drawable.bg_border_bottom_medium_error);
				if(!date.getText().toString().equals(""))
					date.setBackgroundResource(R.drawable.bg_border_bottom_medium_gray);
				if(!hour.getText().toString().equals(""))
					hour.setBackgroundResource(R.drawable.bg_border_bottom_medium_gray);
				return false;
			}

		}

		public  void auto(int sug){
			if(sug==1)
			{
				if (!hour.getText().toString().equals("")) {

					String txt = hour.getText().toString();
					Date date = new Date();
					try {
						date = simpleDateFormat.parse(txt);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(until.getText().toString().equals("")&&hour_s.getText().toString().equals("") ){
						Date CurrentTime=new Date();
						try {
							CurrentTime = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
						}
						catch (ParseException e){

						}
						if(date.before(CurrentTime)){
							until.setText(simpleDateFormat.format(Calendar.getInstance().getTime()));
						}
					}
					if (!hour_s.getText().toString().equals("") || !until.getText().toString().equals("")) {
						String untilTxt = until.getText().toString();
						Date untilDate = new Date();
						try {
							untilDate = simpleDateFormat.parse(untilTxt);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						if (!until.getText().toString().equals(""))  {

							long difference = (untilDate.getTime() - date.getTime());

							int days = (int) (difference / (1000 * 60 * 60 * 24));
							double hours = (double) Math.abs(((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60)));
							double min = (double) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours));
							min /= 100000 * 60;

							if (hours == 0.0&&min==0)
								hour_s.setText("0.00");
							else {
								if(min!=0) {

									min = (min * 100) / 60;
								}
								hours = hours + min;

								hour_s.setText(Formatter.getInstance(getContext()).formatValueTowDig(hours));
							}
						}
						else {

							Calendar calendar = Calendar.getInstance();
							calendar.setTime(date);
							double h=Double.valueOf(hour_s.getText().toString());
							calendar.add(Calendar.HOUR,(int) h);
							//until.setText(simpleDateFormat.format(calendar.getTime()).toString());
						}
					}
				}
			}else {
				if(!until.getText().toString().equals("")) {
					if (!hour_s.getText().toString().equals("") || !hour.getText().toString().equals("")) {
						String untilTxt = until.getText().toString();
						String txt = hour.getText().toString();
						Date untilDate = new Date();
						Date date = new Date();
						try {
							untilDate = simpleDateFormat.parse(untilTxt);
							date = simpleDateFormat.parse(txt);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (hour.getText().toString().equals("")) {
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(untilDate);
								calendar.add(Calendar.HOUR, -(Integer.parseInt(hour_s.getText().toString())));
								hour.setText(simpleDateFormat.format(calendar.getTime()).toString());

						} else {

							double hours = (double) Math.abs(untilDate.getHours() - date.getHours());
							double min = (double)untilDate.getMinutes() - date.getMinutes();
							min /= 100;

							if (hours == 0.0&&min==0.0)
								hour_s.setText("0.00");
							else {
								if(min!=0) {

									min = (min * 100) / 60;
								}
								hours = hours + min;
								hour_s.setText(Formatter.getInstance(getContext()).formatValueTowDig(hours));

							}
						}
					}
				}
			}
		}

//	@Override
//	public void onFocusChange(View v, boolean hasFocus) {
//		if(hasFocus==false) {
//
//			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
//			switch (v.getId()){
//				case R.id.hour: {
//					if (!hour.getText().toString().equals("")) {
//						if (!hour_s.getText().toString().equals("") || !until.getText().toString().equals("")) {
//							if (until.getText().toString().equals("")) {
//
//							} else {
//								String untilTxt = until.getText().toString();
//								String txt = hour.getText().toString();
//								Date untilDate = new Date();
//								Date date = new Date();
//								try {
//									untilDate = simpleDateFormat.parse(untilTxt);
//									date = simpleDateFormat.parse(txt);
//								} catch (ParseException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//								long difference = (untilDate.getTime() - date.getTime());
//
//								int days = (int) (difference / (1000 * 60 * 60 * 24));
//								double hours = (double) Math.abs(((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60)));
//								double min = (double) Math.abs((difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)));
//								min /= 100000 * 60;
//								hours = hours + min;
//								if (hours == 0.0)
//									hour_s.setText("0.00");
//								else
//									hour_s.setText(Formatter.getInstance(getContext()).formatValueTowDig(hours));
//							}
//						}
//					}
//				}break;
//				case R.id.until:{
//						if(!until.getText().toString().equals(""))
//						{
//							if (!hour_s.getText().toString().equals("") || !hour.getText().toString().equals("")) {
//								if(hour.getText().toString().equals("") ) {
//
//								}
//								else {
//									String untilTxt = hour.getText().toString();
//									String txt = until.getText().toString();
//									Date untilDate = new Date();
//									Date date = new Date();
//									try {
//										untilDate = simpleDateFormat.parse(untilTxt);
//										date = simpleDateFormat.parse(txt);
//									} catch (ParseException e) {
//										// TODO Auto-generated catch block
//										e.printStackTrace();
//									}
//									//long difference = (untilDate.getTime() - date.getTime());
////									double hours = (double) Math.abs(difference / (60 * 1000) % 60);
////									double min = (double) Math.abs(difference / (60 * 60 * 1000) % 24);
//									double hours = (double) Math.abs(untilDate.getHours()-date.getHours());
//									double min = (double) Math.abs(untilDate.getMinutes()-date.getMinutes());
//									min/=100;
//									hours=hours+min;
//									if(hours==0.0)
//										hour_s.setText("0.00");
//									else
//										hour_s.setText(Formatter.getInstance(getContext()).formatValueTowDig(hours));
//								}
//							}
//						}
//				}break;
//
//			}
//
//
//		}
//
//	}

	@Override
	public void onDismiss(DialogInterface dialog) {}
}

