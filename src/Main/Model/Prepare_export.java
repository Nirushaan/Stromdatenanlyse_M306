package Main.Model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;

public class Prepare_export {

    public ArrayList<String[]> prepareforexport(ArrayList<Timeandpower> timeandpowerlist, ArrayList<Absolute> absolutes) {
        Integer absoluteposition = 0;
        // add data to csv
        ArrayList<String[]> stringstowrite = new ArrayList<>();

        Float totalpower = absolutes.get(absoluteposition).getTotalpower();
        String month = absolutes.get(absoluteposition).getMonth();
        String year = absolutes.get(absoluteposition).getYear();
        String instantstring = year + "-" + month + "-01T00:00:00Z";
        Instant instant = Instant.parse(instantstring);
        //get forward values
        Timeandpower propertime = new Timeandpower();
        for (Timeandpower t : timeandpowerlist
        ) {
            if (CompareDates.isSameMinuteUsingInstant(t.getTime(), instant)) {
                propertime = t;
                break;
            }
        }
        Integer position = timeandpowerlist.indexOf(propertime);
        for (; position < timeandpowerlist.size(); position++) {
            instant = instant.plus(15, ChronoUnit.MINUTES);
            totalpower = totalpower + timeandpowerlist.get(position).getPower();
        }
        position--;
        for (; position >= 0; position--) {
            instant = instant.minus(15, ChronoUnit.MINUTES);
            String utctime = CompareDates.toStringUnixTime(timeandpowerlist.get(position).getTime());
            String absolutevalue = timeandpowerlist.get(position).getPower().toString();
            String[] stringtowrite = {utctime, absolutevalue};
            stringstowrite.add(stringtowrite);
        }

        Collections.reverse(stringstowrite);
        return stringstowrite;
    }
}
