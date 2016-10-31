/*
 * TCSS 305
 * Assignment 3 - Easy Street
 */

package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Direction;
import model.Light;
import model.Terrain;
import model.Truck;

import org.junit.Test;


/**
 * Unit test for Truck class.
 * @author Jasmine Dacones
 * @version 4/22/16
 *
 */
public class TruckTest {

    /** Tries for randomness. */
    private static final int TRIES_FOR_RANDOMNESS = 50;

    /**
     * Tests the truck setter method.
     */
    @Test
    public void testTruckSetters() {
        final Truck truck = new Truck(10, 11, Direction.WEST);
        
        assertEquals("Truck x coordinate not initialized correctly!", 
                     10, truck.getX());
        assertEquals("Truck y coordinate not initialized correctly!", 
                     11, truck.getY());
        assertEquals("Truck direction not initialized correctly!",
                     Direction.WEST, truck.getDirection());
        assertEquals("Truck death time not initialized correctly!", 0 , truck.getDeathTime());
        assertTrue("Truck isAlive() fails initially!", truck.isAlive());
    }
    
    /**
     * Test method for canPass().
     */
    @Test
    public void testCanPass() {
        
        // Trucks can move to STREET or LIGHT or CROSSWALK
        
        // Trucks should NOT choose to move to other terrain types
        
        // Trucks should only reverse direction if no other option is available
        
        final ArrayList<Terrain> validTerrain = new ArrayList<Terrain>();
        validTerrain.add(Terrain.STREET);
        validTerrain.add(Terrain.LIGHT);
        validTerrain.add(Terrain.CROSSWALK);
                
        final Truck truck = new Truck(10, 11, Direction.NORTH);
        // tests each terrain as a destination type
        for (final Terrain destinationTerrain : Terrain.values()) {
            // tries the test under each light condition
            for (final Light currentLightCondition : Light.values()) {
                if (destinationTerrain == Terrain.STREET) {
                
                    // trucks can pass STREET under any light condition
                    assertTrue("Truck should be able to pass STREET"
                               + ", with light " + currentLightCondition,
                               truck.canPass(destinationTerrain, currentLightCondition));
                
                } else if (destinationTerrain == Terrain.CROSSWALK) {
                           // trucks can pass CROSSWALK
                           // if the light is YELLOW or GREEN but not RED

                    if (currentLightCondition == Light.RED) {
                        assertFalse("Truck should NOT be able to pass " + destinationTerrain
                            + ", with light " + currentLightCondition,
                            truck.canPass(destinationTerrain,
                                          currentLightCondition));
                    
                    } else { // light is yellow or red
                        assertTrue("Truck should be able to pass " + destinationTerrain
                            + ", with light " + currentLightCondition,
                            truck.canPass(destinationTerrain,
                                          currentLightCondition));
                    }
                    
                } else if (destinationTerrain == Terrain.LIGHT) {
                            // trucks can pass any LIGHT
                    assertTrue("Human should be able to pass GRASS"
                               + ", with light " + currentLightCondition,
                               truck.canPass(destinationTerrain, currentLightCondition));
                
                } else if (!validTerrain.contains(destinationTerrain)) {
 
                    assertFalse("Truck should NOT be able to pass " + destinationTerrain
                        + ", with light " + currentLightCondition,
                        truck.canPass(destinationTerrain, currentLightCondition));
                }
            } 
        }
    } 
    
    
    /**
     * Test method for chooseDirection() when there are streets.
     */
    @Test
    public void testChooseDirectionStreet() {
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(Direction.WEST, Terrain.STREET);
        neighbors.put(Direction.NORTH, Terrain.STREET);
        neighbors.put(Direction.EAST, Terrain.STREET);
        neighbors.put(Direction.SOUTH, Terrain.WALL);
        
        boolean seenWest = false;
        boolean seenNorth = false;
        boolean seenEast = false;
        boolean seenSouth = false;
        
        final Truck truck = new Truck(6, 11, Direction.NORTH);
        
        for (int count = 0; count < TRIES_FOR_RANDOMNESS; count++) {
            final Direction d = truck.chooseDirection(neighbors);
            
            if (d == Direction.WEST) {
                seenWest = true;
            } else if (d == Direction.NORTH) {
                seenNorth = true;
            } else if (d == Direction.EAST) {
                seenEast = true;
            } else if (d == Direction.SOUTH) { // this should NOT be chosen
                seenSouth = true;
            }
        }
        
        assertTrue("Truck randomly chose a valid direction and didn't go reverse", 
               seenWest || seenNorth || seenEast);
            
        assertFalse("Truck chooseDirection() reversed direction when not necessary!",
                    seenSouth);
    }
    

    
    /**
     * Test method for chooseDirection() when there are lights.
     */
    @Test
    public void testChooseDirectionLight() {
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(Direction.WEST, Terrain.LIGHT);
        neighbors.put(Direction.NORTH, Terrain.LIGHT);
        neighbors.put(Direction.EAST, Terrain.LIGHT);
        neighbors.put(Direction.SOUTH, Terrain.STREET);
        
        boolean seenWest = false;
        boolean seenNorth = false;
        boolean seenEast = false;
        boolean seenSouth = false;
        
        final Truck truck = new Truck(6, 11, Direction.NORTH);
        
        for (int count = 0; count < TRIES_FOR_RANDOMNESS; count++) {
            final Direction d = truck.chooseDirection(neighbors);
            
            if (d == Direction.WEST) {
                seenWest = true;
            } else if (d == Direction.NORTH) {
                seenNorth = true;
            } else if (d == Direction.EAST) {
                seenEast = true;
            } else if (d == Direction.SOUTH) { // this should NOT be chosen
                seenSouth = true;
            }
        }
        
        assertTrue("Truck randomly chose a valid direction and didn't go reverse", 
               seenWest || seenNorth || seenEast);
            
        assertFalse("Truck chooseDirection() reversed direction when not necessary!",
                    seenSouth);
    }
    
