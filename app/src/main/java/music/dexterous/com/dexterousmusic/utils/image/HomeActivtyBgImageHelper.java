package music.dexterous.com.dexterousmusic.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.widget.ImageView;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.databaseutils.DataManager;
import music.dexterous.com.dexterousmusic.event.PlayMusicEvent;
import music.dexterous.com.dexterousmusic.models.AlbumModel;

/**
 * Created by Honey on 8/21/2016.
 */
public class HomeActivtyBgImageHelper {

    /**
     * set albumArt of  upDateHomeActivityEvent into imageView if it exist else ramdom other drawable image will be shown
     *
     * @param upDateHomeActivityEvent
     * @param context
     * @param imageView
     * @param isBlur
     */
    public static void setImage(PlayMusicEvent upDateHomeActivityEvent, Context context, ImageView imageView, boolean isBlur) {
        String albumArtPath = "";
        String songAlbum = upDateHomeActivityEvent.music.getSONG_ALBUM();
        AlbumModel albumModel = DataManager.getInstance(context).getAlbumsMap().get(songAlbum);
        if (albumModel != null) {
            albumArtPath = albumModel.getAlbumArtPath();
        }
        setImage(context, albumArtPath, imageView, isBlur);
    }

    public static void setImage(Context context, String albumArtPath, ImageView imageView, boolean isBlur) {
        if (!TextUtils.isEmpty(albumArtPath)) {

            Bitmap songCoverImage = BitmapFactory.decodeFile(albumArtPath);
            if (songCoverImage != null) {
                //TODO make blur via glide and {@link BlurTransformation}
                if (isBlur)
                    new ImageLoader(context, R.drawable.bg_1)
                            .loadImage(context, BlurBuilder.blur(context, songCoverImage), imageView);
                else
                    new ImageLoader(context)
                            .loadImage(context, albumArtPath, imageView);
                return;
            }
        }
        showRandomImage(context, imageView, isBlur);
    }


    private static void showRandomImage(Context context, ImageView imageView, boolean isBlr) {
        if (isBlr) {
            new ImageLoader(context)
                    .loadImage(context, BlurBuilder.blur(context, BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_1)), imageView);

        } else {
            new ImageLoader(context)
                    .loadImage(context, R.drawable.bg_1, imageView);

        }

    }
}
