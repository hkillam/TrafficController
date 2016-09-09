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

    public static long insertRow(String table, String fields, String values) {

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        long retval = 0;

        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            String query = "INSERT INTO " + table + fields + " VALUES (" + values + " )";
            st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            rs = st.getGeneratedKeys();
            if (rs.next()) {
                retval = rs.getLong(1);
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
        return (retval);
    }

    public static void updateTotalTrips(long trialid, int tripcount) {

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

}
