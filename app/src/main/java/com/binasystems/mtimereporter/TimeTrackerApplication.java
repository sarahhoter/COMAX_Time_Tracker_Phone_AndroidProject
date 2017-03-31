package com.binasystems.mtimereporter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.PowerManager;

import com.binasystems.mtimereporter.objects.Branch;
import com.binasystems.mtimereporter.objects.Category;
import com.binasystems.mtimereporter.objects.Combo;
import com.binasystems.mtimereporter.objects.Customer;
import com.binasystems.mtimereporter.objects.Product;
import com.binasystems.mtimereporter.utils.PersistenceManager;
import com.binasystems.mtimereporter.utils.onScreenOffReceiver;
import com.splunk.mint.Mint;

public class TimeTrackerApplication extends Application {
	TimeTrackerApplication timeTrackerApplication;
	private PowerManager.WakeLock wakeLock;
	private onScreenOffReceiver onScreen;
	static final String PREF_KEY_SELECTED_DATE = "PREF_KEY_SELECTED_DATE";
	static final String PREF_KEY_SELECTED_HOUR = "PREF_KEY_SELECTED_HOUR";
	static final String PREF_KEY_SELECTED_DURING = "PREF_KEY_SELECTED_DURING";
	static final String PREF_KEY_SELECTED_UNTIL_HOUR = "PREF_KEY_SELECTED_UNTIL_HOUR";
	static final String PREF_KEY_SELECTED_TYPE = "PREF_KEY_SELECTED_TYPE";
	static final String PREF_KEY_SELECTED_STATUS = "PREF_KEY_SELECTED_STATUS";
	static final String PREF_KEY_SELECTED_SWTYPE = "PREF_KEY_SELECTED_SWTYPE";
	static final String PREF_KEY_SELECTED_SWSTATUS = "PREF_KEY_SELECTED_SWSTATUS";
	static final String PREF_KEY_SELECTED_LOCATION = "PREF_KEY_SELECTED_STATUS";
	static final String PREF_KEY_SELECTED_PRESENT = "PREF_KEY_SELECTED_STATUS";
	static final String PREF_KEY_SELECTED_DESCRIPTION = "PREF_KEY_SELECTED_STATUS";
	static final String PREF_KEY_SELECTED_SUM = "PREF_KEY_SELECTED_STATUS";
	static final String PREF_KEY_SELECTED_TREATMENT_DATE = "PREF_KEY_SELECTED_TREATMENT_DATE";
	static final String PREF_KEY_SELECTED_TREATMENT_HOUR = "PREF_KEY_SELECTED_TREATMENT_HOUR";
	static final String PREF_KEY_SELECTED_SUM_CLOSE = "PREF_KEY_SELECTED_SUM_CLOSE";
	static final String PREF_KEY_SELECTED_PRESENT_CLOSE = "PREF_KEY_SELECTED_PRESENT_CLOSE";
	static final String PREF_KEY_SELECTED_CHANCE = "PREF_KEY_SELECTED_CHANCE";
	static final String PREF_KEY_SELECTED_STATUS_CHANCE = "PREF_KEY_SELECTED_STATUS_CHANCE";
	static final String PREF_KEY_SELECTED_BRANCH = "PREF_KEY_SELECTED_BRANCH";
	static final String PREF_KEY_SELECTED_SUPPLIER = "PREF_KEY_SELECTED_SUPPLIER";
	static final String PREF_KEY_SELECTED_STORE = "PREF_KEY_SELECTED_STORE";
	static final String PREF_KEY_SELECTED_CUSTOMER = "PREF_KEY_SELECTED_CUSTOMER";
	static final String PREF_KEY_SELECTED_MLAYDOCMSOFONC = "PREF_KEY_SELECTED_MLAYDOCMSOFONC";
	static final String PREF_KEY_SELECTED_PRODUCT = "PREF_KEY_SELECTED_PRODUCT";
	static final String PREF_KEY_SELECTED_DOCTYPE = "PREF_KEY_SELECTED_DOCTYPE";
	static final String PREF_KEY_SELECTED_ALLMENU = "PREF_KEY_SELECTED_ALLMENU";
	static final String PREF_KEY_SELECTED_MHRC  = "PREF_KEY_SELECTED_MHRC";
	static final String PREF_KEY_SELECTED_SEND  = "PREF_KEY_SELECTED_SEND";
	static final String PREF_KEY_SELECTED_EDI  = "PREF_KEY_SELECTED_EDI";
	static final String PREF_KEY_SELECTED_SIGN  = "PREF_KEY_SELECTED_SIGN";
	static final String PREF_KEY_SELECTED_GIVUNMODE  = "PREF_KEY_SELECTED_GIVUNMODE";
	static final String PREF_KEY_SELECTED_CLOSE  = "PREF_KEY_SELECTED_CLOSE";

