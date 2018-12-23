package id.odojadmin.view.interfaces;

import id.odojadmin.model.Admin;

public interface SignInView {
    interface View extends BaseView {
        void onSuccessLogin(Admin response);

        void onFailure(String s);
    }

    interface Presenter {
        void login(String email, String password);
    }
}
