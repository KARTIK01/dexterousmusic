package music.dexterous.com.dexterousmusic.customeviews;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.SeekBar;

import music.dexterous.com.dexterousmusic.musicutils.SongsDuration;
import music.dexterous.com.dexterousmusic.notification.NotificationMusic;
import music.dexterous.com.dexterousmusic.service.DexterousPlayMusicService;
import music.dexterous.com.dexterousmusic.service.musiccontrol.AbstractMusicControlService;

/**
 * Created by naren on 25/8/16.
 */
public class MusicControlBar extends SeekBar implements SeekBar.OnSeekBarChangeListener {

    MediaPlayer mediaPlayer;

    Handler mHandler = new Handler();

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
        // remove message Handler from updating progress bar
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
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
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    Runnable mUpdateTimeTask = new Runnable() {

        public void run() {
            if (mediaPlayer == null)
                mediaPlayer = DexterousPlayMusicService.mDexterousMediaPlayer;

            long totalDuration = mediaPlayer.getDuration();
            long currentDuration = mediaPlayer.getCurrentPosition();

            // Displaying Total Duration time
//            songTotalDurationLabel.setText(""+utils.milliSecondsToTimer(totalDuration));
//            // Displaying time completed playing
//            songCurrentDurationLabel.setText(""+utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int) (getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            setProgress(progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };

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


    private void updateNotification() {
        NotificationMusic notificationMusic = AbstractMusicControlService.notification;
        notificationMusic. setUpSuscription(AbstractMusicControlService.mDexterousMediaPlayer.getCurrentPosition() / 1000,
                AbstractMusicControlService.mDexterousMediaPlayer.getDuration() / 1000);
    }

}
