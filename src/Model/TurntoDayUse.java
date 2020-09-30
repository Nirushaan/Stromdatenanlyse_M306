package Model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class TurntoDayUse {

    public ArrayList<Use> turn(ArrayList<Use> uses){
        ArrayList<Use> dayUses = new ArrayList<>();
        for (Use u:uses) {
                Instant startdate = u.getStarttime();
                Instant potentialenddate = startdate.plus(15,ChronoUnit.MINUTES);
                while (!isSameDayUsingInstant(u.getEndtime(), potentialenddate)) {
                    int i=0;


                    while (isSameDayUsingInstant(startdate, potentialenddate)) {
                        potentialenddate = potentialenddate.plus(15, ChronoUnit.MINUTES);
                        i++;
                    }
                    Use newuse = new Use();
                    newuse.setStarttime(startdate);
                    newuse.setID(u.getID());
                    newuse.setUpdateTime(u.getUpdateTime());
                    newuse.setEndtime(potentialenddate);
                    ArrayList<Float> usearray = new ArrayList<>();
                    for (i = i; i >= 0; i--) {
                        usearray.add(0, u.getUsearray().get(i));
                        u.getUsearray().remove(i);
                    }
                    newuse.setUsearray(usearray);
                    startdate = potentialenddate;
                    dayUses.add(newuse);
                }

        }
        return dayUses;
    }

    private static boolean isSameDayUsingInstant(Instant date1, Instant date2) {
        Instant instant1 = date1.truncatedTo(ChronoUnit.DAYS);
        Instant instant2 = date2.truncatedTo(ChronoUnit.DAYS);
        return instant1.equals(instant2);
    }
}
