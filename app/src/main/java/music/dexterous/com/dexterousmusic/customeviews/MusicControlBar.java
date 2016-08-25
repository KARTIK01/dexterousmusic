package music.dexterous.com.dexterousmusic.customeviews;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.SeekBar;

import music.dexterous.com.dexterousmusic.musicutils.SongsDuration;
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
//        updateProgressBar();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        // remove message Handler from updating progress bar
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

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
    }

    public int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }

    /**
     * Update timer on seekbar
     */
    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }


    Runnable mUpdateTimeTask = new Runnable() {

        public void run() {
            long currentDuration = mediaPlayer.getCurrentPosition();
            long elapsedDuration = mediaPlayer.getDuration() - currentDuration;

            // Displaying current song progress
            // playing
//            current_song_playing_time.setText("" + SongsDuration.songDurationToDisplay(currentDuration));
//            // Displaying remaining time
//            current_song_duration.setText("" + SongsDuration.songDurationToDisplay(elapsedDuration));
//
//            // Updating progress bar
            int progress = (int) (SongsDuration.getProgressPercentage(currentDuration,
                    elapsedDuration));
//            // Log.d("Progress", ""+progress);
            setProgress(progress);

            // Running this thread after 100
            // milliseconds
            mHandler.postDelayed(this, 1000);
        }
    };

}
