package com.binasystems.mtimereporter.dialog.Add_action;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.R;

import com.binasystems.mtimereporter.api.requests.ProjectsLoadTask;
import com.binasystems.mtimereporter.api.requests.UniRequest;
import com.binasystems.mtimereporter.objects.Project;

import java.util.ArrayList;

/**
 * This is sales select menu fragment
 * @author binasystems
 *
 */
public class AddActionProjectDialog extends Dialog implements OnItemClickListener,View.OnClickListener{
	
	ListView list;
	SearchView searchView;
	Handler mHandler = new Handler();
	String filterText;
	ProjectsLoadTask projectsLoadTask;
	ProjectsListAdapter projectsListAdapter;
	View action_bar;
	TextView cancel;
	TextView next;
	Context context;
	Dialog my_dialog;

	public AddActionProjectDialog(Context context, int theme) {
		super(context, theme);
		my_dialog=this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(theme);
		searchView=(SearchView)findViewById(R.id.searchViewProject);
		list=(ListView)findViewById(R.id.project_list);
		searchView.setIconifiedByDefault(false);
		action_bar=findViewById(R.id.action_bar);
		((TextView)action_bar.findViewById(R.id.action_bar_textView_title)).setText(R.string.add_action);
		cancel=(TextView)action_bar.findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
		next=(TextView)action_bar. findViewById(R.id.actionBarNext);
		next.setOnClickListener(this);
		this.context=context;
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
				mHandler.removeCallbacks(mFilterCustomerAction);
				mHandler.post(mFilterCustomerAction);
				return true;
			}

			@Override
			public boolean onQueryTextChange(String text) {
				filterText = text;
				mHandler.removeCallbacks(mFilterCustomerAction);
				mHandler.postDelayed(mFilterCustomerAction, 1000);
				return true;
			}
		});
		list.setOnItemClickListener(this);
		projectsListAdapter=new ProjectsListAdapter(context,R.id.category_item_code,null);
		list.setAdapter(projectsListAdapter);
		doLoadCustomersAction();


	}

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

	

	private void doLoadCustomersAction() {
		projectsListAdapter.clear();
		if(projectsLoadTask == null ||
				projectsLoadTask.getStatus() != AsyncTask.Status.RUNNING){

			projectsLoadTask = new ProjectsLoadTask(this.getContext(),projectsListAdapter);
			projectsLoadTask.execute(filterText);
		}


	}
	private Runnable mFilterCustomerAction = new Runnable() {

		@Override
		public void run() {
			doLoadCustomersAction();
		}
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Project project=(Project) parent.getItemAtPosition(position);
		UniRequest.project=project;
		next();


	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.cancel:dismiss();break;
			case R.id.actionBarNext:{
				UniRequest.project=null;
				next();
			}break;
		}
	}
	private void next(){
		AddActionDateDialog addActionDateDialog=new AddActionDateDialog(context,R.layout.dialog_add_action_select_date);
		addActionDateDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				if(TimeTrackerApplication.getInstance().getClose())
					my_dialog.dismiss();
			}
		});
		addActionDateDialog.show();
	}
	public class ProjectsListAdapter extends SimpleAdapter {
		private ArrayList<Project> listProject = null;
		LayoutInflater layout_inflater;
		private boolean has_more_rows = false;


		public ProjectsListAdapter(Context context, int textViewResourceId,
								   ArrayList<Project> objects) {
			super(context, null, textViewResourceId, null, null);
			layout_inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if(objects!=null)
				listProject = objects;
			else listProject=new ArrayList<Project>();
		}
		private class ViewHolder {

			TextView code;
			TextView name;

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

			holder = (ViewHolder) list_item.getTag();
			holder.code.setText(listProject.get(position).getKod().toString());
			holder.name.setText(listProject.get(position).getName());


			return list_item;
		}

		public void clear() {

			listProject.clear();
			notifyDataSetChanged();

		}

		public void addItems(ArrayList<Project> items) {

			listProject.addAll(items);
			if(listProject.size()==0) {
				next.setVisibility(View.VISIBLE);
				list.setVisibility(View.GONE);

			}
			else {
				searchView.setVisibility(View.VISIBLE);
				next.setVisibility(View.INVISIBLE);
				list.setVisibility(View.VISIBLE);
			}
			notifyDataSetChanged();

		}

		@Override
		public int getCount() {
			if(list!=null)
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

		public boolean hasMoreRows(){return has_more_rows;}

		public void setHasMoreRows(boolean has_more_rows){this.has_more_rows = has_more_rows;}
	}


}

