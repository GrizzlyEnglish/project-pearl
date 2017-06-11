package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Obstructions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.Coordinates;

/**
 * Created by Ryan English on 6/10/2017.
 */

public abstract class Obstructions {

    private Sprite sprite;

    private Coordinates obstructCoords;

    private boolean isSolid;
    private boolean isVisible;

    public Obstructions(Coordinates c){
        this.obstructCoords = c;
    }

    public abstract void update(float dt);

    public void render(SpriteBatch batch){
        if(isVisible) sprite.draw(batch);
    }

    public void setSprite(Texture t){
        this.sprite = new Sprite(t);
        this.sprite.setPosition(obstructCoords.position.x, obstructCoords.position.y);
    }

}
