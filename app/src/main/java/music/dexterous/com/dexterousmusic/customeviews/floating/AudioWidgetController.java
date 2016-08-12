package music.dexterous.com.dexterousmusic.customeviews.floating;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.cleveroad.audiowidget.AudioWidget;

import music.dexterous.com.dexterousmusic.service.DexterousPlayMusicService;

/**
 * Created by Honey on 8/12/2016.
 */
public class AudioWidgetController implements AudioWidget.OnControlsClickListener, AudioWidget.OnWidgetStateChangedListener {

    private Context context;

    public AudioWidgetController(Context context) {
        this.context = context;
    }

    @Override
    public boolean onPlaylistClicked() {
        // playlist icon clicked
        // TODO
        // return false to collapse widget, true to stay in expanded state
        return false;
    }

    @Override
    public void onPreviousClicked() {
        // previous track button clicked
        DexterousPlayMusicService.startService(context, DexterousPlayMusicService.PREVIOUS_MUSIC);
    }

    @Override
    public boolean onPlayPauseClicked() {
        DexterousPlayMusicService.startService(context, DexterousPlayMusicService.TOGGLE_MUSIC);
        //TODO
        // return true to change playback state of widget and play button click animation (in collapsed state)
        return false;
    }

    @Override
    public void onNextClicked() {
        // next track button clicked
        DexterousPlayMusicService.startService(context, DexterousPlayMusicService.NEXT_MUSIC);
    }

    @Override
    public void onAlbumClicked() {
        // album cover clicked
    }

    @Override
    public void onPlaylistLongClicked() {
        // playlist button long clicked
    }

    @Override
    public void onPreviousLongClicked() {
        // previous track button long clicked
    }

    @Override
    public void onPlayPauseLongClicked() {
        // play/pause button long clicked
    }

    @Override
    public void onNextLongClicked() {
        // next track button long clicked
    }

    @Override
    public void onAlbumLongClicked() {

    }

    @Override
    public void onWidgetStateChanged(@NonNull AudioWidget.State state) {
        // widget state changed (COLLAPSED, EXPANDED, REMOVED)
    }

    @Override
    public void onWidgetPositionChanged(int cx, int cy) {
        // widget position change. Save coordinates here to reuse them next time AudioWidget.show(int, int) called.
    }
}
