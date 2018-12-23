package id.odojadmin.model;

public class Rekapan {
    private String batasLaporan;
    private String iconPembatas;
    private String iconKholas;
    private String iconBelumKholas;
    private String iconKholasTelat;
    private String iconKholas1Juz;
    private String iconKholasLebih1Juz;
    private String iconTidakKholas;
    private String spritWords;

    public Rekapan(String batasLaporan, String iconPembatas, String iconKholas, String iconBelumKholas, String iconKholasTelat, String iconKholas1Juz, String iconKholasLebih1Juz, String iconTidakKholas, String spritWords) {
        this.batasLaporan = batasLaporan;
        this.iconPembatas = iconPembatas;
        this.iconKholas = iconKholas;
        this.iconBelumKholas = iconBelumKholas;
        this.iconKholasTelat = iconKholasTelat;
        this.iconKholas1Juz = iconKholas1Juz;
        this.iconKholasLebih1Juz = iconKholasLebih1Juz;
        this.iconTidakKholas = iconTidakKholas;
        this.spritWords = spritWords;
    }

    public String getBatasLaporan() {
        return batasLaporan;
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
}
