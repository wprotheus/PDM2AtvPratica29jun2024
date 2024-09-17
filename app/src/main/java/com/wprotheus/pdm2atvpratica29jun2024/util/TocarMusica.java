package com.wprotheus.pdm2atvpratica29jun2024.util;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.wprotheus.pdm2atvpratica29jun2024.R;

public class TocarMusica extends Service {
    private MediaPlayer mPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = MediaPlayer.create(this.getApplicationContext(), R.raw.jeremy_blake_powerup);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            mPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
        super.onDestroy();
    }
}