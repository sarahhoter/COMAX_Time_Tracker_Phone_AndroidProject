package com.binasystems.mtimereporter.dialog.Add_action;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.activity.MainMenuActivity;
import com.binasystems.mtimereporter.api.requests.ObligationPsishaLoadTask;
import com.binasystems.mtimereporter.api.requests.UniRequest;
import com.binasystems.mtimereporter.dialog.CustomDatePickerDialog;
import com.binasystems.mtimereporter.utils.Formatter;

/**
 * This is sales select menu fragment
 * @author binasystems
 *
 */
public class AddActionObligationDialog extends Dialog implements OnItemClickListener,View.OnClickListener{

	View action_bar;
	TextView cancel;
	TextView location;
	TextView present;
	TextView description;
	TextView sum;
	TextView customer;
	TextView project;
	TextView date;
	TextView hour;
	TextView during;
	TextView until;
	TextView type;
	CheckBox send;
	TextView status;
	TextView date_treatment;
	TextView hour_treatment;
	TextView sum_close;
	TextView present_close;
	TextView chance;
	TextView status_chance;
	TextView location_txt;
	TextView present_txt;
	TextView description_txt;
	TextView sum_txt;
	TextView customer_txt;
	TextView project_txt;
	TextView date_txt;
	TextView hour_txt;
	TextView during_txt;
	TextView until_txt;
	TextView type_txt;
	TextView status_txt;
	TextView date_treatment_txt;
	TextView hour_treatment_txt;
	TextView sum_close_txt;
	TextView present_close_txt;
	TextView chance_txt;
	TextView status_chance_txt;
	Formatter formatter;
	TextView next;
	Context context;

