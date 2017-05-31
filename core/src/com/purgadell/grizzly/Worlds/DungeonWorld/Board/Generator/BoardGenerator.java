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

        //Find the two furthest points, call these the main path
        Coordinates a = elbows.get(0);
        Coordinates b = elbows.get(1);

        double max = 0;

        for(Coordinates c : elbows){
            for(Coordinates c2 : elbows){
                if(c == c2) continue;
                double dist = TileCoordinates.tileDistance(c,c2);
                if(dist > max){
                    a = c;
                    b = c2;
                    max = dist;
                }
            }
        }

        Stack<Coordinates> mainPath = drawLine(queuedCoords, a, b);

        boolean[] ignoreElbow = new boolean[elbows.size()];

        for(Coordinates c : mainPath){
            for(int i = 0; i < elbows.size(); i++){
                Coordinates c2 = elbows.get(i);

                if(c.coords.row == c2.coords.row &&
                        c.coords.column == c2.coords.column) ignoreElbow[i] = true;
            }
        }

        //Connect other points to main path, or to branch
        for(Coordinates c : elbows) {
            if(ignoreElbow[1]) continue;

            Coordinates end = null;
            double min = Double.MAX_VALUE;
            for(Coordinates c2 : mainPath) {
                double dist = TileCoordinates.tileDistance(c,c2);
                if(dist < min){
                    end = c2;
                    min = dist;
                }
            }

            Stack<Coordinates> branch = drawLine(queuedCoords, c, end);

            for(Coordinates cb : branch){
                for(int i = 0; i < elbows.size(); i++){
                    Coordinates cb2 = elbows.get(i);

                    if(cb.coords.row == cb2.coords.row &&
                            cb.coords.column == cb2.coords.column) ignoreElbow[i] = true;
                }
            }
        }

        return queuedCoords;
    }

    private static LinkedList<Coordinates> pickRandomTiles(LinkedList<Coordinates> queuedCoords, Tile[][] board){
        LinkedList<Coordinates> randoms = new LinkedList<Coordinates>();

        int elbowCount = boardHeight * boardWidth / 100;
        int count = 0;

        while(count < elbowCount){
            Coordinates c = new Coordinates(r.nextInt(boardWidth),r.nextInt(boardHeight));

            if(!checkCoords(c)) continue;

            boolean isQueued = tileIsQueued(queuedCoords. c);
            if(!isQueued){
                queTile(queuedCoords, c);
                randoms.add(c);
                count++;
            }
        }

        return randoms;
    }

    private static Stack<Coordinates> drawLine(LinkedList<Coordinates> queuedCoords, Coordinates a, Coordinates b){
        Stack<Coordinates> path = TileCoordinates.LineOfSight(a, b);

        for(Coordinates c : path){
            queTile(queuedCoords, c);
        }

        //Make a draw line that returns the path and a draw line that returns overlapped tiles

        return path;
    }

    //Probably scrapped
    private static Room generateRoom(Tile[][] board, int x, int y, int size) {
        Room room = null;
        LinkedList<Coordinates> qRoom = new LinkedList<Coordinates>();
        boolean queued;

        queued = queTile(qRoom, new Coordinates(x,y), false);
        if(!queued) return null;

        Coordinates startTile = TileCoordinates.getCords(x, y, 0);

        for(int s = 1; s <= size; s++){
            int side = 2;
            Coordinates newCords = startTile;

            for(int i = 0; i <= SIDES; i++){
                for(int r = 0; r < s; r++){
                    queued = queTile(qRoom, newCords, false);
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

    private static void queTile(LinkedList<Coordinates> queuedCords, Coordinates cords){
        if(queuedCords == null) queuedCords = new LinkedList<Coordinates>();
        queuedCords.add(cords);
    }

    private static boolean tileIsQueued(LinkedList<Coordinates> queuedCords, Coordinates cords){
        for(Coordinates c : queuedCords){
            if(c.coords.row == cords.coords.row && c.coords.column == cords.coords.column){
                return true;
            }
        }

        return false;
    }

    private static boolean checkCoords(Coordinates cords){
        if(cords.coords.row < 0 || cords.coords.row >= boardWidth
                || cords.coords.column < 0 || cords.coords.column >= boardHeight) {
            return false;
        }
    }

    private static boolean unQueTile(LinkedList<Coordinates> queuedCords, Coordinates cords) {
        Coordinates remove = null;
        for(Coordinates c : queuedCords){
            if(c.coords.row == cords.coords.row && c.coords.column == cords.coords.column){
                remove = c;
                break;
            }
        }

        if(remove != null){
            queuedCords.remove(remove);
            return true;
        }

        return false;
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
