package id.odojadmin.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class TAGHeavyEditText extends AppCompatEditText {
    public TAGHeavyEditText(Context context) {
        super(context);
        init(context);
    }

    public TAGHeavyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "CerebriSans-Heavy.ttf"));
    }
}
