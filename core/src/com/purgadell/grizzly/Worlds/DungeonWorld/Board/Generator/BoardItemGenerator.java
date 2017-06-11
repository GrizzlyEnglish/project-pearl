package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Generator;

import com.purgadell.grizzly.Worlds.DungeonWorld.Board.GameBoard;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Obstructions.Obstructions;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Ryan English on 6/10/2017.
 */

public class BoardItemGenerator {

    private GameBoard gameBoard;
    private Random r;

    public BoardItemGenerator(GameBoard gameBoard){
        this.gameBoard = gameBoard;
        this.r = new Random();
    }

    public LinkedList<Obstructions> generateItems(){
        LinkedList<Obstructions> obs = new LinkedList<Obstructions>();


        return obs;
    }

}
