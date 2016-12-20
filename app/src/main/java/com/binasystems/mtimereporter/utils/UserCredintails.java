package com.binasystems.mtimereporter.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by serghei.pershin@binasystems.com on 12.05.15.
 */
public class UserCredintails {

    static  UserCredintails instance = null;

    /*
     * Credentials
     */
    public String LkC = null;
    public String SwSQL = null;
    public String UserC = null;
    public String LogC = null;
    public String StoreC = null;
    public String StoreN = null;
    private UserCredintails(){

    }

    public static UserCredintails getInstance(Context context){
        if(instance == null){
            synchronized (UserCredintails.class){
                if(instance == null){
                    instance = new UserCredintails();
                    PersistentManager.loadUser(context, instance);
                }
            }
        }

        return instance;
    }

    public void saveState(Context context){
        PersistentManager.saveUser(context, this);
    }

    public boolean isLogged() {
        if (LkC == null || LkC.isEmpty() || SwSQL == null || SwSQL.isEmpty()
                || UserC == null || UserC.isEmpty())
            return false;

        return true;
    }

    public static class PersistentManager{
        static final int MODE = Context.MODE_PRIVATE;
        static final String NAMESPACE = "comax.credintails_manager";

        public static void loadUser(Context context, UserCredintails target){
            SharedPreferences preferences = context.getSharedPreferences(NAMESPACE, MODE);
            target.LkC = preferences.getString("LkC", "");
            target.SwSQL = preferences.getString("SwSQL", "");
            target.UserC = preferences.getString("UserC", "");
            target.LogC  = preferences.getString("LogC", "");
            target.StoreC = preferences.getString("StoreC", "");
            target.StoreC = preferences.getString("StoreN", "");
        }

        public static void saveUser(Context context, UserCredintails source){
            SharedPreferences preferences = context.getSharedPreferences(NAMESPACE, MODE);
            preferences
                    .edit()
                    .putString("LkC", source.LkC)
                    .putString("SwSQL", source.SwSQL)
                    .putString("UserC", source.UserC)
                    .putString("LogC", source.LogC)
                    .putString("StoreC", source.StoreC)
                    .putString("StoreN", source.StoreC)

                    .apply();
        }
    }

}
