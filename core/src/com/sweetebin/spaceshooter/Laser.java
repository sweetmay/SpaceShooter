package com.sweetebin.spaceshooter;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

class Laser {

    //position dimensions
    float xPos, yPos;
    float width, height;

    //characteristics
    float speed; //world units per sec
    int damage;
    int ID;
    //graphics

    TextureRegion textureRegion;

    public Laser(float xPos, float yPos, float width, float height, float speed, TextureRegion textureRegion, int ID, int damage) {
        this.xPos = xPos - width/2;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.textureRegion = textureRegion;
        this.ID = ID;
        this.damage = damage;
    }


    public void laserMove(float delta){
        if(this.ID == 0){
            this.yPos += this.speed*delta;
        }else this.yPos -= this.speed*delta;
    }

    public void draw(Batch batch, float delta){
        batch.draw(textureRegion, xPos, yPos, width, height);
        laserMove(delta);
    }
    public Rectangle getHitbox(){
        return new Rectangle(xPos, yPos, width, height*0.8f);
    }

    public int getDamage() {
        return this.damage;
    }
}
