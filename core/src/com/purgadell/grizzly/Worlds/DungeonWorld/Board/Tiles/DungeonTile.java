package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles;

import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.Coordinates;

/**
 * Created by Ryan English on 5/13/2017.
 */

public class DungeonTile extends Tile {

    public DungeonTile(int boardX, int boardY, float posX, float posY) {
        super(boardX, boardY, posX, posY);
    }

    public DungeonTile(int boardX, int boardY){
        super(boardX, boardY);
    }

    public DungeonTile(Coordinates c){
        super(c);
    }

    @Override
    public void extraUpdate(float delta) {

    }


}
