package com.example.drudispuzzle;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

public class ApplicationLifecycleHandler implements Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {

    private static final String TAG = ApplicationLifecycleHandler.class.getSimpleName();
    private static boolean isInBackground = false;
    public static MediaPlayer mp1 = null;
    public static boolean repro=false;

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (!repro){
            mp1 = new MediaPlayer();
            mp1 = MediaPlayer.create(activity,R.raw.jazzopedie);
            mp1.setLooping(true);
            mp1.start();
            repro=true;
        }

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if(isInBackground) {
            isInBackground = false;
            ApplicationLifecycleHandler.mp1.start();
            Log.d(TAG, "app went to foreground");
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        //SelectionActivity.mp.pause();
        Log.d(TAG, "app went to background");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        //SelectionActivity.mp.stop();
        Log.d(TAG, "app went to background");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        Log.d(TAG, "app went to background");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.d(TAG, "app went to background");
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        Log.d(TAG, "app went to background");
    }

    @Override
    public void onLowMemory() {
        Log.d(TAG, "app went to background");
    }

    @Override
    public void onTrimMemory(int i) {
        if(i == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN){
            Log.d(TAG, "app went to background");
            isInBackground = true;
            ApplicationLifecycleHandler.mp1.pause();
        }
    }
}
