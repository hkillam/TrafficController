/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficcontroller;

import java.util.*;

/**
 *
 * @author drago
 */
public class TrialClass {
    List<BuildingClass>  buildings;
    int trialid = 0;
    boolean generated = true;  // is hte list loaded from the DB or created here.
    DataConnection conn = new DataConnection();
    
    // loads a list of buildings from the database
    TrialClass(int trialID) {
        this.trialid = trialID;
        generated = false;
        buildings = conn.getBuildings(trialID);
    }
    
    // empty trial, waiting for crazy made up buildings to be stuck in it.
    TrialClass() {
        generated = true;
        buildings = new ArrayList<>();
    }
    
    public int size() {
        return buildings.size();
    }
    
    public BuildingClass get(int index) {
        if (index >= buildings.size()) {
            return null;
        }
        return(buildings.get(index));
    }
    
    public void add(BuildingClass building) {
        buildings.add(building);
    }
    
    // a deep copy, making new building objects so that the new buildings can be mashed
    public TrialClass copy() {
        TrialClass newcopy = new TrialClass();
        for (BuildingClass building : this.buildings) {
            newcopy.add(building.copy());
        }
        return newcopy;
    }
    
    public boolean overlaps() {
        // check each building in the set
        for (int i=0; i<buildings.size(); i++) {
            BuildingClass A = buildings.get(i);
            // check them against the rest of the set
            for (int j=i+1; j<buildings.size(); j++) {
                BuildingClass B = buildings.get(j);
                //int overlap = true;
                if (A.xcor > B.xcor + B.width // is B completely to the right of A?
                        || A.xcor + A.width < B.xcor // is B to the left?
                        || A.ycor > B.ycor + B.height // 
                        || A.ycor + A.height < B.ycor
                        ) {
                    return false;
                }
            }
        }
        return true; 
                
    }
}
