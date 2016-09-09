package music.dexterous.com.dexterousmusic.utils.other;

import java.util.Random;

import hugo.weaving.DebugLog;

/**
 * Created by Honey on 8/6/2016.
 */
public class RandomNumberGeneratorForMusic {

    /**
     * Random number generator for the Shuffle Mode.
     */
    private static Random randomNumberGenerator = new Random();

    //TODO improve this algo and handle all cases

    /**
     * @param currentSongPosition is current song index if allready playing else  first song to be
     *                            shuffled then -1
     * @param size                size of playlist
     *
     * @return newSongPosition
     */
    @DebugLog
    public static int nextInt(int currentSongPosition, int size) {
        if (size == 0) {
            return -1;
        } else if (size == 1) {
            return 1;
        } else {
            if (currentSongPosition == -1) {
                return randomNumberGenerator.nextInt(size);
            } else {
                int newSongPosition = currentSongPosition;
                int loopCount       = 0;
                while (newSongPosition == currentSongPosition && ++loopCount < 8)
                    newSongPosition = randomNumberGenerator.nextInt(size);
                return newSongPosition;
            }
        }
    }
}
