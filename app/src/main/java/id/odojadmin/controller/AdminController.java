package id.odojadmin.controller;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import id.odojadmin.ApplicationMain;
import id.odojadmin.event.CommonEvent;
import id.odojadmin.event.LoginEvent;
import id.odojadmin.event.RegisterEvent;
import id.odojadmin.helper.PreferenceHelper;
import id.odojadmin.model.Admin;

/**
 * Created by Andini Rachmah on 24/12/18.
 */

public class AdminController extends BaseController {
    public void login(final String email, final String password) {
        ApplicationMain.getInstance().getFirebaseDatabaseAdmin().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Admin admin = noteDataSnapshot.getValue(Admin.class);
                    if (admin.getEmail().equals(email) && admin.getPassword().equals(password)) {
                        eventBus.post(new LoginEvent(true, "Success", admin));
                        break;
                    } else {
                        eventBus.post(new LoginEvent(false, "Failure", null));
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                eventBus.post(new LoginEvent(false, "Failure " + databaseError.getMessage(), null));
            }
        });
    }

    public void register(final Admin admin) {
        String id = admin.getEmail().replace("@", "");
        id = id.replace(".", "");

        ApplicationMain.getInstance().getFirebaseDatabaseAdmin().child(id).setValue(admin).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    String id = admin.getEmail().replace("@", "");
                    id = id.replace(".", "");
                    eventBus.post(new RegisterEvent(true, "Success", admin));
                } else {
                    eventBus.post(new RegisterEvent(false, "Failure", null));
                }
            }
        });
    }

    public void update(final Map<String, Object> hashMap) {
        String id = PreferenceHelper.getInstance().getSessionString(PreferenceHelper.KEY_USER_ID);
        ApplicationMain.getInstance().getFirebaseDatabaseAdmin().child(id).updateChildren(hashMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        eventBus.post(new CommonEvent(true, "Success"));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        eventBus.post(new CommonEvent(false, "Failure"));
                    }
                });
            }
        });
    }
}
