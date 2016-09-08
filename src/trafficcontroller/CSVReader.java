/*
 * Source:  https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
 */
package trafficcontroller;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author drago
 */
public class CSVReader {
    private static String filename;
    
    public static void CSVReader(String results) {
        filename = results;
    }
    
    public void ReadFile(String filename) {

        String csvFile = "C:\\WorksiteTrafficResults\\results-groupstats.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] dataentry = line.split(cvsSplitBy);

                System.out.println("Sample values " + dataentry[4] + " Sample values=" + dataentry[5]);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
