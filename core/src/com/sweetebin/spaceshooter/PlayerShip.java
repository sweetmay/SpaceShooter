package com.sweetebin.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerShip extends Ship {

    public PlayerShip(float xPos, float yPos,
                      float movementSpeed, int shield, float width, float height,
                      float laserWidth, float laserHeight,
                      float laserMovementSpeed, float timeBeetwenShots,
                      TextureRegion shipTexture, TextureRegion shieldTexture, TextureRegion laserTexture, int ID, int health) {
        super(xPos, yPos, movementSpeed, shield, width, height, laserWidth, laserHeight, laserMovementSpeed, timeBeetwenShots, shipTexture, shieldTexture, laserTexture, ID, health);
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
    public void moveLeft(float delta) {
        shipRect.setX(shipRect.getX()-delta*movementSpeed);
    }

    @Override
    public void moveRight(float delta) {
        shipRect.setX(shipRect.getX()+ Gdx.graphics.getDeltaTime()*movementSpeed);
    }
}
