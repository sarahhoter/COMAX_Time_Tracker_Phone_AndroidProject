package com.binasystems.mtimereporter.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.binasystems.mtimereporter.activity.LoginActivity;

/**
 * Created by hani on 09/03/2017.
 */

public class StartMyServiceAtBootReceiver  extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
                Intent serviceIntent = new Intent(context, service.class);
                context.startService(serviceIntent);
            }
        }

}
