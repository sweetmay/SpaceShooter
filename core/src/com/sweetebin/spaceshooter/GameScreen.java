package com.sweetebin.spaceshooter;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.Spring;
import javax.xml.soap.Text;

public class GameScreen implements Screen {

    //screen
    private Camera camera;
    private Viewport viewport;

    //graphics
    private SpriteBatch batch;
    private TextureRegion[] backgrounds;
    private TextureAtlas textureAtlas;
    private TextureRegion playerTextureRegion, playerShieldTextureRegion,
            enemyShipTextureRegion, enemyShieldTextureRegion, laserBlueRegion, laserRedRegion;

    //timing
    private float[] backgroundOffsets = {0,0,0,0};
    private float backgroundMaxScrollSpeed;


    //parameters
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT = 128;

    //game objs
    private Ship playerShip;
    private Ship enemyShip;
    private LinkedList<Laser> lasersLinkedList;

    GameScreen(){
        initTextures();

        playerShip = new PlayerShip(WORLD_WIDTH/2, WORLD_HEIGHT/4, 5, 10, 10, 10, 2f, 10, 60, 1, playerTextureRegion, playerShieldTextureRegion, laserBlueRegion, 0);
        enemyShip = new EnemyShip(WORLD_WIDTH/2, WORLD_HEIGHT*3/4, 5, 10, 10, 10, 4f, 5, 40, 2f, enemyShipTextureRegion, enemyShieldTextureRegion, laserRedRegion, 1);

        lasersLinkedList = new LinkedList<>();


        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);




        backgroundMaxScrollSpeed = (float) WORLD_HEIGHT / 4;
        batch = new SpriteBatch();

    }

    private void initTextures() {
        textureAtlas = new TextureAtlas("images.atlas");
        playerTextureRegion = textureAtlas.findRegion("playerShip1_green");
        playerShieldTextureRegion = textureAtlas.findRegion("shield1");
        enemyShipTextureRegion = textureAtlas.findRegion("enemyBlack1");
        enemyShieldTextureRegion = textureAtlas.findRegion("shield2");
        laserBlueRegion = textureAtlas.findRegion("laserBlue01");
        laserRedRegion = textureAtlas.findRegion("laserRed01");
        enemyShieldTextureRegion.flip(false, true);

        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("background0");
        backgrounds[1] = textureAtlas.findRegion("background1");
        backgrounds[2] = textureAtlas.findRegion("background2");
        backgrounds[3] = textureAtlas.findRegion("background3");
    }

    @Override
    public void render(float delta) {
        batch.begin();
        playerShip.update(delta);
        enemyShip.update(delta);
        //scrolling background
        renderBackground(delta);

        enemyShip.draw(batch);

        playerShip.draw(batch);

        renderLasers(delta);

        detectCollisions();

        batch.end();
    }

    private void detectCollisions() {
        ListIterator<Laser> iter = lasersLinkedList.listIterator();
        while (iter.hasNext()){
            Laser laser = iter.next();
            if(laser.ID == 0 && enemyShip.doesIntersects(laser.getHitbox())){
                enemyShip.hit(laser);
                iter.remove();
            }
            if(laser.ID == 1 && playerShip.doesIntersects(laser.getHitbox())){
                playerShip.hit(laser);
                iter.remove();
            }
        }
    }

    private void renderLasers(float delta) {
        if(playerShip.canFire()){
            Laser[] lasers = playerShip.fireLaser();
            lasersLinkedList.addAll(Arrays.asList(lasers));
        }
        if(enemyShip.canFire()){
            Laser[] lasers = enemyShip.fireLaser();
            lasersLinkedList.addAll(Arrays.asList(lasers));
        }

        ListIterator<Laser> iter = lasersLinkedList.listIterator();

        while (iter.hasNext()){
            Laser laser = iter.next();
            laser.draw(batch, delta);
        }
    }

    private void renderBackground(float delta) {
        backgroundOffsets[0] += delta * backgroundMaxScrollSpeed / 8;
        backgroundOffsets[1] += delta * backgroundMaxScrollSpeed / 6;
        backgroundOffsets[2] += delta * backgroundMaxScrollSpeed / 2;
        backgroundOffsets[3] += delta * backgroundMaxScrollSpeed;

        for(int layer = 0; layer < backgroundOffsets.length; layer++){
            if(backgroundOffsets[layer]>WORLD_HEIGHT){
                backgroundOffsets[layer] = 0;
            }
            batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer],
                    WORLD_WIDTH, WORLD_HEIGHT);
            batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer]+WORLD_HEIGHT,
                    WORLD_WIDTH, WORLD_HEIGHT);
        }

    }

    @Override
    public void show() {

    }



    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
