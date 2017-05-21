package com.purgadell.grizzly.Tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.purgadell.grizzly.Resources.Variables;

/**
 * Created by Ryan English on 5/13/2017.
 */

public abstract class Tile {

    private int boardX;
    private int boardY;

    private float posX;
    private float posY;

    private Triangle bottomLeft;
    private Triangle bottomRight;
    private Triangle topLeft;
    private Triangle topRight;

    private Sprite tileSprite;

    private boolean isSolid = false;
    private boolean isVisible = true;

    public Tile(int boardX, int boardY, float posX, float posY){
        this.boardX = boardX;
        this.boardY = boardY;
        this.posX = posX;
        this.posY = posY;
        buildAlphaTriangles();
    }

    private void buildAlphaTriangles(){
        float hexTop = 382;
        Vector2 bL = new Vector2(0,0);
        Vector2 bSplit = new Vector2(Variables.TILE_WIDTH/2,0);
        Vector2 bLt = new Vector2(0,Variables.TILE_HEIGHT-hexTop);
        bottomLeft = new Triangle(bL,bSplit, bLt);

        Vector2 bR = new Vector2(Variables.TILE_WIDTH,0);

        Vector2 tL = new Vector2(0,Variables.TILE_HEIGHT);

        Vector2 tR = new Vector2(Variables.TILE_WIDTH, Variables.TILE_HEIGHT);


        Vector2 tSplit = new Vector2(Variables.TILE_WIDTH/2,Variables.TILE_HEIGHT);


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
