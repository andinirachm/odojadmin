package id.odojadmin.model;

/**
 * Created by Andini Rachmah on 11/12/18.
 */
public class Admin {
    private String noAdmin;
    private String name;
    private String totalGroup;
    private String origin;
    private String email;
    private String password;

    public Admin() {
    }

    public Admin(String noAdmin, String name, String totalGroup, String origin, String email, String password) {

        this.noAdmin = noAdmin;
        this.name = name;
        this.totalGroup = totalGroup;
        this.origin = origin;
        this.email = email;
        this.password = password;
    }

    public String getNoAdmin() {
        return noAdmin;
    }

    public void setNoAdmin(String noAdmin) {
        this.noAdmin = noAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalGroup() {
        return totalGroup;
    }

    public void setTotalGroup(String totalGroup) {
        this.totalGroup = totalGroup;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
