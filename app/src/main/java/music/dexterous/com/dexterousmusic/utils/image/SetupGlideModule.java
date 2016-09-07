package music.dexterous.com.dexterousmusic.utils.image;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Honey on 8/1/2016.
 */
public class SetupGlideModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(20, SECONDS)
                .readTimeout(25, SECONDS)
                .writeTimeout(20, SECONDS)
                .addInterceptor(chain -> {
                    Request  request  = chain.request();
                    Response response = null;

                    boolean responseOk = false;
                    int     tryCount   = 0;

                    while (!responseOk && tryCount < 3) {
                        try {
                            response = chain.proceed(request);
                            responseOk = response.isSuccessful();
                        } catch (Exception e) {
                            e.printStackTrace();
                            //TODO Crashlytics
//                            Crashlytics.logException(e , tryCount);
                        } finally {
                            tryCount++;
                        }
                    }
                    return response;
                });

        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(okHttpClientBuilder.build());
        glide.register(GlideUrl.class, InputStream.class, factory);
    }
}
