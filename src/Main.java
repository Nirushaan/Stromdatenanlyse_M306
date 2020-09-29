public class Main {
    eslArray
    public static void main(String[] args) {
        Reader_Esl eslreader = new Reader_Esl();
        eslreader.readAllFiles();
        for (int i = 0; i < eslreader.getOutput().size(); i++){
            System.out.println(eslreader.getOutput().get(i).getTimePeriod());
        }
    }
}
