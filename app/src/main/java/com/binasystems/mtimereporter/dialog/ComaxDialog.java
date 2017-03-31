package com.binasystems.mtimereporter.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.support.v7.internal.widget.ListPopupWindow;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.utils.Utils;


public class ComaxDialog extends Dialog implements
		android.view.View.OnClickListener {

	private LinearLayout dismiss = null;
	private TextView title = null;
	private View main = null;
	protected Context context = null;
	protected boolean onBackPressedFlag = false;
	boolean cancable=false;
	//protected ItemSelectListener itemSelectListener;
	private double widthParam;
	private double heightParams = 0;
	/**
	 * This flag used to present dialog as popup
	 * or as dialog
	 */
	boolean popupMode = false;

	WaitDialog mProgressDialog;

	public ComaxDialog(Context context, int contentView) {
		super(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
		this.context = context;
		System.err.println("Open dialog: " + this.getClass().getName());
		setContentView(contentView);
	}

	public ComaxDialog(Context context, int contentView, boolean popupMode, double widthParam) {
		super(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
		this.popupMode = popupMode;
		this.context = context;
		this.widthParam = widthParam;
		System.err.println("Open dialog: " + this.getClass().getName());
		setContentView(contentView);
	}
	public ComaxDialog(Context context, int contentView, double heightParams) {
		super(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
		this.popupMode = false;
		this.context = context;
		this.widthParam = widthParam;
		this.heightParams=heightParams;
		System.err.println("Open dialog: " + this.getClass().getName());
		setContentView(contentView);
	}
	public ComaxDialog(Context context, int contentView, boolean popupMode, double widthParam, double heightParams) {
		super(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
		this.popupMode = popupMode;
		this.context = context;
		this.heightParams = heightParams;
		this.widthParam = widthParam;
		System.err.println("Open dialog: " + this.getClass().getName());
		setContentView(contentView);
	}
	public ComaxDialog(Context context, int contentView, boolean popupMode, double widthParam, double heightParams,boolean cancable) {
		super(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
		this.popupMode = popupMode;
		this.context = context;
		this.heightParams = heightParams;
		this.cancable=cancable;
		this.widthParam = widthParam;
		System.err.println("Open dialog: " + this.getClass().getName());
		setContentView(contentView);
	}
	public Activity getActivity(){
		return TimeTrackerApplication.getInstance().getCurrentActivity();
	}

	@Override
	public void setContentView(int contentView) {
		int gravity = 0;

		if(!popupMode) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
					WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
//			getWindow().setBackgroundDrawableResource(android.R.color.transparent);
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
			getWindow().setWindowAnimations(R.style.PopUpAnimation);
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
			gravity = Gravity.RIGHT| Gravity.BOTTOM;

			this.setCanceledOnTouchOutside(true);
		} else{
			if(cancable) {
				this.setCancelable(true);
				this.setCanceledOnTouchOutside(true);
				getWindow().setSoftInputMode(
						WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
				ListPopupWindow listPopupWindow=new ListPopupWindow(getContext());
				listPopupWindow.setModal(false);
			}
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
					WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
		}

		super.setContentView(contentView);
		//	setCanceledOnTouchOutside(true);
		//	setCancelable(true);
		main = (LinearLayout) findViewById(R.id.main);

		if (main != null) {
			main.setFocusableInTouchMode(false);
			main.setClickable(false);
		}

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);


		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;

		if(popupMode){
			// update dimension sizes
			// Sara, you can update it
			WindowManager.LayoutParams params = getWindow().getAttributes();
			params.x = 0;
			width = (int)(width * widthParam);
			if(heightParams > 0) {
				height = (int) (height * heightParams);

			}
			params.y = 50;

			gravity = Gravity.CENTER;
			getWindow().setFlags(0x04000000, 0x04000000);
			//	getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
			getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
			setCancelable(true);
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
					| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		}

		WindowManager.LayoutParams params = this.getWindow().getAttributes();
		params.y = 50;
		params.x = 0;
		getWindow().setAttributes(params);
		getWindow().setGravity(gravity);
		if (!popupMode)
			getWindow().setDimAmount(0.0f);
		getWindow().setLayout(width, height);


		try {
			View focus = this.getCurrentFocus();
			focus.clearFocus();
			title.requestFocus();
			Utils.closeKeyboard(focus);
		} catch (Exception e) {
		}
	}

//	public ItemSelectListener getItemSelectListener() {
//		return itemSelectListener;
//	}
//
//	public void setItemSelectListener(ItemSelectListener itemSelectListener) {
//		this.itemSelectListener = itemSelectListener;
//	}

	@Override
	public void onBackPressed() {
		onBackPressedFlag = true;
		super.onBackPressed();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		System.err.println("onTouch " + ComaxDialog.this);

		// Handle this
//		if (event.getAction() == MotionEvent.ACTION_OUTSIDE && (event.getY() > 0 && event.getX() < getWindow().getDecorView().getWidth()) ) {
//			dismiss();
//			onBackPressed();
//		}

		return super.onTouchEvent(event);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		System.err.println("onDispatchTouchEvent " + ComaxDialog.this);

		return super.dispatchTouchEvent(ev);
	}

	public boolean isBackPressed(){
		return onBackPressedFlag;
	}

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.dismiss:
//			onBackPressedFlag = true;
//
//			onBackPressed();
//
//			dismiss();
//
//			break;
//
//		default:
//			break;
//		}
	}

	@Override
	public void setTitle(CharSequence title) {
		this.title.setText(title);
	}

	@Override
	public void dismiss() {
		try {
			View focus = this.getCurrentFocus();
			focus.clearFocus();
			//title.requestFocus();
			Utils.closeKeyboard(focus);
		} catch (Exception e) {
		}
		super.dismiss();
	}

	public void setHeightRelative(int dy) {

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int height = size.y;

		WindowManager.LayoutParams params = this.getWindow().getAttributes();
		params.x = 0;
		params.height = height - dy;
		getWindow().setAttributes(params);

	}

	public void setWidthRelative(int dx) {

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;

		WindowManager.LayoutParams params = this.getWindow().getAttributes();
		params.x = 0;
		getWindow().setAttributes(params);
		getWindow().setLayout(width-dpToPx(dx), height);
	}

	public void setGravity(int gravity) {
		getWindow().setGravity(gravity);
	}

	public int pxToDp(int px) {
		DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
		int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
		return dp;
	}

	private int dpToPx(int dp){
		return Utils.dpToPx(getContext(), dp);
	}

	public void setLeftTopMargins(int dl, int dt) {

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;

		WindowManager.LayoutParams params = this.getWindow().getAttributes();
		params.x = dpToPx(5);
		params.y = dpToPx(5);
		getWindow().setAttributes(params);
		getWindow().setLayout(width-dpToPx(dl), height-dpToPx(dt));
	}

	public void showProgress(){
		if(mProgressDialog == null){
			mProgressDialog = new WaitDialog(context);
		}

		if(!mProgressDialog.isShowing()){
			mProgressDialog.show();
		}
	}

	public void hideProgress(){
		if(mProgressDialog != null){
			if(mProgressDialog.isShowing()){
				mProgressDialog.dismiss();
				mProgressDialog = null;
			}
		}
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = dismiss != null ? dismiss.hashCode() : 0;
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (main != null ? main.hashCode() : 0);
		result = 31 * result + (context != null ? context.hashCode() : 0);
		result = 31 * result + (onBackPressedFlag ? 1 : 0);
		//result = 31 * result + (itemSelectListener != null ? itemSelectListener.hashCode() : 0);
		temp = Double.doubleToLongBits(widthParam);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(heightParams);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + (popupMode ? 1 : 0);
		result = 31 * result + (mProgressDialog != null ? mProgressDialog.hashCode() : 0);
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ComaxDialog dialog = (ComaxDialog) o;

		if (onBackPressedFlag != dialog.onBackPressedFlag) return false;
		if (Double.compare(dialog.widthParam, widthParam) != 0) return false;
		if (Double.compare(dialog.heightParams, heightParams) != 0) return false;
		if (popupMode != dialog.popupMode) return false;
		if (dismiss != null ? !dismiss.equals(dialog.dismiss) : dialog.dismiss != null)
			return false;
		if (title != null ? !title.equals(dialog.title) : dialog.title != null) return false;
		if (main != null ? !main.equals(dialog.main) : dialog.main != null) return false;
		if (context != null ? !context.equals(dialog.context) : dialog.context != null)
			return false;
//		if (itemSelectListener != null ? !itemSelectListener.equals(dialog.itemSelectListener) : dialog.itemSelectListener != null)
//			return false;
		return mProgressDialog != null ? mProgressDialog.equals(dialog.mProgressDialog) : dialog.mProgressDialog == null;

	}
}
