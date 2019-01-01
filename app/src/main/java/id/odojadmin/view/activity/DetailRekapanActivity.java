package id.odojadmin.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.timqi.sectorprogressview.ColorfulRingProgressView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.odojadmin.R;
import id.odojadmin.controller.FormatRekapanController;
import id.odojadmin.controller.GroupController;
import id.odojadmin.controller.MemberController;
import id.odojadmin.controller.RekapanHarianController;
import id.odojadmin.event.GetDetailGroupEvent;
import id.odojadmin.event.GetFormatRekapanByGroupIdEvent;
import id.odojadmin.event.GetMemberByGroupIdEvent;
import id.odojadmin.event.KholasClickEvent;
import id.odojadmin.event.NotKholasClickEvent;
import id.odojadmin.event.SubscriberPriority;
import id.odojadmin.event.WhatsappClickEvent;
import id.odojadmin.helper.DateHelper;
import id.odojadmin.model.FormatRekapan;
import id.odojadmin.model.Group;
import id.odojadmin.model.Member;
import id.odojadmin.model.RekapHarian;
import id.odojadmin.view.adapter.RekapAdapter;
import id.odojadmin.widget.TAGBoldText;
import id.odojadmin.widget.TAGBookText;
import id.odojadmin.widget.TAGMediumText;

public class DetailRekapanActivity extends BaseActivity {
    @BindView(R.id.text_view_name)
    TAGBoldText textViewName;
    @BindView(R.id.text_view_total_member)
    TAGBookText textViewTotalMember;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.recycler_view_member)
    RecyclerView recyclerViewMember;
    @BindView(R.id.crpv)
    ColorfulRingProgressView crpv;
    @BindView(R.id.text_view_persentase)
    TAGMediumText textViewPersentase;
    private List<Member> memberList = new ArrayList<>();
    private List<RekapHarian> rekapHarianList = new ArrayList<>();
    private MemberController controller;
    private GroupController groupController;
    private RekapanHarianController rekapanHarianController;
    private FormatRekapanController formatRekapanController;
    private int groupId;
    private RekapAdapter adapter;

    private Group group;

    private int totalKholas = 0;
    private AlertDialog dialog;
    private float pr;
    private FormatRekapan formatRekapan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_rekapan);
        ButterKnife.bind(this);
        eventBus.register(this, SubscriberPriority.HIGH);

        showDialogKholasGroup();
        setRecyclerViewMember();
        groupId = getIntent().getIntExtra("groupId", 0);
        controller = new MemberController();
        groupController = new GroupController();
        rekapanHarianController = new RekapanHarianController();
        formatRekapanController = new FormatRekapanController();
        formatRekapanController.getRekapan(groupId);
        groupController.getGroupDetail(String.valueOf(groupId));
        progressBar.setVisibility(View.VISIBLE);
        controller.getAllMemberByGroupId(groupId);
    }

    private void setRecyclerViewMember() {
        adapter = new RekapAdapter(this, memberList);
        recyclerViewMember.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        recyclerViewMember.setAdapter(adapter);
    }

    public void onEventMainThread(GetMemberByGroupIdEvent event) {
        progressBar.setVisibility(View.GONE);
        totalKholas = 0;
        if (event.isSuccess()) {
            memberList.clear();
            memberList.addAll(event.getMemberList());
            adapter.notifyDataSetChanged();

            for (Member m : event.getMemberList()) {
                if (m.getKholas().equals("k"))
                    totalKholas = totalKholas + 1;
            }

            RekapHarian rekapHarian = new RekapHarian(groupId + "-" + DateHelper.getSimpleDate(), groupId, DateHelper.getSimpleDate2(), 0, memberList.size(), memberList);
            rekapanHarianController.addRekapan(rekapHarian);

            setTotalKholas(totalKholas);
        }
    }

    public void onEventMainThread(GetDetailGroupEvent event) {
        if (event.isSuccess()) {
            group = event.getGroupDetail();
            textViewName.setText("Grup " + event.getGroupDetail().getId());
            textViewTotalMember.setText(event.getGroupDetail().getTotalMember() + " Member");
            float p = ((float) group.getTotalKholas() / (float) group.getTotalMember()) * 100;
            crpv.setPercent(p);
            pr = p;

            if (group.getTotalMember() != 0) {
                textViewPersentase.setText(String.format("%,.0f", p) + "%");
            } else
                textViewPersentase.setText("0%");
        }
    }

    public void onEventMainThread(WhatsappClickEvent event) {
        Toast.makeText(this, "Whatsapp " + event.getMember().getName() + " - " + event.getMember().getKholas(), Toast.LENGTH_SHORT).show();
    }

    public void onEventMainThread(KholasClickEvent event) {
        Toast.makeText(this, "Kholas " + event.getMember().getName(), Toast.LENGTH_SHORT).show();
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("kholas", "k");
        controller.update(event.getMember().getName(), hashMap);
    }

    public void onEventMainThread(NotKholasClickEvent event) {
        Toast.makeText(this, "Tidak kholas " + event.getMember().getName(), Toast.LENGTH_SHORT).show();
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("kholas", "t");
        controller.update(event.getMember().getName(), hashMap);
    }

    public void onEventMainThread(GetFormatRekapanByGroupIdEvent event) {
        System.out.println("FORMAT REKAPAN : " + event.isSuccess());
        if (event.isSuccess()) {
            formatRekapan = event.getRekapan();
        } else {
            formatRekapan = null;
        }
    }

    private void setTotalKholas(int totalKholas) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("totalKholas", totalKholas);
        groupController.update(groupId, hashMap);
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

    private void showDialogKholasGroup() {
        LayoutInflater li = LayoutInflater.from(this);
        final View dialogView = li.inflate(R.layout.dialog_kholas_group, null);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        Button btnYes = dialogView.findViewById(R.id.btn_yes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });

        alertDialog.setPositiveButton("Alhamdulillah", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        alertDialog.setView(dialogView);

        dialog = alertDialog.create();

    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
//        if (group != null && formatRekapan != null) {
//            PackageManager pm = getPackageManager();
//            try {
//                Intent waIntent = new Intent(Intent.ACTION_SEND);
//                waIntent.setType("text/plain");
//                String text = RekapanHelper.getRekapan(group, "Rena", formatRekapan, memberList);
//                PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
//                waIntent.setPackage("com.whatsapp");
//                waIntent.putExtra(Intent.EXTRA_TEXT, text);
//                startActivity(Intent.createChooser(waIntent, "Bagikan dengan"));
//            } catch (PackageManager.NameNotFoundException e) {
//                Toast.makeText(this, "WhatsApp belum terinstal", Toast.LENGTH_SHORT)
//                        .show();
//            }
//        }

        rekapanHarianController.getRekapanByDate(DateHelper.getSimpleDate2(), groupId);
    }
}
