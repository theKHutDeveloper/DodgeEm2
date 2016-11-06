package com.knowledgehut.developments.dodgeem2.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.knowledgehut.developments.dodgeem2.Camera.OrthoCamera;
import com.knowledgehut.developments.dodgeem2.DodgeEm2;
import com.knowledgehut.developments.dodgeem2.Entity.*;
//import com.knowledgehut.developments.dodgeem2.Level;
import com.knowledgehut.developments.dodgeem2.ScrollingBackground;

import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.knowledgehut.developments.dodgeem2.DodgeEm2.*;

class GameScreen extends Screen implements InputProcessor {
    private OrthoCamera camera;
    private float GAME_SCALE_X;

    private Player player;
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private ArrayList<Item> fruits = new ArrayList<Item>();
    private ArrayList<Item> icons = new ArrayList<Item>();
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private ArrayList<AnimatedItem> pellets = new ArrayList<AnimatedItem>();
    private Item galaxian;
    private Paddle paddle;

    private Texture[] enemyTextures = new Texture[4];
    private Texture[] fruitTextures = new Texture[3];
    private ItemType[] fruitType = new ItemType[3];

    private ScrollingBackground background, foreground;
    private int counterToAddNewEnemy, counterToNewFruit, counterToNewPellet;
    private String scoreText, fruitText, enemiesText, timeText;
    private String cherryText, berryText, orangeText, galaxianText;
    private BitmapFont bmpFont;

    private boolean playerCanFireBullets;
    private boolean bulletExists;
    private boolean playerInvincible;
    private boolean playerTouched;

    private long delayTime = 1000;
    private long screenActive;
    private long fireStartTime, invincibleStartTime;
    private long startTime, scoreTime;
    private long gameTime, galaxianTime;
    private long FIRE_TIME_LIMIT = 10000;
    private int MAX_SPEED = 2;
    private int ADD_NEW_ENEMY_RATE = 0;
    ///private Level levelManager;

    private int playerPointer;

    private Music backgroundMusic;


    @Override
    public void create() {
        GAME_SCALE_X = (float)(Gdx.graphics.getWidth() )/ (float)(WIDTH);
        camera = new OrthoCamera(WIDTH, HEIGHT);

        screenActive = System.currentTimeMillis();
        Gdx.input.setInputProcessor(this);

        SCORE = FRUIT_SCORE = ENEMIES_KILLED =0;
        CHERRY_SCORE = STRAWBERRY_SCORE = ORANGE_SCORE = GALAXIAN_SCORE = PELLET_SCORE = 0;
        counterToAddNewEnemy = 0;
        counterToNewFruit = 0;
        counterToNewPellet = 0;

        playerCanFireBullets = false;
        playerInvincible = true;
        bulletExists = false;
        scoreText = "Score: ";
        fruitText = "Fruits: ";
        enemiesText = "Enemies: ";
        timeText = "Time: ";
        cherryText = ":";
        berryText = ":";
        orangeText = ":";
        galaxianText = ":";

        bmpFont = new BitmapFont(Gdx.files.internal("Fonts/mediumFont.fnt"), true);
        bmpFont.getData().setScale(GAME_SCALE_X);
        background = new ScrollingBackground(new Texture(Gdx.files.internal("Images/Parallax100.png")), 1);
        foreground = new ScrollingBackground(new Texture(Gdx.files.internal("Images/BackdropBlackLittleSparkTransparent.png")), 2);

        playerTouched = false;

        int playerFrames = 2;
        player = new Player(new Texture(Gdx.files.internal("Images/pacman.png")),
                new Vector2((DodgeEm2.WIDTH/2) * GAME_SCALE_X, (DodgeEm2.HEIGHT - 101) * GAME_SCALE_X),
                new Vector2(0,0), playerFrames, DodgeEm2.WIDTH * GAME_SCALE_X);


        enemyTextures[0] = new Texture(Gdx.files.internal("Images/cyborg_blink.png"));
        enemyTextures[1] = new Texture(Gdx.files.internal("Images/alien_blink.png"));
        enemyTextures[2] = new Texture(Gdx.files.internal("Images/devil_blink.png"));
        enemyTextures[3] = new Texture(Gdx.files.internal("Images/chequers_blink.png"));

        fruitTextures[0] = new Texture(Gdx.files.internal("Images/cherry.png"));
        fruitType[0] = ItemType.CHERRY;
        fruitTextures[1] = new Texture(Gdx.files.internal("Images/strawberry.png"));
        fruitType[1] = ItemType.STRAWBERRY;
        fruitTextures[2] = new Texture(Gdx.files.internal("Images/mandarin.png"));
        fruitType[2] = ItemType.ORANGE;

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Sounds/halloween_hunting.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

        icons.add(new Item(fruitTextures[0], new Vector2(10 * GAME_SCALE_X, 460 * GAME_SCALE_X),
                new Vector2(0,0), 15,fruitType[0]));
        icons.add(new Item(fruitTextures[1], new Vector2(60 * GAME_SCALE_X, 460 * GAME_SCALE_X),
                new Vector2(0,0), 15,fruitType[1]));
        icons.add(new Item(fruitTextures[2], new Vector2(110 * GAME_SCALE_X, 460 * GAME_SCALE_X),
                new Vector2(0,0), 15,fruitType[2]));
        icons.add(new Item(new Texture(Gdx.files.internal("Images/galaxian.png")),
                new Vector2(160 * GAME_SCALE_X, 460 * GAME_SCALE_X), new Vector2(0,0), 15, ItemType.GALAXIAN));


        paddle = new Paddle(new Texture(Gdx.files.internal("Images/paddle.png")),
                new Vector2(player.getPosition().x, player.getPosition().y));

        startTime = scoreTime = gameTime = galaxianTime = TimeUtils.millis();

       // if(GAME_MODE){
            //levelManager = new Level(DodgeEm2.level_number);
       // }
    }

