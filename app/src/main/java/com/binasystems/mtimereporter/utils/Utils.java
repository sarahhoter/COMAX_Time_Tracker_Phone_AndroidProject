package com.binasystems.mtimereporter.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.api.requests.UniRequest;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	private static String httpUserAgent;
	private static Integer versionCode;
	public static void showErrorDialog(Context context, String title,
			String message) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setNegativeButton("Ok", null);
		builder.show();
	}
	public static void closeKeyboard(View view) {
		// Check if no view has focus:
		if (view != null && view.getWindowToken() != null) {
			InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	public static int getApplicationVersionCode(Context context){
		if(versionCode == null){
			try {
				PackageManager packageManager = context.getPackageManager();
				PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
				versionCode = packageInfo.versionCode;
			} catch (Exception e) {
			}
		}

		return versionCode != null ? versionCode : 0;
	}
	public static boolean checkInternetConnection(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		
		if(ni != null){
			return ni.isConnected();
		}
		
		return false;
	}

	public static int dpToPx(Context context, int dp) {
		float density =  context.getResources().getDisplayMetrics().density;
		return Math.round((float)dp * density);
	}

	public static String getUserAgent(Context context){
		if(httpUserAgent == null){
			synchronized (Utils.class){
				if(httpUserAgent == null){
					// pattern: appName/appVersionName(appVersionCode) (deviceName; androidVersion)
					int stringId = context.getApplicationInfo().labelRes;
					String appName = context.getString(stringId);
					String appVersionName = "";
					String appVersionCode = "";
					String deviceName = Build.MODEL;
					String osVersion = Build.VERSION.RELEASE;
					try {
						PackageManager manager = context.getPackageManager();
						PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
						appVersionName = info.versionName;
						appVersionCode= String.valueOf(info.versionCode);
					} catch (PackageManager.NameNotFoundException e) {
						e.printStackTrace();
					}

					httpUserAgent = String.format("%s/%s(%s) (%s; android version: %s)", "comax-sales", appVersionName, appVersionCode, deviceName, osVersion);
				}
			}
		}

		return httpUserAgent;
	}
	public static String getStackTraceAsText(Throwable throwable){

		if(throwable != null){
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			PrintWriter writer = new PrintWriter(bos);
			throwable.printStackTrace(writer);
			return new String(bos.toByteArray());
		}

		return "";
	}
	public static String formatItemImageURL(String url, String item,ImageSize imageSize){
		// Pattern: http://www.comax.co.il/Max2000Upload/[Lk]/Prt_Pic/[Company]/[itemC].jpg
		String pic=url.substring(url.lastIndexOf("\\") + 1);
		String ur=String.format("http://www.comax.co.il/Max2000Upload/%s/Prt_Pic/%s", UniRequest.LkC, TimeTrackerApplication.getInstance().getBranch().getC());
		String C=item;
		switch (imageSize){
			case ImageSizeBig:
				return String.format("%s/%s.jpg", ur, C);
			case ImageSizeMedium:
			case ImageSizeSmall:
			default:
				return String.format("%s/_%s.jpg", ur, C);
		}




	}
	public static String getVersionName(Context context) {
		String app_ver=null;
		try {
			 app_ver = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return app_ver;

	}
	public static String currentDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		// get current date time with Date()
		Date date = new Date();
		// System.out.println(dateFormat.format(date));
		// don't print it, but save it!
		return dateFormat.format(date);

	}    public static String getApplicationName(Context ctx) {
		String p=null;
		try {
			PackageManager packageManager = ctx.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);
			p=packageInfo.packageName;

		}catch (Exception e){}
		return p;


	}
}
