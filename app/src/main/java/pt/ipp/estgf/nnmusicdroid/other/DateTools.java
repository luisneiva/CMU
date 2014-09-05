package pt.ipp.estgf.nnmusicdroid.other;

/**
 * Created by Luis Teixeira & Nuno Nunes
 */

/**
 * COMENTAR!!!
 */
public class DateTools {

    public static String getHoursFromMillis(long milliseconds) {
        return "" + (int) ((milliseconds / (1000 * 60 * 60)) % 24);
    }

    public static String getMinutesFromMillis(long milliseconds) {
        return "" + (int) ((milliseconds / (1000 * 60)) % 60);
    }

    public static String getSecoundsFromMillis(long milliseconds) {
        return "" + (int) ((milliseconds / 1000) % 60);
    }

    public static String format(long milliseconds) {
        String hours = getHoursFromMillis(milliseconds);

        if (hours.equals("0")) {
            return getMinutesFromMillis(milliseconds) + ":" + getSecoundsFromMillis(milliseconds);
        } else {
            return  hours + ":" + getMinutesFromMillis(milliseconds) + ":" + getSecoundsFromMillis(milliseconds);
        }
    }

}
