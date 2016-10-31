/*
 * TCSS 305
 * Assignment 3 - Easy Street
 */

package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * Truck class for the Easy Street GUI.
 * @author Jasmine Dacones
 * @version 4/20/16
 */
public class Truck extends AbstractVehicle {   
    
    /** Death time of Truck. */
    private static final int DEATH_TIME = 0;
    
    /** Valid directions the vehicle may move in. */
    private List<Direction> myPossDir;
    
    /** Direction the vehicle wants to move in. */
    private Direction myDirection;
    
    
    /**
     * Constructor for a new ATV vehicle.
     * 
     * @param theX the x coordinate
     * @param theY the y coordinate
     * @param theDirection directions the ATV can travel
     */
    public Truck(final int theX, final int theY, final Direction theDirection) {
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
        return theTerrain == Terrain.STREET 
                        || theTerrain == Terrain.LIGHT 
                        || ((theTerrain == Terrain.CROSSWALK) 
                        && (theLight == Light.YELLOW || theLight == Light.GREEN));     
    }
    
    /**
     * Returns the direction this object would like to move, based on the given
     * map of the neighboring terrain.
     * 
     * @param theNeighbors The map of neighboring terrain.
     * @return the direction this object would like to move.
     */
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {

        final Random random = new Random();
        
        myPossDir = new ArrayList<Direction>();
                
        leftDir(theNeighbors);
        rightDir(theNeighbors);
        straightDir(theNeighbors);
        
        if (myPossDir.isEmpty()) {
            myDirection = getDirection().reverse();  
        } else {
            myDirection = myPossDir.get(random.nextInt(myPossDir.size()));
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
