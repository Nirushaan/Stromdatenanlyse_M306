package Main.Model;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Reader_Esl {
    private ArrayList<Esl> output = new ArrayList<>();

    private void readFile(File f){
        try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(f);
        NodeList TimeList = doc.getElementsByTagName("TimePeriod");
        for (int i = 0; i < TimeList.getLength(); i++){
            Node nodetime = TimeList.item(i);
            Esl esl = new Esl();
            if (nodetime.getNodeType() == Node.ELEMENT_NODE){
                Element e = (Element) nodetime; //Timeperiod
                esl.setTimePeriod(e.getAttribute("end"));
                NodeList ValueList = doc.getElementsByTagName("ValueRow");
                ArrayList<Esl_values> list = new ArrayList<>();
                for (int j = 0; j < ValueList.getLength(); j++) {
                    Node node = ValueList.item(j);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element ee = (Element) node; //ValueRow
                        Element eee = (Element) node.getParentNode();
                        if (    eee.getAttribute("end").equals(e.getAttribute("end")) &&
                                (       ee.getAttribute("obis").equals("1-1:1.8.1") ||
                                        ee.getAttribute("obis").equals("1-1:1.8.2") ||
                                        ee.getAttribute("obis").equals("1-1:2.8.1") ||
                                        ee.getAttribute("obis").equals("1-1:2.8.2"))) {
                                    Esl_values values = new Esl_values();
                                    values.setObis(ee.getAttribute("obis"));
                                    values.setValue(Float.parseFloat(ee.getAttribute("value")));
                                    list.add(values);
                        }
                    }
                }
                if (list.size() > 0){
                    esl.setArray(list);
                }
            }
            output.add(esl);
        }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        //Sorts Objects in array
        output.sort((o1, o2) -> o1.getTimePeriod().compareToIgnoreCase(o2.getTimePeriod()));
        //Removes duplicates
        for(int i = 0; i < output.size()-1; i++){
            if (output.get(i).getTimePeriod().equals(output.get(i + 1).getTimePeriod())){
                output.remove(i+1);
                i--;
            }
        }
    }


    public void readAllFiles(){
        try {
            String[] fileNames =
                    Files.list(Paths.get("resources\\ESL-Files")).filter(
                            Files::isRegularFile).map(
                            p -> p.toFile().getName()).toArray(String[]::new);
            for (String s : fileNames) {
                File filepath = new File("resources\\ESL-Files\\" + s);
                readFile(filepath);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Esl> getOutput() {
        return output;
    }

    public void setOutput(ArrayList<Esl> output) {
        this.output = output;
    }
}
