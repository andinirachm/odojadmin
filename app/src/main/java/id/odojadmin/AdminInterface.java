package id.odojadmin;

import id.odojadmin.model.Admin;

public interface AdminInterface {
    void onSuccessLogin(Admin admin);
    void onFailureLogin(String s);
}
