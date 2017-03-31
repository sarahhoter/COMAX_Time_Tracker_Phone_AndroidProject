package com.binasystems.mtimereporter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.objects.Combo;

import java.util.ArrayList;

public class TypeListAdapter extends SimpleAdapter {
	private String[] listProject = null;
	LayoutInflater layout_inflater;
	private boolean has_more_rows = false;
	Context context;

	public TypeListAdapter(Context context, int textViewResourceId,
						  String[] objects) {
		super(context, null, textViewResourceId, null, null);
		layout_inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(objects!=null)
			listProject = objects;
		else listProject=new String[]{};
		this.context=context;

	}
	private class ViewHolder {

		TextView name;

	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		View list_item = null;
		if (convertView != null) {
			list_item = convertView;
		} else {
			list_item = layout_inflater.inflate(R.layout.combo_list_item,
					parent, false);
			holder = new ViewHolder();

			holder.name = (TextView) list_item
					.findViewById(R.id.name);

			list_item.setTag(holder);
		}

		holder = (ViewHolder) list_item.getTag();

		holder.name.setText(listProject[position]);
	//	list_item.setBackgroundResource((position==0) ?R.drawable.grid_item_no_choose: R.drawable.grid_item);
//		list_item
//				.setBackgroundResource((position % 2 == 0) ? ColorChooser.getColorLightWhite()
//						: ColorChooser.getColorLightDark());
		return list_item;
	}

	public void clear() {

		listProject=new String[]{};
		notifyDataSetChanged();

	}

	public void addItems(String[] items) {
		listProject=items;
		notifyDataSetChanged();

	}
	@Override
	public int getCount() {
			return listProject.length;
	}

	@Override
	public Object getItem(int position) {
		return listProject[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public boolean hasMoreRows(){return has_more_rows;}

	public void setHasMoreRows(boolean has_more_rows){this.has_more_rows = has_more_rows;}
}