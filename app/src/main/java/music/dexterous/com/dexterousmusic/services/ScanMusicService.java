package music.dexterous.com.dexterousmusic.services;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;

import music.dexterous.com.dexterousmusic.DBHelper.DBAccessHelper;
import music.dexterous.com.dexterousmusic.DBHelper.MediaStoreAccessHelper;
import music.dexterous.com.dexterousmusic.GlobalApplication;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class ScanMusicService extends IntentService {
    GlobalApplication mApp;
    private String mMediaStoreSelection = null;

    public ScanMusicService() {
        super("ScanMusicService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    private Cursor getSongsFromMediaStore() {
        //Get a cursor of all active music folders.
        Cursor musicFoldersCursor = mApp.getDBAccessHelper().getAllMusicFolderPaths();

        //Build the appropriate selection statement.
        Cursor mediaStoreCursor = null;
        String sortOrder = null;
        String projection[] = {MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.TRACK,
                MediaStore.Audio.Media.YEAR,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DATE_ADDED,
                MediaStore.Audio.Media.DATE_MODIFIED,
                MediaStore.Audio.Media._ID,
                MediaStoreAccessHelper.ALBUM_ARTIST};

        //Grab the cursor of MediaStore entries.
        if (musicFoldersCursor == null || musicFoldersCursor.getCount() < 1) {
            //No folders were selected by the user. Grab all songs in MediaStore.
            mediaStoreCursor = MediaStoreAccessHelper.getAllSongs(this, projection, sortOrder);
        } else {
            //Build a selection statement for querying MediaStore.
            mMediaStoreSelection = buildMusicFoldersSelection(musicFoldersCursor);
            mediaStoreCursor = MediaStoreAccessHelper.getAllSongsWithSelection(this,
                    mMediaStoreSelection,
                    projection,
                    sortOrder);

            //Close the music folders cursor.
            musicFoldersCursor.close();
        }

        return mediaStoreCursor;
    }

    private String buildMusicFoldersSelection(Cursor musicFoldersCursor) {
        String mediaStoreSelection = MediaStore.Audio.Media.IS_MUSIC + "!=0 AND (";
        int folderPathColIndex = musicFoldersCursor.getColumnIndex(DBAccessHelper.FOLDER_PATH);
        int includeColIndex = musicFoldersCursor.getColumnIndex(DBAccessHelper.INCLUDE);

        for (int i = 0; i < musicFoldersCursor.getCount(); i++) {
            musicFoldersCursor.moveToPosition(i);
            boolean include = musicFoldersCursor.getInt(includeColIndex) > 0;

            //Set the correct LIKE clause.
            String likeClause;
            if (include)
                likeClause = " LIKE ";
            else
                likeClause = " NOT LIKE ";

            //The first " AND " clause was already appended to mediaStoreSelection.
            if (i != 0 && !include)
                mediaStoreSelection += " AND ";
            else if (i != 0 && include)
                mediaStoreSelection += " OR ";

            mediaStoreSelection += MediaStore.Audio.Media.DATA + likeClause
                    + "'%" + musicFoldersCursor.getString(folderPathColIndex)
                    + "/%'";

        }

        //Append the closing parentheses.
        mediaStoreSelection += ")";
        return mediaStoreSelection;
    }


}
