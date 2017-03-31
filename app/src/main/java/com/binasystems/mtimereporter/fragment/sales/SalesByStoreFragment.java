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
import com.binasystems.mtimereporter.activity.sales.SaleByStoreDetailsActivity;
import com.binasystems.mtimereporter.api.Callback;
import com.binasystems.mtimereporter.api.ComaxApiManager;
import com.binasystems.mtimereporter.customview.PrevNextNavigationView;
import com.binasystems.mtimereporter.customview.PrevNextNavigationView.OnPrevNextNavigateViewListener;
import com.binasystems.mtimereporter.dialog.CustomDatePickerDialog;
import com.binasystems.mtimereporter.objects.Branch;
import com.binasystems.mtimereporter.objects.SalesByStoreDetails;
import com.binasystems.mtimereporter.objects.SalesByStoreDetails.StoreInfo;
import com.binasystems.mtimereporter.utils.Formatter;
import com.binasystems.mtimereporter.utils.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SuppressLint("SimpleDateFormat")
public class SalesByStoreFragment extends BaseFragment implements
        OnPrevNextNavigateViewListener, OnItemClickListener{

    public static String EXTRA_DATE = "EXTRA_DATE";
    ListView mListView;
    Calendar mCurrentCalendar = Calendar.getInstance();
    SalesByStoreAdatpter mAdapter;
    CustomDatePickerDialog mDatePickerDialog;
    PrevNextNavigationView mByDateNavigationView;
    ComaxApiManager.DateViewType mDateViewType;
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
        countDownTimer.cancel();
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
        countDownTimer.start();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_sales_by_store, null);

    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI(view);
        loadData(null, true);



    }
    private void setupListViewAdapter(SalesByStoreDetails result) {
        mListView.setAdapter(null);
        if (result.getStoreListInfo().size() > 0) {
            mListView.setVisibility(View.VISIBLE);
            header_view.setVisibility(View.VISIBLE);
            // Update header view
            Double total = result.getSalesInfo().TotalHours;
            Double other = result.getSalesInfo().TotalOther;
            Double credit = result.getSalesInfo().TotalHufsha;
            Double cash = result.getSalesInfo().TotalAvoda;
            if (total != null && total != 0) {
                if (other != null) {
                    other = other / total;
                }

                if (credit != null) {
                    credit = credit / total;
                }
                if (cash != null) {
                    cash = cash / total;
                }
            }
            Formatter formatter = Formatter.getInstance(getActivity());
            TextView t1;
            TextView t3;
            TextView t4;
            // TODO string
            // total
            t1 = ((TextView) header_view.findViewById(R.id.textView_total));
            t3 = ((TextView) header_view.findViewById(R.id.textView2));
            t4 = ((TextView) header_view.findViewById(R.id.textView3));
            t1.setText(formatter.formatValue(String.format("%1$,.2f", result.getSalesInfo().TotalHours) + " " + getResources().getString(R.string.total).toString()));

            // other
            if (other != 0.0) {
                ((TextView) header_view.findViewById(R.id.textView1)).setText(" אחר " + formatter.formatPercent(other)
                );
                if (other < 0.0)
                    ((TextView) header_view.findViewById(R.id.textView1)).setTextColor(Color.parseColor("#FF0000"));
                else
                    ((TextView) header_view.findViewById(R.id.textView1)).setTextColor(Color.parseColor("#A9A9A9"));

            } else {
                ((TextView) header_view.findViewById(R.id.textView1)).setText("");
            }
            // credit
            if (credit != 0.0) {
                t3.setText(String.format("חופשה %s",
                        formatter.formatValueD(credit)));
                if (credit < 0.0)
                    t3.setTextColor(Color.parseColor("#FF0000"));
                else
                    t3.setTextColor(Color.parseColor("#A9A9A9"));

            } else {
                t3.setText("");
            }
            // cash
            if (cash != 0.0) {
                t4.setText(String.format("עבודה %s",
                        formatter.formatValueD(cash)));
                if (cash < 0.0)
                    t4.setTextColor(Color.parseColor("#FF0000"));
                else
                    t4.setTextColor(Color.parseColor("#A9A9A9"));
            } else {
                t4.setText("");
            }

            mAdapter = new SalesByStoreAdatpter(getActivity(), result);
            mListView.setAdapter(mAdapter);
        } else {


            mListView.setVisibility(View.GONE);
            header_view.setVisibility(View.GONE);
        }

//        getView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                loadData(null,false);
//                // Do something after 1000 ms
//            }
//        }, 1000);
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

    CountDownTimer countDownTimer = new CountDownTimer(180000, 180000) {
        public void onTick(long millisUntilFinished) {
        }
        public void onFinish() {
            loadData(null,false);
        }
    }.start();


    private void setupUI(View view) {
        header_view = view.findViewById(R.id.sales_by_store_header_view);
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
                if(imt.getVisibility()== View.VISIBLE) {
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
                        //Sort = "Scm";
                        IsSortDesc = false;
                        loadData(null, false);
                        imb.setImageResource(R.drawable.upn);
                    }
                    else {
                        imb.setTag("down");
                        //Sort ="  desc";
                        IsSortDesc = true;
                        imb.setImageResource(R.drawable.down);
                        loadData(null, false);
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
                        imc.setImageResource(R.drawable.down);
                        loadData(null, false);
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
        api.requestSaleByStore(String.valueOf(IsSortDesc),Sort, mCurrentCalendar.getTime(), mDateViewType, lastStoreCode,
                new Callback<SalesByStoreDetails>() {

                    @Override
                    public void onSuccess(SalesByStoreDetails result) {
                        hideProgress();
                        if (getActivity() != null) {
                            if (mAdapter == null || isFirstLoad) {
                                setupListViewAdapter(result);

                            } else {
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
        int positionInStore = position ; // update header position
        StoreInfo storeInfo = (StoreInfo) listView.getItemAtPosition(position);
        //UniRequest.store =branch;
        /*if (storeInfo != null) {
            SaleByStoreDetailsActivity.startActivity(getActivity(),
                    positionInStore, mAdapter.getDataList(),
                    mCurrentCalendar.getTime(), mDateViewType);
        }*/
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
//                if(storeInfo.StoreNm==null||storeInfo.StoreNm.equals("")&&storeInfo.Scm==0.000) {
//                    if (convertView != null) {
//                        tag = (Tag) convertView.getTag();
//                    }
//                }
//                else {
                if(storeInfo.Scm==0.0)
                    flag=true;
                    if (convertView == null||convertView.getTag()==null) {

                           // convertView=mInflater.inflate(null, null);
                        convertView = mInflater.inflate(R.layout.sales_by_store_list_item, null);
                        tag = new Tag();
                        tag.textView1 = (TextView) convertView.findViewById(R.id.textView1);
                        tag.textView2 = (TextView) convertView.findViewById(R.id.textView2);
                        tag.textView3 = (TextView) convertView.findViewById(R.id.textView3);
                        convertView.setTag(tag);

                    } else {
                       // if(storeInfo.Scm==0.0)
                            //convertView=mInflater.inflate(null, null);
                        tag = (Tag) convertView.getTag();
                    }


                    // sum percent
                    Double totalSumInPercent = null;
                    double totalSales = mSalesByStoreDetails.getSalesInfo().TotalHours != null ? mSalesByStoreDetails
                            .getSalesInfo().TotalHours : 0d;
                    if (storeInfo.Scm != null && totalSales != 0) {
                        totalSumInPercent = storeInfo.Scm / totalSales;

                    }
                    if (totalSumInPercent != null) {
                        tag.textView1.setText(mFormatter.formatValueD(totalSumInPercent));
                        if (totalSumInPercent < 0.0)
                            tag.textView1.setTextColor(Color.parseColor("#FF0000"));
                        else
                            tag.textView1.setTextColor(Color.parseColor("#A9A9A9"));
                    } else tag.textView1.setText("");
                    tag.textView2.setText(mFormatter.formatValue(storeInfo.Scm));
                    if (storeInfo.Scm < 0.0)
                        tag.textView2.setTextColor(Color.parseColor("#FF0000"));
                    else
                        tag.textView2.setTextColor(Color.parseColor("#A9A9A9"));
                    tag.textView3.setText(mFormatter.formatValue(storeInfo.D));
                    if (position == getCount() - 1
                            && mSalesByStoreDetails.getHasMoreRows()) {

                        //load next data
                        loadData(String.valueOf(storeInfo.D), false);
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
