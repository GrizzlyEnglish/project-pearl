package com.purgadell.grizzly.Tiles;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Ryan English on 5/20/17.
 */

public class Triangle {

    private Vector2 p1;
    private Vector2 p2;
    private Vector2 p3;

    public Triangle(Vector2 p1, Vector2 p2, Vector2 p3){
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    /*
    * Not my code taken from Stack Overflow
    * http://stackoverflow.com/questions/13300904/determine-whether-point-lies-inside-triangle
    * */
    public boolean isWithinTriangle(Vector2 p){
        float alpha = ((p2.y - p3.y)*(p.x - p3.x) + (p3.x - p2.x)*(p.y - p3.y)) /
                ((p2.y - p3.y)*(p1.x - p3.x) + (p3.x - p2.x)*(p1.y - p3.y));
        float beta = ((p3.y - p1.y)*(p.x - p3.x) + (p1.x - p3.x)*(p.y - p3.y)) /
                ((p2.y - p3.y)*(p1.x - p3.x) + (p3.x - p2.x)*(p1.y - p3.y));
        return (1.0f - alpha - beta) > 0.0f;
    }

}
