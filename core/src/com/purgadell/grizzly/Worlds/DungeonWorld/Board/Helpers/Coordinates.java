package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers;

import com.purgadell.grizzly.Resources.Variables;

/**
 * Created by Ryan English on 5/29/2017.
 */

public class Coordinates {

    public class Position {
        public float x;
        public float y;

        public Position(float x, float y){
            this.x = x;
            this.y = y;
        }

        public boolean move(float dist, Position maxP){
            this.x = move(this.x, dist, maxP.x);
            this.y = move(this.y, dist, maxP.y);

            return this.x == maxP.x && this.y == maxP.y;
        }

        private float move(float val, float dist, float maxY){
            if(val > maxY){
                val -= dist;
                return val < maxY ? maxY : val;
            } else if(val < maxY){
                val += dist;
                return val > maxY ? maxY : val;
            }

            return val;
        }

        public boolean equals(Position p ){
            return p.x == this.x && p.y == this.y;
        }

        public double distance(Position p){
            return Coordinates.distance(x,y,p.x,p.y);
        }

        public String ToString(){ return "(" + this.x + "," + this.y + ")"; }
    }

    public class BoardCoords {
        public int row;
        public int column;

        public BoardCoords(int r, int c){
            this.row = r;
            this.column = c;
        }

        public boolean isWithinWithBounds(Coordinates c, int offset){
            return isWithinWithBounds(c, offset, offset);
        }

        public boolean isWithinWithBounds(Coordinates c, int offsetX, int offsetY){
            return c.coords.row-offsetX <= row && c.coords.row+offsetX >= row
                    && c.coords.column-offsetX <= column && c.coords.column+offsetY >= column;
        }

        public boolean isWithin(int x, int y){
            return row >= 0 && row < x && column >= 0 && column < y;
        }

        public boolean Equals(Coordinates c){
            return c.coords.row == row && c.coords.column == column;
        }

        public int rowDistance(BoardCoords b){
            return Math.abs(row - b.row);
        }

        public int colDistance(BoardCoords b){
            return Math.abs(column - b.column);
        }
    }

    public Position position;
    public BoardCoords coords;

    public Coordinates(float x, float y){
        this.position = new Position(x,y);
        this.coords = coordsFromPosition(position);
    }

    public Coordinates(int row, int col){
        this.coords = new BoardCoords(row, col);
        this.position = positionFromBoard(coords);
    }

    public Coordinates(float x, float y, int row, int col){
        this.coords = new BoardCoords(row, col);
        this.position = new Position(x,y);
    }

    public Position positionFromBoard(BoardCoords coords){
        float posX = coords.row * Variables.TILE_WIDTH;
        if (coords.column % 2 == 0) posX += Variables.TILE_WIDTH / 2;

        float posY = coords.column * Variables.TILE_HEIGHT_OFFSET;

        return new Position(posX, posY);
    }

    public BoardCoords coordsFromPosition(Position p){
        int row = (int)(p.x / Variables.TILE_WIDTH);
        int column = (int)(p.y / Variables.TILE_HEIGHT_OFFSET);

        return new BoardCoords(row, column);
    }

    public String ToString(){
        return "BOARD(" + coords.row + "," + coords.column + ") -- POS(" + position.x + "," + position.y + ")";
    }

    public double distance(Coordinates c){
        return distance(position.x, position.y, c.position.x, c.position.y);
    }

    public static double distance(float x1, float y1, float x2, float y2){
        return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
    }

    public double boardDistance(Coordinates c){
        return Math.sqrt(Math.pow(coords.row-c.coords.row, 2) + Math.pow(coords.column-c.coords.column, 2));
    }

}


