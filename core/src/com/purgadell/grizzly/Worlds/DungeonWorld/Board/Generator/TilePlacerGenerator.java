package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Generator;

import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Containers.Path;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Containers.Room;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.GameBoard;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.Coordinates;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.PathFinder;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.TileGetter;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Ryan English on 6/10/2017.
 */

public class TilePlacerGenerator {

    private GameBoard gameBoard;

    private int boardWidth;
    private int boardHeight;

    private Random r;
    private TileGetter tGetter;

    public TilePlacerGenerator(GameBoard gameBoard){
        this.gameBoard = gameBoard;
        this.boardHeight = gameBoard.getBoardHeight();
        this.boardWidth = gameBoard.getBoardWidth();
        this.r = new Random();
        this.tGetter = new TileGetter(boardWidth, boardHeight);
    }

    public LinkedList<Coordinates> generateTiles(){
        return buildPaths();
    }

    private LinkedList<Coordinates> buildPaths(){
        LinkedList<Coordinates> queuedCoords = new LinkedList<Coordinates>();
        TileGetter tG = new TileGetter(boardWidth, boardHeight);

        System.out.println("------------------MAIN PATH--------------------");
        buildMainPath(queuedCoords);
        System.out.println("------------------END MAIN PATH--------------------");

        System.out.println("------------------BRANCHES--------------------");
        LinkedList<LinkedList<Coordinates>> emptyBlocks = findOpenBlocks(tG, queuedCoords);

        System.out.println("Found " + emptyBlocks.size() + " empty blocks");
        for(LinkedList<Coordinates> block : emptyBlocks){
            buildBranch(tG, block, queuedCoords);
        }
        System.out.println("------------------END BRANCHES--------------------");

        System.out.println("------------------START DEAD END--------------------");

        LinkedList<Coordinates> deadEnd = findDeadEnds(tG, queuedCoords);
        placeRooms(deadEnd, queuedCoords);

        System.out.println("DeadEnd count: " + deadEnd.size());

        System.out.println("------------------END DEAD END--------------------");

        return queuedCoords;
    }

    private void placeRooms(LinkedList<Coordinates> deadEnds, LinkedList<Coordinates> queued){
        TileGetter tileGetter = new TileGetter(boardWidth, boardHeight);

        System.out.println("Placing " + deadEnds.size() + " rooms");
        int connected = 0;

        LinkedList<Coordinates> potentialEnds = singleConnectors(tileGetter, queued);

        for(Coordinates end : deadEnds){
            int radius = r.nextInt(3) + 1;

            for(int i = 0; i <= radius; i++){
                LinkedList<Coordinates> room = tileGetter.borderAroundTile(end, i);

                for(Coordinates rT: room){
                    queued.push(rT);
                }

                Room r = new Room(room, end, radius);
                gameBoard.addRoom(r);
            }

            if(radius > 1) {
                boolean passed = connectRoom(tileGetter, end, radius, potentialEnds);
                if(passed) connected++;
            }
        }

        System.out.println("Connected " + connected + " rooms");
    }

    private LinkedList<Coordinates> singleConnectors(TileGetter tileGetter, LinkedList<Coordinates> queued){
        LinkedList<Coordinates> sc = new LinkedList<Coordinates>();

        for(Coordinates c : queued){
            LinkedList<Coordinates> border = tileGetter.borderAroundTile(c, 1);
            int connections = tGetter.listWithinListCount(border, queued);
            if(connections == 2) sc.push(c);
        }

        return sc;
    }

    private boolean connectRoom(TileGetter tileGetter, Coordinates centerRoom, int roomRadius, LinkedList<Coordinates> queued){
        LinkedList<Coordinates> outsideRoom = tileGetter.borderAroundTile(centerRoom, roomRadius+1);
        int connections = tGetter.listWithinListCount(outsideRoom, queued);

        if(connections == 1){
            /* TODO: rce--
            * This doesn't work like it should. I need to figure out how I could go about
            * finding somewhere to connect the room that is not the path that is currently connected
            * */
            Coordinates end = findClosestTile(centerRoom, queued, roomRadius+5);

            System.out.println("Connecting " + centerRoom.ToString() + " and " + end.ToString());

            buildPath(queued, centerRoom, end);
            return true;
        }

        return false;
    }

    private Coordinates findClosestTile(Coordinates c, LinkedList<Coordinates> queued, double lowestDist){
        Coordinates closest = null;
        double dist = Double.MAX_VALUE;

        for(Coordinates check : queued){
            double d = c.boardDistance(check);
            if(d >= lowestDist && d < dist) {
                closest = check;
                dist = d;
            }
        }

        return closest;
    }

