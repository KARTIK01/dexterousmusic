package music.dexterous.com.dexterousmusic.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestListener;

import music.dexterous.com.dexterousmusic.BuildConfig;
import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.utils.image.transformation.BlurTransformation;

/**
 * Created by Honey on 8/1/2016.
 */
public class ImageLoader extends ImageLoaderHelper {

    public ImageLoader(Context context) {
        super(context, new CenterCrop(Glide.get(context).getBitmapPool()));
    }

    public ImageLoader(Context context, Transformation<Bitmap> transformation) {
        super(context, transformation);
    }


    /**
     * Construct an ImageLoaderHelper with a default placeholder drawable.
     */
    public ImageLoader(Context context, Integer placeHolderResId, Transformation<Bitmap> transformation) {
        super(context, placeHolderResId, transformation);
    }

    /**
     * Construct an ImageLoaderHelper with a default placeholder drawable.
     */
    public ImageLoader(Context context, Integer placeHolderResId) {
        super(context, placeHolderResId);
    }

    /**
     * Construct an ImageLoaderHelper with a default placeholder drawable.
     */
    public ImageLoader(Context context, int placeHolderResId, DiskCacheStrategy strategy) {
        super(context, placeHolderResId, strategy);
    }

    /**
     * Load an image from a url into an ImageView using the default placeholder
     * drawable if available.
     *
     * @param url             The web URL of an image.
     * @param imageView       The target ImageView to load the image into.
     * @param requestListener A listener to monitor the request result.
     */
    public void loadImage(String url, ImageView imageView, RequestListener<String, Bitmap> requestListener) {
        loadImage(url, imageView, requestListener, null, shouldCropByDefault);
    }

    public void loadImage(String url, ImageView imageView, int placeholderOverride, RequestListener<String, Bitmap> requestListener) {
        loadImage(url, imageView, requestListener, placeholderOverride, shouldCropByDefault /*crop*/);
    }

    public void loadImage(String url, ImageView imageView, int placeholderOverride) {
        loadImage(url, imageView, null, placeholderOverride, shouldCropByDefault /*crop*/);
    }

    /**
     * Load an image from a url into an ImageView using the given placeholder drawable.
     *
     * @param url                 The web URL of an image.
     * @param imageView           The target ImageView to load the image into.
     * @param requestListener     A listener to monitor the request result.
     * @param placeholderOverride A placeholder to use in place of the default placholder.
     */
    public void loadImage(String url, ImageView imageView, RequestListener<String, Bitmap> requestListener,
                          Integer placeholderOverride) {
        loadImage(url, imageView, requestListener, placeholderOverride, shouldCropByDefault /*crop*/);
    }

    /**
     * Load an image from a url into an ImageView using the default placeholder
     * drawable if available.
     *
     * @param url                 The web URL of an image.
     * @param imageView           The target ImageView to load the image into.
     * @param requestListener     A listener to monitor the request result.
     * @param placeholderOverride A drawable to use as a placeholder for this specific image.
     *                            If this parameter is present, {@link #mPlaceHolderResId}
     *                            if ignored for this request.
     */
    private void loadImage(String url, ImageView imageView, RequestListener<String, Bitmap> requestListener,
                           Integer placeholderOverride, boolean crop) {
        BitmapRequestBuilder<String, Bitmap> request = beginImageLoad(url, requestListener, crop)
                .animate(R.anim.image_fade_in);

        if (placeholderOverride != null) {
            request.placeholder(placeholderOverride);
        } else if (mPlaceHolderResId != null) {
            request.placeholder(mPlaceHolderResId);
        }
        request.diskCacheStrategy(mDiskCacheStrategy);
        request.into(imageView);
    }


    /**
     * Load an image from a url into the given image view using the default placeholder if
     * available.
     *
     * @param url       The web URL of an image.
     * @param imageView The target ImageView to load the image into.
     */
    public void loadImage(String url, ImageView imageView) {
        loadImage(url, imageView, shouldCropByDefault /*crop*/);
    }

    /**
     * Load an image from a url into an ImageView using the default placeholder
     * drawable if available.
     *
     * @param url       The web URL of an image.
     * @param imageView The target ImageView to load the image into.
     * @param crop      True to apply a center crop to the image.
     */
    public void loadImage(String url, ImageView imageView, boolean crop) {
        loadImage(url, imageView, null, null, crop);
    }

    /**
     * to load a drawable into imageview
     */
    public void loadImage(Context context, @DrawableRes int drawableResId, ImageView imageView) {
        /**
         * set cache-strategy to result as reading source is pretty fast and we don't have
         * to keep that in memory
         */
        Glide.with(context).load(drawableResId)
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }

    /**
     * to load a drawable into imageview
     */
    public void loadImage(Context context, Drawable drawableResId, ImageView imageView) {
        imageView.setImageDrawable(drawableResId);
    }

    /**
     * to load image from byte array
     *
     * @param context
     * @param imageInBytes
     * @param imageView
     */
    public void loadImage(Context context, byte[] imageInBytes, ImageView imageView) {
        Glide.with(context).load(imageInBytes)
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }

    /**
     * to load image from byte array
     *
     * @param context
     * @param bitmap
     * @param imageView
     */
    public void loadImage(Context context, Bitmap bitmap, ImageView imageView) {
//        Glide.with(context).load(bitmap)
//                .priority(Priority.IMMEDIATE)
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .into(imageView);
        //TODO use glide for this of requires caching and images are loading show
        imageView.setImageBitmap(bitmap);
    }


    /**
     * to load image from byte array
     *
     * @param context
     * @param albumArtPath
     * @param imageView
     */
    public static void loadBlurImage(Context context, Bitmap albumArtPath, ImageView imageView) {
        Glide.with(context).load(albumArtPath)
                .bitmapTransform(new BlurTransformation(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .asBitMap()
                .into(imageView);
    }

}
