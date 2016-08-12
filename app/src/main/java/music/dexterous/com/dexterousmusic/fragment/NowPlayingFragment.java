package music.dexterous.com.dexterousmusic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cleveroad.audiovisualization.AudioVisualization;
import com.cleveroad.audiovisualization.DbmHandler;
import com.cleveroad.audiovisualization.SpeechRecognizerDbmHandler;
import com.cleveroad.audiovisualization.VisualizerDbmHandler;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.activity.BaseActivity;

public class NowPlayingFragment extends BaseFragment {

    private AudioVisualization audioVisualization;

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
        audioVisualization = (AudioVisualization) view.findViewById(R.id.visualizer_view);


        audioVisualization.linkTo(DbmHandler.Factory.newVisualizerHandler(getContext(), 0));

        // set speech recognizer handler
//        SpeechRecognizerDbmHandler speechRecHandler = DbmHandler.Factory.newSpeechRecognizerHandler(getActivity());
//        speechRecHandler.innerRecognitionListener(...);
//        audioVisualization.linkTo(speechRecHandler);
//
//        // set audio visualization handler. This will REPLACE previously set speech recognizer handler
//        VisualizerDbmHandler vizualizerHandler = DbmHandler.Factory.newVisualizerHandler(getContext(), 0);
//        audioVisualization.linkTo(vizualizerHandler);

    }

    @Override
    public void onResume() {
        super.onResume();
        audioVisualization.onResume();
    }

    @Override
    public void onPause() {
        audioVisualization.onPause();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        audioVisualization.release();
        super.onDestroyView();
    }
}
