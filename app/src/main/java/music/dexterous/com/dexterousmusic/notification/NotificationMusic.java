package music.dexterous.com.dexterousmusic.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;
import android.widget.ProgressBar;
import android.widget.RemoteViews;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.activity.HomeActivity;
import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.task.TaskExecutor;
import music.dexterous.com.dexterousmusic.utils.android.Package;


public class NotificationMusic extends NotificationSimple {

    /**
     * Reference to the context that notified.
     */
    Context context = null;
    /**
     * Notification progress bar
     */
    private final int STATUS_BAR_NOTIFICATION = 1;
    private boolean mRun;


    /**
     * Used to create and update the same notification_big.
     */
    NotificationCompat.Builder notificationBuilder = null;

    /**
     * Custom appearance of the notification_big, also updated.
     */
    RemoteViews smallNotificationView = null;

    /**
     * Used to actually broadcast the notification_big.
     * Depends on the Activity that originally called
     * the nofitication.
     */
    NotificationManager notificationManager = null;


    /**
     * Reference to the service we're attached to.
     */
    private Service service;

    /**
     * Sends a system notification_big with a Music's information.
     * <p>
     * If the user clicks the notification_big, will be redirected
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
     * update the old notification_big.
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
        Helper.setButtonSkipNextIntent(context, smallNotificationView, R.id.notification_button_skip_small);
        Helper.setButtonCloseIntent(context, smallNotificationView, R.id.notification_button_close_small);


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
            RemoteViews bigContentView = new RemoteViews(Package.getPackageName(context), R.layout.notification_big);
//            bigContentView.setTextViewText(R.id.bigNotificationTitle, title);

            //set resources to view from small notification_big
            Helper.setBigNotificationView(bigContentView, music);
            Helper.setBigButtonPlayIntent(context, bigContentView);
            Helper.setButtonSkipNextIntent(context, bigContentView, R.id.notification_button_skip_next_big);
            Helper.setButtonCloseIntent(context, bigContentView, R.id.notification_button_close_big);
            Helper.setButtonSkipPreviousIntent(context, bigContentView, R.id.notificaion_button_skip_previous);
            bigContentView.setProgressBar(R.id.status_progress, 100, 0, false);
            notification.bigContentView = bigContentView;
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

        TaskExecutor.getInstance().executeTask(() -> {
            int mCount = 0;
            mRun = true;
            while (mRun) {
                ++mCount;
                SystemClock.sleep(1000);
                notification.contentView.setProgressBar(R.id.status_progress, 100, mCount % 100, false);
                notificationManager.notify(NOTIFICATION_ID, notification);
            }

        });
    }


    /**
     * Updates the Notification icon if the music is paused.
     */
    public void notifyPaused(boolean isPaused) {
        //TODO big notification_big gone when this funcation calls
        //TODO if paused make a cross symol to cancle notification_big
        if ((smallNotificationView == null) || (notificationBuilder == null))
            return;

        int iconID = ((isPaused) ?
                R.drawable.ic_play_vector :
                R.drawable.ic_pause_vector);

        smallNotificationView.setImageViewResource(R.id.notification_button_play_big, iconID);
        smallNotificationView.setImageViewResource(R.id.notification_button_play_small, iconID);

        notificationBuilder.setContent(smallNotificationView);

//		notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());

        // Sets the notification_big to run on the foreground.
        // (why not the former commented line?)
        service.startForeground(NOTIFICATION_ID, notificationBuilder.build());
    }

    /**
     * Cancels this notification_big.
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
