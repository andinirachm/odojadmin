package id.odojadmin.view.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.commons.lang3.StringEscapeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.odojadmin.R;
import id.odojadmin.controller.UserController;
import id.odojadmin.controller.GroupController;
import id.odojadmin.event.LoginEvent;
import id.odojadmin.event.SubscriberPriority;
import id.odojadmin.helper.PreferenceHelper;
import id.odojadmin.helper.Symbol;
import id.odojadmin.model.Admin;
import id.odojadmin.widget.TAGBookEditText;
import id.odojadmin.widget.TAGMediumText;

public class SignInActivity extends BaseActivity {

    @BindView(R.id.edit_text_email)
    TAGBookEditText editTextEmail;
    @BindView(R.id.btn_sign_in)
    Button btnSignIn;
    @BindView(R.id.edit_text_password)
    TAGBookEditText editTextPassword;

    private Admin adminLoggedIn;
    private String id;
    private UserController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        eventBus.register(this, SubscriberPriority.HIGH);
        controller = new UserController();
        setListenerDrawableRight();

        group();
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
                    Symbol.checkList + "22-Dini-Ad-137-08/12/2018-" + Symbol.crown + "24-" + Symbol.women + "30-" + Symbol.clock + "21.00";
        } else {
            sample = Symbol.checkList + "22-Dini-G-137-08/12/2018-05.36\n" +
                    Symbol.checkList + "22-Dini-Ad-137-08/12/2018-" + Symbol.crown + "29-" + Symbol.women + "30-" + Symbol.clock + "21.00\n" +
                    Symbol.checkList + "22-Dini-Ad-325-08/12/2018-" + Symbol.crown + "24-" + Symbol.women + "27-" + Symbol.clock + "21.00";

        }
        textSample.setText(StringEscapeUtils.unescapeJava(sample));
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void setListenerDrawableRight() {
        editTextEmail.setDrawableClickListener(new TAGBookEditText.DrawableClickListener() {
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

    }

    @OnClick(R.id.btn_sign_in)
    public void onBtnSignInClicked() {
        if (!editTextEmail.getText().toString().isEmpty()
                && !editTextPassword.getText().toString().trim().isEmpty()) {
            controller.login(editTextEmail.getText().toString().trim(), editTextPassword.getText().toString().trim());
        } else {
            Toast.makeText(this, "Silahkan lengkapi form di atas.", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.text_view_register)
    public void onTextViewRegisterClicked() {
        startActivity(new Intent(SignInActivity.this, RegisterActivity.class));
    }

    public void onEventMainThread(LoginEvent event) {
        if (event.isSuccess()) {
            adminLoggedIn = event.getAdmin();
            if (adminLoggedIn != null) {
                id = editTextEmail.getText().toString().replace("@", "");
                id = id.replace(".", "");
                PreferenceHelper.getInstance().saveSession(PreferenceHelper.KEY_IS_AUTHENTICATED, true);
                PreferenceHelper.getInstance().saveSession(PreferenceHelper.KEY_USER_ID, id);
                PreferenceHelper.getInstance().saveSession(PreferenceHelper.KEY_NAME, adminLoggedIn.getName());
                PreferenceHelper.getInstance().saveSession(PreferenceHelper.KEY_EMAIL, editTextEmail.getText().toString().trim());
                //Toast.makeText(SignInActivity.this, "Berhasil Login " + adminLoggedIn.getName(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("name", adminLoggedIn.getName());
                i.putExtra("email", adminLoggedIn.getEmail());
                startActivity(i);
            } else {
                Toast.makeText(SignInActivity.this, "Gagal Login", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SignInActivity.this, "Gagal Login", Toast.LENGTH_SHORT).show();
        }
    }

    private void group() {
//        Group group = new Group("325", 25, 0, "andinirachmahgmailcom");
        GroupController controller = new GroupController();
        /*Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("totalMember", 30);*/
        //controller.delete(325);
    }
}