	static TimeTrackerApplication instance;
	PersistenceManager mPersistenceManager;
	//Add Action
	private String date = "";
	private String hour = "";
	private String during = "";
	private String until_hour = "";
	private String swtype = "";
	private String swstatus = "";
	private String type = "";
	private String status = "";
	private String location = "";
	private String sendEnail = "";
	private String description = "";
	private String present = "";
	private String sum = "";
	private String treatmentDate = "";
	private String treatmentHour = "";
	private String sumClose = "";
	private String presentClose = "";
	private Combo chance = null;
	private String statusChance = "";
	public  Category branch = null;
	public  Branch store = null;
	public Customer lastCustomer = null;
	public Product product = null;
	public Uri image = null;
	public long doc=0;
	public int allMenu=0;
	public int mlayDoc_MsofonC=0;
	public long givunMode=0;
	public String MhrC =null;
	public Boolean edi =false;
	public Boolean sign =false;
	public Boolean close =false;

	public String getDate() {
		if(date == null){
		// load from local LkC
			date = mPersistenceManager.getString(PREF_KEY_SELECTED_DATE);
		}
		return date;

	}

	public Boolean getClose() {
		if(close == null){
			// load from local LkC
			close =Boolean.valueOf(mPersistenceManager.getString(mPersistenceManager.getString(PREF_KEY_SELECTED_CLOSE)));
		}
		return close;
	}

