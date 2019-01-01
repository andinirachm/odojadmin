package id.odojadmin.model;

public class FormatRekapan {
    private int idGroup;
    private String iconPembatas;
    private String iconKholas;
    private String iconBelumKholas;
    private String iconKholasTelat;
    private String iconKholas1Juz;
    private String iconKholasLebih1Juz;
    private String iconTidakKholas;
    private String iconAlKahfi;
    private String spritWords;
    private String adminId;

    public FormatRekapan() {
    }

    public FormatRekapan(int idGroup, String iconPembatas, String iconKholas, String iconBelumKholas, String iconKholasTelat, String iconKholas1Juz, String iconKholasLebih1Juz, String iconTidakKholas, String iconAlKahfi, String spritWords, String adminId) {
        this.idGroup = idGroup;
        this.iconPembatas = iconPembatas;
        this.iconKholas = iconKholas;
        this.iconBelumKholas = iconBelumKholas;
        this.iconKholasTelat = iconKholasTelat;
        this.iconKholas1Juz = iconKholas1Juz;
        this.iconKholasLebih1Juz = iconKholasLebih1Juz;
        this.iconTidakKholas = iconTidakKholas;
        this.iconAlKahfi = iconAlKahfi;
        this.spritWords = spritWords;
        this.adminId= adminId;
    }

    public String getIconAlKahfi() {
        return iconAlKahfi;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public String getIconPembatas() {
        return iconPembatas;
    }

    public String getIconKholas() {
        return iconKholas;
    }

    public String getIconBelumKholas() {
        return iconBelumKholas;
    }

    public String getIconKholasTelat() {
        return iconKholasTelat;
    }

    public String getIconKholas1Juz() {
        return iconKholas1Juz;
    }

    public String getIconKholasLebih1Juz() {
        return iconKholasLebih1Juz;
    }

    public String getIconTidakKholas() {
        return iconTidakKholas;
    }

    public String getSpritWords() {
        return spritWords;
    }

    public String getAdminId() {
        return adminId;
    }
}
