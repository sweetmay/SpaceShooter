package com.sweetebin.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class EnemyShip extends Ship {
    float moveDelta = 0;
    float timeBetweenMove = (float) (2 + Math.random() * 6);
    public EnemyShip(float xPos, float yPos,
                     float movementSpeed, int shield,
                     float width, float height,
                     float laserWidth, float laserHeight, float laserMovementSpeed,
                     float timeBeetwenShots,
                     TextureRegion shipTexture, TextureRegion shieldTexture, TextureRegion laserTexture, int ID, int health) {
        super(xPos, yPos, movementSpeed, shield, width, height, laserWidth, laserHeight, laserMovementSpeed, timeBeetwenShots, shipTexture, shieldTexture, laserTexture, ID, health);
        touchVector = new Vector2();
    }

    @Override
    public boolean hit(Laser laser) {
        {
            if(shield>0){
                shield -= laser.getDamage();
                isShieldDamaged = true;
            }else {
                health-=laser.getDamage();
            }
            if(health <= 0){
                isAlive = false;
                return false;
            }
            return true;
        }
    }

    @Override
    public Laser[] fireLaser() {
        Laser[] lasers = new Laser[2];
        lasers[0] = new Laser(shipRect.getX()+shipRect.getWidth()*0.2f, shipRect.getY()-laserHeight, laserWidth, laserHeight, laserMovementSpeed, laserTexture, ID, 1);
        lasers[1] = new Laser(shipRect.getX()+shipRect.getWidth()*0.8f, shipRect.getY()-laserHeight, laserWidth, laserHeight, laserMovementSpeed, laserTexture, ID, 1);
        shotDelta = 0;

        return lasers;
    }


    @Override
    public void moveToPos() {
        if(moveDelta >= timeBetweenMove || moveDelta == 0){
            touchVector.set(new Vector2((float)Math.random()*GameScreen.getWorldWidth(),
                    (float) (GameScreen.getWorldHeight()-Math.random()*GameScreen.getWorldHeight()/2)));
            moveDelta = 0.1f;
        } else {
            destVector.set(touchVector);
            destVector.sub(centrePos).nor();
            if(touchVector.dst(centrePos) > 0.5f){
            shipRect.setCenter(centrePos.mulAdd(destVector,
                    movementSpeed * Gdx.graphics.getDeltaTime()));
            }
            moveDelta += Gdx.graphics.getDeltaTime();
        }
    }

    @Override
    public void setDestVect(Vector2 vect) {

    }



}
