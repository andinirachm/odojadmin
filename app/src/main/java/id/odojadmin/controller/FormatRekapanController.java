package id.odojadmin.controller;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import id.odojadmin.ApplicationMain;
import id.odojadmin.event.CommonEvent;
import id.odojadmin.event.GetFormatRekapanByGroupIdEvent;
import id.odojadmin.model.FormatRekapan;

/**
 * Created by Andini Rachmah on 24/12/18.
 */

public class FormatRekapanController extends BaseController {
    public void getRekapan(final int id) {
        final List<FormatRekapan> rekapanList = new ArrayList<>();
        ApplicationMain.getInstance().getFirebaseDatabaseRekapan().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    rekapanList.clear();
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                            FormatRekapan rekapan = noteDataSnapshot.getValue(FormatRekapan.class);
                            if (rekapan != null)
                                if (rekapan.getIdGroup() == id) {
                                    rekapanList.add(rekapan);
                                }
                        }
                        if (rekapanList != null) {
                            System.out.println("POPO tidak null ");
                            eventBus.post(new GetFormatRekapanByGroupIdEvent(true, "success", rekapanList.get(0)));
                        } else {
                            System.out.println("POPO null ");
                            eventBus.post(new GetFormatRekapanByGroupIdEvent(false, "failure", null));
                        }

                    } else {
                        eventBus.post(new GetFormatRekapanByGroupIdEvent(false, "failure", null));
                    }
                } else {
                    eventBus.post(new GetFormatRekapanByGroupIdEvent(false, "failure", null));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                eventBus.post(new GetFormatRekapanByGroupIdEvent(false, "failure", null));
            }
        });

    }

    public void getRekapan(final String adminId) {
        final List<FormatRekapan> rekapanList = new ArrayList<>();
        ApplicationMain.getInstance().getFirebaseDatabaseRekapan().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    rekapanList.clear();
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                            FormatRekapan rekapan = noteDataSnapshot.getValue(FormatRekapan.class);
                            if (rekapan.getAdminId() == adminId) {
                                rekapanList.add(rekapan);
                            }
                        }
                        eventBus.post(new GetFormatRekapanByGroupIdEvent(true, "success", rekapanList.get(0)));
                    } else {
                        eventBus.post(new GetFormatRekapanByGroupIdEvent(false, "failure", null));
                    }
                } else {
                    eventBus.post(new GetFormatRekapanByGroupIdEvent(false, "failure", null));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                eventBus.post(new GetFormatRekapanByGroupIdEvent(false, "failure", null));
            }
        });

    }

    public void addRekapan(final FormatRekapan rekapan) {
        ApplicationMain.getInstance().getFirebaseDatabaseRekapan().child(String.valueOf(rekapan.getIdGroup())).setValue(rekapan).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    eventBus.post(new CommonEvent(true, "Success"));
                    System.out.println("REKAPAN CREATE S");
                } else {
                    eventBus.post(new CommonEvent(false, "Failure"));
                    System.out.println("REKAPAN CREATE F");
                }
            }
        });
    }

    public void update(final String id, final Map<String, Object> hashMap) {
        ApplicationMain.getInstance().getFirebaseDatabaseRekapan().child(id).updateChildren(hashMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        eventBus.post(new CommonEvent(true, "Success"));
                        System.out.println("REKAPAN UPDATE S");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        eventBus.post(new CommonEvent(false, "Failure"));
                        System.out.println("REKAPAN UPDATE F");
                    }
                });
            }
        });
    }
}
