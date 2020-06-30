package com.sweetebin.spaceshooter;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

abstract class Ship {

    //ship characteristics
    float movementSpeed; //world units per second
    int shield;
    int ID;

    //laser information
    float laserWidth, laserHeight;
    float shotDelta = 0;
    float timeBeetwenShots;
    float laserMovementSpeed;

    //pos & dimensions
    float shieldHeight;
    float shieldWidth;
    float shieldxPos;
    float shieldyPos;
    float xPosCentre, yPosCentre, xPos, yPos;
    float width, height;
    Rectangle thisRect;
    //graphics
    TextureRegion shipTexture, shieldTexture, laserTexture;

    public Ship(float xPos, float yPos,
                float movementSpeed, int shield,
                float width, float height,
                float laserWidth, float laserHeight, float laserMovementSpeed, float timeBeetwenShots,
                TextureRegion shipTexture, TextureRegion shieldTexture, TextureRegion laserTexture, int ID) {
        this.movementSpeed = movementSpeed;
        this.shield = shield;
        this.xPos = xPos;
        this.yPos = yPos;
        this.xPosCentre = xPos - width/2;
        this.yPosCentre = yPos - height/2;
        this.width = width;
        this.height = height;
        this.shipTexture = shipTexture;
        this.shieldTexture = shieldTexture;
        this.shieldWidth = width*1.2f;
        this.shieldHeight = height*1.4f;
        this.shieldxPos = xPos - shieldWidth/2;
        this.shieldyPos = yPos - shieldHeight/2;
        this.laserTexture = laserTexture;
        this.laserWidth = laserWidth;
        this.laserHeight = laserHeight;
        this.laserMovementSpeed = laserMovementSpeed;
        this.timeBeetwenShots = timeBeetwenShots;
        this.ID = ID;
        thisRect = new Rectangle(xPosCentre, yPosCentre, width, height);
    }

    public void update(float delta){
        shotDelta += delta;
        thisRect.set(xPosCentre, yPosCentre, width, height);
    }

    public boolean canFire(){
        return (shotDelta - timeBeetwenShots >= 0);
    }

    public boolean doesIntersects(Rectangle rect){
        return thisRect.overlaps(rect);
    }

    public void hit(Laser laser){
        if(shield>0){
            shield--;
        }
    }

    public abstract Laser[] fireLaser();

    public void draw(Batch batch){
        batch.draw(shipTexture, xPosCentre, yPosCentre, width, height);
        if(shield > 0){
            batch.draw(shieldTexture, shieldxPos, shieldyPos, shieldWidth, shieldHeight);
        }
    }
}
