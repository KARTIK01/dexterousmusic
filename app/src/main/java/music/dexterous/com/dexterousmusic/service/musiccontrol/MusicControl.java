package music.dexterous.com.dexterousmusic.service.musiccontrol;

/**
 * Created by Honey on 8/4/2016.
 */
public interface MusicControl {

    /**
     * Initializes the Android's internal MediaPlayer.
     * <p>
     * called in {@link music.dexterous.com.dexterousmusic.GlobalApplication}
     */
    public void initMusicPlayer();

    /**
     * Actually plays the song set by `currentSongPosition`.
     * <p>
     * set currentSongPosition on {@link #, NowPlayingList}before calling this function
     */
    public void playMusic();

    /**
     * Jumps to the next song on the list.
     *
     * @note Remember to call {@link #playMusic} to make the MusicPlayer
     * actually play the music.
     */
    public void playNextMusic(boolean isUserSkipped);

    /**
     * Jumps to the previous song on the list.
     *
     * @note Remember to call {@link #playMusic} to make the MusicPlayer
     * actually play the music.
     */
    public void playPreviousMusic(boolean isUserSkipped);

    public void pauseMusic();

    /**
     * Cleans resources from Android's native MediaPlayer.
     *
     * @note According to the MediaPlayer guide, you should release
     * the MediaPlayer as often as possible.
     * For example, when losing Audio Focus for an extended
     * period of time.
     */
    public void stopMusicPlayer();

    /**
     * Kills the service.
     *
     * @note Explicitly call this when the service is completed
     * or whatnot.
     */
    public void destroySelf();

    public void notifyCurrentSong();

    public void unPauseMusic();
}
