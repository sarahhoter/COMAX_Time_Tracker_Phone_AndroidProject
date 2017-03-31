package com.binasystems.mtimereporter.fragment.sales.Sales;

import android.annotation.SuppressLint;
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
import android.widget.ListView;
import android.widget.TextView;

import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.activity.BaseFragment;
import com.binasystems.mtimereporter.activity.sales.SaleByAgentDetailsActivity;
import com.binasystems.mtimereporter.api.Callback;
import com.binasystems.mtimereporter.api.ComaxApiManager;
import com.binasystems.mtimereporter.api.requests.UniRequest;
import com.binasystems.mtimereporter.customview.PrevNextNavigationView;
import com.binasystems.mtimereporter.customview.PrevNextNavigationView.OnPrevNextNavigateViewListener;
import com.binasystems.mtimereporter.dialog.CustomDatePickerDialog;
import com.binasystems.mtimereporter.objects.SalesByAgentDetails;
import com.binasystems.mtimereporter.utils.Formatter;
import com.binasystems.mtimereporter.utils.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SuppressLint("SimpleDateFormat")
public class SalesByAgentFragment extends BaseFragment implements
        OnPrevNextNavigateViewListener, OnItemClickListener ,View.OnClickListener{

    public static String EXTRA_DATE = "EXTRA_DATE";
    ListView mListView;
    Calendar mCurrentCalendar = Calendar.getInstance();
    SalesByAgentAdatpter mAdapter;
    CustomDatePickerDialog mDatePickerDialog;
    PrevNextNavigationView mByDateNavigationView;
    ComaxApiManager.DateViewType mDateViewType;
    TextView cancel;
    private  Double totalsum;
    private  Double avg_deal;
    private TextView branch = null;
    private TextView total;
    private TextView contribution;
    private ImageView imc;
    private ImageView imt;
    private ImageView imb;
    String Sort = "Scm";
    Boolean IsSortDesc=true;
    View header_view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        totalsum=0.0;
        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey(EXTRA_DATE))
                mCurrentCalendar.setTime((Date) args.get(EXTRA_DATE));

            mDateViewType = (ComaxApiManager.DateViewType) args.get(EXTRA_DATE_VIEW_TYPE);
            if (mDateViewType == null) {
                mDateViewType = ComaxApiManager.DateViewType.VIEW_BY_DAY;
            }
        }

        setHasOptionsMenu(true);

    }
    CountDownTimer countDownTimer = new CountDownTimer(180000, 180000) {
        public void onTick(long millisUntilFinished) {
        }
        public void onFinish() {
            loadData(null,false);
        }
    }.start();
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
            mDateViewType = ComaxApiManager.DateViewType.VIEW_BY_YEAR;
            showSelectDatePickerDialog();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_sales_by_agent, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupUI(view);
        loadData(null, true);
    }
    private void setupListViewAdapter(SalesByAgentDetails result) {
        mListView.setAdapter(null);


        if (result.getAgentListInfo().size() > 0) {
            totalsum=0.0;
            mListView.setVisibility(View.VISIBLE);
            header_view.setVisibility(View.VISIBLE);

            for (SalesByAgentDetails.AgentInfo agentInfo:result.getAgentListInfo())
                totalsum+=agentInfo.Scm;

            Formatter formatter = Formatter.getInstance(getActivity());
            TextView t1;
            t1=((TextView) header_view.findViewById(R.id.textView_total));
            t1.setText(formatter.formatValue(String.format("%1$,d",Math.round(result.getSalesInfo().TotalSales)) + " " + getResources().getString(R.string.nis).toString()));
            mAdapter = new SalesByAgentAdatpter(getActivity(), result);
            mListView.setAdapter(mAdapter);
        }
    }


    private void showSelectDatePickerDialog() {
        countDownTimer.cancel();
            mDatePickerDialog = new CustomDatePickerDialog();
            mDatePickerDialog.setOnDateSelectListener(new CustomDatePickerDialog.OnDateSelectListener() {
                @Override
                public void onDateSelect(CustomDatePickerDialog datePickerDialog, Date date) {
                    mCurrentCalendar.setTime(date);
                    updateNavigationViewTitle();
                    loadData(null, true);
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

    private void setupUI(View view) {
        header_view = view.findViewById(R.id.sales_by_dep_header_view);
        mListView = (ListView) view.findViewById(R.id.list);
        branch = (TextView)header_view.findViewById(R.id.branch);
        total = (TextView)header_view.findViewById(R.id.total);
        contribution = (TextView)header_view.findViewById(R.id.contribution);
        imb=(ImageView) header_view.findViewById(R.id.imb);
        imt=(ImageView) header_view.findViewById(R.id.imt);
        imc=(ImageView) header_view.findViewById(R.id.imc);



        mByDateNavigationView = new PrevNextNavigationView(
                view.findViewById(R.id.navigationByDateView));
        // set listeners
        mListView.setOnItemClickListener(this);
        mByDateNavigationView.setListener(this);

        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imt.getVisibility()== View.VISIBLE){
                    if(imt.getTag().equals("down")) {
                        imt.setTag("up");
                        Sort = "Scm";
                        IsSortDesc = false;
                        loadData(null, false);
                        imt.setImageResource(R.drawable.upn);
                    }
                    else {
                        imt.setTag("down");
                        Sort = "Scm";
                        IsSortDesc = true;
                        loadData(null, false);
                        imt.setImageResource(R.drawable.down);
                    }
                }
                else {
                    if (imb.getVisibility() == View.VISIBLE)
                        imb.setVisibility(View.INVISIBLE);
                    imc.setVisibility(View.INVISIBLE);
                    imt.setImageResource(R.drawable.upn);
                    imt.setVisibility(View.VISIBLE);
                    imt.setTag("up");
                    IsSortDesc=false;
                    Sort = "Scm";
                    loadData(null, false);

                }
            }
        });
        branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imb.getVisibility() == View.VISIBLE) {
                    if(imb.getTag().equals("down")) {
                        imb.setTag("up");
                       // Sort = "Scm";
                        IsSortDesc = false;
                        loadData(null, false);
                        imb.setImageResource(R.drawable.upn);
                    }
                    else {
                        imb.setTag("down");
                    //    Sort = null;
                        IsSortDesc = true;
                        loadData(null, false);
                        imb.setImageResource(R.drawable.down);
                    }
                } else {
                    if (imc.getVisibility() == View.VISIBLE)
                        imc.setVisibility(View.INVISIBLE);
                    imt.setVisibility(View.INVISIBLE);
                    imb.setImageResource(R.drawable.upn);
                    imb.setVisibility(View.VISIBLE);
                    imb.setTag("up");
                    IsSortDesc=false;
                    Sort = null;
                    loadData(null, false);
                }
            }
        });
        contribution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imc.getVisibility() == View.VISIBLE) {
                    if(imc.getTag().equals("down")) {
                        imc.setTag("up");
                        Sort = "Total_ScmM";
                        IsSortDesc = false;
                        loadData(null, false);
                        imc.setImageResource(R.drawable.upn);
                    }
                    else {
                        imc.setTag("down");
                        Sort = "Total_ScmM";
                        IsSortDesc = true;
                        loadData(null, false);
                        imc.setImageResource(R.drawable.down);
                    }
                } else {
                    if (imb.getVisibility() == View.VISIBLE)
                        imb.setVisibility(View.INVISIBLE);
                    imt.setVisibility(View.INVISIBLE);
                    imc.setImageResource(R.drawable.upn);
                    imc.setTag("up");
                    imc.setVisibility(View.VISIBLE);
                    IsSortDesc=false;
                    Sort = "Total_ScmM";
                    loadData(null, false);
                }
            }
        });
        // setup aciton bar

