package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.purgadell.grizzly.Resources.Variables;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;

/**
 * Created by Ryan English on 5/21/2017.
 */

public class TileHighlighter {

    private Sprite Highlighter;
    private boolean isVisible;

    private Tile tile;

    private static final float OFFSET = 28;

    public TileHighlighter(Texture t, float x, float y){
        this.Highlighter = new Sprite(t);
        this.Highlighter.setPosition(x, y);
        this.isVisible = false;
    }

    public TileHighlighter(Texture t){
        this.Highlighter = new Sprite(t);
        this.isVisible = false;
    }

    public void setTile(Tile t){
        tile = t;
    }

    public void render(SpriteBatch batch){
        if(tile != null && tile.isSelected()){
            float x = tile.getPosX() - OFFSET;
            float y = tile.isHovered() ? tile.getPosY() + Variables.HOVER_OFFSET : tile.getPosY();
            Highlighter.setPosition(x, y);
            Highlighter.draw(batch);
        }
    }

    public void removeTile(){
        tile = null;
    }

}
