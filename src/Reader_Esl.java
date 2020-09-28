import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Reader_Esl {
    private Esl output;

    public Esl readFile(File f){
        return null;
    }

    public void readAllFiles(){
        try {
            String[] fileNames =
                    Files.list(Paths.get("/bin/ESL-Files")).filter(
                            Files::isRegularFile).map(
                            p -> p.toFile().getName()).toArray(String[]::new);
            for (String s : fileNames) {
                System.out.println(s);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Esl getOutput() {
        return output;
    }

    public void setOutput(Esl output) {
        this.output = output;
    }
}