	public void setClose(Boolean close) {

		this.close = close;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_CLOSE,close.toString());
	}

	public Boolean getSign() {
		if(sign == null){
			// load from local LkC
			sign =Boolean.valueOf(mPersistenceManager.getString(mPersistenceManager.getString(PREF_KEY_SELECTED_SIGN)));
		}
		return sign;
	}

	public void setSign(Boolean sign) {
		this.sign = sign;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_SIGN,sign.toString());
	}

	public String getSendEnail() {
		if(sendEnail == null){
			// load from local LkC
			sendEnail = mPersistenceManager.getString(PREF_KEY_SELECTED_SEND);
		}
		return sendEnail;
	}

	public void setSendEnail(String sendEnail) {
		this.sendEnail = sendEnail;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_SEND, sendEnail);
	}

	public Uri getImage() {
		return image;
	}

	public void setImage(Uri image) {
		this.image = image;
	}

	public Customer getLastCustomer() {
		if(lastCustomer == null){
			lastCustomer = mPersistenceManager.getObject(PREF_KEY_SELECTED_CUSTOMER,Customer.class);
		}
		return lastCustomer;
	}

	public void setLastCustomer(Customer lastCustomer) {
		this.lastCustomer = lastCustomer;
		mPersistenceManager.saveObject(PREF_KEY_SELECTED_CUSTOMER, lastCustomer);
	}

	public void setDate(String date) {
		this.date=date;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_DATE, date);

	}

	public Product getProduct() {
		if(product == null){
			product = mPersistenceManager.getObject(PREF_KEY_SELECTED_PRODUCT,Product.class);
		}
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
		mPersistenceManager.saveObject(PREF_KEY_SELECTED_PRODUCT, product);
	}

	public String getHour() {
		if(hour == null){
			// load from local LkC
			hour = mPersistenceManager.getString(PREF_KEY_SELECTED_HOUR);
		}
		return hour;
	}

	public int getMlayDoc_MsofonC() {


		return mlayDoc_MsofonC;
	}

	public void setMlayDoc_MsofonC(int mlayDoc_MsofonC) {

		this.mlayDoc_MsofonC = mlayDoc_MsofonC;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_MLAYDOCMSOFONC, String.valueOf(mlayDoc_MsofonC));


	}

	public void setHour(String hour) {
		this.hour = hour;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_HOUR, hour);
	}

	public String getDuring() {
		if(during == null){
			// load from local LkC
			during = mPersistenceManager.getString(PREF_KEY_SELECTED_DURING);
		}
		return during;
	}

	public void setDuring(String during) {
		this.during = during;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_DURING, during);
	}

	public String getUntil_hour() {
		if(until_hour == null){
			// load from local LkC
			until_hour = mPersistenceManager.getString(PREF_KEY_SELECTED_UNTIL_HOUR);
		}
		return until_hour;
	}

	public void setUntil_hour(String until_hour) {
		this.until_hour = until_hour;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_UNTIL_HOUR, until_hour);
	}
	public void setCurrentActivity(Activity currentActivity) {
		this.currentActivity = currentActivity;
	}

	public String getType() {
		if(type == null){
			// load from local LkC
			type = mPersistenceManager.getString(PREF_KEY_SELECTED_TYPE);
		}
		return type;
	}

	public void setType(String type) {
		this.type = type;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_TYPE, type);
	}

	public String getStatus() {
		if(status == null){
			// load from local LkC
			status = mPersistenceManager.getString(PREF_KEY_SELECTED_STATUS);
		}
		return status;
	}


	public long getGivunMode() {
//		if(givunMode == 0){
//			// load from local LkC
//			givunMode =Long.getLong(mPersistenceManager.getString(PREF_KEY_SELECTED_GIVUNMODE));
//		}

		return givunMode;
	}

	public void setGivunMode(long givunMode) {
		this.givunMode = givunMode;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_GIVUNMODE, String.valueOf(givunMode));

	}

	public String getLocation() {
		if(location == null){
			// load from local LkC
			location = mPersistenceManager.getString(PREF_KEY_SELECTED_LOCATION);
		}
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_LOCATION, location);
	}

	public String getDescription() {
		if(description == null){
			// load from local LkC
			description = mPersistenceManager.getString(PREF_KEY_SELECTED_DESCRIPTION);
		}
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_DESCRIPTION, description);
	}

	public String getPresent() {
		if(present == null){
			// load from local LkC
			present = mPersistenceManager.getString(PREF_KEY_SELECTED_PRESENT);
		}
		return present;
	}

	public void setPresent(String present) {
		this.present = present;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_PRESENT, status);
	}

	public String getSum() {
		if(sum == null){
			// load from local LkC
			sum = mPersistenceManager.getString(PREF_KEY_SELECTED_SUM);
		}
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_SUM, sum);
	}

	public void setStatus(String status) {
		this.status = status;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_STATUS, status);
	}

	public String getTreatmentDate() {
		if(treatmentDate == null){
			// load from local LkC
			treatmentDate = mPersistenceManager.getString(PREF_KEY_SELECTED_TREATMENT_DATE);
		}
		return treatmentDate;
	}

	public void setTreatmentDate(String treatmentDate) {
		this.treatmentDate = treatmentDate;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_TREATMENT_DATE, treatmentDate);
	}

	public String getTreatmentHour() {
		if(treatmentHour == null){
			// load from local LkC
			treatmentHour = mPersistenceManager.getString(PREF_KEY_SELECTED_TREATMENT_HOUR);
		}
		return treatmentHour;
	}

	public void setTreatmentHour(String treatmentHour) {
		this.treatmentHour = treatmentHour;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_TREATMENT_HOUR, treatmentHour);
	}

	public String getSumClose() {
		if(sumClose == null){
			// load from local LkC
			sumClose = mPersistenceManager.getString(PREF_KEY_SELECTED_SUM_CLOSE);
		}
		return sumClose;
	}

	public void setSumClose(String sumClose) {
		this.sumClose = sumClose;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_SUM_CLOSE, sumClose);
	}

	public String getPresentClose() {
		if(presentClose == null){
			// load from local LkC
			presentClose = mPersistenceManager.getString(PREF_KEY_SELECTED_PRESENT_CLOSE);
		}
		return presentClose;
	}

	public void setPresentClose(String presentClose) {
		this.presentClose = presentClose;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_PRESENT_CLOSE, presentClose);
	}

	public Combo getChance() {
		if(chance == null){
			// load from local LkC
			chance = mPersistenceManager.getObject(PREF_KEY_SELECTED_CHANCE,Combo.class);
		}
		return chance;
	}

	public void setChance(Combo chance) {
		this.chance = chance;
		mPersistenceManager.saveObject(PREF_KEY_SELECTED_CHANCE, chance);

	}

	public String getStatusChance() {
		if(statusChance == null){
			// load from local LkC
			statusChance = mPersistenceManager.getString(PREF_KEY_SELECTED_STATUS_CHANCE);
		}
		return statusChance;
	}

	public void setStatusChance(String statusChance) {
		this.statusChance = statusChance;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_STATUS_CHANCE, statusChance);

	}

	public String getSwtype() {
		if(swtype == null){
			// load from local LkC
			swtype = mPersistenceManager.getString(PREF_KEY_SELECTED_SWTYPE);
		}
		return swtype;
	}

	public void setSwtype(String swtype) {
		this.swtype = swtype;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_SWTYPE, swtype);
	}

	public String getSwstatus() {
		if(swstatus == null){
			// load from local LkC
			swstatus = mPersistenceManager.getString(PREF_KEY_SELECTED_SWSTATUS);
		}
		return swstatus;
	}

	public void setSwstatus(String swstatus) {
		this.swstatus = swstatus;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_SWSTATUS, swstatus);
	}

	public  Category getBranch() {
		if(branch == null){
			// load from local LkC
			branch = mPersistenceManager.getObject(PREF_KEY_SELECTED_BRANCH, Category.class);
		}
		return branch;
	}

	public  void setBranch(Category branch) {
		this.branch = branch;
		mPersistenceManager.saveObject(PREF_KEY_SELECTED_BRANCH, branch);

	}

	public Branch getStore() {
		if(store == null){
			// load from local LkC
			store = mPersistenceManager.getObject(PREF_KEY_SELECTED_STORE, Branch.class);
		}
		return store;
	}

	public void setStore(Branch store) {
		this.store = store;
		mPersistenceManager.saveObject(PREF_KEY_SELECTED_STORE, store);
	}

	public long getDoc() {
//		if(doc == 0){
//			// load from local LkC
//			doc =Long.getLong(mPersistenceManager.getString(PREF_KEY_SELECTED_DOCTYPE));
//		}
		return doc;
	}

	public void setDoc(long doc) {
		this.doc = doc;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_DOCTYPE, String.valueOf(doc));

	}

	public int getAllMenu() {
		if(allMenu == 0){
			// load from local LkC
			allMenu =Integer.getInteger(mPersistenceManager.getString(PREF_KEY_SELECTED_ALLMENU));
		}
		return allMenu;
	}

	public void setAllMenu(int allMenu) {
		this.allMenu = allMenu;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_ALLMENU, String.valueOf(allMenu));

	}

	public String getMhrC() {
		if(MhrC == null){
			// load from local LkC
			MhrC =mPersistenceManager.getString(mPersistenceManager.getString(PREF_KEY_SELECTED_MHRC));
		}
		return MhrC;
	}

	public void setMhrC(String mhrC) {
		MhrC = mhrC;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_MHRC,mhrC);

	}

	public Boolean getEdi() {
		if(edi == null){
			// load from local LkC
			edi =Boolean.valueOf(mPersistenceManager.getString(mPersistenceManager.getString(PREF_KEY_SELECTED_EDI)));
		}
		return edi;
	}

	public void setEdi(Boolean edi) {
		this.edi = edi;
		mPersistenceManager.saveString(PREF_KEY_SELECTED_EDI,edi.toString());

	}

	Activity currentActivity;
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		Mint.initAndStartSession(this, "b9a6d5e4");
		mPersistenceManager = new PersistenceManager(this);
		TimeTrackerApplication.instance = this;


			registerKioskModeScreenOffReceiver();
		}

		private void registerKioskModeScreenOffReceiver() {
			// register screen off receiver
			final IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
			onScreen = new onScreenOffReceiver();
			registerReceiver(onScreen, filter);
		}

		public PowerManager.WakeLock getWakeLock() {
			if(wakeLock == null) {
				// lazy loading: first call, create wakeLock via PowerManager.
				PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
				wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "wakeup");
			}
			return wakeLock;
		}


	public Activity getCurrentActivity(){
		return currentActivity;
	}
	public static TimeTrackerApplication getInstance(){
		return instance;
	}

}
