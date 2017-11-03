package com.example.jiamoufang.lab3.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStructure;
import android.widget.RemoteViews;

import com.example.jiamoufang.lab3.MainActivity;
import com.example.jiamoufang.lab3.ProductDetails;
import com.example.jiamoufang.lab3.R;

import java.util.UUID;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static Bundle bundle;
    private String state = "initial";

   static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, "当前没有任何信息");
        views.setImageViewResource(R.id.widget_imageView, R.drawable.shoplist);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, UUID.randomUUID().hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);;
        updateViews.setOnClickPendingIntent(R.id.widget_imageView, pendingIntent);
        ComponentName me = new ComponentName(context, NewAppWidget.class);
        appWidgetManager.updateAppWidget(me,updateViews);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        bundle = intent.getExtras();

        if (intent.getAction().equals("com.example.jiamoufang.lab3.StaticBroadcastToWidget")) {
           // int mAppWidgetId = bundle.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID); //获取App Widget ID
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.new_app_widget);
            Intent intent1 = new Intent(context,ProductDetails.class);
            intent1.putExtras(bundle);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widget_imageView, pendingIntent);
            remoteViews.setTextViewText(R.id.appwidget_text, bundle.getString("name") + "仅售" + bundle.getString("price"));
            remoteViews.setImageViewResource(R.id.widget_imageView, bundle.getInt("imageId"));
            ComponentName me = new ComponentName(context, NewAppWidget.class);
            appWidgetManager.updateAppWidget(me,remoteViews);
        }
    }
}

