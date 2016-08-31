package music.dexterous.com.dexterousmusic.customeviews.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import music.dexterous.com.dexterousmusic.R;
import music.dexterous.com.dexterousmusic.utils.Constants;
import music.dexterous.com.dexterousmusic.utils.ui.FontUtils;

/**
 * Created by Kartik on 20-11-2015 in NewZuppit.
 * <p>
 * ** Set fontname from xml for textview
 */
public class FontTextView extends TextView {

    public FontTextView(Context context) {
        super(context);
        init(null);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    protected void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FontText);
            String fontName = a.getString(R.styleable.FontText_fontName);
            a.recycle();
            if (fontName == null) {
                fontName = Constants.DEFAULT_ENGLISH_FONT;
            }

            //TODO language
//            if (fontName.contains("Calibri") && UserPreference.isHindiSelected()) {
//                fontName = Constants.DEFAULT_HINDI_FONT;
//            }

            /**
             * do not override font with default hindi font if language is hindi
             * there is issue with font in assets. Not only this one but with others too
             * be careful while changing the fonts
             */
            Typeface fontFace = FontUtils.getTypefaceFromName(getContext(), fontName);
            if (fontFace != null) {
                setTypeface(fontFace);
            }
        }
    }


}
