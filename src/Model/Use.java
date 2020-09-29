package Model;

import java.util.ArrayList;

public class Use {
    private String ID;
    private String Starttime;
    private String Endtime;
    private Integer updateTime;
    private ArrayList<Float> usearray;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getStarttime() {
        return Starttime;
    }

    public void setStarttime(String starttime) {
        Starttime = starttime;
    }

    public String getEndtime() {
        return Endtime;
    }

    public void setEndtime(String endtime) {
        Endtime = endtime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public ArrayList<Float> getUsearray() {
        return usearray;
    }

    public void setUsearray(ArrayList<Float> usearray) {
        this.usearray = usearray;
    }
}
