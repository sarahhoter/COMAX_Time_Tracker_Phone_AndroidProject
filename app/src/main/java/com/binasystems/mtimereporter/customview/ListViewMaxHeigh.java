package com.binasystems.mtimereporter.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ListView;

import com.binasystems.mtimereporter.R;

/**
 * Created by hani on 27/11/2016.
 */

public class ListViewMaxHeigh extends ListView {

    private final int maxHeight;

    public ListViewMaxHeigh(Context context) {
        this(context, null);
    }

    public ListViewMaxHeigh(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListViewMaxHeigh(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ListViewMaxHeight);
            maxHeight = a.getDimensionPixelSize(R.styleable.ListViewMaxHeight_maxHeight, Integer.MAX_VALUE);
            a.recycle();
        } else {
            maxHeight = 0;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (maxHeight > 0 && maxHeight < measuredHeight) {
            int measureMode = MeasureSpec.getMode(heightMeasureSpec);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, measureMode);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}
