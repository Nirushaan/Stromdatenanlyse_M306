import Model.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class DiagrammController{
    ArrayList<Absolute> absolutes = new ArrayList<>();
    ArrayList<Use> uses742 = new ArrayList<>();
    ArrayList<Use> uses735 = new ArrayList<>();
    ArrayList<Timeandpower> timeandpowers742 = new ArrayList<>();
    ArrayList<Timeandpower> timeandpowers735 = new ArrayList<>();
    String[] idlist = {"ID742","ID735"};
    public void initializeData(){
        Reader_Esl eslreader = new Reader_Esl();
        eslreader.readAllFiles();
        Reader_Sdat sdatreader = new Reader_Sdat();
        sdatreader.readAllFiles();
        ArrayList<Esl> eslarray = eslreader.getOutput();
        ArrayList<Sdat> sdatarray = sdatreader.getOutput();
        Combine_Esl_Sdat combine = new Combine_Esl_Sdat(eslarray, sdatarray);
        ArrayList<Absolute> absolutes = combine.getAbsolutelist();
        ArrayList<Use> uses742 = combine.getId742uselist();
        ArrayList<Use> uses735 = combine.getId735uselist();
        TurntoDayUse day = new TurntoDayUse();
        ArrayList<Use> newuses742 = day.turn(uses742);
        ArrayList<Timeandpower> timepower742 = day.getTimeandpowerslist();
        ArrayList<Use> newuses735 = day.turn(uses735);
        ArrayList<Timeandpower> timepower735 = day.getTimeandpowerslist();

        this.absolutes = absolutes;
        this.uses735 = uses735;
        this.uses742 = uses742;
        this.timeandpowers735 = timepower735;
        this.timeandpowers742 = timepower742;
    }

    public void zustanddiagramm(ActionEvent actionEvent) {
        zaehlerscene();
    }

    public void verbrauchsiagramm(ActionEvent actionEvent) {
        verbrauchscene(i);
    }

    private Scene zaehlerscene(){
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String,Number> bc =
                new LineChart<>(xAxis, yAxis);
        bc.setTitle("Absoluter Z?hlerstand");
        xAxis.setLabel("Z?hlerstand");
        yAxis.setLabel("Wert");
        for (String id:idlist
        ) {
            addSeriestoZaehler(bc,id);
        }
        return new Scene(bc,1750,800);
    }
    private void addSeriestoZaehler(LineChart<String,Number> bc, String id){
        XYChart.Series high = new XYChart.Series<>();
        high.setName(id + " Hochtarif");
        XYChart.Series low = new XYChart.Series<>();
        low.setName(id + " Niedertarif");
        for (Absolute a:absolutes) {
            if (a.getID().equals(id)){
                String date = a.getMonth() + "-" + a.getYear();
                high.getData().add(new XYChart.Data<>(date, a.getDaypower()));
                low.getData().add(new XYChart.Data<>(date, a.getNightpower()));
            }
        }
        bc.getData().addAll(high,low);
    }

    private Scene verbrauchscene(){
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String,Number> bc =
                new LineChart<>(xAxis, yAxis);
        bc.setTitle("Relativer Verbrauch");
        xAxis.setLabel("Verbrauch");
        yAxis.setLabel("Wert");
        for (String id:idlist
        ) {
            addSeriestoVerbrauch(bc,id);
        }
        return new Scene(bc,1750,800);
    }

    private void addSeriestoVerbrauch(LineChart<String,Number> bc, String id) {

        Use u742 = uses742.get(x);
        Use u735 = uses735.get(x);
        XYChart.Series use742 = new XYChart.Series<>();
        use742.setName("ID742 Verbrauchszahlen");
        XYChart.Series use735 = new XYChart.Series<>();
        use735.setName("ID735 Verbrauchszahlen");

        int i = 0;
        for (Float f:u742.getUsearray()) {
            Instant time = u742.getStarttime().plus(u742.getUpdateTime()*i, ChronoUnit.MINUTES);
            use742.getData().add(new XYChart.Data<>(time.toString(), f));
            i++;
        }
        i = 0;
        for (Float f:u735.getUsearray()) {
            Instant time = u735.getStarttime().plus(u735.getUpdateTime()*i,ChronoUnit.MINUTES);
            use735.getData().add(new XYChart.Data<>(time.toString(), f));
            i++;
        }

        bc.getData().addAll(use742,use735);
    }

}
