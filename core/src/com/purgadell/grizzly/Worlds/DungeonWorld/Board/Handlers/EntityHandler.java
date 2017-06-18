package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Handlers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.purgadell.grizzly.Resources.Assets;
import com.purgadell.grizzly.Resources.Textures;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.GameBoard;
import com.purgadell.grizzly.Worlds.DungeonWorld.Entities.Entity;

import java.util.LinkedList;

/**
 * Created by Ryan English on 6/17/2017.
 */

public class EntityHandler {

    private GameBoard gameBoard;
    private LinkedList<Entity> boardEntities;

    public EntityHandler(){
        boardEntities = new LinkedList<Entity>();
    }

    public void loadAssets(Assets assetManager){
        Texture t = assetManager.getTexture(Textures.TEST_CHAR1);
        for(Entity e : boardEntities){
            e.setSprite(t);
        }
    }

    public void addEntity(Entity e){
        boardEntities.push(e);
    }

    public void removeEntity(Entity e){
        boardEntities.remove(e);
    }

    public void update(float dt){
        for(Entity e : boardEntities){
            e.update(dt);
        }
    }

    public void render(SpriteBatch batch){
        for(Entity e : boardEntities){
            e.render(batch);
        }
    }

    public void renderWireFrame(ShapeRenderer shapeRenderer){
        for(Entity e : boardEntities){
            e.renderWireFrame(shapeRenderer);
        }
    }

}
