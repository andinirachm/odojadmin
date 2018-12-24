package id.odojadmin.controller;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import id.odojadmin.ApplicationMain;
import id.odojadmin.event.LoginEvent;
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
}
