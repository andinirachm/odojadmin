package id.odojadmin.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class TAGSemiBoldButton extends Button {
    public TAGSemiBoldButton(Context context) {
        super(context);
        init(context);
    }

    public TAGSemiBoldButton(Context context, AttributeSet attrs) {
        super(context, attrs, android.R.attr.borderlessButtonStyle);
        init(context);
    }

    private void init(Context context) {
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "CerebriSans-SemiBold.ttf"));
    }
}
