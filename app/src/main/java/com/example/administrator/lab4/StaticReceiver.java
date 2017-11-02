package com.example.administrator.lab4;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

/**
 * Created by Administrator on 2017/10/31.
 */

public class StaticReceiver extends BroadcastReceiver {
    final static String STATCATION="STATICATION";

    @Override
    public void onReceive(Context context, Intent intent){    	
        Bundle mBundle = intent.getExtras();
        Intent detailItent = new Intent(context, Activity2.class);        
        detailItent.putExtras(mBundle);

        int imageId = mBundle.getInt("extra_imageId");
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), mBundle.getInt("extra_imageId"));//大图标

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
        
        builder.setContentTitle("新商品热卖")
                .setContentText(mBundle.get("extra_name")+"仅售"+mBundle.get("extra_price"))
                .setSmallIcon(mBundle.getInt("extra_imageId"))
                .setLargeIcon(bitmap)
                .setAutoCancel(true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, detailItent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notify = builder.build();
        notificationManager.notify(0,notify);
    }
}
