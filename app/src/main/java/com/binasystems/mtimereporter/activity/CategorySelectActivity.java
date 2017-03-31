package com.binasystems.mtimereporter.activity;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.adapter.CategoriesListAdapter;
import com.binasystems.mtimereporter.api.requests.LoadCategoriesTask;
import com.binasystems.mtimereporter.api.requests.UniRequest;
import com.binasystems.mtimereporter.objects.Category;
import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.utils.AppPref;
import com.binasystems.mtimereporter.utils.LoggerFacade;
import com.binasystems.mtimereporter.utils.UserCredintails;

public class CategorySelectActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener {
	
	/*
	 * UI Elements
	 */
	private ListView list = null;
	private TextView cancel;
	private CategoriesListAdapter list_adapter = null;
	SearchView searchView;
	
	Handler mHandler = new Handler();

	String filterText="";
	ActionBar actionBar;
	LoadCategoriesTask mLoadCategoryTask;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.view_action_bar);
		View v = getActionBar().getCustomView();
		TextView titleTxtView = (TextView) v.findViewById(R.id.action_bar_textView_title);
		cancel=(TextView)v.findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
		titleTxtView.setText(AppPref.getCurrentUser(this));

		searchView = (SearchView) findViewById(R.id.searchView);
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
		searchView.setQueryHint(getResources().getString(R.string.title_activity_brances));
		actionBar=getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);

		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			
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
				mHandler.postDelayed(mFilterCategoryAction, 3000);
				return true;
			}
		});
		
		/*
		 * Load elements
		 */
		AutoCompleteTextView search_text = (AutoCompleteTextView) searchView.findViewById(searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null));
		search_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.search_view_size));
		list = (ListView) findViewById(R.id.category_list);

		/*
		 * Set listeners
		 */
		list.setOnItemClickListener(this);
		
		/*
		 * Set retro style for search fields
		 * Remove focus line from Search field.
		 */
		doLoadCompaniesAction();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {		
		if(item.getItemId() == android.R.id.home){
			logout(true);
			
			return true;
		}		
		return super.onOptionsItemSelected(item);
	}
			
	private void logout(boolean withConfirmation){


		if(withConfirmation){
			Builder builder = new Builder(this);
			builder.setMessage(R.string.msgLogout);
			builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					close();
				}
			});

			builder.setNegativeButton(R.string.no, null);
			builder.show();


		} else {
			close();

		}
	}
	private void close(){
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}
	public void closeActivity(){
		overridePendingTransition(R.anim.left_slide_in,
				R.anim.right_slide_out);						
				finish();								
	}
	
	@Override
	public void onBackPressed() {
		logout(true);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	public void setCategoriesList(ArrayList<Category> categories_list) {		
		list_adapter = new CategoriesListAdapter(this, R.id.category_item_code,
				categories_list);
		list.setAdapter(list_adapter);
		
		if (categories_list.size() == 1) {
			if(filterText.toString().equals("")) {
				TimeTrackerApplication.getInstance().setAllMenu(1);
			}else TimeTrackerApplication.getInstance().setAllMenu(2);
			openTimeReporterFormCategory(categories_list.get(0));

		}
		else TimeTrackerApplication.getInstance().setAllMenu(2);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.category_back: {

			onBackPressed();

		}
			break;
			case R.id.cancel: {

				onBackPressed();

			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		UserCredintails userCredintails = UserCredintails.getInstance(TimeTrackerApplication.getInstance());

		if (!UniRequest.isLogged()) {

			Toast.makeText(this, "Please relogin", Toast.LENGTH_SHORT)
					.show();

			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();

		}						
				
		openTimeReporterFormCategory((Category) adapterView.getItemAtPosition(position));
	}
	
	private void openTimeReporterFormCategory(Category category){
		TimeTrackerApplication.getInstance().setBranch(category);
		LoggerFacade.leaveBreadcrumb("openTimeReporterFormCategory " + category);
		UserCredintails userCredintails = UserCredintails.getInstance(TimeTrackerApplication.getInstance());
		userCredintails.StoreN = category.getName();
		userCredintails.StoreC = category.getC();
		userCredintails.saveState(TimeTrackerApplication.getInstance());

		Intent intent = new Intent(this, MainMenuActivity.class); //BranchSelectActivity
		startActivity(intent);
		overridePendingTransition(R.anim.top_slide_in, R.anim.bottom_slide_out);
		UniRequest.lastCustomers.clear();
	}
	
	private void doLoadCompaniesAction() {
		Resources appR = getResources();
		CharSequence txt = appR.getText(appR.getIdentifier("app_name",
				"string", getPackageName()));
		if(mLoadCategoryTask == null ||
				mLoadCategoryTask.getStatus() != AsyncTask.Status.RUNNING){

			mLoadCategoryTask = new LoadCategoriesTask(this);
			mLoadCategoryTask.execute(filterText);			
		}
	}
	
		private Runnable mFilterCategoryAction = new Runnable() {

			@Override
			public void run() {
				doLoadCompaniesAction();
			}
		};







/*	class CategoriesSelectAdatpter extends BaseAdapter {
		List<SalesByStoreDetails.StoreInfo> mData;
		Context mContext;
		LayoutInflater mInflater;
		Formatter mFormatter;
		SalesByStoreDetails mSalesByStoreDetails;


		public CategoriesSelectAdatpter(Context context,
									SalesByStoreDetails salesByStoreDetails) {
			this.mSalesByStoreDetails = salesByStoreDetails;
			this.mData = salesByStoreDetails.getStoreListInfo();
			this.mContext = context;
			this.mInflater = LayoutInflater.from(context);
			this.mFormatter = Formatter.getInstance(context);
		}

		public List<SalesByStoreDetails.StoreInfo> getDataList() {
			return mData;
		}

		public void updateCategoriesSelect(
				SalesByStoreDetails salesByStoreDetails) {
			this.mSalesByStoreDetails = salesByStoreDetails;
			if (mData != null) {
				mData.addAll(salesByStoreDetails.getStoreListInfo());
			} else {
				mData = salesByStoreDetails.getStoreListInfo();
			}

			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			if (mData != null)
				return mData.size();

			return 0;
		}

		@Override
		public Object getItem(int position) {
			if (mData != null)
				return mData.get(position);

			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Tag tag;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.sales_by_store_list_item, null);
				tag = new Tag();
				tag.textView1 = (TextView) convertView.findViewById(R.id.textView1);
				tag.textView2 = (TextView) convertView.findViewById(R.id.textView2);
				tag.textView3 = (TextView) convertView.findViewById(R.id.textView3);
				convertView.setTag(tag);

			} else {
				tag = (Tag) convertView.getTag();
			}

			Object item = mData.get(position);
			if (item != null) {
				SalesByStoreDetails.StoreInfo storeInfo = (SalesByStoreDetails.StoreInfo) item;
				// sum percent
				Double totalSumInPercent = null;
				double totalSales = mSalesByStoreDetails.getSalesInfo().TotalSales != null ? mSalesByStoreDetails
						.getSalesInfo().TotalSales : 0d;
				if (storeInfo.Scm != null && totalSales != 0) {
					totalSumInPercent = storeInfo.Scm / totalSales;
				}
				tag.textView1.setText(mFormatter
						.formatPercent(totalSumInPercent));
				tag.textView2.setText(mFormatter.formatValue(storeInfo.Scm));
				tag.textView3.setText(mFormatter.formatValue(storeInfo.StoreNm));

				if (position == getCount() - 1
						&& mSalesByStoreDetails.getHasMoreRows()) {

					// load next data
					loadData(String.valueOf(storeInfo.StoreC), false);
				}
			}

			return convertView;
		}

		class Tag {
			TextView textView1;
			TextView textView2;
			TextView textView3;
		}
	}*/
}
