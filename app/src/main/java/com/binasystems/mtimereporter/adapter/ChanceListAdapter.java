package com.binasystems.mtimereporter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.objects.Combo;
import com.binasystems.mtimereporter.utils.ColorChooser;

import java.util.ArrayList;

public class ChanceListAdapter extends SimpleAdapter {
	private ArrayList<Combo> listProject = null;
	LayoutInflater layout_inflater;
	private boolean has_more_rows = false;
	Context context;
	public ChanceListAdapter(Context context, int textViewResourceId,
							 ArrayList<Combo> objects) {
		super(context, null, textViewResourceId, null, null);
		layout_inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(objects!=null)
			listProject = objects;
		else listProject=new ArrayList<Combo>();
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

		holder.name.setText(listProject.get(position).getName());
		list_item.setBackgroundResource((position==0) ?R.drawable.grid_item_no_choose: R.drawable.grid_item);
//		list_item
//				.setBackgroundResource((position % 2 == 0) ? ColorChooser.getColorLightWhite()
//						: ColorChooser.getColorLightDark());
		return list_item;
	}

	public void clear() {

		listProject.clear();
		notifyDataSetChanged();

	}

	public void addItems(ArrayList<Combo> items) {
		Combo combo=new Combo();
		combo.setName(context.getResources().getString(R.string.itemSuper));
		listProject.addAll(items);
		listProject.add(0,combo);
		notifyDataSetChanged();

	}
	@Override
	public int getCount() {
		if(listProject!=null)
			return listProject.size();
		else return 0;
	}

	@Override
	public Object getItem(int position) {
		return listProject.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	public boolean hasMoreRows(){

		return has_more_rows;

	}

	public void setHasMoreRows(boolean has_more_rows){

		this.has_more_rows = has_more_rows;

	}
}