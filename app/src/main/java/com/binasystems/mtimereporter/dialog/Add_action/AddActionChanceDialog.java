package com.binasystems.mtimereporter.dialog.Add_action;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.adapter.ChanceListAdapter;
import com.binasystems.mtimereporter.api.requests.ChanceLoadTask;
import com.binasystems.mtimereporter.objects.Combo;

/**
 * This is sales select menu fragment
 * @author binasystems
 *
 */
public class AddActionChanceDialog extends Dialog implements OnItemClickListener,View.OnClickListener{

	SearchView searchView;
	ListView list;
	Handler mHandler = new Handler();
	String filterText;
	ChanceLoadTask chanceLoadTask;
	View action_bar;
	TextView cancel;
	Button next;
	Context context;
	ChanceListAdapter chanceListAdapter;
	Dialog my_dialog;

	public AddActionChanceDialog(Context context, int theme) {
		super(context, theme);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.context=context;
		setContentView(theme);
		list=(ListView)findViewById(R.id.chance_list);
		action_bar=findViewById(R.id.action_bar);
		((TextView)action_bar.findViewById(R.id.action_bar_textView_title)).setText(R.string.add_action);
		cancel=(TextView)action_bar.findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
		searchView = (SearchView) findViewById(R.id.searchView);
		searchView.setQueryHint(this.getContext().getResources().getString(R.string.title_activity_chance));
		searchView.setIconifiedByDefault(false);
		my_dialog=this;
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
		/*
		 * Set retro style for search fields
		 * Remove focus line from Search field.
		 */

		list.setOnItemClickListener(this);
		chanceListAdapter=new ChanceListAdapter(this.getContext(),R.id.text1,null);
		list.setAdapter(chanceListAdapter);
		doLoadChanceAction();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


	}
	private Runnable mFilterCategoryAction = new Runnable() {

		@Override
		public void run() {
			doLoadChanceAction();
		}
	};

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		
		if(item.getItemId() == android.R.id.home){
			dismiss();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	private void doLoadChanceAction() {
		chanceListAdapter.clear();
		if(chanceLoadTask == null ||
				chanceLoadTask.getStatus() != AsyncTask.Status.RUNNING){

			chanceLoadTask = new ChanceLoadTask(this.getContext(),chanceListAdapter,this);
			chanceLoadTask.execute(filterText);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Combo chance=(Combo) parent.getItemAtPosition(position);
		if(position!=0) TimeTrackerApplication.getInstance().setChance(chance);
		else TimeTrackerApplication.getInstance().setChance(null);
		next();

	}
	public void noItem(){
		next.setVisibility(View.VISIBLE);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.cancel:dismiss();break;
			case R.id.next:{
				next();
			}break;
		}
	}
	private void next(){
		AddActionStatusChanceDialog addActionStatusChanceDialog=new AddActionStatusChanceDialog(context,R.layout.dialog_add_action_select_status_chance);
		addActionStatusChanceDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				if(TimeTrackerApplication.getInstance().getClose())
					my_dialog.dismiss();
			}
		});
		addActionStatusChanceDialog.show();
	}



}

