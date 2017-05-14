package com.purgadell.grizzly.Input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Stack;

/**
 * Created by Ryan English on 5/13/2017.
 */

public class InputHandler implements InputProcessor {

    public Stack<InputAction> actions = new Stack<InputAction>();

    private Vector2 downPos;

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        downPos = new Vector2(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        InputAction ia = new InputAction(InputAction.DRAGGED_MOUSE, new Vector3(downPos.x - screenX, screenY - downPos.y, 0));
        actions.push(ia);

        downPos = new Vector2(screenX, screenY);

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        InputAction ia = new InputAction(InputAction.SCROLL_MOUSE, null, amount);
        actions.push(ia);

        return false;
    }

}
