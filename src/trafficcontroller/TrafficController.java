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
import java.util.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import org.nlogo.app.App;


/* NOTE
 * There is something strange in the NetLogo neighbourhood.  When a netlogo object
 * is used from this main class, it works perfectly.  If the object is passed to another 
 * class or a child window, netlogo fails when it is called.  Not spending more time finding out why, continuing with what works.
*/

/**
 *
 * @author drago
 */
public class TrafficController extends Application {

    private TextField txtPoplabel;
    private TextField txt2ndPopSize = new TextField();
    private TextField txtTickCount = new TextField();
    private TextField txtBuildingsListFile;

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

        Font titlefont = new Font("Courier", 14);
        int stage1row = 0;
        int stage2row = 8;
        int settingsrow = 14;

        // build the main window
        // main window - start with a grid
        GridPane root = new GridPane();
        primaryStage.setTitle("Worksite Traffic Controller");
        root.setHgap(5);
        root.setVgap(5);
        root.setPadding(new Insets(25, 25, 25, 25));
        //       root.setGridLinesVisible(true);

        Label lblStage1Section = new Label("STAGE 1: GENERATION");
        lblStage1Section.setFont(titlefont);
        lblStage1Section.setTextFill(Color.web("#480F56"));
        root.add(lblStage1Section, 0, stage1row);

        Label lblStage2Section = new Label("STAGE 2: REPRODUCTION");
        lblStage2Section.setFont(titlefont);
        lblStage2Section.setTextFill(Color.web("#480F56"));
        root.add(lblStage2Section, 0, stage2row);

        Label lblSettingsSection = new Label("NETLOGO SETTINGS");
        lblSettingsSection.setFont(titlefont);
        lblSettingsSection.setTextFill(Color.web("#480F56"));
        root.add(lblSettingsSection, 0, settingsrow);

// GENERATION
        /* text: give the population a name */
        txtPoplabel = new TextField();
        txtPoplabel.setText("Give this a name");
        txtPoplabel.setPrefWidth(200);
        txtPoplabel.setId("txtPoplabel"); // NOI18N
        root.add(new Label("Population name"), 0, stage1row + 1);
        root.add(txtPoplabel, 1, stage1row + 1);

        /* text: size of population */
        TextField txtIterations = new TextField();
        txtIterations.setText("20");
        txtIterations.setPrefWidth(50);
        txtIterations.setId("txtIterations"); // NOI18N
        root.add(new Label("Population size"), 0, stage1row + 2);
        root.add(txtIterations, 1, stage1row + 2);

        Button btnShuffle = new Button();
        btnShuffle.setText("Generate Population");
        btnShuffle.setPrefWidth(200);
        btnShuffle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CreateRandomPopulation(txtIterations);
            }
        });
        root.add(btnShuffle, 1, stage1row + 3);

// REPRODUCTION

        /* text: size of population */
        txt2ndPopSize.setText("20");
        txt2ndPopSize.setPrefWidth(50);
        root.add(new Label("Reproduction Population size"), 0, stage2row + 1);
        root.add(txt2ndPopSize, 1, stage2row + 1);

        /* text: percentage of slow ones to remove, replace with clones of the fast ones */
        TextField txtPrecenttocull = new TextField();
        txtPrecenttocull.setText("20");
        txtPrecenttocull.setPrefWidth(50);
        root.add(new Label("Percentage to remove"), 0, stage2row + 2);
        root.add(txtPrecenttocull, 1, stage2row + 2);

        /* text: amount to move mutated buildings by */
        TextField txtMutationAmnt = new TextField();
        txtMutationAmnt.setText("10");
        txtMutationAmnt.setPrefWidth(50);
        root.add(new Label("Move(mutate) by x%"), 0, stage2row + 3);
        root.add(txtMutationAmnt, 1, stage2row + 3);

        /* text: ratio of crossovers vs mutations */
        TextField txtCrossoverMutantRatio = new TextField();
        txtCrossoverMutantRatio.setText("80");
        txtCrossoverMutantRatio.setPrefWidth(50);
        root.add(new Label("Percentage of crossovers"), 0, stage2row + 4);
        root.add(txtCrossoverMutantRatio, 1, stage2row + 4);


        /* Button - create reproduction population */
        Button btnReproductions = new Button();
        btnReproductions.setText("Create reproduction population");
        btnReproductions.setPrefWidth(200);
        btnReproductions.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               int popsize = Integer.parseInt(txt2ndPopSize.getText());
               int percenttocull = Integer.parseInt(txtPrecenttocull.getText());
               int mutationAmnt = Integer.parseInt(txtMutationAmnt.getText());
               int crossoverMutantRatio = Integer.parseInt(txtCrossoverMutantRatio.getText());
               Population newpopulation = Mutate(popsize, percenttocull, mutationAmnt, crossoverMutantRatio);
            }
        });
        root.add(btnReproductions, 1, stage2row + 5);

