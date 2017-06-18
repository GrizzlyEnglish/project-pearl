package com.purgadell.grizzly.Worlds.DungeonWorld.Entities;

/**
 * Created by Ryan English on 6/17/2017.
 */

public class Stats {

    public int Speed;
    public int Range;

    public int PhysicalAttack;
    public int PhysicalDefense;

    public int MagicalAttack;
    public int MagicalDefense;

    public int ArmorDefense;

    public int Weight;

    public int WeightStage1;
    public int WeightStage2;
    public int WeightStage3;

    public Stats(){
        Speed = 1;
        Range = 1;

        PhysicalAttack = 1;
        PhysicalDefense = 1;

        MagicalAttack = 1;
        MagicalDefense = 1;

        ArmorDefense = 1;

        Weight = 1;

        WeightStage1 = 1;
        WeightStage2 = 1;
        WeightStage3 = 1;
    }

    public int getMovementAllowance(){
        //TODO: rce - Make this more complex to account for which stage of weight the ent is on
        return Speed * Range / Weight;
    }

}
