package com.purgadell.grizzly.Worlds.DungeonWorld.Board;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.purgadell.grizzly.Cameras.BoardCamera;
import com.purgadell.grizzly.Input.InputAction;
import com.purgadell.grizzly.PearlGame;
import com.purgadell.grizzly.Resources.Assets;
import com.purgadell.grizzly.Resources.Textures;
import com.purgadell.grizzly.Resources.Variables;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Generator.BoardGenerator;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.Coordinates;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.TileHighlighter;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Containers.*;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.DungeonTile;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.VoidTile;

import java.util.LinkedList;

/**
 * Created by Ryan English on 5/13/2017.
 */

public class GameBoard {

    private LinkedList<Tile> boardTiles;
    private LinkedList<Room> boardRooms;
    private LinkedList<Path> boardPaths;

    private BoardCamera boardCamera;

    private TileHighlighter tileHighlighter;
    private Tile hoveredTile;

    private int boardWidth;
    private int boardHeight;

    public GameBoard(int w, int l){
        boardHeight = l;
        boardWidth = w;
        boardCamera = new BoardCamera(800,600);

        boardRooms = new LinkedList<Room>();
        boardPaths = new LinkedList<Path>();
    }

    public void loadAssets(Assets assetManager){
        Texture texture = assetManager.getTexture(Textures.TEST_TILE_DUNGEON);
        Texture voidTexture = assetManager.getTexture(Textures.TEST_VOID_TILE);

        for(Tile t : boardTiles){
            if(t instanceof DungeonTile) t.setTileSprite(texture);
            else t.setTileSprite(voidTexture);
        }

        texture = assetManager.getTexture(Textures.TEST_SELECTED);
        tileHighlighter = new TileHighlighter(texture);
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
            hoveredTile = t;
            hoveredTile.setHovered(true);
        }
    }

    private Tile findTile(Vector3 cords){
        //TODO: rce--Speed this up
        for(Tile t : boardTiles){
            if(t == null) continue;
            if(t.contains(cords.x, cords.y)){
                return t;
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
        for(Tile t : boardTiles){
            if(t == null) continue;
            if(boardCamera.contains(t.getPosX(), t.getPosY()))
                t.render(batch);
        }
        tileHighlighter.render(batch);

        if(PearlGame.WIRERENDER) debugRender();
    }

    private void debugRender(){
        PearlGame.WIRERENDERER.setProjectionMatrix(boardCamera.getGameCamera().combined);
        for(Tile t : boardTiles){
            if(t == null) continue;
            if(boardCamera.contains(t.getPosX(), t.getPosY()))
                t.renderWireFrame(PearlGame.WIRERENDERER);
        }
    }

    public void update(float dt){
        for(Tile t : boardTiles){
            if(t == null) continue;
            t.update(dt);
        }
    }

    public void addRoom(Room r){
        boardRooms.add(r);
    }

    public void addPath(Path p){
        boardPaths.push(p);
    }

    public LinkedList<Room> getBoardRoooms(){
        return boardRooms;
    }

    public LinkedList<Path> getBoardPaths(){
        return boardPaths;
    }

    public int getBoardWidth(){
        return boardWidth;
    }

    public int getBoardHeight(){
        return boardHeight;
    }

    public void setTileHighlighter(TileHighlighter th){
        this.tileHighlighter = th;
    }

    public void setBoardTiles(LinkedList<Tile> tiles){
        this.boardTiles = tiles;
    }

}
