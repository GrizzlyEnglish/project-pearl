package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Generator;

import com.purgadell.grizzly.Worlds.DungeonWorld.Board.GameBoard;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.Coordinates;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.VoidTile;
import com.purgadell.grizzly.Worlds.DungeonWorld.Entities.BoardEntities.BoardPlayer;
import com.purgadell.grizzly.Worlds.DungeonWorld.Entities.Entity;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Ryan English on 6/17/2017.
 */

public class EntityGenerator {

    private GameBoard gameBoard;

    public EntityGenerator(GameBoard gameBoard){
        this.gameBoard = gameBoard;
    }

    public LinkedList<Entity> generateEntities(LinkedList<Tile> tiles){
        LinkedList<Entity> ents = new LinkedList<Entity>();

        setPlayer(ents, tiles);

        return ents;
    }

    private void setPlayer(LinkedList<Entity> ents, LinkedList<Tile> tiles){
        Tile t = getNonVoid(tiles);
        System.out.println("Place player at " + t.getTileCoords().ToString());
        BoardPlayer player = new BoardPlayer(t);
        t.setTileEntity(player);
        ents.push(player);
    }

    private Tile getNonVoid(LinkedList<Tile> tiles){
        Random r = new Random();
        int i;
        Tile t;

        do{

            i = r.nextInt(tiles.size());
            t = tiles.get(i);

        }while((t instanceof VoidTile) && !t.hasObstructions());

        return t;
    }

}
