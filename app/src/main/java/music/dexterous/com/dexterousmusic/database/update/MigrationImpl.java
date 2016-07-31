package music.dexterous.com.dexterousmusic.database.update;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

/**
 * An abstract base class with the basic implementation to promote reuse.
 *
 * @author Kartik
 */
public abstract class MigrationImpl implements Migration {

    /**
     * A helper method which helps the migration prepare by passing the call up the chain.
     *
     * @param sqLiteDatabase
     * @param currentVersion
     */
    protected void prepareMigration(@NonNull SQLiteDatabase sqLiteDatabase,
                                    int currentVersion) {
        if (sqLiteDatabase == null) {
            return;
        }
        if (currentVersion < 1) {
            throw new IllegalArgumentException(
                    "Lowest suported schema version is 1, unable to prepare for migration from version: "
                            + currentVersion);
        }

        if (currentVersion < getTargetVersion()) {
            Migration previousMigration = getPreviousMigration();

            if (previousMigration == null) {
                // This is the first migration
                if (currentVersion != getTargetVersion()) {
                    throw new IllegalStateException(
                            "Unable to apply migration as Version: "
                                    + currentVersion
                                    + " is not suitable for this Migration.");
                }
            }
            if (previousMigration.applyMigration(sqLiteDatabase, currentVersion) != getTargetVersion()) {
                // For all other migrations ensure that after the earlier
                // migration has been applied the expected version matches
                throw new IllegalStateException(
                        "Error, expected migration parent to update database to appropriate version");
            }
        }
    }
}