    @Override
    public void update() {

        /*if(GAME_MODE){
            if(levelManager.Success()){
            levelManager.setLevel(++level_number);
            clearScores();
                backgroundMusic.stop();
                ScreenManager.setScreen(new FinScreen());
            }
        }*/
        float currentTime = TimeUtils.timeSinceMillis(gameTime) + Gdx.graphics.getDeltaTime();
        int seconds = (int)(currentTime / 1000)% 60;
        int minutes = (int)((currentTime / 1000)/60);

        timeText = (minutes + " : " + (seconds<10?"0":"") + seconds);

        player.update();
        paddle.update(new Vector2(player.getPosition().x, player.getPosition().y));

        counterToAddNewEnemy++;
        counterToNewFruit++;
        counterToNewPellet++;

        if(TimeUtils.timeSinceMillis(scoreTime) > 10000){
            SCORE += 20;
            scoreTime = TimeUtils.millis();
        }

        if(MAX_SPEED <= 5){
            ADD_NEW_ENEMY_RATE = 100;
        }
        else if(MAX_SPEED > 5 && MAX_SPEED <= 10){
            ADD_NEW_ENEMY_RATE = 50;
        }
        else if(MAX_SPEED > 11 && MAX_SPEED <= 14){
            ADD_NEW_ENEMY_RATE = 36;
        }
        else if(MAX_SPEED > 15){
            ADD_NEW_ENEMY_RATE = 25;
        }

        int MIN_SPEED = 1;

        if(TimeUtils.timeSinceMillis(startTime) > 30000){
            MAX_SPEED += 2;
            startTime += 30000;
            counterToAddNewEnemy = 0;
        }

        if(counterToAddNewEnemy == ADD_NEW_ENEMY_RATE){
            counterToAddNewEnemy = 0;
            int randomX = random.nextInt((WIDTH - 60) + 20) + 20;

            int MIN_SIZE = 25;
            int MAX_SIZE = 60;
            int FRAME_SIZE = 5;

            float randomSize = (float)(random.nextInt((MAX_SIZE - MIN_SIZE)+ 1) + MIN_SIZE);
            float randomSpeed = (float)(MIN_SPEED + random.nextInt(MAX_SPEED));

            int no = random.nextInt(enemyTextures.length);
            enemies.add(new Enemy(enemyTextures[no],
                    new Vector2(randomX * GAME_SCALE_X, 0),
                    new Vector2(0,randomSpeed), FRAME_SIZE, randomSize));
        }

        if(counterToNewFruit == 240){
            counterToNewFruit = 0;
            int randomX = random.nextInt((WIDTH - 60) + 20) + 20;
            int no = random.nextInt(fruitTextures.length);
            float randomSpeed = (float)(MIN_SPEED + random.nextInt(6));
            fruits.add(new Item(fruitTextures[no],
                    new Vector2(randomX * GAME_SCALE_X, 0),
                    new Vector2(0, randomSpeed), 25, fruitType[no]));
        }

        if(counterToNewPellet == 1200){
            counterToNewPellet = 0;
            int randomX = random.nextInt((WIDTH - 60) + 20) + 20;
            float randomSpeed = (float)(MIN_SPEED + random.nextInt(6));

            pellets.add(new AnimatedItem(new Texture(Gdx.files.internal("Images/pellet_anim.png")),
                    new Vector2(randomX * GAME_SCALE_X, 0),
                    new Vector2(0, randomSpeed), 5, 28));
        }


        if(TimeUtils.timeSinceMillis(galaxianTime) > 60000){
            int randomX = random.nextInt((WIDTH - 60) + 20) + 20;
            float randomSpeed = (float)(MIN_SPEED + random.nextInt(6));

            galaxian = new Item(new Texture(Gdx.files.internal("Images/galaxian.png")),
                    new Vector2(randomX * GAME_SCALE_X,0), new Vector2(0,randomSpeed), 30, ItemType.GALAXIAN);
            galaxianTime = TimeUtils.millis();
        }

        int SCREEN_HEIGHT = HEIGHT - 48;
        if(!enemies.isEmpty()){
            for(int i = enemies.size()-1; i >=0; i--){
                enemies.get(i).update();

                if(!playerInvincible && player.hasCollided(enemies.get(i).getCircle())){
                    if(enemies.get(i).getKillMode()) {
                        backgroundMusic.stop();
                        ScreenManager.setScreen(new FinScreen());
                    }
                    break;
                }

                if(enemies.get(i).isDestroy()){
                    enemies.remove(i);
                }
                else if(bulletExists){
                    if(enemies.get(i).bulletHitCollision(bullets.get(0).getCircle())){
                        enemies.get(i).setEffect();
                        ENEMIES_KILLED += 1;
                        SCORE += 2000;
                        bullets.remove(0);
                        bulletExists = false;
                    }
                }

            }
        }

        if(!fruits.isEmpty()){
            for(int i = fruits.size()-1; i >=0; i--){
                fruits.get(i).update();

                if(player.hasCollided(fruits.get(i).getCircle())){
                    player.setSparkleEffect();
                    FRUIT_SCORE += 1;
                    switch (fruits.get(i).getItemType()){
                        case CHERRY:
                            CHERRY_SCORE++;
                            SCORE += 100;
                        break;
                        case STRAWBERRY:
                            STRAWBERRY_SCORE++;
                            SCORE += 300;
                        break;
                        case ORANGE:
                            ORANGE_SCORE++;
                            SCORE += 500;
                        break;
                    }
                    fruits.remove(i);
                }
                else if(fruits.get(i).getPosition().y > SCREEN_HEIGHT * GAME_SCALE_X){
                    fruits.remove(i);
                }
            }
        }

        if(!pellets.isEmpty()){
            for(int i = pellets.size()-1; i >=0; i--){

                if(player.hasCollided(pellets.get(i).getCircle())){
                    playerCanFireBullets = true;
                    fireStartTime = TimeUtils.millis();
                    player.changePlayerTexture("Images/bomber_pacman.png");
                    pellets.remove(i);
                }
                else if(pellets.get(i).getPosition().y > SCREEN_HEIGHT * GAME_SCALE_X){
                    pellets.remove(i);
                }

                if( i < pellets.size()) pellets.get(i).update();
            }
        }

        if(!bullets.isEmpty()){
            for (int i = bullets.size()-1; i >=0 ; i--) {
                if(bullets.get(i).isFinished()){
                    bullets.remove(i);
                    bulletExists = false;
                }
            }
        }

        if(galaxian != null){
            galaxian.update();
            if(player.hasCollided(galaxian.getCircle())){
                galaxian.setPosition(new Vector2(galaxian.getPosition().x * GAME_SCALE_X, 500 * GAME_SCALE_X));
                SCORE += 3000;
                GALAXIAN_SCORE++;
                playerInvincible = true;
                invincibleStartTime = TimeUtils.millis();
            }
            if(galaxian.getPosition().y > SCREEN_HEIGHT * GAME_SCALE_X){
                galaxian.dispose();
            }
        }

        if(playerInvincible){
            long INVINCIBILITY_TIME = 20000;
            if(TimeUtils.timeSinceMillis(invincibleStartTime) > INVINCIBILITY_TIME){
                playerInvincible = false;
                player.hideShield();
            }
            else if(TimeUtils.timeSinceMillis(invincibleStartTime) < INVINCIBILITY_TIME){
                //player is invincible
                player.showShield();

            }

        }

        if(playerCanFireBullets){

            if(TimeUtils.timeSinceMillis(fireStartTime) > FIRE_TIME_LIMIT){
                playerCanFireBullets = false;
                bulletExists = false;
                player.changePlayerTexture("Images/pacman.png");
            }
        }

        for (Bullet bullet : bullets) {
            bullet.update();
        }

        camera.update();
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        background.updateAndRender(0.05f, spriteBatch);
        foreground.updateAndRender(0.1f, spriteBatch);

        bmpFont.draw(spriteBatch, scoreText + Integer.toString(SCORE), 10 * GAME_SCALE_X, 10 * GAME_SCALE_X);
        bmpFont.draw(spriteBatch, enemiesText + Integer.toString(ENEMIES_KILLED), 210 * GAME_SCALE_X, 10 * GAME_SCALE_X);
        bmpFont.draw(spriteBatch, timeText, 100 * GAME_SCALE_X, 10 * GAME_SCALE_X);

        player.render(spriteBatch);


        for (Enemy enemy : enemies) {
            enemy.render(spriteBatch);
        }

        for (Item fruit : fruits) {
            fruit.render(spriteBatch);
        }

        for (AnimatedItem pellet : pellets) {
            pellet.render(spriteBatch);
        }

        for (Bullet bullet : bullets) {
            bullet.render(spriteBatch);
        }

        if(galaxian != null){
            galaxian.render(spriteBatch);
        }

        spriteBatch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        /*shapeRenderer.circle(player.getCircle().x, player.getCircle().y, player.getCircle().radius);
        for (Enemy enemy : enemies) {
            shapeRenderer.circle(enemy.getCircle().x, enemy.getCircle().y, enemy.getCircle().radius);
        }*/
        shapeRenderer.rectLine(0,(HEIGHT - 48 - 5) * GAME_SCALE_X, WIDTH * GAME_SCALE_X, (HEIGHT - 48 - 5) * GAME_SCALE_X, 5 * GAME_SCALE_X);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rectLine(0,HEIGHT * GAME_SCALE_X, WIDTH * GAME_SCALE_X, HEIGHT * GAME_SCALE_X, 102 * GAME_SCALE_X);
        shapeRenderer.end();

        spriteBatch.begin();
        bmpFont.draw(spriteBatch, fruitText + Integer.toString(FRUIT_SCORE), 10 * GAME_SCALE_X, 440 * GAME_SCALE_X);

        for (Item icon : icons) {
            icon.render(spriteBatch);
        }
        bmpFont.draw(spriteBatch, cherryText + Integer.toString(CHERRY_SCORE), 30 * GAME_SCALE_X, 460 * GAME_SCALE_X);
        bmpFont.draw(spriteBatch, berryText + Integer.toString(STRAWBERRY_SCORE), 80 * GAME_SCALE_X, 460 * GAME_SCALE_X);
        bmpFont.draw(spriteBatch, orangeText + Integer.toString(ORANGE_SCORE), 130 * GAME_SCALE_X, 460 * GAME_SCALE_X);
        bmpFont.draw(spriteBatch, galaxianText + Integer.toString(GALAXIAN_SCORE), 180 * GAME_SCALE_X, 460 * GAME_SCALE_X);

        paddle.render(spriteBatch);
        spriteBatch.end();
    }

