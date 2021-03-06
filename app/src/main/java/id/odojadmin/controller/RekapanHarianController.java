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

import java.util.Map;

import id.odojadmin.ApplicationMain;
import id.odojadmin.event.CommonEvent;
import id.odojadmin.event.GetRekapanHarianByDateGroupIdEvent;
import id.odojadmin.model.RekapHarian;

/**
 * Created by Andini Rachmah on 24/12/18.
 */

public class RekapanHarianController extends BaseController {
    public void getRekapanByDate(final String date, final int group) {
        Query query = ApplicationMain.getInstance().getFirebaseDbRekapHarian();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    RekapHarian rekapHarian = null;
                    for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                        rekapHarian = noteDataSnapshot.getValue(RekapHarian.class);
                        if (rekapHarian.getDate().equals(date) && rekapHarian.getGroupId() == group) {
                            break;
                        }
                    }

                    System.out.println("olaf lala " + rekapHarian.getDate());
                    eventBus.post(new GetRekapanHarianByDateGroupIdEvent(true, "Success", rekapHarian));
                } else {
                    eventBus.post(new GetRekapanHarianByDateGroupIdEvent(false, "Failure", null));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                eventBus.post(new GetRekapanHarianByDateGroupIdEvent(false, "Failure", null));
            }
        });
    }

    public void addRekapan(final RekapHarian rekapHarian) {
        ApplicationMain.getInstance().getFirebaseDbRekapHarian().child(rekapHarian.getId()).setValue(rekapHarian).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    eventBus.post(new CommonEvent(true, "Success"));
                    System.out.println("MEMBER CREATE S");
                } else {
                    eventBus.post(new CommonEvent(false, "Failure"));
                    System.out.println("MEMBER CREATE F");
                }
            }
        });
    }

    public void update(final String id, final Map<String, Object> hashMap) {
        ApplicationMain.getInstance().getFirebaseDatabaseMember().child(id).updateChildren(hashMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        eventBus.post(new CommonEvent(true, "Success"));
                        System.out.println("MEMBER UPDATE S");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        eventBus.post(new CommonEvent(false, "Failure"));
                        System.out.println("MEMBER UPDATE F");
                    }
                });
            }
        });
    }

    public void delete(final String name) {
        Query query = ApplicationMain.getInstance().getFirebaseDatabaseMember().child(name);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        snapshot.getRef().removeValue();
                        eventBus.post(new CommonEvent(true, "Success"));
                    }
                } else {
                    eventBus.post(new CommonEvent(false, "Failure"));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                eventBus.post(new CommonEvent(false, "Failure"));
            }
        });
    }
}
