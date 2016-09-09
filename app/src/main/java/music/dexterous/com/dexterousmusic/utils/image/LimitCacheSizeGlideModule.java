package music.dexterous.com.dexterousmusic.utils.image;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

import music.dexterous.com.dexterousmusic.utils.android.InternalStorageSize;

/**
 * Created by Honey on 8/1/2016.
 */
public class LimitCacheSizeGlideModule implements GlideModule {

    private static final int SMALL_INTERNAL_STORAGE_THRESHOLD_GIB           = 6;
    private static final int DISK_CACHE_SIZE_FOR_SMALL_INTERNAL_STORAGE_MIB = 80 * 1024 * 1024;
    private static final int DISK_CACHE_SIZE_FOR_LARGE_INTERNAL_STORAGE_MIB = 150 * 1024 * 1024;


    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        double totalGiB = InternalStorageSize.getTotalGiBOfInternalStorage();
        if (totalGiB < SMALL_INTERNAL_STORAGE_THRESHOLD_GIB) {
            builder.setDiskCache(new InternalCacheDiskCacheFactory(context, DISK_CACHE_SIZE_FOR_SMALL_INTERNAL_STORAGE_MIB));
        } else {
            builder.setDiskCache(new InternalCacheDiskCacheFactory(context, DISK_CACHE_SIZE_FOR_LARGE_INTERNAL_STORAGE_MIB));
        }
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }


}