//        if (actionBar != null) {
//            actionBar.setTitle(TimeTrackerApplication.getInstance().getBranch().getName());
//            actionBar.setDisplayShowHomeEnabled(false);
//        }

        updateNavigationViewTitle();
    }

    private void updateNavigationViewTitle() {
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
        mByDateNavigationView.setPrevNextButtonsVisible(prevButtonVisible, nextButtonVisible);
        mByDateNavigationView.setTitle(formatedTitle);
    }

    private void loadData(String lastStoreCode, final boolean isFirstLoad) {
        countDownTimer.cancel();
        showProgress();
        ComaxApiManager api = ComaxApiManager.newInstance();
        // ComaxApiManager.DateViewType viewType = (getArguments() != null && getArguments().get(EXTRA_DATE_VIEW_TYPE) != null) ? (ComaxApiManager.DateViewType) getArguments().get(EXTRA_DATE_VIEW_TYPE) : null;
        api.requestSaleByAgent(String.valueOf(IsSortDesc),Sort, mCurrentCalendar.getTime(), mDateViewType, lastStoreCode,
                new Callback<SalesByAgentDetails>() {

                    @Override
                    public void onSuccess(SalesByAgentDetails result) {
                        hideProgress();
                        if (getActivity() != null) {
                            if (mAdapter == null || isFirstLoad) {
                                setupListViewAdapter(result);

                            } else
                            {

                                mAdapter.updateSalesByStoreDetails(result);
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

    @Override
    public void onItemClick(AdapterView<?> listView, View view, int position,
                            long id) {
        int positionInAgent = position ; // update header position
        SalesByAgentDetails.AgentInfo agentInfo = (SalesByAgentDetails.AgentInfo) listView.getItemAtPosition(position);
        UniRequest.agent=agentInfo.SochenC.toString();
        if (agentInfo != null) {
            SaleByAgentDetailsActivity.startActivity(getActivity(),
                    positionInAgent, mAdapter.getDataList(),
                    mCurrentCalendar.getTime(), mDateViewType);
        }
    }

    @Override
    public void onPrevButtonClick(PrevNextNavigationView view) {
        int calendarField = Calendar.DAY_OF_MONTH;
        switch (mDateViewType){
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
        updateNavigationViewTitle();
        loadData(null, true);
    }

    @Override
    public void onNextButtonClick(PrevNextNavigationView view) {
        int calendarField = Calendar.DAY_OF_MONTH;
        switch (mDateViewType){
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
        updateNavigationViewTitle();
        loadData(null, true);
    }

    @Override
    public void onButtonClick(PrevNextNavigationView view) {
        showSelectDatePickerDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:  getActivity().onBackPressed(); break;
        }
    }

    // Classes
    class SalesByAgentAdatpter extends BaseAdapter {
        List<SalesByAgentDetails.AgentInfo> mData;
        Context mContext;
        LayoutInflater mInflater;
        Boolean flag=false;
        Formatter mFormatter;
        SalesByAgentDetails mSalesByAgentDetails;

        public SalesByAgentAdatpter(Context context,
                                    SalesByAgentDetails salesByagentDetails) {
            this.mSalesByAgentDetails = salesByagentDetails;
            this.mData = salesByagentDetails.getAgentListInfo();;
            this.mContext = context;
            this.mInflater = LayoutInflater.from(context);
            this.mFormatter = Formatter.getInstance(context);
        }

        public List<SalesByAgentDetails.AgentInfo> getDataList() {
            return mData;
        }

        public void updateSalesByStoreDetails(
                SalesByAgentDetails salesByagentDetails) {
            this.mSalesByAgentDetails = salesByagentDetails;
            mData = salesByagentDetails.getAgentListInfo();
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if (mData != null)
                return mData.size();

            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mData != null)
                return mData.get(position);

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
            Object item = mData.get(position);
            if (item != null) {
                SalesByAgentDetails.AgentInfo agentInfo = (SalesByAgentDetails.AgentInfo) item;
//                if (agentInfo == null || agentInfo.SochenNm.equals("") && agentInfo.Scm == 0.000) {
//                } else {
                if(agentInfo.Scm==0.0)
                    flag=true;
                    if (convertView == null) {
                        convertView = mInflater.inflate(R.layout.sales_by_agent_list_item, null);
                        tag = new Tag();
                        tag.textView1 = (TextView) convertView.findViewById(R.id.textView1);
                        tag.textView2 = (TextView) convertView.findViewById(R.id.textView2);
                        tag.textView3 = (TextView) convertView.findViewById(R.id.textView3);
                        convertView.setTag(tag);

                    } else {
                        tag = (Tag) convertView.getTag();
                    }


                    //   sum percent

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
   //         }
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
