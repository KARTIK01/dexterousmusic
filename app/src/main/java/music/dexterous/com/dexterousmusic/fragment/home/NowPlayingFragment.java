package music.dexterous.com.dexterousmusic.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.customeviews.FontTextView;
import music.dexterous.com.dexterousmusic.event.PlayMusicEvent;
import music.dexterous.com.dexterousmusic.fragment.BaseFragment;
import music.dexterous.com.dexterousmusic.receiver.widget.ToggleMusicReceiver;
import music.dexterous.com.dexterousmusic.utils.image.HomeActivtyBgImageHelper;
import music.dexterous.com.dexterousmusic.utils.image.ImageLoader;
import music.dexterous.com.dexterousmusic.utils.logger.PrettyLogger;
import music.dexterous.com.dexterousmusic.utils.ui.UiUtils;

public class NowPlayingFragment extends BaseFragment {


    public static final String FRAGMENT_TAG = NowPlayingFragment.class.getSimpleName();

    private ImageView mNowPlayingImageView;
    private FontTextView mNowPlayingSongNameTextView;
    private ImageView mToggelButton;

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

        mNowPlayingImageView = (ImageView) view.findViewById(R.id.now_playing_imageview);
        mNowPlayingSongNameTextView = (FontTextView) view.findViewById(R.id.now_playing_song_name_textview);
        mToggelButton = (ImageView) view.findViewById(R.id.toggle);

        safeRegister();
        mToggelButton.setOnClickListener(view1 -> {
            Intent buttonPlayIntent = new Intent(getActivity(), ToggleMusicReceiver.class);
            buttonPlayIntent.putExtra(ToggleMusicReceiver.ACTION, ToggleMusicReceiver.ACTION_TYPE_TOGGLE);
            getActivity().sendBroadcast(buttonPlayIntent);
        });


//        ImageView album_art = (ImageView) view.findViewById(R.id.album_art);
//        album_art.setOnClickListener(view1 -> {
//
//            getActivity().getSupportFragmentManager()
//                    .beginTransaction()
//                    .add(R.id.rootHomeContainerBottom, PlayListFragment.newInstance(), PlayListFragment.FRAGMENT_TAG)
//                    .commit();
//        });
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void setUpUI(PlayMusicEvent playMusicEvent) {
        //show image into imageView
        HomeActivtyBgImageHelper.setImage(playMusicEvent, getActivity(), mNowPlayingImageView, false);
        //show song Name
        mNowPlayingSongNameTextView.setText(playMusicEvent.music.getSONG_TITLE());

        //show image for play or pause
        if (playMusicEvent.music.getSONG_IS_PLAYING() != null && playMusicEvent.music.getSONG_IS_PLAYING()) {
            new ImageLoader(getContext()).loadImage(getContext(), UiUtils.ic_pause_vector, mToggelButton);
        } else {
            new ImageLoader(getContext()).loadImage(getContext(), UiUtils.ic_play_vector, mToggelButton);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        safeRegister();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregister();
    }
}
