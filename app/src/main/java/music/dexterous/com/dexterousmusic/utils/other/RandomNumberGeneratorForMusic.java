package music.dexterous.com.dexterousmusic.utils.other;

import java.util.Random;

/**
 * Created by Honey on 8/6/2016.
 */
public class RandomNumberGeneratorForMusic {

    /**
     * Random number generator for the Shuffle Mode.
     */
    private static Random randomNumberGenerator = new Random();

    //TODO improve this algo
    public static int nextInt(int currentSongPosition, int size) {
        int newSongPosition = currentSongPosition;
        int loopCount = 0;
        while (newSongPosition == currentSongPosition && ++loopCount < 5)
            newSongPosition = randomNumberGenerator.nextInt(size);
        return newSongPosition;
    }
}
