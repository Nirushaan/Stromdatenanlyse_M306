import java.util.ArrayList;

public class Sdat {
    private String documentID;
    private String startDateTime;
    private String endDateTime;
    private Resolution resolution;
    private ArrayList<Observation> array;

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Resolution getResolution() {
        return resolution;
    }

    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    public ArrayList<Observation> getArray() {
        return array;
    }

    public void setArray(ArrayList<Observation> array) {
        this.array = array;
    }
}
