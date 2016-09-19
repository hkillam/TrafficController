/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficcontroller;

import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.collections.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author drago
 */
public class ResultsTable {

    public void showResults() {
        //TableView<PopulationResult> table = new TableView<PopulationResult>();
        Scene scene = new Scene(new Group());
        Stage stage = new Stage();
        stage.setTitle("Results of every trial");
        stage.setWidth(680);
        stage.setHeight(300);

        ObservableList<PopulationResult> data = new DataConnection().GetResults();

        TableView<PopulationResult> table = new TableView<PopulationResult>(data);
        table.setEditable(true);
        table.setMinWidth(655);

        TableColumn IDCol = new TableColumn("ID");
        IDCol.setCellValueFactory(
                new PropertyValueFactory<PopulationResult, String>("IDCol"));

        TableColumn<PopulationResult, String> typeCol = new TableColumn<>("Population Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<PopulationResult, String>("typeCol"));

        TableColumn parentCol = new TableColumn("Parent");
        parentCol.setCellValueFactory(
                new PropertyValueFactory<PopulationResult, String>("parentpopulationid"));

        TableColumn numTrialsCol = new TableColumn("Number of Trials");
        numTrialsCol.setMinWidth(20);
        numTrialsCol.setCellValueFactory(
                new PropertyValueFactory<PopulationResult, String>("numTrialsCol"));

        TableColumn averageTripsCol = new TableColumn("Average # of trips");
        averageTripsCol.setCellValueFactory(
                new PropertyValueFactory<PopulationResult, String>("averageTripsCol"));
        TableColumn minTripsCol = new TableColumn("Smallest # of trips");
        minTripsCol.setCellValueFactory(
                new PropertyValueFactory<PopulationResult, String>("minTripsCol"));
        TableColumn maxTripsCol = new TableColumn("Greatest # of trips");
        maxTripsCol.setCellValueFactory(
                new PropertyValueFactory<PopulationResult, String>("maxTripsCol"));

        table.getColumns().addAll(IDCol, typeCol, parentCol, numTrialsCol, averageTripsCol, minTripsCol, maxTripsCol);
        table.setItems(data);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public static class PopulationResult {

        private final SimpleStringProperty IDCol;
        private final SimpleStringProperty typeCol;
        private final SimpleStringProperty parentpopulationid;
        private final SimpleStringProperty numTrialsCol;
        private final SimpleStringProperty averageTripsCol;
        private final SimpleStringProperty minTripsCol;
        private final SimpleStringProperty maxTripsCol;

        public PopulationResult(String IDCol, String typeCol, String parentpopulationid, String numTrialsCol, String averageTripsCol, String minTripsCol, String maxTripsCol) {
            this.IDCol = new SimpleStringProperty(IDCol);
            this.typeCol = new SimpleStringProperty(typeCol);
            this.parentpopulationid = new SimpleStringProperty(parentpopulationid);
            this.numTrialsCol = new SimpleStringProperty(numTrialsCol);
            this.averageTripsCol = new SimpleStringProperty(averageTripsCol);
            this.minTripsCol = new SimpleStringProperty(minTripsCol);
            this.maxTripsCol = new SimpleStringProperty(maxTripsCol);
        }

        public String getIDCol() {
            return IDCol.get();
        }

        public String getTypeCol() {
            return typeCol.get();
        }

        public String getParentpopulationid() {
            return parentpopulationid.get();
        }

        public String getNumTrialsCol() {
            return numTrialsCol.get();
        }

        public String getAverageTripsCol() {
            return averageTripsCol.get();
        }

        public String getMinTripsCol() {
            return minTripsCol.get();
        }

        public String getMaxTripsCol() {
            return maxTripsCol.get();
        }

    }

}
