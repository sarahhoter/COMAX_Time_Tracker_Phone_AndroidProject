package com.binasystems.mtimereporter.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.NumberPicker;


import com.binasystems.mtimereporter.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Month/Year selection picker dialog
 */
public class CustomDatePickerDialog extends DialogFragment {
    public static final int DISPLAY_FIELD_YEAR  = 0x1;
    public static final int DISPLAY_FIELD_MONTH = 0x2;
    public static final int DISPLAY_FIELD_DAY   = 0x4;
    public static final int DISPLAY_FIELD_WEEK  = 0x8;
    public static final int DISPLAY_FILED_MONTH_YEAR = DISPLAY_FIELD_MONTH | DISPLAY_FIELD_YEAR;
    public static final int DISPLAY_FIELD_ALL = DISPLAY_FIELD_DAY | DISPLAY_FIELD_MONTH | DISPLAY_FIELD_YEAR | DISPLAY_FIELD_WEEK;

    /**
     * Display mode
     */
    public static enum DisplayType{
        DAY, WEEK, MONTH, MONTH_YEAR, YEAR
    }

    // FORMATTERS
    static final SimpleDateFormat FORMAT_YEAR = new SimpleDateFormat("yyyy");
    static final SimpleDateFormat FORMAT_ONLY_MONTH = new SimpleDateFormat("MMM");
    static final SimpleDateFormat FORMAT_MONTH_WITH_YEAR  = new SimpleDateFormat("MMM, yyyy");
    static final SimpleDateFormat FORMAT_DAY = new SimpleDateFormat("EEE, MMM dd, yyyy");
    static final SimpleDateFormat FORMAT_WEEK = new SimpleDateFormat("w");

    static final int MIN_YEAR_VALUE = 1900;
    static final int MAX_YEAR_VALUE = 2036;

    // TODO localize
    static final String[] monthNames = new String[]{
            "Jan", "Feb", "Mar",
            "Apr", "May", "Jun",
            "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec"};

    public static interface OnDateSelectListener {
        public void onDateSelect(CustomDatePickerDialog datePickerDialog, Date date);

        public void onCancel(CustomDatePickerDialog datePickerDialog);
    }

    protected NumberPicker npYear;
    protected NumberPicker npMonth;
    private DatePicker mDatePicker;
    private View mMonthYarContainer;

    private Calendar mCurrentCalendarInstance;
    private int mDisplayFieldMap = DISPLAY_FIELD_ALL;
    private DisplayType mDisplayType = DisplayType.DAY;
    private SimpleDateFormat titleDateFormat = FORMAT_DAY;
    private OnDateSelectListener mOnDateSelectListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View contentView = inflater.inflate(R.layout.dialog_datepicker, null);
        npYear = (NumberPicker) contentView.findViewById(R.id.np_year);
        npMonth = (NumberPicker) contentView.findViewById(R.id.np_month);
        mDatePicker = (DatePicker) contentView.findViewById(R.id.datePicker);
        mMonthYarContainer = contentView.findViewById(R.id.monthYarPicker);

        // setup year picker
        npYear.setMinValue(MIN_YEAR_VALUE);
        npYear.setMaxValue(MAX_YEAR_VALUE);
        npYear.setValue(getCurrentCalendarInstance().get(Calendar.YEAR));
        npYear.setOnValueChangedListener(mOnNumberPickerValueChangeListener);

        // setup month picker
        npMonth.setDisplayedValues(monthNames);
        npMonth.setMinValue(0);
        npMonth.setMaxValue(monthNames.length - 1);
        npMonth.setValue(getCurrentCalendarInstance().get(Calendar.MONTH));
        npMonth.setOnValueChangedListener(mOnNumberPickerValueChangeListener);

        mDatePicker.init(getCurrentCalendarInstance().get(Calendar.YEAR),
                getCurrentCalendarInstance().get(Calendar.MONTH),
                getCurrentCalendarInstance().get(Calendar.DAY_OF_MONTH),
                mOnDateChangeListener);

