package com.purgadell.grizzly.Worlds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.purgadell.grizzly.Cameras.BoardCamera;
import com.purgadell.grizzly.Input.InputAction;
import com.purgadell.grizzly.Resources.Assets;
import com.purgadell.grizzly.Resources.Variables;
import com.purgadell.grizzly.Tiles.DungeonTile;
import com.purgadell.grizzly.Tiles.Tile;

/**
 * Created by Ryan English on 5/13/2017.
 */

public class GameBoard {

    private BoardCamera boardCamera;
    private Tile[][] boardTiles;

    private int boardWidth;
    private int boardHeight;

    public GameBoard(int w, int l){
        boardHeight = l;
        boardWidth = w;
        boardTiles = new Tile[w][l];
        boardCamera = new BoardCamera(800,600);

        generate();
    }

    private void generate(){
        float x = 0;
        float y = 0;

        for(int l = 0; l < boardHeight; l++){
            for(int w = 0; w < boardWidth; w++){
                boardTiles[w][l] = new DungeonTile(w,l,x,y);
                x += Variables.TILE_WIDTH;
            }

            if(l % 2 == 0) x = Variables.TILE_WIDTH / 2;
            else x = 0;

            //TODO: rce -> Figure this out, this is a hack
            y += 158;
        }

    }

    public void loadAssests(Assets assetManager){
        Texture t = assetManager.getTexture("Tiles/test_tile_1.png");
        for(int l = 0; l < boardHeight; l++){
            for(int w = 0; w < boardWidth; w++){
                boardTiles[w][l].setTileSprite(t);
            }
        }
    }

    public void handleInput(InputAction action){
        switch (action.actionCode){
            case InputAction.DRAGGED_MOUSE:
                boardCamera.panCamera(action.mouseCords);
                break;
            case InputAction.SCROLL_MOUSE:
                boardCamera.zoomCamera(null, action.zoomAmount);
        }
    }

    public void render(SpriteBatch batch){
        batch.setProjectionMatrix(boardCamera.getGameCamera().combined);

        for(int l = boardHeight-1; l > 0; l--){
            for(int w = boardWidth-1; w > 0; w--){
                boardTiles[w][l].render(batch);
            }
        }
    }

    public void update(float dt){
        for(int l = 0; l < boardHeight; l++){
            for(int w = 0; w < boardWidth; w++){
                boardTiles[w][l].update(dt);
            }
        }
    }

}
