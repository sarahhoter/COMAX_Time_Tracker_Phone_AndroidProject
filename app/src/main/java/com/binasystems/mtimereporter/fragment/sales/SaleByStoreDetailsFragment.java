package com.binasystems.mtimereporter.fragment.sales;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.activity.BaseFragment;
import com.binasystems.mtimereporter.activity.sales.SaleByAgentDetailsActivity;
import com.binasystems.mtimereporter.activity.sales.SaleByDepDetailsActivity;
import com.binasystems.mtimereporter.api.Callback;
import com.binasystems.mtimereporter.api.ComaxApiManager;
import com.binasystems.mtimereporter.api.requests.UniRequest;
import com.binasystems.mtimereporter.customview.PrevNextNavigationView;
import com.binasystems.mtimereporter.customview.PrevNextNavigationView.OnPrevNextNavigateViewListener;
import com.binasystems.mtimereporter.dialog.CustomDatePickerDialog;
import com.binasystems.mtimereporter.objects.Branch;
import com.binasystems.mtimereporter.objects.SalesByAgentDetails;
import com.binasystems.mtimereporter.objects.SalesByDepDetails;
import com.binasystems.mtimereporter.objects.SalesByStoreDetails;
import com.binasystems.mtimereporter.objects.SalesByStoreDetails.StoreInfo;
import com.binasystems.mtimereporter.utils.Formatter;
import com.binasystems.mtimereporter.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SuppressLint("SimpleDateFormat")

