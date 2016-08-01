package music.dexterous.com.dexterousmusic.utils.android;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

/**
 * Created by Honey on 8/1/2016.
 */
public class InternalStorageSize {

    static public double getTotalGiBOfInternalStorage() {
        return getTotalBytesOfInternalStorage() / 1024.0 / 1024.0 / 1024.0;
    }

    /**
     * Gives total internal storage size in bites of that device
     *
     * @return
     */
    static public long getTotalBytesOfInternalStorage() {
        // http://stackoverflow.com/a/4595449/1474113
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return getTotalBytesOfInternalStorageWithStatFs(stat);
        } else {
            return getTotalBytesOfInternalStorageWithStatFsPreJBMR2(stat);
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    static private long getTotalBytesOfInternalStorageWithStatFs(StatFs stat) {
        return stat.getTotalBytes();
    }

    @SuppressWarnings("deprecation")
    static private long getTotalBytesOfInternalStorageWithStatFsPreJBMR2(StatFs stat) {
        return (long) stat.getBlockSize() * stat.getBlockCount();
    }
}
