package com.example.notification;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.CaseMap;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class NotificationHelper {
    private  static final String CHANNEL_ID_LOW="low_importance_channel";
    private  static final String CHANNEL_ID_DEFAULT="default_importance_channel";
    private  static final String CHANNEL_ID_HIGH="high_importance_channel";
    private static final String CHANNEL_NAME = "Kanał powiadomień";

    public static void createNotificationChannels(Context context){
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                NotificationChannel channelLow = new NotificationChannel(CHANNEL_ID_LOW,
                        CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
                NotificationChannel channelDefault = new NotificationChannel(CHANNEL_ID_DEFAULT,
                        CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                NotificationChannel channelHigh = new NotificationChannel(CHANNEL_ID_HIGH,
                        CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);

                if(notificationManager!= null){
                    notificationManager.createNotificationChannel(channelLow);
                    notificationManager.createNotificationChannel(channelDefault);
                    notificationManager.createNotificationChannel(channelHigh);
                }

            }
    }
    
    public static void sendNotification(int Notification_ID, AppCompatActivity activity,
                                        String title, String message, int styleType,
                                        int largeIconResID){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS)
                    !=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
                return;
            }
        };
        NotificationManager notificationManager = (NotificationManager)
                activity.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder =
                new NotificationCompat.Builder(activity, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);
        {
            
        }
        if (largeIconResID != null) {
            Bitmap largeIcon = BitmapFactory.decodeResource(activity.getResources(), largeIconResID);
            Notification.Builder builder;
            builder.setLargeIcon(largeIcon);
        }
        switch(styleType){
            case 1:
                Notification.Builder builder;
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
                break;
            case 2:
                if (largeIconResID !=0){
                    Bitmap largeIcon = BitmapFactory.decodeResource(activity.getResources(), largeIconResID);
                    builder.setLargeIcon(largeIcon);
                }
                break;
            case 3:
                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                inboxStyle.addLine(message);
                inboxStyle.addLine("Dodatkowa linia tekstu");
                builder.setStyle(inboxStyle);
                break;
        }
        
        
    }
}
