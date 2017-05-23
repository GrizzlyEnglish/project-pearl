package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Generator;

import com.purgadell.grizzly.Resources.Variables;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.DungeonTile;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;

import java.util.Random;

/**
 * Created by Ryan English on 5/22/2017.
 */

public class BoardGenerator {

    private static int boardWidth;
    private static int boardHeight;

    private static Random r = new Random();
    private static final int SIDES = 6;

    public static Tile[][] GenerateBoard(int w, int h){
        Tile[][] board = new Tile[w][h];

        buildPath(board, 10, 10, 250, 375);

        return board;
    }

    private static void buildPath(Tile[][] board, int startX, int startY, int endX, int endY){
        float posX = startX * Variables.TILE_WIDTH;
        float posY = (startY%2 * Variables.TILE_HEIGHT) + (startY%2 * (Variables.TILE_HEIGHT/2 + 15));

        do{

            board[startX][startY] = new DungeonTile(startX, startY, posX, posY);

            int side = pickSide(availableSides(startX,startY));

            if(side == -1) break;

            switch(side){
                case 0:
                    startY += 1;
                    startX -= 1;
                    break;
                case 1:
                    startY += 1;
                    startX += 1;
                    break;
                case 2:
                    startX += 1;
                    break;
                case 3:
                    startX += 1;
                    startY -= 1;
                    break;
                case 4:
                    startX -= 1;
                    startY -= 1;
                    break;
                case 5:
                    startX -= 1;
                    break;
            }

        }while(startX != endX && startY != endY);
    }

    private static boolean[] availableSides(int x, int y){
        return new boolean[]{
                (y+1 < boardHeight) && (x-1 > 0),
                (y+1 < boardHeight) && (x+1 > 0),
                (x+1 < boardWidth),
                (y-1 > 0) && (x+1 < boardWidth),
                (y-1 > 0) && (x-1 > 0),
                (x-1 > 0)
        };
    }

    private static int pickSide(boolean[] sideRemoved){
        //Check and make sure we actually have an available side
        boolean avail = false;
        for (boolean aSideRemoved : sideRemoved) {
            if (aSideRemoved) {
                avail = true;
                break;
            }
        }

        //Return -1 there are no sides
        if(!avail) return -1;

        //Get random num between 0-6, if it is removed then find next available
        int side = r.nextInt(SIDES);
        if(sideRemoved[side]){
            int start = side;
            for(int i = side; i < sideRemoved.length; i++){
                if(!sideRemoved[i]){
                    side = i;
                    break;
                }
                if(i == sideRemoved.length-1) i = 0;
                if(i == start) break;
            }
        }
        return side;
    }

}
