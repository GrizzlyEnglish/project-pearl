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
