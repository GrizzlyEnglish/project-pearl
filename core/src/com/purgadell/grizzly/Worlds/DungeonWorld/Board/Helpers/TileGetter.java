package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers;

import java.util.LinkedList;

/**
 * Created by Ryan English on 6/3/2017.
 */

public class TileGetter {

    private int boardWidth;
    private int boardHeight;

    public TileGetter(int boardWidth, int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
    }

    public LinkedList<Coordinates> borderAroundTile(Coordinates cords, int radius){
        LinkedList<Coordinates> cordsRadius = new LinkedList<Coordinates>();

        int x = cords.coords.row;
        int y = cords.coords.column;

        x = x - radius;

        for(int i = 0; i < 6; i++){
            int direction = i + 1;

            if(direction == 6) direction = 0;

            for(int t = 0; t < radius; t++){
                Coordinates nextC = getCords(x,y, direction);

                if(nextC.coords.isWithin(boardWidth, boardHeight)) cordsRadius.push(nextC);

                x = nextC.coords.row;
                y = nextC.coords.column;
            }
        }

        return cordsRadius;
    }

    public LinkedList<Coordinates> boardBox(Coordinates c, int w, int h) {
        return boardBox(c.coords.row, c.coords.column, w, h);
    }

    public LinkedList<Coordinates> boardBox(int bottomLeftX, int bottomLeftY, int w, int h) {
        LinkedList<Coordinates> block = new LinkedList<Coordinates>();
        System.out.println("-------------BLOCK------------------");
        for(int i = 0; i < w; i++){
            for(int i2 = 0; i2 < h; i2++){
                Coordinates nextC = new Coordinates(bottomLeftX+i,bottomLeftY+i2);

                if(nextC.coords.isWithin(boardWidth, boardHeight)) block.push(nextC);
                System.out.println(nextC.ToString());
            }
        }

        return block;
    }

    public LinkedList<Coordinates> tilesInRadius(Coordinates c, int radius){
        LinkedList<Coordinates> cordsRadius = new LinkedList<Coordinates>();

        for(int i = 1; i <= radius; i++){
            LinkedList<Coordinates> temp = borderAroundTile(c, radius);

            for(Coordinates c2 : temp){
                cordsRadius.push(c2);
            }
        }

        return cordsRadius;
    }

    public Coordinates getCords(Coordinates c, int side){
        return getCords(c.coords.row, c.coords.column, side);
    }

    public Coordinates getCords(int x, int y, int side){
        boolean even = y % 2 == 0;

        switch(side){
            case 0:
                return new Coordinates(even ? x : x -1, y + 1);
            //return even ? new int[]{x,y+1} : new int[]{x-1, y+1};
            case 1:
                return new Coordinates(even ? x + 1 : x, y + 1);
            //return even ? new int[]{x+1,y+1} : new int[]{x, y+1};
            case 2:
                return new Coordinates(x + 1, y);
            //return new int[]{x+1, y};
            case 3:
                return new Coordinates(even ? x + 1 : x, y - 1);
            //return even ? new int[]{x+1,y-1} : new int[]{x, y-1};
            case 4:
                return new Coordinates(even ? x : x - 1, y - 1);
            //return even ? new int[]{x,y-1} :  new int[]{x-1, y-1};
            case 5:
                return new Coordinates(x - 1, y);
            //return new int[]{x-1, y};
            default:
                return new Coordinates(x, y);
            //return new int[]{x,y};
        }
    }
}
