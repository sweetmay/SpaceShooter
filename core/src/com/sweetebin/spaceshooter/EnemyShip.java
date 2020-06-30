package com.sweetebin.spaceshooter;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyShip extends Ship {
    public EnemyShip(float xPos, float yPos,
                     float movementSpeed, int shield,
                     float width, float height,
                     float laserWidth, float laserHeight, float laserMovementSpeed,
                     float timeBeetwenShots,
                     TextureRegion shipTexture, TextureRegion shieldTexture, TextureRegion laserTexture, int ID) {
        super(xPos, yPos, movementSpeed, shield, width, height, laserWidth, laserHeight, laserMovementSpeed, timeBeetwenShots, shipTexture, shieldTexture, laserTexture, ID);
    }

    @Override
    public Laser[] fireLaser() {
        Laser[] lasers = new Laser[2];
        lasers[0] = new Laser(xPosCentre+width*0.2f, yPosCentre-laserHeight, laserWidth, laserHeight, laserMovementSpeed, laserTexture, ID);
        lasers[1] = new Laser(xPosCentre+width*0.8f, yPosCentre-laserHeight, laserWidth, laserHeight, laserMovementSpeed, laserTexture, ID);

        shotDelta = 0;

        return lasers;
    }
}
