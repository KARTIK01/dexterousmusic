package music.dexterous.com.dexterousmusic.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.List;

import music.dexterous.com.dexterousmusic.utils.PlayStore;

/**
 * Created by Honey on 8/17/2016.
 */
public class DeepLinkingActivity extends BaseActivity {

    private static final String INTENT_PREFIX = "com.dexterous.";

    public static final String ACTION_OPEN_PLAY_STORE = INTENT_PREFIX + "action.playstore";


    public static Intent getIntent(Context context, String action) {
        Intent intent = new Intent(context, DeepLinkingActivity.class);
        intent.setAction(action);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        String host;
        String scheme;
        List<String> listUrlPrefixes;

        if (intent != null) {
            String action = intent.getAction();
            switch (action) {
                case ACTION_OPEN_PLAY_STORE:
                    PlayStore.openAppRating(DeepLinkingActivity.this);
                    finish();
            }
        }

    }
}
