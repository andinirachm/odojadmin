package id.odojadmin.model;

public class Member {
    private int id;
    private String name;
    private boolean isKholas;
    private String juz;
    private String phone;
    private boolean isKarantina;
    private int groupId;

    public Member() {
    }

    public Member(int id, String name, boolean isKholas, String juz, String phone, boolean isKarantina, int groupId) {
        this.id = id;
        this.name = name;
        this.isKholas = isKholas;
        this.juz = juz;
        this.phone = phone;
        this.isKarantina = isKarantina;
        this.groupId = groupId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isKholas() {
        return isKholas;
    }

    public String getJuz() {
        return juz;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isKarantina() {
        return isKarantina;
    }

    public int getGroupId() {
        return groupId;
    }
}
