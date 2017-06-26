package com.purgadell.grizzly.Worlds.DungeonWorld.Board;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.purgadell.grizzly.Cameras.BoardCamera;
import com.purgadell.grizzly.Input.InputAction;
import com.purgadell.grizzly.PearlGame;
import com.purgadell.grizzly.Resources.Assets;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Handlers.BoardHighlighterHelper;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Handlers.BoardInputHandler;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Handlers.BoardMovementHandler;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Handlers.EntityHandler;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.Coordinates;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.TileGetter;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Containers.*;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Obstructions.Obstructions;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;
import com.purgadell.grizzly.Worlds.DungeonWorld.Entities.Entity;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Ryan English on 5/13/2017.
 */

public class GameBoard {

    private LinkedList<Tile> boardTiles;
    private LinkedList<Room> boardRooms;
    private LinkedList<Path> boardPaths;

    private BoardCamera boardCamera;
    private BoardInputHandler boardInputHandler;
    private BoardHighlighterHelper boardHighlighter;
    private BoardMovementHandler boardMovement;

    private TileGetter tileGetter;
    private Tile hoveredTile;

    private EntityHandler entityHandler;

    private int boardWidth;
    private int boardHeight;

    public GameBoard(int w, int l){
        boardHeight = l;
        boardWidth = w;

        boardCamera = new BoardCamera(800,600);
        tileGetter = new TileGetter(w, l);
        boardInputHandler = new BoardInputHandler(this);
        boardHighlighter = new BoardHighlighterHelper(this);
        boardMovement = new BoardMovementHandler(this);

        boardRooms = new LinkedList<Room>();
        boardPaths = new LinkedList<Path>();

        entityHandler = new EntityHandler();
    }

    public void loadAssets(Assets assetManager){
        for(Tile t : boardTiles){
            Texture texture = assetManager.getTexture(t);
            t.setTileSprite(texture);
            Obstructions obs = t.getTileObstruction();
            if(obs != null){
                Texture obsTexture = assetManager.getTexture(obs);
                obs.setSprite(obsTexture);
            }
        }

        entityHandler.loadAssets(assetManager);
        boardHighlighter.loadAssets(assetManager);
    }

    public void resize(float width, float height){
        boardCamera.resize(width, height);
    }

    public void handleInput(InputAction action){
        boardInputHandler.handleInput(action);
    }

    public void render(SpriteBatch batch){
        batch.setProjectionMatrix(boardCamera.getGameCamera().combined);

        renderTiles(batch);
        boardHighlighter.render(batch);
        renderEntities(batch);
    }

    private void renderTiles(SpriteBatch batch){
        Iterator<Tile> iterator = boardTiles.descendingIterator();

        while(iterator.hasNext()){
            Tile t = iterator.next();

            if(t == null) continue;
            if(boardCamera.contains(t.getPosX(), t.getPosY()))
                t.render(batch);
        }

        if(PearlGame.WIRERENDER) debugRender();
    }

    private void renderEntities(SpriteBatch batch){
        entityHandler.render(batch);
    }

    private void debugRender(){
        PearlGame.WIRERENDERER.setProjectionMatrix(boardCamera.getGameCamera().combined);
        for(Tile t : boardTiles){
            if(t == null) continue;
            if(boardCamera.contains(t.getPosX(), t.getPosY()))
                t.renderWireFrame(PearlGame.WIRERENDERER);
        }
        entityHandler.renderWireFrame(PearlGame.WIRERENDERER);
    }

    public void update(float dt){
        for(Tile t : boardTiles){
            if(t == null) continue;
            t.update(dt);
        }
        boardHighlighter.update(dt);
        boardMovement.update(dt);
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

    public void setBoardTiles(LinkedList<Tile> tiles){
        this.boardTiles = tiles;
    }

    public LinkedList<Tile> getBoardTiles(){
        return boardTiles;
    }

    public void setEntities(LinkedList<Entity> entities){
        for(Entity e : entities) entityHandler.addEntity(e);
    }

    public BoardCamera getBoardCamera(){
        return boardCamera;
    }

    public void setHoveredTile(Tile t){
        this.hoveredTile = t;
    }

    public Tile getHoveredTile(){
        return this.hoveredTile;
    }

    public TileGetter getTileGetter(){
        return tileGetter;
    }

    public BoardHighlighterHelper getBoardHighlighter(){
        return boardHighlighter;
    }

    public BoardMovementHandler getBoardMovement(){ return boardMovement; }

    public BoardInputHandler getBoardInputHandler() { return boardInputHandler; }

    public static int COORDTOLISTPOS(Coordinates c, int boardWidth){
        return c.coords.row + (c.coords.column * boardWidth);
    }

}
