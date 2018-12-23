package id.odojadmin.presenter;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import id.odojadmin.model.Admin;
import id.odojadmin.view.interfaces.SignInView;

/**
 * Created by Andini Rachmah on 19/12/18.
 */
public class SignInPresenter implements SignInView.Presenter, BasePresenter {
    private SignInView.View view;
    private DatabaseReference mFireDatabase;

    public SignInPresenter(SignInView.View view, DatabaseReference mFireDatabase) {
        this.view = view;
        this.mFireDatabase = mFireDatabase;
    }

    @Override
    public void onDetachView() {
        this.view = null;
    }

    @Override
    public void login(final String email, final String password) {
        view.onShowLoading();
        mFireDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                view.onRemoveLoading();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Admin admin = noteDataSnapshot.getValue(Admin.class);
                    if (admin.getEmail().equals(email) && admin.getPassword().equals(password)) {
                        view.onSuccessLogin(admin);
                        break;
                    }else{
                        view.onSuccessLogin(null);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                view.onRemoveLoading();
                view.onFailure(databaseError.getMessage());
            }
        });
    }
}
