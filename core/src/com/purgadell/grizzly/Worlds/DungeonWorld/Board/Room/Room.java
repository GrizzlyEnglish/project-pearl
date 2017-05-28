package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Room;

import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;

/**
 * Created by Ryan English on 5/28/2017.
 */

public class Room {

    private Tile[][] roomTiles;
    private Tile centerTile;
    private int rings;

    public Room(Tile center, int size){
        this.centerTile = center;
        this.rings = size;

        setupTiles();
    }

    private void setupTiles(){
        int rows = rings * 2 + 1;
        roomTiles = new Tile[rows][];

        int length = rings + 1;
        for(int i = 0; i < rows-1; i++){
            Tile[] topRow = new Tile[length];
            Tile[] bottomRow = new Tile[length];

            roomTiles[i] = topRow;
            roomTiles[rows-i-1] = bottomRow;

            length++;
        }

        roomTiles[rings] = new Tile[rows];
        roomTiles[rings][rings] = centerTile;
    }

    public void addTile(Tile t){
        int x = rings + (centerTile.getBoardX() - t.getBoardX());
        int y = rings + (t.getBoardY() - centerTile.getBoardY());
        System.out.println(centerTile.getBoardX() + "  " + centerTile.getBoardY() + "   " +
                t.getBoardX() + "  " + t.getBoardY() + "    " + x + "  " + y);
        roomTiles[x][y] = t;
        System.out.println("--------------");
    }

}
