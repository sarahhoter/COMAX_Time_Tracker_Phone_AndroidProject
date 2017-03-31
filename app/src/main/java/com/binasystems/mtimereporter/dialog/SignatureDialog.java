package com.binasystems.mtimereporter.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.binasystems.mtimereporter.R;

import com.binasystems.mtimereporter.views.PaintView;





public class SignatureDialog extends Dialog implements View.OnClickListener{
	private LinearLayout paintHolder = null;
	private PaintView signature = null;
	View action_bar;
	TextView tytle;
	TextView cancel;
	public static Bitmap sign = null;
	public SignatureDialog(Context context, int theme) {
		super(context,theme);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(theme);
		action_bar=(View)findViewById(R.id.action_bar);
		tytle=(TextView)action_bar.findViewById(R.id.action_bar_textView_title);
		tytle.setText(R.string.definitions);
		cancel=(TextView) action_bar.findViewById(R.id.cancel);
		cancel.setOnClickListener(this);

		paintHolder = (LinearLayout) findViewById(R.id.paint_container);
		signature = new PaintView(getContext(), null, sign);
		signature.setBackgroundColor(Color.WHITE);
		paintHolder.addView(signature);

	}


	public void setMessage(String message){

	}
//	public void setCustomSize(int width, int height) {
//		/*
//		 * than height = 0 we take default height
//		 */
//		if (height == 0)
//			height = this.getWindow().getAttributes().height;
//		this.getWindow().setLayout(width, height);
//	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.cancel: {
				this.dismiss();
			}break;
			case R.id.display_store_val:{

			}

		}
	}

}
