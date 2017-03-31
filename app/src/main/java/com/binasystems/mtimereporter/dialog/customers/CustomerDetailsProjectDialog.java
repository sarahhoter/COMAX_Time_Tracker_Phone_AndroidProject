package com.binasystems.mtimereporter.dialog.customers;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.api.requests.AgingDebtsLoadTask;
import com.binasystems.mtimereporter.api.requests.LoadCustomerTask;
import com.binasystems.mtimereporter.api.requests.UniRequest;
import com.binasystems.mtimereporter.customview.PrevNextNavigationView;
import com.binasystems.mtimereporter.dialog.Add_action.AddActionDateDialog;
import com.binasystems.mtimereporter.dialog.ViewGraphDialog;
import com.binasystems.mtimereporter.objects.Customer;
import com.binasystems.mtimereporter.utils.ComaxBarGraphUtils;
import com.binasystems.mtimereporter.utils.Formatter;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This is sales select menu fragment
 * @author binasystems
 *
 */
public class CustomerDetailsProjectDialog extends Dialog implements View.OnClickListener,DialogInterface.OnShowListener{


	View action_bar;
	TextView cancel;
	TableLayout table;
	LinearLayout graphContainerDoc;
	PrevNextNavigationView navigationByCustomerView;
	//TextView name;
	//TextView nameTxt;
	TextView tel;
	TextView telTxt;
	TextView telCall;
	TextView pelCall;
	TextView pel;
	TextView pelTxt;
	TextView email;

	TextView emailTxt;
	TextView grp;
	TextView grpTxt;

	String title;
	Dialog dialog;
	TextView by_date_title;
	TextView adress;
	TextView adressTxt;
	LinearLayout gioolHovot;
	LinearLayout comboGraph;
	Spinner by;
	GraphicalView gr1;
	List<Customer> mStoreInfoList = new ArrayList<Customer>();
	int mStorePosition = 0;
	Button new_invoice;
	String by_date;
	private ViewGraphDialog viewGraphDialog;
	private AgingDebtsLoadTask agingDebts;
	ArrayAdapter<CharSequence> sort_adapter = new ArrayAdapter<CharSequence>(
			getContext(), R.layout.spinner_list_item, getContext().getResources().getStringArray(R.array.date_by));

