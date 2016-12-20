package com.binasystems.mtimereporter.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	private static String httpUserAgent = null;

	public static void showErrorDialog(Context context, String title,
			String message) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setNegativeButton("Ok", null);
		builder.show();
	}

	public static boolean checkInternetConnection(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		
		if(ni != null){
			return ni.isConnected();
		}
		
		return false;
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

					httpUserAgent = String.format("%s/%s(%s) (%s; android version: %s)", appName, appVersionName, appVersionCode, deviceName, osVersion);
				}
			}
		}

		return httpUserAgent;
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
	}
}
