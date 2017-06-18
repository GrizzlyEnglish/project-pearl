package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Obstructions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.Coordinates;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;

/**
 * Created by Ryan English on 6/10/2017.
 */

public abstract class Obstructions {

    private Tile onTile;

    private Sprite sprite;

    private Coordinates obstructCoords;

    private boolean isSolid;
    private boolean isVisible;

    public Obstructions(Coordinates c){
        this.obstructCoords = c;
        this.isVisible = true;
    }

    public Obstructions(Tile onTile){
        this.onTile = onTile;
        this.obstructCoords = onTile.getTileCoords();
        this.isVisible = true;
    }

    public abstract void update(float dt);

    public void render(SpriteBatch batch){
        if(isVisible) sprite.draw(batch);
    }

    public void setTile(Tile t){
        this.onTile = t;
    }

    public void setSprite(Texture t){
        this.sprite = new Sprite(t);
        this.sprite.setPosition(obstructCoords.position.x, obstructCoords.position.y);
    }

    public void setSpritePos(float x, float y){
        this.sprite.setPosition(x, y);
    }

    public void offsetSpritePos(float xOffset, float yOffset){
        this.sprite.setPosition(obstructCoords.position.x + xOffset, obstructCoords.position.y + yOffset);
    }

    public void resetSpritePos(){
        this.sprite.setPosition(obstructCoords.position.x, obstructCoords.position.y);
    }

    public float getX(){
        return obstructCoords.position.x;
    }

    public float getY(){
        return obstructCoords.position.y;
    }

    public int getBoardX(){
        return obstructCoords.coords.row;
    }

    public int getBoardY(){
        return obstructCoords.coords.column;
    }

}
