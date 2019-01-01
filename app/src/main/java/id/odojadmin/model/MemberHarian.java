package id.odojadmin.model;

public class MemberHarian {
    private int id;
    private String name;
    private String kholas;
    private String juz;
    private String phone;
    private boolean isKarantina;
    private int groupId;

    public MemberHarian() {
    }

    public MemberHarian(int id, String name, String kholas, String juz, String phone, boolean isKarantina, int groupId) {
        this.id = id;
        this.name = name;
        this.kholas = kholas;
        this.juz = juz;
        this.phone = phone;
        this.isKarantina = isKarantina;
        this.groupId = groupId;
    }

    public String getKholas() {
        return kholas;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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
