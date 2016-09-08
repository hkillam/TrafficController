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
//javafx.scene.layout.HBox;

import org.nlogo.app.App;

/**
 *
 * @author drago
 */
public class TrafficController extends Application {

    private static App myApp;
            
    public static void main(String[] args) {
        App.main(args);
        myApp = App.app();
        launch(args);

   }

    
    @Override
    public void start(Stage primaryStage) {
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

        GridPane root = new GridPane();
        primaryStage.setTitle("Worksite Traffic Controller");
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(25, 25, 25, 25));

        Button btnSetup = new Button();
        btnSetup.setMinWidth(20);
        btnSetup.setText("Setup and Run Netlogo");
        btnSetup.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //System.out.println("debug info");
                try {
                    App.app().command("set total-ticks 2000");
                    //App.app().command("setup");
                    //App.app().command("repeat 2000 [ go ]");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        root.getChildren().add(btnSetup);

        Button btnShuffle = new Button();
        btnShuffle.setText("Randomize and Run");
        btnShuffle.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    App.app().command("set total-ticks 2000");
                    App.app().command("setup");
                    App.app().command("make-buildings-dance");
                    App.app().command("repeat 2000 [ go ]");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

HBox hbBtn = new HBox(10);
//hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
hbBtn.getChildren().add(btnShuffle);
root.add(hbBtn, 1, 4);       

        
        MainWindow mainDialog = new MainWindow();
        mainDialog.setVisible(true);
        
        CSVReader  csv = new CSVReader();
        csv.ReadFile("C:\\WorksiteTrafficResults\\results-groupstats.csv");

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
