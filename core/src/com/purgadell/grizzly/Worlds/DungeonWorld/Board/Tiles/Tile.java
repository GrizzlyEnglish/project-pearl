package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.purgadell.grizzly.Resources.Variables;

/**
 * Created by Ryan English on 5/13/2017.
 */

public abstract class Tile {

    private int boardX;
    private int boardY;

    private float posX;
    private float posY;

    private Triangle topTri;
    private Triangle bottomTri;
    private Rectangle body;

    private Sprite tileSprite;

    private boolean isSolid = false;
    private boolean isVisible = true;
    private boolean isSelected = false;
    private boolean isHovered = false;

    private static final float HEX_HEIGHT = Variables.TILE_HEIGHT - 65;
    private static final float HEX_WIDTH = Variables.TILE_WIDTH;

    private static final float HEX_BODY_HEIGHT = 93;
    private static final float HEX_TRI_HEIGHT = 69;

    public Tile(int boardX, int boardY, float posX, float posY){
        this.boardX = boardX;
        this.boardY = boardY;
        this.posX = posX;
        this.posY = posY;

        divideSprite();
    }

    //Divvy up the sprite for easy matching when clicking
    //If clicked in dead center it was this tile, if not check and make sure
    private void divideSprite(){
        float tbY = posY + (HEX_TRI_HEIGHT*2) + HEX_BODY_HEIGHT;

        topTri = new Triangle(new Vector2(posX,tbY),
                new Vector2(posX+Variables.TILE_WIDTH, tbY), new Vector2(posX+(Variables.TILE_WIDTH/2), posY+Variables.TILE_HEIGHT));

        body = new Rectangle(posX, posY + (HEX_TRI_HEIGHT*2), Variables.TILE_WIDTH, HEX_BODY_HEIGHT);

        float bbY = tbY - HEX_BODY_HEIGHT;

        bottomTri = new Triangle(new Vector2(posX, bbY), new Vector2(posX+Variables.TILE_WIDTH, bbY),
                new Vector2(posX+Variables.TILE_WIDTH/2, posY+HEX_TRI_HEIGHT));
    }

    public boolean contains(float x, float y){
        boolean b = body.contains(x, y);
        boolean b2 = topTri.isWithinTriangle(x,y);
        boolean b3 = bottomTri.isWithinTriangle(x,y);
        return (b || b2 || b3);
    }

    public void update(float dt){
        extraUpdate(dt);
    }

    public abstract void extraUpdate(float delta);

    public void setTileSprite(Texture t){
        this.tileSprite = new Sprite(t);
        this.tileSprite.setPosition(posX, posY);
    }

    public void render(SpriteBatch batch){
        if(isVisible){
            tileSprite.draw(batch);
        }
    }

    public void renderWireFrame(ShapeRenderer test){
        test.begin(ShapeRenderer.ShapeType.Line);
        test.setColor(Color.RED);
        test.rect(body.x,body.y,body.width,body.height);
        test.end();

        test.begin(ShapeRenderer.ShapeType.Line);
        test.setColor(Color.GOLD);
        test.line(topTri.p1, topTri.p2);
        test.line(topTri.p2, topTri.p3);
        test.line(topTri.p1, topTri.p3);
        test.end();

        test.begin(ShapeRenderer.ShapeType.Line);
        test.setColor(Color.GREEN);
        test.line(bottomTri.p1, bottomTri.p2);
        test.line(bottomTri.p2, bottomTri.p3);
        test.line(bottomTri.p1, bottomTri.p3);
        test.end();
    }

    public boolean toggleSelected() {
        setSelected(!isSelected);
        return isSelected;
    }

    public void setVisible(boolean b){
        isVisible = b;
    }

    public void setSelected(boolean b){
        isSelected = b;
    }

    public boolean isSelected() { return isSelected; }

    public boolean isHovered() { return isHovered; }

    public void setHovered(boolean b) {
        isHovered = b;
        if(isHovered) tileSprite.setPosition(posX, posY + Variables.HOVER_OFFSET);
        else tileSprite.setPosition(posX, posY);
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }
}
