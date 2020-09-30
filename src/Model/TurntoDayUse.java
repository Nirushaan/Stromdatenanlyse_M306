package Model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class TurntoDayUse {

    public ArrayList<Use> turn(ArrayList<Use> uses){
        ArrayList<Use> dayUses = new ArrayList<>();
        Instant startdate = uses.get(0).getStarttime();
        Instant potentialenddate = uses.get(0).getStarttime();
        /*for (Use u:uses) {

            int i = 0;
                while (!(u.getEndtime() == potentialenddate)) {
                    while (isSameDayUsingInstant(startdate, potentialenddate)) {
                        potentialenddate = potentialenddate.plus(15, ChronoUnit.MINUTES);
                        i++;


                        System.out.println(potentialenddate);

                    }
                    System.out.println(potentialenddate);
                    Use newuse = new Use();

                    newuse.setStarttime(startdate);
                    newuse.setID(u.getID());
                    newuse.setUpdateTime(u.getUpdateTime());
                    newuse.setEndtime(potentialenddate);
                    System.out.println(potentialenddate);

                    ArrayList<Float> usearray = new ArrayList<>();
                    System.out.println(u.getUsearray());
                    for (int j = 0; j <= i; j++) {
                        System.out.println(j);
                        usearray.add(0, u.getUsearray().get(0));
                        System.out.println(j);
                        System.out.println(u.getUsearray().get(0));
                        u.getUsearray().remove(0);
                    }
                    System.out.println(potentialenddate);
                    newuse.setUsearray(usearray);
                    startdate = potentialenddate;
                    dayUses.add(newuse);
                    i = 0;
                }
        */
        }
        return dayUses;
    }

    private static boolean isSameDayUsingInstant(Instant date1, Instant date2) {
        Instant instant1 = date1.truncatedTo(ChronoUnit.DAYS);
        Instant instant2 = date2.truncatedTo(ChronoUnit.DAYS);
        return instant1.equals(instant2);
    }
}
