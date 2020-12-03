package com.example.drudispuzzle;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyServiceMusica extends Service {
    MediaPlayer mediaPlayer;

    public MyServiceMusica() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent, flags, startId);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.jazzopedie);
        mediaPlayer.start();

        return START_STICKY;
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}