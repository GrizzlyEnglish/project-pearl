package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers;

import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;

/**
 * Created by Ryan English on 5/28/2017.
 */

public class TileCordinates {

    public static int X_ARRAY = 0;
    public static int Y_ARRAY = 1;

    public static int[] getCords(int[] cords, int side){
        return getCords(cords[X_ARRAY], cords[Y_ARRAY], side);
    }

    public static int[] getCords(int x, int y, int side){
        boolean even = y % 2 == 0;

        switch(side){
            case 0:
                return even ? new int[]{x,y+1} : new int[]{x-1, y+1};
            case 1:
                return even ? new int[]{x+1,y+1} : new int[]{x, y+1};
            case 2:
                return new int[]{x+1, y};
            case 3:
                return even ? new int[]{x+1,y-1} : new int[]{x, y-1};
            case 4:
                return even ? new int[]{x,y-1} :  new int[]{x-1, y-1};
            case 5:
                return new int[]{x-1, y};
            default:
                return new int[]{x,y};
        }
    }

}
