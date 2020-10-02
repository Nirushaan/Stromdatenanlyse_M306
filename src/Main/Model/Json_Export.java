package Main.Model;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Json_Export {
    public void writeDataLineByLine(String filePath, ArrayList<String[]> stringstowrite742, ArrayList<String[]> stringstowrite735)
    {
            JSONArray sensorarray = new JSONArray();

            sensorarray.add(createJSONObject(stringstowrite742,"ID742"));
            sensorarray.add(createJSONObject(stringstowrite735,"ID735"));

            try (FileWriter fileWriter = new FileWriter(new File (filePath,"ID742_ID735.json"))){
                String prettystring = sensorarray.toJSONString();
                fileWriter.write(prettystring);
                fileWriter.flush();

            } catch (IOException e){
                e.printStackTrace();
            }
    }
    private JSONObject createJSONObject(ArrayList<String[]> strings,String id){
        JSONObject sensor = new JSONObject();
        JSONObject bigdata = new JSONObject();
        JSONArray dataarray = new JSONArray();
        for (String[] s:strings
        ) {
            JSONObject data = new JSONObject();
            data.put("value",Float.parseFloat(s[1]));
            data.put("ts",s[0]);
            dataarray.add(data);
        }
        bigdata.put("data",dataarray);
        sensor.put("data",dataarray);
        sensor.put("sensorID",id);
        System.out.println("finished " + id);
        return sensor;
    }

}
