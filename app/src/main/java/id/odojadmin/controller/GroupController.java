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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import id.odojadmin.ApplicationMain;
import id.odojadmin.event.CommonEvent;
import id.odojadmin.event.GetDetailGroupEvent;
import id.odojadmin.event.GetGroupByAdminIdEvent;
import id.odojadmin.model.Group;

/**
 * Created by Andini Rachmah on 24/12/18.
 */

public class GroupController extends BaseController {
    public void getAllGroupByAdminId(final String adminId) {
        System.out.println("ADMIN ID : " + adminId);
        final List<Group> groupList = new ArrayList<>();
        ApplicationMain.getInstance().getFirebaseDatabaseGroup().orderByChild("id").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    groupList.clear();
                    for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                        Group group = noteDataSnapshot.getValue(Group.class);
                        if (group.getAdminId() != null) {
                            System.out.println("ADMIN IDO : " + group.getAdminId());
                            if (group.getAdminId().contains(adminId)) {
                                System.out.println("ADMIN IDO : true");
                                groupList.add(group);
                                eventBus.post(new GetGroupByAdminIdEvent(true, "Success", groupList));
                            } else {
                                System.out.println("ADMIN IDO : false");
                                eventBus.post(new GetGroupByAdminIdEvent(false, "Failure", null));
                            }
                        }
                    }
                } else {
                    eventBus.post(new GetGroupByAdminIdEvent(false, "Failure", null));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                eventBus.post(new GetGroupByAdminIdEvent(false, "Failure " + databaseError.getMessage(), null));
            }
        });
    }

    public void getGroupDetail(final String groupId) {
        ApplicationMain.getInstance().getFirebaseDatabaseGroup().orderByChild("id").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                        Group group = noteDataSnapshot.getValue(Group.class);
                        if (group.getAdminId() != null) {
                            if (group.getId().equals(groupId)) {
                                eventBus.post(new GetDetailGroupEvent(true, "Success", group));
                            } else {
                                eventBus.post(new GetDetailGroupEvent(false, "Failure", null));
                            }
                        }
                    }
                } else {
                    eventBus.post(new GetDetailGroupEvent(false, "Failure", null));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                eventBus.post(new GetDetailGroupEvent(false, "Failure " + databaseError.getMessage(), null));
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

    public void delete(final String groupId) {
        System.out.println("GROUP DEL  " + groupId);
        Query query = ApplicationMain.getInstance().getFirebaseDatabaseGroup().child(groupId);
        System.out.println("QUE " + ((DatabaseReference) query).getKey());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        snapshot.getRef().removeValue();
                        eventBus.post(new CommonEvent(true, "Success"));
                        System.out.println("GROUP DEL S");
                    }
                } else {
                    eventBus.post(new CommonEvent(false, "Failure"));
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
