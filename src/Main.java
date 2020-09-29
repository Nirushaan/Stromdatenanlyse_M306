import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Reader_Esl eslreader = new Reader_Esl();
        eslreader.readAllFiles();
        Reader_Sdat sdatreader = new Reader_Sdat();
        sdatreader.readAllFiles();
        ArrayList<Esl> eslarray = eslreader.getOutput();
        ArrayList<Sdat> sdatarray = sdatreader.getOutput();
        for (Sdat sdat:sdatarray) {
            System.out.println(sdat.getStartDateTime());
            System.out.println(sdat.getEndDateTime());
        }
    }
}
