package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Handlers;

import com.badlogic.gdx.math.Vector3;
import com.purgadell.grizzly.Cameras.BoardCamera;
import com.purgadell.grizzly.Input.InputAction;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.GameBoard;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.TileGetter;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;

/**
 * Created by Ryan English on 6/17/2017.
 */

public class BoardInputHandler {

    private GameBoard gameBoard;
    private TileGetter tileGetter;

    public BoardInputHandler(GameBoard gameboard){
        this.gameBoard = gameboard;
        this.tileGetter = gameboard.getTileGetter();
    }

    public void handleInput(InputAction action){
        Vector3 unprojectedCords;
        BoardCamera boardCamera = gameBoard.getBoardCamera();

        switch (action.actionCode){
            case InputAction.DRAGGED_MOUSE:
                boardCamera.panCamera(action.mouseCords);
                break;
            case InputAction.SCROLL_MOUSE:
                boardCamera.zoomCamera(null, action.zoomAmount);
                break;
            case InputAction.CLICKED_MOUSE:
                unprojectedCords = boardCamera.unprojectCords(action.mouseCords);
                selectTile(unprojectedCords);
                break;
            case InputAction.MOUSE:
                unprojectedCords = boardCamera.unprojectCords(action.mouseCords);
                hoverTile(unprojectedCords);
                break;
        }
    }

    private void hoverTile(Vector3 cords){
        Tile hoveredTile = gameBoard.getHoveredTile();

        if(hoveredTile != null) hoveredTile.setHovered(false);

        Tile t = tileGetter.findTileInList(cords, gameBoard.getBoardTiles());

        if(t != null) {
            t.setHovered(true);
            gameBoard.setHoveredTile(t);
        }
    }

    private void selectTile(Vector3 cords){
        Tile t = tileGetter.findTileInList(cords, gameBoard.getBoardTiles());

        if(t != null) {
            t.toggleSelected();
            gameBoard.getBoardHighlighter().setHighlightedTile(t);

        }
    }

}
