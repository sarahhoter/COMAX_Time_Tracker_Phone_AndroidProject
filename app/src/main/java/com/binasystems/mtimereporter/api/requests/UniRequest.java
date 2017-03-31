package com.binasystems.mtimereporter.api.requests;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.client.methods.HttpPost;

import android.util.Log;

import com.binasystems.mtimereporter.objects.Attribute;
import com.binasystems.mtimereporter.objects.Branch;
import com.binasystems.mtimereporter.objects.Customer;
import com.binasystems.mtimereporter.objects.Product;
import com.binasystems.mtimereporter.objects.Project;
import com.binasystems.mtimereporter.objects.Status;

/**
 * Class used for collect httpPost headers and parameters.<br>
 * with encrypted request information. It used with {@link PostRequest} class<br>
 * constructor: <br>
 * UniRequest(String, String); <br>
 * Methods:<br>
 * addLine(String, String);<br>
 * (HashMap<String, Object>) getHttpHeadersMap()<br>
 * (HashMap<String, Object>) getHttpParamsMap()<br>
 * (String) getUrl()<br>
 **/

public class UniRequest {
	
	public static class MyHttpPostTransfer extends HttpPost{
		
		HashMap<String, Object> mParams;
		HashMap<String, Object> mHeaders;

		public MyHttpPostTransfer(String url, HashMap<String, Object> mHeaders, 
				HashMap<String, Object> mParams) {
			super(url);
			this.mParams = mParams;
			this.mHeaders = mHeaders;
		}

		public MyHttpPostTransfer() {
			super();
		}


		public MyHttpPostTransfer(String uri) {
			super(uri);
		}


		public MyHttpPostTransfer(URI uri) {
			super(uri);
		}


		public HashMap<String, Object> getParamsAsMap(){
			return mParams;
		}
		
		public HashMap<String, Object> getHeadersAsMap(){
			return mHeaders;
		}
	}
	
	/*
	 * Credentials
	 */
	public static String LkC = "";
	public static String SwSQL = "";
	public static String UserC = "";
	public static String LogC = "";
	public static Integer Super =0;
	public static Integer SwBlockToBuyPrices = 0;
	public static String UserName = "";
	
	/*
	 * Work with models
	 */
	public static boolean workWithModels = false;
	
	/*
	 * Branch
	 */

	public static Branch store = null;
	public static String dep = null;
	public static String agent = null;


	/*
	 * Customer
	 */
	public static Customer customer = null;
	public static String getCustomerCode(){
		return customer != null ? customer.getCode() : "0";
	}

	/*
	 * Product
	 */
	public static Product product = null;
	public static Project project = null;

	/*
	 * Permisions
	 */
	public static final short USER = 1;
	public static final short ADMIN = 2;

	/*
	 * Array list of last selected customers
	 */
	public static ArrayList<Customer> lastCustomers = new ArrayList<Customer>();

	public static ArrayList<Status> statuses = null;
	public static ArrayList<Attribute> attributes = null;

	public static short role = USER;
	public static boolean[][] access = new boolean[9][10];

	static {

		for (int i = 0; i < access.length; i++)
			for (int j = 0; j < access.length; j++)
				access[i][j] = false;

	}

//	private ArrayList<BasicNameValuePair> postLS;
	private String url = null;
	
	private HashMap<String, Object> mHeadersMap;
	private HashMap<String, Object> mParamsMap;

	/**
	 * 
	 * @param table
	 * <br>
	 *            contains the ending of the destination URL
	 * @param comand
	 * <br>
	 *            command type.
	 */

	public UniRequest(String table, String comand) {
		Log.w("atf","START REQUEST");
		
		url = ServerChooser.getUrl() + table;
		
		addHeader("Device", "Ipad");
		
//		post.addHeader("Device", "Ipad");
//		postLS = new ArrayList<BasicNameValuePair>();
//		postLS.add(new BasicNameValuePair("cmd", Encrypter.encrypt(comand)));
//		addParameter("cmd", Encrypter.encrypt(comand));

		addParameter("cmd", comand);    
	}
	
	public void addHeader(String name, String value){		
		if(name == null)
			return;
		
		// lazy initialization
		if(mHeadersMap == null) 
			mHeadersMap = new HashMap<String, Object>();
		
		mHeadersMap.put(name, value);
	}
	
	private void addParameter(String name, String value){
		
		if(name == null)
			return;
		
		if(mParamsMap == null)
			mParamsMap = new HashMap<String, Object>();
		mParamsMap.put(name, value);
	}
	
	public HashMap<String, Object> getHttpHeadersMap(){
		return mHeadersMap;
	}
	
	public HashMap<String, Object> getHttpParamsMap(){
		return mParamsMap;
	}
	
	/**
	 * Return service url
	 * 
	 * @return url
	 */
	public String getUrl(){
		return url;
	}

	/**
	 * Adds a line of encrypted information in HttpPost request with a specific
	 * key.
	 * 
	 * @param key
	 * <br>
	 *            the key of encrypted information
	 * @param value
	 * <br>
	 *            String to be encrypted and attached to the HttpPost.
	 */
	public void addLine(String key, String value) {
		if(value != null){
//			addParameter(key, Encrypter.encrypt(value));
			addParameter(key, value);
			
		} else{
			addParameter(key, null);			
		}

	}

	/**
	 * 
	 * @return the created HttpPost object
	 */
//	public HttpPost getPost() {
//		try {
//			post.setEntity(new UrlEncodedFormEntity(postLS));
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
//		return post;
//	}
	
	public static boolean isLogged() {
		if (LkC == null || LkC.isEmpty() || SwSQL == null || SwSQL.isEmpty()
				|| UserC == null || UserC.isEmpty())
			return false;

		return true;

	}

//	public static boolean isOnBranch() {
//		if (branch == null)
//			return false;
//
//		return true;
//	}
//
//	public static void logout() {
//		LkC = "";
//		SwSQL = "";
//		UserC = "";
//		Log.i("MainMenuFragment", "LOGOUT");
//		branch = null;
//	}

	/**
	 * This method is deprecated and used only for back compatibility
	 * 
	 * @return 
	 * 		apache http post object
	 */
	@Deprecated
	public HttpPost getPost() {		
		HttpPost post = new MyHttpPostTransfer(getUrl(), getHttpHeadersMap(), getHttpParamsMap());
		return post;
	}
	
}
