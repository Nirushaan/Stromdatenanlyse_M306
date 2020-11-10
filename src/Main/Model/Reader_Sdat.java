package Main.Model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Reader_Sdat {
    private ArrayList<Sdat> output = new ArrayList<>();

    private void readFile(File f) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);
            Sdat sdat = new Sdat();
            //Get Document ID
            NodeList idlist = doc.getElementsByTagName("rsm:DocumentID");
            Node id = idlist.item(0);
            Element idelement = (Element) id;
            sdat.setDocumentID(idelement.getTextContent());
            //Get Start- Enddate
            NodeList intervallist = doc.getElementsByTagName("rsm:Interval");
            Node interval = intervallist.item(0);
            Element intervalelement = (Element) interval;
            Element start = (Element) (intervalelement).getElementsByTagName("rsm:StartDateTime").item(0);
            Element end = (Element) (intervalelement).getElementsByTagName("rsm:EndDateTime").item(0);
            sdat.setStartDateTime(start.getTextContent());
            sdat.setEndDateTime(end.getTextContent());
            // Get Resolution value + unit
            Resolution resolution = new Resolution();
            NodeList resolist = doc.getElementsByTagName("rsm:Resolution");
            Node resoNode = resolist.item(0);
            Element resoElement = (Element) resoNode;
            String resoString = resoElement.getTextContent();
            String numbers = resoString.replaceAll("\\D+","");
            resolution.setValue(Integer.parseInt(numbers));
            String unit = resoString.replaceAll("[^A-Za-z]+","");
            resolution.setUnit(unit);
            sdat.setResolution(resolution);
            // Get Observation
            ArrayList<Observation> list = new ArrayList<>();
            NodeList oblist = doc.getElementsByTagName("rsm:Observation");
            for (int i = 0; i < oblist.getLength(); i++){
                Observation observation = new Observation();
                Node nodeob = oblist.item(i);
                Node Volume = nodeob.getFirstChild().getNextSibling();
                Node Position = nodeob.getFirstChild();
                observation.setValue(Float.parseFloat(Volume.getTextContent()));
                observation.setSequence(Integer.parseInt(Position.getFirstChild().getTextContent()));
                list.add(observation);
            }
            sdat.setArray(list);
            output.add(sdat);

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }




    public void readAllFiles() {
        try {
            String[] fileNames =
                    Files.list(Paths.get("resources\\SDAT-Files")).filter(
                            Files::isRegularFile).map(
                            p -> p.toFile().getName()).toArray(String[]::new);
            for (String s : fileNames) {
                File filepath = new File("resources\\SDAT-Files\\" + s);
                readFile(filepath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Sdat> getOutput() {
        return output;
    }

    public void setOutput(ArrayList<Sdat> output) {
        this.output = output;
    }
}
