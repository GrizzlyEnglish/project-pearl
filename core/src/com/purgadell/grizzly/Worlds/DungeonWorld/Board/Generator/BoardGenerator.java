package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Generator;

import com.purgadell.grizzly.Worlds.DungeonWorld.Board.GameBoard;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.Coordinates;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.DungeonTile;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.VoidTile;

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

        Tile[][] arr = new Tile[gameBoard.getBoardWidth()][gameBoard.getBoardWidth()];
        placeQueued(coords, arr);

        gameBoard.setBoardTiles(getList(arr));

        return gameBoard;
    }

    private LinkedList<Tile> getList(Tile[][] boardTiles){
        LinkedList<Tile> tiles = new LinkedList<Tile>();

        for(int l = 0; l < gameBoard.getBoardHeight(); l++){
            for(int w = 0; w < gameBoard.getBoardWidth(); w++){
                if(boardTiles[w][l] == null) boardTiles[w][l] = new VoidTile(w,l);
                tiles.push(boardTiles[w][l]);
            }
        }

        return tiles;
    }

    private void placeQueued(LinkedList<Coordinates> queuedCords, Tile[][] board){
        for(Coordinates c : queuedCords){
            int x = c.coords.row;
            int y = c.coords.column;

            Tile t = new DungeonTile(x,y);
            board[x][y] = t;
        }
    }


}
