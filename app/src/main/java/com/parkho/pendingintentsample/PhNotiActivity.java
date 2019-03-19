package com.parkho.pendingintentsample;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class PhNotiActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti);

        Intent intent = getIntent();

        // 전달 받은 "noti id" 출력
        final int notiId = intent.getIntExtra("noti_id", 0);
        final String strFormattedNotiId = String.format(getString(R.string.noti_receive_id), notiId);
        TextView tvNotiId = findViewById(R.id.tv_noti_id);
        tvNotiId.setText(strFormattedNotiId);

        // 전달 받은 "noti text" 출력
        final String strNotiText = intent.getStringExtra("noti_text");
        final String strFormattedNotiText = String.format(getString(R.string.noti_receive_text), strNotiText);
        TextView tvNotiText = findViewById(R.id.tv_noti_text);
        tvNotiText.setText(strFormattedNotiText);

        // Notification 제거
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notiId);
    }
}
