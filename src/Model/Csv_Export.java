package Model;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;

public class Csv_Export {
    public void writeDataLineByLine(String filePath,ArrayList<Timeandpower> timeandpowerlist, ArrayList<Absolute> absolutes, String id)
    {
        // first create file object for file placed at location
        // specified by filepath
        File file = new File(filePath);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = { "timestamp", "value" };
            writer.writeNext(header);
            Integer absoluteposition = 0;
            // add data to csv
            ArrayList<String[]> stringstowrite = new ArrayList<>();

                Float totalpower = absolutes.get(absoluteposition).getTotalpower();
                String month = absolutes.get(absoluteposition).getMonth();
                String year = absolutes.get(absoluteposition).getYear();
                String instantstring = year + "-" + month + "-01T00:00:00Z";
                Instant instant = Instant.parse(instantstring);
                //get forward values
                Timeandpower propertime = new Timeandpower();
                    for (Timeandpower t : timeandpowerlist
                    ) {
                        if (isSameMinuteUsingInstant(t.getTime(), instant)) {
                            propertime = t;
                            break;
                        }
                    }
                    Integer position = timeandpowerlist.indexOf(propertime);
                for (; position < timeandpowerlist.size();position++) {
                    instant = instant.plus(15,ChronoUnit.MINUTES);
                    totalpower = totalpower + timeandpowerlist.get(position).getPower();
                }
                position--;
                for (; position >= 0 ; position--) {
                    instant = instant.minus(15,ChronoUnit.MINUTES);
                    String utctime = toStringUnixTime(instant);

                    String absolutevalue = timeandpowerlist.get(position).getPower().toString();
                    String[] stringtowrite = {utctime,absolutevalue};
                    stringstowrite.add(stringtowrite);
                }
                System.out.println(instant);

            Collections.reverse(stringstowrite);
            for (String[] s:stringstowrite
                 ) {
                writer.writeNext(s);
            }


            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static boolean isSameMinuteUsingInstant (Instant date1, Instant date2){
        Instant instant1 = date1.truncatedTo(ChronoUnit.MINUTES);
        Instant instant2 = date2.truncatedTo(ChronoUnit.MINUTES);
        return instant1.equals(instant2);
    }
    private boolean containsDate(ArrayList<Timeandpower> list, Instant datetofind){
        return list.stream().map(Timeandpower::getTime).anyMatch(datetofind::equals);
    }

    public static String toStringUnixTime(Instant i){
        BigDecimal nanos = BigDecimal.valueOf(i.getNano(), 9);
        BigDecimal seconds = BigDecimal.valueOf(i.getEpochSecond());
        BigDecimal total = seconds.add(nanos);
        DecimalFormat df = new DecimalFormat("#.#########");
        return df.format(total);
    }
}
