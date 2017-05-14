package com.purgadell.grizzly.GameStates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.purgadell.grizzly.PearlGame;
import com.purgadell.grizzly.Resources.Assets;

import java.util.Stack;

/**
 * Created by Ryan English on 5/13/2017.
 */

public class GameStateManager {

    public static final int MENU = 1;
    public static final int PLAY = 2;
    public static final int PAUSED = 3;

    private PearlGame Game;

    private Stack<GameState> gameStates;
    private Load loadingState;

    public GameStateManager(PearlGame game) {
        this.Game = game;
        gameStates = new Stack<GameState>();
    }

    public void update(float delta){
        if (loadingState == null) gameStates.peek().update(delta);
    }

    public void render(float delta){
        if (loadingState != null){
            loadingState.render(delta);
            if(loadingState.isFinished()){
                gameStates.peek().loadAssets();
                loadingState = null;
            }
        }
        else gameStates.peek().render(delta);
    }

    public void setState(int state) {
        popState();
        pushState(state);
    }

    public void pushState(int state){
        GameState gState = getState(state);
        gameStates.push(gState);
    }

    public void popState(){
        GameState g = gameStates.pop();
        g.dispose();
    }

    private GameState getState(int state){
        switch(state){
            case MENU: return new Play(this);
            case PLAY: {
                //TODO: rce - make the loading more dynamic?
                loadingState = new Load(this);
                return new Play(this);
            }
            case PAUSED: return new Play(this);
        }

        return null;
    }

    public PearlGame getGame() { return Game; }

}
