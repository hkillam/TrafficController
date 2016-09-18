/*
 * Source:  https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
 */
package trafficcontroller;

import java.io.*;
import java.text.SimpleDateFormat;

/**
 *
 * @author drago
 */
public class CSVReader {

    //
    // ReadWorkerStatsFile
    //
    // Reads the CSV file, and sends it to the DB.
    // Parameters:  filename - found as a field in the main form.
    // Returns an id for this dataset.  This ID will also be used to connect the building list.
    //
    public Long ReadWorkerStatsFile(String groupFile, String buildingsFile, long starttime, long populationid) {

        BufferedReader br = null, br2 = null;
        String line = "";
        String cvsSplitBy = ",";
        DataConnection trafficdata = new DataConnection();

        File file = new File(groupFile);
        Long timestamp = file.lastModified();   // use this to identify trials, and connect to results
        Long timeout = starttime + 300000;  // five minutes max
        Long currenttime = System.currentTimeMillis();

        // stall - netlogo needs time to run.  Watch to see new results files appear.
        while (timestamp < starttime && currenttime < timeout) {

            synchronized (this) {

                try {
                    Thread.sleep(1000);
                    //System.out.println("waiting...  file: " + timestamp+ " start: "+ starttime  + " current: " + currenttime + " timeout: " + timeout);
                } catch (Exception e) {
                }
            }
            file = new File(groupFile);
            timestamp = file.lastModified();
            currenttime = System.currentTimeMillis();
        }

        // check for timeout - files weren't created after 10 minutes
        if (timestamp < starttime) {
            return 0L;
        }
        long runtime = (timestamp - starttime) / 1000;

        long trialid = trafficdata.insertRow("traffictrials", "(timestamp,runtime,populationid)", timestamp.toString() + "," + runtime + "," + populationid);
        if (trialid == 0L) {
            // this data is alread imported
            return 0L;
        }

        try {

            int totaltrips = 0;

            br = new BufferedReader(new FileReader(groupFile));
            String columns = br.readLine();  // headers - could use this as the columns in the insert statement
            while ((line = br.readLine()) != null) {
                // put the entire row into the database
                columns = "(trialid,source_building,dest_building,number_avoiders,number_bold,avg_trips_avoiders,avg_trips_bold,avg_trip_time_avoider,avg_trip_time_bold,avg_bold_tired_ticks,injured_workers,total_trips,total_bold_trips,total_avoider_trips)";
                trafficdata.insertRow("groupstats", columns, trialid + "," + line);

                // grab the trip count
                String[] dataentry = line.split(cvsSplitBy);
                totaltrips += Integer.parseInt(dataentry[10]);
            }
            trafficdata.updateTotalTrips(trialid, totaltrips);

            // Buildings file
            br2 = new BufferedReader(new FileReader(buildingsFile));
            columns = br2.readLine();  // headers - could use this as the columns in the insert statement
            while ((line = br2.readLine()) != null) {
                // put the entire row into the database
                columns = "(trialid,facility_id,facility_name,xcor,ycor,width,height,num_workers,dest,slow_in_mud,tired_in_mud,injured_in_mud,min_time_working, max_time_working)";
                trafficdata.insertRow("buildings", columns, trialid + "," + line);
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

        return (trialid);
    }

    public void CreateBuildingsFile(TrialClass trial, String filename) throws IOException {
            FileWriter file = new FileWriter(filename, false);
            PrintWriter printer = new PrintWriter(file);
//facility,name,x,y,width,height,num workers,dest,slow in mud,tired in mud,risk danger,min time working,max time working,,notes
//14,long term laydown,34,115,25,15,375,1,1,1,0,17,20,,375
            String textline = "facility,name,x,y,width,height,num workers,dest,slow in mud,tired in mud,risk danger,min time working,max time working,,notes\n";
            printer.printf(textline);
            for (BuildingClass hut : trial.buildings) {
                int slow = 0;
                int tired = 0;
                int injured = 0;
                if (hut.slow) slow = 1;
                if (hut.tired) tired = 1;
                if (hut.injured) injured = 1;
                
                textline = hut.id + "," + hut.name + "," + hut.xcor + "," + hut.ycor + ",";
                textline += hut.width + "," + hut.height + "," + hut.numworkers + "," + hut.dest + ",";
                textline += slow + "," + tired + "," + injured + "," + hut.mintimeworking + ","+ hut.maxtimeworking;
                textline += ",,\n";
                printer.printf(textline);
            }
            printer.close();
    }
}
