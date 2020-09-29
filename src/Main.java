public class Main {
    public static void main(String[] args) {
        Reader_Esl eslreader = new Reader_Esl();
        eslreader.readAllFiles();
        Reader_Sdat sdatreader = new Reader_Sdat();
        sdatreader.readAllFiles();
    }
}
