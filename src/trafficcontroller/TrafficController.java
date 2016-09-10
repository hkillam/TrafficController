/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficcontroller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.geometry.*;

import org.nlogo.app.App;

/**
 *
 * @author drago
 */
public class TrafficController extends Application {

    public static void main(String[] args) {
        App.main(args);  // launch netlogo
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //DataConnection mydb = new DataConnection();
        //mydb.test();
        // load our NetLogo application
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

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // build the main window
        // main window - start with a grid
        GridPane root = new GridPane();
        primaryStage.setTitle("Worksite Traffic Controller");
        root.setHgap(5);
        root.setVgap(5);
        root.setPadding(new Insets(25, 25, 25, 25));

        Button btnSetup = new Button();
        btnSetup.setPrefWidth(200);
        btnSetup.setText("Setup and Run Netlogo");
        btnSetup.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //System.out.println("debug info");
                try {
                    App.app().command("set total-ticks 2000");
                    App.app().command("setup");
                    //App.app().command("repeat 2000 [ go ]");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        //root.add(btnSetup, 1, 1);
        Label lblResultsFilename = new Label();
        lblResultsFilename.setText("Building List Results");
        root.add(lblResultsFilename, 1, 7);

        TextField txtResultsFilename = new TextField();
        txtResultsFilename.setText("C:\\WorksiteTrafficResults\\results-buildings.csv");
        txtResultsFilename.setId("txtResultsFile"); // NOI18N
        txtResultsFilename.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });
        root.add(txtResultsFilename, 2, 7);

        Label lblWorkerResultsFilename = new Label();
        lblWorkerResultsFilename.setText("Performance Results");
        root.add(lblWorkerResultsFilename, 1, 8);
        TextField txtWorkerResultsFilename = new TextField();
        txtWorkerResultsFilename.setText("C:\\WorksiteTrafficResults\\results-groupstats.csv");
        txtWorkerResultsFilename.setId("txtResultsFile"); // NOI18N
        txtWorkerResultsFilename.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });
        root.add(txtWorkerResultsFilename, 2, 8);

        TextField txtIterations = new TextField();
        txtIterations.setText("20");
        txtIterations.setPrefWidth(50);
        txtIterations.setId("txtIterations"); // NOI18N
        txtIterations.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });
        root.add(txtIterations, 2, 2);
        Label lblIterations = new Label();
        lblIterations.setText(" iterations");
        root.add(lblIterations, 2, 3);

        Button btnShuffle = new Button();
        btnShuffle.setText("Randomize and Run");
        btnShuffle.setPrefWidth(200);
        btnShuffle.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                int iterations = Integer.parseInt(txtIterations.getText());
                for (int i = 0; i < iterations; i++) {
                    try {
                        Long starttime = System.currentTimeMillis();
                        App.app().command("set total-ticks 2000");
                        App.app().command("setup");
                        App.app().command("make-buildings-dance");
                        App.app().command("repeat 2000 [ go ]");
                        App.app().command("write-results");
                        new CSVReader().ReadWorkerStatsFile("C:\\WorksiteTrafficResults\\results-groupstats.csv", "C:\\WorksiteTrafficResults\\results-buildings.csv", starttime);
                        // note - slow function.  start time 9:30
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        root.add(btnShuffle, 1, 2);

        // put this scene in the new window, and show it
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();

        //new CSVReader().ReadWorkerStatsFile("C:\\WorksiteTrafficResults\\results-groupstats.csv", "C:\\WorksiteTrafficResults\\results-buildings.csv", System.currentTimeMillis());
    }

    public void RunNetlogoStartup() {
        try {
            App.app().command("set total-ticks 2000");
            //App.app().command("setup");
            //App.app().command("repeat 2000 [ go ]");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
