package music.dexterous.com.dexterousmusic.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.widget.RemoteViews;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.database.Music;
import music.dexterous.com.dexterousmusic.receiver.widget.CloseMusicReceiver;
import music.dexterous.com.dexterousmusic.receiver.widget.NextMusicReceiver;
import music.dexterous.com.dexterousmusic.receiver.widget.PreviousMusicReceiver;
import music.dexterous.com.dexterousmusic.receiver.widget.ToggleMusicReceiver;

/**
 * Created by Honey on 8/7/2016.
 */
public class Helper {

    // Manually settings the buttons and text
    // (ignoring the defaults on the XML)
    static protected void setSmallNotificationView(RemoteViews smallNotificationView, Music music) {
        smallNotificationView.setImageViewResource(R.id.notification_button_play_small, R.drawable.ic_pause_vector);
        smallNotificationView.setImageViewResource(R.id.notification_button_skip_next_small, R.drawable.ic_skip_next_vector);
        smallNotificationView.setTextViewText(R.id.notification_text_title, music.getSONG_TITLE());
        smallNotificationView.setTextViewText(R.id.notification_text_artist, music.getSONG_ARTIST());
    }

    // Manually settings the buttons and text
    // (ignoring the defaults on the XML)
    static protected void setBigNotificationView(RemoteViews smallNotificationView, Music music) {
        smallNotificationView.setImageViewResource(R.id.notification_button_play_big, R.drawable.ic_pause_vector);
        smallNotificationView.setImageViewResource(R.id.notification_button_skip_next_big, R.drawable.ic_skip_next_vector);
        smallNotificationView.setImageViewResource(R.id.notificaion_button_skip_previous, R.drawable.ic_skip_previous_vector);
        smallNotificationView.setTextViewText(R.id.notification_text_title, music.getSONG_TITLE());
        smallNotificationView.setTextViewText(R.id.notification_text_artist, music.getSONG_ARTIST());
    }

    // On the notification_big we have two buttons - Play and Skip
    // Here we make sure the class `NotificationButtonHandler`
    // gets called when user selects one of those.
    //
    // First, building the play button and attaching it.
    static protected void setSmallButtonPlayIntent(Context context, RemoteViews smallNotificationView) {
        Intent buttonPlayIntent = new Intent(context, ToggleMusicReceiver.class);
        buttonPlayIntent.putExtra(ToggleMusicReceiver.ACTION, ToggleMusicReceiver.ACTION_TYPE_TOGGLE);
        PendingIntent buttonPlayPendingIntent = PendingIntent.getBroadcast(context, 0, buttonPlayIntent, 0);
        smallNotificationView.setOnClickPendingIntent(R.id.notification_button_play_small, buttonPlayPendingIntent);
    }

    // On the notification_big we have two buttons - Play and Skip
    // Here we make sure the class `NotificationButtonHandler`
    // gets called when user selects one of those.
    //
    // First, building the play button and attaching it.
    static protected void setBigButtonPlayIntent(Context context, RemoteViews smallNotificationView) {
        Intent buttonPlayIntent = new Intent(context, ToggleMusicReceiver.class);
        buttonPlayIntent.putExtra(ToggleMusicReceiver.ACTION, ToggleMusicReceiver.ACTION_TYPE_TOGGLE);
        PendingIntent buttonPlayPendingIntent = PendingIntent.getBroadcast(context, 0, buttonPlayIntent, 0);
        smallNotificationView.setOnClickPendingIntent(R.id.notification_button_play_big, buttonPlayPendingIntent);
    }


    // And now, building and attaching the Skip button.
    static protected void setButtonSkipNextIntent(Context context, RemoteViews smallNotificationView, @IdRes int buttonId) {
        Intent buttonSkipIntent = new Intent(context, NextMusicReceiver.class);
        buttonSkipIntent.putExtra(NextMusicReceiver.ACTION, NextMusicReceiver.ACTION_TYPE_SKIP);
        PendingIntent buttonSkipPendingIntent = PendingIntent.getBroadcast(context, 0, buttonSkipIntent, 0);
        smallNotificationView.setOnClickPendingIntent(buttonId, buttonSkipPendingIntent);
    }


    // And now, building and attaching the cross button.
    static protected void setButtonCloseIntent(Context context, RemoteViews smallNotificationView, @IdRes int buttonId) {
        Intent buttonCrossIntent = new Intent(context, CloseMusicReceiver.class);
        buttonCrossIntent.putExtra(CloseMusicReceiver.ACTION, CloseMusicReceiver.ACTION_TYPE_CLOSE);
        PendingIntent buttonSkipPendingIntent = PendingIntent.getBroadcast(context, 0, buttonCrossIntent, 0);
        smallNotificationView.setOnClickPendingIntent(buttonId, buttonSkipPendingIntent);
    }

    //And now ,building and attaching previous button
    static protected void setButtonSkipPreviousIntent(Context context, RemoteViews smallNotificationView, @IdRes int buttonId) {
        Intent buttonSkipPreviousIntent = new Intent(context, PreviousMusicReceiver.class);
        buttonSkipPreviousIntent.putExtra(PreviousMusicReceiver.ACTION, PreviousMusicReceiver.ACTION_TYPE_SKIP);
        PendingIntent buttonSkipPendingIntent = PendingIntent.getBroadcast(context, 0, buttonSkipPreviousIntent, 0);
        smallNotificationView.setOnClickPendingIntent(buttonId, buttonSkipPendingIntent);
    }

}
