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
import music.dexterous.com.dexterousmusic.customeviews.widget.FontTextView;
import music.dexterous.com.dexterousmusic.customeviews.widget.MusicControlSeekBar;
import music.dexterous.com.dexterousmusic.customeviews.widget.TimerTextView;
import music.dexterous.com.dexterousmusic.event.MusicPaused;
import music.dexterous.com.dexterousmusic.event.MusicStop;
import music.dexterous.com.dexterousmusic.event.MusicUnPaused;
import music.dexterous.com.dexterousmusic.event.PlayMusicEvent;
import music.dexterous.com.dexterousmusic.fragment.BaseFragment;
import music.dexterous.com.dexterousmusic.musicutils.HumanReadableTime;
import music.dexterous.com.dexterousmusic.receiver.widget.NextMusicReceiver;
import music.dexterous.com.dexterousmusic.receiver.widget.PreviousMusicReceiver;
import music.dexterous.com.dexterousmusic.receiver.widget.ToggleMusicReceiver;
import music.dexterous.com.dexterousmusic.service.DexterousPlayMusicService;
import music.dexterous.com.dexterousmusic.utils.image.HomeActivtyBgImageHelper;
import music.dexterous.com.dexterousmusic.utils.image.ImageLoader;
import music.dexterous.com.dexterousmusic.utils.music.RepeatMode;
import music.dexterous.com.dexterousmusic.utils.preference.UsersAppPreference;
import music.dexterous.com.dexterousmusic.utils.ui.UiUtils;

public class NowPlayingFragment extends BaseFragment implements View.OnClickListener {


    public static final String FRAGMENT_TAG = NowPlayingFragment.class.getSimpleName();

    // Views for small bar
    private ImageView mNowPlayingImageView;
    private FontTextView mNowPlayingSongNameTextView;
    private ImageView mToggelButton;

    //views for big bar
    private MusicControlSeekBar musicControlBar;
    private TimerTextView current_song_playing_time;
    private FontTextView current_song_duration;

    private ImageView album_art_image_view;
    private FontTextView song_name_tv;
    private FontTextView song_artist_tv;
    private FontTextView song_album_tv;

    //controll widget
    private ImageView widget_toggle;
    private ImageView widget_skip_previous;
    private ImageView widget_skip_next;

