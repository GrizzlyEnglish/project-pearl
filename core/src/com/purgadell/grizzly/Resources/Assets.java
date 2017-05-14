package com.purgadell.grizzly.Resources;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Ryan English on 5/13/2017.
 */

public class Assets{

    private AssetManager assetManager;

    public Assets(){
        assetManager = new AssetManager();
    }

    public void queAssets(){
        assetManager.load("Tiles/test_tile_1.png", Texture.class);
    }

    public void unloadAsset(){
        assetManager.unload("Tiles/test_tile_1.png");
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
