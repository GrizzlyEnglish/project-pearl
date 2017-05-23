package com.purgadell.grizzly.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;

/**
 * Created by Ryan English on 5/22/2017.
 */

public abstract class Entity {

    private Tile onTile;

    private Sprite entitySprite;

    private int attackDmg;
    private int magicDmg;

    private int physDefense;
    private int magicDefense;

    private int speed;
    private int wieght;

    public Entity(Texture t){
        this.entitySprite = new Sprite(t);
    }

    public void moveTile(Tile t){
        onTile = t;
        entitySprite.setPosition(onTile.getPosX(), onTile.getPosY());
    }

    public abstract void update(float dt);

    public void render(SpriteBatch batch){
        if(onTile.isVisible()){
            entitySprite.draw(batch);
        }
    }

}
