package id.odojadmin.model;

public class Group {
    private  String id;
    private int totalMember;
    private int totalKholas;

    public Group(String id, int totalMember, int totalKholas) {
        this.id = id;
        this.totalMember = totalMember;
        this.totalKholas = totalKholas;
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
}
