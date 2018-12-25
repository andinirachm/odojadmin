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
import id.odojadmin.event.GetMemberByGroupIdEvent;
import id.odojadmin.model.Member;

/**
 * Created by Andini Rachmah on 24/12/18.
 */

public class MemberController extends BaseController {
    public void getAllMemberByGroupId(final int groupId) {
        final List<Member> memberList = new ArrayList<>();
        ApplicationMain.getInstance().getFirebaseDatabaseMember().orderByChild("id").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    memberList.clear();
                    for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                        Member member = noteDataSnapshot.getValue(Member.class);
                        if (member.getGroupId() == groupId) {
                            memberList.add(member);
                            eventBus.post(new GetMemberByGroupIdEvent(true, "Success", memberList));
                        } else {
                            eventBus.post(new GetMemberByGroupIdEvent(false, "Failure", memberList));
                        }
                    }
                } else {
                    eventBus.post(new GetMemberByGroupIdEvent(false, "Failure", memberList));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                eventBus.post(new GetMemberByGroupIdEvent(false, "Failure " + databaseError.getMessage(), memberList));
            }
        });
    }

    public void addMember(final Member member) {
        ApplicationMain.getInstance().getFirebaseDatabaseMember().child(member.getName()).setValue(member).addOnCompleteListener(new OnCompleteListener<Void>() {
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
