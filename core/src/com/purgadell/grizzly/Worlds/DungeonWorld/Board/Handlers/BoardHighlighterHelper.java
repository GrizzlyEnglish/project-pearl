package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Handlers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.purgadell.grizzly.Resources.Assets;
import com.purgadell.grizzly.Resources.Textures;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.GameBoard;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.HighlightTile;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;
import com.purgadell.grizzly.Worlds.DungeonWorld.Entities.Entity;

import java.util.LinkedList;

/**
 * Created by Ryan English on 6/17/2017.
 */

public class BoardHighlighterHelper {

    private GameBoard gameBoard;

    private Texture[] movementHighlighter;
    private Texture selectedHighlighter;

    private LinkedList<HighlightTile> movementTiles;
    private HighlightTile selectedTile;

    public BoardHighlighterHelper(GameBoard gameBoard){
        this.gameBoard = gameBoard;
    }

    public void loadAssets(Assets assets){
        selectedHighlighter = assets.getTexture(Textures.TEST_SELECTED);
        movementHighlighter = assets.getTexturePack(Textures.TEST_MOVEMENT);
    }

    public void update(float dt){
        if(movementTiles != null){
            for(HighlightTile ht : movementTiles) ht.update(dt);
        }
        if(selectedTile != null) selectedTile.update(dt);
    }

    public void render(SpriteBatch batch){
        if(movementTiles != null){
            for(HighlightTile t : movementTiles){
                t.render(batch);
            }
        } else if(selectedTile != null){
            selectedTile.render(batch);
        }
    }

    public void setupMovement(Entity ent){
        int movementAllowance = ent.getEntityStats().getMovementAllowance();
        LinkedList<Tile> tiles = gameBoard.getTileGetter().borderAroundTile(gameBoard.getBoardTiles(), selectedTile.getTile(), movementAllowance);
        movementTiles = new LinkedList<HighlightTile>();
        for(Tile t : tiles) {
            movementTiles.push(new HighlightTile(t, movementHighlighter));
        }
    }

    public void setHighlightedTile(Tile t){
        t.toggleSelected();
        if(t.isSelected()){
            selectedTile = new HighlightTile(t, selectedHighlighter);

            Entity ent = t.getEntity();
            if (ent != null && t.isSelected()){
                setupMovement(ent);
            } else movementTiles = null;
        } else clear();
    }

    public Tile getSelectedTile(){
        return selectedTile == null ? null : selectedTile.getTile();
    }

    public LinkedList<Tile> getMovementTiles(){
        LinkedList<Tile> tiles = new LinkedList<Tile>();
        for(HighlightTile ht : movementTiles) tiles.push(ht.getTile());
        return tiles;
    }

    public boolean queuedMovement(){
        return movementTiles != null;
    }

    public boolean selectedTile(){
        return selectedTile != null;
    }

    public void clear(){
        movementTiles = null;
        selectedTile = null;
    }

}
