package com.binasystems.mtimereporter.fragment.sales;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.activity.BaseFragment;
import com.binasystems.mtimereporter.activity.sales.SaleByStoreActivity;
import com.binasystems.mtimereporter.api.Callback;
import com.binasystems.mtimereporter.api.ComaxApiManager;
import com.binasystems.mtimereporter.dialog.CustomDatePickerDialog;
import com.binasystems.mtimereporter.objects.SalesByDayDetails;
import com.binasystems.mtimereporter.utils.Formatter;
import com.binasystems.mtimereporter.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SalesByDateFragment extends BaseFragment implements OnItemClickListener {
	ListView mListView;
	CustomDatePickerDialog mDatePickerDialog;
	Calendar mCurrentCalendar = Calendar.getInstance();
	SaleByDateAdapter mAdapter;
	ComaxApiManager.DateViewType mDateViewType;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.date) {
			mDateViewType= ComaxApiManager.DateViewType.VIEW_BY_DAY;
			showSelectDatePickerDialog();
			return true;
		}
		if (id == R.id.month) {
			mDateViewType= ComaxApiManager.DateViewType.VIEW_BY_MONTH;
			showSelectDatePickerDialog();
			return true;
		}
		if (id == R.id.year) {
			mDateViewType= ComaxApiManager.DateViewType.VIEW_BY_YEAR;
			showSelectDatePickerDialog();
			return true;

		}

		return super.onOptionsItemSelected(item);
	}
	CountDownTimer countDownTimer = new CountDownTimer(180000, 180000) {
		public void onTick(long millisUntilFinished) {
		}
		public void onFinish() {
			loadData();
		}
	}.start();
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_sales_by_date, null);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initUI(view);
		
		loadData();
	}
	
	private void initUI(View view){
		
		ActionBar actionBar = getActivity().getActionBar();
		if(actionBar != null);
		{
			actionBar.setTitle(TimeTrackerApplication.getInstance().getBranch().getName());
			actionBar.setDisplayShowHomeEnabled(false);

		}
		
		mListView = (ListView) view.findViewById(R.id.listView);
		mAdapter = new SaleByDateAdapter(getActivity(), new SalesByDayDetails(), mCurrentCalendar.getTime());
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);		
	}
	
	@Override
	public void onPause() {
		super.onPause();
		System.out.println("onPause");
	}
	
	@Override
	public void onStop() {
		super.onStop();
		System.out.println("onStop");
	}


	private void showSelectDatePickerDialog() {
		countDownTimer.cancel();
		mDatePickerDialog = new CustomDatePickerDialog();
		mDatePickerDialog.setOnDateSelectListener(new CustomDatePickerDialog.OnDateSelectListener() {
			@Override
			public void onDateSelect(CustomDatePickerDialog datePickerDialog, Date date) {
				mCurrentCalendar.setTime(date);
				//updateNavigationViewTitle();
				loadData();
			}

			@Override
			public void onCancel(CustomDatePickerDialog datePickerDialog) {
				countDownTimer.start();
			}
		});
		if(mDateViewType == ComaxApiManager.DateViewType.VIEW_BY_MONTH){
			mDatePickerDialog.setmDisplayType(CustomDatePickerDialog.DisplayType.MONTH_YEAR);
		} else if(mDateViewType == ComaxApiManager.DateViewType.VIEW_BY_WEEK){
			mDatePickerDialog.setmDisplayType(CustomDatePickerDialog.DisplayType.WEEK);
		} else if(mDateViewType == ComaxApiManager.DateViewType.VIEW_BY_YEAR){
			mDatePickerDialog.setmDisplayType(CustomDatePickerDialog.DisplayType.YEAR);
		} else {
			mDatePickerDialog.setmDisplayType(CustomDatePickerDialog.DisplayType.DAY);
		}
		mDatePickerDialog.setCancelable(false);

		mDatePickerDialog.setDate(mCurrentCalendar.getTime());
		mDatePickerDialog.show(getActivity().getFragmentManager(), "tag_custom_date_picker");
	}

	@Override
	public void onResume() {
		super.onResume();
		System.out.println("onResume");
	}
	
	protected void loadData(){
		countDownTimer.cancel();
		showProgress();		
		ComaxApiManager.newInstance().requestSaleDetailsByDay(mCurrentCalendar.getTime(), new Callback<SalesByDayDetails>() {
			
			@Override
			public void onSuccess(SalesByDayDetails result) {
				hideProgress();		
				// update result
				mAdapter.setSalesByDayDetails(result, mCurrentCalendar.getTime());
			}
			
			@Override
			public void onError(Exception error) {
				hideProgress();
				if(getActivity() != null)
					Utils.showErrorDialog(getActivity(), "Error", error.getMessage());
			}
		});
		countDownTimer.start();
	}
	
	@Override
	public void onItemClick(AdapterView<?> listView, View view, int position,
			long id) {
		if(position == 0){
			// DAY
			//TimeTrackerApplication.getInstance().setStore(null);
			SaleByStoreActivity.startAcitivty(getActivity(), mCurrentCalendar.getTime(), ComaxApiManager.DateViewType.VIEW_BY_DAY);

		} else if(position == 1){
			/// WEEK
			//TimeTrackerApplication.getInstance().setStore(null);
			SaleByStoreActivity.startAcitivty(getActivity(), mCurrentCalendar.getTime(), ComaxApiManager.DateViewType.VIEW_BY_WEEK);

		} else if(position == 2){
			// MONTH
			//TimeTrackerApplication.getInstance().setStore(null);
			SaleByStoreActivity.startAcitivty(getActivity(), mCurrentCalendar.getTime(), ComaxApiManager.DateViewType.VIEW_BY_MONTH);

		} else if(position == 3){
			// YEAR
			//TimeTrackerApplication.getInstance().setStore(null);
			SaleByStoreActivity.startAcitivty(getActivity(), mCurrentCalendar.getTime(), ComaxApiManager.DateViewType.VIEW_BY_YEAR);
		}
	}

	/**
	 * Adapter for sales by date list view
	 */
	public static class SaleByDateAdapter extends BaseAdapter{
		Context mContext;
		LayoutInflater mInflater;
		SalesByDayDetails mDataModel;
		Date mDate;
		@SuppressLint("SimpleDateFormat")
		SimpleDateFormat mDateFormat = new SimpleDateFormat();
		Formatter mFormatter;
		
		public SaleByDateAdapter(Context mContext, SalesByDayDetails dataModel, Date date) {
			super();
			this.mContext = mContext;
			this.mInflater = LayoutInflater.from(mContext);
			this.mDataModel = dataModel;			
			this.mDate = date;
			mFormatter = Formatter.getInstance(mContext);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		public void setSalesByDayDetails(SalesByDayDetails details, Date date){
			this.mDataModel = details;
			this.mDate = date;
			notifyDataSetChanged();
		}

		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Tag tag;
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.sales_by_date_list_item, null);
				tag = new Tag();
				tag.tv_dateHeader = (TextView) convertView
						.findViewById(R.id.tv_saleDateLabel);
				tag.tv_totalValue = (TextView) convertView
						.findViewById(R.id.tv_saleTotal);
				tag.tv_cardValue = (TextView) convertView
						.findViewById(R.id.tv_saleType1);
				tag.tv_checkValue = (TextView) convertView
						.findViewById(R.id.tv_saleType2);
				tag.tv_cashValue = (TextView) convertView
						.findViewById(R.id.tv_saleType3);
				convertView.setTag(tag);

			} else {
				tag = (Tag) convertView.getTag();
			}
			
			Double total = null;
			Double card  = null;
			Double check = null;
			Double cash  = null;
			Double other  = null;
			switch(position){
				case 0: {
					// day
					mDateFormat.applyPattern("dd/MM/yyyy");
					if(mDateFormat.format(mDate).equals(mDateFormat.format(new Date())))
						mDateFormat.applyPattern("dd/MM/yy hh:mm");
					tag.tv_dateHeader.setText(mDateFormat.format(mDate));
					if(mDataModel != null){
						total = mDataModel.getTotalHours();
						card  = mDataModel.getTotalHufsha();
						check = mDataModel.getTotalMahala(); 
						cash  = mDataModel.getTotalAvoda();

					}
					break;
				}
	
				case 1:  {
					// week
					mDateFormat.applyPattern("w");
					tag.tv_dateHeader.setText(mContext.getString(R.string.sale_week, mDateFormat.format(mDate)));
					mDateFormat.applyPattern("yyyy");
					tag.tv_dateHeader.setText(tag.tv_dateHeader.getText()+"   "+mContext.getString(R.string.sale_year, mDateFormat.format(mDate)));
					if(mDataModel != null){
						total = mDataModel.getTotalHours_w();
						card  = mDataModel.getTotalHufsha_w();
						check = mDataModel.getTotalMahala_w();
						cash  = mDataModel.getTotalAvoda_w();


					}
					break;
				}
				
				case 2: {
					// month
					mDateFormat.applyPattern("MM/yyyy");
					tag.tv_dateHeader.setText(mContext.getString(R.string.sale_month,mDateFormat.format(mDate)));
					if(mDataModel != null){
						total = mDataModel.getTotalHours_m();
						card  = mDataModel.getTotalHufsha_m();
						check = mDataModel.getTotalMahala_m();
						cash  = mDataModel.getTotalAvoda_m();


					}
					break;
				}
				
				case 3: {
					// year
					mDateFormat.applyPattern("yyyy");
					tag.tv_dateHeader.setText(mContext.getString(R.string.sale_year,mDateFormat.format(mDate)));
					if(mDataModel != null){
						total = mDataModel.getTotalHours_y();
						card  = mDataModel.getTotalHufsha_y();
						check = mDataModel.getTotalMahala_y();
						cash  = mDataModel.getTotalAvoda_y();



					}
					break;
				}
			}

			// update percents
			if(total != null){
				if(card != null && card != 0){
					card = card / total;
				}				
				if(check != null && check != 0){
					check = check / total; 
				}				
				if(cash != null && cash != 0){
					cash = cash / total;
				}

			}
			
			// bind data
			bindTagValue(tag, 
					mFormatter.formatValue(total),
					mContext.getString(R.string.sale_card_value, mFormatter.formatPercent(card)),
					mContext.getString(R.string.sale_cash_value, mFormatter.formatPercent(cash)),
					mContext.getString(R.string.sale_check_value, mFormatter.formatPercent(check)));
			return convertView;
		}
		
		private void bindTagValue(Tag tag, String totalValue, String cardValue, String cashValue, String checkValue) {
			tag.tv_totalValue.setText(totalValue);
			if (cardValue.equals("חופשה "))
				tag.tv_cardValue.setVisibility(View.GONE);
			else {
				tag.tv_cardValue.setVisibility(View.VISIBLE);
				tag.tv_cardValue.setText(cardValue);
			}
			if (cashValue.equals("עבודה "))
				tag.tv_cashValue.setVisibility(View.GONE);
			else {
				tag.tv_cashValue.setVisibility(View.VISIBLE);
				tag.tv_cashValue.setText(cashValue);
			}
			if (checkValue.equals("אחר "))
				tag.tv_checkValue.setVisibility(View.GONE);
			else {
				tag.tv_checkValue.setVisibility(View.VISIBLE);
				tag.tv_checkValue.setText(checkValue);
			}
		}

		class Tag {
			TextView tv_dateHeader;
			TextView tv_totalValue;
			TextView tv_cardValue;
			TextView tv_checkValue;
			TextView tv_cashValue;
		}

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}
	}
}
