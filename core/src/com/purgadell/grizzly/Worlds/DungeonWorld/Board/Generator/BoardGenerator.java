package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Generator;

import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.Coordinates;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.PathFinder;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.TileGetter;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.DungeonTile;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;

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

        LinkedList<Coordinates> queued = buildPaths();

        placeQueued(queued, board);

        return board;
    }

    private static LinkedList<Coordinates> buildPaths(){
        LinkedList<Coordinates> queuedCoords = new LinkedList<Coordinates>();
        TileGetter tG = new TileGetter(boardWidth, boardHeight);

        buildMainPath(queuedCoords);

        buildBranch(tG, queuedCoords);

        return queuedCoords;
    }

    private static void buildBranch(TileGetter tileGetter, LinkedList<Coordinates> queued){

        //Break off map into 15x15 squares, if no tiles exist in squre create end point
        //Then find the closest tile to the end point, on the path or on another square
        //Connect everything

        int index = r.nextInt(queued.size());
        Coordinates c = queued.get(index);

        int borderCount = tileGetter.borderAroundTileCount(c);

        if(borderCount == 5) return;

        Coordinates end = randomPoint(0, 0, boardWidth, boardHeight);

        drawLine(queued, c, end);
    }

    private static void buildMainPath(LinkedList<Coordinates> queued){
        int startSide = r.nextInt(2);
        int endSide = startSide + 2;

        System.out.println("Start Side: " + startSide + " End Side: " + endSide);

        Coordinates start = getPointBySide(startSide);
        Coordinates end = getPointBySide(endSide);

        System.out.println("Start Point: " + start.ToString());
        System.out.println("End Point: " + end.ToString());

        buildPath(queued, start, end);
    }

    private static LinkedList<Coordinates> buildPath(LinkedList<Coordinates> queued, Coordinates start, Coordinates end){
        LinkedList<Coordinates> path = new LinkedList<Coordinates>();
        Coordinates midp = start;
        boolean searching = true;

        int minDiff = 5;
        int maxDiff = 15;

        do{
            queued.push(midp);

            LinkedList<Coordinates> shortPath;
            double endDist = midp.boardDistance(end);

            System.out.println("--------------------------------------");
            System.out.println("On Point: " + midp.ToString() + "  ->  " + end.ToString());
            System.out.println("Dist Left: " + endDist);

            if(endDist < 10.0){
                shortPath = drawLine(queued, midp, end);
                searching = false;
            } else {
                Coordinates nextP;

                boolean xNegative = end.coords.row > midp.coords.row;
                boolean yNegative = end.coords.column > midp.coords.column;

                int offset = randomIntInRange(minDiff, maxDiff);

                int midP_Offset_X = plusMinus(midp.coords.row, offset, xNegative);
                int midP_Offset_Y = plusMinus(midp.coords.column, offset, yNegative);

                int end_Offset_X = plusMinus(end.coords.row, offset, xNegative);
                int end_Offset_Y = plusMinus(end.coords.column, offset, yNegative);

                double dist = Coordinates.distance(midP_Offset_X, midP_Offset_Y, end_Offset_X, end_Offset_Y);

                if(dist > maxDiff){
                    end_Offset_X = midP_Offset_X + maxDiff;
                    end_Offset_Y = midP_Offset_Y + maxDiff;
                }

                nextP = randomPoint(midP_Offset_X, midP_Offset_Y, end_Offset_X, end_Offset_Y);
                System.out.println("Next Point: " + nextP.ToString());

                if(nextP.coords.Equals(midp) || nextP.coords.isWithinWithBounds(midp, minDiff-1)) continue;

                System.out.println("Placed");
                shortPath = drawLine(queued, midp, nextP);
                midp = nextP;
            }

            for(Coordinates c : shortPath){
                path.push(c);
            }

        }while(searching);

        return path;
    }

    private static int plusMinus(int value, int diff, boolean minus){
        return value + (minus ? diff : -diff);
    }

    private static Coordinates getPointBySide(int side){
        switch(side){
            case 0:
                return new Coordinates(0, r.nextInt(boardHeight-1));
            case 1:
                return new Coordinates(r.nextInt(boardWidth-1), boardHeight-1);
            case 2:
                return new Coordinates(boardWidth-1, r.nextInt(boardHeight-1));
            default:
                return new Coordinates(r.nextInt(boardWidth-1), 0);
        }
    }

    private static Coordinates randomPoint(int x1, int y1, int x2, int y2){
        int x = randomIntInRange(x1, x2);
        int y = randomIntInRange(y1, y2);

        if(x >= boardWidth) x = boardWidth-1;
        else if (x < 0) x = 0;

        if(y >= boardHeight) y = boardHeight-1;
        else if (y < 0) y = 0;

        return new Coordinates(x,y);
    }

    private static int randomIntFromDiff(int p, int diff, int min, int max){
        int pMax = p + diff;
        if(pMax > max) pMax = max-1;

        int pMin = p - diff;
        if(pMin < min) pMin = min;

        return randomIntInRange(pMin, pMax);
    }

    private static int randomIntInRange(int v1, int v2){
        int max = Math.max(v1, v2);
        int min = Math.min(v1, v2);
        int range = (max - min) + 1;
        if(range <= 0) return min;
        return r.nextInt(range) + min;
    }

    private static LinkedList<Coordinates> drawLine(LinkedList<Coordinates> queuedCoords, Coordinates a, Coordinates b){
        LinkedList<Coordinates> path = PathFinder.getPath(a, b, boardWidth, boardHeight);

        for(Coordinates c : path){
            queTile(queuedCoords, c);
        }

        return path;
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
        return (cords.coords.row >= 0 || cords.coords.row < boardWidth
                || cords.coords.column >= 0 || cords.coords.column < boardHeight);
    }

    private static void queTile(LinkedList<Coordinates> queuedCords, Coordinates cords){
        if(queuedCords == null) queuedCords = new LinkedList<Coordinates>();
        queuedCords.add(cords);
    }

    private static int findCordsInList(LinkedList<Coordinates> queuedCords, Coordinates cords) {
        for(int i = 0; i < queuedCords.size(); i++){
            Coordinates c = queuedCords.get(i);
            if(c.coords.row == cords.coords.row && c.coords.column == cords.coords.column){
                return i;
            }
        }
        return -1;
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
