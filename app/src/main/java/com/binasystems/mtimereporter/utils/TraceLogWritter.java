package com.binasystems.mtimereporter.utils;

import android.os.Environment;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by serghei.pershin@binasystems.com on 15.04.15.
 */
public class TraceLogWritter {

    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void writeLog(String log){
        executorService.submit(new LogWritter(log));
    }

    static class LogWritter implements Runnable{

        static SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");

        String logText;

        public LogWritter(String log){
            this.logText = log;
        }

        private String getLogFileName(){
            return "log_" + format.format(Calendar.getInstance().getTime()) + ".txt";
        }

        @Override
        public void run() {
            File cdCardDirectory = Environment.getExternalStorageDirectory();

            if(cdCardDirectory != null){
                try {
                    File logDir = new File(cdCardDirectory, "TimeTracker");
                    logDir.mkdirs();
                    File outFIle = new File(logDir, getLogFileName());
                    if(!outFIle.exists()){
                        outFIle.getParentFile().mkdirs();
                        outFIle.createNewFile();
                    }
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outFIle, true)));
                    out.println(logText);
                    out.println("");
                    out.close();
                } catch (IOException e) {
                    //exception handling left as an exercise for the reader
                    e.printStackTrace();
                }
            }
        }
    }

}
