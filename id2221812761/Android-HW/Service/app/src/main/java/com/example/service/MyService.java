package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;

public class MyService extends Service {

    private MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        return null; // لا حاجة للربط لأنه Service عادية وليست Bound Service
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        player.setLooping(true); // تشغيل متواصل
        player.start();

        //  عرض Toast عند بدء الخدمة
        android.widget.Toast.makeText(this, "Service Started 🎵", android.widget.Toast.LENGTH_SHORT).show();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // هذه الإضافة تشمل التأكد من حالة Media Player قبل إيقافه لتجنب استثناء NullPointerException وIllegalStateException
        // NullPointerException:  يحدث عند محاولة الوصول لدالة من كائن غير موجود في الذاكرة
        // IllegalStateException: يحدث عند تنفيذ دالة غير مسموح بها حالياً لحالة الكائن (مثلاً لم يتم تشغيله بعد)

        if (player != null && player.isPlaying()) {
            player.stop();
            player.release();
        }

        //  عرض Toast عند إيقاف الخدمة
        android.widget.Toast.makeText(this, "Service Stopped ❌", android.widget.Toast.LENGTH_SHORT).show();
    }
}

