package Model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class TurntoDayUse {

    public ArrayList<Use> turn(ArrayList<Use> uses) {
        ArrayList<Use> dayUses = new ArrayList<>();
        Instant startdate = uses.get(0).getStarttime();
        Instant enddate = uses.get(0).getEndtime();
        Use giantuse = new Use();
        giantuse.setStarttime(startdate);
        giantuse.setID(uses.get(0).getID());
        ArrayList<Float> floats = new ArrayList<>();
        Use latestuse = uses.get(0);
        while (containsDate(uses, enddate)) {
            floats.addAll(latestuse.getUsearray());
            for (Use u : uses
            ) {
                if (isSameDayUsingInstant(u.getStarttime(), enddate)) {
                    latestuse = u;
                    enddate = latestuse.getEndtime();
                    break;
                }
            }
        }
        floats.addAll(latestuse.getUsearray());
        giantuse.setEndtime(enddate);
        giantuse.setUsearray(floats);
        giantuse.setUpdateTime(uses.get(0).getUpdateTime());
        System.out.println(giantuse.getStarttime());
        System.out.println(giantuse.getEndtime());
        System.out.println(giantuse.getUsearray().size());
        startdate = giantuse.getStarttime();
        Instant tempenddate = giantuse.getStarttime();
        while (isSameMinuteUsingInstant(enddate, tempenddate)){
            int i = 0;
            while (isSameDayUsingInstant(startdate, tempenddate) && isSameMinuteUsingInstant(enddate, tempenddate)) {
                tempenddate = tempenddate.plus(15, ChronoUnit.MINUTES);
                i++;
            }

            Use newuse = new Use();
            newuse.setStarttime(startdate);
            newuse.setID(giantuse.getID());
            newuse.setEndtime(tempenddate);

            ArrayList<Float> newfloatarray = new ArrayList<>();
            for (int j = 0; j < i; j++) {

                newfloatarray.add(giantuse.getUsearray().get(0));
                giantuse.getUsearray().remove(0);

            }
            newuse.setUsearray(newfloatarray);
            newuse.setUpdateTime(giantuse.getUpdateTime());
            startdate = tempenddate;
            dayUses.add(newuse);
        }
        return dayUses;
    }

    private static boolean isSameDayUsingInstant (Instant date1, Instant date2){
        Instant instant1 = date1.truncatedTo(ChronoUnit.DAYS);
        Instant instant2 = date2.truncatedTo(ChronoUnit.DAYS);
        return instant1.equals(instant2);
    }
    private static boolean isSameMinuteUsingInstant (Instant date1, Instant date2){
        Instant instant1 = date1.truncatedTo(ChronoUnit.MINUTES);
        Instant instant2 = date2.truncatedTo(ChronoUnit.MINUTES);
        return !instant1.equals(instant2);
    }
    private boolean containsDate(ArrayList<Use> list, Instant datetofind){
        return list.stream().map(Use::getStarttime).anyMatch(datetofind::equals);
    }

}
