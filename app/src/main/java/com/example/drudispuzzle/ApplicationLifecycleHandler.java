package com.example.drudispuzzle;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

public class ApplicationLifecycleHandler implements Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {

    private static final String TAG = ApplicationLifecycleHandler.class.getSimpleName();
    private static boolean isInBackground = false;

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if(isInBackground){
            Log.d(TAG, "app went to foreground");
            try {
                if(SelectionActivity.enBack){
                    SelectionActivity.mp.prepare();
                    SelectionActivity.mp.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            isInBackground = false;
            SelectionActivity.enBack=false;
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        SelectionActivity.mp.pause();
    }

    @Override
    public void onActivityStopped(Activity activity) {
        SelectionActivity.mp.stop();
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
    }

    @Override
    public void onLowMemory() {
    }

    @Override
    public void onTrimMemory(int i) {
        if(i == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN){
            Log.d(TAG, "app went to background");
            if (SelectionActivity.encambioMusica) {
                SelectionActivity.mp.pause();
                SelectionActivity.enBack=true;
                SelectionActivity.encambioMusica=false;
            }else{
                isInBackground = true;
            }



        }
    }
}
