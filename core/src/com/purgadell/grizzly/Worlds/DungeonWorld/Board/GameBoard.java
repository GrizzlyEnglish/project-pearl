package com.purgadell.grizzly.Worlds.DungeonWorld.Board;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.purgadell.grizzly.Cameras.BoardCamera;
import com.purgadell.grizzly.Input.InputAction;
import com.purgadell.grizzly.Resources.Assets;
import com.purgadell.grizzly.Resources.Textures;
import com.purgadell.grizzly.Resources.Variables;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.DungeonTile;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;

/**
 * Created by Ryan English on 5/13/2017.
 */

public class GameBoard {

    private BoardCamera boardCamera;
    private Tile[][] boardTiles;

    private int boardWidth;
    private int boardHeight;

    private ShapeRenderer testing;

    public GameBoard(int w, int l){
        boardHeight = l;
        boardWidth = w;
        boardTiles = new Tile[w][l];
        boardCamera = new BoardCamera(800,600);
        testing = new ShapeRenderer();
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
            y += Variables.TILE_HEIGHT/2 + 15;
        }

    }

    public void loadAssets(Assets assetManager){
        Texture t = assetManager.getTexture(Textures.TEST_TILE);
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
                break;
            case InputAction.CLICKED_MOUSE:
                Vector3 unprojectedCords = boardCamera.unprojectCords(action.mouseCords);
                selectTile(unprojectedCords);
        }
    }

    private void selectTile(Vector3 cords){
        System.out.println("Clicked at X: " + cords.x + " Y: " + cords.y);
        boolean found = false;
        for(int l = boardHeight-1; l >= 0; l--){
            for(int w = boardWidth-1; w >= 0; w--){
                Tile t = boardTiles[w][l];
                if((found = t.checkSelected(cords.x, cords.y))) {
                    t.setVisible(false);
                    break;
                }
            }

            if(found) break;
        }
    }

    public void render(SpriteBatch batch){
        batch.setProjectionMatrix(boardCamera.getGameCamera().combined);
        batch.begin();
        for(int l = boardHeight-1; l >= 0; l--){
            for(int w = boardWidth-1; w >= 0; w--){
                boardTiles[w][l].render(batch);
            }
        }
        batch.end();

        testing.setProjectionMatrix(boardCamera.getGameCamera().combined);
        for(int l = boardHeight-1; l >= 0; l--){
            for(int w = boardWidth-1; w >= 0; w--){
                boardTiles[w][l].renderWireFrame(testing);
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
