package id.odojadmin.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import id.odojadmin.model.Member;
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
    private List<Member> memberList = new ArrayList<>();
    private MemberController controller;
    private int groupId;
    private MemberAdapter adapter;
    private BottomSheetDialog bottomSheetDetail;
    private View viewDetail;

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

    private void showDialogDetail(final Member member) {
        bottomSheetDetail = new BottomSheetDialog(this);
        bottomSheetDetail.setContentView(viewDetail);
        TextView textViewInitial = viewDetail.findViewById(R.id.text_view_initial);
        TextView textViewName = viewDetail.findViewById(R.id.text_view_name);
        TextView textViewStatus = viewDetail.findViewById(R.id.text_view_status);
        Button btnEdit = viewDetail.findViewById(R.id.btn_edit);
        Button btnKontak = viewDetail.findViewById(R.id.btn_kontak);
        Button btnHapus = viewDetail.findViewById(R.id.btn_hapus);
        String initial = member.getName().substring(0, 2);
        textViewInitial.setText(initial.toUpperCase());
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
                Toast.makeText(DetailGroupActivity.this, "Edit", Toast.LENGTH_SHORT).show();
            }
        });

        btnKontak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                controller.delete(member.getName());
                Toast.makeText(DetailGroupActivity.this, "Hapus", Toast.LENGTH_SHORT).show();
            }
        });

        bottomSheetDetail.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialog.dismiss();
                ((ViewGroup) viewDetail.getParent()).removeView(viewDetail);
            }
        });

        bottomSheetDetail.show();
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        Member member = new Member(6, "Assyfas", false, "10b", "081904940091", false, 137);
        controller.addMember(member);
    }
}
