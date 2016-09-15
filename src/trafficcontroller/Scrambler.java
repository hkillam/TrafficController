/*
 * Methods for mixing and shaking the data sets.
 */
package trafficcontroller;

import java.util.*;


/**
 *
 * @author drago
 */
public class Scrambler {
    
    private Random rand = new Random(); 
    
    public List<BuildingClass> removeSlow(List<BuildingClass> originalcrew, double ratio) {
     
        // TODO.  write a real function
        
        //List<BuildingClass> newset = new ArrayList<BuildingClass>();
        return originalcrew;
    }
    
    // Heather's prediction - every building will land in a black zone or on the main building and be rejected.  Need an exit for endless looping.
    public  List<BuildingClass> shakeuplist(List<BuildingClass> originalcrew, List<Integer> trials) {
        //List<BuildingClass> newset = new ArrayList<>();
        
        // do one of crossover, and the other two mashups.  
        
        
        return originalcrew;       
    }
    
    // returns two new trials, neither tested to see if the buildings actually work.
    public Population crossover(TrialClass trialA, TrialClass trialB) {
        TrialClass oldA = trialA.copy();
        TrialClass oldB = trialB.copy();
        TrialClass newA = new TrialClass();
        TrialClass newB = new TrialClass();
        Population newpair = new Population();
        
        // preserve some
        for (int i=rand.nextInt(3); i>0; i--) {
            newA.buildings.add (oldA.buildings.remove(rand.nextInt(oldA.size())));
            newB.buildings.add (oldB.buildings.remove(rand.nextInt(oldB.size())));
        }
        
        // mash the rest
        while (oldA.size() > 0) {
            BuildingClass buildA = oldA.buildings.remove(0);
            BuildingClass buildB = oldB.buildings.remove(oldB.size()-1);
            int x = buildA.xcor;
            buildA.xcor = buildB.ycor;
            buildB.ycor = x;
            newA.add(buildA);
            newB.add(buildB);
        }
        
        newpair.add(newA);
        newpair.add(newB);
        return newpair;
    }
    
}
