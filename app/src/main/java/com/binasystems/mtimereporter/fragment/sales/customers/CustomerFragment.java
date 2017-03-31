package com.binasystems.mtimereporter.fragment.sales.customers;

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

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.activity.BaseFragment;
import com.binasystems.mtimereporter.adapter.CustomersListAdapter;
import com.binasystems.mtimereporter.api.requests.LoadCustomersTask;
import com.binasystems.mtimereporter.api.requests.UniRequest;
import com.binasystems.mtimereporter.dialog.Add_action.AddActionProjectDialog;
import com.binasystems.mtimereporter.dialog.customers.CustomerDetailsProjectDialog;
import com.binasystems.mtimereporter.objects.Customer;

import java.util.ArrayList;

/**
 * This is sales select menu fragment
 * @author binasystems
 *
 */
public class CustomerFragment extends BaseFragment implements OnItemClickListener{
	
	private ListView list;
	private SearchView searchView;
	Handler mHandler = new Handler();
	String filterText;
	LoadCustomersTask mloadCustomersTask;
	CustomersListAdapter customersListAdapter;
	ArrayList<Customer> items;
	ArrayList<Customer> searchItem;
	Boolean flag;
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
		return inflater.inflate(R.layout.fragment_add_action_select_cutomer, null);
	}		
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);	
		
//		ActionBar actionBar = getActivity().getActionBar();
//		if(actionBar != null) {
//
//			actionBar.setTitle(R.string.choose_customer);
//			actionBar.setDisplayShowHomeEnabled(false);
//
//		}
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
		searchView.setQueryHint(getResources().getString(R.string.choose_customer));
		AutoCompleteTextView search_text = (AutoCompleteTextView) searchView.findViewById(searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null));
		search_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.search_view_size));
		searchView.setOnQueryTextListener(new OrderSearchListener());
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

						if (last != null && customersListAdapter.hasMoreRows()) {
							doLoadCustomersAction(last,false);


						}
					}
				}

			}
		});
		list.setOnItemClickListener(this);
		customersListAdapter=new CustomersListAdapter(getActivity(),R.id.category_item_code,null,R.layout.customer_list_item);
		list.setAdapter(customersListAdapter);
		doLoadCustomersAction(null,false);


	}
	private void doLoadCustomersAction (Customer last,Boolean clear){
		if(clear)
			customersListAdapter.clear();
		if(mloadCustomersTask == null ||
				mloadCustomersTask.getStatus() != AsyncTask.Status.RUNNING){

			mloadCustomersTask = new LoadCustomersTask(getActivity(),customersListAdapter,this);
			if(last!=null)
			mloadCustomersTask.setLastItem(last);
			mloadCustomersTask.execute(filterText,"30");
		}


	}
	private Runnable mFilterCustomerAction = new Runnable() {

		@Override
		public void run() {
			doLoadCustomersAction(null,true);
		}
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Customer customer=(Customer) parent.getItemAtPosition(position);
		UniRequest.customer=customer;
		if(position+1==parent.getCount())
			TimeTrackerApplication.getInstance().setLastCustomer((Customer) parent.getItemAtPosition(position));

		else TimeTrackerApplication.getInstance().setLastCustomer((Customer) parent.getItemAtPosition(position+1));

		CustomerDetailsProjectDialog addActionDetailsProjectDialog=new CustomerDetailsProjectDialog(getActivity(),R.layout.dialog_customer_details);
//		if(search){
//			int pos=-1, i;
//			flag=false;
//			for(i = 0; i < this.items.size() ; i++) {
//				if(this.items.get(i).getCode().equals(customer.getCode())) {
//					pos = i;
//					flag=true;
//				}
//			}
//			if(flag)
//				addActionDetailsProjectDialog.setdata(items,pos);
//			else {
//				for(i = 0; i < this.searchItem.size()&&!this.searchItem.get(i).getCode().equals(customer.getCode()) ; i++) ;
//				pos = i;
//				addActionDetailsProjectDialog.setdata(searchItem, pos);
//			}
//		}
//		else
//		addActionDetailsProjectDialog.setdata(items,position);
		addActionDetailsProjectDialog.show();
	}
	public void setList(ArrayList<Customer> items,Boolean s){
		if(!s) {
			if(this.items!=null&&this.items.size()>0)
				this.items.addAll(items);
			else this.items = items;
		}
		else {
			search=true;
			searchItem=items;

		}
	}
	private class OrderSearchListener implements SearchView.OnQueryTextListener {



			@Override
			public boolean onQueryTextSubmit(String text) {
				System.out.println(text + " on onQueryTextSubmit");
				filterText = text;
				mHandler.removeCallbacks(mFilterCustomerAction);
				mHandler.post(mFilterCustomerAction);
				return false;
			}

			@Override
			public boolean onQueryTextChange(String text) {
				filterText = text;
				mHandler.removeCallbacks(mFilterCustomerAction);
				mHandler.postDelayed(mFilterCustomerAction, 1000);
				return false;
			}


	}
}
