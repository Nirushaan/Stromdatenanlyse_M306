package Main.Model;

import java.util.ArrayList;

public class Esl {
    private String timePeriod;
    private ArrayList<Esl_values> array;

    String getTimePeriod() {
        return timePeriod;
    }

    void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public ArrayList<Esl_values> getArray() {
        return array;
    }

    void setArray(ArrayList<Esl_values> array) {
        this.array = array;
    }
}
