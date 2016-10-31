/*
 * TCSS 305
 * Assignment 3 - Easy Street
 */

package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Bicycle class for the Easy Street GUI.
 * class AbstractVehicle.
 * @author Jasmine Dacones
 * @version 4/20/16
 */
public class Bicycle extends AbstractVehicle {
    
    /** Death time of Truck. */
    private static final int DEATH_TIME = 30;
    
    /** Valid directions the vehicle may move in. */
    private List<Direction> myPossDir;
    
    /**
     * Constructor for a new ATV Bicycle.
     * 
     * @param theX the x coordinate
     * @param theY the y coordinate
     * @param theDirection directions the ATV can travel
     */
    public Bicycle(final int theX, final int theY, final Direction theDirection) {
        super(theX, theY, theDirection, DEATH_TIME, true);
    }
    
    /**
     * Returns whether or not this object may move onto the given type of
     * terrain, when the street lights are the given color.
     * 
     * @param theTerrain the terrain.
     * @param theLight the light color.
     * @return whether or not this object may move onto the given type of
     *         terrain when the street lights are the given color.
     */
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        return theTerrain == Terrain.STREET || theTerrain == Terrain.TRAIL
                        || (theTerrain == Terrain.LIGHT && (theLight == Light.GREEN)) 
                        || (theTerrain == Terrain.CROSSWALK && (theLight == Light.GREEN));
    }
    
    /**
     * Returns the direction this object would like to move, based on the given
     * map of the neighboring terrain.
     * 
     * @param theNeighbors the map of neighboring terrain.
     * @return the direction this object would like to move.
     */
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction direction;
        
        myPossDir = new ArrayList<Direction>();
               
        straightDir(theNeighbors);
        leftDir(theNeighbors);
        rightDir(theNeighbors);
     
        for (final Direction d : theNeighbors.keySet()) {
            if (theNeighbors.get(d) == Terrain.TRAIL
                            && d != getDirection().reverse()) {
                direction = d;
            }
        }
        
        if (theNeighbors.get(getDirection()) == Terrain.TRAIL) {
            direction = getDirection();
        } else if (theNeighbors.get(getDirection().left()) == Terrain.TRAIL) {
            direction = getDirection().left();
        } else if (theNeighbors.get(getDirection().right()) == Terrain.TRAIL) {
            direction = getDirection().right();
        } else if (myPossDir.contains(getDirection())) {
            direction = getDirection();
        } else if (myPossDir.contains(getDirection().left())) {
            direction = getDirection().left();
        } else if (myPossDir.contains(getDirection().right())) {
            direction = getDirection().right();           
        } else {
            direction = getDirection().reverse();
        }
        
        return direction;             
    }
    
    /**
     * Determines valid terrain on the left side.
     * 
     * @param theNeighbors map of neighboring terrain.
     */
    private void leftDir(final Map<Direction, Terrain> theNeighbors) {
        if (theNeighbors.get(getDirection().left()) == Terrain.STREET
                        || theNeighbors.get(getDirection().left()) == Terrain.LIGHT
                        || (theNeighbors.get(getDirection().left()) == Terrain.CROSSWALK)
                        || theNeighbors.get(getDirection().left()) == Terrain.TRAIL) {
            myPossDir.add(getDirection().left());      
        }        
    }   
    
    /**
     * Determines valid terrain on the right side.
     * 
     * @param theNeighbors map of neighboring terrain.
     */
    private void rightDir(final Map<Direction, Terrain> theNeighbors) {
        if (theNeighbors.get(getDirection()) == Terrain.STREET
                        || theNeighbors.get(getDirection().right()) == Terrain.LIGHT
                        || (theNeighbors.get(getDirection().right()) == Terrain.CROSSWALK)
                        || theNeighbors.get(getDirection().right()) == Terrain.TRAIL) {
            myPossDir.add(getDirection().right());      
        } 
    }
    
    /**
     * Determines valid terrain straight ahead.
     * 
     * @param theNeighbors map of neighboring terrain.
     */
    private void straightDir(final Map<Direction, Terrain> theNeighbors) {
        if (theNeighbors.get(getDirection()) == Terrain.STREET
                        || theNeighbors.get(getDirection()) == Terrain.LIGHT
                        || (theNeighbors.get(getDirection()) == Terrain.CROSSWALK)
                        || theNeighbors.get(getDirection()) == Terrain.TRAIL) {
            myPossDir.add(getDirection());      
        } 
    }
}
