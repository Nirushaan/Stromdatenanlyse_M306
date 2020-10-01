import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class DiagrammController{
    ArrayList<Absolute> absolutes = new ArrayList<>();
    ArrayList<Use> uses742 = new ArrayList<>();
    ArrayList<Use> uses735 = new ArrayList<>();
    ArrayList<Timeandpower> timeandpowers742 = new ArrayList<>();
    ArrayList<Timeandpower> timeandpowers735 = new ArrayList<>();
    String[] idlist = {"ID742","ID735"};
    @FXML
    private DatePicker date;
    @FXML
    private TextField export;

    public DiagrammController() {
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
        this.uses735 = newuses735;
        this.uses742 = newuses742;
        this.timeandpowers735 = timepower735;
        this.timeandpowers742 = timepower742;
    }

    public void zustanddiagramm(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(zaehlerscene());
        window.show();
    }

    public void verbrauchsiagramm(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(verbrauchscene());
        window.show();
    }

    private Scene zaehlerscene(){
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String,Number> bc =
                new LineChart<>(xAxis, yAxis);
        bc.setTitle("Absoluter Zahlerstand");
        xAxis.setLabel("Zahlerstand");
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
        addSeriestoVerbrauch(bc);
        return new Scene(bc,1750,800);
    }

    private void addSeriestoVerbrauch(LineChart<String,Number> bc) {

        LocalDate localDate = this.date.getValue();

        System.out.println(localDate);
        System.out.println(Instant.from(localDate.atStartOfDay(ZoneId.systemDefault())));
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        instant = instant.plus(1,ChronoUnit.DAYS);
        Use chosenuse742 = new Use();
        Use chosenuse735 = new Use();
        for (Use u:uses742
             ) {
            if (CompareDates.isSameDayUsingInstant(u.getStarttime(),instant)){
                chosenuse742 = u;
                break;
            }
        }
        for (Use u:uses735
             ) {
            if (CompareDates.isSameDayUsingInstant(u.getStarttime(),instant)){
                chosenuse735 = u;
                break;
            }
        }
        System.out.println(chosenuse735.getStarttime());
        System.out.println(localDate);
        XYChart.Series use742 = new XYChart.Series<>();
        use742.setName("ID742 Verbrauchszahlen");
        XYChart.Series use735 = new XYChart.Series<>();
        use735.setName("ID735 Verbrauchszahlen");
        System.out.println(localDate);
        int i = 0;
        for (Float f:chosenuse742.getUsearray()) {
            Instant time = chosenuse742.getStarttime().plus(chosenuse742.getUpdateTime()*i, ChronoUnit.MINUTES);
            use742.getData().add(new XYChart.Data<>(time.toString(), f));
            i++;
        }

        i = 0;
        for (Float f:chosenuse735.getUsearray()) {
            Instant time = chosenuse735.getStarttime().plus(chosenuse735.getUpdateTime()*i,ChronoUnit.MINUTES);
            use735.getData().add(new XYChart.Data<>(time.toString(), f));
            i++;
        }

        bc.getData().addAll(use742,use735);
        System.out.println(2);
    }

    public void choosefolder(ActionEvent actionEvent){

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("CSV-Datei Verzeichnis wählen");
        File defaultDirectory = new File(".");
        directoryChooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = directoryChooser.showDialog((Stage)((Node)actionEvent.getSource()).getScene().getWindow());
        export.setText(selectedDirectory.toString());
    }

    public void csvexport(ActionEvent actionEvent) {
        Csv_Export csv = new Csv_Export();
        String selectedDirectory = export.getText();
        csv.writeDataLineByLine(selectedDirectory + "\\ID742.csv",timeandpowers742,absolutes,"ID742");
        csv.writeDataLineByLine(selectedDirectory + "\\ID735.csv",timeandpowers735,absolutes,"ID735");
    }

}
