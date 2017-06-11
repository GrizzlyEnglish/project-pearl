package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Containers;

import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.Coordinates;

import java.util.LinkedList;

/**
 * Created by Ryan English on 6/10/2017.
 */

public class Path {

    private LinkedList<Coordinates> pathTiles;

    public Path(LinkedList<Coordinates> tiles){
        this.pathTiles = tiles;
    }

    public LinkedList<Coordinates> getPathTiles(){
        return pathTiles;
    }

}
