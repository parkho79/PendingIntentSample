package com.parkho.pendingintentsample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class PhMainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button click 시 pending intent 를 사용한 notification 생성
        findViewById(R.id.btn_pending).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                generateNotification();
            }
        });
    }

    /**
     * Notification 생성
     */
    private void generateNotification() {
        final int notiId = 101;
        final String strChannelId = "channel_id";

        // Pending intent 생성
        Intent notificationIntent = new Intent(this, PhNotiActivity.class);
        notificationIntent.putExtra("noti_id", notiId);
        notificationIntent.putExtra("noti_text", "PendingIntent test");
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Notification 생성
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, strChannelId);
        builder.setContentTitle(getString(R.string.noti_content_title))
                .setContentText(getString(R.string.noti_content_text))
                .setTicker(getString(R.string.noti_ticker))
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL);

        // Android O (API 26) 이상 부터는 channel id 등록 필요
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final String strTitle = getString(R.string.app_name);
            NotificationChannel channel = notificationManager.getNotificationChannel(strChannelId);
            if (channel == null) {
                channel = new NotificationChannel(strChannelId, strTitle, NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(channel);
            }

            builder.setChannelId(strChannelId);
        }

        notificationManager.notify(notiId, builder.build());
    }
}