    private LinkedList<Coordinates> findDeadEnds(TileGetter tileGetter, LinkedList<Coordinates> queued){
        LinkedList<Coordinates> deadEnds = new LinkedList<Coordinates>();

        for(Coordinates c : queued){
            int borderCount = tileGetter.tileConnectionsCount(c, queued);
            if(borderCount == 1) deadEnds.push(c);
        }


        return deadEnds;
    }

    private LinkedList<LinkedList<Coordinates>> findOpenBlocks(TileGetter tileGetter, LinkedList<Coordinates> queued){
        int blockW = boardWidth-1 % 2 == 0 ? boardWidth / 4 : boardWidth / 5;
        int blockH = boardHeight-1 % 2 == 0 ? boardHeight / 4 : boardHeight / 5;

        System.out.println("Block size: " + blockW + " by " + blockH);

        LinkedList<LinkedList<Coordinates>> blocks = new LinkedList<LinkedList<Coordinates>>();

        for(int x = 0; x < boardWidth; x+=blockW){
            for(int y = 0; y < boardHeight; y+=blockH){
                LinkedList<Coordinates> block = tileGetter.boardBox(x,y,blockW,blockH);

                if(!tGetter.listWithinList(block, queued)){
                    blocks.push(block);
                    System.out.println("Added Block");
                }
            }
        }

        return blocks;
    }

    private void buildBranch(TileGetter tileGetter, LinkedList<Coordinates> emptyBlock, LinkedList<Coordinates> queued){
        int index = r.nextInt(emptyBlock.size());
        Coordinates start = emptyBlock.get(index);

        Coordinates end = start;
        boolean searching = true;

        while(searching){
            index = r.nextInt(queued.size());
            end = queued.get(index);

            if(end.coords.row == start.coords.row || end.coords.column == start.coords.column) continue;

            LinkedList<Coordinates> border = tileGetter.borderAroundTile(end, 1);

            int borders = tGetter.listWithinListCount(border, queued);

            searching = borders > 3;
        }

        System.out.println("Branch at " + start.ToString() + " to " + end.ToString());

        drawLine(queued, start, end, true);

    }

    private void buildMainPath(LinkedList<Coordinates> queued){
        int startSide = r.nextInt(2);
        int endSide = startSide + 2;

        System.out.println("Start Side: " + startSide + " End Side: " + endSide);

        Coordinates start = getPointBySide(startSide);
        Coordinates end = getPointBySide(endSide);

        System.out.println("Start Point: " + start.ToString());
        System.out.println("End Point: " + end.ToString());

        buildPath(queued, start, end);
    }

    private LinkedList<Coordinates> buildPath(LinkedList<Coordinates> queued, Coordinates start, Coordinates end){
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
                shortPath = drawLine(queued, midp, end, false);
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
                shortPath = drawLine(queued, midp, nextP, false);
                midp = nextP;
            }

            for(Coordinates c : shortPath){
                path.push(c);
            }

        }while(searching);

        return path;
    }

    private int plusMinus(int value, int diff, boolean minus){
        return value + (minus ? diff : -diff);
    }

    private Coordinates getPointBySide(int side){
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

    private Coordinates randomPoint(int x1, int y1, int x2, int y2){
        int x = randomIntInRange(x1, x2);
        int y = randomIntInRange(y1, y2);

        if(x >= boardWidth) x = boardWidth-1;
        else if (x < 0) x = 0;

        if(y >= boardHeight) y = boardHeight-1;
        else if (y < 0) y = 0;

        return new Coordinates(x,y);
    }

    private int randomIntInRange(int v1, int v2){
        int max = Math.max(v1, v2);
        int min = Math.min(v1, v2);
        int range = (max - min) + 1;
        if(range <= 0) return min;
        return r.nextInt(range) + min;
    }

    private LinkedList<Coordinates> drawLine(LinkedList<Coordinates> queuedCoords, Coordinates a, Coordinates b, boolean quitOnOverlap){
        LinkedList<Coordinates> path = PathFinder.getPath(a, b, boardWidth, boardHeight);

        for(Coordinates c : path){
            if(quitOnOverlap && tGetter.isWithinList(c, queuedCoords)) break;
            queTile(queuedCoords, c);
        }

        Path p = new Path(path);
        gameBoard.addPath(p);

        return path;
    }

    private void queTile(LinkedList<Coordinates> queuedCords, Coordinates cords){
        if(queuedCords == null) queuedCords = new LinkedList<Coordinates>();
        queuedCords.add(cords);
    }

}
