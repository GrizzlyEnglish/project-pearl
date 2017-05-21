package com.purgadell.grizzly.GameStates;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.purgadell.grizzly.Input.InputHandler;
import com.purgadell.grizzly.Resources.Assets;

/**
 * Created by Ryan English on 5/13/2017.
 */

public abstract class GameState implements Screen {

    protected SpriteBatch spriteBatch;
    protected Assets assetManager;
    protected InputHandler inputHandler;

    public GameState(GameStateManager manager){
        this.spriteBatch = manager.getGame().getSpriteBatch();
        this.assetManager = manager.getGame().getAssetManager();
        this.inputHandler = manager.getGame().getInputHandler();
    }

    public abstract void handleInput();

    public abstract void loadAssets();

    public abstract void update(float dt);

    public abstract void debugRender(float dt);

}
