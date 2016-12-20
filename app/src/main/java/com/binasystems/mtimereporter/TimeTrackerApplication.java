package com.binasystems.mtimereporter;

import android.app.Application;

import com.splunk.mint.Mint;

public class TimeTrackerApplication extends Application {
	static TimeTrackerApplication instance;

	public void onCreate() {
		super.onCreate();
		instance = this;

		Mint.initAndStartSession(this, "69bcb2e5");
	};

	public static TimeTrackerApplication getInstace(){
		return instance;
	}

}
