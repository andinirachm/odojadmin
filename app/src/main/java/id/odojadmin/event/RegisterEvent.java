package id.odojadmin.event;

import id.odojadmin.model.Admin;

public class RegisterEvent extends BaseEvent {
    private Admin admin;

    public RegisterEvent(boolean isSuccess, String message, Admin admin) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.admin = admin;
    }

    public Admin getAdmin() {
        return admin;
    }
}
