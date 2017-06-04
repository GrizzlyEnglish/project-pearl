package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Generator;

import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.Coordinates;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.PathFinder;
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

        buildMainPath(queuedCoords);

        return queuedCoords;
    }

    private static void buildMainPath(LinkedList<Coordinates> queued){
        //Get 3 points, start exit and mid point, these will gene

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

                //Do a check to see if x or y are within certain bounds, if they are skip them
                int offset = 10;
                int xMin = midp.coords.row + (xNegative ? offset : -offset);
                int xMax = end.coords.row + (xNegative ? -offset : offset);
                if(xMax > xMin + 15) xMax = xMin + 15;

                boolean yNegative = end.coords.column > midp.coords.column;
                int yMin = midp.coords.column + (yNegative ? offset : -offset);
                int yMax = end.coords.column + (yNegative ? -offset : offset);
                if(yMax > yMin + 15) yMax = yMin + 15;

                System.out.println("xMin: " + xMin + " xMax: " + xMax + " yMin: " + yMin + " yMax: " + yMax);
                nextP = randomPoint(xMin, yMin, xMax, yMax);
                System.out.println("Next Point: " + nextP.ToString());

                if(nextP.coords.row == midp.coords.row ||
                        nextP.coords.column == midp.coords.column) continue;
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

    private static Coordinates randomPoint(int minX, int minY, int maxX, int maxY){
        int x = randomIntInRange(minX, maxX);
        int y = randomIntInRange(minY, maxY);

        if(x > boardWidth) x = boardWidth-1;
        else if (x < 0) x = 0;

        if(y > boardHeight) y = boardHeight-1;
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