public class SaleByStoreDetailsFragment extends BaseFragment implements
		OnPrevNextNavigateViewListener,OnItemClickListener ,View.OnClickListener{
	public static final String EXTRA_ARRAY_POSITION = "EXTRA_ARRAY_POSITION";
	public static final String EXTRA_ARRAY_DATA = "EXTRA_ARRAY_DATA";
	public static final String EXTRA_CURRENT_DATE = "EXTRA_CURRENT_DATE";
	public static final String EXTRA_STORE_CODE = "EXTRA_STORE_CODE";
	// Store details
	private  Double totalsum;
	String Sort = "Scm";
	String SortAgent = "Scm";
	Boolean IsSortDesc = true;
	Boolean IsSortDescAgent = true;
	LinearLayout general_view;
	LinearLayout list_view;
	LinearLayout list_view_agents;
	ListView DepListView;
	ListView AgentListView;
	ListView mListView;
	private Double sump;
	PrevNextNavigationView mNavigationByDateView;
	PrevNextNavigationView mNavigationByStoreView;
	TextView branch;
	TextView total;
	TextView contribution;
	TextView agent;
	TextView total_agent;
	TextView avg_deal;
	ImageView imb;
	ImageView imt;
	ImageView imc;
	ImageView imb_agent;
	ImageView imt_agent;
	ImageView imc_agent;
	TextView text_View1;
	TextView text_View2;
	TextView text_View3;
	SaleByStoreDetailsAdapter mAdapter;
	SalesByDepAdatpter DepAdapter;
	SalesByDepAdatpter AgentAdapter;
	CustomDatePickerDialog mDatePickerDialog;
	Formatter mFormatter;
	TextView mTransactionCounterTV;
	TextView mTransactionAverageTV;
	TextView mTransactionAverage;
	ComaxApiManager.DateViewType mDateViewType;
	TextView general;
	TextView departments;
	TextView agents;
	View header_view;
	View agent_header_view;
	View sec_header_view;
	// model from boundle args
	List<StoreInfo> mStoreInfoList = new ArrayList<StoreInfo>();
	int mStorePosition = 0;
	String mStoreCode;
	View mynavigationByDateView;
	// state
	Calendar mCurrentCalendar = Calendar.getInstance();
	int i;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		totalsum=0.0;
		mFormatter = Formatter.getInstance(getActivity());
		mDateViewType = (getArguments() != null && getArguments().get(EXTRA_DATE_VIEW_TYPE) != null) ?
				(ComaxApiManager.DateViewType) getArguments().get(EXTRA_DATE_VIEW_TYPE) :
				ComaxApiManager.DateViewType.VIEW_BY_DAY;

		ActionBar actionBar = getActivity().getActionBar();

		if (actionBar != null) {
			// TODO String!@!!
			actionBar.setTitle(TimeTrackerApplication.getInstance().getBranch().getName());
			actionBar.setDisplayShowHomeEnabled(false);

		}
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

	@Override
	public View onCreateView(LayoutInflater inflater,
							 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_sales_by_store_details, null);
	}

	private void showSelectDatePickerDialog() {
		countDownTimer.cancel();
		mDatePickerDialog = new CustomDatePickerDialog();
		mDatePickerDialog.setOnDateSelectListener(new CustomDatePickerDialog.OnDateSelectListener() {
			@Override
			public void onDateSelect(CustomDatePickerDialog datePickerDialog, Date date) {
				mCurrentCalendar.setTime(date);
				updateNavigationByDateTitle();
				loadData(getCurrentStoreCode());
				loadAentData(null, false);
				loadDepData(null, false);
				switch (i) {
					case 1: {
						general_view.setVisibility(View.VISIBLE);
						list_view.setVisibility(View.GONE);
						list_view_agents.setVisibility(View.GONE);
					}break;
					case 2: {
						general_view.setVisibility(View.GONE);
						list_view.setVisibility(View.GONE);
						list_view_agents.setVisibility(View.VISIBLE);


					}break;
					case 3: {
						general_view.setVisibility(View.GONE);
						list_view.setVisibility(View.VISIBLE);
						list_view_agents.setVisibility(View.GONE);
					}break;
				}
			}

			@Override
			public void onCancel(CustomDatePickerDialog datePickerDialog) {
				countDownTimer.start();
			}
		});
		if (mDateViewType == ComaxApiManager.DateViewType.VIEW_BY_MONTH) {
			mDatePickerDialog.setmDisplayType(CustomDatePickerDialog.DisplayType.MONTH_YEAR);
		} else if (mDateViewType == ComaxApiManager.DateViewType.VIEW_BY_WEEK) {
			mDatePickerDialog.setmDisplayType(CustomDatePickerDialog.DisplayType.WEEK);
		} else if (mDateViewType == ComaxApiManager.DateViewType.VIEW_BY_YEAR) {
			mDatePickerDialog.setmDisplayType(CustomDatePickerDialog.DisplayType.YEAR);
		} else {
			mDatePickerDialog.setmDisplayType(CustomDatePickerDialog.DisplayType.DAY);
		}
		mDatePickerDialog.setCancelable(false);

		mDatePickerDialog.setDate(mCurrentCalendar.getTime());
		mDatePickerDialog.show(getActivity().getFragmentManager(), "tag_custom_date_picker");
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		getExtras();
		setupUI(view);
		loadData(getCurrentStoreCode());
		loadAentData(getCurrentStoreCode(), true);
		loadDepData(getCurrentStoreCode(), true);
	}
	private void loadAentData(String lastStoreCode, final boolean isFirstLoad) {
		countDownTimer.cancel();
		showProgress();
		ComaxApiManager api = ComaxApiManager.newInstance();
		// ComaxApiManager.DateViewType viewType = (getArguments() != null && getArguments().get(EXTRA_DATE_VIEW_TYPE) != null) ? (ComaxApiManager.DateViewType) getArguments().get(EXTRA_DATE_VIEW_TYPE) : null;
		api.requestSaleByAgentStore(String.valueOf(IsSortDescAgent),SortAgent, mCurrentCalendar.getTime(), mDateViewType, lastStoreCode,
				new Callback<SalesByAgentDetails>() {

					@Override
					public void onSuccess(SalesByAgentDetails result) {
						hideProgress();
						if (getActivity() != null) {
							if (AgentAdapter == null || isFirstLoad) {
								setupAgentListViewAdapter(result);

							} else
							{
								AgentAdapter.updateSalesByAgentDetails(result);
							}

						}

					}
					@Override
					public void onError(Exception error) {
						hideProgress();

						if (getActivity() != null)
							Utils.showErrorDialog(getActivity(), "Error", error.getMessage());
					}
				});
		countDownTimer.start();
	}
	private void loadDepData(String lastStoreCode, final boolean isFirstLoad) {
		showProgress();
		ComaxApiManager api = ComaxApiManager.newInstance();
		api.requestSaleByDepStore(String.valueOf(IsSortDesc), Sort, mCurrentCalendar.getTime(), mDateViewType, lastStoreCode,
				new Callback<SalesByDepDetails>() {

					@Override
					public void onSuccess(SalesByDepDetails result) {
						hideProgress();
						if (getActivity() != null)
							if (DepAdapter == null || isFirstLoad)
								setupDepListViewAdapter(result);
							else
								DepAdapter.updateSalesByStoreDetails(result);
					}
					@Override
					public void onError(Exception error) {
						hideProgress();

						if (getActivity() != null)
							Utils.showErrorDialog(getActivity(), "Error", error.getMessage());
					}
				});
		countDownTimer.start();
	}

	private void getExtras() {
		Bundle args = getArguments();
		if (args != null) {
			if (args.containsKey(EXTRA_ARRAY_DATA)) {
				mStoreInfoList = (ArrayList<StoreInfo>) args.get(EXTRA_ARRAY_DATA);
			}
			if (args.containsKey(EXTRA_ARRAY_POSITION)) {
				mStorePosition = args.getInt(EXTRA_ARRAY_POSITION, 0);
			}
			if (args.containsKey(EXTRA_CURRENT_DATE)) {
				mCurrentCalendar.setTime((Date) args.get(EXTRA_CURRENT_DATE));
			}
			mStoreCode = args.getString(EXTRA_STORE_CODE);
		}
	}

	public void setupUI(View view) {
		AgentListView=(ListView)view.findViewById(R.id.agentlistView);
		mListView = (ListView) view.findViewById(R.id.listView);
		DepListView = (ListView) view.findViewById(R.id.deplistView);
		header_view = view.findViewById(R.id.sales_by_store_header_view);
		header_view.findViewById(R.id.head).setVisibility(View.GONE);
		header_view.findViewById(R.id.textView).setVisibility(View.GONE);
		sec_header_view = view.findViewById(R.id.sales_by_d_header_view);
		agent_header_view=view.findViewById(R.id.sales_by_agent_header_view);
		agent_header_view.findViewById(R.id.textView_total).setVisibility(View.GONE);
		sec_header_view.findViewById(R.id.textView_total).setVisibility(View.GONE);
		branch = (TextView)sec_header_view.findViewById(R.id.branch);
		total = (TextView) sec_header_view.findViewById(R.id.total);

		general_view = (LinearLayout)view.findViewById(R.id.general_view);
		list_view = (LinearLayout)view.findViewById(R.id.list_view);
		list_view_agents = (LinearLayout)view.findViewById(R.id.list_view_socen);
		general = (TextView)view.findViewById(R.id.general);
		departments = (TextView)view.findViewById(R.id.departments);
		agents = (TextView)view.findViewById(R.id.agents);
		text_View1= (TextView)view.findViewById(R.id.text_View1);
		text_View2= (TextView)view.findViewById(R.id.text_View2);
		text_View3= (TextView)view.findViewById(R.id.text_View3);
		//contribution = (TextView) sec_header_view.findViewById(R.id.contribution);
		agent = (TextView)agent_header_view.findViewById(R.id.branch);
		total_agent = (TextView)agent_header_view.findViewById(R.id.total);
		//avg_deal = (TextView)agent_header_view.findViewById(R.id.contribution);
		imb_agent = (ImageView) agent_header_view.findViewById(R.id.imb);
		imt_agent = (ImageView) agent_header_view.findViewById(R.id.imt);
		imc_agent = (ImageView) agent_header_view.findViewById(R.id.imc);
		imb = (ImageView) sec_header_view.findViewById(R.id.imb);
		imt = (ImageView) sec_header_view.findViewById(R.id.imt);
		imc = (ImageView) sec_header_view.findViewById(R.id.imc);
		mTransactionCounterTV = (TextView) view.findViewById(R.id.textCounterValue);
		mTransactionAverageTV = (TextView) view.findViewById(R.id.textAverageValue);
		mTransactionAverage = (TextView) view.findViewById(R.id.textAverage);
		mNavigationByDateView = new PrevNextNavigationView(view.findViewById(R.id.navigationByDateView));
		mNavigationByStoreView = new PrevNextNavigationView(view.findViewById(R.id.navigationByStoreView));
		mNavigationByStoreView.setViewBackgroundColor(0xFF7F7F7F);
		header_view = view.findViewById(R.id.sales_by_store_header_view);
		mynavigationByDateView = header_view.findViewById(R.id.navigationByDateView);
		DepListView.setOnItemClickListener(this);
		AgentListView.setOnItemClickListener(this);
		general.setOnClickListener(this);
		departments.setOnClickListener(this);
		agents.setOnClickListener(this);
		total.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (imt.getVisibility() == View.VISIBLE) {
					if(imt.getTag().equals("down")) {
						imt.setTag("up");
						Sort = "Scm";
						IsSortDesc = false;
						loadDepData(null, false);
						imt.setImageResource(R.drawable.upn);
					}
					else {
						imt.setTag("down");
						Sort = "Scm";
						IsSortDesc = true;
						loadDepData(null, false);
						imt.setImageResource(R.drawable.down);
					}
				} else {
					if (imb.getVisibility() == View.VISIBLE)
						imb.setVisibility(View.INVISIBLE);
					imc.setVisibility(View.INVISIBLE);
					imt.setImageResource(R.drawable.upn);
					imt.setVisibility(View.VISIBLE);
					IsSortDesc = false;
					imt.setTag("up");
					Sort = "Scm";
					loadDepData(null, false);
				}
			}
		});
		branch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (imb.getVisibility() == View.VISIBLE) {
					if(imb.getTag().equals("down")) {
						imb.setTag("up");
						IsSortDesc = false;
						loadDepData(null, false);
						imb.setImageResource(R.drawable.upn);
					}
					else {
						imb.setTag("down");
						IsSortDesc = true;
						loadDepData(null, false);
						imb.setImageResource(R.drawable.down);
					}
				} else {
					if (imc.getVisibility() == View.VISIBLE)
						imc.setVisibility(View.INVISIBLE);
					imt.setVisibility(View.INVISIBLE);
					imb.setImageResource(R.drawable.upn);
					imb.setVisibility(View.VISIBLE);
					imb.setTag("up");
					IsSortDesc = false;
					Sort = null;
					loadDepData(null, false);
				}
			}
		});
		/*contribution.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (imc.getVisibility() == View.VISIBLE) {
					if(imc.getTag().equals("down")) {
						imc.setTag("up");
						Sort = "Scm";
						IsSortDesc = false;
						loadDepData(null, false);
						imc.setImageResource(R.drawable.upn);
					}
					else {
						imc.setTag("down");
						Sort = "AczTr";
						IsSortDesc = true;
						loadDepData(null, false);
						imc.setImageResource(R.drawable.down);
					}
				} else {
					if (imb.getVisibility() == View.VISIBLE)
						imb.setVisibility(View.INVISIBLE);
					imt.setVisibility(View.INVISIBLE);
					imc.setImageResource(R.drawable.upn);
					imc.setVisibility(View.VISIBLE);
					imc.setTag("up");
					IsSortDesc = false;
					Sort = "AczTr";
					loadDepData(null, false);
				}
			}
		});*/
		agent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				changeSort(imb_agent,imc_agent,imt_agent, null,true);


			}
		});

		total_agent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				changeSort(imt_agent,imb_agent,imc_agent, "Scm",true);
			}
		});
		/*avg_deal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				changeSort(imc_agent,imb_agent,imt_agent, "Total_ScmM",true);
			}
		});*/
		general.setBackgroundColor(Color.WHITE);
		// set listeners
		mNavigationByDateView.setListener(this);
		mNavigationByStoreView.setListener(this);
		updateNavigationByDateTitle();
		updateNavigationByStoreTitle();

	}
	private void changeSort(ImageView image1,ImageView image2,ImageView image3,String sort,boolean i){

		if (image1.getVisibility() == View.VISIBLE) {
			if(image1.getTag().equals("down")) {
				image1.setTag("up");


				if(i) {
					SortAgent = sort;
					IsSortDescAgent = false;
					loadAentData(null, false);
				}
				else {
					Sort = sort;
					IsSortDesc = false;
					loadDepData(null, false);
				}
				image1.setImageResource(R.drawable.upn);
			}
			else {
				image1.setTag("down");


				if(i) {
					SortAgent = sort;
					IsSortDescAgent = true;
					loadAentData(null, false);
				}
				else {
					Sort = sort;
					IsSortDesc = true;
					loadDepData(null, false);
				}
				image1.setImageResource(R.drawable.down);
			}
		} else {
			if (image2.getVisibility() == View.VISIBLE)
				image2.setVisibility(View.INVISIBLE);
			image3.setVisibility(View.INVISIBLE);
			image1.setImageResource(R.drawable.upn);
			image1.setVisibility(View.VISIBLE);

			if(i) {
				IsSortDescAgent = false;
				SortAgent = sort;
				loadAentData(null, false);
			}
			else {
				IsSortDesc = false;
				Sort = sort;
				loadDepData(null, false);
			}
		}
	}
	CountDownTimer countDownTimer = new CountDownTimer(180000, 180000) {
		public void onTick(long millisUntilFinished) {
		}
		public void onFinish() {
			loadData(getCurrentStoreCode());
			loadDepData(null,false);
			loadAentData(null,false);
		}
	}.start();
	private String getCurrentStoreCode() {

		if (mStoreInfoList != null && mStoreInfoList.size() > 0) {
			int pos = 0;
			if (mStorePosition > 0 && mStorePosition < mStoreInfoList.size()) {
				pos = mStorePosition;
			}
			return ""; //+ mStoreInfoList.get(pos).StoreC;
		}
		if (mStoreCode != null) {
			return mStoreCode;
		}
		return null;
	}

	private void updateNavigationByDateTitle() {
		boolean prevButtonVisible = true;
		boolean nextButtonVisible = true;
		String formatedTitle;
		switch (mDateViewType) {
			case VIEW_BY_DAY:
				if (mCurrentCalendar.getTime().getDay() == Calendar.getInstance().getTime().getDay()) {
					nextButtonVisible = false;
				}
				formatedTitle = Formatter.DAY_FORMATTER.format(mCurrentCalendar.getTime());
				break;

			case VIEW_BY_WEEK:
				// TODO string
				if (Formatter.WEEK_FORMATTER.format(mCurrentCalendar.getTime()) == Formatter.WEEK_FORMATTER.format(Calendar.getInstance().getTime().getDay())) {
					nextButtonVisible = false;
				}
				formatedTitle = " שבוע "+ Formatter.WEEK_FORMATTER.format(mCurrentCalendar.getTime())+" שנת  "+ Formatter.YEAR_FORMATTER.format(mCurrentCalendar.getTime());
				break;

			case VIEW_BY_MONTH:
				if (mCurrentCalendar.getTime().getMonth() == Calendar.getInstance().getTime().getMonth()) {
					nextButtonVisible = false;
				}
				formatedTitle = Formatter.MONTH_FORMATTER.format(mCurrentCalendar.getTime());
				break;

			case VIEW_BY_YEAR:
				if (mCurrentCalendar.getTime().getYear() == Calendar.getInstance().getTime().getYear()) {
					nextButtonVisible = false;
				}
				formatedTitle = Formatter.YEAR_FORMATTER.format(mCurrentCalendar.getTime());
				break;

			default:
				formatedTitle = Formatter.DAY_FORMATTER.format(mCurrentCalendar.getTime());
		}
		mNavigationByDateView.setPrevNextButtonsVisible(prevButtonVisible, nextButtonVisible);
		mNavigationByDateView.setTitle(formatedTitle);

	}

	private void updateNavigationByStoreTitle() {

		if (mStoreInfoList != null && mStoreInfoList.size() > 0) {

			// update store position
			if (mStorePosition >= mStoreInfoList.size()) {
				mStorePosition = 0;
			}
			// update state buttons
			boolean prevButtonVisible = true;
			boolean nextButtonVisible = true;
			if (mStorePosition == 0) {
				prevButtonVisible = false;
			}
			if (mStorePosition == mStoreInfoList.size() - 1) {
				nextButtonVisible = false;
			}
				mNavigationByStoreView.setPrevNextButtonsVisible(prevButtonVisible, nextButtonVisible);
			// update title
			String title = mStoreInfoList.get(mStorePosition).D;
			mNavigationByStoreView.setTitle(mFormatter.formatValue(title));
		} else mNavigationByStoreView.setTitle("");

	}

	@Override
	public void onItemClick(AdapterView<?> listView, View view, int position,
							long id) {
		// open store details
		switch (listView.getId()) {
			case R.id.deplistView: {
				int positionInStore = position; // update header position
				SalesByDepDetails.DepInfo depInfo = (SalesByDepDetails.DepInfo) listView.getItemAtPosition(position);
				if(depInfo.Ind_DepC==null)
					UniRequest.dep ="0";
				else  UniRequest.dep = depInfo.Ind_DepC.toString();
				if (depInfo != null) {
					SaleByDepDetailsActivity.startActivity(getActivity(),
							positionInStore, DepAdapter.getDataList(),
							mCurrentCalendar.getTime(), mDateViewType);
				}
			}
			break;
			case R.id.agentlistView: {
				int positionInAgent = position; // update header position
				SalesByAgentDetails.AgentInfo agentInfo = (SalesByAgentDetails.AgentInfo) listView.getItemAtPosition(position);
				UniRequest.dep = agentInfo.SochenC.toString();
				if (agentInfo != null) {
					SaleByAgentDetailsActivity.startActivity(getActivity(),
							positionInAgent, AgentAdapter.getAgentDataList(),
							mCurrentCalendar.getTime(), mDateViewType);
				}
			}
		}
	}






	private void setupListViewAdapter(SalesByStoreDetails result) {
		mListView.setAdapter(null);
		if (result != null) {
			// Update header view		
			Formatter formatter = Formatter.getInstance(getActivity());
			// TODO string
			// total
			((TextView) header_view.findViewById(R.id.textView_total)).setText(formatter.formatValue(String.format("%1$,d", Math.round(result.getSalesInfo().TotalHours)) + "  " + getResources().getString(R.string.nis).toString()));
			if (result.getSalesInfo().TotalHours < 0)
				((TextView) header_view.findViewById(R.id.textView_total)).setTextColor(Color.parseColor("#ff0000"));
			// other
			text_View1.setText("אחר");
			// credit
			text_View2.setText("חופשה");
			// cash
			text_View3.setText("עבודה");

			mAdapter = new SaleByStoreDetailsAdapter(getActivity(), result);
			//mListView.addHeaderView(mListHeaderView);
			mListView.setAdapter(mAdapter);
		}
	}

	private void loadData(String storeC) {
		countDownTimer.cancel();
		ComaxApiManager manager = ComaxApiManager.newInstance();
		showProgress();
		manager.requestSaleByStoreDetails(mCurrentCalendar.getTime(), mDateViewType, storeC, new Callback<SalesByStoreDetails>() {

			@Override
			public void onSuccess(SalesByStoreDetails result) {
				if (getActivity() != null) {
					hideProgress();
					if (result != null) {
						setupListViewAdapter(result);
						if (getActivity() != null) {
								String stringCounterValue = "0";
							String stringAverageValue = "0";
							if (result.getSalesInfo().TransactionCounter != null && result.getSalesInfo().TransactionCounter > 0) {
									stringCounterValue = Formatter.getInstance(getActivity()).formatValueNoDigit(result.getSalesInfo().TransactionCounter);
								stringAverageValue = Formatter.getInstance(getActivity()).formatValue(String.format(result.getSalesInfo().AvgTransaction+ "  " + getResources().getString(R.string.nis).toString()));
							}
								mTransactionCounterTV.setText(stringCounterValue);
							if(result.getSalesInfo().TransactionCounter<0)
								mTransactionCounterTV.setTextColor(Color.parseColor("FF0000"));
							else
								mTransactionCounterTV.setTextColor(Color.parseColor("#A9A9A9"));

							mTransactionAverageTV.setText(stringAverageValue);
							if(result.getSalesInfo().AvgTransaction<0)
								mTransactionAverageTV.setTextColor(Color.parseColor("FF0000"));
							else
								mTransactionAverageTV.setTextColor(Color.parseColor("#A9A9A9"));
						}

					} else {
						mTransactionCounterTV.setText("");
						mTransactionAverageTV.setText("");
						mTransactionAverage.setText("");
					}
				}
			}

			@Override
			public void onError(Exception error) {
				if (getActivity() != null) {
					hideProgress();
					Utils.showErrorDialog(getActivity(), "Error", error.getMessage());
					mTransactionCounterTV.setText("");
					mTransactionAverageTV.setText("");
					mTransactionAverage.setText("");
				}
			}
		});
	}
	private void setupDepListViewAdapter(SalesByDepDetails result) {
		DepListView.setAdapter(null);

		if (result.getDepListInfo().size() > 0) {
			DepListView.setVisibility(View.VISIBLE);
			header_view.setVisibility(View.VISIBLE);
			DepAdapter = new SalesByDepAdatpter(getActivity(),result, R.layout.sales_by_store_list_item,null);
			DepListView.setAdapter(DepAdapter);
			for (SalesByDepDetails.DepInfo depInfo:result.getDepListInfo())
			{
				totalsum+=depInfo.Scm;
			}
		}

	}
	private void setupAgentListViewAdapter(SalesByAgentDetails result) {
		AgentListView.setAdapter(null);

		if (result.getAgentListInfo().size() > 0) {
			AgentListView.setVisibility(View.VISIBLE);
			header_view.setVisibility(View.VISIBLE);
			AgentAdapter = new SalesByDepAdatpter(getActivity(),null, R.layout.sales_by_agent_list_item,result);
			AgentListView.setAdapter(AgentAdapter);
			for (SalesByAgentDetails.AgentInfo agentInfo:result.getAgentListInfo())
			{
				totalsum+=agentInfo.Scm;
			}
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.general:{
				i=1;
				general.setBackgroundColor(Color.WHITE);
				agents.setBackgroundColor(Color.parseColor("#A9A9A9"));
				departments.setBackgroundColor(Color.parseColor("#A9A9A9"));
				general_view.setVisibility(View.VISIBLE);
				list_view.setVisibility(View.GONE);
				list_view_agents.setVisibility(View.GONE);

			}break;
			case R.id.agents:{
				i=2;
				general.setBackgroundColor(Color.parseColor("#A9A9A9"));
				agents.setBackgroundColor(Color.WHITE);
				departments.setBackgroundColor(Color.parseColor("#A9A9A9"));
				general_view.setVisibility(View.GONE);
				list_view.setVisibility(View.GONE);
				list_view_agents.setVisibility(View.VISIBLE);
				//loadAentData(getCurrentStoreCode(), true);

			}break;
			case R.id.departments:{
				i=3;
				general.setBackgroundColor(Color.parseColor("#A9A9A9"));
				agents.setBackgroundColor(Color.parseColor("#A9A9A9"));
				departments.setBackgroundColor(Color.WHITE);
				general_view.setVisibility(View.GONE);
				list_view.setVisibility(View.VISIBLE);
				list_view_agents.setVisibility(View.GONE);
				//loadDepData(getCurrentStoreCode(), true);

			}break;
		}
	}

	@Override
	public void onNextButtonClick(PrevNextNavigationView view) {
		if (view == mNavigationByDateView) {
			int calendarField = Calendar.DAY_OF_MONTH;
			switch (mDateViewType) {
				case VIEW_BY_DAY:
					calendarField = Calendar.DAY_OF_MONTH;
					break;
				case VIEW_BY_WEEK:
					calendarField = Calendar.WEEK_OF_YEAR;
					break;
				case VIEW_BY_MONTH:
					calendarField = Calendar.MONTH;
					break;
				case VIEW_BY_YEAR:
					calendarField = Calendar.YEAR;
					break;
			}
			mCurrentCalendar.add(calendarField, 1);
			updateNavigationByDateTitle();

		} /*else if (view == mNavigationByStoreView) {
			if (mStorePosition < mStoreInfoList.size() - 1) {
				mStorePosition++;
				Branch branch=new Branch();
				branch.setC(mStoreInfoList.get(mStorePosition).StoreC.toString());
				branch.setName(mStoreInfoList.get(mStorePosition).StoreNm);
				branch.setCode(mStoreInfoList.get(mStorePosition).StoreKod.toString());
				TimeTrackerApplication.getInstance().setStore(branch);
				updateNavigationByStoreTitle();
				loadData("" + mStoreInfoList.get(mStorePosition).StoreC);
				loadDepData(null, false);
				loadAentData(null, false);
			}
		}*/
		loadData(getCurrentStoreCode());
		loadDepData(null, false);
		loadAentData(null, false);
	}
		@Override
		public void onPrevButtonClick (PrevNextNavigationView view){
			if (view == mNavigationByDateView) {
				int calendarField = Calendar.DAY_OF_MONTH;
				switch (mDateViewType) {
					case VIEW_BY_DAY:
						calendarField = Calendar.DAY_OF_MONTH;
						break;
					case VIEW_BY_WEEK:
						calendarField = Calendar.WEEK_OF_YEAR;
						break;
					case VIEW_BY_MONTH:
						calendarField = Calendar.MONTH;
						break;
					case VIEW_BY_YEAR:
						calendarField = Calendar.YEAR;
						break;
				}
				mCurrentCalendar.add(calendarField, -1);
				updateNavigationByDateTitle();

			} /*else if (view == mNavigationByStoreView) {
				if (mStorePosition > 0) {
					mStorePosition--;
					updateNavigationByStoreTitle();
					Branch branch=new Branch();
					branch.setC(mStoreInfoList.get(mStorePosition).StoreC.toString());
					branch.setName(mStoreInfoList.get(mStorePosition).StoreNm);
					branch.setCode(mStoreInfoList.get(mStorePosition).StoreKod.toString());
					TimeTrackerApplication.getInstance().setStore(branch);
					loadData("" + mStoreInfoList.get(mStorePosition).StoreC);
					loadDepData(null, false);
					loadAentData(null, false);
				}
			}*/
			loadData(getCurrentStoreCode());
			loadDepData(null, false);
			loadAentData(null, false);
		}

		@Override
		public void onButtonClick (PrevNextNavigationView view){
			if (view == mNavigationByDateView)
				showSelectDatePickerDialog();
			else getActivity().finish();
		}

	// Classes
	class SaleByStoreDetailsAdapter extends BaseAdapter {
		Context mContext;
		LayoutInflater mInflater;
		Formatter mFormatter;
		SalesByStoreDetails mSalesDetails;

		public SaleByStoreDetailsAdapter(Context context, SalesByStoreDetails salesDetails) {
			this.mContext = context;
			this.mInflater = LayoutInflater.from(context);
			this.mSalesDetails = salesDetails;
			this.mFormatter = Formatter.getInstance(context);
		}


		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Tag tag;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.sales_by_store_detail_list_item, null);
				tag = new Tag();
				tag.textView1 = (TextView) convertView.findViewById(R.id.textView1);
				tag.textView2 = (TextView) convertView.findViewById(R.id.textView2);
				tag.textView3 = (TextView) convertView.findViewById(R.id.textView3);
				convertView.setTag(tag);

			} else {
				tag = (Tag) convertView.getTag();
			}

			Double totalOther = mSalesDetails.getSalesInfo().TotalOther;
			Double totalCreadit = mSalesDetails.getSalesInfo().TotalHufsha;
			Double totalCash = mSalesDetails.getSalesInfo().TotalAvoda;

			switch (position) {
				case 0:
					if (totalOther != null && totalOther != 0.0) {
						tag.textView1.setText(mFormatter.formatValue(totalOther));
						if (totalOther < 0.0)
							tag.textView1.setTextColor(Color.parseColor("#ff0000"));
						else
							tag.textView1.setTextColor(Color.parseColor("#A9A9A9"));

					} else
						tag.textView1.setText("");
					if (totalCreadit != null && totalCreadit != 0.0) {
						tag.textView2.setText(mFormatter.formatValue(totalCreadit));
						if (totalCreadit < 0.0)
							tag.textView2.setTextColor(Color.parseColor("#ff0000"));
						else
							tag.textView2.setTextColor(Color.parseColor("#A9A9A9"));
					} else
						tag.textView2.setText("");
					if (totalCash != null && totalCash != 0.0) {
						tag.textView3.setText(mFormatter.formatValue(totalCash));
						if (totalCash < 0.0)
							tag.textView3.setTextColor(Color.parseColor("#ff0000"));
						else
							tag.textView3.setTextColor(Color.parseColor("#A9A9A9"));
					} else
						tag.textView3.setText("");
					break;

				case 1:
					// calculate percents
					Double totalSales = mSalesDetails.getSalesInfo().TotalHours;
					if (totalSales != null && totalSales != 0) {
						if (totalOther != null && totalOther != 0.0) {
							totalOther = totalOther / totalSales;
							if (totalOther < 0.0)
								tag.textView1.setTextColor(Color.parseColor("#ff0000"));
							else
								tag.textView1.setTextColor(Color.parseColor("#A9A9A9"));

						}

						if (totalCreadit != null && totalCreadit != 0.0) {
							totalCreadit = totalCreadit / totalSales;
							if (totalCreadit < 0.0)
								tag.textView2.setTextColor(Color.parseColor("#ff0000"));
							else
								tag.textView2.setTextColor(Color.parseColor("#A9A9A9"));

						}
						if (totalCash != null && totalCash != 0.0) {
							totalCash = totalCash / totalSales;
							if (totalCash < 0.0)
								tag.textView3.setTextColor(Color.parseColor("#ff0000"));
							else
								tag.textView3.setTextColor(Color.parseColor("#A9A9A9"));
						}

						tag.textView1.setText(mFormatter.formatPercent(totalOther));
						tag.textView2.setText(mFormatter.formatPercent(totalCreadit));
						tag.textView3.setText(mFormatter.formatPercent(totalCash));

					} else {
						// can't calculate percents
						tag.textView1.setText("");
						tag.textView2.setText("");
						tag.textView3.setText("");
					}
					break;

				default:
					break;
			}

			return convertView;
		}

		class Tag {

			TextView textView1;
			TextView textView2;
			TextView textView3;
		}
	}

	class SalesByDepAdatpter extends BaseAdapter {
		List<SalesByDepDetails.DepInfo> mData;
		List<SalesByAgentDetails.AgentInfo> mDataAgent;
		Context mContext;
		Boolean flag=false;
		LayoutInflater mInflater;
		Formatter mFormatter;
		SalesByDepDetails mSalesByStoreDetails;
		SalesByAgentDetails mSalesByAgentDetails;
		int src;
		public SalesByDepAdatpter(Context context,
								  SalesByDepDetails salesBydepDetails, int src, SalesByAgentDetails salesByAgentDetails) {
			this.mSalesByStoreDetails = salesBydepDetails;
			if(salesBydepDetails!=null)
			this.mData = salesBydepDetails.getDepListInfo();
			this.mSalesByAgentDetails=salesByAgentDetails;
			if(salesByAgentDetails!=null)
				mDataAgent=salesByAgentDetails.getAgentListInfo();
			this.mContext = context;
			this.mInflater = LayoutInflater.from(context);
			this.mFormatter = Formatter.getInstance(context);
			this.src=src;
		}

		public List<SalesByDepDetails.DepInfo> getDataList() {
			return mData;
		}
		public List<SalesByAgentDetails.AgentInfo> getAgentDataList() {
			return mDataAgent;
		}

		public void updateSalesByStoreDetails(
				SalesByDepDetails salesByDepDetails) {
				this.mSalesByStoreDetails = salesByDepDetails;
				mData = salesByDepDetails.getDepListInfo();

			notifyDataSetChanged();
		}
		public void updateSalesByAgentDetails(
				SalesByAgentDetails salesByAgentDetails) {
			this.mSalesByAgentDetails = salesByAgentDetails;
			mDataAgent = salesByAgentDetails.getAgentListInfo();

			notifyDataSetChanged();
		}
		@Override
		public int getCount() {
			if (mData != null)
				return mData.size();
			else if (mDataAgent != null)
				return mDataAgent.size();

			return 0;
		}

		@Override
		public Object getItem(int position) {
			if (mData != null)
				return mData.get(position);
			else 	if (mDataAgent != null)
				return mDataAgent.get(position);
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Tag tag;
			flag=false;
			if(mSalesByStoreDetails!=null) {
				Object item = mData.get(position);
				if (item != null) {
					SalesByDepDetails.DepInfo depInfo = (SalesByDepDetails.DepInfo) item;
					if(depInfo.Scm==0.0)
						flag=true;
						if (convertView == null) {
							convertView = mInflater.inflate(src, null);
							tag = new Tag();
							tag.textView1 = (TextView) convertView.findViewById(R.id.textView1);
							tag.textView2 = (TextView) convertView.findViewById(R.id.textView2);
							tag.textView3 = (TextView) convertView.findViewById(R.id.textView3);
							convertView.setTag(tag);

						} else {
							tag = (Tag) convertView.getTag();
						}


						//   sum percent
						sump = null;
						if (depInfo.Scm != null && totalsum != 0) {
							sump = depInfo.Scm / totalsum;
						}
						tag.textView1.setText(mFormatter.formatValueD(sump));
						if (sump != null && sump < 0.0)
							tag.textView1.setTextColor(Color.parseColor("#FF0000"));
						else
							tag.textView1.setTextColor(Color.parseColor("#A9A9A9"));
						tag.textView2.setText(mFormatter.formatValue(depInfo.Scm));
						if (depInfo.Scm < 0.0)
							tag.textView2.setTextColor(Color.parseColor("#FF0000"));
						else
							tag.textView2.setTextColor(Color.parseColor("#A9A9A9"));
						tag.textView3.setText(mFormatter.formatValue(depInfo.Ind_DepNm));

						if (position == getCount() - 1
								&& mSalesByStoreDetails.getHasMoreRows()) {
							//load next data
							loadDepData(String.valueOf(depInfo.Ind_DepC), false);
						}

				}
				} else {
					Object item = mDataAgent.get(position);
					if (item != null) {
						SalesByAgentDetails.AgentInfo agentInfo = (SalesByAgentDetails.AgentInfo) item;
						//   sum percent
						if (agentInfo == null || agentInfo.SochenNm.equals("") && agentInfo.Scm == 0.000) {
						} else {
							if (convertView == null) {
								convertView = mInflater.inflate(src, null);
								tag = new Tag();
								tag.textView1 = (TextView) convertView.findViewById(R.id.textView1);
								tag.textView2 = (TextView) convertView.findViewById(R.id.textView2);
								tag.textView3 = (TextView) convertView.findViewById(R.id.textView3);
								convertView.setTag(tag);

							} else {
								tag = (Tag) convertView.getTag();
							}
							double totalSales = mSalesByAgentDetails.getSalesInfo().TotalSales != null ? mSalesByAgentDetails
									.getSalesInfo().TotalSales : 0d;

							tag.textView1.setText(mFormatter.formatValueTowDig(agentInfo.Total_ScmM));
							if (agentInfo.Total_ScmM < 0.0)
								tag.textView1.setTextColor(Color.parseColor("#FF0000"));
							else tag.textView1.setTextColor(Color.parseColor("#A9A9A9"));
							tag.textView2.setText(mFormatter.formatValue(agentInfo.Scm));
							if (agentInfo.Scm < 0.0)
								tag.textView2.setTextColor(Color.parseColor("#FF0000"));
							else tag.textView2.setTextColor(Color.parseColor("#A9A9A9"));
							if (agentInfo.SochenC == 0) tag.textView3.setText("");
							else tag.textView3.setText(mFormatter.formatValue(agentInfo.SochenNm));

						}

				}
			}
			if (flag)
				return  mInflater.inflate(R.layout.null_item, null);
			return convertView;
		}

		class Tag {
			TextView textView1;
			TextView textView2;
			TextView textView3;
		}
	}
}