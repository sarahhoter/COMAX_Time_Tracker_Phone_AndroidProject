package com.binasystems.mtimereporter.dialog.Add_action;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.api.requests.ObligationPsishaLoadTask;
import com.binasystems.mtimereporter.api.requests.UniRequest;
import com.binasystems.mtimereporter.objects.Meeting;
import com.binasystems.mtimereporter.utils.Formatter;

/**
 * This is sales select menu fragment
 * @author binasystems
 *
 */
public class AddActionDialog extends Dialog implements OnItemClickListener,View.OnClickListener{

	private static final long Add_ACTIONS_DISPLAY = 4;
	View action_bar;
	TextView cancel;
	TextView location;
	TextView present;
	TextView description;
	TextView sum;
	TextView location_txt;
	TextView present_txt;
	TextView description_txt;
	TextView sum_txt;
	TextView customer;
	TextView project;
	TextView date;
	TextView hour;
	TextView during;
	TextView duringTxt;
	TextView until;
	TextView untilTxt;
	TextView type;
	TextView status;
	TextView date_treatment;
	TextView hour_treatment;
	TextView hour_treatment_txt;
	TextView sum_close;
	TextView present_close;
	TextView chance;
	TextView status_chance;
	TextView sum_close_txt;
	TextView present_close_txt;
	TextView chance_txt;
	TextView status_chance_txt;
	TextView projectTxt;
	TextView customer_txt;
	TextView date_treatment_txt;
	TextView date_txt;
	TextView hour_txt;
	TextView type_txt;
	TextView status_txt;
	Formatter formatter;
	LinearLayout email;
	Context context;
	Meeting meeting;
	public AddActionDialog(Context context, int theme, Meeting meeting) {
		super(context, theme);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(theme);
		this.context=context;
		this.meeting=meeting;
		formatter=Formatter.getInstance(getContext());
		action_bar=findViewById(R.id.action_bar);
		((TextView)findViewById(R.id.action_bar_textView_title)).setText(getContext().getString(R.string.action_details));
		cancel=(TextView)findViewById(R.id.cancel);
		location=(TextView)findViewById(R.id.location);
		present = (TextView)findViewById(R.id.present);
		description = (TextView)findViewById(R.id.description);
		sum = (TextView)findViewById(R.id.sum);
		email=(LinearLayout)findViewById(R.id.email);
		customer = (TextView)findViewById(R.id.customers);
		project = (TextView)findViewById(R.id.project);
		projectTxt = (TextView)findViewById(R.id.project_txt);
		date_treatment_txt = (TextView)findViewById(R.id.treatment_date_txt);
		status_txt = (TextView)findViewById(R.id.status_txt);
		date = (TextView)findViewById(R.id.date);
		hour = (TextView)findViewById(R.id.hour);
		during = (TextView)findViewById(R.id.hour_s);
		duringTxt = (TextView)findViewById(R.id.during);
		until = (TextView)findViewById(R.id.until);
		untilTxt = (TextView)findViewById(R.id.until_txt);
		hour_txt = (TextView)findViewById(R.id.hour_txt);
		type = (TextView)findViewById(R.id.type);
		status = (TextView)findViewById(R.id.status);
		date_treatment = (TextView)findViewById(R.id.treatment_date);
		hour_treatment = (TextView)findViewById(R.id.treatment_hour);
		hour_treatment_txt = (TextView)findViewById(R.id.treatment_hour_txt);
		sum_close = (TextView)findViewById(R.id.sumClose);
		present_close = (TextView)findViewById(R.id.presentClose);
		chance = (TextView)findViewById(R.id.chance);
		status_chance = (TextView)findViewById(R.id.status_chance);
		sum_close_txt = (TextView)findViewById(R.id.sumCloseTxt);
		present_close_txt = (TextView)findViewById(R.id.presentCloseTxt);
		chance_txt = (TextView)findViewById(R.id.chanceTxt);
		status_chance_txt = (TextView)findViewById(R.id.status_chanceTxt);
		location_txt=(TextView)findViewById(R.id.location_txt);
		present_txt=(TextView)findViewById(R.id.present_textView2);
		description_txt=(TextView)findViewById(R.id.description_textView3);
		sum_txt=(TextView)findViewById(R.id.sum_textView4);
		customer_txt=(TextView)findViewById(R.id.customer_txt);
		type_txt=(TextView)findViewById(R.id.type_txt);
		date_txt=(TextView)findViewById(R.id.date_txt);
		hour_treatment_txt.setVisibility(View.INVISIBLE);
		cancel.setOnClickListener(this);
		project.setVisibility(View.GONE);
		until.setVisibility(View.INVISIBLE);
		during.setVisibility(View.GONE);
		hour_treatment.setVisibility(View.GONE);
		present_close.setVisibility(View.GONE);
		untilTxt.setVisibility(View.INVISIBLE);
		sum_close.setVisibility(View.GONE);
		chance.setVisibility(View.GONE);
		status_chance.setVisibility(View.GONE);
		projectTxt.setVisibility(View.GONE);
		duringTxt.setVisibility(View.GONE);
		sum_close_txt.setVisibility(View.GONE);
		present_close_txt.setVisibility(View.GONE);
		chance_txt.setVisibility(View.GONE);
		status_chance_txt.setVisibility(View.GONE);
		if(meeting.getWhere().toString().equals("")) {
			location_txt.setVisibility(View.GONE);
			location.setVisibility(View.GONE);
		}
		else {
			location_txt.setVisibility(View.VISIBLE);
			location.setVisibility(View.VISIBLE);
			location.setText(meeting.getWhere());
		}
		if(meeting.getWho().toString().equals("")) {
			present_txt.setVisibility(View.GONE);
			present.setVisibility(View.GONE);
		}
		else {
			present_txt.setVisibility(View.VISIBLE);
			present.setVisibility(View.VISIBLE);
			present.setText(meeting.getWho());
		}
		if(meeting.getRemark().toString().equals("")) {
			description_txt.setVisibility(View.GONE);
			description.setVisibility(View.GONE);
		}
		else {
			description_txt.setVisibility(View.VISIBLE);
			description.setVisibility(View.VISIBLE);
			description.setText(meeting.getRemark());
		}
		if(meeting.getSummary().toString().equals("")) {
			sum_txt.setVisibility(View.GONE);
			sum.setVisibility(View.GONE);
		}
		else {
			sum_txt.setVisibility(View.VISIBLE);
			sum.setVisibility(View.VISIBLE);
			sum.setText(meeting.getSummary());
		}
		if(meeting.getCustomer().toString().equals("")) {
			customer_txt.setVisibility(View.GONE);
			customer.setVisibility(View.GONE);
		}
		else {
			customer_txt.setVisibility(View.VISIBLE);
			customer.setVisibility(View.VISIBLE);
			customer.setText(meeting.getCustomer());
		}
		if(meeting.getDate().toString().equals("")) {
			date_txt.setVisibility(View.GONE);
			date.setVisibility(View.GONE);
		}
		else {
			date_txt.setVisibility(View.VISIBLE);
			date.setVisibility(View.VISIBLE);
			date.setText(meeting.getDate());
		}
		if(meeting.getHour().toString().equals("")) {
			hour_txt.setVisibility(View.GONE);
			hour_treatment_txt.setVisibility(View.GONE);
			hour.setVisibility(View.GONE);
			hour_treatment.setVisibility(View.GONE);
			//hour_treatment.setVisibility(View.GONE);
		}
		else {
			hour_txt.setVisibility(View.VISIBLE);
			hour_treatment_txt.setVisibility(View.INVISIBLE);
			hour.setVisibility(View.VISIBLE);
			hour.setText(meeting.getHour());
			hour_treatment.setVisibility(View.INVISIBLE);
		}
		if(meeting.getType().toString().equals("")) {
			type_txt.setVisibility(View.GONE);
			type.setVisibility(View.GONE);
		}
		else {
			type_txt.setVisibility(View.VISIBLE);
			type.setVisibility(View.VISIBLE);
			type.setText(meeting.getType());
		}
		if(!meeting.getState_id().toString().equals("0")) {
			status_txt.setVisibility(View.VISIBLE);
			status.setVisibility(View.VISIBLE);
			status.setText("");
			status.setText(context.getResources().getStringArray(R.array.statusArray)[Integer.parseInt(meeting.getState_id()) - 1]);
		}
		else {
			status_txt.setVisibility(View.GONE);
			status.setVisibility(View.GONE);
			status.setText("");
		}

		if(!meeting.getFollow_up_at().equals("null")) {
			date_treatment_txt.setVisibility(View.VISIBLE);
			date_treatment.setVisibility(View.VISIBLE);
			date_treatment.setText(meeting.getFollow_up_at());
		}
		else {
			date_treatment_txt.setVisibility(View.GONE);
			date_treatment.setVisibility(View.GONE);
			date_treatment.setText("");
		}
		if(TimeTrackerApplication.getInstance().getDoc()==Add_ACTIONS_DISPLAY)
			email.setVisibility(View.GONE);
//		if(TimeTrackerApplication.getInstance().getChance()!=null)
//		chance.setText(TimeTrackerApplication.getInstance().getChance().getName());
//		else chance.setText("");
//		if(TimeTrackerApplication.getInstance().getStatusChance()!=null)
//			status_chance.setText(TimeTrackerApplication.getInstance().getStatusChance());
//		else status_chance.setText("");
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

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.cancel: {
				dismiss();
			}break;

		}
	}

}

