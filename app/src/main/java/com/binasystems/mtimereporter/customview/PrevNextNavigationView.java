package com.binasystems.mtimereporter.customview;

import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.binasystems.mtimereporter.R;


public class PrevNextNavigationView {
	private View nextButton;
	private View prevButton;
	private TextView titleTV;
	private View contentView;

	private OnPrevNextNavigateViewListener mListener;

	public PrevNextNavigationView(View contentView){
		this.contentView = contentView;
		this.prevButton = contentView.findViewById(R.id.navigation_buttonPrev);
		this.nextButton = contentView.findViewById(R.id.navigation_buttonNext);

		this.titleTV = (TextView) contentView.findViewById(R.id.navigation_title);
		titleTV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(mListener != null){
					mListener.onButtonClick(PrevNextNavigationView.this);
				}
			}
		});
		nextButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(mListener != null){
					mListener.onNextButtonClick(PrevNextNavigationView.this);
				}
			}
		});
		
		prevButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mListener != null){
					mListener.onPrevButtonClick(PrevNextNavigationView.this);
				}				
			}
		});
	}
	
	public void setPrevNextButtonsVisible(boolean prevButtonVisible, boolean nextButtonVisible){
		nextButton.setVisibility(nextButtonVisible ? View.VISIBLE : View.INVISIBLE);
		prevButton.setVisibility(prevButtonVisible ? View.VISIBLE : View.INVISIBLE);
	}
	
	public void setTitle(String title){
		this.titleTV.setText(title);
	}
	
	public void setViewBackgroundColor(int color){
		this.contentView.setBackgroundColor(color);
	}
	public void setTitleStyle(int color,int size){
		this.titleTV.setTextColor(color);
		this.titleTV.setTextSize(size);
	}
	public void setListener(OnPrevNextNavigateViewListener listener){
		this.mListener = listener;
	}
	
//	public void setTitle(String ttt, String fd){
//		TranslateAnimation translateAnimation = new TranslateAnimation(-100, 100, 0, 0);
//		translateAnimation.setDuration(500);
//		translateAnimation.setFillAfter(true);
//		titleTV.startAnimation(translateAnimation);
//	}
//	
//	public void setAnimatedTitleFromLeftToRight(String title, String fd){
//		TranslateAnimation translateAnimation = new TranslateAnimation(-100, 100, 0, 0);
//		translateAnimation.setDuration(500);
//		translateAnimation.setFillAfter(true);
//		titleTV.startAnimation(translateAnimation);
//	}
		
	public static interface OnPrevNextNavigateViewListener{
		public void onNextButtonClick(PrevNextNavigationView view);
		public void onPrevButtonClick(PrevNextNavigationView view);
		public void  onButtonClick(PrevNextNavigationView view);
	}
}
