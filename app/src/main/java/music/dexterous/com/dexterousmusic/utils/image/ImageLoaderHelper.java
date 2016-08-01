package music.dexterous.com.dexterousmusic.utils.image;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import music.dexterous.com.dexterousmusic.BuildConfig;

/**
 * Created by Honey on 8/1/2016.
 */
public class ImageLoaderHelper {

    protected DiskCacheStrategy mDiskCacheStrategy = DiskCacheStrategy.RESULT;

    /**
     * Flag to determine if images should be cropped by default or not
     */
    protected final static boolean shouldCropByDefault = true;

    /**
     * Glide url cache
     */
    private static final ModelCache<String, GlideUrl> urlCache =
            new ModelCache<>(150);

    /**
     * Generic image load request
     */
    private final BitmapTypeRequest<String> mGlideModelRequest;


    /**
     * Default transformation
     */
    private final Transformation<Bitmap> mBitmapTransformation;

    protected Integer mPlaceHolderResId = null;


    /**
     * Construct a standard ImageLoaderHelper object.
     */
    public ImageLoaderHelper(Context context) {
        this(context, new CenterCrop(Glide.get(context).getBitmapPool()));
    }

    public ImageLoaderHelper(Context context, Transformation<Bitmap> transformation) {
        VariableWidthImageLoader imageLoader = new VariableWidthImageLoader(context);
        LoggingListener<String, Bitmap> LOGGING_LISTENER = new LoggingListener<>();

        mGlideModelRequest = Glide.with(context)
                .using(imageLoader)
                .from(String.class)
                .asBitmap();
        mGlideModelRequest.priority(Priority.HIGH);

        if (BuildConfig.DEBUG)
            mGlideModelRequest.listener(LOGGING_LISTENER);

        mBitmapTransformation = transformation;
    }


    /**
     * Construct an ImageLoaderHelper with a default placeholder drawable.
     */
    public ImageLoaderHelper(Context context, Integer placeHolderResId, Transformation<Bitmap> transformation) {
        this(context, transformation);
        mPlaceHolderResId = placeHolderResId;
    }

    /**
     * Construct an ImageLoaderHelper with a default placeholder drawable.
     */
    public ImageLoaderHelper(Context context, Integer placeHolderResId) {
        this(context);
        mPlaceHolderResId = placeHolderResId;
    }

    /**
     * Construct an ImageLoaderHelper with a default placeholder drawable.
     */
    public ImageLoaderHelper(Context context, int placeHolderResId, DiskCacheStrategy strategy) {
        this(context, placeHolderResId);
        this.mDiskCacheStrategy = strategy;
    }


    protected BitmapRequestBuilder<String, Bitmap> beginImageLoad(String url,
                                                                  RequestListener<String, Bitmap> requestListener,
                                                                  boolean crop) {
        if (crop) {
            return mGlideModelRequest.load(url)
                    .listener(requestListener)
                    .transform(mBitmapTransformation);
        } else {
            return mGlideModelRequest.load(url)
                    .listener(requestListener);
        }
    }


    private static class VariableWidthImageLoader extends BaseGlideUrlLoader<String> {
        private static final Pattern PATTERN = Pattern.compile("__w-((?:-?\\d+)+)__");

        public VariableWidthImageLoader(Context context) {
            super(context, urlCache);
        }

        /**
         * If the URL contains a special variable width indicator (eg "__w-200-400-800__")
         * we get the buckets from the URL (200, 400 and 800 in the example) and replace
         * the URL with the best bucket for the requested width (the bucket immediately
         * larger than the requested width).
         */
        @Override
        protected String getUrl(String model, int width, int height) {
            Matcher m = PATTERN.matcher(model);
            int bestBucket = 0;
            if (m.find()) {
                String[] found = m.group(1).split("-");
                for (String bucketStr : found) {
                    bestBucket = Integer.parseInt(bucketStr);
                    if (bestBucket >= width) {
                        // the best bucket is the first immediately bigger than the requested width
                        break;
                    }
                }
                if (bestBucket > 0) {
                    model = m.replaceFirst("w" + bestBucket);
                }
            }
            return model;
        }
    }

}
