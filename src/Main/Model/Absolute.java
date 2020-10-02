package Main.Model;

public class Absolute {

    private String month;
    private String year;
    private String ID;
    private Float nightpower;
    private Float daypower;
    private Float totalpower;

    public Absolute(String month, String year, String ID, Float nightpower, Float daypower) {
        this.month = month;
        this.year = year;
        this.ID = ID;
        this.nightpower = nightpower;
        this.daypower = daypower;
        this.totalpower = daypower + nightpower;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Float getNightpower() {
        return nightpower;
    }

    public void setNightpower(Float nightpower) {
        this.nightpower = nightpower;
    }

    public Float getDaypower() {
        return daypower;
    }

    public void setDaypower(Float daypower) {
        this.daypower = daypower;
    }

    public Float getTotalpower(){return totalpower;}

}
