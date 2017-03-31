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
import android.widget.ListView;
import android.widget.TextView;

import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.activity.BaseFragment;
import com.binasystems.mtimereporter.activity.sales.SaleByDepartmentActivity;
import com.binasystems.mtimereporter.activity.sales.SaleByStoreDetailsActivity;
import com.binasystems.mtimereporter.api.Callback;
import com.binasystems.mtimereporter.api.ComaxApiManager;
import com.binasystems.mtimereporter.customview.PrevNextNavigationView;
import com.binasystems.mtimereporter.customview.PrevNextNavigationView.OnPrevNextNavigateViewListener;
import com.binasystems.mtimereporter.dialog.CustomDatePickerDialog;
import com.binasystems.mtimereporter.objects.Branch;
import com.binasystems.mtimereporter.objects.SalesByDepDetails.DepInfo;
import com.binasystems.mtimereporter.objects.SalesByStoreDetails;
import com.binasystems.mtimereporter.objects.SalesByStoreDetails.StoreInfo;
import com.binasystems.mtimereporter.utils.Formatter;
import com.binasystems.mtimereporter.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SuppressLint("SimpleDateFormat")
public class SalesByDepartDetailsFragment extends BaseFragment implements
        OnPrevNextNavigateViewListener, OnItemClickListener {

        public static final String EXTRA_STORE_CODE = "EXTRA_STORE_CODE";
        public static final String EXTRA_ARRAY_POSITION = "EXTRA_ARRAY_POSITION";
        public static final String EXTRA_ARRAY_DATA = "EXTRA_ARRAY_DATA";

        public static final String EXTRA_CURRENT_DATE = "EXTRA_CURRENT_DATE";
        public static String EXTRA_DATE = "EXTRA_DATE";
        ListView mListView;
        Calendar mCurrentCalendar = Calendar.getInstance();
        SalesByStoreAdatpter mAdapter;
        CustomDatePickerDialog mDatePickerDialog;
        PrevNextNavigationView mByDateNavigationView;
        PrevNextNavigationView mNavigationByDepView;
        View header;
        private  Double totalsum;
        ComaxApiManager.DateViewType mDateViewType;
        List<DepInfo> mDepInfoList = new ArrayList<DepInfo>();
        Formatter mFormatter;
        private TextView branch = null;
        private TextView total;
        private TextView contribution;
        private ImageView imc;
        private ImageView imt;
        private ImageView imb;
        String Sort = "Scm";
        Boolean IsSortDesc=true;
        // store sales details for all stores
        View mListHeaderView;
        View header_view;
        int mDepPosition = 0;
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
                if (mDateViewType == null) {
                    mDateViewType = ComaxApiManager.DateViewType.VIEW_BY_DAY;
                }
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

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            return inflater.inflate(R.layout.fragment_sales_by_dep_details, null);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            getExtras();
            setupUI(view);
            loadData(null, true,null);
        }
        private void setupListViewAdapter(SalesByStoreDetails result) {
            mListView.setAdapter(null);
            totalsum=0.0;
            mListView.removeHeaderView(mListHeaderView);
            if (result.getStoreListInfo().size() > 0) {
                mListView.setVisibility(View.VISIBLE);
                header_view.setVisibility(View.VISIBLE);
                for (StoreInfo storeInfo:result.getStoreListInfo())
                {
                    totalsum+=storeInfo.Scm;
                }

                Formatter formatter = Formatter.getInstance(getActivity());
                TextView t1;
                t1=((TextView) header_view.findViewById(R.id.textView_total));
                t1.setText(formatter.formatValue(String.format("%1$,d",Math.round(totalsum)) + " " + getResources().getString(R.string.nis).toString()));
                mAdapter = new SalesByStoreAdatpter(getActivity(), result);
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
            TextView t=(TextView) header_view.findViewById(R.id.branch);
            t.setHint(R.string.branch);

            mListView = (ListView) view.findViewById(R.id.listView);
            branch = (TextView)header_view.findViewById(R.id.branch);
            total = (TextView)header_view.findViewById(R.id.total);
            contribution = (TextView)header_view.findViewById(R.id.contribution);
            imb=(ImageView) header_view.findViewById(R.id.imb);
            imt=(ImageView) header_view.findViewById(R.id.imt);
            imc=(ImageView) header_view.findViewById(R.id.imc);

            mByDateNavigationView = new PrevNextNavigationView(
                    view.findViewById(R.id.navigationByDateView));
           header=view.findViewById(R.id.navigationByDateView);
           header.findViewById(R.id.navigation_buttonPrev).setVisibility(View.INVISIBLE);
            header.findViewById(R.id.navigation_buttonNext).setVisibility(View.INVISIBLE);
            // set listeners
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
                        if (imb.getVisibility() == View.VISIBLE)
                            imb.setVisibility(View.INVISIBLE);
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
            branch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (imb.getVisibility() == View.VISIBLE) {
                        if(imb.getTag().equals("down")) {
                            imb.setTag("up");
                            IsSortDesc = false;
                            loadData(null, false, null);
                            imb.setImageResource(R.drawable.upn);
                        }
                        else {
                            imb.setTag("down");
                            IsSortDesc = true;
                            imb.setImageResource(R.drawable.down);
                            loadData(null, false, null);
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
                            Sort = "Scm";
                            IsSortDesc = false;
                            loadData(null, false, null);
                            imc.setImageResource(R.drawable.upn);
                        }
                        else {
                            imc.setTag("down");
                            Sort = "AczTr";
                            IsSortDesc = true;

                            imc.setImageResource(R.drawable.down);
                            loadData(null, false, null);
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
                        loadData(null, false, null);
                    }
                }
            });
            // setup aciton bar
            ActionBar actionBar = getActivity().getActionBar();
            if (actionBar != null) {
                actionBar.setTitle(TimeTrackerApplication.getInstance().getBranch().getName());
                actionBar.setDisplayShowHomeEnabled(false);
            }
            mNavigationByDepView.setListener(this);
            mByDateNavigationView.setListener(this);
            updateNavigationByStoreTitle();
            updateNavigationViewTitle();
        }
    private String getCurrentStoreCode() {

        if (mDepInfoList != null && mDepInfoList.size() > 0) {
            int pos = 0;
            if (mDepPosition > 0 && mDepPosition < mDepInfoList.size()) {
                pos = mDepPosition;
            }

            return "" + mDepInfoList.get(pos).Ind_DepC;
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
            mByDateNavigationView.setPrevNextButtonsVisible(prevButtonVisible, nextButtonVisible);
            mByDateNavigationView.setTitle(formatedTitle);
        }

        private void loadData(String lastStoreCode, final boolean isFirstLoad, String depNm) {
            countDownTimer.cancel();
            showProgress();

            ComaxApiManager api = ComaxApiManager.newInstance();
            api. requestStoreByDep(String.valueOf(IsSortDesc),Sort, mCurrentCalendar.getTime(), mDateViewType, lastStoreCode,depNm,
                    new Callback<SalesByStoreDetails>() {

                        @Override
                        public void onSuccess(SalesByStoreDetails result) {
                            hideProgress();
                            if (getActivity() != null) {
                                setupListViewAdapter(result);
                                if(mAdapter!=null)
                                mAdapter.updateSalesByStoreDetails(result);
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
                mDepInfoList = (ArrayList<DepInfo>) args.get(EXTRA_ARRAY_DATA);
            }

            if (args.containsKey(EXTRA_ARRAY_POSITION)) {
                mDepPosition = args.getInt(EXTRA_ARRAY_POSITION, 0);
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
            // open store details
            /*int positionInStore = position ; // update header position
            StoreInfo storeInfo = (StoreInfo) listView.getItemAtPosition(position);
            Branch branch=new Branch();
            branch.setC(storeInfo.StoreC.toString());
            branch.setName(storeInfo.StoreNm);
            branch.setCode(storeInfo.StoreKod.toString());
            TimeTrackerApplication.getInstance().setStore(branch);
            if (storeInfo != null) {
                SaleByStoreDetailsActivity.startActivity(getActivity(),
                        positionInStore, mAdapter.getDataList(),
                        mCurrentCalendar.getTime(), mDateViewType);
            }*/
        }
    private void updateNavigationByStoreTitle() {

        if (mDepInfoList != null && mDepInfoList.size() > 0) {

            // update store position
            if (mDepPosition >= mDepInfoList.size()) {
                mDepPosition = 0;
            }

            // update state buttons
            boolean prevButtonVisible = true;
            boolean nextButtonVisible = true;
            if (mDepPosition == 0) {
                prevButtonVisible = false;
            }
            if (mDepPosition == mDepInfoList.size() - 1) {
                nextButtonVisible = false;
            }
            mNavigationByDepView.setPrevNextButtonsVisible(prevButtonVisible, nextButtonVisible);

            // update title
            String title = mDepInfoList.get(mDepPosition).Ind_DepNm;
            mNavigationByDepView.setTitle(mFormatter.formatValue(title));
        } else {

            mNavigationByDepView.setTitle("");
        }
    }

        @Override
        public void onPrevButtonClick(PrevNextNavigationView view) {

            if (view == mNavigationByDepView) {
                if (mDepPosition > 0) {
                    mDepPosition--;
                    updateNavigationByStoreTitle();
                    loadData("" + mDepInfoList.get(mDepPosition).Ind_DepC,false,""+ mDepInfoList.get(mDepPosition).Ind_DepC.toString());
                }


                loadData(getCurrentStoreCode(), false, ""+ mDepInfoList.get(mDepPosition).Ind_DepC.toString());
            }
        }
        @Override
        public void onNextButtonClick(PrevNextNavigationView view) {
                if (view == mNavigationByDepView) {
                    if (mDepPosition < mDepInfoList.size() - 1) {
                        mDepPosition++;
                        updateNavigationByStoreTitle();
                        loadData("" + mDepInfoList.get(mDepPosition).Ind_DepC,false,"" + mDepInfoList.get(mDepPosition).Ind_DepC.toString());
                    }
                    loadData(getCurrentStoreCode(), false,""+ mDepInfoList.get(mDepPosition).Ind_DepC.toString());
                }
        }

        @Override
        public void onButtonClick(PrevNextNavigationView view) {
            if (view==mNavigationByDepView){
                SaleByDepartmentActivity.startAcitivty(getActivity(),
                        null, ComaxApiManager.DateViewType.VIEW_BY_DAY);

            }
            if(view==mByDateNavigationView)
                showSelectDatePickerDialog();
        }

        // Classes
        class SalesByStoreAdatpter extends BaseAdapter {
            List<StoreInfo> mData;
            Context mContext;
            LayoutInflater mInflater;
            Formatter mFormatter;
            Boolean flag=false;
            SalesByStoreDetails mSalesByStoreDetails;

            public SalesByStoreAdatpter(Context context,
                                        SalesByStoreDetails salesByStoreDetails) {
                this.mSalesByStoreDetails = salesByStoreDetails;
                this.mData = salesByStoreDetails.getStoreListInfo();
                this.mContext = context;
                this.mInflater = LayoutInflater.from(context);
                this.mFormatter = Formatter.getInstance(context);
            }

            public List<StoreInfo> getDataList() {
                return mData;
            }

            public void updateSalesByStoreDetails(
                    SalesByStoreDetails salesByStoreDetails) {
                this.mSalesByStoreDetails = salesByStoreDetails;
                mData = salesByStoreDetails.getStoreListInfo();
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
                    StoreInfo storeInfo = (StoreInfo) item;
//                    if (storeInfo == null || storeInfo.StoreNm.equals("") && storeInfo.Scm == 0.000) {
//                        if (convertView != null) {
//                            tag = (Tag) convertView.getTag();
//                        }
//                    } else {
                    if(storeInfo.Scm==0.00)
                        flag=true;
                        if (convertView == null||convertView .getTag()== null) {
                            convertView = mInflater.inflate(R.layout.sales_by_store_list_item, null);
                            tag = new Tag();
                            tag.textView1 = (TextView) convertView.findViewById(R.id.textView1);
                            tag.textView2 = (TextView) convertView.findViewById(R.id.textView2);
                            tag.textView3 = (TextView) convertView.findViewById(R.id.textView3);
                            convertView.setTag(tag);

                        } else {
                            tag = (Tag) convertView.getTag();
                        }


                        // sum percent
                        Double totalSumInPercent = null;

                        if (storeInfo.Scm != null && totalsum != 0) {
                            totalSumInPercent = storeInfo.Scm / totalsum;

                        }
                        tag.textView1.setText(mFormatter.formatValueD(totalSumInPercent));
                        if (totalSumInPercent < 0.0)
                            tag.textView1.setTextColor(Color.parseColor("#FF0000"));
                        else
                            tag.textView1.setTextColor(Color.parseColor("#A9A9A9"));
                        tag.textView2.setText(mFormatter.formatValue(storeInfo.Scm));
                        if (storeInfo.Scm < 0.0)
                            tag.textView2.setTextColor(Color.parseColor("#FF0000"));
                        else
                            tag.textView2.setTextColor(Color.parseColor("#A9A9A9"));
                        tag.textView3.setText(mFormatter.formatValue(storeInfo.D));
                        if (position == getCount() - 1
                                && mSalesByStoreDetails.getHasMoreRows()) {

                            //load next data
                            loadData(String.valueOf(storeInfo.D), false, mDepInfoList.get(mDepPosition).Ind_DepC.toString());
                        }
                    }
 //               }
                if(flag)
                    return mInflater.inflate(R.layout.null_item, null);
                return convertView;
            }

            class Tag {
                TextView textView1;
                TextView textView2;
                TextView textView3;
            }
        }
    }

