package id.odojadmin.helper;

import java.util.List;

import id.odojadmin.model.Member;

public class RekapanHelper {
    public static String showRekap(String group, String day, String date, String admin, String asmin,
                                   String pjh, String batasLapor, List<Member> memberList) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bismillaahirrahmaanirrahiim.\n\n" +
                Symbol.book + " *LIST TILAWAH ODALF " + group + "* " + Symbol.book + "\n\n" +
                Symbol.calendar + " *" + day + ", " + date + "*\n" +
                Symbol.admin + " *Admin :* " + admin + "\n" +
                Symbol.admin + " *Asmin :* " + asmin + "\n" +
                Symbol.womanHijab + " *PJH :* " + pjh + "\n" +
                Symbol.batasLapor + " *Batas Lapor :* " + batasLapor + "WIB \n\n" +
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

            stringBuilder.append(no + " " + member.getName() + " ~ " + member.getJuz().replace("-", "") + "\n");

            if (i == 9 || i == 19 || i == 29) {
                stringBuilder.append("\n " + Symbol.divider + "\n\n");
            }
        }

        return stringBuilder.toString();
    }


}
