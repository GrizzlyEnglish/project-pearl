package com.purgadell.grizzly.GameStates;

import com.purgadell.grizzly.Input.InputAction;
import com.purgadell.grizzly.Resources.Textures;
import com.purgadell.grizzly.Worlds.DungeonWorld.Dungeon;

/**
 * Created by Ryan English on 5/13/2017.
 */

public class Play extends GameState {

    private Dungeon dungeonWorldMap;

    public Play(GameStateManager manager) {
        super(manager);
        assetManager.queTextures(Textures.TEST_PACK);
        dungeonWorldMap = new Dungeon(manager);
    }

    @Override
    public void handleInput() {
        while(!inputHandler.actions.isEmpty()){
            InputAction ia = inputHandler.actions.pop();

            //Figure out who I need to give action too
            dungeonWorldMap.handleInput(ia);
        }
    }

    @Override
    public void update(float dt) {
        handleInput();

        dungeonWorldMap.update(dt);
    }

    @Override
    public void render(float delta) {
//        spriteBatch.begin();

        dungeonWorldMap.render(spriteBatch);

//        spriteBatch.end();
    }

    @Override
    public void loadAssets() {
        dungeonWorldMap.loadAssets(assetManager);
    }

    @Override
    public void show() {

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
}
