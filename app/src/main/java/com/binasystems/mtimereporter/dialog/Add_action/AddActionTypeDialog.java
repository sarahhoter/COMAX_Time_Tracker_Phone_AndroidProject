package com.binasystems.mtimereporter.dialog.Add_action;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.adapter.TypeListAdapter;

import java.util.ArrayList;

/**
 * This is sales select menu fragment
 * @author binasystems
 *
 */
public class AddActionTypeDialog extends Dialog implements OnItemClickListener,View.OnClickListener{

	View action_bar;
	TextView cancel;
	String filterText;
	SearchView searchView;
	Handler mHandler = new Handler();
	ListView list;
	Context context;
	TypeListAdapter typeListAdapter;
	String[] androidStrings =getContext().getResources().getStringArray(R.array.typeArray);
	String[] androidStringsSearch=new String[]{};
	String[] androidStringsSearchTest=new String[]{};
	Dialog my_dialog;


	public AddActionTypeDialog(final Context context, int theme) {
		super(context, theme);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(theme);
		this.context=context;
		action_bar=findViewById(R.id.action_bar);
		list=(ListView)findViewById(R.id.type_list);
		cancel=(TextView)action_bar.findViewById(R.id.cancel);
		typeListAdapter=new TypeListAdapter(context,R.layout.combo_list_item,androidStrings);
		cancel.setOnClickListener(this);
		((TextView)findViewById(R.id.action_bar_textView_title)).setText(R.string.add_action);
		list.setAdapter(typeListAdapter);
		my_dialog=this;
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TimeTrackerApplication.getInstance().setType(parent.getItemAtPosition(position).toString());
				TimeTrackerApplication.getInstance().setSwtype(String.valueOf(position+1));
				AddActionStatusDialog addActionStatusDialog=new AddActionStatusDialog(context,R.layout.dialog_add_action_select_type);
				addActionStatusDialog.setOnDismissListener(new OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						if(TimeTrackerApplication.getInstance().getClose())
							my_dialog.dismiss();
					}
				});
				addActionStatusDialog.show();
			}
		});
		searchView = (SearchView) findViewById(R.id.searchView);
		searchView.setQueryHint(this.getContext().getResources().getString(R.string.title_activity_type));
		searchView.setIconifiedByDefault(false);
		/*
		 * Set retro style for search fields
		 * Remove focus line from Search field.
		 */
		int searchPlateId = searchView.getContext().getResources()
				.getIdentifier("android:id/search_plate", null, null);
		View searchPlateView = searchView.findViewById(searchPlateId);
		if (searchPlateView != null) {
			searchPlateView.setBackgroundColor(Color.TRANSPARENT);
		}
		AutoCompleteTextView search_text = (AutoCompleteTextView) searchView.findViewById(searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null));
		search_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.search_view_size));
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String text) {
				System.out.println(text + " on onQueryTextSubmit");
				filterText = text;

				mHandler.removeCallbacks(mFilterCategoryAction);
				mHandler.post(mFilterCategoryAction);
				return true;
			}

			@Override
			public boolean onQueryTextChange(String text) {
				filterText = text;
				mHandler.removeCallbacks(mFilterCategoryAction);
				mHandler.postDelayed(mFilterCategoryAction, 1000);
				return true;
			}
		});

	}

	private Runnable mFilterCategoryAction = new Runnable() {

		@Override
		public void run() {
			ArrayList<String> strings=new ArrayList<>();
			for(int i=0;i<androidStrings.length;i++)
				if(androidStrings[i].contains(filterText))
					strings.add(androidStrings[i]);
			androidStringsSearchTest=androidStrings;
			androidStringsSearch=new String[strings.size()];
			for(int i=0;i<strings.size();i++)
				androidStringsSearch[i]=strings.get(i);
			typeListAdapter.clear();
			typeListAdapter.addItems(androidStringsSearch);
			androidStrings=androidStringsSearchTest;
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


	}

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {

		if(item.getItemId() == android.R.id.home){
			dismiss();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.cancel: {dismiss();}break;
		}
	}



}

