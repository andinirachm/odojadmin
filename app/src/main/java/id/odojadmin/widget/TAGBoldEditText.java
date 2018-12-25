package id.odojadmin.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

public class TAGBoldEditText extends AppCompatEditText {
    public TAGBoldEditText(Context context) {
        super(context);
        init(context);
    }

    public TAGBoldEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "CerebriSans-Bold.ttf"));
    }
}
