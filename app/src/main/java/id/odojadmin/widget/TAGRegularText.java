package id.odojadmin.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class TAGRegularText extends AppCompatTextView {
    public TAGRegularText(Context context) {
        super(context);
        init(context);
    }

    public TAGRegularText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "CerebriSans-Regular.ttf"));
    }
}
