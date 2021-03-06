package id.odojadmin.view.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.odojadmin.R;
import id.odojadmin.controller.UserController;
import id.odojadmin.event.GetDetailUserEvent;
import id.odojadmin.event.SubscriberPriority;
import id.odojadmin.helper.PreferenceHelper;
import id.odojadmin.helper.Symbol;
import id.odojadmin.widget.TAGBookEditText;
import id.odojadmin.widget.TAGMediumText;

public class UserSettingActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.edit_text_no_admin)
    TAGBookEditText editTextNoAdmin;
    @BindView(R.id.edit_text_origin_group)
    TAGBookEditText editTextOriginGroup;
    @BindView(R.id.edit_text_name)
    TAGBookEditText editTextName;
    @BindView(R.id.edit_text_password)
    TAGBookEditText editTextPassword;
    @BindView(R.id.edit_text_phone)
    TAGBookEditText editTextPhone;
    @BindView(R.id.edit_text_email)
    TAGBookEditText editTextEmail;

    private UserController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_setting);
        ButterKnife.bind(this);
        eventBus.register(this, SubscriberPriority.HIGH);

        getSupportActionBar().setTitle("Pengaturan Pengguna");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        controller = new UserController();
        controller.getAdminDetail(PreferenceHelper.getInstance().getSessionString(PreferenceHelper.KEY_EMAIL));

        setListenerDrawableRight();
        editTextPassword.setOnClickListener(this);
    }

    public void onEventMainThread(GetDetailUserEvent event) {
        if (event.isSuccess()) {
            editTextNoAdmin.setText(event.getAdmin().getNoAdmin());
            editTextOriginGroup.setText(event.getAdmin().getOrigin());
            editTextName.setText(event.getAdmin().getName());
            editTextPhone.setText(event.getAdmin().getPhone());
            editTextEmail.setText(event.getAdmin().getEmail());
            editTextPassword.setText(event.getAdmin().getPassword());
        }
    }

    private void updateInfo() {
        if (editTextNoAdmin.getText().toString().trim().isEmpty() ||
                editTextNoAdmin.getText().toString().trim().isEmpty() ||
                editTextName.getText().toString().trim().isEmpty() ||
                editTextPhone.getText().toString().trim().isEmpty() ||
                editTextPassword.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Silahkan lengkapi form terlebih dahulu.", Toast.LENGTH_SHORT).show();
        } else {
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("noAdmin", editTextNoAdmin.getText().toString().trim());
            hashMap.put("origin", editTextOriginGroup.getText().toString().trim());
            hashMap.put("name", editTextName.getText().toString().trim());
            hashMap.put("phone", editTextPhone.getText().toString().trim());
            hashMap.put("password", editTextPassword.getText().toString().trim());
            controller.update(hashMap);
            Toast.makeText(this, "Berhasil menyimpan pengaturan.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setListenerDrawableRight() {
        editTextNoAdmin.setDrawableClickListener(new TAGBookEditText.DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        showDialogInfo("Nomor Urut Pengguna");
                        break;
                    default:
                        break;
                }
            }
        });

        editTextName.setDrawableClickListener(new TAGBookEditText.DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        showDialogInfo("Nama Pengguna");
                        break;
                    default:
                        break;
                }
            }
        });

        editTextOriginGroup.setDrawableClickListener(new TAGBookEditText.DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        showDialogInfo("Grup Asal Pengguna");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void showDialogInfo(String type) {
        String sample = "";
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_info, null);
        dialogBuilder.setView(dialogView);
        TAGMediumText textTitle = dialogView.findViewById(R.id.text_view_title);
        TAGMediumText textDesc = dialogView.findViewById(R.id.text_view_description);
        TAGMediumText textSample = dialogView.findViewById(R.id.text_view_sample);
        textTitle.setText("Info " + type);
        textDesc.setText("Isiliah kolom '" + type + "' sesuai dengan yang didaftarkan. " + type + " ini akan digunakan untuk men-generate secara otomatis laporan ke SG.");

        if (!type.equalsIgnoreCase("Total Grup")) {
            sample = Symbol.getCheckList() + "22-Dini-G-137-08/12/2018-05.36\n" +
                    Symbol.getCheckList() + "22-Dini-Ad-137-08/12/2018-" + Symbol.getCrown() + "24-" + Symbol.getWomen() + "30-" + Symbol.getClock() + "21.00";
        } else {
            sample = Symbol.getCheckList() + "22-Dini-G-137-08/12/2018-05.36\n" +
                    Symbol.getCheckList() + "22-Dini-Ad-137-08/12/2018-" + Symbol.getCrown() + "29-" + Symbol.getWomen() + "30-" + Symbol.getClock() + "21.00\n" +
                    Symbol.getCheckList() + "22-Dini-Ad-325-08/12/2018-" + Symbol.getCrown() + "24-" + Symbol.getWomen() + "27-" + Symbol.getClock() + "21.00";

        }
        textSample.setText(sample);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    @OnClick({R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                updateInfo();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this, "Email tidak dapat diganti.", Toast.LENGTH_SHORT).show();
    }
}
