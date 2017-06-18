package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Generator;

import com.purgadell.grizzly.Worlds.DungeonWorld.Board.GameBoard;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.Coordinates;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.TileGetter;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Obstructions.Containers;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Obstructions.Obstructions;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Ryan English on 6/10/2017.
 */

public class BoardItemGenerator {

    private GameBoard gameBoard;
    private Random r;
    private TileGetter tGetter;

    public BoardItemGenerator(GameBoard gameBoard){
        this.gameBoard = gameBoard;
        this.r = new Random();
        this.tGetter = new TileGetter(gameBoard.getBoardWidth(), gameBoard.getBoardHeight());
    }

    public LinkedList<Obstructions> generateItems(LinkedList<Coordinates> queued){
        System.out.println("-----------------BEGIN OBS------------------------------");
        LinkedList<Coordinates> queuedObs = new LinkedList<Coordinates>();
        Random r = new Random();

        int obsCount = queued.size() / 3;
        System.out.println("Adding " + obsCount + " obstructions");
        int count = 0;
        int looped = 0;

        while(count < obsCount && looped < 1400){
            int loc = r.nextInt(queued.size());

            Coordinates c = queued.get(loc);

            if(obsPlacable(c, queuedObs, queued)){
                queuedObs.push(c);
                count++;
                System.out.println("Placed obs num " + count + " at " + c.ToString());
            }

            looped++;
        }

        LinkedList<Obstructions> obs = new LinkedList<Obstructions>();

        for(Coordinates c : queuedObs){
            Obstructions o = getObstruction(c);
            obs.push(o);
        }
        System.out.println("-----------------END OBS------------------------------");
        return obs;
    }

    private boolean obsPlacable(Coordinates c, LinkedList<Coordinates> queuedObs, LinkedList<Coordinates> queued){
        return !tGetter.isWithinList(c, queuedObs) &&
                tGetter.tileConnectionsCount(c, queued) == 2 &&
                !hasBorderObs(c, queuedObs);
    }

    private boolean hasBorderObs(Coordinates c, LinkedList<Coordinates> queuedObs){
        LinkedList<Coordinates> border = tGetter.borderAroundCoordinate(c, 1);
        int count = tGetter.listWithinListCount(border, queuedObs);
        return count > 0;
    }

    private Obstructions getObstruction(Coordinates c){
        return new Containers(c);
    }



}
