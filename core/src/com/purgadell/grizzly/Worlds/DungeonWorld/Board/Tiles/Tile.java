package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.purgadell.grizzly.PearlGame;
import com.purgadell.grizzly.Resources.Variables;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers.Coordinates;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Obstructions.Obstructions;
import com.purgadell.grizzly.Worlds.DungeonWorld.Entities.Entity;

/**
 * Created by Ryan English on 5/13/2017.
 */

public abstract class Tile {

    private Obstructions tileObstruction;
    private Entity tileEntity;

    private Coordinates tileCoords;

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
        this.tileCoords = new Coordinates(posX, posY, boardX, boardY);

        divideSprite();
    }

    public Tile(int boardX, int boardY){
        this.tileCoords = new Coordinates(boardX, boardY);

        divideSprite();
    }

    public Tile(Coordinates c){
        this.tileCoords = c;

        divideSprite();
    }

    //Divvy up the sprite for easy matching when clicking
    //If clicked in dead center it was this tile, if not check and make sure
    private void divideSprite(){
        float tbY = tileCoords.position.y + (HEX_TRI_HEIGHT*2) + HEX_BODY_HEIGHT;

        topTri = new Triangle(new Vector2(tileCoords.position.x,tbY),
                new Vector2(tileCoords.position.x+Variables.TILE_WIDTH, tbY),
                new Vector2(tileCoords.position.x+(Variables.TILE_WIDTH/2), tileCoords.position.y+Variables.TILE_HEIGHT));

        body = new Rectangle(tileCoords.position.x, tileCoords.position.y + (HEX_TRI_HEIGHT*2), Variables.TILE_WIDTH, HEX_BODY_HEIGHT);

        float bbY = tbY - HEX_BODY_HEIGHT;

        bottomTri = new Triangle(new Vector2(tileCoords.position.x, bbY), new Vector2(tileCoords.position.x+Variables.TILE_WIDTH, bbY),
                new Vector2(tileCoords.position.x+Variables.TILE_WIDTH/2, tileCoords.position.y+HEX_TRI_HEIGHT));
    }

    private void placeTile(int x, int y) {
        tileCoords.position.x = x * Variables.TILE_WIDTH;
        if (y % 2 == 0) tileCoords.position.x += Variables.TILE_WIDTH / 2;

        tileCoords.position.y = y * Variables.TILE_HEIGHT_OFFSET;
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
        this.tileSprite.setPosition(tileCoords.position.x, tileCoords.position.y);
    }

    public void render(SpriteBatch batch){
        if(isVisible){
            tileSprite.draw(batch);

            if(tileObstruction != null) tileObstruction.render(batch);

            if(PearlGame.DEBUGLOCATION){
                PearlGame.FONT.getData().setScale(4f,4f);
                PearlGame.FONT.draw(batch, "(" + tileCoords.coords.row + "," + tileCoords.coords.column + ")",
                        tileCoords.position.x + Variables.TILE_WIDTH/3, tileCoords.position.y + 200);
            }

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

    public boolean isVisible() { return isVisible; }

    public void setHovered(boolean b) {
        //TODO: rce- Make this better
        isHovered = b;
        if(isHovered){
            tileSprite.setPosition(tileCoords.position.x, tileCoords.position.y + Variables.HOVER_OFFSET);
            if(tileObstruction != null) tileObstruction.offsetSpritePos(0, Variables.HOVER_OFFSET);
            if(tileEntity != null) tileEntity.offsetSpritePos(0, Variables.HOVER_OFFSET);
        } else {
            tileSprite.setPosition(tileCoords.position.x, tileCoords.position.y);
            if(tileObstruction != null) tileObstruction.resetSpritePos();
            if(tileEntity != null) tileEntity.resetSpritePos();
        }
    }

    public float getPosX() {
        return tileCoords.position.x;
    }

    public float getPosY() {
        return tileCoords.position.y;
    }

    public int getBoardX() { return tileCoords.coords.row; }

    public int getBoardY() { return tileCoords.coords.column; }

    public Coordinates getTileCoords() { return tileCoords; }

    public Coordinates getEntityPlacementCoords(){
        return new Coordinates(this.tileCoords.position.x, this.tileCoords.position.y + Variables.ENTITY_OFFSET);
    }

    public int hueristicCots() { return 0; };

    public void setTileObstruction(Obstructions obs){
        this.tileObstruction = obs;
    }

    public void removeTileObstruction(){
        this.tileObstruction = null;
    }

    public Obstructions getTileObstruction(){
        return tileObstruction;
    }

    public boolean hasObstructions(){
        return tileObstruction != null || tileEntity != null;
    }

    public boolean hasObjectOnTile(){
        return tileObstruction != null;
    }

    public void setTileEntity(Entity e){
        this.tileEntity = e;
    }

    public void removeTileEntity(){
        this.tileEntity = null;
    }

    public boolean hasEntity(){
        return tileEntity != null;
    }

    public Entity getEntity(){
        return tileEntity;
    }

    public boolean equals(Tile t){
        return this.tileCoords.coords.Equals(t.getTileCoords());
    }

    public int movementCost(){
        if(this instanceof VoidTile) return 1000;
        else if(this.hasEntity()) return 300;
        else if(this.hasObstructions()) return 15;
        return 10;
    }

}
