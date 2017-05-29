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
    }

    public class BoardCoords {
        public int row;
        public int column;

        public BoardCoords(int r, int c){
            this.row = r;
            this.column = c;
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


}


