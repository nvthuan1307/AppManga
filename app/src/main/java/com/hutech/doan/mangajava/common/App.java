package com.hutech.doan.mangajava.common;

import android.app.Application;
import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by thuan on 02012018.
 them file
 */

public class App extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public static Context getContext(){
        return mContext;
    }
}