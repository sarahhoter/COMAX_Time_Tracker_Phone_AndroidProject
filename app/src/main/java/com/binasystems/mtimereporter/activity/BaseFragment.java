package com.binasystems.mtimereporter.activity;

import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment{
	public static final String EXTRA_DATE_VIEW_TYPE = "EXTRA_DATE_VIEW_TYPE";

	public void showProgress(){
		if(getActivity() != null){
			((BaseActivity) getActivity()).showProgress();
		}

	}
	
	public void hideProgress(){
		if(getActivity() != null){
			((BaseActivity) getActivity()).hideProgress();
		}		
	}
}
