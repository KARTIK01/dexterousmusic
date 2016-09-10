package music.dexterous.com.dexterousmusic.fragment.home;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.fragment.BaseFragment;

/**
 * Created by mudit-pc on 9/11/2016.
 */

public class SettingFragment extends BaseFragment {

    EditText et_audio_length;
    ToggleButton tb_inset_headset,tb_shuffle_mode,tb_music_notification;
    TextView tv_about_us;
    Spinner sp_music_repeat_mode_tye;

    public static SettingFragment newInstance()
    {
        SettingFragment fragment = new SettingFragment();
        Bundle           info     = new Bundle();
        fragment.setArguments(info);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpComponents(view);
        setUpDropDown(view);
    }
    private void setUpComponents(View view)
    {
        et_audio_length = (EditText) view.findViewById(R.id.et_audio_length);
        tb_inset_headset=(ToggleButton) view.findViewById(R.id.tb_inset_headset);
        tb_music_notification=(ToggleButton) view.findViewById(R.id.tb_notification);
        tb_shuffle_mode=(ToggleButton) view.findViewById(R.id.tb_shuffle_mode);
        tv_about_us=(TextView)view.findViewById(R.id.tv_about_us);
        sp_music_repeat_mode_tye=(Spinner) view.findViewById(R.id.sp_mode_type);
    }
    private void setUpDropDown(View view)
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.spinner_itmes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_music_repeat_mode_tye.setAdapter(adapter);
    }
}
