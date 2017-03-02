package me.andika.lockscreen;

import android.app.Application;

/**
 * Created by andika on 2/25/17.
 */

public class LockApplication extends Application {

   public boolean lockScreenShow=false;
    public int notificationId=1989;

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