    @SuppressWarnings("unused")
    private void clearScores(){
        SCORE = FRUIT_SCORE = ENEMIES_KILLED =0;
        CHERRY_SCORE = STRAWBERRY_SCORE = ORANGE_SCORE = GALAXIAN_SCORE = 0;
        counterToAddNewEnemy = 0;
        counterToNewFruit = 0;
        counterToNewPellet = 0;

        playerCanFireBullets = false;
        playerInvincible = true;
        bulletExists = false;

        startTime = scoreTime = gameTime = galaxianTime = TimeUtils.millis();
    }

    @Override
    public void resize(int width, int height) {
        camera.resize(width, height);
    }

    @Override
    public void dispose() {
        background.dispose();
        foreground.dispose();
        player.dispose();
        paddle.dispose();

        for (Enemy enemy : enemies) {
            enemy.dispose();
        }

        for (Item fruit : fruits) {
            fruit.dispose();
        }

        for (AnimatedItem pellet : pellets) {
            pellet.dispose();
        }

        for (Bullet bullet : bullets) {
            bullet.dispose();
        }

        if(galaxian != null) galaxian.dispose();

        bmpFont.dispose();

        for (Item icon : icons) {
            icon.dispose();
        }

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(pointer < 5) {
            if (TimeUtils.timeSinceMillis(screenActive) > delayTime)
                if (playerCanFireBullets) {
                        if (TimeUtils.timeSinceMillis(fireStartTime) < FIRE_TIME_LIMIT && !bulletExists) {
                            bulletExists = true;
                            bullets.add(new Bullet(new Texture(Gdx.files.internal("Images/bullet.png")),
                                    new Vector2(player.getCircle().x, player.getPosition().y), new Vector2(0f, -3f)));
                        }
                    }

            if (screenX >= paddle.getPosition().x && screenX <= paddle.getPosition().x + paddle.getScale()
                    && screenY >= paddle.getPosition().y && screenY <= paddle.getPosition().y + paddle.getScale()) {
                playerTouched = true;
                playerPointer = pointer;
            }

        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if(pointer == playerPointer) playerTouched = false;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        if(TimeUtils.timeSinceMillis(screenActive) > delayTime)
          if(playerTouched)  player.movePlayer(screenX);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

//TODO: Super Pellet required - player can eat ghosts, NOT SURE IF THIS IS A THING
//TODO: Freeze ghosts for a limited time, this will allow player to get some extra items - maybe not sure
//TODO: Lift platform only in classic mode


       