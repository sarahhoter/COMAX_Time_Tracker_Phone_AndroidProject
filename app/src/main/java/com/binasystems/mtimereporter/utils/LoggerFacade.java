package com.binasystems.mtimereporter.utils;


import com.binasystems.mtimereporter.Constants;
import com.splunk.mint.DataSaverResponse;
import com.splunk.mint.Mint;
import com.splunk.mint.MintCallback;
import com.splunk.mint.NetSenderResponse;

/**
 * Created by serghei.pershin@binasystems.com on 15.04.15.
 */
public class LoggerFacade {

    public static final boolean ENABLED = Constants.DEV_ENABLE_TRACELOG_FILE;

    static {
        if(ENABLED){
            Mint.setMintCallback(new MintCallback() {
                @Override
                public void netSenderResponse(NetSenderResponse netSenderResponse) {
                }

                @Override
                public void dataSaverResponse(DataSaverResponse dataSaverResponse) {
                    TraceLogWritter.writeLog(dataSaverResponse.getData());
                    android.util.Log.d("info", "dataSaverResponse" + dataSaverResponse.getData());
                }

                @Override
                public void lastBreath(Exception e) {
                    TraceLogWritter.writeLog(Utils.getStackTraceAsText(e));
                    e.printStackTrace();
                }
            });
        }
    }


    public static void leaveBreadcrumb(String breadcrumb, boolean autoflush){
        LogTracker.logMessage(breadcrumb);

        if(ENABLED){
            Mint.leaveBreadcrumb(breadcrumb);
            Mint.logEvent(breadcrumb);

            if(autoflush){
                Mint.flush();
            }
        }
    }

    public static void leaveBreadcrumb(String breadcrumb){
        leaveBreadcrumb(breadcrumb, true);
    }

}
