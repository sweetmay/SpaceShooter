package com.sweetebin.spaceshooter;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyShip extends Ship {
    public EnemyShip(float xPos, float yPos,
                     float movementSpeed, int shield,
                     float width, float height,
                     float laserWidth, float laserHeight, float laserMovementSpeed,
                     float timeBeetwenShots,
                     TextureRegion shipTexture, TextureRegion shieldTexture, TextureRegion laserTexture, int ID, int health) {
        super(xPos, yPos, movementSpeed, shield, width, height, laserWidth, laserHeight, laserMovementSpeed, timeBeetwenShots, shipTexture, shieldTexture, laserTexture, ID, health);
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
    public void moveLeft(float delta) {

    }

    @Override
    public void moveRight(float delta) {

    }

}
