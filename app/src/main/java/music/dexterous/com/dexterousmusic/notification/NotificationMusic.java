package music.dexterous.com.dexterousmusic.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import java.util.concurrent.TimeUnit;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.activity.HomeActivity;
import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.service.musiccontrol.AbstractMusicControlService;
import music.dexterous.com.dexterousmusic.utils.android.Package;
import rx.Observable;
import rx.Subscription;


public class NotificationMusic extends NotificationSimple {

    /**
     * Notification progress bar
     */
    private final int STATUS_BAR_NOTIFICATION = 1;
    /**
     * Reference to the context that notified.
     */
    Context context = null;
    /**
     * Used to create and update the same notification_big.
     */
    NotificationCompat.Builder notificationBuilder = null;

    /**
     * Custom appearance of the notification_big, also updated.
     */
    RemoteViews smallNotificationView = null;
    RemoteViews bigNotificationView   = null;

    /**
     * Used to actually broadcast the notification_big. Depends on the Activity that originally
     * called the nofitication.
     */
    NotificationManager notificationManager = null;


    /**
     * Reference to the service we're attached to.
     */
    private Service      service;
    private Subscription subscription;

    /**
     * Cancels all sent notifications.
     */
    public static void cancelAll(Context c) {
        NotificationManager manager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
    }

    /**
     * Sends a system notification_big with a Music's information.
     * <p>
     * If the user clicks the notification_big, will be redirected to the "Now Playing" Activity.
     * <p>
     * If the user clicks on any of the buttons inside it, custom actions will be executed on the
     * `NotificationButtonHandler` class.
     *
     * @param context Activity that calls this function.
     * @param service Service that calls this function. Required so the Notification can run on the
     *                background.
     * @param music   Song which we'll display information.
     *
     * @note By calling this function multiple times, it'll update the old notification_big.
     */
    public void notifySong(Context context, Service service, Music music) {

        if (this.context == null)
            this.context = context;
        if (this.service == null)
            this.service = service;


        // Intent that launches the "Now Playing" Activity
        Intent notifyIntent = new Intent(context, HomeActivity.class);
        //TODO put extra for now playing
        notifyIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //TODO build TaskStackBuilder for notification_big
        // Letting the Intent be executed later by other application.
        PendingIntent pendingIntent = PendingIntent.getActivity
                (context,
                        0,
                        notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);


        // Setting our custom appearance for the notification_big
        smallNotificationView = new RemoteViews(Package.getPackageName(service), R.layout.notification_small);

        //set resources to view from small notification_big
        Helper.setSmallNotificationView(smallNotificationView, music);
        Helper.setSmallButtonPlayIntent(context, smallNotificationView);
        Helper.setButtonSkipNextIntent(context, smallNotificationView, R.id.notification_button_skip_next_small);
        Helper.setButtonCloseIntent(context, smallNotificationView, R.id.notification_button_close_small);
        Helper.setAlbumImage(context, music, smallNotificationView);


        // Finally... Actually creating the Notification
        notificationBuilder = new NotificationCompat.Builder(context);

        notificationBuilder.setContentIntent(pendingIntent)
                .setSmallIcon(getSmallIcon())
                //TODO
                .setTicker("Playing '" + music.getSONG_TITLE() + "' from '" + music.getSONG_ARTIST() + "'")
                .setOngoing(true)
                .setContentTitle(music.getSONG_TITLE())
                .setContentText(music.getSONG_ARTIST())
                .setContent(smallNotificationView)/*.setProgress(100, 0, false)*/;

        Notification notification = notificationBuilder.build();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            bigNotificationView = new RemoteViews(Package.getPackageName(context), R.layout.notification_big);
//            bigNotificationView.setTextViewText(R.id.bigNotificationTitle, title);

            //set resources to view from small notification_big
            Helper.setBigNotificationView(bigNotificationView, music);
            Helper.setBigButtonPlayIntent(context, bigNotificationView);
            Helper.setButtonSkipNextIntent(context, bigNotificationView, R.id.notification_button_skip_next_big);
            Helper.setButtonCloseIntent(context, bigNotificationView, R.id.notification_button_close_big);
            Helper.setButtonSkipPreviousIntent(context, bigNotificationView, R.id.notificaion_button_skip_previous);
            Helper.setAlbumImage(context, music, bigNotificationView);
            bigNotificationView.setProgressBar(R.id.status_progress, 100, 0, false);
            notification.bigContentView = bigNotificationView;
            notification.priority = Notification.PRIORITY_MAX;
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notification.visibility = Notification.VISIBILITY_PUBLIC;
        }

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//		notificationManager.notify(NOTIFICATION_ID, notification_big);

        // Sets the notification_big to run on the foreground.
        // (why not the former commented line?)
        service.startForeground(NOTIFICATION_ID, notification);

        setUpSuscription(0, Integer.parseInt(music.getSONG_DURATION()) / 1000);
    }

    public void setUpSuscription(int intialValue, int songDuration) {

        int START_DELAY  = 0;
        int INTERVEL_GAP = 1;

        saveUnSubscribe();
        subscription =
                Observable
                        .interval(START_DELAY, INTERVEL_GAP, TimeUnit.SECONDS)
                        .map(aLong1 -> aLong1 + intialValue)
                        .subscribe(aLong ->
                                updateProgress(aLong, songDuration));

    }

    /**
     * call this method when you want to update progress bar with currentSongDuration
     *
     * @param currentSongDuration
     */
    public void updateProgress(long currentSongDuration, int songDuration) {
        bigNotificationView.setProgressBar(R.id.status_progress, songDuration, (int) currentSongDuration, false);
        notificationBuilder.setContent(smallNotificationView);
        notificationBuilder.setCustomBigContentView(bigNotificationView);

        service.startForeground(NOTIFICATION_ID, notificationBuilder.build());
    }

    /**
     * Updates the Notification icon if the music is paused.
     */
    public void notifyPaused(boolean isPaused) {
        if (isPaused)
            saveUnSubscribe();
        else
            setUpSuscription(
                    AbstractMusicControlService.mDexterousMediaPlayer.getCurrentPosition() / 1000,
                    AbstractMusicControlService.mDexterousMediaPlayer.getDuration() / 1000);

        if ((smallNotificationView == null) || (notificationBuilder == null))
            return;

        int iconID = ((isPaused) ?
                R.drawable.ic_play_vector :
                R.drawable.ic_pause_vector);

        bigNotificationView.setImageViewResource(R.id.notification_button_play_big, iconID);
        smallNotificationView.setImageViewResource(R.id.notification_button_play_small, iconID);

        notificationBuilder.setContent(smallNotificationView);
        notificationBuilder.setCustomBigContentView(bigNotificationView);

//		notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());

        // Sets the notification_big to run on the foreground.
        // (why not the former commented line?)
        service.startForeground(NOTIFICATION_ID, notificationBuilder.build());
    }

    /**
     * Cancels this notification_big.
     */
    public void cancel() {
        saveUnSubscribe();
        service.stopForeground(true);
        notificationManager.cancel(NOTIFICATION_ID);
    }

    private void saveUnSubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
