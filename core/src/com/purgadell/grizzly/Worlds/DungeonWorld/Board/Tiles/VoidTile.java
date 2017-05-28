package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.purgadell.grizzly.PearlGame;

/**
 * Created by Ryan English on 5/27/2017.
 */

public class VoidTile extends Tile {
    public VoidTile(int boardX, int boardY, float posX, float posY) {
        super(boardX, boardY, posX, posY);
    }

    public VoidTile(int boardX, int boardY) {
        super(boardX, boardY);
    }

    @Override
    public boolean contains(float x, float y) {
        return false;
    }

    @Override
    public void render(SpriteBatch batch) {
        if(PearlGame.DEBUGRENDER) super.render(batch);
    }

    @Override
    public void extraUpdate(float delta) {

    }
}
