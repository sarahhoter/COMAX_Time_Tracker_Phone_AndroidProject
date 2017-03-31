package com.binasystems.mtimereporter.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.binasystems.mtimereporter.objects.Category;
import com.binasystems.mtimereporter.utils.ColorChooser;
import com.binasystems.mtimereporter.R;

public class CategoriesListAdapter extends SimpleAdapter {
	private ArrayList<Category> list = null;	
	LayoutInflater layout_inflater;

	private class ViewHolder {

		TextView code;
		TextView name;

	}

	public CategoriesListAdapter(Context context, int textViewResourceId,
			ArrayList<Category> objects) {
		super(context, null, textViewResourceId, null, null);
		layout_inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
		list = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		View list_item = null;
		if (convertView != null) {
			list_item = convertView;
		} else {
			list_item = layout_inflater.inflate(R.layout.category_list_item,
					parent, false);
			holder = new ViewHolder();
			holder.code = (TextView) list_item
					.findViewById(R.id.category_item_code);
			holder.name = (TextView) list_item
					.findViewById(R.id.category_item_name);

			list_item.setTag(holder);
		}

		holder = (ViewHolder)list_item.getTag();
		holder.code.setText(list.get(position).getCode());
		holder.name.setText(list.get(position).getName());

//		list_item.setBackgroundResource((position % 2 == 0) ? ColorChooser.getColorLightWhite()
//						: ColorChooser.getColorLightDark());
		return list_item;
	}

	public void clear() {

		list.clear();
		notifyDataSetChanged();

	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
