package com.sweetebin.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class PlayerShip extends Ship {

    public PlayerShip(float xPos, float yPos,
                      float movementSpeed, int shield, float width, float height,
                      float laserWidth, float laserHeight,
                      float laserMovementSpeed, float timeBeetwenShots,
                      TextureRegion shipTexture, TextureRegion shieldTexture, TextureRegion laserTexture, int ID, int health) {
        super(xPos, yPos, movementSpeed, shield, width, height, laserWidth, laserHeight, laserMovementSpeed, timeBeetwenShots, shipTexture, shieldTexture, laserTexture, ID, health);
    }

    @Override
    public boolean hit(Laser laser) {
        {
            if(shield>0){
                shield -= laser.getDamage();
                System.out.println(shield+ "  "+ ID);
            }else {
                health-=laser.getDamage();
                Gdx.input.vibrate(50);
                System.out.println(health+ "  "+ ID);
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
        lasers[0] = new Laser(shipRect.getX(), centrePos.y, laserWidth, laserHeight, laserMovementSpeed, laserTexture, ID, 1);
        lasers[1] = new Laser(shipRect.getX() + shipRect.getWidth(), centrePos.y, laserWidth, laserHeight, laserMovementSpeed, laserTexture, ID, 1);

        shotDelta = 0;

        return lasers;
    }

    @Override
    public void moveToPos(){
        if(touchVector != null && touchVector.dst(centrePos)>1){
            Vector2 mag;
            destVector.set(touchVector);
            mag = destVector.sub(centrePos);
            System.out.println(touchVector);
            shipRect.setCenter(centrePos.mulAdd(mag.nor(),
                    movementSpeed * Gdx.graphics.getDeltaTime()));
        }
    }
    @Override
    public void setDestVect(Vector2 vect){
        if(touchVector != null){
            touchVector.set(vect);
        }else {
            touchVector = vect;
        }
    }

    @Override
    public void moveLeft(float delta) {
        shipRect.setX(shipRect.getX()-delta*movementSpeed);
    }

    @Override
    public void moveRight(float delta) {
        shipRect.setX(shipRect.getX()+ Gdx.graphics.getDeltaTime()*movementSpeed);
    }
}
