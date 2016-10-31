/*
 * TCSS 305
 * Assignment 3 - Easy Street
 */

package model;

import java.util.Map;

/**
 * Abstract class that represents all vehicles.
 * @author Jasmine Dacones
 * @version 4/16/16
 *
 */
public abstract class AbstractVehicle implements Vehicle {
    
    /** Death time of vehicle. */
    private final int myDeathTime;
    
    /** x-coordinate of the vehicle's position. */
    private int myX;
    
    /** y-coordinate of the vehicle's position. */
    private int myY;
    
    /** X-coordinate starting point of the vehicle. */
    private final int myInitialX;

    /** Y-coordinate starting point of the vehicle. */
    private final int myInitialY;
    
    /** Vehicle's initial direction. */
    private Direction myDirection;
    
    /** Saves the vehicle's initial direction. */
    private final Direction myInitialDirection;
    
    /** Status of vehicle. */
    private boolean myAlive;
    
    /** Death time status of vehicle. */
    private int myPoke;


    
    /**
     * Constructor that creates a new vehicle.
     * 
     * @param theX x-coordinate of vehicle's position.
     * @param theY y-coordinate of vehicle's position.
     * @param theDirection direction the vehicle starts in.
     * @param theDeathTime death time of vehicle.
     * @param theAlive status of vehicle.
     */
    protected AbstractVehicle(final int theX, final int theY, 
                              final Direction theDirection, 
                              final int theDeathTime, 
                              final boolean theAlive) {
        myX = theX;
        myY = theY;
        myInitialX = theX;
        myInitialY = theY;
        myDirection = theDirection;
        myInitialDirection = theDirection;
        myDeathTime = theDeathTime;
        myAlive = theAlive;
    }
    
    /** 
     * Abstract method for the child class to implement. 
     * 
     * @param theTerrain the terrain the vehicle would like to pass through.
     * @param theLight the light the vehicle would like to pass through.
     * @return true if the vehicle can pass.
     */
    public abstract boolean canPass(Terrain theTerrain, Light theLight);
    
    /**
     * Abstract method for child class to implement.
     * 
     * @param theNeighbors map of neighboring terrains.
     * @return direction the vehicle will move in.
     */
    public abstract Direction chooseDirection(Map<Direction, Terrain> theNeighbors);
    
    /**
     * Called when this Vehicle collides with the specified 
     * other Vehicle.
     * 
     * @param theOther The other object.
     */
    public void collide(final Vehicle theOther) {
        if ((this.isAlive() && theOther.isAlive()) 
                        && (this.getDeathTime() > theOther.getDeathTime())) {    
            myAlive = false;
        }
    }
    
    /**
     * Returns death time of a vehicle.
     * 
     * @return death time of a vehicle.
     */
    public int getDeathTime() {
        return myDeathTime;
    }
    
    /**
     * Returns this Vehicle object's x-coordinate.
     * 
     * @return the x-coordinate.
     */
    public int getX() {
        return myX;
    }
    
    /**
     * Returns this Vehicle object's y-coordinate.
     * 
     * @return the y-coordinate.
     */
    public int getY() {
        return myY;
    }
    
    /**
     * Sets this object's x-coordinate to the given value.
     * 
     * @param theX The new x-coordinate.
     */
    public void setX(final int theX) {
        myX = theX;
    }
    
    /**
     * Sets this object's y-coordinate to the given value.
     * 
     * @param theY The new y-coordinate.
     */
    public void setY(final int theY) {
        myY = theY;
    }
    
    /**
     * Returns whether this Vehicle object is alive and 
     * should move on the map.
     * 
     * @return true if the object is alive, false otherwise.
     */
    public boolean isAlive() {
        return myAlive;     
    }
    
    /** 
     * Returns direction of vehicle.
     * 
     * @return direction of vehicle;
     */
    public Direction getDirection() {
        return myDirection;
    }
    
    /**
     * Sets the direction of the vehicle.
     * 
     * @param theDirection direction of vehicle.
     */
    public void setDirection(final Direction theDirection) {
        myDirection = theDirection;
    }

    /**
     * Called by the UI to notify a dead vehicle that 1 movement 
     * round has passed, so that it will become 1 move closer to revival.
     */
    public void poke() {
        if (!isAlive()) {
            myPoke++;
        }
        if (myPoke == getDeathTime()) {
            myPoke = 0;
            myAlive = true;
            setDirection(Direction.random());
        }
    }

    /**
     * Moves this vehicle back to its original position.
     */
    public void reset() {
        setX(myInitialX);
        setY(myInitialY);
        setDirection(myInitialDirection);
        myAlive = true;
        
    }
    
    /**
     * Returns image of vehicle.
     * 
     * @return image of vehicle.
     */
    public String getImageFileName() {
        final StringBuilder file = new StringBuilder();
        file.append(getClass().getSimpleName().toLowerCase());
        
        if (myAlive) {
            file.append(".gif");
        
        } else if (!myAlive) {
            file.append("_dead.gif");
        }   
        return file.toString();
    }
    
    /**
     * Overrides toString with coordinates of the vehicle's
     * position.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("( ");
        sb.append(myX);
        sb.append(", ");
        sb.append(myY);
        sb.append(" )");
        return sb.toString();
    }
}
