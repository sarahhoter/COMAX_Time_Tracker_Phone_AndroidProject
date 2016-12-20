package com.binasystems.mtimereporter.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class BSLocationManager {	
	private static final long LOCATION_UPDATE_TIME = 0;
	private static final long LOCATION_UPDATE_TIMEOUT = 30 * 1000l;
	
	public static interface LocationCallback{
		public void onLocationResult(Location location);
	}
	
	private LocationManager mLocationManager;
	private Location mLastLocation;
	private Handler mHandler = new Handler();
	
	public BSLocationManager(Context context){
		mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	}
	
	public void getCurrentLocation(final LocationCallback callback){
		
		if(!isLocationServiceEnabled()){
			Log.w("MLocationService", "Can't get location service. disabled");
			
			callback.onLocationResult(null);
		}
		
		TimeoutLocationUpdateListener locationUpdateListener = new TimeoutLocationUpdateListener(callback);
		mHandler.postDelayed(locationUpdateListener, LOCATION_UPDATE_TIMEOUT);
								
		Location gpsLastLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		Location networkLastLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);		

		if(gpsLastLocation != null && networkLastLocation != null){
			if(isBetterLocation(gpsLastLocation, networkLastLocation)){				
				mLastLocation = gpsLastLocation;				
			} else{
				mLastLocation = networkLastLocation;
			}
		} else if(gpsLastLocation != null){
			mLastLocation = gpsLastLocation;
		} else {
			mLastLocation = networkLastLocation;
		}
		
		try {
			mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_UPDATE_TIME, 0, locationUpdateListener);
			mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_UPDATE_TIME, 0, locationUpdateListener);
		} catch (Exception e) {
			if(callback != null){
				callback.onLocationResult(null);
			}
		}				
	}
	
	public Location getLastLocation(){
		return mLastLocation;
	}
	
	public boolean isLocationServiceEnabled(){
		return (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || 
				mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
	}
		
	private static final int TWO_MINUTES = 1000 * 60 * 2;

	/** Determines whether one Location reading is better than the current Location fix
	  * @param location  The new Location that you want to evaluate
	  * @param currentBestLocation  The current Location fix, to which you want to compare the new one
	  */
	protected boolean isBetterLocation(Location location, Location currentBestLocation) {
	    if (currentBestLocation == null) {
	        // A new location is always better than no location
	        return true;
	    }

	    // Check whether the new location fix is newer or older
	    long timeDelta = location.getTime() - currentBestLocation.getTime();
	    boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
	    boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
	    boolean isNewer = timeDelta > 0;

	    // If it's been more than two minutes since the current location, use the new location
	    // because the user has likely moved
	    if (isSignificantlyNewer) {
	        return true;
	    // If the new location is more than two minutes older, it must be worse
	    } else if (isSignificantlyOlder) {
	        return false;
	    }

	    // Check whether the new location fix is more or less accurate
	    int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
	    boolean isLessAccurate = accuracyDelta > 0;
	    boolean isMoreAccurate = accuracyDelta < 0;
	    boolean isSignificantlyLessAccurate = accuracyDelta > 200;

	    // Check if the old and new location are from the same provider
	    boolean isFromSameProvider = isSameProvider(location.getProvider(),
	            currentBestLocation.getProvider());

	    // Determine location quality using a combination of timeliness and accuracy
	    if (isMoreAccurate) {
	        return true;
	    } else if (isNewer && !isLessAccurate) {
	        return true;
	    } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
	        return true;
	    }
	    return false;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
	    if (provider1 == null) {
	      return provider2 == null;
	    }
	    return provider1.equals(provider2);
	}		
	
	/**
	 * This class return in {@link LocationCallback} location or null, 
	 * if location not received by timeout. 
	 */
	class TimeoutLocationUpdateListener implements Runnable, LocationListener{
		LocationCallback callback;

		public TimeoutLocationUpdateListener(LocationCallback callback) {
			this.callback = callback;
		}

		@Override
		public void onLocationChanged(Location location) {
			synchronized (TimeoutLocationUpdateListener.class) {
				if(mLastLocation != null){
					if(isBetterLocation(location, mLastLocation)){
						mLastLocation = location;
					}					
				} else {
					mLastLocation = location;
				}			
				unregisterListeners();
				
				// provide callback
				if(callback != null)
					callback.onLocationResult(mLastLocation);				
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		@Override
		public void run() {
			synchronized (TimeoutLocationUpdateListener.class) {
				unregisterListeners();
				
				if(callback != null){
					// no location by timeout
					callback.onLocationResult(null);
				}				
			}
		}
		
		private void unregisterListeners(){						
			// remove location update
			mLocationManager.removeUpdates(this);
			
			// remove timer
			mHandler.removeCallbacks(this);
		}				
	}
}