// SETTINGS        

        /* filename for building list */
        txtBuildingsListFile = new TextField();
        txtBuildingsListFile.setText("C:\\Users\\drago\\OneDrive\\Documents\\Work\\Omar NetLogo\\worksitetraffic\\buildings.csv");
        txtBuildingsListFile.setId("txtBuildingsListFile"); // NOI18N
        root.add(new Label("Building List Source"), 0, settingsrow + 1);
        root.add(txtBuildingsListFile, 1, settingsrow + 1);

        /* filename for building results file */
        TextField txtResultsFilename = new TextField();
        txtResultsFilename.setText("C:\\WorksiteTrafficResults\\results-buildings.csv");
        txtResultsFilename.setId("txtResultsFilename"); // NOI18N
        root.add(new Label("Building List Results"), 0, settingsrow + 2);
        root.add(txtResultsFilename, 1, settingsrow + 2);

        Label lblWorkerResultsFilename = new Label("Performance Results");
        root.add(lblWorkerResultsFilename, 0, settingsrow + 3);

        TextField txtWorkerResultsFilename = new TextField();
        txtWorkerResultsFilename.setText("C:\\WorksiteTrafficResults\\results-groupstats.csv");
        txtWorkerResultsFilename.setId("txtWorkerResultsFilename"); // NOI18N
        root.add(txtWorkerResultsFilename, 1, settingsrow + 3);

        txtTickCount.setText("2000");
        txtTickCount.setId("txtTickCount"); // NOI18N
        root.add(new Label("Ticks per trial"), 0, settingsrow + 4);
        root.add(txtTickCount, 1, settingsrow + 4);

        // put this scene in the new window, and show it
        primaryStage.setScene(new Scene(root, 400, 600));
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
                App.app().command("setup");
                App.app().command("make-buildings-dance");
                App.app().command("repeat " + txtTickCount.getText() + " [ go ]");
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
    // First version - prior to Sept 13.
 /*   public void CreateReproductionPopulation() {
        //DataConnection conn = new DataConnection();
        Population original = new Population(Population.populationtypes.generated); // most recent population will be loaded.
        Population newset = new Population();
        Scrambler shaker = new Scrambler();
        Random rand = new Random();

        newset.type = Population.populationtypes.reproduction;
        try {
            // create the world! (but no breadcrumbs, no workers)
            original.loadTrials();
            App.app().command("setup-world-only");
            for (TrialClass trial : original.trials) {
                newset.add(shaker.crossover(original.get(0), original.trials.get(rand.nextInt(original.size())), Scrambler.crossover_type.puree));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    } 
    */

    // original will be destroyed, pass in something that was already a copy.
    public Population Mutate( int quantity, int cullpercent, int percentmutateby, int percentcrossover) {
        Scrambler shaker = new Scrambler();
        Random rand = new Random();

        // grab the most recently generated population
        Population original = new Population(Population.populationtypes.generated);
        
        // STEP 1:  REPRODUCE
        Population reproduced = shaker.Reproduce (original, quantity, cullpercent);
        int mutantfails = 0;

        // STEP 2: CROSSOVER
        Population mutated;
        int numcrossover = reproduced.size() * percentcrossover / 100;
        mutated = shaker.Crossover(reproduced, numcrossover);
      
        // STEP 3: MUTATION - grab one building, and move by the percentage passed in.
        while (reproduced.size() > 0) {
            TrialClass trialA = reproduced.trials.remove(rand.nextInt(reproduced.size()));
            TrialClass ninja = shaker.Mutate (trialA, percentmutateby);

            // test it
            if (validTrial(ninja)) {
                 mutated.add(ninja);
                 try {
                 new CSVReader().CreateBuildingsFile(trialA, txtBuildingsListFile.getText());
                 } catch (Exception ex) {}
            } else {
                    // toss this back into the pond and fish some more
                reproduced.add(trialA);
                mutantfails++;
            }
        }

        // RUN the trials, adn collect all the data.
        int parentPopulation = original.ID;
        int populationtype = 3; // mutated
        String insertvalues = populationtype +"," + parentPopulation + ",'"  + txtPoplabel.getText() + "'";
        long populationID = new DataConnection().insertRow("population", "(population_type_id, parentpopulationid, label)", insertvalues);
        for (TrialClass trial : mutated.trials) {
            try {
                Long starttime = System.currentTimeMillis();
                new CSVReader().CreateBuildingsFile(trial, txtBuildingsListFile.getText());
                App.app().command("setup");
                App.app().command("repeat " + txtTickCount.getText() + " [ go ]");
                App.app().command("write-results");
                new CSVReader().ReadWorkerStatsFile("C:\\WorksiteTrafficResults\\results-groupstats.csv", "C:\\WorksiteTrafficResults\\results-buildings.csv", starttime, populationID);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        return mutated;
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
    
    public boolean validTrial(TrialClass trial) {
            try {
                App.app().command("setup-world-only");
                for (BuildingClass unit : trial.buildings) {
                    Boolean fits = (Boolean) App.app().report("building-fits? " + unit.xcor + " " + unit.ycor + " " + unit.width + " " + unit.height);
                    if (fits == true) {
                        int x2 = unit.xcor+unit.width;
                        int y2 = unit.ycor+unit.height;
                        App.app().command("sketch-building " + unit.id + " " + unit.xcor + " " + unit.ycor + " " + x2 + " " + y2);
                    } else {
                        return false;
                    }

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return true;
    }

}
