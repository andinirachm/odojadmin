package id.odojadmin.view.activity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.odojadmin.R;
import id.odojadmin.controller.GroupController;
import id.odojadmin.model.Group;
import id.odojadmin.view.adapter.AdminAdapter;
import id.odojadmin.widget.TAGBookEditText;
import id.odojadmin.widget.TAGBookText;
import id.odojadmin.widget.TAGHeavyText;

public class GroupSettingActivity extends AppCompatActivity {

    @BindView(R.id.text_view_name)
    TAGHeavyText textViewName;
    @BindView(R.id.text_view_total_member)
    TAGBookText textViewTotalMember;
    @BindView(R.id.recycler_view_admin)
    RecyclerView recyclerViewAdmin;
    @BindView(R.id.edit_text_batas_lapor)
    TAGBookEditText editTextBatasLapor;

    private Group group;
    private GroupController groupController;
    private int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_setting);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pengaturan Grup");

        groupController = new GroupController();

        Intent intent = this.getIntent();
        group = (Group) intent.getSerializableExtra("group");

        textViewName.setText("Grup " + group.getId());
        textViewTotalMember.setText(group.getTotalMember() + " Member");
        editTextBatasLapor.setText(group.getJamKholas());
        editTextBatasLapor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextBatasLapor.getText().toString().trim().isEmpty()) {
                    String s[] = editTextBatasLapor.getText().toString().trim().split(":");
                    hour = Integer.parseInt(s[0]);
                    minute = Integer.parseInt(s[1]);
                } else {
                    hour = 0;
                    minute = 0;
                }
                TimePickerDialog timePickerDialog = new TimePickerDialog(GroupSettingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (String.valueOf(minutes).length() == 1) {
                            editTextBatasLapor.setText(hourOfDay + ":0" + minutes);
                        } else {
                            editTextBatasLapor.setText(hourOfDay + ":" + minutes);
                        }

                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });

        setRecyclerViewAdmin(this, userList(group.getAdminId()));
    }

    private List<String> userList(String adminName) {
        int count = 1;
        List<String> userList = new ArrayList<>();
        String[] s;
        for (int i = 0; i < adminName.length(); i++)
            if (adminName.charAt(i) == ',')
                count++;

        for (int i = 0; i < count; i++) {
            s = adminName.split(",");
            if (s[i].contains(" "))
                s[i] = s[i].replace(" ", "");

            userList.add(s[i]);
        }

        return userList;
    }

    private void setRecyclerViewAdmin(Context context, List<String> userList) {
        AdminAdapter adapter = new AdminAdapter(this, userList);
        recyclerViewAdmin.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL, false));
        recyclerViewAdmin.setAdapter(adapter);
    }

    private void addAdmin(String adminId) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("adminId", group.getAdminId() + ", " + adminId);
        groupController.addAdminToGroup(Integer.parseInt(group.getId()), adminId, hashMap);
    }

    @OnClick(R.id.btn_save)
    public void onViewClicked() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("jamKholas", editTextBatasLapor.getText().toString().trim());
        groupController.update(Integer.parseInt(group.getId()), hashMap);
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
}
