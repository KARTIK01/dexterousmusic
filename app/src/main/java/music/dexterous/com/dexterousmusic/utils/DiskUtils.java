package music.dexterous.com.dexterousmusic.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import music.dexterous.com.dexterousmusic.utils.logger.LogUtils;
import music.dexterous.com.dexterousmusic.utils.ui.UiUtils;

/**
 * Created by Kartik on 14/01/16.
 */
public class DiskUtils {

    /**
     * tag for logging
     */
    private static final String TAG = LogUtils.makeLogTag("DiskUtils");

    /**
     * return the uri to file for sharing the image of the passed view.
     * If it already exists, return uri to that file else generate and return uri
     * to that file
     */
    public static Uri readOrGenerateFileUri(View view, String fileIdSuffix) {
        return readOrGenerateInternalFileUri(view.getContext(), view, fileIdSuffix, true);
    }

    /**
     * return the uri to file for sharing the image of the passed view.
     * If it already exists, return uri to that file else generate and return uri
     * to that file
     * URI is in form of
     * content://com.lockscreen.zuppit.debug.imageprovider/shareimages/zuppit-referral.jpg
     */
    public static Uri readOrGenerateInternalFileUri(Context context, View view, String fileIdSuffix, boolean shouldUseCache) {
        String fileName = getShareImageFileNameById(fileIdSuffix);
        Uri imageFileUri;
        if (shouldUseCache && checkImageExists(context, fileName)) {
            imageFileUri = getInternalUriForFile(context, new File(getImageStorePath(context), fileName));
        } else {
            Bitmap bitmap = UiUtils.renderViewOnBitmapForShare(view);
            imageFileUri = generateFileUriInInternalMemory(context, fileName, bitmap);
        }
        return imageFileUri;
    }

    /**
     * @return Uri to the the file being passed.
     * It is assumed that file is stored in the internal storage.
     * Do not use this for files which are stored in external storage.
     */
    private static Uri getInternalUriForFile(Context context, File theFile) {
        return FileProvider.getUriForFile(context.getApplicationContext(), context.getApplicationContext().getPackageName() + ".imageprovider", theFile);
    }

    /**
     * @return Either create or use already existing image file uri for the view being passed.
     * Name of the file is generated from the @newsItemId being passed.
     */
    public static Uri readOrGenerateBookmarkFileUri(View view, long newsItemId) {
        return readOrGenerateInternalFileUri(view.getContext(), view, "bookmark-" + newsItemId, true);
    }

    /**
     * @return Either create or use already existing image file uri for the view being passed.
     * Name of the file is generated from the @itemId being passed.
     */
    public static Uri readOrGenerateLiveScoreFileUri(View view, String itemId) {
        return readOrGenerateInternalFileUri(view.getContext(), view, "liveScore-" + itemId, false);
    }

    /**
     * @return Create image file uri for the view being passed.
     */
    public static Uri generateReferralFileUri(View view) {
        return readOrGenerateInternalFileUri(view.getContext(), view, "referral", false);
    }

    /**
     * @return name of the file where screenshot for the current view of the news item
     * shall be stored. This will be used while sharing the news with other people
     */
    private static String getShareImageFileNameById(String fileIdSuffix) {
        return "zuppit-" + fileIdSuffix + ".jpg";
    }

    /**
     * @return True if image - file with the given name exists
     */
    public static boolean checkImageExists(Context context, String fileName) {
        boolean isFound = false;
        try {
            isFound = new File(getImageStorePath(context) + fileName).exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isFound;
    }

    /**
     * @return URI to stored image
     * TODO   if want to use this method them update this for internal storage
     */
    @Nullable
    @Deprecated
    public static Uri generateFileUri(String fileName, Bitmap bitmap) {
        // Store image to default external storage directory
        Uri bmpUri = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File imageFile = new File(fileName);
            if (!imageFile.getParentFile().exists()) {
                imageFile.getParentFile().mkdirs();
            }
            try {
                imageFile.createNewFile();
                FileOutputStream picOut = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, picOut);
                picOut.flush();
                picOut.close();
                bmpUri = Uri.fromFile(imageFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bmpUri;
    }

    /**
     * @return URI to stored image in internal memory
     */
    @Nullable
    public static Uri generateFileUriInInternalMemory(Context context, String fileName, Bitmap bitmap) {
        // Store image to default internal storage directory
        String imageFileName = getImageStorePath(context) + fileName;

        File imageFile = new File(imageFileName);
        if (!imageFile.getParentFile().exists()) {
            imageFile.getParentFile().mkdirs();
        }
        try {
            imageFile.createNewFile();
            FileOutputStream picOut = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, picOut);
            picOut.flush();
            picOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getInternalUriForFile(context, imageFile);
    }

    /**
     * delete all the files within @directoryPath directory
     * <p>
     * TODO this method is always returning false
     */
    public static boolean deleteDirectory(File directory) {
        LogUtils.LOGD(TAG, "deleteDirectory() called with: " + "directory = [" + directory + "]");
        if (directory == null) return false;

        File[] filesInDirectory = directory.listFiles();
        if (filesInDirectory == null) {
            return true;
        }

        boolean success = false;
        for (File file : filesInDirectory) {
            LogUtils.LOGD(TAG, "deleteDirectory: " + file.getName());
            if (file.isDirectory()) {
                success |= deleteDirectory(file);
            } else {
                success |= file.delete();
            }
        }
        return success;
    }

    /**
     * @return the storage folder path for the images within the app
     * @Deprecated
     */
    public static String getImageStorePath(Context context) {
        return getAppInternalStoragePath(context) + File.separator + "shareimages" + File.separator;
    }

    /**
     * @return the internal storage path for app
     */
    private static String getAppInternalStoragePath(Context context) {
        return context.getFilesDir().getAbsolutePath();
    }

    /**
     * @return Directory name for extracted font files
     */
    public static String getExtractFontFilePath(Context context, String fontName) {
        String outputFilePath = getAppInternalStoragePath(context) + File.separator + "fonts" + File.separator;
        new File(outputFilePath).mkdirs();
        extractZippedFont(context.getAssets(), outputFilePath, "fonts" + File.separator + fontName + ".zip");
        return outputFilePath + fontName;
    }

    /**
     * Unzips the file zipname to the path
     */
    public static boolean extractZippedFont(AssetManager assetManager, String outputFilePath, String zipFontFileName) {
        InputStream is = null;
        ZipInputStream zis = null;
        try {
            is = assetManager.open(zipFontFileName);
            zis = new ZipInputStream(new BufferedInputStream(is));
            byte[] buffer = new byte[1024];
            int count;

            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                File file = new File(outputFilePath + zipEntry.getName());

                if (zipEntry.isDirectory()) {
                    if (!file.exists())
                        file.mkdirs();
                    continue;
                } else {
                    /**
                     * Make sure path to file exists
                     */
                    file.getParentFile().mkdirs();
                }

                FileOutputStream fout = null;
                try {
                    fout = new FileOutputStream(file);
                    while ((count = zis.read(buffer)) != -1) {
                        fout.write(buffer, 0, count);
                    }
                    fout.flush();
                } finally {
                    if (fout != null)
                        fout.close();
                }
                zis.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (zis != null) {
                    zis.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
