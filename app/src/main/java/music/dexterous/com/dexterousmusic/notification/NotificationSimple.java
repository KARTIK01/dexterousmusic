package music.dexterous.com.dexterousmusic.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import music.dexterous.com.dexterousmusic.R;
/**
 * Created by Honey on 8/7/2016.
 */

/**
 * Sticks a message outside of the application UI, both on the "notification_big area" and the
 * "notification_big drawer".
 * <p>
 * Simple class that wraps the Android API.
 * <p>
 * You should inherit it and add your custom needs.
 */
public class NotificationSimple {

    /**
     * Counter to assure each created Notification gets an unique ID at runtime.
     */
    protected static int LAST_NOTIFICATION_ID = 1;
    /**
     * Unique identifier for the current Notification.
     * <p>
     * When sending a new Notification, if it has the same ID number it'll only get updated, not
     * created from scratch.
     */
    protected int NOTIFICATION_ID;

    public NotificationSimple() {
        NOTIFICATION_ID = LAST_NOTIFICATION_ID;
        LAST_NOTIFICATION_ID++;
    }

    /**
     * Sends a quick text notification_big.
     *
     * @param title Title of the notification_big.
     * @param text  Text of the notification_big.
     *
     * @note This notification_big can be dismissed by the user and if clicked won't do nothing.
     */
    public void notify(Context c, String title, String text) {

        Notification.Builder builder = new Notification.Builder(c);

        builder.setSmallIcon(getSmallIcon())
                .setContentTitle(title)
                .setContentText(text);

        NotificationManager manager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());
    }

    /**
     * Sends a quick text re-directable notification_big.
     *
     * @param toWhere Class of the Activity it'll redirect when it is clicked.
     * @param title   Title of the notification_big.
     * @param text    Text of the notification_big.
     *
     * @note This notification_big can be dismissed by the user and will be redirected to specified
     * Activity if clicked.
     */
    public void notify(Context context, Class<?> toWhere, String title, String text) {

        Notification.Builder builder = new Notification.Builder(context);

        builder.setSmallIcon(getSmallIcon())
                .setContentTitle(title)
                .setContentText(text);

        Intent        intent        = new Intent(context, toWhere);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        builder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());
    }


    //TODO replace with proper small icon
    protected int getSmallIcon() {
        return R.drawable.alphabet_background_color;
    }
}
