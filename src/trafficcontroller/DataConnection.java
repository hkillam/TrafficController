/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficcontroller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author drago
 */
public class DataConnection {

    static String url = "jdbc:mysql://localhost:3306/worksite?autoReconnect=true&useSSL=false";
    static String user = "root";
    static String password = "Libelul@";
    static int populationID = 0;  // ID of most recently loaded population
        
    public long insertRow(String table, String fields, String values) {

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;


        long retval = 0;

        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            String query = "INSERT INTO " + table + " " + fields + " VALUES (" + values + " )";
            st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            rs = st.getGeneratedKeys();
            if (rs.next()) {
                retval = rs.getLong(1);
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DataConnection.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return 0L;
        } finally {

            try {

                if (rs != null) {
                    rs.close();
                }

                if (st != null) {
                    st.close();
                }

                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {

                Logger lgr = Logger.getLogger(DataConnection.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return (retval);
    }





    public List<BuildingClass> getBuildings(Integer trialid) {
        List<BuildingClass> buildings = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            String query = "SELECT * FROM worksite.buildings where trialid =" + trialid;
            rs = st.executeQuery(query);
            while (rs.next()) {
                BuildingClass building = new BuildingClass();
                building.index = rs.getInt("buildingid");
                building.trialid = trialid;
                building.id = rs.getInt("facility_id");
                building.name = rs.getString("facility_name");
                building.xcor = rs.getInt("xcor");
                building.ycor = rs.getInt("ycor");
                building.width = rs.getInt("width");
                building.height = rs.getInt("height");
                building.dest = rs.getString("dest");
                building.numworkers = rs.getInt("num_workers");
                building.mintimeworking = rs.getInt("min_time_working");
                building.maxtimeworking = rs.getInt("max_time_working");
                building.slow = rs.getBoolean("slow_in_mud");
                building.tired = rs.getBoolean("tired_in_mud");
                building.injured = rs.getBoolean("injured_in_mud");
                buildings.add(building);
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DataConnection.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(DataConnection.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }

        return buildings;
    }

    // returns a list of IDs for all the trials in the newest population
    // IDs are sorted by total trips, highest at top, lowest at bottom.
    public List<Integer> getLatestPopulationTrialList( int poptype ) {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        List<Integer> trials = new ArrayList<>();

        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            String query = "SELECT population.populationid, population_type_id, label, time, trialid, total_trips\n"
                    + "FROM  worksite.population, traffictrials \n"
                    + "where population_type_id = " + poptype + " and traffictrials.populationid = population.populationid\n"
                    + "and population.populationid = (select max(populationid) from population)\n"
                    + "ORDER BY  total_trips DESC;";
            rs = st.executeQuery(query);
            while (rs.next()) {
                populationID = rs.getInt("populationid");
                Integer trialid = rs.getInt("trialid");
                trials.add(trialid);
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DataConnection.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(DataConnection.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return trials;
    }

    public void updateTotalTrips(long trialid, int tripcount) {

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            String query = "UPDATE traffictrials set total_trips= " + tripcount + " where trialid=" + trialid;
            st.executeUpdate(query);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DataConnection.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            System.out.println(ex.getMessage());

        } finally {

            try {

                if (rs != null) {
                    rs.close();
                }

                if (st != null) {
                    st.close();
                }

                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {

                Logger lgr = Logger.getLogger(DataConnection.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    
    public void ShowResults() {
        //SELECT traffictrials.populationid, populationtypename, count(total_trips), avg(total_trips), min(total_trips), max(total_trips) FROM worksite.traffictrials, populationtypes, population where traffictrials.populationid = population.populationid and population_type_id= populationtypeid  group by populationid;
    }

    
}
