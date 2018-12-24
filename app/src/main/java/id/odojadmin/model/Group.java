package id.odojadmin.model;

public class Group {
    private String id; //not editable
    private int totalMember;
    private int totalKholas;
    private String adminId;

    public Group() {
    }

    public Group(String id, int totalMember, int totalKholas, String adminId) {
        this.id = id;
        this.totalMember = totalMember;
        this.totalKholas = totalKholas;
        this.adminId = adminId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTotalMember() {
        return totalMember;
    }

    public void setTotalMember(int totalMember) {
        this.totalMember = totalMember;
    }

    public int getTotalKholas() {
        return totalKholas;
    }

    public void setTotalKholas(int totalKholas) {
        this.totalKholas = totalKholas;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
}
