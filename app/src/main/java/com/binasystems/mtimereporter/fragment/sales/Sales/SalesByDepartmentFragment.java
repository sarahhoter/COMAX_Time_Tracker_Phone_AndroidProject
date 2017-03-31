package com.binasystems.mtimereporter.fragment.sales.Sales;

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
import android.widget.ListView;
import android.widget.TextView;

import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.activity.BaseFragment;
import com.binasystems.mtimereporter.activity.sales.SaleByDepDetailsActivity;
import com.binasystems.mtimereporter.api.Callback;
import com.binasystems.mtimereporter.api.ComaxApiManager;
import com.binasystems.mtimereporter.api.requests.UniRequest;
import com.binasystems.mtimereporter.customview.PrevNextNavigationView;
import com.binasystems.mtimereporter.customview.PrevNextNavigationView.OnPrevNextNavigateViewListener;
import com.binasystems.mtimereporter.dialog.CustomDatePickerDialog;
import com.binasystems.mtimereporter.objects.SalesByDepDetails;
import com.binasystems.mtimereporter.objects.SalesByDepDetails.DepInfo;
import com.binasystems.mtimereporter.utils.Formatter;
import com.binasystems.mtimereporter.utils.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SuppressLint("SimpleDateFormat")
public class SalesByDepartmentFragment extends BaseFragment implements
        OnPrevNextNavigateViewListener, OnItemClickListener {

    public static String EXTRA_DATE = "EXTRA_DATE";
    ListView mListView;
    Calendar mCurrentCalendar = Calendar.getInstance();
    SalesByDepAdatpter mAdapter;
    CustomDatePickerDialog mDatePickerDialog;
    PrevNextNavigationView mByDateNavigationView;
    ComaxApiManager.DateViewType mDateViewType;
    private  Double totalsum;
    private  Double sump;
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

        return inflater.inflate(R.layout.fragment_sales_by_dep, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI(view);

        loadData(null, true);
    }
    private void setupListViewAdapter(SalesByDepDetails result) {
        mListView.setAdapter(null);

        if (result.getDepListInfo().size() > 0) {
            totalsum=0.0;
            mListView.setVisibility(View.VISIBLE);
            header_view.setVisibility(View.VISIBLE);
            for (DepInfo depInfo:result.getDepListInfo())
            {

                totalsum+=depInfo.Scm;
            }

         Formatter formatter = Formatter.getInstance(getActivity());
         TextView t1;
         t1=((TextView) header_view.findViewById(R.id.textView_total));
         t1.setText(formatter.formatValue(String.format("%1$,d",Math.round(result.getSalesInfo().TotalSales)) + " " + getResources().getString(R.string.nis).toString()));
         mAdapter = new SalesByDepAdatpter(getActivity(), result);
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
                        if(imt.getTag().equals("up")) {
                            imt.setTag("down");
                            Sort = "Scm";
                            IsSortDesc = true;
                            loadData(null, false);
                            imt.setImageResource(R.drawable.down);
                        }
                        else{
                            imt.setTag("up");
                            Sort = "Scm";
                            IsSortDesc = false;
                            loadData(null, false);
                            imt.setImageResource(R.drawable.upn);
                        }
                    }
                }
                else {
                    if (imb.getVisibility() == View.VISIBLE)
                        imb.setVisibility(View.INVISIBLE);
                    imc.setVisibility(View.INVISIBLE);
                    imt.setImageResource(R.drawable.upn);
                    imt.setTag("up");
                    imt.setVisibility(View.VISIBLE);
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
                        IsSortDesc = false;
                        loadData(null, false);
                        imb.setImageResource(R.drawable.upn);
                    }
                    else {
                        imb.setTag("down");
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
                        Sort = "Scm";
                        IsSortDesc = false;
                        loadData(null, false);
                        imc.setImageResource(R.drawable.upn);
                    }
                    else {
                        imc.setTag("down");
                        Sort = "AczTr";
                        IsSortDesc = true;
                        loadData(null, false);
                        imc.setImageResource(R.drawable.down);
                    }
                } else {
                    if (imb.getVisibility() == View.VISIBLE)
                        imb.setVisibility(View.INVISIBLE);
                    imt.setVisibility(View.INVISIBLE);
                    imc.setImageResource(R.drawable.upn);
                    imc.setVisibility(View.VISIBLE);
                    imc.setTag("up");
                    IsSortDesc=false;
                    Sort = "AczTr";
                    loadData(null, false);
                }
            }
        });
        // setup aciton bar
        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            actionBar.setTitle(TimeTrackerApplication.getInstance().getBranch().getName());
            actionBar.setDisplayShowHomeEnabled(false);
        }

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
        api.requestSaleByDep(String.valueOf(IsSortDesc),Sort, mCurrentCalendar.getTime(), mDateViewType, lastStoreCode,
                new Callback<SalesByDepDetails>() {

                    @Override
                    public void onSuccess(SalesByDepDetails result) {
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
        // open store details
        int positionInStore = position ;
        DepInfo depInfo = (DepInfo) listView.getItemAtPosition(position);
        if(depInfo.Ind_DepC!=null)
            UniRequest.dep=depInfo.Ind_DepC.toString();
        else  UniRequest.dep="0";
        if (depInfo != null) {
            SaleByDepDetailsActivity.startActivity(getActivity(),
                    positionInStore, mAdapter.getDataList(),
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

    // Classes
    class SalesByDepAdatpter extends BaseAdapter {
        List<DepInfo> mData;
        Context mContext;
        Boolean flag=false;
        LayoutInflater mInflater;
        Formatter mFormatter;
        SalesByDepDetails mSalesByStoreDetails;

        public SalesByDepAdatpter(Context context,
                                    SalesByDepDetails salesBydepDetails) {
            this.mSalesByStoreDetails = salesBydepDetails;
            this.mData = salesBydepDetails.getDepListInfo();;
            this.mContext = context;
            this.mInflater = LayoutInflater.from(context);
            this.mFormatter = Formatter.getInstance(context);
        }

        public List<DepInfo> getDataList() {
            return mData;
        }

        public void updateSalesByStoreDetails(
                SalesByDepDetails salesByDepDetails) {
            this.mSalesByStoreDetails = salesByDepDetails;
            mData = salesByDepDetails.getDepListInfo();
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
                DepInfo depInfo = (DepInfo) item;
                if(depInfo.Scm==0.0)
                    flag=true;
//                if (depInfo == null || depInfo.Ind_DepNm.equals("") && depInfo.Scm == 0.000) {
//                } else {
                    if (convertView == null||convertView.getTag() == null) {
                        convertView = mInflater.inflate(R.layout.sales_by_store_list_item, null);
                        tag = new Tag();
                        tag.textView1 = (TextView) convertView.findViewById(R.id.textView1);
                        tag.textView2 = (TextView) convertView.findViewById(R.id.textView2);
                        tag.textView3 = (TextView) convertView.findViewById(R.id.textView3);
                        convertView.setTag(tag);

                    } else {
                        tag = (Tag) convertView.getTag();
                    }


                    //   sum percent
                    sump = 0.0;
                    double totalSales = mSalesByStoreDetails.getSalesInfo().TotalSales != null ? mSalesByStoreDetails
                            .getSalesInfo().TotalSales : 0d;
                    if (depInfo.Scm != null && totalsum != 0)
                        sump = depInfo.Scm / totalsum;
                    tag.textView1.setText(mFormatter.formatValueD(sump));
                    if (sump < 0.0)
                        tag.textView1.setTextColor(Color.parseColor("#FF0000"));
                    else tag.textView1.setTextColor(Color.parseColor("#A9A9A9"));
                    tag.textView2.setText(mFormatter.formatValue(depInfo.Scm));
                    if (depInfo.Scm < 0.0)
                        tag.textView2.setTextColor(Color.parseColor("#FF0000"));
                    else tag.textView2.setTextColor(Color.parseColor("#A9A9A9"));
                    tag.textView3.setText(mFormatter.formatValue(depInfo.Ind_DepNm));

                    if (position == getCount() - 1
                            && mSalesByStoreDetails.getHasMoreRows()) {
                        //load next data
                        loadData(String.valueOf(depInfo.Ind_DepC), false);
                    }
                }
  //          }
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
