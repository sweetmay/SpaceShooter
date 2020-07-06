package com.sweetebin.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

abstract class Ship {

    //ship characteristics
    float movementSpeed; //world units per second
    int shield;
    int ID;
    boolean isAlive;
    int health;
    //laser information
    float laserWidth, laserHeight;
    float shotDelta = 0;
    float timeBeetwenShots;
    float laserMovementSpeed;

    //pos & dimensions
    Vector2 touchVector;
    Vector2 destVector = new Vector2();
    Rectangle shipRect;
    Vector2 centrePos = new Vector2();
    Rectangle shieldRect = new Rectangle();
    //graphics

    TextureRegion shipTexture, shieldTexture, laserTexture;

    public Ship(float xPos, float yPos,
                float movementSpeed, int shield,
                float width, float height,
                float laserWidth, float laserHeight, float laserMovementSpeed, float timeBeetwenShots,
                TextureRegion shipTexture, TextureRegion shieldTexture, TextureRegion laserTexture, int ID, int health) {
        this.movementSpeed = movementSpeed;
        this.shield = shield;
        this.ID = ID;
        this.shipRect = new Rectangle(xPos, yPos, width, height);
        this.health = health;
        this.centrePos = shipRect.getCenter(centrePos);
        this.shipTexture = shipTexture;
        this.shieldTexture = shieldTexture;
        bindShield(width, height);
        this.laserTexture = laserTexture;
        this.laserWidth = laserWidth;
        this.laserHeight = laserHeight;
        this.laserMovementSpeed = laserMovementSpeed;
        this.timeBeetwenShots = timeBeetwenShots;
        isAlive = true;
    }

    public void update(float delta){
        moveToPos();
        shotDelta += delta;
        centrePos = shipRect.getCenter(centrePos);
        bindShield(shipRect.getWidth(), shipRect.getHeight());
    }

    private void bindShield(float width, float height) {
        this.shieldRect.setX(centrePos.x - width / 2);
        this.shieldRect.setWidth(width);
        this.shieldRect.setHeight(height*1.4f);
        if (this.ID == 1) {
            this.shieldRect.setY(shipRect.getY() - height/2);
        }else {
        this.shieldRect.setY(shipRect.getY());
        }
    }

    public boolean canFire(){
        if(isAlive){
        return (shotDelta - timeBeetwenShots >= 0);
        }else return false;
    }

    public boolean doesIntersects(Rectangle rect){
        if(shieldRect.overlaps(rect) && shield>0){
            return true;
        }else {
            return shipRect.overlaps(rect);
        }
    }

    public abstract boolean hit(Laser laser);

    public abstract Laser[] fireLaser();

    public void draw(Batch batch){
        if(isAlive){
        batch.draw(shipTexture, shipRect.getX(), shipRect.getY(), shipRect.getWidth(), shipRect.getHeight());
        if(shield > 0){
            batch.draw(shieldTexture,shieldRect.getX(), shieldRect.getY(), shieldRect.getWidth(), shieldRect.getHeight());
        }
        }
    }

    public abstract void moveToPos();

    public abstract void setDestVect(Vector2 vect);

    public abstract void moveLeft(float delta);
    public abstract void moveRight(float delta);
    public void healthBar(){

    }
}
