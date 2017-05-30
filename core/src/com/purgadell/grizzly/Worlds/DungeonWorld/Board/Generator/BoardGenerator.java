package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Generator;

import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.Coordinates;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.TileCoordinates;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Room.Room;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.DungeonTile;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;

import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

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

        LinkedList<Coordinates> queued = buildPaths(board);

        placeQueued(queued, board);

        return board;
    }

    private static LinkedList<Coordinates> buildPaths(Tile[][] board){
        LinkedList<Coordinates> queuedCoords = new LinkedList<Coordinates>();
        
        LinkedList<Coordinates> elbows = pickRandomTiles(queuedCoords, board);
        int elbowCount = elbows.size();
        int lineCount = 0;

        //Find the two furthest points, call these the main path

        //Connect other points to main path, or to branch

        return queuedCoords;
    }

    @SuppressWarnings("unchecked")
    private static LinkedList<Coordinates> pickRandomTiles(LinkedList<Coordinates> queuedCoords, Tile[][] board){
        int elbowCount = boardHeight * boardWidth / 100;
        int count = 0;

        while(count < elbowCount){
            int x = r.nextInt(boardWidth);
            int y = r.nextInt(boardHeight);

            boolean queued = queTile(queuedCoords, board, new Coordinates(x,y));
            if(queued){
                System.out.println("Queued (" + x + "," + y + ")");
                count++;
            }
        }

        return (LinkedList<Coordinates>)queuedCoords.clone();
    }

    private static void drawLine(LinkedList<Coordinates> queuedCoords, Tile[][] board, Coordinates a, Coordinates b){
        Stack<Coordinates> path = TileCoordinates.LineOfSight(a, b);

        for(Coordinates c : path){
            queTile(queuedCoords, board, c);
        }

        System.out.println("Drew line from (" + a.coords.row + "," + a.coords.column + ") to (" + b.coords.row + "," + b.coords.column + ")");
    }

    //Probably scrapped
    private static Room generateRoom(Tile[][] board, int x, int y, int size) {
        Room room = null;
        LinkedList<Coordinates> qRoom = new LinkedList<Coordinates>();
        boolean queued;

        queued = queTile(qRoom, board, new Coordinates(x,y));
        if(!queued) return null;

        Coordinates startTile = TileCoordinates.getCords(x, y, 0);

        for(int s = 1; s <= size; s++){
            int side = 2;
            Coordinates newCords = startTile;

            for(int i = 0; i <= SIDES; i++){
                for(int r = 0; r < s; r++){
                    queued = queTile(qRoom, board, newCords);
                    if(!queued) return null;
                    newCords = TileCoordinates.getCords(newCords, side);
                }

                side++;
                if(side >= SIDES) side = 0;
            }
            startTile = TileCoordinates.getCords(startTile, 0);
        }

        for(Coordinates cords : qRoom){
            Tile t = placeTile(board, cords.coords.row, cords.coords.column);
            if(room == null) room = new Room(t, size);
            else room.addTile(t);
        }

        return room;
    }

    private static boolean queTile(LinkedList<Coordinates> queuedCords, Tile[][] board, Coordinates cords){
        if(cords.coords.row < 0 || cords.coords.row >= boardWidth || cords.coords.column < 0 || cords.coords.column >= boardHeight ||
                board[cords.coords.row][cords.coords.column] != null) {
            return false;
        }

        if(queuedCords == null) queuedCords = new LinkedList<Coordinates>();
        queuedCords.add(cords);

        return true;
    }

    private static void placeQueued(LinkedList<Coordinates> queuedCords, Tile[][] board){
        for(Coordinates c : queuedCords){
            placeTile(board, c.coords.row, c.coords.column);
        }
    }

    private static Tile placeTile(Tile[][] board, int x, int y) {
        board[x][y] = new DungeonTile(x, y);
        return board[x][y];
    }


}
