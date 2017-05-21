package com.purgadell.grizzly.Cameras;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Ryan English on 5/13/2017.
 */

public class BoardCamera extends GameCamera {

    public BoardCamera(int width, int height) {
        super(width, height);
    }

    public BoardCamera(int width, int height, Rectangle bounds) {
        super(width, height, bounds);
    }

    @Override
    public void moveCamera(Vector3 cords) {

    }

    @Override
    public void panCamera(Vector3 cords) {
        cords.x = cords.x * gameCamera.zoom;
        cords.y = cords.y * gameCamera.zoom;

        translateCamera(cords);
    }

    private void translateCamera(Vector3 cords){
        gameCamera.translate(cords);
        gameCamera.update();
    }
}
