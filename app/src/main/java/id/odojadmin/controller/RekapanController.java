package id.odojadmin.controller;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import id.odojadmin.ApplicationMain;
import id.odojadmin.event.CommonEvent;
import id.odojadmin.model.Member;
import id.odojadmin.model.Rekapan;

/**
 * Created by Andini Rachmah on 24/12/18.
 */

public class RekapanController extends BaseController {
    public void getRekapan() {
        ApplicationMain.getInstance().getFirebaseDatabaseRekapan().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                        Member member = noteDataSnapshot.getValue(Member.class);
                        System.out.println("REKAPAN GET S ");

                    }
                } else {
                    System.out.println("REKAPAN GET KOSONG F");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addRekapan(final Rekapan rekapan) {
        Date currentTime = Calendar.getInstance().getTime();
        ApplicationMain.getInstance().getFirebaseDatabaseRekapan().child(currentTime.toString()).setValue(rekapan).addOnCompleteListener(new OnCompleteListener<Void>() {
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
