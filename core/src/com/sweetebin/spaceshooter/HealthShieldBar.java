package com.sweetebin.spaceshooter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HealthShieldBar{
    int fullHP;
    int fullShield;
    Ship ship;
    Pixmap BG;
    Pixmap health;
    Pixmap shield;
    Pixmap shieldBG;
    Texture hptexture;
    Texture shieldTexture;
    public HealthShieldBar(Ship ship){
        this.ship = ship;
        fullHP = ship.health;
        fullShield = ship.shield;
    }

    public void createHealthBar(){
        float percentage = (float) ship.health/fullHP;
        BG = new Pixmap(40, 10, Pixmap.Format.RGBA8888);
        BG.setColor(Color.BLACK);
        BG.drawRectangle(0, 0, BG.getWidth(), BG.getHeight());

        health = new Pixmap((int) (percentage*38), 8, Pixmap.Format.RGBA8888);
        health.setColor(Color.RED);
        health.fillRectangle(0, 0, 38, 8);


        hptexture = new Texture(BG);
        hptexture.draw(health, 1, 1);
        BG.dispose();
        health.dispose();

    }

    public void createShieldBar(){
        float percentage = (float) ship.shield/fullShield;
        BG = new Pixmap(40, 10, Pixmap.Format.RGBA8888);
        BG.setColor(Color.BLACK);
        BG.drawRectangle(0, 0, BG.getWidth(), BG.getHeight());

        shield = new Pixmap((int) (percentage*38), 8, Pixmap.Format.RGBA8888);
        shield.setColor(Color.BLUE);
        shield.fillRectangle(0, 0, 38, 8);


        shieldTexture = new Texture(BG);
        shieldTexture.draw(shield, 1, 1);
        BG.dispose();
        shield.dispose();
    }

    public void update(){
        createHealthBar();
        createShieldBar();
    }

    public void draw(SpriteBatch batch){
        batch.draw(hptexture, 0, GameScreen.getWorldHeight()- hptexture.getHeight());
        batch.draw(shieldTexture, 0, GameScreen.getWorldHeight()- hptexture.getHeight()*2);
    }
}
