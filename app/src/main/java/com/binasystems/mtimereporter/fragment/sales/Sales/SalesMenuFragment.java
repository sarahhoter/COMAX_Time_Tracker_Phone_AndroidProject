package com.binasystems.mtimereporter.fragment.sales.Sales;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.activity.BaseFragment;
import com.binasystems.mtimereporter.activity.sales.SaleByAgentActivity;
import com.binasystems.mtimereporter.activity.sales.SaleByDateActivity;
import com.binasystems.mtimereporter.activity.sales.SaleByDepartmentActivity;
import com.binasystems.mtimereporter.activity.sales.SaleByStoreActivity;
import com.binasystems.mtimereporter.adapter.menu.MenuAdapter;
import com.binasystems.mtimereporter.adapter.menu.MenuItem;
import com.binasystems.mtimereporter.api.ComaxApiManager;

import java.util.Arrays;

/**
 * This is sales select menu fragment
 * @author binasystems
 *
 */
public class SalesMenuFragment extends BaseFragment {
	
	private static final long ID_SALES_BY_DATE = 0;
	private static final long ID_SALES_BY_STORE = 1;
	private static final long ID_SALES_BY_DEPARTMENT = 2;
	private static final long ID_SALES_BY_AGENT = 3;
	MenuItem[] menuItems;
	ListView menuListView;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		initilize();		
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
		return inflater.inflate(R.layout.fragment_sales_menu, null);
	}		
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);	
		
		ActionBar actionBar = getActivity().getActionBar();
		if(actionBar != null) {

			actionBar.setTitle(TimeTrackerApplication.getInstance().getBranch().getName());
			actionBar.setDisplayShowHomeEnabled(false);
		}
		
		menuListView = (ListView) view.findViewById(R.id.listView);
		menuListView.setAdapter(new MenuAdapter(getActivity(), Arrays.asList(menuItems)));
		menuListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
				if (id == ID_SALES_BY_DATE) {
					SaleByDateActivity.startActivity(getActivity());


				} else {
					if (id == ID_SALES_BY_STORE)
						SaleByStoreActivity.startAcitivty(getActivity(), null, ComaxApiManager.DateViewType.VIEW_BY_DAY);

					else if (id == ID_SALES_BY_DEPARTMENT) {
						TimeTrackerApplication.getInstance().setStore(null);
						SaleByDepartmentActivity.startAcitivty(getActivity(), null, ComaxApiManager.DateViewType.VIEW_BY_DAY);
					}
					else if(id == ID_SALES_BY_AGENT)
						SaleByAgentActivity.startAcitivty(getActivity(), null, ComaxApiManager.DateViewType.VIEW_BY_DAY);
				}
			}

		});
}
	
	private void initilize(){
		// initialize menu
		menuItems = new MenuItem[]{
			new MenuItem(ID_SALES_BY_DATE , R.string.sales_by_date,null),
			new MenuItem(ID_SALES_BY_STORE, R.string.sales_by_store,null),
				new MenuItem(ID_SALES_BY_DEPARTMENT, R.string.sales_by_dep,null),
				new MenuItem(ID_SALES_BY_AGENT, R.string.sales_by_agent,null),

		};
	}
	
}
