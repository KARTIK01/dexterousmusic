package music.dexterous.com.dexterousmusic.database.update.migrate;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import music.dexterous.com.dexterousmusic.database.update.Migration;
import music.dexterous.com.dexterousmusic.database.update.MigrationImpl;

/**
 * Migration from Version1 to Version2
 *
 * @author Kartik
 */
public class MigrateV2ToV3 extends MigrationImpl {
    /**
     * {@inheritDoc}
     */
    @Override
    public int applyMigration(@NonNull SQLiteDatabase db,
                              int currentVersion) {
        super.prepareMigration(db, currentVersion);

        // TODO : Apply required database update

        return getMigrationVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTargetVersion() {
        return 2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMigrationVersion() {
        return 3;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Migration getPreviousMigration() {
        return new MigrateV1ToV2();
    }
}
