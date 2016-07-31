package music.dexterous.com.dexterousmusic.database.update.migrate;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import music.dexterous.com.dexterousmusic.database.update.Migration;
import music.dexterous.com.dexterousmusic.database.update.MigrationImpl;

/**
 * Migration from Version1 to Version2
 *
 * @author Kartik
 */
public class MigrateV1ToV2 extends MigrationImpl {
    @Override
    public int applyMigration(@NonNull SQLiteDatabase db, int currentVersion) {
        prepareMigration(db, currentVersion);

        // TODO : Apply required database update

        return getMigrationVersion();
    }

    @Nullable
    @Override
    public Migration getPreviousMigration() {
        return null;
    }

    @Override
    public int getTargetVersion() {
        return 1;
    }

    @Override
    public int getMigrationVersion() {
        return 2;
    }
}
