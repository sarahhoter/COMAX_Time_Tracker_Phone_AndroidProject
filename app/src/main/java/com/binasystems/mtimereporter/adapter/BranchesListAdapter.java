package com.binasystems.mtimereporter.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.api.requests.UniRequest;
import com.binasystems.mtimereporter.objects.Branch;
import com.binasystems.mtimereporter.utils.ColorChooser;

import java.util.ArrayList;

public class BranchesListAdapter extends SimpleAdapter {
	private ArrayList<Branch> list = null;
	LayoutInflater layout_inflater;
	Context context;
	Boolean empty;
	private class ViewHolder {

		TextView code;
		TextView name;

	}

	public BranchesListAdapter(Context context, int textViewResourceId, ArrayList<Branch> objects,Boolean empty) {
		super(context, null, textViewResourceId, null, null);
		layout_inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		list = objects;
		this.context=context;
		this.empty=empty;
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

		if(UniRequest.Super==1&&empty) {
		if(position==0){
			int Height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,80,context.getResources().getDisplayMetrics());
			//int height = getResources().getDimension(R.dimen.textView_height);
			LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Height);
			llp.setMargins(0, 0, 0, 0);
			// llp.setMargins(left, top, right, bottom);
			holder.name.setLayoutParams(llp);

			holder.name.setGravity(Gravity.CENTER|Gravity.CENTER_VERTICAL);
			holder.code.setVisibility(View.GONE);
		}

		list_item.setBackgroundResource((position==0&&empty) ?R.drawable.grid_item_no_choose: R.drawable.grid_item);

		}

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