    private ImageView widget_repeate;
    private ImageView widget_vol_down;
    private ImageView widget_vol_inc;
    private ImageView widget_shuffel;

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
        findViewById(view);
        safeRegister();
        setListener();
    }


    private void findViewById(View view) {

        mNowPlayingImageView = (ImageView) view.findViewById(R.id.now_playing_imageview);
        mNowPlayingSongNameTextView = (FontTextView) view.findViewById(R.id.now_playing_song_name_textview);
        mToggelButton = (ImageView) view.findViewById(R.id.toggle_upper);
        album_art_image_view = (ImageView) view.findViewById(R.id.album_art);
        song_name_tv = (FontTextView) view.findViewById(R.id.song_tittle);
        song_artist_tv = (FontTextView) view.findViewById(R.id.song_artist);
        song_album_tv = (FontTextView) view.findViewById(R.id.song_album);


        widget_toggle = (ImageView) view.findViewById(R.id.widget_toggle);
        widget_skip_previous = (ImageView) view.findViewById(R.id.widget_skip_previous);
        widget_skip_next = (ImageView) view.findViewById(R.id.widget_skip_next);

        widget_repeate = (ImageView) view.findViewById(R.id.widget_repeate);
        widget_vol_down = (ImageView) view.findViewById(R.id.widget_vol_down);
        widget_vol_inc = (ImageView) view.findViewById(R.id.widget_vol_inc);
        widget_shuffel = (ImageView) view.findViewById(R.id.widget_shuffel);

        musicControlBar = (MusicControlSeekBar) view.findViewById(R.id.forword_song_seekbar);
        current_song_playing_time = (TimerTextView) view.findViewById(R.id.current_song_playing_time);
        current_song_duration = (FontTextView) view.findViewById(R.id.current_song_duration);
    }

    private void setListener() {
        //small
        mToggelButton.setOnClickListener(this);

        //big
        widget_toggle.setOnClickListener(this);
        widget_skip_previous.setOnClickListener(this);
        widget_skip_next.setOnClickListener(this);

        widget_repeate.setOnClickListener(this);
        widget_vol_down.setOnClickListener(this);
        widget_vol_inc.setOnClickListener(this);
        widget_shuffel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.widget_toggle:
            case R.id.toggle_upper:
                Intent toggleMusicintent = new Intent(getActivity(), ToggleMusicReceiver.class);
                toggleMusicintent.putExtra(ToggleMusicReceiver.ACTION, ToggleMusicReceiver.ACTION_TYPE_TOGGLE);
                getActivity().sendBroadcast(toggleMusicintent);
                break;
            case R.id.widget_skip_next:
                Intent nextIntent = new Intent(getActivity(), NextMusicReceiver.class);
                nextIntent.putExtra(NextMusicReceiver.ACTION, NextMusicReceiver.ACTION_TYPE_SKIP);
                getActivity().sendBroadcast(nextIntent);
                break;
            case R.id.widget_skip_previous:
                Intent previosIntent = new Intent(getActivity(), PreviousMusicReceiver.class);
                previosIntent.putExtra(PreviousMusicReceiver.ACTION, PreviousMusicReceiver.ACTION_TYPE_SKIP);
                getActivity().sendBroadcast(previosIntent);
                break;
            case R.id.widget_repeate:
                RepeatMode.changeRepeteMode(getContext(), widget_repeate);
                break;
            case R.id.widget_shuffel:
                UsersAppPreference.setMusicShuffleMode(!UsersAppPreference.isMusicShuffle());
                if (UsersAppPreference.isMusicShuffle()) {
                    new ImageLoader(getContext()).loadImage(getContext(), UiUtils.ic_shuffle_on_vector, widget_shuffel);
                } else {
                    new ImageLoader(getContext()).loadImage(getContext(), UiUtils.ic_shuffle_off_vector, widget_shuffel);
                }
                break;
            case R.id.widget_vol_inc:

                break;
            case R.id.widget_vol_down:

                break;


        }
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void setUpUI(PlayMusicEvent playMusicEvent) {
        //show image into imageView
        HomeActivtyBgImageHelper.setImage(playMusicEvent, getActivity(), mNowPlayingImageView, false);
        HomeActivtyBgImageHelper.setImage(playMusicEvent, getActivity(), album_art_image_view, false);

        //show song Name
        mNowPlayingSongNameTextView.setText(playMusicEvent.music.getSONG_TITLE());
        song_name_tv.setText(playMusicEvent.music.getSONG_TITLE());
        song_artist_tv.setText(playMusicEvent.music.getSONG_ARTIST());
        song_album_tv.setText(playMusicEvent.music.getSONG_ALBUM());



        if (DexterousPlayMusicService.mDexterousMediaPlayer != null ) {
            if (musicControlBar != null) {
                musicControlBar.setProgress(DexterousPlayMusicService.mDexterousMediaPlayer.getCurrentPosition());
                musicControlBar.setMax(100);
                musicControlBar.updateProgressBar();
            }
            if (current_song_playing_time != null) {
                current_song_playing_time.updateTimerTextView();
            }
        }


        //Update TextViews and small images
        current_song_duration.setText(HumanReadableTime.getSongsDuration(playMusicEvent.music));
        if (UsersAppPreference.isMusicShuffle()) {
            new ImageLoader(getContext()).loadImage(getContext(), UiUtils.ic_shuffle_on_vector, widget_shuffel);
        } else {
            new ImageLoader(getContext()).loadImage(getContext(), UiUtils.ic_shuffle_off_vector, widget_shuffel);
        }
        RepeatMode.showRepeatIcon(getActivity(), widget_repeate);
        //show image for play or pause
        if (playMusicEvent.music.getSONG_IS_PLAYING() != null && playMusicEvent.music.getSONG_IS_PLAYING()) {
            new ImageLoader(getContext()).loadImage(getContext(), UiUtils.ic_pause_vector, mToggelButton);
            new ImageLoader(getContext()).loadImage(getContext(), UiUtils.ic_pause_vector, widget_toggle);
        } else {
            new ImageLoader(getContext()).loadImage(getContext(), UiUtils.ic_play_vector, mToggelButton);
            new ImageLoader(getContext()).loadImage(getContext(), UiUtils.ic_play_vector, widget_toggle);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void pause(MusicPaused musicPaused) {
        new ImageLoader(getContext()).loadImage(getContext(), UiUtils.ic_play_vector, mToggelButton);
        new ImageLoader(getContext()).loadImage(getContext(), UiUtils.ic_play_vector, widget_toggle);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void unpause(MusicUnPaused musicUnPaused) {
        new ImageLoader(getContext()).loadImage(getContext(), UiUtils.ic_pause_vector, mToggelButton);
        new ImageLoader(getContext()).loadImage(getContext(), UiUtils.ic_pause_vector, widget_toggle);
        musicControlBar.updateProgressBar();
        current_song_playing_time.updateTimerTextView();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void stoped(MusicStop musicStop) {
        new ImageLoader(getContext()).loadImage(getContext(), UiUtils.ic_play_vector, mToggelButton);
    }

    @Override
    public void onResume() {
        super.onResume();
        safeRegister();
        current_song_playing_time.updateTimerTextView();
        musicControlBar.updateProgressBar();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregister();
        current_song_playing_time.safeUnSubscription();
        musicControlBar.safeUnSubscription();
    }

}
