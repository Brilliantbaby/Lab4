package com.example.administrator.lab4;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

/**
 * Created by Administrator on 2017/10/31.
 */
public class DynamicReceiver extends BroadcastReceiver {
    public final static String DYNAMICATION="DYNAMICATION";
    @Override
    public void onReceive(Context mcontext, Intent intent){
        if (intent.getAction().equals(DYNAMICATION)){

        	//从收到的广播中获取商品信息
            Bundle bundle = intent.getExtras();
            int imageIdbroadcast=bundle.getInt("extra_imageId");
            String namebroadcast=bundle.getString("extra_name");
            NotificationManager manager = (NotificationManager) mcontext.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder mBuilder = new Notification.Builder(mcontext);

            //定义通知的内容
            mBuilder.setContentTitle("马上下单")
                    .setContentText(namebroadcast+"已经加入购物车")
                    .setTicker("您有一条新消息")
                    .setLargeIcon(BitmapFactory.decodeResource(mcontext.getResources(),imageIdbroadcast)) 
                    .setSmallIcon(imageIdbroadcast)
                    .setAutoCancel(true);

            //转换intent，跳转到主活动页面，查看购物车的内容
            Intent mIntent = new Intent(mcontext,MainActivity.class);
            mIntent.putExtras(intent.getExtras());
            PendingIntent mPendingIntent = PendingIntent.getActivity(mcontext,0,mIntent,PendingIntent.FLAG_ONE_SHOT);
            mBuilder.setContentIntent(mPendingIntent);
            
            Notification notify=mBuilder.build();
            manager.notify(1,notify);
        }
    }
}
