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
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.TileHighlighter;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.DungeonTile;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;

/**
 * Created by Ryan English on 5/13/2017.
 */

public class GameBoard {

    private BoardCamera boardCamera;
    private Tile[][] boardTiles;
    private TileHighlighter tileHighlighter;

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

        t = assetManager.getTexture(Textures.TEST_SELECTED);
        tileHighlighter = new TileHighlighter(t);
    }

    public void handleInput(InputAction action){
        Vector3 unprojectedCords;

        switch (action.actionCode){
            case InputAction.DRAGGED_MOUSE:
                boardCamera.panCamera(action.mouseCords);
                break;
            case InputAction.SCROLL_MOUSE:
                boardCamera.zoomCamera(null, action.zoomAmount);
                break;
            case InputAction.CLICKED_MOUSE:
                unprojectedCords = boardCamera.unprojectCords(action.mouseCords);
                selectTile(unprojectedCords);
                break;
            case InputAction.MOUSE:
                unprojectedCords = boardCamera.unprojectCords(action.mouseCords);
                hoverTile(unprojectedCords);
                break;
        }
    }

    private void hoverTile(Vector3 cords){
        boolean found = false;
        int height = (int)(cords.y / Variables.TILE_HEIGHT) + 3;

        for(int l = height; l >= 0; l--){
            for(int w = boardWidth-1; w >= 0; w--){
                Tile t = boardTiles[w][l];
                if(!found){
                    found = t.contains(cords.x, cords.y);
                    t.setHovered(found);
                } else t.setHovered(false);
            }
        }
    }

    private void selectTile(Vector3 cords){
        boolean found = false;
        int height = (int)(cords.y / Variables.TILE_HEIGHT) + 3;

        for(int l = height; l >= 0; l--){
            for(int w = boardWidth-1; w >= 0; w--){
                Tile t = boardTiles[w][l];
                if((found = t.contains(cords.x, cords.y))) {
                    boolean highlight = t.toggleSelected();
                    if(highlight) tileHighlighter.setTile(t);
                    break;
                }
            }

            if(found) break;
        }
    }

    public void render(SpriteBatch batch){
        batch.setProjectionMatrix(boardCamera.getGameCamera().combined);
        for(int l = boardHeight-1; l >= 0; l--){
            for(int w = boardWidth-1; w >= 0; w--){
                Tile t = boardTiles[w][l];
                if(boardCamera.contains(t.getPosX(), t.getPosY()))
                    t.render(batch);
            }
        }
        tileHighlighter.render(batch);
    }

    public void debugRender(ShapeRenderer wireRender){
        wireRender.setProjectionMatrix(boardCamera.getGameCamera().combined);
        for(int l = boardHeight-1; l >= 0; l--){
            for(int w = boardWidth-1; w >= 0; w--){
                boardTiles[w][l].renderWireFrame(wireRender);
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
