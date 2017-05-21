package com.purgadell.grizzly.Input;

import com.badlogic.gdx.math.Vector3;

/**
 * Created by Ryan English on 5/13/2017.
 */

public class InputAction {

    public static final byte MOUSE = 0;
    public final static byte CLICKED_MOUSE = 1;
    public final static byte DRAGGED_MOUSE = 2;
    public final static byte SCROLL_MOUSE = 3;

    public final static byte PRESSED_KEY = 4;
    public final static byte RELEASED_KEY = 5;

    public byte actionCode;
    public Vector3 mouseCords;
    public int zoomAmount;
    public int keyCode;

    public InputAction(byte actionType, Vector3 mouseCords){
        this.actionCode = actionType;
        this.mouseCords = mouseCords;
    }

    public InputAction(byte actionType, Vector3 mouseCords, int zoomAmout){
        this.actionCode = actionType;
        this.mouseCords = mouseCords;
        this.zoomAmount = zoomAmout;
    }

}
