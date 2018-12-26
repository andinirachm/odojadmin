package id.odojadmin.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.odojadmin.R;
import id.odojadmin.controller.MemberController;
import id.odojadmin.event.GetMemberByGroupIdEvent;
import id.odojadmin.event.MemberClickEvent;
import id.odojadmin.event.SubscriberPriority;
import id.odojadmin.helper.Symbol;
import id.odojadmin.model.Member;
import id.odojadmin.view.adapter.MemberAdapter;
import id.odojadmin.widget.TAGBoldText;
import id.odojadmin.widget.TAGBookText;
import id.odojadmin.widget.TAGMediumText;

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
    private List<Member> memberList = new ArrayList<>();
    private MemberController controller;
    private int groupId;
    private MemberAdapter adapter;
    private View viewDetail;
    private String juzSelected;

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
            adapter.notifyDataSetChanged();
        } else {

        }
    }

    public void onEventMainThread(MemberClickEvent event) {
        showDialogDetail(event.getMember());
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        showDialogAdd();
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
                Toast.makeText(DetailGroupActivity.this, "Edit", Toast.LENGTH_SHORT).show();
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

    private void showDialogAdd() {
        final AlertDialog alertDialog;
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
        btnA.setSelected(true);
        btnA.setTextColor(getResources().getColor(android.R.color.white));
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
                Member member = new Member(Integer.parseInt(editTextNo.getText().toString().trim()), editTextName.getText().toString().trim(), false, editTextJuz.getText().toString().trim() + "" + juzSelected, editTextPhone.getText().toString().trim(), false, groupId);
                controller.addMember(member);
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
}