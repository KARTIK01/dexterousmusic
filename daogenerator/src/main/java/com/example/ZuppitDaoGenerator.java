package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class ZuppitDaoGenerator {
    public static final int SCHEMA_VERSION = 0;

    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(SCHEMA_VERSION, "music.dexterous.com.dexterousmusic.database");

        Entity newsItems = addNewsIemEntity(schema);
        Entity chunkInfo = addChunkTable(schema);

        addUserInfoEntity(schema);
        addCategoryTable(schema);
        addLanguageTable(schema);
        addLiveScoreCricketTable(schema);
        leaderBoard(schema);

        /**
         * One-To-Many relations for chunk to news-items
         */
        Property chunkIdProperty = newsItems.addLongProperty("chunkId").index().getProperty();
        chunkInfo.addToMany(newsItems, chunkIdProperty);

        schema.enableKeepSectionsByDefault();
        String generatedModelPath = "app/src/main/java";
        new DaoGenerator().generateAll(schema, generatedModelPath);
    }

    /**
     * Store all generated codes
     */
    private static Entity addNewsIemEntity(Schema schema) {
        Entity newsItemEntity = schema.addEntity("NewsItem");

        newsItemEntity.addLongProperty("id").primaryKey();

        newsItemEntity.addStringProperty("title");
        newsItemEntity.addStringProperty("summary");
        newsItemEntity.addStringProperty("shortUrl");
        newsItemEntity.addStringProperty("sourceName");
        newsItemEntity.addStringProperty("imageLink");
        newsItemEntity.addStringProperty("url");
        newsItemEntity.addStringProperty("preview");
        newsItemEntity.addStringProperty("timestamp");

        newsItemEntity.addStringProperty("categories")
                .index().customType("java.util.HashSet<String>",
                "com.lockscreen.zuppit.database.custom.HashPropertyConverter");
        newsItemEntity.addStringProperty("language").index();
        newsItemEntity.addBooleanProperty("isRead").index();

        newsItemEntity.addBooleanProperty("isBookmark");

        newsItemEntity.addDateProperty("updatedAt");
        newsItemEntity.addDateProperty("syncedAt");

        newsItemEntity.implementsInterface("Parcelable");

        return newsItemEntity;
    }

    /**
     * Store all generated codes
     */
    private static Entity addUserInfoEntity(Schema schema) {
        Entity userInfoEntity = schema.addEntity("User");

        userInfoEntity.addIdProperty().primaryKey();
        userInfoEntity.addStringProperty("phone");
        userInfoEntity.addStringProperty("email");
        userInfoEntity.addStringProperty("deviceId");
        userInfoEntity.addStringProperty("token");

        userInfoEntity.addDateProperty("createdAt");
        userInfoEntity.addDateProperty("updatedAt");

        return userInfoEntity;
    }

    /**
     * Chunks info stored
     */
    private static Entity addChunkTable(Schema schema) {
        Entity chunkInfo = schema.addEntity("ChunkInfo");

        chunkInfo.addLongProperty("chunkId").primaryKey();
        chunkInfo.addLongProperty("prev");
        chunkInfo.addLongProperty("next");
        chunkInfo.addBooleanProperty("isComplete");
        chunkInfo.addStringProperty("fetchedLanguages")
                .index()
                .customType("java.util.HashSet<String>",
                        "com.lockscreen.zuppit.database.custom.HashPropertyConverter");

        return chunkInfo;
    }

    /**
     * Supported Categories
     */
    private static Entity addCategoryTable(Schema schema) {
        Entity categoryInfo = schema.addEntity("Category");

        categoryInfo.addIdProperty();
        categoryInfo.addStringProperty("language");
        categoryInfo.addStringProperty("name").codeBeforeField("@SerializedName(\"name\")");
        categoryInfo.addStringProperty("englishName").codeBeforeField("@SerializedName(\"english_name\")");
        categoryInfo.addStringProperty("imageUrl").codeBeforeField("@SerializedName(\"icon_url\")");
        categoryInfo.addBooleanProperty("isInFeed");

        return categoryInfo;
    }

    /**
     * Supported languages
     */
    private static Entity addLanguageTable(Schema schema) {
        Entity language = schema.addEntity("Language");

        language.addIdProperty();
        language.addStringProperty("name");
        language.addStringProperty("englishName");
        language.addDateProperty("createdAt");
        language.addDateProperty("updatedAt");

        return language;
    }

    private static Entity addLiveScoreCricketTable(Schema schema) {
        Entity cricketLiveScore = schema.addEntity("CricketLiveScore");

        cricketLiveScore.addIdProperty();
        cricketLiveScore.addStringProperty("matchId").unique().index();

        cricketLiveScore.addIntProperty("state");

        cricketLiveScore.addBooleanProperty("showNotification");

        cricketLiveScore.addStringProperty("match_type");
        cricketLiveScore.addStringProperty("match_state");
        cricketLiveScore.addStringProperty("match_series");
        cricketLiveScore.addStringProperty("match_desc");
        cricketLiveScore.addStringProperty("match_status");
        cricketLiveScore.addStringProperty("start_time");

        cricketLiveScore.addStringProperty("runs_team1");
        cricketLiveScore.addStringProperty("desc_team1");
        cricketLiveScore.addStringProperty("Decl_team1");
        cricketLiveScore.addStringProperty("FollowOn_team1");
        cricketLiveScore.addStringProperty("ovrs_team1");
        cricketLiveScore.addStringProperty("wkts_team1");
        cricketLiveScore.addStringProperty("name_team1");
        cricketLiveScore.addIntProperty("id_team1_team1");
        cricketLiveScore.addStringProperty("flag_team1_team1");


        cricketLiveScore.addStringProperty("runs_team2");
        cricketLiveScore.addStringProperty("desc_team2");
        cricketLiveScore.addStringProperty("Decl_team2");
        cricketLiveScore.addStringProperty("FollowOn_team2");
        cricketLiveScore.addStringProperty("ovrs_team2");
        cricketLiveScore.addStringProperty("wkts_team2");
        cricketLiveScore.addStringProperty("name_team2");
        cricketLiveScore.addIntProperty("id_team2");
        cricketLiveScore.addStringProperty("flag_team2");
        cricketLiveScore.addStringProperty("timestamp");

        cricketLiveScore.addStringProperty("short_url");

        cricketLiveScore.implementsInterface("Parcelable");
        return cricketLiveScore;
    }

    private static Entity leaderBoard(Schema schema) {
        Entity leaderBoard = schema.addEntity("Leaderboard");

        leaderBoard.addIdProperty();

        leaderBoard.addStringProperty("downloads");
        leaderBoard.addStringProperty("filter");
        leaderBoard.addStringProperty("name");

        leaderBoard.implementsInterface("Parcelable");
        return leaderBoard;
    }
}