    /**
     * Test method for chooseDirection() when there are crosswalks.
     */
    @Test
    public void testChooseDirectionCrosswalk() {
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(Direction.WEST, Terrain.CROSSWALK);
        neighbors.put(Direction.NORTH, Terrain.CROSSWALK);
        neighbors.put(Direction.EAST, Terrain.CROSSWALK);
        neighbors.put(Direction.SOUTH, Terrain.LIGHT);
        
        boolean seenWest = false;
        boolean seenNorth = false;
        boolean seenEast = false;
        boolean seenSouth = false;
        
        final Truck truck = new Truck(6, 11, Direction.NORTH);
        
        for (int count = 0; count < TRIES_FOR_RANDOMNESS; count++) {
            final Direction d = truck.chooseDirection(neighbors);
            
            if (d == Direction.WEST) {
                seenWest = true;
            } else if (d == Direction.NORTH) {
                seenNorth = true;
            } else if (d == Direction.EAST) {
                seenEast = true;
            } else if (d == Direction.SOUTH) { // this should NOT be chosen
                seenSouth = true;
            }
        }
        
        assertTrue("Truck randomly chose a valid direction and didn't go reverse", 
               seenWest || seenNorth || seenEast);
            
        assertFalse("Truck chooseDirection() reversed direction when not necessary!",
                    seenSouth);
    }
    
    /**
     * Test method for chooseDirection() when the truck must reverse.
     */
    @Test
    public void testChooseDirectionMustReverse() {
        
        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.STREET && t != Terrain.LIGHT && t != Terrain.CROSSWALK) {
                
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, t);
                neighbors.put(Direction.SOUTH, Terrain.STREET);
                
                final Truck truck = new Truck(0, 0, Direction.NORTH);
                
                // the Truck must reverse and go SOUTH
                assertEquals("Truck chooseDirection() failed "
                                + "when reverse was the only valid choice!",
                             Direction.SOUTH, truck.chooseDirection(neighbors));
            }                
        }
    }
}
