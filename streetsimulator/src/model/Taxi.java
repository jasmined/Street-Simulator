/*
 * TCSS 305
 * Assignment 3 - Easy Street
 */

package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Taxi class for the Easy Street GUI.
 * @author Jasmine Dacones
 * @version 4/20/16
 */
public class Taxi extends AbstractVehicle {
    
    /** Death time of Taxi. */
    private static final int DEATH_TIME = 10;
    
    /** Direction the vehicle wants to move in. */
    private Direction myDirection;
    
    /** Valid directions the vehicle may move in. */
    private List<Direction> myPossDir;
    
    /**
     * Constructor for a new Taxi vehicle.
     * 
     * @param theX the x coordinate
     * @param theY the y coordinate
     * @param theDirection directions the ATV can travel
     */
    public Taxi(final int theX, final int theY, final Direction theDirection) {
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
        return theTerrain == Terrain.STREET || ((theTerrain == Terrain.LIGHT) 
                        && (theLight == Light.YELLOW || theLight == Light.GREEN)) 
                        || theTerrain == Terrain.CROSSWALK;
    }
    
    /**
     * Returns the direction this object would like to move, based on the given
     * map of the neighboring terrain.
     * 
     * @param theNeighbors The map of neighboring terrain.
     * @return the direction this object would like to move.
     */
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        
        myPossDir = new ArrayList<Direction>();
        
        
        leftDir(theNeighbors);
        rightDir(theNeighbors);
        straightDir(theNeighbors);

        
        if (myPossDir.contains(getDirection())) {
            myDirection = getDirection();
        } else if (myPossDir.contains(getDirection().left())) {
            myDirection = getDirection().left();
        } else if (myPossDir.contains(getDirection().right())) {
            myDirection = getDirection().right();
        } else if (myPossDir.isEmpty()) {
            myDirection = getDirection().reverse();  
        }
        
        return myDirection;             
    }
    
    /**
     * Determines valid terrain on the left side.
     * 
     * @param theNeighbors map of neighboring terrain.
     */
    private void leftDir(final Map<Direction, Terrain> theNeighbors) {
        if (theNeighbors.get(getDirection().left()) == Terrain.STREET
                        || theNeighbors.get(getDirection().left()) == Terrain.LIGHT
                        || (theNeighbors.get(getDirection().left()) == Terrain.CROSSWALK)) {
            myPossDir.add(getDirection().left());      
        }        
    }
    
    /**
     * Determines valid terrain on the right side.
     * 
     * @param theNeighbors map of neighboring terrain.
     */
    private void rightDir(final Map<Direction, Terrain> theNeighbors) {
        if (theNeighbors.get(getDirection().right()) == Terrain.STREET
                        || theNeighbors.get(getDirection().right()) == Terrain.LIGHT
                        || (theNeighbors.get(getDirection().right()) == Terrain.CROSSWALK)) {
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
                        || (theNeighbors.get(getDirection()) == Terrain.CROSSWALK)) {
            myPossDir.add(getDirection());      
        } 
    }   
}
