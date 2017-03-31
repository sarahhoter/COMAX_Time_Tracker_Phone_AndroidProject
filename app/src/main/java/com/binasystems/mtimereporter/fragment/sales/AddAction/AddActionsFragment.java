package com.binasystems.mtimereporter.fragment.sales.AddAction;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SearchView;

import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.activity.BaseFragment;
import com.binasystems.mtimereporter.adapter.AddActionsListAdapter;
import com.binasystems.mtimereporter.adapter.CustomersListAdapter;
import com.binasystems.mtimereporter.api.requests.LoadActionsTask;
import com.binasystems.mtimereporter.api.requests.LoadCustomersTask;
import com.binasystems.mtimereporter.api.requests.UniRequest;
import com.binasystems.mtimereporter.dialog.Add_action.AddActionDialog;
import com.binasystems.mtimereporter.dialog.customers.CustomerDetailsProjectDialog;
import com.binasystems.mtimereporter.objects.Customer;
import com.binasystems.mtimereporter.objects.Meeting;

import java.util.ArrayList;

/**
 * This is sales select menu fragment
 * @author binasystems
 *
 */
public class AddActionsFragment extends BaseFragment implements OnItemClickListener{
	
	private ListView list;
	private SearchView searchView;
	Handler mHandler = new Handler();
	String filterText;
	LoadActionsTask mloadActionsTask;
	AddActionsListAdapter addActionsListAdapter;
	ArrayList<Meeting> items;
	boolean search=false;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

	}
	
	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		
		if(item.getItemId() == android.R.id.home){
			getActivity().finish();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_add_actions, null);
	}		
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);	
		
		ActionBar actionBar = getActivity().getActionBar();
		if(actionBar != null) {

			actionBar.setTitle(R.string.add_action);
			actionBar.setDisplayShowHomeEnabled(false);


		}
		initilize(view);



	}
	
	private void initilize(View view){
		// initialize menu
		searchView=(SearchView) view.findViewById(R.id.searchViewActions);
		list=(ListView)view.findViewById(R.id.add_actions);
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
		AutoCompleteTextView search_text = (AutoCompleteTextView) searchView.findViewById(searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null));
		search_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.search_view_size));
		list.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
								 int visibleItemCount, int totalItemCount) {

				boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;

				if (loadMore && mloadActionsTask != null
						&& !mloadActionsTask.isRunning()) {

					Meeting last = null;
					if (addActionsListAdapter.getCount() > 0) {
						last = addActionsListAdapter.getlastItem();

						if (last != null && addActionsListAdapter.hasMoreRows()) {
							doLoadCustomersAction(last);


						}
					}
				}

			}
		});
		list.setOnItemClickListener(this);
		addActionsListAdapter=new AddActionsListAdapter(getActivity(),R.id.hour,null,R.layout.add_action_list_item);
		list.setAdapter(addActionsListAdapter);
		doLoadCustomersAction(null);


	}
	private void doLoadCustomersAction (Meeting last){
		addActionsListAdapter.clear();
		if(mloadActionsTask == null ||
				mloadActionsTask.getStatus() != AsyncTask.Status.RUNNING){

			mloadActionsTask = new LoadActionsTask(getActivity(),addActionsListAdapter,this);
			if(last!=null)
				mloadActionsTask.setLastItem(last);
			mloadActionsTask.execute(filterText);
		}


	}
	private Runnable mFilterCustomerAction = new Runnable() {

		@Override
		public void run() {
			doLoadCustomersAction(null);
		}
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Meeting meeting=(Meeting)parent.getItemAtPosition(position);
		AddActionDialog addActionDialog=new AddActionDialog(getContext(),R.layout.dialog_add_action_obligation,meeting);
		addActionDialog.show();

	}
	public void setList(ArrayList<Meeting> items){
		if(items!=null)
		this.items=items;
		else search=true;
	}
}
