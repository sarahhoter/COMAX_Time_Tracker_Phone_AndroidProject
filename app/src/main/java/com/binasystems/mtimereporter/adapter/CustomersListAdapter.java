package com.binasystems.mtimereporter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.objects.Category;
import com.binasystems.mtimereporter.objects.Customer;
import com.binasystems.mtimereporter.utils.ColorChooser;

import java.util.ArrayList;

public class CustomersListAdapter extends SimpleAdapter {
	private ArrayList<Customer> list = null;
	LayoutInflater layout_inflater;
	private boolean has_more_rows = false;
	int layoutId;
	private class ViewHolder {

		TextView code;
		TextView name;
		TextView phone;
		TextView mail;


	}

	public CustomersListAdapter(Context context, int textViewResourceId,
								ArrayList<Customer> objects,int layoutId) {
		super(context, null, textViewResourceId, null, null);
		layout_inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(objects!=null)
		list = objects;
		else list=new ArrayList<Customer>();
		this.layoutId=layoutId;
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
			holder.code = (TextView) list_item
					.findViewById(R.id.category_item_code);
			holder.name = (TextView) list_item
					.findViewById(R.id.category_item_name);
			if(layoutId==R.layout.customer_list_item){
				holder.phone = (TextView) list_item
						.findViewById(R.id.category_item_phone);
				holder.mail = (TextView) list_item
						.findViewById(R.id.category_item_mail);
			}
			list_item.setTag(holder);
		}

		holder = (ViewHolder)list_item.getTag();
		holder.code.setText(list.get(position).getKod().toString());
		holder.name.setText(list.get(position).getName());
		if(layoutId==R.layout.customer_list_item){
			holder.phone.setText(list.get(position).getPelefon());
			holder.mail.setText(list.get(position).getEmail());
		}
//		list_item
//				.setBackgroundResource((position % 2 == 0) ? ColorChooser.getColorLightWhite()
//						: ColorChooser.getColorLightDark());
		return list_item;
	}

	public void clear() {

		list.clear();
		notifyDataSetChanged();

	}

	public void addItems(ArrayList<Customer> items) {

		list.addAll(items);

		notifyDataSetChanged();

	}
	@Override
	public int getCount() {
		if(list!=null)
		return list.size();
		else return 0;
	}
	public Customer getlastItem(){

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
