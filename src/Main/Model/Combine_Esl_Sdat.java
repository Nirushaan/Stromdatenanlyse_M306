package Main.Model;

import java.time.Instant;
import java.util.ArrayList;

public class Combine_Esl_Sdat {
    private ArrayList<Sdat> id742list = new ArrayList<>();
    private ArrayList<Sdat> id735list = new ArrayList<>();
    private ArrayList<Absolute> absolutelist = new ArrayList<>();
    private ArrayList<Use> id742uselist = new ArrayList<>();
    private ArrayList<Use> id735uselist = new ArrayList<>();
    public Combine_Esl_Sdat(ArrayList<Esl> esllist, ArrayList<Sdat> sdatlist) {
        splitDocuments(sdatlist);
        sortDocuments(id735list);
        sortDocuments(id742list);
        removeRedundance(id742list);
        removeRedundance(id735list);
        //Create Absolute Data
        for (Esl e:esllist) {
            String month = e.getTimePeriod().substring(5,7);
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
        for (Sdat s:id742list){
            Use use = new Use();
            use.setID("ID742");
            use.setStarttime(Instant.parse(s.getStartDateTime()));
            use.setEndtime(Instant.parse(s.getEndDateTime()));
            use.setUpdateTime(s.getResolution().getValue());
            ArrayList<Float> floats = new ArrayList<>();
            for (Observation o:s.getArray()){
                floats.add(o.getValue());
            }
            use.setUsearray(floats);
            id742uselist.add(use);
        }
        for (Sdat s:id735list){
            Use use = new Use();
            use.setID("ID735");
            use.setStarttime(Instant.parse(s.getStartDateTime()));
            use.setEndtime(Instant.parse(s.getEndDateTime()));
            use.setUpdateTime(s.getResolution().getValue());
            ArrayList<Float> floats = new ArrayList<>();
            for (Observation o:s.getArray()){
                floats.add(o.getValue());
            }
            use.setUsearray(floats);
            id735uselist.add(use);
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

    public ArrayList<Absolute> getAbsolutelist() {
        return absolutelist;
    }

    public void setAbsolutelist(ArrayList<Absolute> absolutelist) {
        this.absolutelist = absolutelist;
    }

    public ArrayList<Use> getId742uselist() {
        return id742uselist;
    }

    public void setId742uselist(ArrayList<Use> id742uselist) {
        this.id742uselist = id742uselist;
    }

    public ArrayList<Use> getId735uselist() {
        return id735uselist;
    }

    public void setId735uselist(ArrayList<Use> id735uselist) {
        this.id735uselist = id735uselist;
    }
}
