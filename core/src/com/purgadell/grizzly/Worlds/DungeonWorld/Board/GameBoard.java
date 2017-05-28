package com.purgadell.grizzly.Worlds.DungeonWorld.Board;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.purgadell.grizzly.Cameras.BoardCamera;
import com.purgadell.grizzly.Entities.Entity;
import com.purgadell.grizzly.Input.InputAction;
import com.purgadell.grizzly.PearlGame;
import com.purgadell.grizzly.Resources.Assets;
import com.purgadell.grizzly.Resources.Textures;
import com.purgadell.grizzly.Resources.Variables;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Generator.BoardGenerator;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.TileHighlighter;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.DungeonTile;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.VoidTile;

import java.util.Stack;

/**
 * Created by Ryan English on 5/13/2017.
 */

public class GameBoard {

    private BoardCamera boardCamera;
    private Tile[][] boardTiles;
    private TileHighlighter tileHighlighter;
    private Tile hoveredTile;

    private int boardWidth;
    private int boardHeight;

    public GameBoard(int w, int l){
        boardHeight = l;
        boardWidth = w;
        boardTiles = BoardGenerator.GenerateBoard(w,l);
        boardCamera = new BoardCamera(800,600);
    }

    public void loadAssets(Assets assetManager){
        Texture t = assetManager.getTexture(Textures.TEST_TILE);
        Texture v = assetManager.getTexture(Textures.TEST_VOID_TILE);

        for(int l = 0; l < boardHeight; l++){
            for(int w = 0; w < boardWidth; w++){
                if(boardTiles[w][l] != null)boardTiles[w][l].setTileSprite(t);
                else {
                    boardTiles[w][l] = new VoidTile(w,l);
                    boardTiles[w][l].setTileSprite(v);
                }
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
        Tile t = findTile(cords);

        if(hoveredTile != null) hoveredTile.setHovered(false);
        if(t != null) {
            System.out.println("Hovering Tile (" + t.getBoardX() + "," + t.getBoardY() + ")");
            hoveredTile = t;
            hoveredTile.setHovered(true);
        }
    }

    private Tile findTile(Vector3 cords){
        int height = (int)(cords.y / Variables.TILE_HEIGHT_OFFSET);

        if(height < boardHeight){
            for(int l = height; l >= 0; l--){
                for(int w = boardWidth-1; w >= 0; w--){
                    Tile t = boardTiles[w][l];
                    if(t == null) continue;
                    if(t.contains(cords.x, cords.y)){
                        return t;
                    }
                }
            }
        }

        return null;
    }

    private void selectTile(Vector3 cords){
        Tile t = findTile(cords);
        if(t != null) {
            t.toggleSelected();
            tileHighlighter.setTile(t);
        }
    }

    public void render(SpriteBatch batch){
        batch.setProjectionMatrix(boardCamera.getGameCamera().combined);
        for(int l = boardHeight-1; l >= 0; l--){
            for(int w = boardWidth-1; w >= 0; w--){
                Tile t = boardTiles[w][l];
                if(t == null) continue;
                if(boardCamera.contains(t.getPosX(), t.getPosY()))
                    t.render(batch);
            }
        }
        tileHighlighter.render(batch);

        if(PearlGame.WIRERENDER) debugRender();
    }

    private void debugRender(){
        PearlGame.WIRERENDERER.setProjectionMatrix(boardCamera.getGameCamera().combined);
        for(int l = boardHeight-1; l >= 0; l--){
            for(int w = boardWidth-1; w >= 0; w--){
                boardTiles[w][l].renderWireFrame(PearlGame.WIRERENDERER);
            }
        }
    }

    public void update(float dt){
        for(int l = 0; l < boardHeight; l++){
            for(int w = 0; w < boardWidth; w++){
                Tile t = boardTiles[w][l];
                if(t == null) continue;
                t.update(dt);
            }
        }
    }

}
