package music.dexterous.com.dexterousmusic.customeviews.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.AttributeSet;

import java.util.concurrent.TimeUnit;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.musicutils.HumanReadableTime;
import music.dexterous.com.dexterousmusic.service.DexterousPlayMusicService;
import music.dexterous.com.dexterousmusic.service.musiccontrol.AbstractMusicControlService;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by kartik on 31/08/16.
 */
public class TimerTextView extends FontTextView {

    /**
     * It can be in following stages STATIC FORWARD BACKWARD
     */
    private String       textType;
    private Subscription subscription;
    private MediaPlayer  mediaPlayer;

    public TimerTextView(Context context) {
        super(context);
        init(null);
    }

    public TimerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TimerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    protected void init(AttributeSet attributeSet) {
        super.init(attributeSet);
        mediaPlayer = AbstractMusicControlService.mDexterousMediaPlayer;
        if (attributeSet != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.TimerText);
            textType = typedArray.getString(R.styleable.TimerText_type);
            if (TextUtils.isEmpty(textType)) {
                textType = "static";
            }
        }
    }

    public String getTextType() {
        return textType;
    }

    public void setTextType(String textType) {
        this.textType = textType;
    }

    public void updateTimerTextView() {
        int START_DELAY  = 0;
        int INTERVEL_GAP = 1;

        safeUnSubscription();
        //TODO handle onError
        subscription = Observable.interval(START_DELAY, INTERVEL_GAP, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    update();
                });
    }

    private void update() {
        if (mediaPlayer == null)
            mediaPlayer = DexterousPlayMusicService.mDexterousMediaPlayer;
        if (mediaPlayer.isPlaying()) {
            long totalDuration   = mediaPlayer.getDuration();
            long currentDuration = mediaPlayer.getCurrentPosition();
            setText(HumanReadableTime.songDurationToDisplay(currentDuration));
        } else {
            safeUnSubscription();
        }
    }

    public void safeUnSubscription() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
