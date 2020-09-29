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
import java.util.EventListener;

class Reader_Sdat {
    private ArrayList<Sdat> output = new ArrayList<>();

    private void readFile(File f) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);
            Sdat sdat = new Sdat();
            //Get Document ID
            NodeList idlist = doc.getElementsByTagName("DocumentID");
            Node id = idlist.item(0);
            Element idelement = (Element) id;
            sdat.setDocumentID(idelement.getAttribute("text"));
            //Get Start- Enddate
            NodeList intervallist = doc.getElementsByTagName("Interval");
            Node interval = intervallist.item(0);
            Element intervalelement = (Element) interval;
            Element start = (Element) (intervalelement).getElementsByTagName("StartDateTime").item(0);
            Element end = (Element) (intervalelement).getElementsByTagName("EndDateTime").item(0);
            sdat.setStartDateTime(start.getAttribute("text"));
            sdat.setEndDateTime(end.getAttribute("text"));
            // Get Resolution value
            Resolution resolution = new Resolution();
            NodeList resolist = doc.getElementsByTagName("Resolution");
            Node resoNode = resolist.item(0);
            Element resoElement = (Element) resoNode;
            System.out.println(resoElement.getAttribute("text"));
            resolution.setValue(Integer.parseInt(resoElement.getAttribute("text")));
            resolution.setUnit(resoElement.getAttribute("text"));


        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        //Sorts Objects in array
    }




    void readAllFiles() {
        try {
            String[] fileNames =
                    Files.list(Paths.get(".\\bin\\SDAT-Files")).filter(
                            Files::isRegularFile).map(
                            p -> p.toFile().getName()).toArray(String[]::new);
            for (String s : fileNames) {
                File filepath = new File(".\\bin\\SDAT-Files\\" + s);
                readFile(filepath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
