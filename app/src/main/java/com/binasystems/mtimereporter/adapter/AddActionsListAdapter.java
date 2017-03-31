package com.binasystems.mtimereporter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.objects.Customer;
import com.binasystems.mtimereporter.objects.Meeting;

import java.util.ArrayList;

public class AddActionsListAdapter extends SimpleAdapter {
	private ArrayList<Meeting> list = null;
	LayoutInflater layout_inflater;
	private boolean has_more_rows = false;
	int layoutId;
	Context context;
	private class ViewHolder {

		TextView hour = null;
		TextView date = null;
		TextView type = null;
		TextView customer = null;
		TextView description = null;
		TextView status = null;
		TextView dact = null;
		TextView employeeName = null;
	}

	public AddActionsListAdapter(Context context, int textViewResourceId,
								 ArrayList<Meeting> objects, int layoutId) {
		super(context, null, textViewResourceId, null, null);
		layout_inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(objects!=null)
		list = objects;
		else list=new ArrayList<Meeting>();
		this.layoutId=layoutId;
		this.context=context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		View list_item = null;
		if (convertView != null) {
			list_item = convertView;
		} else {
			list_item = layout_inflater.inflate(layoutId,
					parent, false);
			holder = new ViewHolder();
			holder.date = (TextView)list_item.findViewById(R.id.date);
			holder.hour = (TextView)list_item.findViewById(R.id.hour);
			holder.employeeName = (TextView)list_item.findViewById(R.id.employee);
			holder.type = (TextView)list_item.findViewById(R.id.type);
			holder.description = (TextView)list_item.findViewById(R.id.description);
			holder.status = (TextView)list_item.findViewById(R.id.status);
			holder.dact = (TextView)list_item.findViewById(R.id.dact);
			holder.customer = (TextView)list_item.findViewById(R.id.customer);
			list_item.setTag(holder);
		}
		holder = (ViewHolder)list_item.getTag();
		holder.hour.setText(list.get(position).getHour());
		holder.date.setText(list.get(position).getDate());
		holder.employeeName.setText(list.get(position).getEmployee());
		holder.type.setText(list.get(position).getType());
		holder.description.setText(list.get(position).getRemark());
		if(Integer.parseInt(list.get(position).getState_id())>0)
		holder.status.setText(context.getResources().getStringArray(R.array.statusArray)[Integer.parseInt(list.get(position).getState_id())-1] );
		else holder.status.setText("");
		if(!list.get(position).getFollow_up_at().equals("null"))
			holder.dact.setText(list.get(position).getFollow_up_at());
		else holder.dact.setText("");
		holder.customer.setText(list.get(position).getCustomer());
		return list_item;
	}

	public void clear() {

		list.clear();
		notifyDataSetChanged();

	}

	public void addItems(ArrayList<Meeting> items) {

		list.addAll(items);

		notifyDataSetChanged();

	}
	@Override
	public int getCount() {
		if(list!=null)
		return list.size();
		else return 0;
	}
	public Meeting getlastItem(){

		return list.get(list.size() - 1);

	}
	@Override
	public Object getItem(int position) {
		return list.get(position);
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
