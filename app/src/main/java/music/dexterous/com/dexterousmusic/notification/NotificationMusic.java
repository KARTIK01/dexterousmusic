package music.dexterous.com.dexterousmusic.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.activity.NowPlayingActivity;
import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.receiver.widget.NextMusicReceiver;
import music.dexterous.com.dexterousmusic.receiver.widget.PlayMusicReceiver;
import music.dexterous.com.dexterousmusic.utils.android.Package;

/**
 * Specific way to stick an on-going message on the system
 * with the current song I'm playing.
 * <p>
 * This is a rather complicated set of functions because
 * it interacts with a great deal of the Android API.
 * Read with care. Modify with care
 * <p>
 * Thanks:
 */
public class NotificationMusic extends NotificationSimple {

    /**
     * Reference to the context that notified.
     */
    Context context = null;

    /**
     * Used to create and update the same notification.
     */
    Notification.Builder notificationBuilder = null;

    /**
     * Custom appearance of the notification, also updated.
     */
    RemoteViews notificationView = null;

    /**
     * Used to actually broadcast the notification.
     * Depends on the Activity that originally called
     * the nofitication.
     */
    NotificationManager notificationManager = null;


    /**
     * Reference to the service we're attached to.
     */
    private Service service;

    /**
     * Sends a system notification with a Music's information.
     * <p>
     * If the user clicks the notification, will be redirected
     * to the "Now Playing" Activity.
     * <p>
     * If the user clicks on any of the buttons inside it,
     * custom actions will be executed on the
     * `NotificationButtonHandler` class.
     *
     * @param context Activity that calls this function.
     * @param service Service that calls this function.
     *                Required so the Notification can
     *                run on the background.
     * @param music   Song which we'll display information.
     * @note By calling this function multiple times, it'll
     * update the old notification.
     */
    public void notifySong(Context context, Service service, Music music) {

        if (this.context == null)
            this.context = context;
        if (this.service == null)
            this.service = service;


        // Intent that launches the "Now Playing" Activity
        Intent notifyIntent = new Intent(context, NowPlayingActivity.class);
        notifyIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Letting the Intent be executed later by other application.
        PendingIntent pendingIntent = PendingIntent.getActivity
                (context,
                        0,
                        notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);


        // Setting our custom appearance for the notification
        notificationView = new RemoteViews(Package.getPackageName(service), R.layout.notification_big);

        // Manually settings the buttons and text
        // (ignoring the defaults on the XML)
        notificationView.setImageViewResource(R.id.notification_button_play, R.drawable.ic_pause);
        notificationView.setImageViewResource(R.id.notification_button_skip, R.drawable.ic_skip);
        notificationView.setTextViewText(R.id.notification_text_title, music.getSONG_TITLE());
        notificationView.setTextViewText(R.id.notification_text_artist, music.getSONG_ARTIST());

        // On the notification we have two buttons - Play and Skip
        // Here we make sure the class `NotificationButtonHandler`
        // gets called when user selects one of those.
        //
        // First, building the play button and attaching it.
        Intent buttonPlayIntent = new Intent(context, PlayMusicReceiver.class);
        buttonPlayIntent.putExtra(PlayMusicReceiver.ACTION, PlayMusicReceiver.ACTION_TYPE_TOGGLE);
        PendingIntent buttonPlayPendingIntent = PendingIntent.getBroadcast(context, 0, buttonPlayIntent, 0);
        notificationView.setOnClickPendingIntent(R.id.notification_button_play, buttonPlayPendingIntent);

        // And now, building and attaching the Skip button.
        Intent buttonSkipIntent = new Intent(context, NextMusicReceiver.class);
        buttonSkipIntent.putExtra(NextMusicReceiver.ACTION, NextMusicReceiver.ACTION_TYPE_SKIP);
        PendingIntent buttonSkipPendingIntent = PendingIntent.getBroadcast(context, 0, buttonSkipIntent, 0);
        notificationView.setOnClickPendingIntent(R.id.notification_button_skip, buttonSkipPendingIntent);

        // Finally... Actually creating the Notification
        notificationBuilder = new Notification.Builder(context);

        notificationBuilder.setContentIntent(pendingIntent)
                .setSmallIcon(getSmallIcon())
                //TODO
                .setTicker("kMP: Playing '" + music.getSONG_TITLE() + "' from '" + music.getSONG_ARTIST() + "'")
                .setOngoing(true)
                .setContentTitle(music.getSONG_TITLE())
                .setContentText(music.getSONG_ARTIST())
                .setContent(notificationView);

        Notification notification = notificationBuilder.build();


        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//		notificationManager.notify(NOTIFICATION_ID, notification);

        // Sets the notification to run on the foreground.
        // (why not the former commented line?)
        service.startForeground(NOTIFICATION_ID, notification);
    }


    /**
     * Updates the Notification icon if the music is paused.
     */
    public void notifyPaused(boolean isPaused) {
        if ((notificationView == null) || (notificationBuilder == null))
            return;

        int iconID = ((isPaused) ?
                R.drawable.ic_play :
                R.drawable.ic_pause);

        notificationView.setImageViewResource(R.id.notification_button_play, iconID);

        notificationBuilder.setContent(notificationView);

//		notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());

        // Sets the notification to run on the foreground.
        // (why not the former commented line?)
        service.startForeground(NOTIFICATION_ID, notificationBuilder.build());
    }

    /**
     * Cancels this notification.
     */
    public void cancel() {
        service.stopForeground(true);
        notificationManager.cancel(NOTIFICATION_ID);
    }

    /**
     * Cancels all sent notifications.
     */
    public static void cancelAll(Context c) {
        NotificationManager manager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
    }
}
