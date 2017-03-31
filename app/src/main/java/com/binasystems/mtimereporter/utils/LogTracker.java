package com.binasystems.mtimereporter.utils;

import android.os.AsyncTask;


import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.api.requests.PostRequest;

import java.util.HashMap;

/**
 * Created by serghei.pershin@binasystems.com on 30.11.15.
 */
public class LogTracker {
    static boolean LOG_TRACKER_ENABLED = false;

    public static void logMessage(final String message){

        if(LOG_TRACKER_ENABLED){
            new AsyncTask<Void, Void, Void>(){

                @Override
                protected Void doInBackground(Void... voids) {
                    PostRequest request = new PostRequest();
                    try {
                        HashMap<String, Object> postData = new HashMap<String, Object>();
                        postData.put("api_key", "comax-time-tracker");
                        postData.put("device_id", Utils.getUserAgent(TimeTrackerApplication.getInstance()));
                        postData.put("message", message);

                        request.executeNotEncrypted("http://binalogs.binaprojects.com/", null, postData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return null;
                }
            }.execute();
        }
    }

}
