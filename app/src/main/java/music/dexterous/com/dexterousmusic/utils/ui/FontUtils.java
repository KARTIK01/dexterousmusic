
package music.dexterous.com.dexterousmusic.utils.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build.VERSION_CODES;
import android.os.StrictMode;
import android.support.v4.util.ArrayMap;

import java.util.concurrent.Future;

import music.dexterous.com.dexterousmusic.task.TaskExecutor;
import music.dexterous.com.dexterousmusic.utils.AppUtils;
import music.dexterous.com.dexterousmusic.utils.DiskUtils;

/**
 * Class containing some static utility methods.
 */
public class FontUtils {

    /**
     * for storing typeface objects as per the names of the font files
     */
    private static final ArrayMap<String, Typeface> fontTypefaceCache = new ArrayMap<>();


    /**
     * @return Type object for the given font name. Either create a new typeface or read it from the
     * map storing the typefaces for fonts.
     */
    public static Typeface getTypefaceFromName(Context context, String fontName) {
        synchronized (fontTypefaceCache) {
            if (!fontTypefaceCache.containsKey(fontName)) {
                try {
                    Future future = TaskExecutor.getInstance().submitTask(() -> {
                        String unzippedFontFileName = DiskUtils.getExtractFontFilePath(context, fontName);
                        return Typeface.createFromFile(unzippedFontFileName);
                    });
                    Typeface typeface = (Typeface) future.get();
                    fontTypefaceCache.put(fontName, typeface);
                } catch (Exception e) {
                    e.printStackTrace();
                    //TODO Crashlytics
//                    Crashlytics.logException(e);
                }
            }
            return fontTypefaceCache.get(fontName);
        }
    }
}
