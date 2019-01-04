package id.odojadmin.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.odojadmin.ApplicationMain;
import id.odojadmin.R;
import id.odojadmin.controller.UserController;
import id.odojadmin.event.GetDetailUserEvent;
import id.odojadmin.model.Admin;
import id.odojadmin.view.activity.BaseActivity;

public class TestActivity extends BaseActivity {

    @BindView(R.id.text_view_name)
    TextView textViewName;
    @BindView(R.id.text_view)
    EditText textView;

    private String email;
    private String password;
    private Admin admin = null;

    private UserController controller;
    private Query query;
    int keyDel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        // eventBus.register(this, SubscriberPriority.HIGH);

//        Admin admin = new Admin("23","Dina","1","128", "andini_rach@gmail.com","1234","081291334711");
//        admin.createAdmin(admin);

//        Admin admin1 = new Admin();
//        Map<String, Object> map = new HashMap<>();
//        map.put("phone", "123123");
//        admin.deleteAdmin("andinarachgmailcom");
//
        email = "andinirachmah@gmail.com";
        password = "qwerty";
//        query = ApplicationMain.getInstance().getFirebaseDatabaseAdmin();
//        query.addListenerForSingleValueEvent(eventGetDetailUser);
        getAdminDetail(email); //listenernya ga terpisah

        //controller.getAdminDetail("andinirachmah@gmail.com");

        textView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                textView.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = textView.getText().length();
                    if (len == 3) {
                        textView.setText(textView.getText() + "-");
                        textView.setSelection(textView.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void onEventMainThread(GetDetailUserEvent event) {
        if (event.isSuccess()) {
            textViewName.setText(event.getAdmin().getName());
        }
    }

    public void onBtnClicked(View view) {
        System.out.println("LOLOP : KETEMU");
        ApplicationMain.getInstance().getFirebaseDatabaseAdmin().removeEventListener(eventGetDetailUser);
    }


    public void getAdminDetail(final String email) {
        ApplicationMain.getInstance().getFirebaseDatabaseAdmin().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean isKetemu = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    admin = snapshot.getValue(Admin.class);
                    if (admin.getEmail().equals(email)) {
                        isKetemu = true;
                        break;
                    }
                }

                if (isKetemu) {
                    System.out.println("LOLOP : KETEMU");
                    textViewName.setText("Hello : " + admin.getName());
                } else {
                    textViewName.setText("Ga ketemu ");
                    System.out.println("LOLOP : GA KETEMU");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // eventBus.post(new GetDetailUserEvent(false, "Failure " + databaseError.getMessage(), null));
            }
        });
    }

    public ValueEventListener eventGetDetailUser = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            boolean isKetemu = false;
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                admin = snapshot.getValue(Admin.class);
                if (admin.getEmail().equals(email) && admin.getPassword().equals(password)) {
                    isKetemu = true;
                    break;
                }
            }

            if (isKetemu) {
                System.out.println("LOLOP : KETEMU");
                textViewName.setText("Hello : " + admin.getName());
            } else {
                textViewName.setText("Ga ketemu ");
                System.out.println("LOLOP : GA KETEMU");
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
