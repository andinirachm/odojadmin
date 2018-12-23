package id.odojadmin.view.activity;

import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.odojadmin.R;
import id.odojadmin.widget.TAGMediumText;

public class RekapanActivity extends BaseActivity {

    @BindView(R.id.text_view_greeting)
    TAGMediumText textViewGreeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekapan);
        ButterKnife.bind(this);


    }
}
