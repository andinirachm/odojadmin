package id.odojadmin.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.odojadmin.R;
import id.odojadmin.widget.TAGBookEditText;

public class AdminSettingActivity extends BaseActivity {

    @BindView(R.id.edit_text_no_admin)
    TAGBookEditText editTextNoAdmin;
    @BindView(R.id.edit_text_origin_group)
    TAGBookEditText editTextOriginGroup;
    @BindView(R.id.edit_text_name)
    TAGBookEditText editTextName;
    @BindView(R.id.edit_text_password)
    TAGBookEditText editTextPassword;
    @BindView(R.id.btn_1)
    Button btn1;
    @BindView(R.id.btn_2)
    Button btn2;

    private int groupSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_setting);
        ButterKnife.bind(this);
        setTitle("Pengaturan Admin");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setTotalGroup(int totalGroup) {
        groupSelected = totalGroup;
        if (totalGroup == 1) {
            btn1.setTextColor(getResources().getColor(android.R.color.white));
            btn1.setSelected(true);
            btn2.setTextColor(getResources().getColor(R.color.colorPrimary));
            btn2.setSelected(false);
        } else {
            btn1.setTextColor(getResources().getColor(R.color.colorPrimary));
            btn1.setSelected(false);
            btn2.setTextColor(getResources().getColor(android.R.color.white));
            btn2.setSelected(true);
        }
    }

    @OnClick({R.id.btn_1, R.id.btn_2, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                break;
            case R.id.btn_2:
                break;
            case R.id.btn_save:
                break;
        }
    }
}