        // show/hide components
        setupInternalPicker();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(contentView);
        builder.setTitle("Select date");
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mOnDateSelectListener != null) {
                    mOnDateSelectListener.onDateSelect(CustomDatePickerDialog.this, getCurrentCalendarInstance().getTime());
                }
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mOnDateSelectListener != null) {
                    mOnDateSelectListener.onCancel(CustomDatePickerDialog.this);
                }

                dialogInterface.dismiss();
            }
        });

        return builder.create();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //to hide keyboard when showing dialog fragment
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public OnDateSelectListener getOnDateSelectListener() {
        return mOnDateSelectListener;
    }

    public void setOnDateSelectListener(OnDateSelectListener mOnDateSelectListener) {
        this.mOnDateSelectListener = mOnDateSelectListener;
    }

    public void setDate(Date date) {
        getCurrentCalendarInstance().setTime(date);
    }

    public void setMinAllowedDate(Date date) {
        // TODO implemetn
    }

    public void setMaxAllowedDate(Date date) {
        // TODO implemetn
    }

    public DisplayType getmDisplayType() {
        return mDisplayType;
    }

    public void setmDisplayType(DisplayType mDisplayType) {
        this.mDisplayType = mDisplayType;
    }

    void setWidgetFieldsVisible(boolean visibleCalendar, boolean visibleMonthPicker, boolean visibleYearPicker){

        if(visibleCalendar){
            mMonthYarContainer.setVisibility(View.GONE);
            mDatePicker.setVisibility(View.VISIBLE);

        } else{
            mMonthYarContainer.setVisibility(View.VISIBLE);
            mDatePicker.setVisibility(View.GONE);

            npMonth.setVisibility(visibleMonthPicker ? View.VISIBLE : View.GONE);
            npYear.setVisibility(visibleYearPicker   ? View.VISIBLE : View.GONE);
        }
    }

    protected void setupInternalPicker(){
        switch (mDisplayType){
            case DAY: {
                titleDateFormat = FORMAT_DAY;
                setWidgetFieldsVisible(true, false, false);
                mDatePicker.setSpinnersShown(true);
                mDatePicker.setCalendarViewShown(false);
            } break;

            case MONTH:{
                titleDateFormat = FORMAT_ONLY_MONTH;
                setWidgetFieldsVisible(false, true, false);
            } break;

            case MONTH_YEAR:
                titleDateFormat = FORMAT_MONTH_WITH_YEAR;
                setWidgetFieldsVisible(false, true, true);
                break;

            case YEAR:
                titleDateFormat = FORMAT_YEAR;
                setWidgetFieldsVisible(false, false, true);
                break;

            case WEEK:
                titleDateFormat = FORMAT_WEEK;
                setWidgetFieldsVisible(true, false, false);
                mDatePicker.setSpinnersShown(false);
                mDatePicker.setCalendarViewShown(true);
                break;

            default:
                titleDateFormat = FORMAT_DAY;
                setWidgetFieldsVisible(true, false, false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateTitleDialog();
    }

    public Calendar getCurrentCalendarInstance() {
        if (mCurrentCalendarInstance == null) {
            mCurrentCalendarInstance = Calendar.getInstance();
        }

        return mCurrentCalendarInstance;
    }

    protected void updateTitleDialog() {
        if (getDialog() != null) {
            // update title
            String title;
            if(mDisplayType == DisplayType.WEEK){
                title = String.format("week %s", FORMAT_WEEK.format(mCurrentCalendarInstance.getTime()));

            } else{
                title = titleDateFormat.format(getCurrentCalendarInstance().getTime());
            }
            getDialog().setTitle(title);
        }
    }

    private DatePicker.OnDateChangedListener mOnDateChangeListener = new DatePicker.OnDateChangedListener() {
        @Override
        public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
            getCurrentCalendarInstance().set(Calendar.YEAR, year);
            getCurrentCalendarInstance().set(Calendar.MONTH, month);
            getCurrentCalendarInstance().set(Calendar.DAY_OF_MONTH, day);

            updateTitleDialog();
        }
    };

    private NumberPicker.OnValueChangeListener mOnNumberPickerValueChangeListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker numberPicker, int prevValue, int newValue) {
            System.out.println("NumberPicker:onValueChange numberPicker:" + numberPicker + " prevValue:" + prevValue + " newValue:" + newValue);
            if (numberPicker == npYear) {
                getCurrentCalendarInstance().set(Calendar.YEAR, newValue);

            } else if (numberPicker == npMonth) {
                getCurrentCalendarInstance().set(Calendar.MONTH, newValue);
            }

            updateTitleDialog();
        }
    };
}
