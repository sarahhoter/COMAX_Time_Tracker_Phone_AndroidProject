package com.binasystems.mtimereporter.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPref {
	
	
	
	public static String getCurrentUser(Context context){
		SharedPreferences pref =  context.getSharedPreferences("com.binasystems.timereporter", Context.MODE_PRIVATE);
		return pref.getString("username", "");
	}	
	public static void setCurrentUser(Context context, String user){
		SharedPreferences pref =  context.getSharedPreferences("com.binasystems.timereporter", Context.MODE_PRIVATE);
		pref.edit().putString("username", user).commit();
	}
	
	public static void saveLoginData(Context context, String company, String login, String pass){
		SharedPreferences pref =  context.getSharedPreferences("com.binasystems.timereporter", Context.MODE_PRIVATE);
		pref.edit().putString("login_company", company).commit();
		pref.edit().putString("login_login", login).commit();
		pref.edit().putString("login_password", pass).commit();
	}
	
	public static String getLofinLogin(Context context){
		SharedPreferences pref =  context.getSharedPreferences("com.binasystems.timereporter", Context.MODE_PRIVATE);
		return pref.getString("login_login", "");
	}
	
	public static String getLoginPassword(Context context){
		SharedPreferences pref =  context.getSharedPreferences("com.binasystems.timereporter", Context.MODE_PRIVATE);
		return pref.getString("login_password", "");
	}
	
	public static String getLofinCompany(Context context){
		SharedPreferences pref =  context.getSharedPreferences("com.binasystems.timereporter", Context.MODE_PRIVATE);
		return pref.getString("login_company", "");
	}

	public static boolean isLoginRememberMe(Context context){
		SharedPreferences pref =  context.getSharedPreferences("login_company", Context.MODE_PRIVATE);
		return pref.getBoolean("login_rememberme", false);
	}

	public static void setLoginRememberMe(Context context, boolean value){
		SharedPreferences pref =  context.getSharedPreferences("login_company", Context.MODE_PRIVATE);
		pref.edit().putBoolean("login_rememberme", value).commit();
	}
	public static boolean isLoginRememberPass(Context context){
		SharedPreferences pref =  context.getSharedPreferences("login_company", Context.MODE_PRIVATE);
		return pref.getBoolean("login_rememberpass", false);
	}

	public static void setLoginRememberPass(Context context, boolean value){
		SharedPreferences pref =  context.getSharedPreferences("login_company", Context.MODE_PRIVATE);
		pref.edit().putBoolean("login_rememberpass", value).commit();
	}
	
}
