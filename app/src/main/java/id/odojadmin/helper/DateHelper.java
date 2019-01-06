package id.odojadmin.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
    public static String getCurrentDate() {
        String dayId = "", month = "";
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMMM yyyy");
        Date now = new Date();
        String strDate = sdfDate.format(now);

        String s[] = strDate.split(" ");
        String date = s[0];
        String year = s[2];

        String m = s[1];

        switch (m) {
            case "January":
                month = "Januari";
                break;
            case "February":
                month = "Februari";
                break;
            case "March":
                month = "Maret";
                break;
            case "April":
                month = "April";
                break;
            case "May":
                month = "Mei";
                break;
            case "June":
                month = "Juni";
                break;
            case "July":
                month = "Juli";
                break;
            case "August":
                month = "Agustus";
                break;
            case "September":
                month = "September";
                break;
            case "October":
                month = "Oktober";
                break;
            case "November":
                month = "November";
                break;
            case "December":
                month = "Desember";
                break;

        }

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                dayId = "Ahad";
                break;
            case Calendar.MONDAY:
                dayId = "Senin";
                break;
            case Calendar.TUESDAY:
                dayId = "Selasa";
                break;
            case Calendar.WEDNESDAY:
                dayId = "Rabu";
                break;
            case Calendar.THURSDAY:
                dayId = "Kamis";
                break;
            case Calendar.FRIDAY:
                dayId = "Jumat";
                break;
            case Calendar.SATURDAY:
                dayId = "Sabtu";
                break;
        }
        return dayId + ", " + date + " " + month + " " + year;
    }

    public static String getSimpleDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("ddMMyyyy");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public static String getSimpleDate2() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    private Date today() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        return cal.getTime();
    }

    private Date tomorrow() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +1);
        return cal.getTime();
    }

    public String getYesterdayDateString() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
        return sdfDate.format(yesterday());
    }

    public String getTodayDateString() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
        return sdfDate.format(today());
    }

    public String getTomorrowDateString() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
        return sdfDate.format(tomorrow());
    }
}
