package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Ryan English on 6/20/2017.
 */

public class HighlightTile {

    private Animation<Texture> tileAnimation;
    private Texture tileTexture;

    private Tile overTile;

    private float animationStep = 0;
    private static final float SELECTED_OFFSET = 25;

    public HighlightTile(Tile t, Texture tex){
        this.overTile = t;
        this.tileTexture = tex;
    }

    public HighlightTile(Tile t, Texture[] tex){
        this.overTile = t;
        this.tileAnimation = new Animation<Texture>(1/15f, tex);
    }

    public void update(float dt){
        this.animationStep += dt;
    }

    public void render(SpriteBatch batch){
        float x = overTile.getPosX();
        float y = overTile.isHovered() ? overTile.getPosY() + SELECTED_OFFSET : overTile.getPosY();

        if(tileAnimation != null){
            batch.draw(tileAnimation.getKeyFrame(animationStep, false), overTile.getPosX()-12, overTile.getPosY());
        } else {
            batch.draw(tileTexture, x - SELECTED_OFFSET, y);
        }
    }

    public Tile getTile(){
        return overTile;
    }

}
