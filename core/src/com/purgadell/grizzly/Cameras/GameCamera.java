package com.purgadell.grizzly.Cameras;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Ryan English on 5/13/2017.
 */

public abstract class GameCamera {

    protected OrthographicCamera gameCamera;

    protected Rectangle worldBounds;

    protected float camWidth;
    protected float camHeight;

    protected float zoomMin;
    protected float zoomMax;

    public GameCamera(int width, int height){
        this.camHeight = height;
        this.camWidth = width;
        this.gameCamera = new OrthographicCamera();
        this.gameCamera.setToOrtho(false,camWidth,camHeight);

        this.zoomMin = 2f;
        this.zoomMax = 10f;

        moveToDefaultPos();
        setWorldBounds(0,0, camWidth, camHeight);
    }

    public GameCamera(int width, int height, Rectangle bounds){
        this.camHeight = height;
        this.camWidth = width;
        this.gameCamera = new OrthographicCamera();
        this.gameCamera.setToOrtho(false,camWidth,camHeight);
        this.worldBounds = bounds;

        this.zoomMin = 2f;
        this.zoomMax = 10f;

        moveToDefaultPos();
    }

    public void moveToDefaultPos(){
        gameCamera.position.set(0,0,0);
        gameCamera.update();
    }

    public Vector3 unprojectCords(Vector3 cords){
        return gameCamera.unproject(cords);
    }

    private void setWorldBounds(float left_x, float bottom_y, float width, float height){
        worldBounds = new Rectangle(left_x, bottom_y, width, height);
    }

    public abstract void moveCamera(Vector3 cords);
    public abstract void panCamera(Vector3 cords);

    public void zoomCamera(Vector3 cords, int diff) {
        gameCamera.zoom += diff;
        if(gameCamera.zoom > zoomMax) gameCamera.zoom = zoomMax;
        else if(gameCamera.zoom < zoomMin) gameCamera.zoom = zoomMin;
        gameCamera.update();
    }

    public OrthographicCamera getGameCamera() { return gameCamera; }

}
