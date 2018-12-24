package id.odojadmin.event;

import id.odojadmin.model.Admin;

public class LoginEvent extends BaseEvent {
    private Admin admin;

    public LoginEvent(boolean isSuccess, String message, Admin admin) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.admin = admin;
    }

    public Admin getAdmin() {
        return admin;
    }
}
