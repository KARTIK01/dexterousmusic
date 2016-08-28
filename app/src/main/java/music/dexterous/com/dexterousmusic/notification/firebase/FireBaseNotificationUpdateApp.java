package music.dexterous.com.dexterousmusic.notification.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.activity.DeepLinkingActivity;
import music.dexterous.com.dexterousmusic.notification.firebase.FireBaseNotification;

/**
 * Created by naren on 22/8/16.
 */
public class FireBaseNotificationUpdateApp extends FireBaseNotification {

    private static final int NOTIFICATION_ID = 123;


    //This method is push notification_big for app update
    static public void sendNotification(Context context, String messageBody) {
        Intent intent = DeepLinkingActivity.getIntent(context, DeepLinkingActivity.ACTION_OPEN_PLAY_STORE);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Firebase Push Notification")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

}
