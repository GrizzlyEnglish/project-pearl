package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Handlers;

import com.purgadell.grizzly.Resources.Variables;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.GameBoard;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.Coordinates;
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
    private Tile toTile;
    private LinkedList<Tile> entityPath;

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
                System.out.println("Moving to " + toTile.getTileCoords().ToString() + " from " + movingEntity.getCoordinates().ToString());
            }

            if(moveEntitySprite(dt)){
                System.out.println("Move to next tile " + entityPath.size() + " remain");
                movingEntity.placeOnTile(toTile);
                toTile = null;
            }
        }
    }

    private boolean moveEntitySprite(float dt){
        float distTraveled = Variables.DIST_PER_SECOND * dt;

        Coordinates entC = movingEntity.getCoordinates();
        Coordinates endC = toTile.getTileCoords();

        entC.position.move(distTraveled, endC.position);

        movingEntity.setCoordinates(entC);
        return entC.position.equals(endC.position);
    }

    public void moveEntity(Entity e, Tile onTile, Tile toTile){
        this.movingEntity = e;
        this.entityPath = PathFinder.getPath(gameBoard.getBoardTiles(), onTile, toTile, gameBoard.getTileGetter());
        System.out.println("Moving " + entityPath.size() + " tiles");
        this.isMoving = true;
        gameBoard.getBoardInputHandler().pauseInputHandling();
    }

}
