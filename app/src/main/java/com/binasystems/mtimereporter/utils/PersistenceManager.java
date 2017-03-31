package com.binasystems.mtimereporter.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Simple json based serialization manager, to persist any object.
 * It need use for small objects.
 * The object convert to json presentation and save as string value
 * in android preference.
 *
 * Created by serghei.pershin@binasystem.com on 8/31/16.
 */


public class PersistenceManager {
    SharedPreferences mPreferences;
    Gson mJsonSerializator = new Gson();

    public PersistenceManager(Context context){
        mPreferences = context.getSharedPreferences("com.binasystems.comaxsaletracker.persistence_manager",
                Context.MODE_PRIVATE);
    }

    /**
     * SAve object in persistence
     *
     * @param key
     * @param object
     */
    public void saveObject(String key, Object object){
        if(object != null){
            String serializedValue = mJsonSerializator.toJson(object);
            mPreferences.edit().putString(key, serializedValue).apply();

        } else{
            mPreferences.edit().remove(key).apply();
        }
    }

    /**
     * Get object from persistence
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getObject(String key, Class<T> clazz){
        String jsonValue = mPreferences.getString(key, null);
        if(jsonValue != null){
            try {
                return mJsonSerializator.fromJson(jsonValue, clazz);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Used to remove object from persistence
     *
     * @param key object key
     */
    public void removeObject(String key){
        mPreferences.edit().remove(key).apply();
    }

    /**
     * Save string in persistence
     *
     * @param key
     * @param value
     */

    public void saveString(String key, String value){
        if(value!=null&&!value.isEmpty())
            mPreferences.edit().putString(key, value).apply();
        else
            mPreferences.edit().remove(key).apply();
    }

    public String getString(String key){
        return mPreferences.getString(key, "");
    }

    public void removeString(String key){
        mPreferences.edit().remove(key).apply();
    }

    /**
     * Save ArrayList<Object> in persistence
     *
     * @param key
     * @param arrayListObject
     */

    String serializedList ="";

    public void saveArrayList(String key, ArrayList<?> arrayListObject){
        if(arrayListObject != null) {
            for (Object object : arrayListObject) {
                String serializedValue = mJsonSerializator.toJson(arrayListObject);
                if (serializedList.equals(""))
                    serializedList = serializedValue;
                else serializedList = serializedList + "---" + serializedValue;
                mPreferences.edit().putString(key, serializedValue).apply();

            }
        }else{
            mPreferences.edit().remove(key).apply();
        }
    }

//    public <T> List<T> magicalListGetter(Class<T> klazz) {
//        List<T> list = new ArrayList<>();
//        list.add(klazz.cast(actuallyT));
//        try {
//            list.add(klazz.getConstructor().newInstance()); // If default constructor
//        } ...
//        return list;

    public <T> ArrayList<T> getArrayList(String key, Class<T> clazz){
        String jsonValue = mPreferences.getString(key, null);
        ArrayList<T> a = new ArrayList<>();
        if(jsonValue != null){
            try {
                String[] strings = jsonValue.split("---");
                for (String s:strings) {
                    a.add(mJsonSerializator.fromJson(jsonValue, clazz));
                }
                return a;
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }

}
