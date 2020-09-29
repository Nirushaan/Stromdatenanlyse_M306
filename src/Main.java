import Model.*;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Reader_Esl eslreader = new Reader_Esl();
        eslreader.readAllFiles();
        Reader_Sdat sdatreader = new Reader_Sdat();
        sdatreader.readAllFiles();
        ArrayList<Esl> eslarray = eslreader.getOutput();
        ArrayList<Sdat> sdatarray = sdatreader.getOutput();
        Combine_Esl_Sdat combine = new Combine_Esl_Sdat(eslarray, sdatarray);
        ArrayList<Absolute> absolutes = combine.getAbsolutelist();
        ArrayList<Use> uses = combine.getUselist();
    }
}
