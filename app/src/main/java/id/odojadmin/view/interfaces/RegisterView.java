package id.odojadmin.view.interfaces;

import id.odojadmin.model.Admin;

public interface RegisterView {
    interface View extends BaseView {
        void onSuccessRegister(String s);

        void onFailure(String s);
    }

    interface Presenter {
        void register(String email, String password);
    }
}
