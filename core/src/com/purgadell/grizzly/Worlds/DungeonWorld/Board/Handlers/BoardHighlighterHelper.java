package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Handlers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.purgadell.grizzly.Resources.Assets;
import com.purgadell.grizzly.Resources.Textures;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.GameBoard;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;
import com.purgadell.grizzly.Worlds.DungeonWorld.Entities.Entity;

import java.util.LinkedList;

/**
 * Created by Ryan English on 6/17/2017.
 */

public class BoardHighlighterHelper {

    private GameBoard gameBoard;

    private Texture movementHighlighter;
    private Texture selectedHighlighter;

    private LinkedList<Tile> movementTiles;
    private Tile selectedTile;

    private static final float SELECTED_OFFSET = 25;

    public BoardHighlighterHelper(GameBoard gameBoard){
        this.gameBoard = gameBoard;
    }

    public void loadAssets(Assets assets){
        selectedHighlighter = assets.getTexture(Textures.TEST_SELECTED);
        movementHighlighter = assets.getTexture(Textures.TEST_MOVEMENT);
    }

    public void render(SpriteBatch batch){
        if(movementTiles != null){
            for(Tile t : movementTiles){
                renderMovement(batch, t);
            }
        } else if(selectedTile != null){
            renderSelected(batch);
        }
    }

    private void renderSelected(SpriteBatch batch){
        float x = selectedTile.getPosX() - SELECTED_OFFSET;
        float y = selectedTile.isHovered() ? selectedTile.getPosY() + SELECTED_OFFSET : selectedTile.getPosY();
        batch.draw(selectedHighlighter, x, y);
    }

    private void renderMovement(SpriteBatch batch, Tile t){
        float x = t.getTileCoords().position.x - 12;
        float y = t.isHovered() ? t.getPosY() + SELECTED_OFFSET : t.getPosY();

        batch.draw(movementHighlighter, x, y);
    }

    public void setupMovement(Entity ent){
        int movementAllowance = ent.getEntityStats().getMovementAllowance();
        movementTiles = gameBoard.getTileGetter().borderAroundTile(gameBoard.getBoardTiles(), selectedTile, movementAllowance);
    }

    public void setHighlightedTile(Tile t){
        selectedTile = t;

        Entity ent = selectedTile.getEntity();
        if (ent != null){
            setupMovement(ent);
        } else movementTiles = null;
    }

}
