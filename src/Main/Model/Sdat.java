package Main.Model;

import java.util.ArrayList;

public class Sdat {
    private String documentID;
    private String startDateTime;
    private String endDateTime;
    private Resolution resolution;
    private ArrayList<Observation> array;

    String getDocumentID() {
        return documentID;
    }

    void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    String getStartDateTime() {
        return startDateTime;
    }

    void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    void setEndDateTime(String endDateTime) {
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

    void setArray(ArrayList<Observation> array) {
        this.array = array;
    }
}
