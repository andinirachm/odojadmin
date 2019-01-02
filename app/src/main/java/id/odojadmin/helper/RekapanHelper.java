package id.odojadmin.helper;

import java.util.List;

import id.odojadmin.model.FormatRekapan;
import id.odojadmin.model.Group;
import id.odojadmin.model.Member;

public class RekapanHelper {
    public static String getRekapan(final Group group,
                                    String pjh, FormatRekapan rekapan, List<Member> memberList) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bismillaahirrahmaanirrahiim..\n\n" +
                Symbol.yellowBook + " *LIST TILAWAH ODALF " + group.getId() + "* " + Symbol.yellowBook + "\n\n" +
                Symbol.calendar + " *" + DateHelper.getCurrentDate() + "*\n" +
                Symbol.admin + " *Admin :* " + group.getAdminName() + "\n");
        if (!group.getAsmin().isEmpty())
            stringBuilder.append(Symbol.admin + " *Asmin :* " + group.getAsmin() + "\n");
        stringBuilder.append(Symbol.womanHijab + " *PJH :* " + pjh + "\n" +
                Symbol.batasLapor + " *Batas Lapor :* " + group.getJamKholas() + " WIB \n\n" +
                Symbol.divider + "\n\n");

        for (int i = 0; i < memberList.size(); i++) {
            String no = "";
            Member member = memberList.get(i);
            if (member.getId() == 1) {
                no = Symbol.zero + Symbol.one;
            } else if (member.getId() == 2) {
                no = Symbol.zero + Symbol.two;
            } else if (member.getId() == 3) {
                no = Symbol.zero + Symbol.three;
            } else if (member.getId() == 4) {
                no = Symbol.zero + Symbol.four;
            } else if (member.getId() == 5) {
                no = Symbol.zero + Symbol.five;
            } else if (member.getId() == 6) {
                no = Symbol.zero + Symbol.six;
            } else if (member.getId() == 7) {
                no = Symbol.zero + Symbol.seven;
            } else if (member.getId() == 8) {
                no = Symbol.zero + Symbol.eight;
            } else if (member.getId() == 9) {
                no = Symbol.zero + Symbol.nine;
            } else if (member.getId() == 10) {
                no = Symbol.one + Symbol.zero;
            } else if (member.getId() == 11) {
                no = Symbol.one + Symbol.one;
            } else if (member.getId() == 12) {
                no = Symbol.one + Symbol.two;
            } else if (member.getId() == 13) {
                no = Symbol.one + Symbol.three;
            } else if (member.getId() == 14) {
                no = Symbol.one + Symbol.four;
            } else if (member.getId() == 15) {
                no = Symbol.one + Symbol.five;
            } else if (member.getId() == 16) {
                no = Symbol.one + Symbol.six;
            } else if (member.getId() == 17) {
                no = Symbol.one + Symbol.seven;
            } else if (member.getId() == 18) {
                no = Symbol.one + Symbol.eight;
            } else if (member.getId() == 19) {
                no = Symbol.one + Symbol.nine;
            } else if (member.getId() == 20) {
                no = Symbol.two + Symbol.zero;
            } else if (member.getId() == 21) {
                no = Symbol.two + Symbol.one;
            } else if (member.getId() == 22) {
                no = Symbol.two + Symbol.two;
            } else if (member.getId() == 23) {
                no = Symbol.two + Symbol.three;
            } else if (member.getId() == 24) {
                no = Symbol.two + Symbol.four;
            } else if (member.getId() == 25) {
                no = Symbol.two + Symbol.five;
            } else if (member.getId() == 26) {
                no = Symbol.two + Symbol.six;
            } else if (member.getId() == 27) {
                no = Symbol.two + Symbol.seven;
            } else if (member.getId() == 28) {
                no = Symbol.two + Symbol.eight;
            } else if (member.getId() == 29) {
                no = Symbol.two + Symbol.nine;
            } else if (member.getId() == 30) {
                no = Symbol.three + Symbol.zero;
            }

            if (member.getKholas().equals("b"))
                stringBuilder.append(no + " " + member.getName() + " ~ " + member.getJuz().replace("-", "") + " " + Symbol.recycle + "\n");
            else if (member.getKholas().equals("t"))
                stringBuilder.append(no + " " + member.getName() + " ~ " + member.getJuz().replace("-", "") + " " + Symbol.tandaSilang + "\n");
            else if (member.getKholas().equals("k"))
                stringBuilder.append(no + " " + member.getName() + " ~ " + member.getJuz().replace("-", "") + " " + Symbol.star + "\n");

            if (i == 9 || i == 19 || i == 29) {
                stringBuilder.append("\n " + Symbol.divider + "\n\n");
            }

        }

        stringBuilder.append("\n*UNTUK YANG HAID WAJIB AMBIL :*\n");
        stringBuilder.append(Symbol.tunjukKanan + "Opsi 1 : Baca terjemah " + Symbol.book + "\n" +
                Symbol.tunjukKanan + "Opsi 2 : Dengar murottal " + Symbol.cd + "\n\n");

        stringBuilder.append(rekapan.getIconKholas() + " : Kholas\n");
        if (!rekapan.getIconBelumKholas().isEmpty())
            stringBuilder.append(rekapan.getIconBelumKholas() + " : Belum Kholas\n");
        stringBuilder.append(rekapan.getIconKholasTelat() + " : Kholas Telat\n");
        stringBuilder.append(rekapan.getIconTidakKholas() + " : Tidak Kholas\n");
        stringBuilder.append(rekapan.getIconKholas1Juz() + " : Kholas 1 juz\n");
        stringBuilder.append(rekapan.getIconKholasLebih1Juz() + " : Kholas lebih dari 1 juz\n");
        stringBuilder.append(rekapan.getIconAlKahfi() + " : Al Kahfi (tiap Jum'at dihitung ½ juz kholas)\n");
        stringBuilder.append(Symbol.haid + " : Haid\n\n");

        stringBuilder.append(Symbol.dividerTotalKholas + "\n");
        stringBuilder.append("*Total kholas : " + group.getTotalKholas() + " dari " + group.getTotalMember() + " member*\n");
        stringBuilder.append(Symbol.dividerTotalKholas + "\n\n");

        stringBuilder.append(Symbol.divider + "\n\n");

        if (!rekapan.getSpritWords().isEmpty()) {
            stringBuilder.append(rekapan.getSpritWords() + "\n\n");
            stringBuilder.append(Symbol.divider + "\n\n");
        }

        stringBuilder.append("*Mohon dikoreksi apabila ada kesalahan*\n");
        stringBuilder.append(Symbol.sunFlower + " Kholas di awal waktu lebih utama " + Symbol.sunFlower + "\n\n");
        stringBuilder.append("*#salam" + Symbol.fiveHand + "lembar*\n");
        return stringBuilder.toString();
    }

    public static String getNextJuz(int juz, String ab) {
        int juzNew = juz;
        String abStr = "";
        if (juz == 30 && ab.equals("b")) {
            juzNew = 1;
            abStr = "a";
        } else {
            if (ab.equals("a")) {
                abStr = "b";
            } else if (ab.equals("b")) {
                abStr = "a";
                juzNew = juz + 1;
            } else if (ab.equals("ab")) {
                abStr = "a";
                juzNew = juz + 1;
            }
        }

        return juzNew + "-" + abStr;
    }

}
