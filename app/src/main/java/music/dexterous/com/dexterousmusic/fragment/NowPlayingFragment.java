package music.dexterous.com.dexterousmusic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import music.dexterous.com.dexterousmusic.R;

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
