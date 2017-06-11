package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Obstructions;

import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.Coordinates;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;

/**
 * Created by Ryan English on 6/11/2017.
 */

public class Containers extends Obstructions {

    public Containers(Coordinates c) {
        super(c);
    }

    public Containers(Tile onTile) {
        super(onTile);
    }

    @Override
    public void update(float dt) {

    }
}
