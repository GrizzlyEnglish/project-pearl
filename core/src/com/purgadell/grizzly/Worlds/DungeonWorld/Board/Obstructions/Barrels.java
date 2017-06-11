package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Obstructions;

import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.Coordinates;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;

/**
 * Created by Ryan English on 6/10/2017.
 */

public class Barrels extends Obstructions {

    public Barrels(Tile t) {
        super(t);
    }

    public Barrels(Coordinates c) {
        super(c);
    }

    @Override
    public void update(float dt) {

    }
}
