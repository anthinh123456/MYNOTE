package code.android.ngocthai.mynote.Common.Utils;

import java.util.Calendar;

/**
 * Created by Thaihn on 11/10/2016.
 */
public class Utils {

    /**
     * Get current date to show list to do
     *
     * @return
     */
    public static String getCurrentDate() {
        String result = "";
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DAY_OF_MONTH);
        result = date + "-" + month + "-" + year;
        return result;
    }
}
