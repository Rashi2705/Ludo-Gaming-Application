package com.example.ludo3;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;


public class dicemusicService extends Service {
    private MediaPlayer pl;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags,int startId)
    {
        pl=MediaPlayer.create(this,R.raw.dicemusic);
        pl.setLooping(true);
        pl.start();
        return START_STICKY;
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        pl.stop();
    }
}
