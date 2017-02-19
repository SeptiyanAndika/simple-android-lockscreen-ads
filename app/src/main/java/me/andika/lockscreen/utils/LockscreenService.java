package me.andika.lockscreen.utils;

/**
 * Created by andika on 2/15/17.
 */

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import me.andika.lockscreen.LockScreenActivity;



public class LockscreenService extends Service {
    private final String TAG = "LockscreenService";
    private int mServiceStartId = 0;
    private Context mContext = null;


    private BroadcastReceiver mLockscreenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != context) {
                if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                    startLockscreenActivity();
                }
            }
        }
    };

    private void stateRecever(boolean isStartRecever) {
        if (isStartRecever) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            registerReceiver(mLockscreenReceiver, filter);
        } else {
            if (null != mLockscreenReceiver) {
                unregisterReceiver(mLockscreenReceiver);
            }
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mServiceStartId = startId;
        stateRecever(true);
        Intent bundleIntet = intent;
        if (null != bundleIntet) {
           // startLockscreenActivity();
            Log.d(TAG, TAG + " onStartCommand intent  existed");
        } else {
            Log.d(TAG, TAG + " onStartCommand intent NOT existed");
        }
        return LockscreenService.START_STICKY;
    }





    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        stateRecever(false);
    }

    private void startLockscreenActivity() {
        Intent startLockscreenActIntent = new Intent(mContext, LockScreenActivity.class);
        startLockscreenActIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startLockscreenActIntent);
    }

}