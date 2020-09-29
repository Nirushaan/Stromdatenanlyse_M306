package Model;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Combine_Esl_Sdat {
    private ArrayList<Esl> esllist;
    private ArrayList<Sdat> sdatlist;
    private ArrayList<Sdat> id742list = new ArrayList<>();
    private ArrayList<Sdat> id735list = new ArrayList<>();
    ArrayList<Absolute> absolutelist = new ArrayList<>();
    ArrayList<Use> uselist = new ArrayList<>();
    public Combine_Esl_Sdat(ArrayList<Esl> esllist,ArrayList<Sdat> sdatlist) {
        this.esllist = esllist;
        this.sdatlist = sdatlist;
        splitDocuments(this.sdatlist);
        sortDocuments(id735list);
        sortDocuments(id742list);
        removeRedundance(id742list);
        removeRedundance(id735list);
        //Create Absolute Data
        for (Esl e:esllist) {
            String month = e.getTimePeriod().substring(6,7);
            String year = e.getTimePeriod().substring(0,4);
            Float daypower1 = e.getArray().get(0).getValue();
            Float nightpower1 = e.getArray().get(1).getValue();
            Absolute absolute1 = new Absolute(month,year,"ID742",nightpower1,daypower1);
            Float daypower2 = e.getArray().get(2).getValue();
            Float nightpower2 = e.getArray().get(3).getValue();
            Absolute absolute2 = new Absolute(month,year,"ID735",nightpower2,daypower2);
            absolutelist.add(absolute1);
            absolutelist.add(absolute2);
        }
        //Create Use Data
        for (Sdat s:sdatlist){

        }

    }

    private void splitDocuments(ArrayList<Sdat> list){
        for (Sdat s:list
             ) {
            if(s.getDocumentID().endsWith("ID742")){
                id742list.add(s);
            } else
                id735list.add(s);
        }
    }
    private void sortDocuments(ArrayList<Sdat> list){
        list.sort((o1, o2) -> o1.getStartDateTime().compareToIgnoreCase(o2.getStartDateTime()));
    }
    private void removeRedundance(ArrayList<Sdat> list){
        //Removes duplicate start Dates
        for(int i = 0; i < list.size()-1; i++){
            if (list.get(i).getStartDateTime().equals(list.get(i + 1).getStartDateTime())){
                list.remove(i+1);
                i--;
            }
        }
    }

}
