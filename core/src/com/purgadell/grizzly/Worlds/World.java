package com.purgadell.grizzly.Worlds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.purgadell.grizzly.GameStates.GameStateManager;
import com.purgadell.grizzly.Input.InputAction;
import com.purgadell.grizzly.Resources.Assets;

/**
 * Created by Ryan English on 5/13/2017.
 */

public abstract class World {

    protected GameStateManager Manager;
    protected ShapeRenderer wireRender;

    public World(GameStateManager manager){
        this.wireRender = new ShapeRenderer();
        this.Manager = manager;
    }

    public abstract void handleInput(InputAction action);
    public abstract void loadAssets(Assets assetManager);
    public abstract void update(float dt);
    public abstract void render(SpriteBatch batch);
    public abstract void resize(float width, float height);

}
