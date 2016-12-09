package org.protaxiandroidapp;

import android.app.Application;

/**
 * Created by DIEGO on 09/12/2016.
 */
public class GlobalApplication extends Application{

    private String userName;
    private String userId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
