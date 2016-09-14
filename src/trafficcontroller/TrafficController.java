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
//import javafx.scene.text.*;
import javafx.geometry.*;

import org.nlogo.app.App;

/**
 *
 * @author drago
 */
public class TrafficController extends Application {
private TextField txtPoplabel;
    public static void main(String[] args) {
        App.main(args);  // launch netlogo
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

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
 //       root.setGridLinesVisible(true);

        // original test button - not being added.
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
        root.add(lblResultsFilename, 0, 7);

        /* filename for building results file */
        TextField txtResultsFilename = new TextField();
        txtResultsFilename.setText("C:\\WorksiteTrafficResults\\results-buildings.csv");
        txtResultsFilename.setId("txtResultsFile"); // NOI18N
        root.add(txtResultsFilename, 2, 7);

        Label lblWorkerResultsFilename = new Label();
        lblWorkerResultsFilename.setText("Performance Results");
        root.add(lblWorkerResultsFilename, 0,8);
        
        TextField txtWorkerResultsFilename = new TextField();
        txtWorkerResultsFilename.setText("C:\\WorksiteTrafficResults\\results-groupstats.csv");
        txtWorkerResultsFilename.setId("txtResultsFile"); // NOI18N
        root.add(txtWorkerResultsFilename, 2, 8);

        /* text: give the population a name */
        txtPoplabel = new TextField();
        txtPoplabel.setText("Give this a name");
        txtPoplabel.setPrefWidth(200);
        txtPoplabel.setId("txtPoplabel"); // NOI18N
        root.add(txtPoplabel, 2, 2);

        /* text: size of population */
        TextField txtIterations = new TextField();
        txtIterations.setText("20");
        txtIterations.setPrefWidth(50);
        txtIterations.setId("txtIterations"); // NOI18N
        root.add(txtIterations, 4, 2);
        
        Label lblIterations = new Label();
        lblIterations.setText("Population size");
        root.add(lblIterations, 3, 2);

        Button btnShuffle = new Button();
        btnShuffle.setText("Generate Population");
        btnShuffle.setPrefWidth(200);
        btnShuffle.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) { CreateRandomPopulation(txtIterations);}
        });
        root.add(btnShuffle, 0, 2);

        /* Button - create reproduction population */
        Button btnReproductions = new Button();
        btnReproductions.setText("Create reproduction population");
        btnReproductions.setPrefWidth(200);
        btnReproductions.setOnAction(new EventHandler<ActionEvent>() {
            @Override  public void handle(ActionEvent event) { CreateReproductionPopulation();}
        });
        root.add(btnReproductions, 0, 3);

        // put this scene in the new window, and show it
        primaryStage.setScene(new Scene(root, 450, 300));
        primaryStage.show();
    }

    // Create a population of building plans.  Each plan places the buildings in
    // random (successful) locations, avoiding obsticles.  Each plan is then executed
    // to gather info on how the traffic flows, including total number of trips.
    // The algorithm for placing buildings was created in NetLogo previously, and is
    // triggered repeatedly from here.
    public void CreateRandomPopulation(TextField txtIterations) {
        // TODO:  show a counter for how many iterations are done.  20 iterations could take more than an hour
        int iterations = Integer.parseInt(txtIterations.getText());
        long populationID = new DataConnection().insertRow("population", "(population_type_id, parentpopulationid,label)", "1,0,'" + txtPoplabel.getText() + "'");
        for (int i = 0; i < iterations; i++) {
            try {
                Long starttime = System.currentTimeMillis();
                App.app().command("set total-ticks 2000");
                App.app().command("setup");
                App.app().command("make-buildings-dance");
                App.app().command("repeat 2000 [ go ]");
                App.app().command("write-results");
                new CSVReader().ReadWorkerStatsFile("C:\\WorksiteTrafficResults\\results-groupstats.csv", "C:\\WorksiteTrafficResults\\results-buildings.csv", starttime, populationID);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } 
    }
    
    // Create Reproduction Population
    // Take the top 80% of the newest 1st gen population, and scramble it.
    // 
    public void CreateReproductionPopulation() {
                

                        // get an existing 1st gen population
                        // load netlogo startup-world-only
                        // App.app().command("setup-world-only");
                        // for every trial
                        //    scramble and test until we have a valid position for each building.
                        
    }
    
    // unused.
    public void RunNetlogoStartup() {
        try {
            App.app().command("set total-ticks 2000");
            App.app().command("setup");
            App.app().command("repeat 2000 [ go ]");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
