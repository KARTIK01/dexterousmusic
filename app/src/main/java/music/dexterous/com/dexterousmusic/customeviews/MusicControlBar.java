package music.dexterous.com.dexterousmusic.customeviews;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.widget.SeekBar;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import music.dexterous.com.dexterousmusic.notification.NotificationMusic;
import music.dexterous.com.dexterousmusic.service.DexterousPlayMusicService;
import music.dexterous.com.dexterousmusic.service.musiccontrol.AbstractMusicControlService;
import rx.Observable;
import rx.Subscription;

/**
 * Created by naren on 25/8/16.
 */
public class MusicControlBar extends SeekBar implements SeekBar.OnSeekBarChangeListener {

    Subscription subscription;

    MediaPlayer mediaPlayer;

    public MusicControlBar(Context context) {
        super(context);
        init(context);
    }

    public MusicControlBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MusicControlBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOnSeekBarChangeListener(this);
        mediaPlayer = AbstractMusicControlService.mDexterousMediaPlayer;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        safeUnSubscription();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        safeUnSubscription();

        int totalDuration = mediaPlayer.getDuration();
        int currentPosition = progressToTimer(seekBar.getProgress(), totalDuration);

        // forward or backward to certain seconds
        mediaPlayer.seekTo(currentPosition);

        // update timer progress again
        updateProgressBar();
        updateNotification();
    }

    /**
     * Update timer on seekbar
     */
    public void updateProgressBar() {

        int START_DELAY = 0;
        int INTERVEL_GAP = 100;

        safeUnSubscription();
        subscription = Observable.interval(START_DELAY, INTERVEL_GAP, TimeUnit.MILLISECONDS)
                .subscribe(aLong -> {
                    updateProgress();
                });
    }

    public void updateProgress() {
        if (mediaPlayer == null)
            mediaPlayer = DexterousPlayMusicService.mDexterousMediaPlayer;

        long totalDuration = mediaPlayer.getDuration();
        long currentDuration = mediaPlayer.getCurrentPosition();

        // Updating progress bar
        int progress = (int) (getProgressPercentage(currentDuration, totalDuration));
        //Log.d("Progress", ""+progress);
        setProgress(progress);

    }


    /**
     * Function to get Progress percentage
     *
     * @param currentDuration
     * @param totalDuration
     */
    public int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // return percentage
        return percentage.intValue();
    }


    /**
     * Function to change progress to timer
     *
     * @param progress      -
     * @param totalDuration returns current duration in milliseconds
     */
    public int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }


    /**
     * updates notification when user move forward seekbar
     */
    private void updateNotification() {
        NotificationMusic notificationMusic = AbstractMusicControlService.notification;
        notificationMusic.setUpSuscription(AbstractMusicControlService.mDexterousMediaPlayer.getCurrentPosition() / 1000,
                AbstractMusicControlService.mDexterousMediaPlayer.getDuration() / 1000);
    }

    private void safeUnSubscription() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

}
