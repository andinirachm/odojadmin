package id.odojadmin.model;

public class A {
    private int id;
    private String date;
    private String name;
    private String kholas;
    private String juz;
    private boolean isKarantina;
    private int groupId;

    public A() {
    }

    public A(int id, String name, String kholas, String juz, boolean isKarantina, int groupId, String date) {
        this.id = id;
        this.name = name;
        this.kholas = kholas;
        this.juz = juz;
        this.isKarantina = isKarantina;
        this.groupId = groupId;
        this.date= date;
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

    public boolean isKarantina() {
        return isKarantina;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getDate() {
        return date;
    }
}
