package music.dexterous.com.dexterousmusic.customeviews;

import android.support.annotation.NonNull;

import com.cleveroad.audiowidget.AudioWidget;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import music.dexterous.com.dexterousmusic.GlobalApplication;
import music.dexterous.com.dexterousmusic.event.MusicPaused;
import music.dexterous.com.dexterousmusic.event.MusicStop;
import music.dexterous.com.dexterousmusic.event.MusicUnPaused;
import music.dexterous.com.dexterousmusic.event.ShowWidget;
import music.dexterous.com.dexterousmusic.service.DexterousPlayMusicService;

public class AudioWidgetController implements AudioWidget.OnControlsClickListener, AudioWidget.OnWidgetStateChangedListener {

    private GlobalApplication globalApplication;

    public AudioWidgetController(GlobalApplication globalApplication) {
        this.globalApplication = globalApplication;
        safeRegister();
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
        DexterousPlayMusicService.startService(globalApplication, DexterousPlayMusicService.PREVIOUS_MUSIC);
    }

    @Override
    public boolean onPlayPauseClicked() {
        DexterousPlayMusicService.startService(globalApplication, DexterousPlayMusicService.TOGGLE_MUSIC);
        //TODO
        // return true to change playback state of widget and play button click animation (in collapsed state)
        return false;
    }

    @Override
    public void onNextClicked() {
        // next track button clicked
        DexterousPlayMusicService.startService(globalApplication, DexterousPlayMusicService.NEXT_MUSIC);
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


    /******************************
     * Event Bus Method
     *******************************/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void show(ShowWidget showWidget) {
        if (!globalApplication.getAudioWidget().isShown())
            globalApplication.getAudioWidget().show(100, 100);

        globalApplication.getController().start();
        globalApplication.getController().duration(Integer.parseInt(showWidget.music.getSONG_DURATION()));
//        globalApplication.getController().albumCoverBitmap(HomeActivtyBgImageHelper.getBitMap(showWidget.music.getSONG_ALBUM(), globalApplication, true));

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void unpause(MusicUnPaused musicUnPaused) {
        globalApplication.getController().start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void paused(MusicPaused musicPaused) {
        globalApplication.getController().pause();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void paused(MusicStop musicStop) {
        globalApplication.getController().stop();
    }

    private EventBus getBus() {
        return GlobalApplication.getBus();
    }

    /**
     * safely registers the activity
     */
    public synchronized void safeRegister() {
        if (!getBus().isRegistered(this)) getBus().register(this);
    }

    /**
     * safely registers the activity
     */
    public synchronized void unregister() {
        if (getBus().isRegistered(this)) getBus().unregister(this);
    }
}
