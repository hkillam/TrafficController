/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficcontroller;

import org.nlogo.app.App;

/**
 *
 * @author drago
 */
public class TrafficController {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] argv) {
        App.main(argv);
        try {
            java.awt.EventQueue.invokeAndWait(
                    new Runnable() {
                public void run() {
                    try {
                        App.app().open(
                                "C:\\Users\\drago\\OneDrive\\Documents\\Work\\Omar NetLogo\\worksitetraffic\\WorksiteTraffic.nlogo");
                    } catch (java.io.IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            App.app().command("set total-ticks 2000");
            App.app().command("setup");
            App.app().command("repeat 2000 [ go ]");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
