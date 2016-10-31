/*
 * TCSS 305
 * Assignment 3 - Easy Street
 */

package model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * ATV vehicle for the Easy Street GUI.
 * @author Jasmine Dacones
 * @version 4/16/16
 */
public class Atv extends AbstractVehicle {
    
    /** Death time of Truck. */
    private static final int DEATH_TIME = 20;
   
              
    /**
     * Constructor for a new ATV.
     * 
     * @param theX the x coordinate
     * @param theY the y coordinate
     * @param theDirection directions the ATV can travel
     */
    public Atv(final int theX, final int theY, final Direction theDirection) {
        super(theX, theY, theDirection, DEATH_TIME, true);
    }
    
    /**
     * Returns whether this vehicle can pass through the given type 
     * of terrain and when the street lights are in the given state.
     * 
     * @param theTerrain terrain type
     * @param theLight state of the light
     * @return whether this vehicle can pass through the given type 
     * of terrain and when the street.
     */
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        return theTerrain != Terrain.WALL;
    }
    
    /**
     * Returns the direction in which this vehicle would like to move.
     * 
     * @param theNeighbors Map containing the types of terrain that
     * neighbor this vehicle.
     * @return the direction in which this vehicle would like to move.
     */
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final Direction direction;
        
        final Random random = new Random();
        
        final ArrayList<Direction> possDir = new ArrayList<Direction>();
        
        if (theNeighbors.get(getDirection().left()) != Terrain.WALL) {
            possDir.add(getDirection().left()); 
        } 
        
        if (theNeighbors.get(getDirection().right()) != Terrain.WALL) {
            possDir.add(getDirection().right());        
        } 
        
        if (theNeighbors.get(getDirection()) != Terrain.WALL) {
            possDir.add(getDirection());        
        }
        
        final int randomDir = random.nextInt(possDir.size());
        
        direction = possDir.get(randomDir);
        
        return direction;        
    }
}
