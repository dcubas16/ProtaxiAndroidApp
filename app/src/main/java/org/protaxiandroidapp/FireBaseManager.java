package org.protaxiandroidapp;

import com.firebase.client.Firebase;

/**
 * Created by DIEGO on 21/08/2016.
 */
public class FireBaseManager extends android.app.Application {


    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);


    }
}
