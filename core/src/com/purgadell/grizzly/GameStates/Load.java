package com.purgadell.grizzly.GameStates;

import com.badlogic.gdx.Screen;

/**
 * Created by Ryan English on 5/13/2017.
 */

public class Load extends GameState {

    private boolean isFinished = false;

    public Load(GameStateManager manager) {
        super(manager);
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void loadAssets() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void debugRender(float dt) {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        isFinished = assetManager.loadQueuedAssets();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public boolean isFinished() { return isFinished; }

}
