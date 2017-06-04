package com.purgadell.grizzly;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.purgadell.grizzly.GameStates.GameStateManager;
import com.purgadell.grizzly.Input.InputHandler;
import com.purgadell.grizzly.Resources.Assets;

public class PearlGame extends ApplicationAdapter {

    public static boolean DEBUGRENDER = true;
    public static boolean WIRERENDER = false;
    public static boolean DEBUGLOCATION = true;
    public static ShapeRenderer WIRERENDERER;
    public static BitmapFont FONT;

    Assets assetManager;
    SpriteBatch spriteBatch;
	GameStateManager gManager;
    InputHandler inputHandler;

	@Override
	public void create () {
        WIRERENDERER  = new ShapeRenderer();
        FONT = new BitmapFont();

        spriteBatch = new SpriteBatch();
        assetManager = new Assets();
        inputHandler = new InputHandler();

        gManager = new GameStateManager(this);
        gManager.pushState(GameStateManager.PLAY);

        Gdx.input.setInputProcessor(inputHandler);
	}

	@Override
	public void render () {
		Gdx.graphics.setTitle(Gdx.graphics.getFramesPerSecond() + " Frames");
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float dt = Gdx.graphics.getDeltaTime();

        spriteBatch.begin();

        gManager.update(dt);
        gManager.render(dt);

        spriteBatch.end();
	}
	
	@Override
	public void dispose () {
        spriteBatch.dispose();
        assetManager.dispose();
	}

	public SpriteBatch getSpriteBatch() { return spriteBatch; }
    public Assets getAssetManager() { return assetManager; }
    public InputHandler getInputHandler() {return inputHandler;}
}
