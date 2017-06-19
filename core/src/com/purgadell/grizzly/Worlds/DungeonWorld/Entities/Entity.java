package com.purgadell.grizzly.Worlds.DungeonWorld.Entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.purgadell.grizzly.Resources.Variables;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.Coordinates;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;

/**
 * Created by Ryan English on 6/17/2017.
 */

public abstract class Entity {

    protected Sprite sprite;

    protected Stats entityStats;

    protected Tile onTile;
    protected Tile targetTile;

    protected Coordinates coordinates;

    protected boolean isVisible;
    protected boolean isMoving;

    public Entity(Coordinates c){
        setupEntity(null, null, c);
    }

    public Entity(Tile t){
        setupEntity(null, t, null);
    }

    public Entity(Texture t){
        setupEntity(t, null, null);
    }

    public Entity(Texture t, Coordinates c){
        setupEntity(t, null, c);
    }

    public Entity(Texture tex, Tile tile){
        setupEntity(tex, tile, null);
    }

    private void setupEntity(Texture t, Tile tile, Coordinates c){
        this.isVisible = true;
        this.entityStats = new Stats();

        if(tile != null) {
            this.onTile = tile;
            setCoordinates(onTile);
        } else if(c != null) this.coordinates = c;

        if(t != null) setSprite(t);
    }

    public void setSprite(Texture t){
        this.sprite = new Sprite(t);
        setSpriteCoords();
    }

    private void setCoordinates(Tile t){
        Coordinates tCoords = t.getTileCoords();
        this.coordinates = new Coordinates(tCoords.position.x, tCoords.position.y + Variables.ENTITY_OFFSET);
    }

    private void setSpriteCoords(){
        setSpritePos(coordinates.position.x, coordinates.position.y);
    }

    public void resetSpritePos(){
        this.sprite.setPosition(coordinates.position.x, coordinates.position.y);
    }

    public void offsetSpritePos(float xOffset, float yOffset){
        this.sprite.setPosition(coordinates.position.x + xOffset, coordinates.position.y + yOffset);
    }

    private void setSpritePos(float x, float y){
        this.sprite.setPosition(x,y);
    }

    public void placeOnTile(Tile t){
        onTile.removeTileEntity();
        onTile = t;
        onTile.setTileEntity(this);

        setCoordinates(onTile);
        setSpriteCoords();
    }

    public boolean moveToTile(float dt){
        //TODO: rce - Animate the movement, return true when done
        return true;
    }

    public void update(float dt){
        if(isMoving && !moveToTile(dt)){
            return;
        }
    }

    public void render(SpriteBatch batch){
        if(isVisible) {
            sprite.draw(batch);
        }
    }

    public void renderWireFrame(ShapeRenderer test){
        Rectangle body = new Rectangle(coordinates.position.x, coordinates.position.y, sprite.getWidth(), sprite.getHeight());
        test.begin(ShapeRenderer.ShapeType.Line);
        test.setColor(Color.BLUE);
        test.rect(body.x,body.y,body.width,body.height);
        test.end();
    }

    public Coordinates getCoordinates(){
        return coordinates;
    }

    public void setCoordinates(Coordinates c){
        this.coordinates = c;
    }

    public Stats getEntityStats(){
        return entityStats;
    }

}
