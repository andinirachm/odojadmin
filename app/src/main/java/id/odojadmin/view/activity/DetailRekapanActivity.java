package id.odojadmin.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import id.odojadmin.ApplicationMain;
import id.odojadmin.R;
import id.odojadmin.controller.FormatRekapanController;
import id.odojadmin.controller.GroupController;
import id.odojadmin.controller.MemberController;
import id.odojadmin.controller.RekapanHarianController;
import id.odojadmin.event.GetDetailGroupEvent;
import id.odojadmin.event.GetFormatRekapanByGroupIdEvent;
import id.odojadmin.event.GetMemberByGroupIdEvent;
import id.odojadmin.event.GetRekapanHarianByDateGroupIdEvent;
import id.odojadmin.event.KholasClickEvent;
import id.odojadmin.event.NotKholasClickEvent;
import id.odojadmin.event.SubscriberPriority;
import id.odojadmin.event.WhatsappClickEvent;
import id.odojadmin.helper.DateHelper;
import id.odojadmin.helper.RekapanHelper;
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

        groupController.getGroupDetail(String.valueOf(groupId));
        progressBar.setVisibility(View.VISIBLE);
        controller.getAllMemberByGroupId(groupId);
        textViewPersentase.setText("Closing");
        rekapanHarianController.getRekapanByDate(DateHelper.getSimpleDate(), groupId);
//        group.createGroup("123", 20, 10, "andinirachmahgmailcom", "20:00", "Dini", "Ilma");
        if (memberList.size() != 0) formatRekapanController.getRekapan(groupId);
    }

    private void setRecyclerViewMember() {
        adapter = new RekapAdapter(this, memberList);
        recyclerViewMember.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        recyclerViewMember.setAdapter(adapter);
    }

    public void onEventMainThread(GetMemberByGroupIdEvent event) {
        List<Member> memberNewList = new ArrayList<>();
        progressBar.setVisibility(View.GONE);
        totalKholas = 0;
        if (event.isSuccess()) {
            if (event.getMemberList().size() != 0)
                memberList.clear();

            for (Member m : event.getMemberList()) {
                String[] jz = m.getJuz().split("-");
                String juz = jz[0];
                String ab = jz[1];
                System.out.println("JUZA : " + m.getName() + " - " + m.getJuz());
                m.setJuz(RekapanHelper.getNextJuz(Integer.parseInt(juz), ab));
                memberNewList.add(m);
                if (m.getKholas().equals("k"))
                    totalKholas = totalKholas + 1;
            }

            adapter = new RekapAdapter(this, memberNewList);
            //recyclerViewMember.setAdapter(adapter);

            RekapHarian rekapHarian = new RekapHarian(groupId + "-" + DateHelper.getSimpleDate(), groupId, DateHelper.getSimpleDate2(), 0, memberNewList.size(), memberNewList);
            rekapanHarianController.addRekapan(rekapHarian);

            setTotalKholas(totalKholas);
        }
    }

    public void onEventMainThread(GetRekapanHarianByDateGroupIdEvent event) {
        if (event.isSuccess()) {
            System.out.println("OLAF : " + event.getMemberList().getDate());

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

//            if (group.getTotalMember() != 0) {
//                textViewPersentase.setText(String.format("%,.0f", p) + "%");
//            } else
//                textViewPersentase.setText("0%");
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

    private void sendToWhatsapp() {
        if (group != null && formatRekapan != null) {
            PackageManager pm = getPackageManager();
            try {
                Intent waIntent = new Intent(Intent.ACTION_SEND);
                waIntent.setType("text/plain");
                String text = RekapanHelper.getRekapan(group, "Rena", formatRekapan, memberList);
                PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                waIntent.setPackage("com.whatsapp");
                waIntent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(waIntent, "Bagikan dengan"));
            } catch (PackageManager.NameNotFoundException e) {
                Toast.makeText(this, "WhatsApp belum terinstal", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @OnClick(R.id.relative_layout_progres)
    public void onRelativeLayoutProgresClicked() {
    }

    @OnClick(R.id.fab)
    public void onFabClicked() {
        sendToWhatsapp();
    }
}
