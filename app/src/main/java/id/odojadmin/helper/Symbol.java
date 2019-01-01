package id.odojadmin.helper;

public class Symbol {
    //rekapan SG
    public static final int crown = 0x1F451;
    public static final int checkList = 0x2705;
    public static final int clock = 0x1F556;
    public static final int women = 0x1F6BA;

    public static final String zero = "\u0030\u20E3";
    public static final String one = "\u0031\u20E3";
    public static final String two = "\u0032\u20E3";
    public static final String three = "\u0033\u20E3";
    public static final String four = "\u0034\u20E3";
    public static final String five = "\u0035\u20E3";
    public static final String six = "\u0036\u20E3";
    public static final String seven = "\u0037\u20E3";
    public static final String eight = "\u0038\u20E3";
    public static final String nine = "\u0039\u20E3";

    public static final String tandaTanya = "\u2753";
    public static final String tunjukKanan = "\uD83D\uDC49\uD83C\uDFFB";
    public static final String tandaSilang = "\u274c";
    public static final String star = "\u2B50";
    public static final String calendar = "\uD83D\uDCC5";
    public static final String cd = "\uD83D\uDCBF";
    public static final String book = "\uD83D\uDCD6";
    public static final String yellowBook = " \uD83D\uDCD2";
    public static final String recycle = "\u267B\uFE0F";
    public static final String batasLapor = "\u26D4";
    public static final String mosque = "\uD83D\uDD4C";
    public static final String kabah = "\uD83D\uDD4B";
    public static final String fiveHand = "\uD83D\uDD90\uD83C\uDFFB";
    public static final String home = "\uD83C\uDFE1";
    public static final String dividerTotalKholas = "\u2796\u2796\u2796\u2796\u2796\u2796\u2796\u2796\u2796\u2796";
    public static final String haid = "\uD83D\uDE45\uD83C\uDFFB\u200D";

    public static final String sunFlower ="\uD83C\uDF3B";
    public static final String divider ="\uD83C\uDF3F\uD83C\uDF3B\uD83C\uDF3F\uD83C\uDF3B\uD83C\uDF3F\uD83C\uDF3B\uD83C\uDF3F\uD83C\uDF3B\uD83C\uDF3F\uD83C\uDF3B\uD83C\uDF3F";
    public static final String womanHijab = "\uD83E\uDDD5\uD83C\uDFFB";
    public static final String admin = "\uD83D\uDC69\uD83C\uDFFB\u200D\uD83D\uDCBB";


    public static String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }

    public static String getCheckList() {
        return getEmojiByUnicode(checkList);
    }

    public static String getCrown() {
        return getEmojiByUnicode(crown);
    }

    public static String getClock() {
        return getEmojiByUnicode(clock);
    }

    public static String getWomen() {
        return getEmojiByUnicode(women);
    }
}
