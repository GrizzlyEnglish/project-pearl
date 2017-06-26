package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Handlers;

import com.purgadell.grizzly.Resources.Variables;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.GameBoard;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.PathFinder;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.TileGetter;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;
import com.purgadell.grizzly.Worlds.DungeonWorld.Entities.Entity;

import java.util.LinkedList;

/**
 * Created by Ryan English on 6/26/2017.
 */

public class BoardMovementHandler {

    private GameBoard gameBoard;

    private boolean isMoving;
    private Entity movingEntity;
    private Tile onTile;
    private Tile toTile;
    private LinkedList<Tile> entityPath;

    private float timePassed = 0;
    private double traveledDistance = 0;
    private double tileDistance = 0;

    public BoardMovementHandler(GameBoard gameBoard){
        this.gameBoard = gameBoard;
    }

    public void update(float dt){
        if(isMoving){
            if(toTile == null && entityPath.isEmpty()){
                System.out.println("Done moving");
                gameBoard.getBoardInputHandler().resumeInputHandling();
                isMoving = false;
                return;
            }

            if(toTile == null){
                toTile = entityPath.pop();
            }

            timePassed += dt;

            if(timePassed > 1){
                System.out.println("Moving to next tile " + entityPath.size() + " remain");
                movingEntity.placeOnTile(toTile);

                onTile = toTile;
                toTile = null;
                timePassed = 0;
            }
        }
    }

    public void moveEntity(Entity e, Tile onTile, Tile toTile){
        this.movingEntity = e;
        this.onTile = onTile;
        this.entityPath = PathFinder.getPath(gameBoard.getBoardTiles(), onTile, toTile, gameBoard.getTileGetter());
        System.out.println("Moving " + entityPath.size() + " tiles");
        this.isMoving = true;
        gameBoard.getBoardInputHandler().pauseInputHandling();
    }

}
