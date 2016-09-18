/*
 * A population is a collection of trials.  Some trials work better than others.
 * Each building has a trialID in it, to group them together.
 * Each trial contains a set of buildings, and after it runs, statistics on how it performs.
 */
package trafficcontroller;

import java.util.*;

/**
 *
 * @author drago
 */
public class Population {

    int ID;
    List<Integer> trialIDs;
    List<TrialClass> trials;
    boolean generated = true;
    populationtypes type;

    public enum populationtypes {
        // fixed values must match database values
        generated(1), // created by letting NetLogo choose valid building locations
        reproduction(2), // take a generated set, remove the slow, mix the rest
        mutation(3);     // take a reproduction set, remove the slow, mix and add newly generated items
        private int value;

        private populationtypes(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
        
    }

    Population() {
        generated = true;
        trials = new ArrayList<>();
        trialIDs = null;
    }

    // grabs the newest population of the given type
    Population(populationtypes type) {
            DataConnection conn = new DataConnection();
            generated = false;
            trialIDs = conn.getLatestPopulationTrialList(type.getValue());
            ID = conn.populationID;
            trials = new ArrayList<>();
    }

    Population(int ID) {
        this.ID = ID;
        // TODO - if an ID is passed in, load that one from the database.
    }

    // initially a population has a list of trialIDs, and needs to load the actual trial information.
    // safe to call again after it is loaded.  only needed once.
    public void loadTrials() {
        if (trials.size() < trialIDs.size()) {
            for (Integer trialid : trialIDs) {
                trials.add(new TrialClass(trialid));
            }
        }
    }

    // returns 1 for no error
    public int add(TrialClass newtrial) {
        // make sure we don't mess up new populations with old.
        if (newtrial.generated != generated) {
            return -2;  // can't mix new with old.
        }

        // TODO if not generated, see if this trial is already in the population
        // TODO if not generated, make sure this trial belongs in this population.
        trials.add(newtrial);
        return 1;  // no error
    }
    
    // preforms a deep copy.
    public Population copy() {
        return copy(0, size());
    }
    
    public TrialClass copy(int index) {
        return trials.get(index).copy();
    }
    
    // deep copy, limited number of trials copied.
     public Population copy( int start, int end) {
        Population newset = new Population();
        this.loadTrials();
        newset.ID = 0;
        newset.trialIDs = null;
        newset.generated = true;
        
        if (end > trials.size())
            end = trials.size();
        
        // deep copy
        newset.trials = new ArrayList<>();
        for (int i=start; i<end; i++) {
            TrialClass trial = trials.get(i);
            if (trial != null) {
              newset.trials.add(trial.copy());
            }
        }
        
        return newset;
    }
    
   
    public int size() {
        
        if (trialIDs == null)
            return trials.size();
        
        // usually trials are already loaded, but if the IDs are still around, maybe they aren't
        return(Math.max(trials.size(), trialIDs.size()));
    }
    
    public TrialClass get( int index ) {
        return trials.get(index);
    }
    
    public int add(Population newpopulation) {
        for (TrialClass trial : newpopulation.trials) {
            this.add(trial);
        }
        return 1; // no error
    }
}
