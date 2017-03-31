package com.binasystems.mtimereporter.fragment.sales;

import android.app.ActionBar;
import android.content.Intent;
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
import com.binasystems.mtimereporter.activity.add_action.AddActionsMainMenuActivity;
import com.binasystems.mtimereporter.activity.add_action.AddMainMenuActivity;
import com.binasystems.mtimereporter.activity.customers.CustomerMainMenuActivity;
import com.binasystems.mtimereporter.activity.sales.SaleByDateActivity;
import com.binasystems.mtimereporter.activity.sales.SalesMainMenuActivity;
import com.binasystems.mtimereporter.activity.times.TimeReportActivity;
import com.binasystems.mtimereporter.adapter.menu.MenuAdapter;
import com.binasystems.mtimereporter.adapter.menu.MenuItem;
import com.binasystems.mtimereporter.api.requests.MenuIphoneTask;
import com.binasystems.mtimereporter.api.requests.UniRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * This is sales select menu fragment
 * @author binasystems
 *
 */
public class MenuFragment extends BaseFragment {

	//menu
	private static final long ID_TIME_TRACKER = 0;
	private static final long TIME_VIEW = 1;
	private static final long Add_ACTIONS_DISPLAY = 2;
	private static final long Add_ACTION = 3;
	ArrayList<MenuItem> menuItems;
	//MenuItem [] menuItems;
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
		menuListView.setAdapter(new MenuAdapter(getActivity(), menuItems));
		menuListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
				//// FIXME: 07/02/2017 change if to switch
				if (id == ID_TIME_TRACKER) {
					TimeTrackerApplication.getInstance().setDoc(ID_TIME_TRACKER);
					Intent intent = new Intent(getActivity(), TimeReportActivity.class);
					getActivity().startActivity(intent);
				}
				else if(id == TIME_VIEW) {
					Intent intent = new Intent(getActivity(), SaleByDateActivity.class); //CustomerMainMenuActivity
					getActivity().startActivity(intent);
				}
				else if(id == Add_ACTIONS_DISPLAY) {
					TimeTrackerApplication.getInstance().setClose(false);
					TimeTrackerApplication.getInstance().setDoc(Add_ACTIONS_DISPLAY);
					Intent intent = new Intent(getActivity(), AddActionsMainMenuActivity.class);
					getActivity().startActivity(intent);

				}
				else if(id == Add_ACTION) {
					TimeTrackerApplication.getInstance().setClose(false);
					TimeTrackerApplication.getInstance().setDoc(Add_ACTION);
					Intent intent = new Intent(getActivity(), AddMainMenuActivity.class); //CustomerMainMenuActivity
					getActivity().startActivity(intent);
				}


				}

		});
}
	
	private void initilize(){
		// initialize menu
		menuItems=new ArrayList<MenuItem>();
		final String[] programs = getContext().getResources().getStringArray(R.array.programs);
		if(UniRequest.Super==1&& TimeTrackerApplication.getInstance().getAllMenu()==1||(UniRequest.LkC.equals("1")|| UniRequest.LkC.equals("14"))){//||UniRequest.LkC.equals("3244")
			int i=0;
			for (; i < programs.length; i++) {
				menuItems.add(i, new MenuItem(i,  0, programs[i]));
			}
			//menuItems.add(i, new MenuItem(i++,  R.string.definitions, null));
			//menuItems.add(i, new MenuItem(i,  R.string.exit, null));
		}else {

			MenuIphoneTask menuIphoneTask = new MenuIphoneTask(getActivity()) {
				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);

					if (result != null) {

						JSONObject response = null;

						try {
							response = new JSONObject(result);
							JSONArray chance_list = response.getJSONArray("Table");

							String code = null;
							String name = null;
							int i = 0;

							for (; i < chance_list.length(); i++) {
								code = chance_list.getJSONObject(i).getString("Kod");
								//name = chance_list.getJSONObject(i).getString("Nm");
								menuItems.add(i, new MenuItem(Integer.parseInt(code), 0, programs[Integer.parseInt(code)]));
							}
							// TODO fix menu item order???
							//menuItems.add(i, new MenuItem(CERTIFICATE_LOGIN_MEGA, 0, R.string.certificate_login_mega_title, null));

							return;
						} catch (JSONException e) {
							e.printStackTrace();

						}
					}
				}
			};
			try {
				menuIphoneTask.execute().get();
			} catch (Exception e) {}
}

//		if(UniRequest.LkC.equals("3149")){
//
//							menuItems.add(0,new MenuItem(SUPPLIERS_ORDER, 0, R.string.suppliers_order,null));
//									menuItems.add(1,new MenuItem(CERTIFICATE_LOGIN, 0, R.string.certificate_login_title,null));
//											menuItems.add(2,new MenuItem(STOCKTAKING, 0, R.string.stocktaking,null));
//													menuItems.add(3,new MenuItem(DEFINITIONS, 0, R.string.definitions,null));
//															menuItems.add(4,new MenuItem(EXIT, 0, R.string.exit,null));
//
//		}
//		else {
//
//
//			menuItems.add(0,new MenuItem(ID_SALES_TRACKER, 0, R.string.sales,null));
//					menuItems.add(1,new MenuItem(ID_TIME_TRACKER, 0, R.string.time,null));
//							menuItems.add(2,new MenuItem(ID_PRICE_TRACKER, 0, R.string.prices,null));
//									menuItems.add(3,new MenuItem(Add_ACTION, 0, R.string.add_action,null));
//											menuItems.add(4,new MenuItem(Add_ACTIONS_DISPLAY, 0, R.string.action,null));
//													menuItems.add(5,new MenuItem(CUSTOMER, 0, R.string.customerss,null));
//															menuItems.add(6,new MenuItem(SUPPLIERS_ORDER, 0, R.string.suppliers_order,null));
//																	menuItems.add(7,new MenuItem(CERTIFICATE_LOGIN, 0, R.string.certificate_login_title,null));
//																			menuItems.add(8,new MenuItem(STOCKTAKING, 0, R.string.stocktaking,null));
//																					menuItems.add(9,new MenuItem(ITEMS, 0, R.string.prts_photo,null));
//																							menuItems.add(10,new MenuItem(DEFINITIONS, 0, R.string.definitions,null));
//																									menuItems.add(11,new MenuItem(EXIT, 0, R.string.exit,null));

//
//
//		}
//
	}
}
