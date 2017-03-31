package com.binasystems.mtimereporter.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

public abstract class BinaBaseAdapter<T> extends BaseAdapter{
	protected List<T> mData;
	protected Context mContext;
	protected LayoutInflater mInflater;
	
	public BinaBaseAdapter(Context context, List<T> data){
		this.mData = data;
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);				
	}
	
	@Override
	public int getCount() {
		if(mData != null)
			return mData.size();
		
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if(mData != null){
			return mData.get(position);
		}		
		
		return null;
	}
}
