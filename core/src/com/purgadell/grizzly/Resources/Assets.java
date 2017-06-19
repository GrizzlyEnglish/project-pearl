package com.purgadell.grizzly.Resources;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Obstructions.Chest;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Obstructions.Containers;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.DungeonTile;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.VoidTile;

import java.util.Random;

/**
 * Created by Ryan English on 5/13/2017.
 */

public class Assets{

    private AssetManager assetManager;

    public Assets(){
        assetManager = new AssetManager();
    }

    public void queTextures(String[] resources){
        for(String s : resources){
            queTexture(s);
        }
    }

    public void queTexture(String s){
        assetManager.load(s, Texture.class);
    }

    public void unloadAsset(String s){
        assetManager.unload(s);
    }

    public <T> Texture getTexture(T obj){
        String[] pack = Textures.TEST_PACK;

        if(obj instanceof DungeonTile){
            pack = Textures.DUNGEON_TILE_PACK;
        } else if(obj instanceof Containers){
            pack = Textures.OBS_PACK;
        } else if(obj instanceof VoidTile){
            return getTexture(Textures.TEST_VOID_TILE);
        }

        return randomTextureInPack(pack);
    }

    public Texture[] getTexturePack(String[] pack) {
        Texture[] tex = new Texture[pack.length];
        for(int i = 0; i < pack.length; i++){
            tex[i] = getTexture(pack[i]);
        }
        return tex;
    }

    public Texture randomTextureInPack(String[] pack){
        Random r = new Random();
        int loc = r.nextInt(pack.length);
        return getTexture(pack[loc]);
    }

    public Texture getTexture(String location){
        if(assetManager.isLoaded(location)){
            return assetManager.get(location);
        }

        return null;
    }

    public boolean loadQueuedAssets(){
        if(assetManager.update()){
            System.out.println("Done loading");
            return true;
        }

        float progress = assetManager.getProgress();
        System.out.println("Loading: " + progress + ", left");

        return false;
    }

    public void dispose(){
        assetManager.dispose();
    }

}
