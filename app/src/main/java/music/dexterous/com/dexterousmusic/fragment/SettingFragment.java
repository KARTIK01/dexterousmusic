package music.dexterous.com.dexterousmusic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.activity.DeepLinkingActivity;
import music.dexterous.com.dexterousmusic.customeviews.widget.FontTextView;
import music.dexterous.com.dexterousmusic.utils.MailUtill;
import music.dexterous.com.dexterousmusic.utils.preference.UsersAppPreference;

/**
 * Created by mudit-pc on 9/11/2016.
 */

public class SettingFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {

    EditText et_audio_length;
    SwitchCompat tb_inset_headset, tb_shuffle_mode, tb_music_notification;
    FontTextView tv_about_us, tv_rate_us, tv_send_feedback;
    Spinner sp_music_repeat_mode_tye;
    Button btn_reset;
    static final String FRAGMENT_TAG = "SettingFragment";

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle info = new Bundle();
        fragment.setArguments(info);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpComponents(view);
        setUpDropDown(view);
        setUpListener();
        updateView();
    }

    private void setUpComponents(View view) {
        et_audio_length = (EditText) view.findViewById(R.id.et_audio_length);
        tb_inset_headset = (SwitchCompat) view.findViewById(R.id.tb_inset_headset);
        tb_music_notification = (SwitchCompat) view.findViewById(R.id.tb_notification);
        tb_shuffle_mode = (SwitchCompat) view.findViewById(R.id.tb_shuffle_mode);
        tv_about_us = (FontTextView) view.findViewById(R.id.tv_about_us);
        tv_rate_us = (FontTextView) view.findViewById(R.id.tv_rate_us);
        tv_send_feedback = (FontTextView) view.findViewById(R.id.tv_send_feedback);
        sp_music_repeat_mode_tye = (Spinner) view.findViewById(R.id.sp_mode_type);
        btn_reset = (Button) view.findViewById(R.id.btn_reset);
    }

    private void setUpListener() {

        tb_shuffle_mode.setOnCheckedChangeListener(this);

        tb_inset_headset.setOnCheckedChangeListener((buttonView, isChecked) -> {
            UsersAppPreference.setMusicPlayONInsetHeadSet(isChecked);
        });

        tb_music_notification.setOnCheckedChangeListener((buttonView, isChecked) -> {
            UsersAppPreference.setMusicNotificationToDisplat(isChecked);
        });

        tv_about_us.setOnClickListener(v -> {
            //TODO
        });

        tv_send_feedback.setOnClickListener(v -> {
            MailUtill.sendFeedbackMail(getActivity());
        });

        tv_rate_us.setOnClickListener(v -> {
            startActivity(DeepLinkingActivity.getIntent(getContext(), DeepLinkingActivity.ACTION_OPEN_PLAY_STORE));
        });

        btn_reset.setOnClickListener(v -> {
            UsersAppPreference.DefaultPreference.restAll();
            updateView();
        });
    }

    private void updateView() {
        tb_inset_headset.setChecked(UsersAppPreference.getMusicPlayONInsetHeadSet());
        tb_music_notification.setChecked(UsersAppPreference.isMusicNotificationToDisplay());
    }

    private void setUpDropDown(View view) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.spinner_itmes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_music_repeat_mode_tye.setAdapter(adapter);
    }

    private void hideSmallClipDuration() {
        String string = et_audio_length.getText().toString();
        Long milli = Long.valueOf(string).longValue();
        UsersAppPreference.setHideSmallClipsDurations(milli);
    }

    private void musicShuffle() {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.tb_shuffle_mode:
                musicShuffle();
                break;
        }
    }
}
