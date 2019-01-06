package id.odojadmin.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.odojadmin.ApplicationMain;
import id.odojadmin.R;
import id.odojadmin.helper.DateHelper;
import id.odojadmin.helper.RekapanHelper;
import id.odojadmin.model.Admin;
import id.odojadmin.model.Member;
import id.odojadmin.model.RekapHarian;
import id.odojadmin.view.activity.BaseActivity;

public class TestActivity extends BaseActivity {

    @BindView(R.id.text_view_name)
    TextView textViewName;
    @BindView(R.id.text_view)
    EditText textView;

    private String email;
    private String password;
    private Admin admin = null;
    private RekapHarian rekapHarian = null;

    private int group = 137;
    private List<Member> memberList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        email = "andinirachmah@gmail.com";
        password = "qwerty";
        //getAdminDetail(email);
        getMember();
    }

    public void onBtnClicked(View view) {
        System.out.println("LOLOP : KETEMU");
//        ApplicationMain.getInstance().getFirebaseDatabaseAdmin().removeEventListener(eventGetDetailUser);
    }

    private void getMember() {
        ApplicationMain.getInstance().getFirebaseDatabaseMember().addValueEventListener(eventListener);
    }

    ValueEventListener eventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                Member member = snapshot.getValue(Member.class);
                if (member.getGroupId() == group) {
                    String[] jz = member.getJuz().split("-");
                    String juz = jz[0];
                    String ab = jz[1];
                    System.out.println(member.getName()+" - "+member.getJuz());
                    member.setJuz(RekapanHelper.getNextJuz(Integer.parseInt(juz), ab));
                    System.out.println(member.getName()+" - "+member.getJuz());
                    memberList.add(member);
                }
            }

            RekapHarian rekapHarian = new RekapHarian(group + "-" + DateHelper.getSimpleDate(), group, DateHelper.getSimpleDate2(), 0, memberList.size(), memberList);
            rekapHarian.createRekapHarian(rekapHarian);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

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
}
