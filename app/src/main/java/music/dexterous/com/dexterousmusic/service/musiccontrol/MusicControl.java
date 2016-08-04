package music.dexterous.com.dexterousmusic.service.musiccontrol;

/**
 * Created by Honey on 8/4/2016.
 */
public interface MusicControl {

    /**
     * Initializes the Android's internal MediaPlayer.
     *
     * @note We might call this function several times without
     * necessarily calling {@link #stopMusicPlayer()}.
     */
    public void initMusicPlayer();


    public void playMusic();

    public void playNextMusic();

    public void playPreviousMusic();

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
}
