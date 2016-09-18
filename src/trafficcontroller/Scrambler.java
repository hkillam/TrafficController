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
    public List<BuildingClass> shakeuplist(List<BuildingClass> originalcrew, List<Integer> trials) {
        //List<BuildingClass> newset = new ArrayList<>();

        // do one of crossover, and the other two mashups.  
        return originalcrew;
    }

    enum crossover_type {
        puree, // pairs of coordinates are split and X & Y are swapped with Y & X of another pair
        mash, // pairs of coordinates split, but X swaps with X and Y with Y
        chop     // pairs of coordinates stay intact, and swap with buildings in other set.
    }

    public Population Reproduce(Population original, int quantity, int cullpercent) {
        int numcull = quantity * cullpercent / 100;
        if (original.size() / 2 < numcull) {
            numcull = original.size() / 2;
        } // do not cull more than half
        original.loadTrials();
        Population newset = original.copy(0, quantity - numcull);
        newset.type = Population.populationtypes.reproduction;

        // copy the best to replace the worse.
        int i = 0;
        while (newset.size() < quantity) {

            TrialClass trial = newset.get(i);
            newset.add(trial.copy());
            i++;
        }

        return newset;
    }

    public Population Crossover(Population reproduced, int numcrossover) {
        Population mutated = new Population();
        mutated.type = Population.populationtypes.mutation;
        int countcrossfails = 0;

        // crossover - break and switch the building lists at random places for two trials
        while (mutated.size() < numcrossover) {
            // two random trials
            TrialClass trialA = reproduced.trials.remove(rand.nextInt(reproduced.size()));
            TrialClass trialB = reproduced.trials.remove(rand.nextInt(reproduced.size()));
            int chainlength = rand.nextInt(trialA.size() - 1) + 1;  // don't let the break be at the beginning or end.
            TrialClass newA = new TrialClass();
            TrialClass newB = new TrialClass();
            for (int j = 0; j < chainlength; j++) {
                newA.add(trialA.get(j).copy());
                newB.add(trialB.get(j).copy());
            }
            // then the crossover for the rest of the chain.
            for (int j = chainlength; j < trialA.size(); j++) {
                newB.add(trialA.get(j).copy());
                newA.add(trialB.get(j).copy());
            }

            // toss back any fish that are not valid sets.  they will be picked up again on another random grab.
            if (newA.overlaps()) {
                reproduced.add(trialA);
                countcrossfails++;
            } else {
                mutated.add(newA);
            }
            if (newB.overlaps()) {
                reproduced.add(trialB);
                countcrossfails++;
            } else {
                mutated.add(newB);
            }
        }

        return mutated;
    }

    public TrialClass Mutate(TrialClass trial, int percentmutateby) {
        TrialClass ninja = trial.copy();
        BuildingClass mutant = (ninja.get(rand.nextInt(ninja.size())));

        // test for overlap - buildings locations are all valid, but can't overlap
        switch (rand.nextInt(4)) {
            case 0:
                mutant.xcor += (mutant.width * percentmutateby / 100);
                break;
            case 1:
                mutant.xcor -= (mutant.width * percentmutateby / 100);
                break;
            case 2:
                mutant.ycor += (mutant.height * percentmutateby / 100);
                break;
            default:
                mutant.ycor -= (mutant.height * percentmutateby / 100);
                break;
        }
        return ninja;
    }

    // original crossover code - pre Sept 15
    // returns two new trials, neither tested to see if the buildings actually 
    //work.
    public Population crossover(TrialClass trialA, TrialClass trialB, crossover_type blenderspeed) {
        TrialClass oldA = trialA.copy();
        TrialClass oldB = trialB.copy();
        TrialClass newA = new TrialClass();
        TrialClass newB = new TrialClass();
        Population newpair = new Population();

        // preserve some
        for (int i = rand.nextInt(3); i > 0; i--) {
            newA.buildings.add(oldA.buildings.remove(rand.nextInt(oldA.size())));
            newB.buildings.add(oldB.buildings.remove(rand.nextInt(oldB.size())));
        }

        while (oldA.size() > 0) {
            BuildingClass buildA = oldA.buildings.remove(0);
            BuildingClass buildB = oldB.buildings.remove(oldB.size() - 1);

            switch (blenderspeed) {
                case puree:
                    int x = buildA.xcor;
                    buildA.xcor = buildB.ycor;
                    buildB.ycor = x;

                    break;

                case mash:
                    x = buildA.xcor;
                    buildA.xcor = buildB.xcor;
                    buildB.xcor = x;
                    break;

                case chop:
                    // coordinates remain in pairs, but are assigned to different buildings
                    x = buildA.xcor;
                    int y = buildA.ycor;
                    buildA.xcor = buildB.xcor;
                    buildA.ycor = buildB.ycor;
                    buildB.xcor = x;
                    buildB.ycor = y;
                    break;
            }
            newA.add(buildA);
            newB.add(buildB);
        }

        newpair.add(newA);
        newpair.add(newB);
        return newpair;
    }

}
