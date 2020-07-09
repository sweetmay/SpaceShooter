package com.sweetebin.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Explosion {
    private static final int FRAME_COLS = 4;
    private static final int FRAME_ROWS = 4;
    Texture explosions;
    Vector2 position;
    float timer;
    float totalExplosionTimer;
    boolean isEnded;
    Ship ship;

    private Animation<TextureRegion> explosionAnimation;

    public Explosion(Ship ship){
        this.ship = ship;
        position = new Vector2(ship.shipRect.getX(), ship.shipRect.getY());
        explosions = new Texture(Gdx.files.internal("exp.png"));
        totalExplosionTimer = 0.3f;
        TextureRegion[][] tmp = TextureRegion.split(explosions,
                explosions.getWidth()/FRAME_COLS,
                explosions.getHeight()/FRAME_ROWS);

        TextureRegion[] explosionsFrames = new TextureRegion[FRAME_ROWS * FRAME_COLS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++){
            for(int j = 0; j < FRAME_COLS; j++){
                explosionsFrames[index++] = tmp[i][j];
            }
        }

        explosionAnimation = new Animation<TextureRegion>(totalExplosionTimer/16, explosionsFrames);
        timer = 0f;
    }

    public void update(SpriteBatch batch){
        timer += Gdx.graphics.getDeltaTime();
        if(timer<=totalExplosionTimer){
            draw(batch);
        }
    }

    public void draw(SpriteBatch batch){
        TextureRegion currentFrame = explosionAnimation.getKeyFrame(timer, true);
        if(ship.ID == 0){
        batch.draw(currentFrame, position.x-ship.shipRect.width/2, position.y, ship.shipRect.width*2f, ship.shipRect.height*1.5f);
        }else {
            batch.draw(currentFrame, position.x, position.y, ship.shipRect.width*1.5f, ship.shipRect.height*1.5f);
        }

    }
}
