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
 * Human class.
 * @author Jasmine Dacones
 * @version 4/17/16
 */
public class Human extends AbstractVehicle {
    
    /** Death time of Truck. */
    private static final int DEATH_TIME = 50;
      
    /** Valid directions the vehicle may move in. */
    private List<Direction> myPossDir;
    
    /**
     * Constructor for a new ATV vehicle.
     * 
     * @param theX the x coordinate
     * @param theY the y coordinate
     * @param theDirection directions the ATV can travel
     */
    public Human(final int theX, final int theY, final Direction theDirection) {
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
        boolean passResult = false;
        
        if (theTerrain == Terrain.GRASS) {
            passResult = true;
        } else if (theTerrain == Terrain.CROSSWALK && (theLight != Light.GREEN)) {
            passResult = true;
        } else {
            passResult = false;
        }
        return passResult;   
    }
    
    /**
     * Returns the direction this object would like to move, based on the given
     * map of the neighboring terrain.
     * 
     * @param theNeighbors the map of neighboring terrain.
     * @return the direction this object would like to move.
     */
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final Direction direction;
        
        final Random random = new Random();
        
        myPossDir = new ArrayList<Direction>();
               
        leftDir(theNeighbors);
        rightDir(theNeighbors);
        straightDir(theNeighbors); 
        
        if (theNeighbors.get(getDirection()) == Terrain.CROSSWALK) {
            direction = getDirection();
        } else if (theNeighbors.get(getDirection().left()) == Terrain.CROSSWALK) {
            direction = getDirection().left();
        } else if (theNeighbors.get(getDirection().right()) == Terrain.CROSSWALK) {
            direction = getDirection().right();
        } else if (myPossDir.isEmpty()) {
            direction = getDirection().reverse(); 
        } else {
            direction = myPossDir.get(random.nextInt(myPossDir.size()));
        }
  
        return direction;             
    }
   
    
        
    
    /**
     * Determines valid terrain on the left side.
     * 
     * @param theNeighbors map of neighboring terrain.
     */
    private void leftDir(final Map<Direction, Terrain> theNeighbors) {
        if (theNeighbors.get(getDirection().left()) == Terrain.GRASS
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
        if (theNeighbors.get(getDirection().right()) == Terrain.GRASS
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
        if (theNeighbors.get(getDirection()) == Terrain.GRASS
                        || (theNeighbors.get(getDirection()) == Terrain.CROSSWALK)) {
            myPossDir.add(getDirection());      
        } 
    }
}
