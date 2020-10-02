package Main.Model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class TurntoDayUse {
    Use giantuse = new Use();


    ArrayList<Timeandpower> timeandpowerslist = new ArrayList<>();

    public ArrayList<Timeandpower> getTimeandpowerslist() {
        return timeandpowerslist;
    }

    public void setTimeandpowerslist(ArrayList<Timeandpower> timeandpowerslist) {
        this.timeandpowerslist = timeandpowerslist;
    }

    public ArrayList<Use> turn(ArrayList<Use> uses) {
        timeandpowerslist = new ArrayList<>();
        ArrayList<Use> dayUses = new ArrayList<>();
        Instant startdate = uses.get(0).getStarttime();
        Instant enddate = uses.get(0).getEndtime();
        giantuse.setStarttime(startdate);
        giantuse.setID(uses.get(0).getID());
        ArrayList<Float> floats = new ArrayList<>();
        Use latestuse = uses.get(0);
        while (CompareDates.containsDate(uses, enddate)) {
            floats.addAll(latestuse.getUsearray());
            for (Use u : uses
            ) {
                if (CompareDates.isSameDayUsingInstant(u.getStarttime(), enddate)) {
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
        startdate = giantuse.getStarttime();
        Instant tempenddate = giantuse.getStarttime();
        while (CompareDates.isSameMinuteUsingInstant(enddate, tempenddate)){
            ArrayList<Timeandpower> timeandpowers = new ArrayList<>();
            int i = 0;
            while (CompareDates.isSameDayUsingInstant(startdate, tempenddate) && CompareDates.isSameMinuteUsingInstant(enddate, tempenddate)) {
                Timeandpower timeandpower = new Timeandpower();
                timeandpower.setTime(tempenddate);
                timeandpowers.add(timeandpower);
                tempenddate = tempenddate.plus(15, ChronoUnit.MINUTES);
                i++;
            }


            Use newuse = new Use();
            newuse.setStarttime(startdate);
            newuse.setID(giantuse.getID());
            newuse.setEndtime(tempenddate);

            ArrayList<Float> newfloatarray = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                timeandpowers.get(j).setPower(giantuse.getUsearray().get(0));
                newfloatarray.add(giantuse.getUsearray().get(0));
                giantuse.getUsearray().remove(0);

            }
            for (Timeandpower t:timeandpowers
                 ) {
                timeandpowerslist.add(t);
            }
            newuse.setUsearray(newfloatarray);
            newuse.setUpdateTime(giantuse.getUpdateTime());
            startdate = tempenddate;
            dayUses.add(newuse);
        }
        return dayUses;
    }



}
