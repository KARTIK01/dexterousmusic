package music.dexterous.com.dexterousmusic.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.customeviews.FontTextView;
import music.dexterous.com.dexterousmusic.event.MusicPaused;
import music.dexterous.com.dexterousmusic.event.MusicStop;
import music.dexterous.com.dexterousmusic.event.MusicUnPaused;
import music.dexterous.com.dexterousmusic.event.PlayMusicEvent;
import music.dexterous.com.dexterousmusic.fragment.BaseFragment;
import music.dexterous.com.dexterousmusic.receiver.widget.ToggleMusicReceiver;
import music.dexterous.com.dexterousmusic.utils.image.HomeActivtyBgImageHelper;
import music.dexterous.com.dexterousmusic.utils.image.ImageLoader;
import music.dexterous.com.dexterousmusic.utils.logger.PrettyLogger;
import music.dexterous.com.dexterousmusic.utils.ui.UiUtils;

public class NowPlayingFragment extends BaseFragment {


    public static final String FRAGMENT_TAG = NowPlayingFragment.class.getSimpleName();

    // Views for small bar
    private ImageView mNowPlayingImageView;
    private FontTextView mNowPlayingSongNameTextView;
    private ImageView mToggelButton;


    private SeekBar forword_song_seekbar;
    private FontTextView current_song_playing_time;
    private FontTextView current_song_duration;

    //views for big bar
    private ImageView album_art_image_view;
    private FontTextView song_name_tv;
    private FontTextView song_artist_tv;
    private FontTextView song_album_tv;

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
        album_art_image_view = (ImageView) view.findViewById(R.id.album_art);
        song_name_tv = (FontTextView) view.findViewById(R.id.song_tittle);
        song_artist_tv = (FontTextView) view.findViewById(R.id.song_artist);
        song_album_tv = (FontTextView) view.findViewById(R.id.song_album);


        forword_song_seekbar = (SeekBar) view.findViewById(R.id.forword_song_seekbar);
        current_song_playing_time = (FontTextView) view.findViewById(R.id.current_song_playing_time);
        current_song_duration = (FontTextView) view.findViewById(R.id.current_song_duration);


        safeRegister();

        mToggelButton.setOnClickListener(view1 -> {
            Intent toggleMusicintent = new Intent(getActivity(), ToggleMusicReceiver.class);
            toggleMusicintent.putExtra(ToggleMusicReceiver.ACTION, ToggleMusicReceiver.ACTION_TYPE_TOGGLE);
            getActivity().sendBroadcast(toggleMusicintent);
        });


    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void setUpUI(PlayMusicEvent playMusicEvent) {
        PrettyLogger.d(playMusicEvent.toString());
        //show image into imageView
        HomeActivtyBgImageHelper.setImage(playMusicEvent, getActivity(), mNowPlayingImageView, false);
        HomeActivtyBgImageHelper.setImage(playMusicEvent, getActivity(), album_art_image_view, false);

        //show song Name
        mNowPlayingSongNameTextView.setText(playMusicEvent.music.getSONG_TITLE());
        song_name_tv.setText(playMusicEvent.music.getSONG_TITLE());
        song_artist_tv.setText(playMusicEvent.music.getSONG_ARTIST());
        song_album_tv.setText(playMusicEvent.music.getSONG_ALBUM());
        current_song_duration.setText(playMusicEvent.music.getSONG_DURATION());

        //show image for play or pause
        if (playMusicEvent.music.getSONG_IS_PLAYING() != null && playMusicEvent.music.getSONG_IS_PLAYING()) {
            new ImageLoader(getContext()).loadImage(getContext(), UiUtils.ic_pause_vector, mToggelButton);
        } else {
            new ImageLoader(getContext()).loadImage(getContext(), UiUtils.ic_play_vector, mToggelButton);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void pause(MusicPaused musicPaused) {
        new ImageLoader(getContext()).loadImage(getContext(), UiUtils.ic_play_vector, mToggelButton);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void unpause(MusicUnPaused musicUnPaused) {
        new ImageLoader(getContext()).loadImage(getContext(), UiUtils.ic_pause_vector, mToggelButton);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void stoped(MusicStop musicStop) {
        new ImageLoader(getContext()).loadImage(getContext(), UiUtils.ic_play_vector, mToggelButton);
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
