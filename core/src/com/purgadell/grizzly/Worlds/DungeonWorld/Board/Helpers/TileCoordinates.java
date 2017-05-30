package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers;

import java.util.Stack;

/**
 * Created by Ryan English on 5/28/2017.
 */

public class TileCoordinates {

    private static class Axial{
        public int row;
        public int col;

        public Axial(int r, int c){
            this.row = r;
            this.col = c;
        }

        public Axial(int x, int y, int z){
            this.col = x + (z + (z&1)) / 2;
            this.row = z;
        }

        public Cube toCube(){
            int x = col - (row + (row&1)) / 2;
            int z = row;
            int y = -x-z;
            return new Cube(x, y, z);
        }
    }

    private static class Cube {
        public double x;
        public double z;
        public double y;

        public Cube(double x, double y, double z){
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Cube(int row, int col){
            this.x = col - (row + (row&1)) / 2;
            this.z = row;
            this.y = -x-z;
        }

        public Axial toAxial(){
            int row = (int)z;
            int col = (int)x + (row + (row&1)) / 2;

            return new Axial(col, row);
        }
    }

    public static Stack<Coordinates> LineOfSight(Coordinates a, Coordinates b){
        Cube cubeA = new Cube(a.coords.row, a.coords.column);
        Cube cubeB = new Cube(b.coords.row, b.coords.column);

        double dist = cubeDistance(cubeA, cubeB);
        Stack<Coordinates> coords = new Stack<Coordinates>();

        for(int i = 0; i < dist; i++){
            Cube lerped = cubeLerp(cubeA, cubeB, 1.0/dist * i);
            Cube rounded = roundCube(lerped);
            Axial axial = rounded.toAxial();

            coords.push(new Coordinates(axial.row, axial.col));
        }

        return coords;
    }

    private static Cube roundCube(Cube cube){
        float rx = Math.round(cube.x);
        float ry = Math.round(cube.y);
        float rz = Math.round(cube.z);

        double x_diff = Math.abs(rx - cube.x);
        double y_diff = Math.abs(ry - cube.y);
        double z_diff = Math.abs(rz - cube.z);

        if (x_diff > y_diff && x_diff > z_diff)rx = -ry-rz;
        else if (y_diff > z_diff) ry = -rx-rz;
        else rz = -rx-ry;

        return new Cube(rx, ry, rz);
    }

    private static Cube cubeLerp(Cube a, Cube b, double t){
        return new Cube(lerp(a.x, b.x, t),lerp(a.y, b.y, t),lerp(a.z, b.z, t));
    }

    private static double lerp(double a, double b, double t){
        return a + (b - a) * t;
    }

    private static double cubeDistance(Cube a, Cube b) {
        return (Math.abs(a.x - b.x) + Math.abs(a.y - b.y) + Math.abs(a.z - b.z)) / 2;
    }

    public static Coordinates getCords(Coordinates cords, int side){
        return getCords(cords.coords.row, cords.coords.column, side);
    }

    public static Coordinates getCords(int x, int y, int side){
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

    public static double tileDistance(Coordinates a, Coordinates b){
        return Math.sqrt(Math.pow(a.position.x-b.position.x, 2) + Math.pow(a.position.y-a.position.y, 2));
    }

}
