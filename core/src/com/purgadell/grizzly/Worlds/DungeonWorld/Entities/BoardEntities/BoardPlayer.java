package com.purgadell.grizzly.Worlds.DungeonWorld.Entities.BoardEntities;

import com.badlogic.gdx.graphics.Texture;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.Coordinates;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;
import com.purgadell.grizzly.Worlds.DungeonWorld.Entities.Entity;
import com.purgadell.grizzly.Worlds.DungeonWorld.Entities.Stats;

/**
 * Created by Ryan English on 6/17/2017.
 */

public class BoardPlayer extends Entity {

    public BoardPlayer(Coordinates c) {
        super(c);
    }

    public BoardPlayer(Tile t) {
        super(t);

        entityStats = new Stats();

        entityStats.Range = 12;
        entityStats.Speed = 5;
        entityStats.Weight = 12;
    }

    public BoardPlayer(Texture t) {
        super(t);
    }

    public BoardPlayer(Texture t, Coordinates c) {
        super(t, c);
    }

    public BoardPlayer(Texture tex, Tile tile) {
        super(tex, tile);
    }
}
