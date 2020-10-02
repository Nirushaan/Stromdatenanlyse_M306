package Main.Model;

import java.time.Instant;
import java.util.ArrayList;

public class Use {
    private String ID;
    private Instant Starttime;
    private Instant Endtime;
    private Integer updateTime;
    private ArrayList<Float> usearray;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Instant getStarttime() {
        return Starttime;
    }

    void setStarttime(Instant starttime) {
        Starttime = starttime;
    }

    public Instant getEndtime() {
        return Endtime;
    }

    void setEndtime(Instant endtime) {
        Endtime = endtime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }


    public ArrayList<Float> getUsearray() {
        return usearray;
    }

    void setUsearray(ArrayList<Float> usearray) {
        this.usearray = usearray;
    }
}
