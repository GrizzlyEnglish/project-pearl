package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Containers;

import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.Coordinates;

import java.util.LinkedList;

/**
 * Created by Ryan English on 5/28/2017.
 */

public class Room {

    private LinkedList<Coordinates> roomTiles;

    private Coordinates centerTile;
    private int radius;

    public Room(LinkedList<Coordinates> room, Coordinates center, int radius){
        this.roomTiles = room;
        this.centerTile = center;
        this.radius = radius;
    }

    public Coordinates getCenterTile() {
        return centerTile;
    }

    public LinkedList<Coordinates> getRoom(){
        return roomTiles;
    }

    public int getRadius(){
        return radius;
    }

}
