package Main;

import Main.Model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
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
    @FXML
    private Label warning;
    @FXML
    private Label warningexport;

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
        if (this.date.getValue() != null) {

            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(verbrauchscene());
            window.show();
        } else {
            warning.setText("Bitte ein Tag mit dem Daypicker wählen");
        }
    }

    private Scene zaehlerscene(){
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String,Number> bc =
                new LineChart<>(xAxis, yAxis);
        AnchorPane layout = new AnchorPane();
        Button zuruck = new Button("zurück");
        layout.getChildren().add(bc);
        AnchorPane.setLeftAnchor(zuruck, 0d); // distance 0 from right side of
        AnchorPane.setTopAnchor(zuruck, 0d);
        layout.getChildren().add(zuruck);



        bc.setTitle("Absoluter Zählerstand");
        xAxis.setLabel("Monat");
        yAxis.setLabel("Wert");
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e)
            {
                try {
                    zuruck(e);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
        zuruck.setOnAction(event);
        for (String id:idlist
        ) {
            addSeriestoZaehler(bc,id);
        }
        Scene zähler = new Scene(layout,550,430);


        return zähler;
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
        AnchorPane layout = new AnchorPane();
        Button zuruck = new Button("zurück");
        layout.getChildren().add(bc);
        AnchorPane.setLeftAnchor(zuruck, 0d); // distance 0 from right side of
        AnchorPane.setTopAnchor(zuruck, 0d);
        bc.setMinSize(1700, 1000);

        layout.getChildren().add(zuruck);


        bc.setTitle("Relativer Verbrauch");
        xAxis.setLabel("Verbrauch");
        yAxis.setLabel("Wert");
        addSeriestoVerbrauch(bc);

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e)
            {
                try {
                    zuruck(e);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
        zuruck.setOnAction(event);

        Scene zähler = new Scene(layout,1700,1000);

        return zähler;
    }

    private void addSeriestoVerbrauch(LineChart<String,Number> bc) {
        LocalDate localDate = this.date.getValue();
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

        XYChart.Series use742 = new XYChart.Series<>();
        use742.setName("ID742 Verbrauchszahlen");
        XYChart.Series use735 = new XYChart.Series<>();
        use735.setName("ID735 Verbrauchszahlen");

        createchartdata(chosenuse742, use742);
        createchartdata(chosenuse735, use735);

        bc.getData().addAll(use742,use735);
    }

    private void createchartdata(Use chosenuse, XYChart.Series use) {
        int i = 0;
        if (chosenuse.getID() != null) {
            for (Float f : chosenuse.getUsearray()) {
                Instant time = chosenuse.getStarttime().plus(chosenuse.getUpdateTime() * i, ChronoUnit.MINUTES);
                use.getData().add(new XYChart.Data<>(time.toString(), f));
                i++;
            }
        }
    }

    public void choosefolder(ActionEvent actionEvent){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Verzeichnis wählen");
        File defaultDirectory = new File(".");
        directoryChooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = directoryChooser.showDialog((Stage)((Node)actionEvent.getSource()).getScene().getWindow());
        export.setText(selectedDirectory.toString());
    }

    public void csvexport(ActionEvent actionEvent) {
        if(!export.getText().equals("")) {
                Prepare_export prepareexport = new Prepare_export();
                Csv_Export csv = new Csv_Export();
                String selectedDirectory = export.getText();
                csv.writeDataLineByLine(selectedDirectory + "\\ID742.csv", prepareexport.prepareforexport(timeandpowers742, absolutes));
                csv.writeDataLineByLine(selectedDirectory + "\\ID735.csv", prepareexport.prepareforexport(timeandpowers735, absolutes));
        } else {
            warningexport.setText("Bitte wählen sie ein Verzeichnis");
        }
    }
    public void jsonexport(ActionEvent actionEvent) {
        if(!export.getText().equals("")) {
                Prepare_export prepareexport = new Prepare_export();
                Json_Export json = new Json_Export();
                String selectedDirectory = export.getText();
                json.writeDataLineByLine(selectedDirectory, prepareexport.prepareforexport(timeandpowers742, absolutes), prepareexport.prepareforexport(timeandpowers735, absolutes));

    } else {
        warningexport.setText("Bitte wählen sie ein Verzeichnis");
    }
    }


    public void zuruck(ActionEvent actionEvent) throws IOException {
        Parent saveGUIparent = FXMLLoader.load(getClass().getResource("DiagrammGui.fxml"));
        Scene saveGUIscene = new Scene(saveGUIparent);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(saveGUIscene);
        window.show();

    }

    public static boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
        return true;
    }




}
