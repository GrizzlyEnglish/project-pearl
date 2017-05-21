package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Ryan English on 5/20/17.
 */

public class Triangle {

    public Vector2 p1;
    public Vector2 p2;
    public Vector2 p3;

    public Triangle(Vector2 p1, Vector2 p2, Vector2 p3){
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    /*
    * Not my code taken from Stack Overflow
    * http://stackoverflow.com/questions/2049582/how-to-determine-if-a-point-is-in-a-2d-triangle
    * */
    public boolean isWithinTriangle(float x, float y){
        float dX = x-p2.x;
        float dY = y-p2.y;
        float dX21 = p2.x-p1.x;
        float dY12 = p1.y-p2.y;
        float D = dY12*(p3.x-p2.x) + dX21*(p3.y-p2.y);
        float s = dY12*dX + dX21*dY;
        float t = (p2.y-p3.y)*dX + (p3.x-p2.x)*dY;
        if (D<0) return s<=0 && t<=0 && s+t>=D;
        return s>=0 && t>=0 && s+t<=D;
    }

}
