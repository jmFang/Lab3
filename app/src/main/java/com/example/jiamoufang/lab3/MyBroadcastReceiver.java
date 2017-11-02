package com.example.jiamoufang.lab3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.style.BulletSpan;

/**
 * Created by jiamoufang on 2017/10/23.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("com.example.jiamoufang.lab3.MyStaticFilter")) {
            Bundle bundle = intent.getExtras();

            //这里为什么实用context.getApplicationContext()，而不是context
            Intent toDetails = new Intent(context, ProductDetails.class);
            toDetails.putExtras(bundle);

/*            toDetails.putExtra("header", bundle.getString("header"));
            toDetails.putExtra("name", bundle.getString("name"));
            toDetails.putExtra("price", bundle.getString("price"));
            toDetails.putExtra("type", bundle.getString("type"));
            toDetails.putExtra("info", bundle.getString("info"));
            toDetails.putExtra("imageId", bundle.getInt("imageId"));*/
            PendingIntent pi =  PendingIntent.getActivity(context, 0, toDetails, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            Notification notification = new NotificationCompat.Builder(context)
                    .setContentTitle("新商品热卖")
                    .setSmallIcon(bundle.getInt("imageId"))
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), bundle.getInt("imageId")))
                    .setContentText(bundle.getString("name") + "仅售" + bundle.getString("price") + "!")
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .build();
            manager.notify(1,notification);
        }
    }
}
