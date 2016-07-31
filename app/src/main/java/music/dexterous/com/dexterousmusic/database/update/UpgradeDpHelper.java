package music.dexterous.com.dexterousmusic.database.update;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import music.dexterous.com.dexterousmusic.database.DaoMaster;
import music.dexterous.com.dexterousmusic.database.update.migrate.MigrateV1ToV2;
import music.dexterous.com.dexterousmusic.database.update.migrate.MigrateV2ToV3;

/**
 * Created by Honey on 6/23/2016.
 */

public class UpgradeDpHelper extends DaoMaster.OpenHelper {

    private static final int DIFF = 100;

    private final Context mContext;

    public UpgradeDpHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
        this.mContext = context;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        If old version is very old
        if (newVersion - oldVersion > DIFF) {
            DaoMaster.dropAllTables(db, true);
            DaoMaster.createAllTables(db, false);
            return;
        }

        switch (newVersion) {
            case 2:
                new MigrateV1ToV2().applyMigration(db, oldVersion);
                break;
            case 3:
                new MigrateV2ToV3().applyMigration(db, oldVersion);
                break;
            default:
                return;
        }
    }

}
