package music.dexterous.com.dexterousmusic.utils.image;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.Locale;

import music.dexterous.com.dexterousmusic.utils.logger.PrettyLogger;

/**
 * Created by Honey on 8/1/2016.
 */
public class LoggingListener<T, R> implements RequestListener<T, R> {

    private static final String TAG = "GLIDE LOGGING ";

    @Override
    public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
        PrettyLogger.d(TAG + String.format(Locale.ROOT,
                "onException(%s, %s, %s, %s)", e, model, target, isFirstResource) + e);
        return false;
    }

    @Override
    public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
        PrettyLogger.d(TAG + String.format(Locale.ROOT,
                "onResourceReady(%s, %s, %s, %s, %s)", resource, model, target, isFromMemoryCache, isFirstResource));
        return false;
    }
}