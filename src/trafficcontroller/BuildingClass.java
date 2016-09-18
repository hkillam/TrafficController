/*
 * A building contains coordinates, size, number of workers, etc.
 */
package trafficcontroller;

//import java.util.*;

/**
 *
 * @author drago
 */
public class BuildingClass {

    int trialid;
    int id;
    String name;
    int xcor, ycor;
    int width, height;
    String dest;  // can be a list of destinations
    int numworkers;
    int mintimeworking, maxtimeworking;
    boolean slow, tired, injured;
    int index;  // index in database;

    public BuildingClass copy() {
        BuildingClass newbuilding = new BuildingClass();
        newbuilding.trialid = trialid;
        newbuilding.id = id;
        newbuilding.name = name;
        newbuilding.xcor = xcor;
        newbuilding.ycor = ycor;
        newbuilding.width = width;
        newbuilding.height = height;
        newbuilding.dest = dest;  // can be a list of destinations
        newbuilding.numworkers = numworkers;
        newbuilding.mintimeworking = mintimeworking;
        newbuilding.maxtimeworking = maxtimeworking;
        newbuilding.slow = slow;
        newbuilding.tired = tired;
        newbuilding.injured = injured;

        return newbuilding;
    }
}
