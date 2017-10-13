package knab.com.smaug.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by hp on 2017-10-03.
 */

public class DateUtils {
    private static final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

    public static Date now() {
        return calendar.getTime();
    }
}
