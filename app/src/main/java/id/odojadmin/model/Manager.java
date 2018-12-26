package id.odojadmin.model;

/**
 * Created by Andini Rachmah on 26/12/18.
 */
public class Manager {
    private String adminId;

    public Manager(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
}
