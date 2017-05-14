package com.purgadell.grizzly.Tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Ryan English on 5/13/2017.
 */

public abstract class Tile {

    private int boardX;
    private int boardY;

    private float posX;
    private float posY;

    private Sprite tileSprite;

    private boolean isSolid = false;
    private boolean isVisible = true;

    public Tile(int boardX, int boardY, float posX, float posY){
        this.boardX = boardX;
        this.boardY = boardY;
        this.posX = posX;
        this.posY = posY;
    }

    public abstract void update(float delta);

    public void setTileSprite(Texture t){
        this.tileSprite = new Sprite(t);
        this.tileSprite.setPosition(posX, posY);
    }

    public void render(SpriteBatch batch){
        if(isVisible){
            tileSprite.draw(batch);
        }
    }

}
