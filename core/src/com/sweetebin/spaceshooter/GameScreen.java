package com.sweetebin.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;


public class GameScreen implements Screen {
    //input
    private SSInputProcessor inputProcessor;

    //screen
    private Camera camera;
    private Viewport viewport;

    //graphics
    private HealthShieldBar healthShieldBar;
    private SpriteBatch batch;
    private TextureRegion[] backgrounds;
    private TextureAtlas textureAtlas;
    private TextureRegion playerTextureRegion, playerShieldTextureRegion,
            enemyShipTextureRegion, enemyShieldTextureRegion,
            laserBlueRegion, laserRedRegion;

    //timing
    private float[] backgroundOffsets = {0,0,0,0};
    private float backgroundMaxScrollSpeed;


    //parameters
    private static final int MAX_ENEMIES = 5;
    private static final int WORLD_WIDTH = 144;
    private static final int WORLD_HEIGHT = 256;

    //game objs
    private LinkedList<Explosion> explosionLinkedList;
    private Ship playerShip;
    private LinkedList<Laser> lasersLinkedList;
    private LinkedList<Ship> ships;
    GameScreen(){
        initTextures();
        playerShip = new PlayerShip(WORLD_WIDTH/2, WORLD_HEIGHT/4, 120, 10, 20, 20, 3f, 20, 300, 0.15f, playerTextureRegion, playerShieldTextureRegion, laserBlueRegion, 0, 100);
        ships = new LinkedList<>();
        ships.add(playerShip);
        lasersLinkedList = new LinkedList<>();
        explosionLinkedList = new LinkedList<>();


        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);


        healthShieldBar = new HealthShieldBar(playerShip);

        backgroundMaxScrollSpeed = (float) WORLD_HEIGHT / 4;
        batch = new SpriteBatch();
        inputProcessor = new SSInputProcessor(playerShip, viewport);
        Gdx.input.setInputProcessor(inputProcessor);

    }

    private void addEnemies(ListIterator<Ship> iter) {
        for (int i = 0; i < MAX_ENEMIES; i++) {
            iter.add(new EnemyShip(WORLD_WIDTH/(i+1), WORLD_HEIGHT + 10,
                    40, 10, 15, 15,
                    4f, 10, (float) (40 + Math.random()*45),
                    (float) (1 + Math.random()), enemyShipTextureRegion, enemyShieldTextureRegion,
                    laserRedRegion, 1, 5));
        }
    }

    public static int getWorldWidth() {
        return WORLD_WIDTH;
    }

    public static int getWorldHeight() {
        return WORLD_HEIGHT;
    }

    private void initTextures() {
        textureAtlas = new TextureAtlas("images.atlas");
        enemyShieldTextureRegion = textureAtlas.findRegion("shield2");
        playerTextureRegion = textureAtlas.findRegion("playerShip1_green");

        playerShieldTextureRegion = textureAtlas.findRegion("shield3");
        enemyShipTextureRegion = textureAtlas.findRegion("enemyBlack1");

        laserBlueRegion = textureAtlas.findRegion("laserBlue01");
        laserRedRegion = textureAtlas.findRegion("laserRed01");
        laserRedRegion.flip(false, true);
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

        renderBackground(delta);
        shipsIterating(delta);
        renderLasers(delta);
        detectCollisions();
        updateAndRenderExplosions();
        healthShieldBar.update();
        healthShieldBar.draw(batch);
        batch.end();
    }

    private void detectCollisions() {
        ListIterator<Laser> iter = lasersLinkedList.listIterator();
        while (iter.hasNext()){
            Laser laser = iter.next();
            for (Ship ship:ships) {
                if(ship.ID != laser.ID && ship.doesIntersects(laser.getHitbox())){
                    ship.hit(laser);
                    iter.remove();
                    break;
                }
            }
        }
    }

    private void createExplosion(Ship ship) {
        explosionLinkedList.add(new Explosion(ship));
    }

    private void updateAndRenderExplosions(){
        ListIterator<Explosion> iter = explosionLinkedList.listIterator();
        while (iter.hasNext()){
            Explosion explosion = iter.next();
            explosion.update(batch);
            if(explosion.isEnded){
                iter.remove();
            }

        }
    }

    private void shipsIterating(float delta) {
        ListIterator<Ship> shipIter = ships.listIterator();

        while (shipIter.hasNext()){
            Ship ship = shipIter.next();
            if(ships.size() == 1){
                addEnemies(shipIter);
            }
            ship.update(delta);
            ship.draw(batch);
            if(!ship.isAlive){
                createExplosion(ship);
                shipIter.remove();
            }
        }
    }

    private void renderLasers(float delta) {
        for (Ship ship: ships) {
            if(ship.canFire()){
                lasersLinkedList.addAll(Arrays.asList(ship.fireLaser()));
            }
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
