import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Reader_Esl {
    private Esl output;

    private Esl readFile(File f){
        try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(f);
        Element rootElement = doc.getDocumentElement();
        System.out.println(rootElement.getNodeName());
        NodeList TimeList = doc.getElementsByTagName("TimePeriod");
        for (int i = 0; i < TimeList.getLength(); i++){
            Node nodetime = TimeList.item(i);
            Esl esl = new Esl();
            if (nodetime.getNodeType() == Node.ELEMENT_NODE){
                Element e = (Element) nodetime;
                esl.setTimePeriod(e.getAttribute("end"));
                System.out.println(esl.getTimePeriod());
            }

        }
        NodeList ValueList = doc.getElementsByTagName("ValueRow");
        System.out.println(ValueList.getLength());
        for (int i = 0; i < ValueList.getLength(); i++) {
            Node node = ValueList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) node;
                if (e.getAttribute("obis").equals("1-1:1.8.1") ||
                        e.getAttribute("obis").equals("1-1:1.8.2") ||
                        e.getAttribute("obis").equals("1-1:2.8.1") ||
                        e.getAttribute("obis").equals("1-1:2.8.2")) {

                    System.out.println(e.getAttribute("value"));
                }
            }

        }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return null;
    }


    void readAllFiles(){
        try {
            String[] fileNames =
                    Files.list(Paths.get("C:\\Users\\Hunter\\IdeaProjects\\Bunzlienergie\\bin\\ESL-Files")).filter(
                            Files::isRegularFile).map(
                            p -> p.toFile().getName()).toArray(String[]::new);
            for (String s : fileNames) {
                File filepath = new File("C:\\Users\\Hunter\\IdeaProjects\\Bunzlienergie\\bin\\ESL-Files\\" + s);
                readFile(filepath);
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
