package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Generator;

import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.TileCordinates;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Room.Room;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.DungeonTile;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Ryan English on 5/22/2017.
 */

public class BoardGenerator {

    private static int boardWidth;
    private static int boardHeight;

    private static Random r = new Random();
    private static final int SIDES = 6;

    public static Tile[][] GenerateBoard(int w, int h) {
        boardWidth = w;
        boardHeight = h;

        Tile[][] board = new Tile[w][h];

        buildRooms(board);

        return board;
    }

    private static void buildRooms(Tile[][] board){
        int tileAmt = boardHeight * boardWidth;
        int roomTileCount = tileAmt / 4;

        boolean notKilled = true;

        //while(roomTileCount > 0 || notKilled){
            int x = r.nextInt(boardWidth);
            int y = r.nextInt(boardHeight);

            Room generatedRoom = generateRoom(board, 10, 10, 1);
        //}
    }

    private static Room generateRoom(Tile[][] board, int x, int y, int size) {
        Room room = null;
        LinkedList<int[]> qRoom = new LinkedList<int[]>();
        boolean queued;

        queued = queTile(qRoom, board, new int[]{x,y});
        if(!queued) return null;

        int[] startTile = TileCordinates.getCords(x, y, 0);

        for(int s = 1; s <= size; s++){
            int side = 2;
            int[] newCords = startTile;

            for(int i = 0; i <= SIDES; i++){
                for(int r = 0; r < s; r++){
                    queued = queTile(qRoom, board, newCords);
                    if(!queued) return null;
                    newCords = TileCordinates.getCords(newCords, side);
                }

                side++;
                if(side >= SIDES) side = 0;
            }
            startTile = TileCordinates.getCords(startTile, 0);
        }

        for(int[] cords : qRoom){
            Tile t = placeTile(board, cords[0], cords[1]);
            if(room == null) room = new Room(t, size);
            else room.addTile(t);
        }

        return room;
    }

    private static boolean queTile(LinkedList<int[]> queuedCords, Tile[][] board, int[] cords){
        int x = cords[0];
        int y = cords[1];

        if(x < 0 || x >= boardWidth || y < 0 || y >= boardHeight ||
                board[x][y] != null) {
            return false;
        }

        if(queuedCords == null) queuedCords = new LinkedList<int[]>();
        queuedCords.add(cords);

        return true;
    }

    private static Tile placeTile(Tile[][] board, int x, int y) {
        board[x][y] = new DungeonTile(x, y);
        return board[x][y];
    }


}
