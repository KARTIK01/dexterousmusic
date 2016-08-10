package music.dexterous.com.dexterousmusic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.activity.BaseActivity;

public class NowPlayingFragment extends BaseFragment {


    public static NowPlayingFragment newInstance() {
        NowPlayingFragment fragment = new NowPlayingFragment();
        Bundle info = new Bundle();
        fragment.setArguments(info);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.now_playing_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
