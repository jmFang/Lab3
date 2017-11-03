package com.example.jiamoufang.lab3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.example.jiamoufang.lab3.Widget.NewAppWidget;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by jiamoufang on 2017/10/23.
 */

public class MyDynamicReceiver extends BroadcastReceiver {
    private static final String dynamication = "com.example.jiamoufang.lab3.MyDynamicFilter";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(dynamication)) {
            Bundle bundle = intent.getExtras();
            Intent toShoplist = new Intent(context, MainActivity.class);
            AppWidgetManager appWidgetManager =  AppWidgetManager.getInstance(context);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.new_app_widget);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, toShoplist, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widget_imageView, pendingIntent);
            remoteViews.setTextViewText(R.id.appwidget_text, bundle.getString("name") + "已添加到购物车");
            remoteViews.setImageViewResource(R.id.widget_imageView, bundle.getInt("imageId"));
            ComponentName me = new ComponentName(context, NewAppWidget.class);
            appWidgetManager.updateAppWidget(me,remoteViews);
            EventBus.getDefault().post(new Product("111","", "", "", "", 0));
   /*         NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = new NotificationCompat.Builder(context)
                    .setContentTitle("马上下单")
                    .setContentText(bundle.getString("name") + "已添加到购物车")
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), bundle.getInt("imageId")))
                    .setSmallIcon(bundle.getInt("imageId"))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();
            manager.notify(2, notification);*/

        }
    }
}
