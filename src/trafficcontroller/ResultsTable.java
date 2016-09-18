/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficcontroller;

import java.sql.ResultSet;
import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 *
 * @author drago
 */
public class ResultsTable {

    
    public void showResults() {
//        TableView<PopulationResult> table = new TableView<PopulationResult>();
        TableView table = new TableView();
        Scene scene = new Scene(new Group());
        Stage stage = new Stage();
        stage.setTitle("Results of every trial");
        stage.setWidth(600);
        stage.setHeight(300);
        ResultSet results = new DataConnection().ShowResults();
 
        ObservableList<PopulationResult> data =
        FXCollections.observableArrayList();
        
        try {
        while (results.next()) {
            PopulationResult row = new PopulationResult (
                results.getString(0),
                results.getString(1),
                    "","","","",""
                
                );
                data.add(row);
        }
        } catch (Exception ex) {}


 
        table.setEditable(true);
 
        TableColumn IDCol = new TableColumn("ID");
        TableColumn typeCol = new TableColumn("Population Type");
        TableColumn parentCol = new TableColumn("Parent");
        TableColumn numTrialsCol = new TableColumn("Number of Trials");
        TableColumn averageTripsCol = new TableColumn("Average # of trips");
        TableColumn minTripsCol = new TableColumn("Smallest # of trips");
        TableColumn maxTripsCol = new TableColumn("Greatest # of trips");
        
        table.getColumns().addAll(IDCol, typeCol, parentCol, numTrialsCol, averageTripsCol, minTripsCol, maxTripsCol);
        //table.setItems(data);

        
        
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));

 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
    }    
    
    
    
    public static class PopulationResult {

        private final String IDCol;
        private final String typeCol;
        private final String parentCol;
        private final String numTrialsCol;
        private final String averageTripsCol;
        private final String minTripsCol;
        private final String maxTripsCol;
 
        private PopulationResult(String IDCol, String typeCol, String parentCol, String numTrialsCol, String averageTripsCol, String minTripsCol, String maxTripsCol) {
            this.IDCol = new String(IDCol);
            this.typeCol = new String(typeCol);
            this.parentCol = new String(parentCol);
            this.numTrialsCol = new String(numTrialsCol);
            this.averageTripsCol = new String(averageTripsCol);
            this.minTripsCol = new String(minTripsCol);
            this.maxTripsCol = new String(maxTripsCol);
        }
 
        public String getIDCol() {
            return IDCol;
        }
 
        public String gettypeCol() {
            return typeCol;
        }
 
        public String getparentCol() {
            return parentCol;
        }
        public String getnumTrialsCol() {
            return numTrialsCol;
        }
        public String getaverageTripsCol() {
            return averageTripsCol;
        }
        public String getminTripsCol() {
            return minTripsCol;
        }
        public String getmaxTripsCol() {
            return maxTripsCol;
        }
 

    }

         
}
