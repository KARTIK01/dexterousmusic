package music.dexterous.com.dexterousmusic.service.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.activity.MainActivity;
import music.dexterous.com.dexterousmusic.notification.FireBaseNotificationUpdateApp;
import music.dexterous.com.dexterousmusic.utils.logger.PrettyLogger;

/**
 * Created by gipsy_danger on 17/8/16.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
        PrettyLogger.d("From: " + remoteMessage.getFrom());
        PrettyLogger.d("Notification Message Body: " + remoteMessage.getNotification().getBody());

        //Calling method to generate notification
        FireBaseNotificationUpdateApp.sendNotification(this, remoteMessage.getNotification().getBody());
    }


}