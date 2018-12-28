package id.odojadmin.view.activity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.odojadmin.R;
import id.odojadmin.controller.GroupController;
import id.odojadmin.controller.MemberController;
import id.odojadmin.event.GetDetailGroupEvent;
import id.odojadmin.event.GetMemberByGroupIdEvent;
import id.odojadmin.event.MemberClickEvent;
import id.odojadmin.event.SubscriberPriority;
import id.odojadmin.model.Group;
import id.odojadmin.model.Member;
import id.odojadmin.view.adapter.AdminAdapter;
import id.odojadmin.view.adapter.MemberAdapter;
import id.odojadmin.widget.TAGBoldText;
import id.odojadmin.widget.TAGBookText;

public class DetailGroupActivity extends BaseActivity {
    @BindView(R.id.text_view_name)
    TAGBoldText textViewName;
    @BindView(R.id.text_view_total_member)
    TAGBookText textViewTotalMember;
    @BindView(R.id.text_view_notif)
    TAGBookText textViewNotif;
    @BindView(R.id.switch_notif)
    SwitchCompat switchNotif;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.recycler_view_member)
    RecyclerView recyclerViewMember;
    RecyclerView recyclerViewAdmin;
    private List<Member> memberList = new ArrayList<>();
    private MemberController controller;
    private GroupController groupController;
    private int groupId;
    private MemberAdapter adapter;
    private View viewDetail;
    private String juzSelected;

    private Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_group);
        ButterKnife.bind(this);
        eventBus.register(this, SubscriberPriority.HIGH);
        viewDetail = getLayoutInflater().inflate(R.layout.dialog_detail_member, null);

        setRecyclerViewMember();
        groupId = getIntent().getIntExtra("groupId", 0);
        controller = new MemberController();
        groupController = new GroupController();
        groupController.getGroupDetail(String.valueOf(groupId));
        progressBar.setVisibility(View.VISIBLE);
        controller.getAllMemberByGroupId(groupId);

    }

    private void setRecyclerViewMember() {
        adapter = new MemberAdapter(this, memberList);
        recyclerViewMember.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        recyclerViewMember.setAdapter(adapter);
    }

    public void onEventMainThread(GetMemberByGroupIdEvent event) {
        progressBar.setVisibility(View.GONE);
        if (event.isSuccess()) {
            memberList.clear();
            memberList.addAll(event.getMemberList());

            updateTotalMember(memberList.size());
            adapter.notifyDataSetChanged();
        } else {

        }
    }

    private void updateTotalMember(int total) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("totalMember", total);
        groupController.updateTotalMember(groupId, hashMap);
    }


    public void onEventMainThread(MemberClickEvent event) {
        showDialogDetail(event.getMember());
    }

    private void showDialogDetail(final Member member) {
        final AlertDialog alertDialog;
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_detail_member, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        TextView textViewInitial = dialogView.findViewById(R.id.text_view_initial);
        TextView textViewName = dialogView.findViewById(R.id.text_view_name);
        TextView textViewStatus = dialogView.findViewById(R.id.text_view_status);
        TextView textViewJuz = dialogView.findViewById(R.id.text_view_juz);
        Button btnEdit = dialogView.findViewById(R.id.btn_edit);
        Button btnKontak = dialogView.findViewById(R.id.btn_kontak);
        Button btnHapus = dialogView.findViewById(R.id.btn_hapus);
        String initial = member.getName().substring(0, 2);
        textViewInitial.setText(initial.toUpperCase());
        textViewJuz.setText("Sedang tilawah juz " + member.getJuz());
        textViewName.setText(member.getName());
        if (!member.isKarantina()) {
            textViewStatus.setText("Member Aktif");
        } else {
            textViewStatus.setText("Member Karantina");
            textViewStatus.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                showDialogAdd(true, member);
            }
        });

        btnKontak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
                String phone = member.getPhone().replace("08", "+628");
                String url = "https://api.whatsapp.com/send?phone=" + phone;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                controller.delete(member.getName());
                Toast.makeText(DetailGroupActivity.this, "Hapus", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.show();
    }

    private void showDialogAdd(final boolean isEdit, Member member) {
        String juz, ab;
        final AlertDialog alertDialog;
        if (!isEdit)
            juzSelected = "a";
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_member, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        final TextView textViewInitial = dialogView.findViewById(R.id.text_view_initial);
        final EditText editTextName = dialogView.findViewById(R.id.edit_text_name);
        final EditText editTextNo = dialogView.findViewById(R.id.edit_text_no);
        final EditText editTextPhone = dialogView.findViewById(R.id.edit_text_phone);
        final EditText editTextJuz = dialogView.findViewById(R.id.edit_text_juz);
        final Button btnAdd = dialogView.findViewById(R.id.btn_add);
        final Button btnA = dialogView.findViewById(R.id.btn_a);
        final Button btnB = dialogView.findViewById(R.id.btn_b);

        if (isEdit) {
            String[] part = member.getJuz().split("-");
            juz = part[0];
            ab = part[1];
            juzSelected = ab;

            editTextJuz.setText(juz);
            if (ab.equals("a")) {
                btnA.setSelected(true);
                btnA.setTextColor(getResources().getColor(android.R.color.white));
                btnB.setSelected(false);
                btnB.setTextColor(getResources().getColor(R.color.colorPrimary));
            } else {
                btnA.setSelected(false);
                btnA.setTextColor(getResources().getColor(R.color.colorPrimary));
                btnB.setSelected(true);
                btnB.setTextColor(getResources().getColor(android.R.color.white));
            }

            editTextNo.setText("" + member.getId());
            editTextName.setText(member.getName());
            editTextPhone.setText(member.getPhone());
            if (member.getName().length() >= 0) {
                String initial = member.getName().substring(0, 2);
                textViewInitial.setText(initial.toUpperCase());
            } else if (member.getName().length() == 1) {
                String initial = member.getName().substring(0, 1);
                textViewInitial.setText(initial.toUpperCase());
            } else {
                textViewInitial.setText("");
            }

            btnAdd.setText("Simpan");
        } else {
            btnA.setSelected(true);
            btnA.setTextColor(getResources().getColor(android.R.color.white));
        }


        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count >= 2) {
                    String initial = editTextName.getText().toString().trim().substring(0, 2);
                    textViewInitial.setText(initial.toUpperCase());
                } else if (count < 1) {
                    textViewInitial.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Member member = new Member(Integer.parseInt(editTextNo.getText().toString().trim()), editTextName.getText().toString().trim(), false, editTextJuz.getText().toString().trim() + "-" + juzSelected, editTextPhone.getText().toString().trim(), false, groupId);
                if (!isEdit) {
                    controller.addMember(member);
                } else {
                    Map<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", Integer.parseInt(editTextNo.getText().toString().trim()));
                    hashMap.put("name", editTextName.getText().toString().trim());
                    hashMap.put("juz", editTextJuz.getText().toString().trim() + "-" + juzSelected);
                    hashMap.put("phone", editTextPhone.getText().toString().trim());
                    controller.update(editTextName.getText().toString().trim(), hashMap);
                }
            }
        });

        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnA.setSelected(true);
                btnA.setTextColor(getResources().getColor(android.R.color.white));
                btnB.setSelected(false);
                btnB.setTextColor(getResources().getColor(R.color.colorPrimary));
                juzSelected = "a";
            }
        });

        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnA.setSelected(false);
                btnA.setTextColor(getResources().getColor(R.color.colorPrimary));
                btnB.setSelected(true);
                btnB.setTextColor(getResources().getColor(android.R.color.white));
                juzSelected = "b";
            }
        });


        alertDialog.show();
    }

    private void setRecyclerViewAdmin(Context context, List<String> userList) {
        AdminAdapter adapter = new AdminAdapter(this, userList);
        recyclerViewAdmin.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL, false));
        recyclerViewAdmin.setAdapter(adapter);
    }

    private void showDialogDetailGroup(final Group group) {
        final AlertDialog alertDialog;
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_group_setting, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        TextView textViewName = dialogView.findViewById(R.id.text_view_name);
        recyclerViewAdmin = dialogView.findViewById(R.id.recycler_view_admin);
        TextView textViewTotalMember = dialogView.findViewById(R.id.text_view_total_member);
        final EditText editTextBatasLapor = dialogView.findViewById(R.id.edit_text_batas_lapor);

        textViewName.setText("Grup " + group.getId());
        textViewTotalMember.setText(group.getTotalMember() + " Member");
        editTextBatasLapor.setText(group.getJamKholas());
        Button btnSave = dialogView.findViewById(R.id.btn_save);

        editTextBatasLapor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(DetailGroupActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (String.valueOf(minutes).length() == 1) {
                            editTextBatasLapor.setText(hourOfDay + ":0" + minutes);
                        } else {
                            editTextBatasLapor.setText(hourOfDay + ":" + minutes);
                        }

                    }
                }, 0, 0, true);
                timePickerDialog.show();
            }
        });

        setRecyclerViewAdmin(viewDetail.getContext(), userList(group.getAdminId()));
        alertDialog.show();
    }

    private List<String> userList(String adminName) {
        System.out.println("HOLLA : " + adminName);
        int count = 1;
        List<String> userList = new ArrayList<>();
        String[] s;
        for (int i = 0; i < adminName.length(); i++)
            if (adminName.charAt(i) == ',')
                count++;

        System.out.println("HOLLA : " + count);
        for (int i = 0; i < count; i++) {
            s = adminName.split(",");
            if (s[i].contains(" "))
                s[i] = s[i].replace(" ", "");

            System.out.println("HOLLA : " + s[i]);
            userList.add(s[i]);
        }

        return userList;
    }

    public void onEventMainThread(GetDetailGroupEvent event) {
        if (event.isSuccess()) {
            group = event.getGroupDetail();
            textViewName.setText("Grup " + event.getGroupDetail().getId());
            textViewTotalMember.setText(event.getGroupDetail().getTotalMember() + " Member");
        }
    }

    @OnClick(R.id.btn_setting_group)
    public void onBtnSettingGroupClicked() {
        //addAdmin("dewigmailcom");
        if (group != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("group", group);
            Intent intent = new Intent(this, GroupSettingActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private void addAdmin(String adminId) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("adminId", group.getAdminId() + ", " + adminId);
        groupController.addAdminToGroup(groupId, adminId, hashMap);
    }

    @OnClick(R.id.fab)
    public void onFabClicked() {
        showDialogAdd(false, null);
    }

    @OnClick(R.id.btn_back)
    public void onBtnBackClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }
}
