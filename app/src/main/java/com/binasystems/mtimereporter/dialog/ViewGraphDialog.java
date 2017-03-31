package com.binasystems.mtimereporter.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.api.requests.UniRequest;
import com.binasystems.mtimereporter.objects.Customer;


public class ViewGraphDialog extends Dialog implements OnShowListener,View.OnClickListener {
	private View graphView;
	View action_bar;
	TextView cancel;
	private TextView graphHeader;
	private LinearLayout graphC = null;
	//private CatalogItem catalogItem;
	/**
	 *
	 * @param context
	 * @param contentView
	 * @param graph

	 *
	 */
	public ViewGraphDialog(Context context, int contentView, View graph, Customer customer,String title) {
		super(context);
		this.graphView = graph;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(contentView);
		/*
		 * Load elements from xml
		 */
		action_bar=findViewById(R.id.action_bar);
		((TextView)findViewById(R.id.action_bar_textView_title)).setText(title);
		cancel=(TextView)action_bar.findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
		graphC = (LinearLayout)findViewById(R.id.graphView);
		((ImageView) findViewById(R.id.image)).setVisibility(View.GONE);
		setOnShowListener(this);
	}

	@Override
	public void onShow(DialogInterface dialog) {
		((ViewGroup) graphView.getParent()).removeView(graphView);
		graphC.addView(graphView);

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.cancel: {dismiss();}break;
		}
	}
}
