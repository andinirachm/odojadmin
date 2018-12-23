package id.odojadmin.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class TAGLightText extends AppCompatTextView {
    public TAGLightText(Context context) {
        super(context);
        init(context);
    }

    public TAGLightText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "CerebriSans-Light.ttf"));
    }
}
