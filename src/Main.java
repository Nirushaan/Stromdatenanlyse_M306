import Model.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) {
        int i = 1;
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


        String[] idlist = {"ID742","ID735"};
        primaryStage.setTitle("Datenleser");
        Button zahl = new Button("Z�hlerstand");
        zahl.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(zaehlerscene(absolutes,idlist));
            }
        });
        TextField daynumber = new TextField();
        daynumber.setPromptText("Verbrauchstagzahl Eingeben");

        Button verbrauch = new Button("Verbrauchzahlen");
        verbrauch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int x = Integer.parseInt(daynumber.getText());

             primaryStage.setScene(verbrauchscene(newuses742,newuses735,idlist,x));
            }
        });
        Button csv = new Button("Export Dateien zu CSV");
        csv.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Csv_Export csv = new Csv_Export();
                csv.writeDataLineByLine(".\\bin\\CSV-Files\\ID742.csv",timepower742,absolutes,"ID742");
                System.out.println("finished1");
                csv = new Csv_Export();
                csv.writeDataLineByLine(".\\bin\\CSV-Files\\ID735.csv",timepower735,absolutes,"ID735");
                System.out.println("finished2");
            }
        });
        VBox pane = new VBox();
        pane.getChildren().addAll(zahl,daynumber,verbrauch,csv);
        Scene primaryscene = new Scene(pane);
        primaryStage.setScene(primaryscene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
    private Scene zaehlerscene(ArrayList<Absolute> absolutes,String[] idlist){
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String,Number> bc =
                new LineChart<>(xAxis, yAxis);
        bc.setTitle("Absoluter Z�hlerstand");
        xAxis.setLabel("Z�hlerstand");
        yAxis.setLabel("Wert");
        for (String id:idlist
             ) {
            addSeriestoZaehler(bc,absolutes,id);
        }
        return new Scene(bc,1750,800);
    }
    private void addSeriestoZaehler(LineChart<String,Number> bc, ArrayList<Absolute> absolutes, String id){
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
    private Scene verbrauchscene(ArrayList<Use> uses742,ArrayList<Use> uses735,String[] idlist,int x){
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String,Number> bc =
                new LineChart<>(xAxis, yAxis);
        bc.setTitle("Relativer Verbrauch");
        xAxis.setLabel("Verbrauch");
        yAxis.setLabel("Wert");
        for (String id:idlist
        ) {
            addSeriestoVerbrauch(bc,uses742,uses735,id,x);
        }
        return new Scene(bc,1750,800);
    }
    private void addSeriestoVerbrauch(LineChart<String,Number> bc,ArrayList<Use> uses742,ArrayList<Use> uses735, String id, int x) {

        Use u742 = uses742.get(x);
        Use u735 = uses735.get(x);
        XYChart.Series use742 = new XYChart.Series<>();
        use742.setName("ID742 Verbrauchszahlen");
        XYChart.Series use735 = new XYChart.Series<>();
        use735.setName("ID735 Verbrauchszahlen");

        int i = 0;
        for (Float f:u742.getUsearray()) {
            Instant time = u742.getStarttime().plus(u742.getUpdateTime()*i,ChronoUnit.MINUTES);
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
