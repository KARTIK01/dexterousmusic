package music.dexterous.com.dexterousmusic.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.fragment.BaseFragment;
import music.dexterous.com.dexterousmusic.receiver.widget.ToggleMusicReceiver;

public class NowPlayingFragment extends BaseFragment {


    public static final String FRAGMENT_TAG = NowPlayingFragment.class.getSimpleName();

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

        ImageView toggle = (ImageView) view.findViewById(R.id.toggle);
        toggle.setOnClickListener(view1 -> {
            Intent buttonPlayIntent = new Intent(getActivity(), ToggleMusicReceiver.class);
            buttonPlayIntent.putExtra(ToggleMusicReceiver.ACTION, ToggleMusicReceiver.ACTION_TYPE_TOGGLE);
            getActivity().sendBroadcast(buttonPlayIntent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
