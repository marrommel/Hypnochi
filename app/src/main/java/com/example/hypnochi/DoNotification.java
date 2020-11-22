package com.example.hypnochi;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.hypnochi.Service.NotificationActionService;

import static androidx.core.app.NotificationCompat.Builder;

public class DoNotification {

    public static final String ChannelId = "channel1";
    public static final String Pre = "actionprevious";
    public static final String Play = "actionplay";
    public static final String Next = "actionnext";

    public static Notification notify;

    public static void doNotification(Context context, Track track, int playButton, int pos, int size) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationManagerCompat nmc = NotificationManagerCompat.from(context);
            MediaSessionCompat msc = new MediaSessionCompat(context, "tag");

            Bitmap icon = BitmapFactory.decodeResource(context.getResources(), track.getImage());

            PendingIntent pipre;
            int drw_pre;
            if(pos == 0) {
                pipre = null;
                drw_pre = 0;
            } else {
                Intent intentPre = new Intent(context, NotificationActionService.class)
                        .setAction(Pre);
                pipre = PendingIntent.getBroadcast(context, 0,
                        intentPre, PendingIntent.FLAG_UPDATE_CURRENT);

                drw_pre = R.drawable.ic_pre;

            }

            Intent intentPlay = new Intent(context, NotificationActionService.class)
                    .setAction(Play);

            PendingIntent piPlay = PendingIntent.getBroadcast(context, 0,
                    intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);


            PendingIntent pinext;
            int drw_next;
            if(pos == size) {
                pinext = null;
                drw_next = 0;
            } else {
                Intent intentNext = new Intent(context, NotificationActionService.class)
                        .setAction(Next);
                pinext = PendingIntent.getBroadcast(context, 0,
                        intentNext, PendingIntent.FLAG_UPDATE_CURRENT);

                drw_next = R.drawable.ic_next;

            }

            notify = new Builder(context, ChannelId)
                    .setSmallIcon(R.drawable.ic_music)
                    .setContentTitle(track.getTitle())
                    .setContentText(track.getArtist())
                    .setOnlyAlertOnce(true)
                    .setShowWhen(false)
                    .setLargeIcon(icon)
                    .addAction(drw_pre, "Previous", pipre)
                    .addAction(playButton, "Play", piPlay)
                    .addAction(drw_next, "Next", pinext)
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                        .setMediaSession(msc.getSessionToken()))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build();

            nmc.notify(1, notify);
        }
    }
}
