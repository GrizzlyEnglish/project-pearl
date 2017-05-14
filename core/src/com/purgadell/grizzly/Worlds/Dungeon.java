package com.purgadell.grizzly.Worlds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.purgadell.grizzly.GameStates.GameStateManager;
import com.purgadell.grizzly.Input.InputAction;
import com.purgadell.grizzly.Resources.Assets;

/**
 * Created by Ryan English on 5/13/2017.
 */

public class Dungeon extends World{

    private GameBoard board;

    public Dungeon(GameStateManager manager) {
        super(manager);

        buildBoard();
    }

    @Override
    public void handleInput(InputAction action) {
        board.handleInput(action);
    }

    public void buildBoard(){
        board = new GameBoard(75, 75);
    }

    @Override
    public void loadAssets(Assets assetManager) {
        board.loadAssests(assetManager);
    }

    @Override
    public void update(float dt)
    {
        board.update(dt);
    }

    @Override
    public void render(SpriteBatch batch)
    {
        board.render(batch);
    }
}