	public CustomerDetailsProjectDialog(Context context, int theme) {
		super(context, theme);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(theme);
		action_bar=findViewById(R.id.action_bar);
		((TextView)action_bar.findViewById(R.id.action_bar_textView_title)).setText(R.string.title_activity_customers);
		cancel=(TextView)action_bar.findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
		dialog=this;
		table = (TableLayout) findViewById(R.id.table);
		graphContainerDoc = (LinearLayout) findViewById(R.id.graphDateDoc);
		//name = (TextView)findViewById(R.id.nameVal);
		by_date_title=(TextView)findViewById(R.id.by_date_title);
		new_invoice=(Button)findViewById(R.id.new_invoice);
		//nameTxt = (TextView)findViewById(R.id.name);
		tel = (TextView)findViewById(R.id.telVal);
		telTxt = (TextView)findViewById(R.id.telphone);
		pel = (TextView)findViewById(R.id.pelVal);
		pelTxt = (TextView)findViewById(R.id.phone);
		email = (TextView)findViewById(R.id.emailVal);
		emailTxt = (TextView)findViewById(R.id.email);
		grp = (TextView)findViewById(R.id.grpVal);
		grpTxt = (TextView)findViewById(R.id.group);
		gioolHovot=(LinearLayout)findViewById(R.id.gioolHovot);
		comboGraph=(LinearLayout)findViewById(R.id.comboGraph);
		adress = (TextView)findViewById(R.id.adressVal);
		adressTxt = (TextView)findViewById(R.id.address);
		telCall =(TextView)findViewById(R.id.telphoneCall);
		pelCall =(TextView)findViewById(R.id.pelphoneCall);
		title=getContext().getResources().getString(R.string.by_document_date);
		by= (Spinner)findViewById(R.id.by);
		new_invoice.setOnClickListener(this);
		tel.setOnClickListener(this);
		pel.setOnClickListener(this);
		telCall.setOnClickListener(this);
		pelCall.setOnClickListener(this);
		navigationByCustomerView = new PrevNextNavigationView(findViewById(R.id.navigationByCustomerView));
		navigationByCustomerView.setTitleStyle(R.color.gray,17);
		this.setOnShowListener(this);
		navigationByCustomerView.setListener(new PrevNextNavigationView.OnPrevNextNavigateViewListener() {
			@Override
			public void onNextButtonClick(PrevNextNavigationView view) {
				LoadCustomerTask customer_load_task = new LoadCustomerTask(getContext(),dialog);
				customer_load_task.execute("next_item",UniRequest.customer.getCode(),UniRequest.customer.getKod().toString());
				try { customer_load_task.get();
				}catch (Exception e){}
				setup();
			//	updateNavigationByStoreTitle();
				setupGraph();
			}

			@Override
			public void onPrevButtonClick(PrevNextNavigationView view) {
				LoadCustomerTask customer_load_task = new LoadCustomerTask(getContext(),dialog);
				customer_load_task.execute("prev_item",UniRequest.customer.getCode(),UniRequest.customer.getKod().toString());
				try { customer_load_task.get();
				}catch (Exception e){}
				setup();
				//	updateNavigationByStoreTitle();
				setupGraph();

			}

			@Override
			public void onButtonClick(PrevNextNavigationView view) {
				dismiss();
			}
		});
		by.setAdapter(sort_adapter);
		by_date = String.valueOf(by.getSelectedItemPosition()+1);
		by.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
									   int arg2, long arg3) {

				by_date = String.valueOf(by.getSelectedItemPosition()+1);

				if(by_date.equals("1")) {
					by_date_title.setText(R.string.by_document_date);
					title=getContext().getResources().getString(R.string.by_document_date);
				}
				else {
					by_date_title.setText(R.string.by_value_date);
					title=getContext().getResources().getString(R.string.by_value_date);
				}
				setupGraph();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
	}
//	public void setdata(ArrayList<Customer> items,int mStorePosition){
//		mStoreInfoList=items;
//		this.mStorePosition=mStorePosition;
//
//	}
//	private void updateNavigationByStoreTitle() {
//
//		if (mStoreInfoList != null && mStoreInfoList.size() > 0) {
//			// update store position
//			if (mStorePosition >= mStoreInfoList.size()) {
//				mStorePosition = 0;
//			}
//			// update state buttons
//			boolean prevButtonVisible = true;
//			boolean nextButtonVisible = true;
//			if (mStorePosition == 0) {
//				prevButtonVisible = false;
//			}
//			if (mStorePosition == mStoreInfoList.size() - 1) {
//				nextButtonVisible = false;
//			}
//			mNavigationByStoreView.setPrevNextButtonsVisible(prevButtonVisible, nextButtonVisible);
//			// update title
//			String title = mStoreInfoList.get(mStorePosition).getName();
//			mNavigationByStoreView.setTitle(Formatter.getInstance(getContext()).formatValue(title));
//		} else mNavigationByStoreView.setTitle("");
//
//	}
	public void onShow(DialogInterface dialogInterface) {
		//updateNavigationByStoreTitle();
		setupGraph();
	}
	private void setupGraph(){
			agingDebts = new AgingDebtsLoadTask(
					getContext(), this);
			agingDebts.execute(by_date);

	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setup();

	}
	private void setup()
	{
		if(UniRequest.customer.getName()!=null&&!UniRequest.customer.getName().equals(""))// {
			navigationByCustomerView.setTitle(UniRequest.customer.getName());
		//	nameTxt.setVisibility(View.VISIBLE);
			//name.setText(UniRequest.customer.getName());
	//	}else{
			//name.setVisibility(View.INVISIBLE);
			//nameTxt.setVisibility(View.GONE);
	//	}
		if(UniRequest.customer.getTel()!=null&&!UniRequest.customer.getTel().equals("")) {
			tel.setVisibility(View.VISIBLE);
			telTxt.setVisibility(View.VISIBLE);
			tel.setText(UniRequest.customer.getTel());
			telCall.setVisibility(View.VISIBLE);
		}else{
			tel.setVisibility(View.GONE);
			telCall.setVisibility(View.GONE);
			telTxt.setVisibility(View.GONE);
		}
		if(UniRequest.customer.getPelefon()!=null&&!UniRequest.customer.getPelefon().equals("")) {
			pel.setVisibility(View.VISIBLE);
			pelTxt.setVisibility(View.VISIBLE);
			pel.setText(UniRequest.customer.getPelefon());
			pelCall.setVisibility(View.VISIBLE);
		}else{
			pel.setVisibility(View.GONE);
			pelCall.setVisibility(View.GONE);
			pelTxt.setVisibility(View.GONE);
		}
		if(UniRequest.customer.getEmail()!=null&&!UniRequest.customer.getEmail().equals("")) {
			email.setVisibility(View.VISIBLE);
			emailTxt.setVisibility(View.VISIBLE);
			email.setText(UniRequest.customer.getEmail());
		}else {
			email.setVisibility(View.GONE);
			emailTxt.setVisibility(View.GONE);
		}
		if(UniRequest.customer.getGroupNm()!=null&&!UniRequest.customer.getGroupNm().equals("")) {
			grp.setVisibility(View.VISIBLE);
			grpTxt.setVisibility(View.VISIBLE);
			grp.setText(UniRequest.customer.getGroupNm());
		}else {
			grp.setVisibility(View.GONE);
			grpTxt.setVisibility(View.GONE);
		}
		if(UniRequest.customer.getAddress()!=null&&!UniRequest.customer.getAddress().equals("")) {
			adress.setVisibility(View.VISIBLE);
			adressTxt.setVisibility(View.VISIBLE);
			adress.setText(UniRequest.customer.getAddress());
			if(UniRequest.customer.getCity()!=null&&!UniRequest.customer.getCity().equals("")) {
				if(adress.getText()!=null&&!adress.getText().toString().equals(""))
					adress.setText(adress.getText()+" "+UniRequest.customer.getCity());
				else adress.setText(UniRequest.customer.getCity());
			}else
				adress.setVisibility(View.GONE);
		}
		else {
			if(UniRequest.customer.getCity()!=null&&!UniRequest.customer.getCity().equals("")) {
				adress.setText(UniRequest.customer.getCity());
			}else
				adress.setVisibility(View.GONE);

			adressTxt.setVisibility(View.GONE);
		}



		graphContainerDoc.setOnClickListener(this);
//		graphContainerValue.setOnClickListener(this);
	}
	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		
		if(item.getItemId() == android.R.id.home){
			dismiss();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	public void update(Map<String, Double> table, int sug,Double balanceVal,Double sumVal,String payment_day,String payment_type) {

		if(sug==0&&table==null&&balanceVal==null&&sumVal==null&&payment_day==null&&payment_type==null){
			gioolHovot.setVisibility(View.GONE);
		}else {
			this.table.removeAllViews();

			LayoutInflater layoutInflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			TableRow table_row = (TableRow) layoutInflater.inflate(
					R.layout.aging_debts_table_row, null, false);

			TextView month = (TextView) table_row.findViewById(R.id.month);
			TextView money = (TextView) table_row.findViewById(R.id.money);


			Iterator<Map.Entry<String, Double>> iterator = table.entrySet().iterator();

			double[] graph_dataset = new double[table.entrySet().size() - 1];

			int[] colors = new int[]{Color.RED};

			XYMultipleSeriesRenderer renderer = ComaxBarGraphUtils
					.buildBarRenderer(colors, Color.parseColor("#ffffff"));

			int i = 0;
			while (iterator.hasNext()) {

				Map.Entry<String, Double> entry = (Map.Entry<String, Double>) iterator
						.next();

				table_row = (TableRow) layoutInflater.inflate(
						R.layout.aging_debts_table_row, null, false);

				month = (TextView) table_row.findViewById(R.id.month);
				money = (TextView) table_row.findViewById(R.id.money);
				LinearLayout px_divider = new LinearLayout(getContext());
				px_divider.setLayoutParams(new ViewGroup.LayoutParams(
						android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 1));
				px_divider.setBackgroundColor(Color.BLACK);
				month.setText(entry.getKey());

				if (entry.getValue() < 0.0)
					money.setTextColor(Color.RED);
				else
					money.setTextColor(Color.BLACK);

				if (entry.getValue() != 0.0)
					money.setText(Formatter.getInstance(getContext()).formatValue(entry.getValue()));

				this.table.addView(px_divider);
				this.table.addView(table_row);

				if (iterator.hasNext()) {
					graph_dataset[i++] = entry.getValue();
					renderer.addXTextLabel(i, entry.getKey());
				}

			}

			table_row = (TableRow) layoutInflater.inflate(
					R.layout.aging_debts_table_row_sum, null, false);
			LinearLayout px_divider = new LinearLayout(getContext());
			px_divider.setLayoutParams(new ViewGroup.LayoutParams(
					android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 1));
			px_divider.setBackgroundColor(Color.BLACK);
			month = (TextView) table_row.findViewById(R.id.month);
			money = (TextView) table_row.findViewById(R.id.money);
			month.setText(R.string.all_sum);
			if (sumVal < 0)
				money.setTextColor(Color.RED);
			else money.setTextColor(getContext().getResources().getColor(R.color.gray));
			money.setText(Formatter.getInstance(getContext()).formatValueNum(sumVal));

			this.table.addView(px_divider);
			this.table.addView(table_row);
			table_row = (TableRow) layoutInflater.inflate(
					R.layout.aging_debts_table_row_sum, null, false);
			 px_divider = new LinearLayout(getContext());
			px_divider.setLayoutParams(new ViewGroup.LayoutParams(
					android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 1));
			px_divider.setBackgroundColor(Color.BLACK);
			month = (TextView) table_row.findViewById(R.id.month);
			money = (TextView) table_row.findViewById(R.id.money);
			month.setText(R.string.balance);
			if (balanceVal < 0)
				money.setTextColor(Color.RED);
			else money.setTextColor(getContext().getResources().getColor(R.color.gray));
			money.setText(Formatter.getInstance(getContext()).formatValueNum(balanceVal));

			this.table.addView(px_divider);
			this.table.addView(table_row);
			table_row = (TableRow) layoutInflater.inflate(
					R.layout.aging_debts_table_row_sum, null, false);

			month = (TextView) table_row.findViewById(R.id.month);
			money = (TextView) table_row.findViewById(R.id.money);
			month.setText(R.string.condition_payment);
			if(payment_day.toString().equals("null")) {
				if (payment_type .toString().equals("null"))
					money.setText("");
				else money.setText(payment_type);
			}
			else {
				if (payment_type.toString().equals("null"))
					money.setText(payment_day);
				else money.setText(payment_day + payment_type);
			}
			 px_divider = new LinearLayout(getContext());
			px_divider.setLayoutParams(new ViewGroup.LayoutParams(
					android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 1));
			px_divider.setBackgroundColor(Color.BLACK);
			this.table.addView(px_divider);
			this.table.addView(table_row);

		/*
		 * Redraw graphic
		 */
			//graphContainer.removeAllViews();

			List<double[]> values = new ArrayList<double[]>();

			values.add(graph_dataset);

			renderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
			renderer.setZoomEnabled(false, false);
			renderer.setPanEnabled(false, false);

			double min = 0.0;
			double max = 0.0;

			for (i = 0; i < graph_dataset.length; i++) {

				if (graph_dataset[i] < min)
					min = graph_dataset[i];

				if (graph_dataset[i] > max)
					max = graph_dataset[i];

			}

			double mm = ((-min) > max) ? (-min) : max;

			ComaxBarGraphUtils.setChartSettings(renderer, "", getContext()
							.getString(R.string.months),
					getContext().getString(R.string.amount), 0.5,
					graph_dataset.length + 0.5, min - (mm / 5), max + (mm / 5),
					Color.BLACK, Color.BLACK);
			renderer.setXLabels(0);
			renderer.setYLabels(3);
			int length = renderer.getSeriesRendererCount();
			for (i = 0; i < length; i++) {
				XYSeriesRenderer seriesRenderer = (XYSeriesRenderer) renderer
						.getSeriesRendererAt(i);
				seriesRenderer.setDisplayChartValues(true);
				seriesRenderer.setChartValuesTextAlign(Paint.Align.LEFT);
			}

			renderer.setGridColor(Color.parseColor("#AA000000"));
			renderer.setShowGridY(true);
			renderer.setShowGridX(true);
			renderer.setXLabelsAngle(45);
			renderer.setXLabelsAlign(Paint.Align.RIGHT);

			GraphicalView grfv = ChartFactory.getBarChartView(getContext(),
					ComaxBarGraphUtils.buildBarDataset(new String[]{getContext().getString(R.string.debts)},
							values), renderer, BarChart.Type.DEFAULT);
			by_date_title.setVisibility(View.VISIBLE);
			comboGraph.setVisibility(View.VISIBLE);
			//if (sug == 1) {
				gr1 = grfv;
				graphContainerDoc.removeAllViews();
				graphContainerDoc.addView(gr1);
		//	}
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.cancel:dismiss();break;

			case R.id.next:{
				UniRequest.project=null;
				AddActionDateDialog addActionDateDialog=new AddActionDateDialog(getContext(),R.layout.dialog_add_action_select_date);
				addActionDateDialog.show();
			}break;

			case R.id.graphDateDoc:{
				viewGraphDialog = new ViewGraphDialog(getContext(), R.layout.dialog_image_view, gr1, UniRequest.customer,title);
				viewGraphDialog.show();
				viewGraphDialog.setOnDismissListener(new OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						((ViewGroup) gr1.getParent()).removeView(gr1);
						graphContainerDoc.addView(gr1);
					}
				});

			}break;

			case R.id.new_invoice:{
				//UniRequest.customer=mStoreInfoList.get(mStorePosition);
				//CustomerNewInvoices customerNewInvoices=new CustomerNewInvoices(this,getContext(),R.layout.dialog_customers_new_invoice);
				//customerNewInvoices.show();
			}break;

			case R.id.telVal:{
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:"+tel.getText().toString()));
				callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getContext().startActivity(callIntent);

			}break;

			case R.id.pelVal:{
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:"+pel.getText().toString()));
				callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getContext().startActivity(callIntent);

			}break;

			case R.id.pelphoneCall:{
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:"+pel.getText().toString()));
				callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getContext().startActivity(callIntent);
			}break;

			case R.id.telphoneCall:{
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:"+tel.getText().toString()));
				callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getContext().startActivity(callIntent);
			}break;
		}
	}




}

