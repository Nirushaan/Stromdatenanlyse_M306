import java.util.ArrayList;
import java.util.regex.Pattern;

public class Combine_Esl_Sdat {
    private ArrayList<Esl> esllist;
    private ArrayList<Sdat> sdatlist;
    private ArrayList<Sdat> id742list = new ArrayList<>();
    private ArrayList<Sdat> id735list = new ArrayList<>();
    public Combine_Esl_Sdat(ArrayList<Esl> esllist,ArrayList<Sdat> sdatlist) {
        this.esllist = esllist;
        this.sdatlist = sdatlist;
        splitDocuments(this.sdatlist);
        sortDocuments(id735list);
        sortDocuments(id742list);
        removeRedundance(id742list);
        removeRedundance(id735list);
        for (Sdat s:id742list
             ) {
            System.out.println(s.getStartDateTime());
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
