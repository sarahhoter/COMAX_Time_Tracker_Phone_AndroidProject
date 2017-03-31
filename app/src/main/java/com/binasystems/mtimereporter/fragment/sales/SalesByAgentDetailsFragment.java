package com.binasystems.mtimereporter.fragment.sales;

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
import com.binasystems.mtimereporter.activity.sales.SaleByDepartmentActivity;
import com.binasystems.mtimereporter.api.Callback;
import com.binasystems.mtimereporter.api.ComaxApiManager;
import com.binasystems.mtimereporter.customview.PrevNextNavigationView;
import com.binasystems.mtimereporter.customview.PrevNextNavigationView.OnPrevNextNavigateViewListener;
import com.binasystems.mtimereporter.dialog.CustomDatePickerDialog;
import com.binasystems.mtimereporter.objects.SalesByAgentDetails;
import com.binasystems.mtimereporter.objects.SalesByAgentDetailsSales;
import com.binasystems.mtimereporter.utils.Formatter;
import com.binasystems.mtimereporter.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SuppressLint("SimpleDateFormat")
public class SalesByAgentDetailsFragment extends BaseFragment implements
        OnPrevNextNavigateViewListener, OnItemClickListener {

        public static final String EXTRA_STORE_CODE = "EXTRA_STORE_CODE";
        public static final String EXTRA_ARRAY_POSITION = "EXTRA_ARRAY_POSITION";
        public static final String EXTRA_ARRAY_DATA = "EXTRA_ARRAY_DATA";
        public static final String EXTRA_CURRENT_DATE = "EXTRA_CURRENT_DATE";
        public static String EXTRA_DATE = "EXTRA_DATE";
        ListView mListView;
        Calendar mCurrentCalendar = Calendar.getInstance();
        SalesByAgentDetailsAdatpter mAdapter;
        CustomDatePickerDialog mDatePickerDialog;
        PrevNextNavigationView mNavigationByDepView;
        Double totalsum;
        ComaxApiManager.DateViewType mDateViewType;
        List<SalesByAgentDetails.AgentInfo> mAgentInfoList = new ArrayList<SalesByAgentDetails.AgentInfo>();
        Formatter mFormatter;
        private TextView date = null;
        private TextView total;
        private TextView contribution;
        private ImageView imc;
        private ImageView imt;
        private ImageView imdate;
        String Sort = null;
        Boolean IsSortDesc=true;
        // store sales details for all stores
        View mListHeaderView;
        View header_view;
        int mAgentPosition = 0;
        String mStoreCode;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            totalsum=0.0;
            mFormatter = Formatter.getInstance(getActivity());
            Bundle args = getArguments();
            if (args != null) {
                if (args.containsKey(EXTRA_DATE))
                    mCurrentCalendar.setTime((Date) args.get(EXTRA_DATE));
                mDateViewType = (ComaxApiManager.DateViewType) args.get(EXTRA_DATE_VIEW_TYPE);
                if (mDateViewType == null)
                    mDateViewType = ComaxApiManager.DateViewType.VIEW_BY_DAY;
            }
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
            if (id == R.id.home) {
                getActivity().finish();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
        public void setParms(List<SalesByAgentDetails.AgentInfo> data){

            mAgentInfoList=data;
        }
        @Override
        public View onCreateView(LayoutInflater inflater,
                                 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            return inflater.inflate(R.layout.fragment_sales_by_agent_details, null);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            getExtras();
            setupUI(view);
            loadData(null, true,null);
        }
        private void setupListViewAdapter(SalesByAgentDetailsSales result) {
            mListView.setAdapter(null);
            totalsum=0.0;
            mListView.removeHeaderView(mListHeaderView);
            if (result.getAgentListInfo().size() > 0) {
                mListView.setVisibility(View.VISIBLE);
                header_view.setVisibility(View.VISIBLE);
                mAdapter = new SalesByAgentDetailsAdatpter(getActivity(), result);
                mListView.setAdapter(mAdapter);
            }
            else {
                mListView.setVisibility(View.GONE);
                header_view.setVisibility(View.GONE);
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
                        loadData(null, true, null);
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

        CountDownTimer countDownTimer = new CountDownTimer(180000, 180000) {

            public void onTick(long millisUntilFinished) {
        }
            public void onFinish() {
            loadData(null,false,null);

        }
        }.start();

        private void setupUI(View view) {
            header_view = view.findViewById(R.id.sales_by_dep_header_view);
            mListView = (ListView) view.findViewById(R.id.listView);
            date = (TextView)header_view.findViewById(R.id.date);
            total = (TextView)header_view.findViewById(R.id.total);
            contribution = (TextView)header_view.findViewById(R.id.contribution);
            imt=(ImageView) header_view.findViewById(R.id.imt);
            imc=(ImageView) header_view.findViewById(R.id.imc);
            imdate=(ImageView) header_view.findViewById(R.id.imdate);
            mListView.setOnItemClickListener(this);
            mNavigationByDepView = new PrevNextNavigationView(view.findViewById(R.id.navigationByStoreView));
            mNavigationByDepView.setViewBackgroundColor(0xFF7F7F7F);
            total.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(imt.getVisibility()== View.VISIBLE) {
                        if(imt.getTag().equals("down")) {
                            imt.setTag("up");
                            Sort = "Scm";
                            IsSortDesc = false;
                            loadData(null, false, null);
                            imt.setImageResource(R.drawable.upn);
                        }
                        else {
                            imt.setTag("down");
                            Sort = "Scm";
                            IsSortDesc = true;
                            imt.setImageResource(R.drawable.down);
                            loadData(null, false, null);
                        }
                    }
                    else {
                        if (imdate.getVisibility() == View.VISIBLE)
                            imdate.setVisibility(View.INVISIBLE);
                        imc.setVisibility(View.INVISIBLE);
                        imt.setImageResource(R.drawable.upn);
                        imt.setVisibility(View.VISIBLE);
                        imt.setTag("up");
                        IsSortDesc=false;
                        Sort = "Scm";
                        loadData(null, false, null);
                    }
                }
            });
            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (imdate.getVisibility() == View.VISIBLE) {
                        if(imdate.getTag().equals("down")) {
                            imdate.setTag("up");
                            IsSortDesc = false;
                            Sort = "DateDoc";
                            loadData(null, false, null);
                            imdate.setImageResource(R.drawable.upn);
                        }
                        else {
                            imdate.setTag("down");
                            IsSortDesc = true;
                            Sort = "DateDoc";
                            imdate.setImageResource(R.drawable.down);
                            loadData(null, false, null);
                        }
                    } else {
                        if (imc.getVisibility() == View.VISIBLE)
                            imc.setVisibility(View.INVISIBLE);
                        imt.setVisibility(View.INVISIBLE);
                        imdate.setImageResource(R.drawable.upn);
                        imdate.setVisibility(View.VISIBLE);
                        imdate.setTag("up");
                        IsSortDesc=false;
                        Sort = "DateDoc";
                        loadData(null, false, null);
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
                            loadData(null, false, null);
                            imc.setImageResource(R.drawable.upn);
                        }
                        else {
                            imc.setTag("down");
                            Sort = "Total_ScmM";
                            IsSortDesc = true;
                            imc.setImageResource(R.drawable.down);
                            loadData(null, false, null);
                        }
                    } else {
                        if (imdate.getVisibility() == View.VISIBLE)
                            imdate.setVisibility(View.INVISIBLE);
                        imt.setVisibility(View.INVISIBLE);
                        imc.setImageResource(R.drawable.upn);
                        imc.setVisibility(View.VISIBLE);
                        imc.setTag("up");
                        IsSortDesc=false;
                        Sort = "Total_ScmM";
                        loadData(null, false, null);
                    }
                }
            });

            mNavigationByDepView.setListener(this);
            updateNavigationByAgentTitle();
            updateNavigationViewTitle();
        }

    private String getCurrentStoreCode() {

        if (mAgentInfoList != null && mAgentInfoList.size() > 0) {
            int pos = 0;
            if (mAgentPosition > 0 && mAgentPosition < mAgentInfoList.size()) {
                pos = mAgentPosition;
            }
            return "" + mAgentInfoList.get(pos).SochenC;
        }

        if (mStoreCode != null) {
            return mStoreCode;
        }

        return null;
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

        }

        private void loadData(String lastStoreCode, final boolean isFirstLoad, String agentNm) {
            countDownTimer.cancel();
            showProgress();
            ComaxApiManager api = ComaxApiManager.newInstance();
            api. requestSaleByAgentDetails(String.valueOf(IsSortDesc),Sort, mCurrentCalendar.getTime(), mDateViewType, lastStoreCode,agentNm,
                    new Callback<SalesByAgentDetailsSales>() {

                        @Override
                        public void onSuccess(SalesByAgentDetailsSales result) {
                            hideProgress();
                            if (getActivity() != null) {
                                setupListViewAdapter(result);
                                mAdapter.updateSalesByAgentDetails(result);
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
    private void getExtras() {
        Bundle args = getArguments();
        if (args != null) {

            if (args.containsKey(EXTRA_ARRAY_DATA)) {
                mAgentInfoList = (ArrayList<SalesByAgentDetails.AgentInfo>) args.get(EXTRA_ARRAY_DATA);
            }

            if (args.containsKey(EXTRA_ARRAY_POSITION)) {
                mAgentPosition = args.getInt(EXTRA_ARRAY_POSITION, 0);
            }

            if (args.containsKey(EXTRA_CURRENT_DATE)) {
                mCurrentCalendar.setTime((Date) args.get(EXTRA_CURRENT_DATE));
            }

            mStoreCode = args.getString(EXTRA_STORE_CODE);
        }
    }
        @Override
        public void onItemClick(AdapterView<?> listView, View view, int position,
                                long id) {



        }

    private void updateNavigationByAgentTitle() {

        if (mAgentInfoList != null && mAgentInfoList.size() > 0) {

            // update store position
            if (mAgentPosition >= mAgentInfoList.size()) {
                mAgentPosition = 0;
            }

            // update state buttons
            boolean prevButtonVisible = true;
            boolean nextButtonVisible = true;

            if (mAgentPosition == 0) {
                prevButtonVisible = false;
            }
            if (mAgentPosition == mAgentInfoList.size() - 1) {
                nextButtonVisible = false;
            }
            mNavigationByDepView.setPrevNextButtonsVisible(prevButtonVisible, nextButtonVisible);
            String title;
            if(mAgentInfoList.get(mAgentPosition).SochenC==0) title ="";
            else title = mAgentInfoList.get(mAgentPosition).SochenNm;
            mNavigationByDepView.setTitle(mFormatter.formatValue(title));
        } else {

            mNavigationByDepView.setTitle("");
        }
    }

        @Override
        public void onPrevButtonClick(PrevNextNavigationView view) {

            if (view == mNavigationByDepView) {
                if (mAgentPosition > 0) {
                    mAgentPosition--;
                    updateNavigationByAgentTitle();
                    loadData("" + mAgentInfoList.get(mAgentPosition).SochenC,false,""+ mAgentInfoList.get(mAgentPosition).SochenC.toString());
                }


                loadData(getCurrentStoreCode(), false, ""+ mAgentInfoList.get(mAgentPosition).SochenC.toString());
            }
        }
        @Override
        public void onNextButtonClick(PrevNextNavigationView view) {
                if (view == mNavigationByDepView) {
                    if (mAgentPosition < mAgentInfoList.size() - 1) {
                        mAgentPosition++;
                        updateNavigationByAgentTitle();
                        loadData("" + mAgentInfoList.get(mAgentPosition).SochenC,false,"" + mAgentInfoList.get(mAgentPosition).SochenC.toString());
                    }
                    loadData(getCurrentStoreCode(), false,""+ mAgentInfoList.get(mAgentPosition).SochenC.toString());
                }
        }

        @Override
        public void onButtonClick(PrevNextNavigationView view) {
            if (view==mNavigationByDepView){
                SaleByDepartmentActivity.startAcitivty(getActivity(),
                        null, ComaxApiManager.DateViewType.VIEW_BY_DAY);

            }

        }

        // Classes
        class SalesByAgentDetailsAdatpter extends BaseAdapter {
            List<SalesByAgentDetailsSales.AgentInfoSale> mData;
            Context mContext;
            LayoutInflater mInflater;
            Boolean flag=false;
            Formatter mFormatter;
            SalesByAgentDetailsSales mSalesByAgentDetails;

            public SalesByAgentDetailsAdatpter(Context context,
                                        SalesByAgentDetailsSales salesByAgentDetails) {
                this.mSalesByAgentDetails = salesByAgentDetails;
                this.mData = salesByAgentDetails.getAgentListInfo();
                this.mContext = context;
                this.mInflater = LayoutInflater.from(context);
                this.mFormatter = Formatter.getInstance(context);
            }

            public List<SalesByAgentDetailsSales.AgentInfoSale> getDataList() {
                return mData;
            }

            public void updateSalesByAgentDetails(
                    SalesByAgentDetailsSales salesByAgentDetails) {
                this.mSalesByAgentDetails = salesByAgentDetails;
                mData = salesByAgentDetails.getAgentListInfo();
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
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.sales_by_agent_details_list_item, null);
                    tag = new Tag();
                    tag.textView0 = (TextView) convertView.findViewById(R.id.textView0);
                    tag.textView1 = (TextView) convertView.findViewById(R.id.textView1);
                    tag.textView2 = (TextView) convertView.findViewById(R.id.textView2);
                    tag.textView3 = (TextView) convertView.findViewById(R.id.textView3);
                    convertView.setTag(tag);

                } else {
                    tag = (Tag) convertView.getTag();
                }

                Object item = mData.get(position);
                if (item != null) {
                    SalesByAgentDetailsSales.AgentInfoSale agentInfoSale = (SalesByAgentDetailsSales.AgentInfoSale) item;
                    // sum percent
                    tag.textView0.setText(mFormatter.formatValueTowDig(agentInfoSale.Total_ScmM));
                    if(agentInfoSale.Total_ScmM<0.0)
                        tag.textView0.setTextColor(Color.parseColor("#FF0000"));
                    else
                        tag.textView0.setTextColor(Color.parseColor("#A9A9A9"));
                    tag.textView1.setText(mFormatter.formatValue(agentInfoSale.Scm));
                    if(agentInfoSale.Scm<0.0)
                        tag.textView1.setTextColor(Color.parseColor("#FF0000"));
                    else
                        tag.textView1.setTextColor(Color.parseColor("#A9A9A9"));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    Date convertedDate = new Date();
                    try {
                        convertedDate = dateFormat.parse((agentInfoSale.DateDoc).trim());
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    SimpleDateFormat dateFormatt = new SimpleDateFormat("dd/MM/yyyy");

                    tag.textView2.setText((agentInfoSale.DayDateDoc).trim());
                    tag.textView3.setText(dateFormatt.format(convertedDate));

                }

                return convertView;
            }

            class Tag {
                TextView textView0;
                TextView textView1;
                TextView textView2;
                TextView textView3;
            }
        }
    }

