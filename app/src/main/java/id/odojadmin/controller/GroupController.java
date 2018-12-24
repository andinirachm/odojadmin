package id.odojadmin.controller;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import id.odojadmin.ApplicationMain;
import id.odojadmin.event.CommonEvent;
import id.odojadmin.model.Group;

/**
 * Created by Andini Rachmah on 24/12/18.
 */

public class GroupController extends BaseController {
    public void getAllGroupByAdminId(final String adminId) {
        final List<Group> groupList = new ArrayList<>();
        ApplicationMain.getInstance().getFirebaseDatabaseGroup().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                        Group group = noteDataSnapshot.getValue(Group.class);
                        if (group.getAdminId().equals(adminId)) {
                            // add to list, post event
                            groupList.add(group);
                            System.out.println("GROUP GET S " + groupList.size());
                        } else {
                            // not found
                            System.out.println("GROUP GET F");
                        }
                    }
                } else {
                    System.out.println("GROUP GET KOSONG F");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void createGroup(final Group group) {
        ApplicationMain.getInstance().getFirebaseDatabaseGroup().child(group.getId()).setValue(group).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    eventBus.post(new CommonEvent(true, "Success"));
                    System.out.println("GROUP CREATE S");
                } else {
                    eventBus.post(new CommonEvent(false, "Failure"));
                    System.out.println("GROUP CREATE F");
                }
            }
        });
    }

    public void update(final int groupId, final Map<String, Object> hashMap) {
        ApplicationMain.getInstance().getFirebaseDatabaseGroup().child(String.valueOf(groupId)).updateChildren(hashMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        eventBus.post(new CommonEvent(true, "Success"));
                        System.out.println("GROUP UPDATE S");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        eventBus.post(new CommonEvent(false, "Failure"));
                        System.out.println("GROUP UPDATE F");
                    }
                });
            }
        });
    }

    public void delete(final int groupId) {
        Query query = ApplicationMain.getInstance().getFirebaseDatabaseGroup().child(String.valueOf(groupId));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        snapshot.getRef().removeValue();
                        eventBus.post(new CommonEvent(true, "Success"));
                        System.out.println("GROUP DEL S");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                eventBus.post(new CommonEvent(false, "Failure"));
                System.out.println("GROUP DEL F");
            }
        });
    }
}
