package Main.Model;

import Main.Model.Use;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class CompareDates {
    public static boolean isSameDayUsingInstant (Instant date1, Instant date2){
        Instant instant1 = date1.truncatedTo(ChronoUnit.DAYS);
        Instant instant2 = date2.truncatedTo(ChronoUnit.DAYS);
        return instant1.equals(instant2);
    }
    public static boolean isSameMinuteUsingInstant (Instant date1, Instant date2){
        Instant instant1 = date1.truncatedTo(ChronoUnit.MINUTES);
        Instant instant2 = date2.truncatedTo(ChronoUnit.MINUTES);
        return !instant1.equals(instant2);
    }
    public static boolean containsDate(ArrayList<Use> list, Instant datetofind){
        return list.stream().map(Use::getStarttime).anyMatch(datetofind::equals);
    }
    public static String toStringUnixTime(Instant i){
        BigDecimal nanos = BigDecimal.valueOf(i.getNano(), 9);
        BigDecimal seconds = BigDecimal.valueOf(i.getEpochSecond());
        BigDecimal total = seconds.add(nanos);
        DecimalFormat df = new DecimalFormat("#.#########");
        return df.format(total);
    }
}
