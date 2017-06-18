package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Generator;

import com.purgadell.grizzly.Worlds.DungeonWorld.Board.GameBoard;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.Coordinates;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Obstructions.Obstructions;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.DungeonTile;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.VoidTile;
import com.purgadell.grizzly.Worlds.DungeonWorld.Entities.Entity;

import java.util.LinkedList;

/**
 * Created by Ryan English on 5/22/2017.
 */

public class BoardGenerator {

    private GameBoard gameBoard;

    public BoardGenerator() {}

    public GameBoard generateBoard(int width, int height){
        this.gameBoard = new GameBoard(width,height);

        LinkedList<Coordinates> coords = new TilePlacerGenerator(gameBoard).generateTiles();
        LinkedList<Obstructions> obstructs = new BoardItemGenerator(gameBoard).generateItems(coords);

        Tile[][] tileArr = placeQueued(coords);
        Obstructions[][] obsArr = placeObs(obstructs);

        LinkedList<Tile> tiles = getList(tileArr, obsArr);
        LinkedList<Entity> entities = new EntityGenerator(gameBoard).generateEntities(tiles);

        gameBoard.setBoardTiles(tiles);
        gameBoard.setEntities(entities);

        return gameBoard;
    }

    private LinkedList<Tile> getList(Tile[][] boardTiles, Obstructions[][] boardObs){
        LinkedList<Tile> tiles = new LinkedList<Tile>();

        for(int l = 0; l < gameBoard.getBoardHeight(); l++){
            for(int w = 0; w < gameBoard.getBoardWidth(); w++){
                if(boardTiles[w][l] == null) boardTiles[w][l] = new VoidTile(w,l);
                else if(boardObs[w][l] != null){
                    boardTiles[w][l].setTileObstruction(boardObs[w][l]);
                    boardObs[w][l].setTile(boardTiles[w][l]);
                }
                tiles.addLast(boardTiles[w][l]);
            }
        }

        return tiles;
    }

    private Tile[][] placeQueued(LinkedList<Coordinates> queuedCords){
        Tile[][] tileArr = new Tile[gameBoard.getBoardWidth()][gameBoard.getBoardWidth()];
        for(Coordinates c : queuedCords){
            int x = c.coords.row;
            int y = c.coords.column;

            Tile t = new DungeonTile(x,y);
            tileArr[x][y] = t;
        }
        return tileArr;
    }

    private Obstructions[][] placeObs(LinkedList<Obstructions> queuedObs){
        Obstructions[][] obsArr = new Obstructions[gameBoard.getBoardWidth()][gameBoard.getBoardWidth()];
        for(Obstructions o : queuedObs){
            int x = o.getBoardX();
            int y = o.getBoardY();

            obsArr[x][y] = o;
        }

        return obsArr;
    }

}
