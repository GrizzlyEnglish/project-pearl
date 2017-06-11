package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Obstructions;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.Coordinates;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;

/**
 * Created by Ryan English on 6/11/2017.
 */

public class Chest extends Obstructions {

    private Sprite alternateSprite;
    private boolean isOpen;

    public Chest(Coordinates c) {
        super(c);
    }

    public Chest(Tile onTile) {
        super(onTile);
    }

    @Override
    public void update(float dt) {

    }
}
