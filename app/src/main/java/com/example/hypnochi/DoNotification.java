package com.example.hypnochi;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationManagerCompat;

import Service.NotificationActionService;

import static androidx.core.app.NotificationCompat.Builder;
import static androidx.core.app.NotificationCompat.PRIORITY_LOW;

public class DoNotification {

    public static final String ChannelId = "channel1";
    public static final String Pre = "actionprevious";
    public static final String Play = "actionnext";
    public static final String Next = "actionplay";

    public static Notification notify;

    public static void doNotification(Context context, Track track, int playButton, int pos, int size) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationManagerCompat nmc = NotificationManagerCompat.from(context);
            MediaSessionCompat msc = new MediaSessionCompat(context, "tag");

            Bitmap icon = BitmapFactory.decodeResource(context.getResources(), track.getImage());

            /*PendingIntent pipre;
            //int dre_pre;
            if(pos==0) {
                pi = null;
                dre_pre = 0;
            } else {}*/

            Intent intentPlay = new Intent(context, NotificationActionService.class)
                    .setAction(Play);

            PendingIntent piPlay = PendingIntent.getBroadcast(context, 0,
                    intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);

            notify = new Builder(context, ChannelId)
                .setSmallIcon(R.drawable.ic_play)
                .setContentTitle(track.getTitle())
                .setContentText(track.getArtist())
                .setOnlyAlertOnce(true)
                .setShowWhen(false)
                .setPriority(PRIORITY_LOW)
                .addAction(playButton, "Play", piPlay)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0,1,2)
                        .setMediaSession(msc.getSessionToken()))
                .build();

            nmc.notify(1, notify);
        }
    }
}
