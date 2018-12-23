package id.odojadmin.view.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.commons.lang3.StringEscapeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.odojadmin.R;
import id.odojadmin.helper.PreferenceHelper;
import id.odojadmin.helper.Symbol;
import id.odojadmin.model.Admin;
import id.odojadmin.widget.TAGBookEditText;
import id.odojadmin.widget.TAGBookText;
import id.odojadmin.widget.TAGMediumText;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.edit_text_name)
    TAGBookEditText editTextName;
    @BindView(R.id.edit_text_origin_group)
    TAGBookEditText editTextOriginGroup;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.btn_1)
    Button btn1;
    @BindView(R.id.btn_2)
    Button btn2;
    @BindView(R.id.edit_text_no_admin)
    TAGBookEditText editTextNoAdmin;
    @BindView(R.id.text_view_total_label)
    TAGBookText textViewTotalLabel;
    @BindView(R.id.edit_text_email)
    TAGBookEditText editTextEmail;
    @BindView(R.id.edit_text_password)
    TAGBookEditText editTextPassword;

    private int groupSelected = 0;
    private String id;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("admin");

        setListenerDrawableRight();
        setTotalGroup(1);
    }

    @OnClick(R.id.btn_register)
    public void onViewClicked() {
        if (editTextName.getText().toString().isEmpty() || editTextNoAdmin.getText().toString().isEmpty() || editTextOriginGroup.getText().toString().isEmpty()) {
            Toast.makeText(this, "Silahkan lengkapi form di atas.", Toast.LENGTH_SHORT).show();
        } else {
            if (!editTextName.getText().toString().isEmpty()
                    && !editTextNoAdmin.getText().toString().isEmpty()
                    && !editTextOriginGroup.getText().toString().isEmpty()
                    && !editTextEmail.getText().toString().trim().isEmpty()
                    && !editTextPassword.getText().toString().trim().isEmpty()) {
                id = editTextEmail.getText().toString().replace("@", "");
                id = id.replace(".", "");
                Admin admin = new Admin(editTextNoAdmin.getText().toString().trim(), editTextName.getText().toString().trim(), String.valueOf(groupSelected), editTextOriginGroup.getText().toString().trim(),
                        editTextEmail.getText().toString().trim(), editTextPassword.getText().toString().trim());
                mFirebaseDatabase.child(id).setValue(admin).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            PreferenceHelper.getInstance().saveSession(PreferenceHelper.KEY_IS_AUTHENTICATED, true);
                            PreferenceHelper.getInstance().saveSession(PreferenceHelper.KEY_USER_ID, id);
                            Toast.makeText(RegisterActivity.this, "Alhamdulillah Anda berhasil mendaftar!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Mohon maaf gagal mendaftar", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
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
            sample = Symbol.checkList + "22-Dini-G-137-08/12/2018-05.36\n" +
                    Symbol.checkList + "22-Dini-Ad-137-08/12/2018-" + Symbol.crown + "24-" + Symbol.female + "30-" + Symbol.clock + "21.00";
        } else {
            sample = Symbol.checkList + "22-Dini-G-137-08/12/2018-05.36\n" +
                    Symbol.checkList + "22-Dini-Ad-137-08/12/2018-" + Symbol.crown + "29-" + Symbol.female + "30-" + Symbol.clock + "21.00\n" +
                    Symbol.checkList + "22-Dini-Ad-325-08/12/2018-" + Symbol.crown + "24-" + Symbol.female + "27-" + Symbol.clock + "21.00";

        }
        textSample.setText(StringEscapeUtils.unescapeJava(sample));
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void setListenerDrawableRight() {

        editTextNoAdmin.setDrawableClickListener(new TAGBookEditText.DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        showDialogInfo("Nomor Urut Admin");
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
                        showDialogInfo("Nama Admin");
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
                        showDialogInfo("Grup Asal Admin");
                        break;
                    default:
                        break;
                }
            }
        });

        textViewTotalLabel.setDrawableClickListener(new TAGBookEditText.DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        showDialogInfo("Total Grup");
                        break;
                    default:
                        break;
                }
            }
        });
    }


    @OnClick({R.id.btn_1, R.id.btn_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                setTotalGroup(1);
                break;
            case R.id.btn_2:
                setTotalGroup(2);
                break;
        }
    }
}
