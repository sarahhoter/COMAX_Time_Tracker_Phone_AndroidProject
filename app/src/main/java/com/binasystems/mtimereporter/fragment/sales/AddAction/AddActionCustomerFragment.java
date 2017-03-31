package com.binasystems.mtimereporter.fragment.sales.AddAction;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.DialogInterface;
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

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.activity.BaseFragment;
import com.binasystems.mtimereporter.adapter.CustomersListAdapter;
import com.binasystems.mtimereporter.api.requests.LoadCustomersTask;
import com.binasystems.mtimereporter.api.requests.UniRequest;
import com.binasystems.mtimereporter.dialog.Add_action.AddActionProjectDialog;
import com.binasystems.mtimereporter.objects.Customer;

/**
 * This is sales select menu fragment
 * @author binasystems
 *
 */
public class AddActionCustomerFragment extends BaseFragment implements OnItemClickListener{
	
	private ListView list;
	private SearchView searchView;
	Handler mHandler = new Handler();
	String filterText;
	LoadCustomersTask mloadCustomersTask;
	CustomersListAdapter customersListAdapter;


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
		return inflater.inflate(R.layout.fragment_add_action_select_cutomer, null);
	}		
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);	
		
		ActionBar actionBar = getActivity().getActionBar();
		if(actionBar != null) {

			actionBar.setTitle(R.string.choose_customer);
			actionBar.setDisplayShowHomeEnabled(false);

		}
		initilize(view);
	}
	
	private void initilize(View view){
		// initialize menu
		searchView=(SearchView) view.findViewById(R.id.searchViewCust);
		list=(ListView)view.findViewById(R.id.customer_list);
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

		searchView.setQueryHint(this.getResources().getString(R.string.choose_customer));
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
		list.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
								 int visibleItemCount, int totalItemCount) {

				boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;

				if (loadMore && mloadCustomersTask != null
						&& !mloadCustomersTask.isRunning()) {

					Customer last = null;
					if (customersListAdapter.getCount() > 0) {
						last = customersListAdapter.getlastItem();
						if (last != null && customersListAdapter.hasMoreRows()) {doLoadCustomersAction(last);}
					}
				}
			}
		});
		customersListAdapter=new CustomersListAdapter(getActivity(),R.id.category_item_code,null,R.layout.category_list_item);
		list.setAdapter(customersListAdapter);
		doLoadCustomersAction(null);


	}
	private void doLoadCustomersAction(Customer customer) {
		customersListAdapter.clear();
		if(mloadCustomersTask == null ||
				mloadCustomersTask.getStatus() != AsyncTask.Status.RUNNING){

			mloadCustomersTask = new LoadCustomersTask(getActivity(),customersListAdapter,this);
			mloadCustomersTask.execute(filterText,null);
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
		Customer customer=(Customer) parent.getItemAtPosition(position);
		UniRequest.customer=customer;
		AddActionProjectDialog addActionProjectDialog=new AddActionProjectDialog(getActivity(),R.layout.dialog_add_action_select_project);
		addActionProjectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				if(TimeTrackerApplication.getInstance().getClose())
					getActivity().finish();
			}
		});
		addActionProjectDialog.show();

	}
}
