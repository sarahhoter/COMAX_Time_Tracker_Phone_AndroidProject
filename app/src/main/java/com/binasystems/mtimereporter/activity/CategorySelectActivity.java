package com.binasystems.mtimereporter.activity;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.binasystems.mtimereporter.CategoriesListAdapter;
import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.utils.LoggerFacade;
import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.api.requests.LoadCategoriesTask;
import com.binasystems.mtimereporter.api.requests.UniRequest;
import com.binasystems.mtimereporter.objects.Category;
import com.binasystems.mtimereporter.utils.UserCredintails;


public class CategorySelectActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener {
	
	/*
	 * UI Elements
	 */
	private ListView list = null;

	private CategoriesListAdapter list_adapter = null;
	SearchView searchView;
	
	Handler mHandler = new Handler();
	ActionBar actionBar;
	String filterText;

	LoadCategoriesTask mLoadCategoryTask;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		actionBar=getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);

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
				mHandler.postDelayed(mFilterCategoryAction, 1000);
				return true;
			}
		});
		
		/*
		 * Load elements
		 */
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
			builder.setTitle("Confirmation Dialog");
			builder.setMessage("Are you sure you wish to log out?");
			builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					closeActivity();
				}
			});
			builder.setNegativeButton("NO", null);
			builder.show();
			
		} else{
			closeActivity();
		}
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
			openTimeReporterFormCategory(categories_list.get(0));			
		}				
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.category_back: {

			onBackPressed();

		}
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		UserCredintails userCredintails = UserCredintails.getInstance(TimeTrackerApplication.getInstace());
		if (!userCredintails.isLogged()) {

			Toast.makeText(this, "Please relogin", Toast.LENGTH_SHORT)
					.show();

			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();

		}						
				
		openTimeReporterFormCategory((Category) arg0.getItemAtPosition(arg2));
	}
	
	private void openTimeReporterFormCategory(Category category){

        LoggerFacade.leaveBreadcrumb("openTimeReporterFormCategory " + category);
		UserCredintails userCredintails = UserCredintails.getInstance(TimeTrackerApplication.getInstace());
		userCredintails.StoreN = category.getName();
		userCredintails.StoreC = category.getC();
		userCredintails.saveState(TimeTrackerApplication.getInstace());

		Intent intent = new Intent(this, TimeReportActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.top_slide_in, R.anim.bottom_slide_out);
		
		finish();
	}
	
	private void doLoadCompaniesAction() {
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
}
