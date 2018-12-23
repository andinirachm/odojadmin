package id.odojadmin.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;

public class TAGMediumCheckbox extends AppCompatCheckBox {
    public TAGMediumCheckbox(Context context) {
        super(context);
        init(context);
    }

    public TAGMediumCheckbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "CerebriSans-Medium.ttf"));
    }
}
