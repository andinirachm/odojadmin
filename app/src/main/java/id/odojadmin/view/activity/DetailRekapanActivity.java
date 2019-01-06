package id.odojadmin.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
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
    private RekapHarian rekapHarian = null;
    private DateHelper dateHelper;
    private String currentDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_rekapan);
        ButterKnife.bind(this);
        eventBus.register(this, SubscriberPriority.HIGH);

        dateHelper = new DateHelper();
        currentDate = dateHelper.getTodayDateString();

        showDialogKholasGroup();
        setRecyclerViewMember();
        groupId = getIntent().getIntExtra("groupId", 0);
        controller = new MemberController();
        groupController = new GroupController();
        rekapanHarianController = new RekapanHarianController();
        formatRekapanController = new FormatRekapanController();

        groupController.getGroupDetail(String.valueOf(groupId));
        progressBar.setVisibility(View.VISIBLE);
        ApplicationMain.getInstance().getFirebaseDbRekapHarian().orderByChild("date").addValueEventListener(eventListener);
        //rekapanHarianController.getRekapanByDate(DateHelper.getSimpleDate(), groupId);
        if (memberList.size() != 0) formatRekapanController.getRekapan(groupId);
    }

    private void setRecyclerViewMember() {
        adapter = new RekapAdapter(this, memberList);
        recyclerViewMember.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        recyclerViewMember.setAdapter(adapter);
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

    ValueEventListener eventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            memberList.clear();
            boolean isFound = false;
            if (progressBar.getVisibility() == View.VISIBLE)
                progressBar.setVisibility(View.GONE);
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                rekapHarian = snapshot.getValue(RekapHarian.class);
                if (rekapHarian.getGroupId() == groupId) {
                    if (rekapHarian.getDate().equals(currentDate)) {
                        isFound = true;
                        memberList.addAll(rekapHarian.getMemberHarianList());
                    }
                }
            }

            adapter.notifyDataSetChanged();

            if (isFound) {
                memberList.clear();
                if (rekapHarian != null) {
                    System.out.println("HOLA GA USAH CREATE");
                    ApplicationMain.getInstance().getFirebaseDbRekapHarian().removeEventListener(eventListener);
                    setDataRekapanHarian();
                }
            } else {
                System.out.println("HOLA CREATE");
                memberList.clear();
                ApplicationMain.getInstance().getFirebaseDbRekapHarian().removeEventListener(eventListener);
                ApplicationMain.getInstance().getFirebaseDatabaseMember().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Member member = snapshot.getValue(Member.class);
                            if (member.getGroupId() == groupId) {
                                memberList.add(member);

                                if (member.getKholas().equals("k"))
                                    totalKholas = totalKholas + 1;
                            }
                        }

                        RekapHarian rekapHarian = new RekapHarian(groupId + "-" + DateHelper.getSimpleDate(), groupId, DateHelper.getSimpleDate2(), 0, memberList.size(), memberList);
                        rekapHarian.createRekapHarian(rekapHarian);

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    /*
     * untuk update table member ketika rekapan diupdate
     * */
    private void setDataMemberOnRekapanHarian() {
        ApplicationMain.getInstance().getFirebaseDatabaseMember().removeEventListener(eventListener);
        Query query = ApplicationMain.getInstance().getFirebaseDatabaseMember();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Member member = ds.getValue(Member.class);
                    String[] jz = member.getJuz().split("-");
                    String juz = jz[0];
                    String ab = jz[1];
                    ds.child("juz").getRef().setValue(RekapanHelper.getNextJuz(Integer.parseInt(juz), ab));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };

        query.addListenerForSingleValueEvent(valueEventListener);
    }

    private void setDataRekapanHarian() {
        memberList.clear();
        ApplicationMain.getInstance().getFirebaseDbRekapHarian().removeEventListener(eventListener);
        Query query = ApplicationMain.getInstance().getFirebaseDbRekapHarian().child("137-05012019").child("memberHarianList").orderByChild("id");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Member member = ds.getValue(Member.class);
                    System.out.println("HOLA " + member.getName() + " - " + member.getJuz());
                    member.setKholas("k");
                    ds.child("kholas").getRef().setValue("k");
                    memberList.add(member);
                }
                //setDataMemberOnRekapanHarian();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };

        query.addListenerForSingleValueEvent(valueEventListener);
    }

    private void ref() {
        memberList.clear();
        ApplicationMain.getInstance().getFirebaseDbRekapHarian().removeEventListener(eventListener);
        Query query = ApplicationMain.getInstance().getFirebaseDbRekapHarian().child("137-05012019").child("memberHarianList").orderByChild("id");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Member member = ds.getValue(Member.class);
                    String[] jz = member.getJuz().split("-");
                    String juz = jz[0];
                    String ab = jz[1];
                    System.out.println("HOLA " + member.getName() + " - " + member.getJuz());
                    member.setJuz(RekapanHelper.getNextJuz(Integer.parseInt(juz), ab));
                    ds.child("juz").getRef().setValue(RekapanHelper.getNextJuz(Integer.parseInt(juz), ab));
                    memberList.add(member);
                }
                //setDataMemberOnRekapanHarian();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };

        query.addListenerForSingleValueEvent(valueEventListener);
    }
}