	public AddActionObligationDialog(Context context, int theme) {
		super(context, theme);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(theme);
		this.context=context;
		formatter=Formatter.getInstance(getContext());
		action_bar=findViewById(R.id.action_bar);
		((TextView)findViewById(R.id.action_bar_textView_title)).setText(R.string.add_action);
		cancel=(TextView)findViewById(R.id.cancel);
		location=(TextView)findViewById(R.id.location);
		next=(TextView) findViewById(R.id.actionBarNext);
		send=(CheckBox) findViewById(R.id.send);
		next.setVisibility(View.VISIBLE);
		next.setText(this.getContext().getResources().getString(R.string.obligation));
		present = (TextView)findViewById(R.id.present);
		description = (TextView)findViewById(R.id.description);
		sum = (TextView)findViewById(R.id.sum);
		customer = (TextView)findViewById(R.id.customers);
		project = (TextView)findViewById(R.id.project);
		date = (TextView)findViewById(R.id.date);
		hour = (TextView)findViewById(R.id.hour);
		during = (TextView)findViewById(R.id.hour_s);
		until = (TextView)findViewById(R.id.until);
		type = (TextView)findViewById(R.id.type);
		status = (TextView)findViewById(R.id.status);
		date_treatment = (TextView)findViewById(R.id.treatment_date);
		hour_treatment = (TextView)findViewById(R.id.treatment_hour);
		sum_close = (TextView)findViewById(R.id.sumClose);
		present_close = (TextView)findViewById(R.id.presentClose);
		chance = (TextView)findViewById(R.id.chance);
		status_chance = (TextView)findViewById(R.id.status_chance);
		location_txt=(TextView)findViewById(R.id.location_txt);
		present_txt=(TextView)findViewById(R.id.present_textView2);
		description_txt=(TextView)findViewById(R.id.description_textView3);
		sum_txt=(TextView)findViewById(R.id.sum_textView4);
		customer_txt = (TextView)findViewById(R.id.customer_txt);
		project_txt = (TextView)findViewById(R.id.project_txt);
		date_txt = (TextView)findViewById(R.id.date_txt);
		hour_txt = (TextView)findViewById(R.id.hour_txt);
		during_txt = (TextView)findViewById(R.id.during);
		until_txt = (TextView)findViewById(R.id.until_txt);
		type_txt = (TextView)findViewById(R.id.type_txt);
		status_txt = (TextView)findViewById(R.id.status_txt);
		date_treatment_txt = (TextView)findViewById(R.id.treatment_date_txt);
		hour_treatment_txt = (TextView)findViewById(R.id.treatment_hour_txt);
		sum_close_txt = (TextView)findViewById(R.id.sumCloseTxt);
		present_close_txt = (TextView)findViewById(R.id.presentCloseTxt);
		chance_txt = (TextView)findViewById(R.id.chanceTxt);
		status_chance_txt = (TextView)findViewById(R.id.status_chanceTxt);
		next.setOnClickListener(this);
		cancel.setOnClickListener(this);

		if(TimeTrackerApplication.getInstance().getLocation()!=null&&!TimeTrackerApplication.getInstance().getLocation().toString().equals("")) {
			location.setVisibility(View.VISIBLE);
			location_txt.setVisibility(View.VISIBLE);
			location.setText(TimeTrackerApplication.getInstance().getLocation());
			((LinearLayout)findViewById(R.id.locationView)).setVisibility(View.VISIBLE);
		}else {
			location.setVisibility(View.GONE);
			location_txt.setVisibility(View.GONE);
			((LinearLayout)findViewById(R.id.locationView)).setVisibility(View.GONE);
		}
		if(TimeTrackerApplication.getInstance().getPresent()!=null&&!TimeTrackerApplication.getInstance().getPresent().toString().equals("")) {
			present.setVisibility(View.VISIBLE);
			present_txt.setVisibility(View.VISIBLE);
			present.setText(TimeTrackerApplication.getInstance().getPresent());
			((LinearLayout)findViewById(R.id.presentView)).setVisibility(View.VISIBLE);
		}else {
			present.setVisibility(View.GONE);
			present_txt.setVisibility(View.GONE);
			((LinearLayout)findViewById(R.id.presentView)).setVisibility(View.GONE);
		}
		if(TimeTrackerApplication.getInstance().getDescription()!=null&&!TimeTrackerApplication.getInstance().getDescription().toString().equals("")) {
			description.setVisibility(View.VISIBLE);
			description_txt.setVisibility(View.VISIBLE);
			description.setText(TimeTrackerApplication.getInstance().getDescription());
			((LinearLayout)findViewById(R.id.descriptionView)).setVisibility(View.VISIBLE);
		}else {
			description.setVisibility(View.GONE);
			description_txt.setVisibility(View.GONE);
			((LinearLayout)findViewById(R.id.descriptionView)).setVisibility(View.GONE);
		}
		if(TimeTrackerApplication.getInstance().getSum()!=null&&!TimeTrackerApplication.getInstance().getSum().toString().equals("")) {
			sum.setVisibility(View.VISIBLE);
			sum_txt.setVisibility(View.VISIBLE);
			sum.setText(TimeTrackerApplication.getInstance().getSum());
			//((LinearLayout)findViewById(R.id.sumView)).setVisibility(View.VISIBLE);
		}else {
			sum.setVisibility(View.GONE);
			sum_txt.setVisibility(View.GONE);
			//((LinearLayout)findViewById(R.id.sumView)).setVisibility(View.GONE);
		}
		if(UniRequest.customer.getName()!=null&&!UniRequest.customer.getName().toString().equals("")) {
			customer.setVisibility(View.VISIBLE);
			customer_txt.setVisibility(View.VISIBLE);
			customer.setText(UniRequest.customer.getName());
		}else {
			customer.setVisibility(View.GONE);
			customer_txt.setVisibility(View.GONE);
		}
		if(UniRequest.project==null) {
			project.setVisibility(View.GONE);
			project_txt.setVisibility(View.GONE);
		}
		else {
			project.setText(UniRequest.project.getName());
			project.setVisibility(View.VISIBLE);
			project_txt.setVisibility(View.VISIBLE);
		}
		if(TimeTrackerApplication.getInstance().getDate()!=null&&!TimeTrackerApplication.getInstance().getDate().toString().equals("")) {
			date.setVisibility(View.VISIBLE);
			date_treatment.setVisibility(View.VISIBLE);
			date.setText(TimeTrackerApplication.getInstance().getDate());
		}else {
			date.setVisibility(View.GONE);
			date_txt.setVisibility(View.GONE);
		}
		if(TimeTrackerApplication.getInstance().getHour()!=null&&!TimeTrackerApplication.getInstance().getHour().toString().equals("")) {
			hour.setVisibility(View.VISIBLE);
			hour_txt.setVisibility(View.VISIBLE);
			hour.setText(TimeTrackerApplication.getInstance().getHour());
		}else {
			hour.setVisibility(View.GONE);
			hour_txt.setVisibility(View.GONE);
		}
		if(!TimeTrackerApplication.getInstance().getDuring().equals("")&&!TimeTrackerApplication.getInstance().getDuring().equals("0.0")) {
			during.setVisibility(View.VISIBLE);
			during_txt.setVisibility(View.VISIBLE);
			during.setText(formatter.formatValueTowDig(Double.parseDouble(TimeTrackerApplication.getInstance().getDuring())));
		}
		else {
			during.setVisibility(View.GONE);
			during_txt.setVisibility(View.GONE);

		}
		if(TimeTrackerApplication.getInstance().getUntil_hour()!=null&&!TimeTrackerApplication.getInstance().getUntil_hour().toString().equals("")) {
			until.setVisibility(View.VISIBLE);
			until_txt.setVisibility(View.VISIBLE);
			until.setText(TimeTrackerApplication.getInstance().getUntil_hour());
		}else {
			until.setVisibility(View.INVISIBLE);
			until_txt.setVisibility(View.INVISIBLE);
		}
		if(TimeTrackerApplication.getInstance().getType()!=null&&!TimeTrackerApplication.getInstance().getType().toString().equals("")) {
			type.setVisibility(View.VISIBLE);
			type_txt.setVisibility(View.VISIBLE);
			type.setText(TimeTrackerApplication.getInstance().getType());
		}else {
			type.setVisibility(View.GONE);
			type_txt.setVisibility(View.GONE);
		}
		if(TimeTrackerApplication.getInstance().getStatus()!=null&&!TimeTrackerApplication.getInstance().getStatus().toString().equals("")) {
			status.setVisibility(View.VISIBLE);
			status_txt.setVisibility(View.VISIBLE);
			status.setText(TimeTrackerApplication.getInstance().getStatus());
		}else {
			status.setVisibility(View.GONE);
			status_txt.setVisibility(View.GONE);
		}
		if(TimeTrackerApplication.getInstance().getTreatmentDate()!=null&&!TimeTrackerApplication.getInstance().getTreatmentDate().toString().equals("")) {
			date_treatment.setVisibility(View.VISIBLE);
			date_treatment_txt.setVisibility(View.VISIBLE);
			date_treatment.setText(TimeTrackerApplication.getInstance().getTreatmentDate());
		}else {
			date_treatment.setVisibility(View.GONE);
			date_treatment_txt.setVisibility(View.GONE);
		}
		if(TimeTrackerApplication.getInstance().getTreatmentHour()!=null&&!TimeTrackerApplication.getInstance().getTreatmentHour().toString().equals("")) {
			hour_treatment.setVisibility(View.VISIBLE);
			hour_treatment_txt.setVisibility(View.VISIBLE);
			hour_treatment.setText(TimeTrackerApplication.getInstance().getTreatmentHour());
			((LinearLayout)findViewById(R.id.treatment)).setVisibility(View.VISIBLE);
		}else {
			hour_treatment.setVisibility(View.GONE);
			hour_treatment_txt.setVisibility(View.GONE);
			((LinearLayout)findViewById(R.id.treatment)).setVisibility(View.GONE);
		}
		if(TimeTrackerApplication.getInstance().getSumClose()!=null&&!TimeTrackerApplication.getInstance().getSumClose().toString().equals("")) {
			sum_close.setVisibility(View.VISIBLE);
			sum_close_txt.setVisibility(View.VISIBLE);
			sum_close.setText(TimeTrackerApplication.getInstance().getSumClose());
		}else {
			sum_close.setVisibility(View.GONE);
			sum_close_txt.setVisibility(View.GONE);
			((LinearLayout)findViewById(R.id.close)).setVisibility(View.GONE);
		}
		if(TimeTrackerApplication.getInstance().getPresentClose()!=null&&!TimeTrackerApplication.getInstance().getPresentClose().toString().equals("")) {
			present_close.setVisibility(View.VISIBLE);
			present_close_txt.setVisibility(View.VISIBLE);
			present_close.setText(TimeTrackerApplication.getInstance().getPresentClose());
		}else {
			present_close.setVisibility(View.GONE);
			present_close_txt.setVisibility(View.GONE);
			((LinearLayout)findViewById(R.id.close)).setVisibility(View.GONE);
		}
		if(TimeTrackerApplication.getInstance().getChance()!=null) {
			chance.setVisibility(View.VISIBLE);
			chance_txt.setVisibility(View.VISIBLE);
			chance.setText(TimeTrackerApplication.getInstance().getChance().getName());
		}else {
			chance.setVisibility(View.GONE);
			chance_txt.setVisibility(View.GONE);
		}
		if(TimeTrackerApplication.getInstance().getStatusChance()!=null&&!TimeTrackerApplication.getInstance().getStatusChance().toString().equals("")) {
			status_chance.setVisibility(View.VISIBLE);
			status_chance_txt.setVisibility(View.VISIBLE);
			status_chance.setText(TimeTrackerApplication.getInstance().getStatusChance());
		}else {
			status_chance_txt.setVisibility(View.GONE);
			status_chance.setVisibility(View.GONE);
		}
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
			case R.id.cancel: {dismiss();}break;

			case R.id.actionBarNext: {
				TimeTrackerApplication.getInstance().setSendEnail(String.valueOf(send.isChecked()));
				ObligationPsishaLoadTask obligationPsishaLoadTask=new ObligationPsishaLoadTask(context,getOwnerActivity(),this);
				obligationPsishaLoadTask.execute();
			}break;
		}
	}

}

