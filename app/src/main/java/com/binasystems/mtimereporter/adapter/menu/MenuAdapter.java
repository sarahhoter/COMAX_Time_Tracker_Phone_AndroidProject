package com.binasystems.mtimereporter.adapter.menu;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.binasystems.mtimereporter.R;


public class MenuAdapter extends BaseAdapter{
	List<MenuItem> mData;
	Context mContext;
	LayoutInflater mInflater;
	
	public MenuAdapter(Context context, List<MenuItem> data){
		this.mData = data;
		this.mContext = context;
		mInflater = LayoutInflater.from(mContext);
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

	@Override
	public long getItemId(int position) {
		if(mData != null){
			return mData.get(position).id;										
		}
		
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Tag tag;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.main_menu_list_item, null);
			tag = new Tag();
			tag.tvTitle = (TextView) convertView.findViewById(R.id.main_menu_item_name);
			//tag.tvDescription = (TextView) convertView.findViewById(R.id.main_menu_item_description);
			//tag.ivIcon = (ImageView) convertView.findViewById(R.id.main_menu_item_icon);
			convertView.setTag(tag);
			
		} else{
			tag = (Tag) convertView.getTag();					
		}
						
		MenuItem menuItem = (MenuItem) getItem(position);
		if(menuItem != null){
			//if(menuItem.imageRes==0)
			//	tag.ivIcon.setVisibility(View.GONE);
			//tag.ivIcon.setImageResource(menuItem.imageRes);
			if(menuItem.titleString!=null)
				tag.tvTitle.setText(menuItem.titleString);
			else tag.tvTitle.setText(menuItem.titleRes);
			//tag.tvDescription.setText(menuItem.subTitleRes);
		}
		
		return convertView;
	}
	
	
	class Tag{
		TextView tvTitle;

	}

}
