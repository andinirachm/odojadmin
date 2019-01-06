package id.odojadmin.model;

import java.util.Map;

import id.odojadmin.ApplicationMain;

public class Member {
    private int id;
    private String name;
    private String kholas;
    private String juz;
    private String phone;
    private boolean isKarantina;
    private int groupId;

    public Member() {
    }

    public Member(int id, String name, String kholas, String juz, String phone, boolean isKarantina, int groupId) {
        this.id = id;
        this.name = name;
        this.kholas = kholas;
        this.juz = juz;
        this.phone = phone;
        this.isKarantina = isKarantina;
        this.groupId = groupId;
    }

    public void setJuz(String juz) {
        this.juz = juz;
    }

    public String getKholas() {
        return kholas;
    }

    public void setKholas(String kholas) {
        this.kholas = kholas;
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

    public void createMember(String id, Member member) { //id = 137-Ilma
        ApplicationMain.getInstance().getFirebaseDatabaseMember().child(name).setValue(member);
    }

    public void updateMember(String name, Map<String, Object> map) {
        ApplicationMain.getInstance().getFirebaseDatabaseMember().child(name).updateChildren(map);
    }

    public void deleteMember(String id) {
        ApplicationMain.getInstance().getFirebaseDatabaseMember().child(id).removeValue();
    }
}
