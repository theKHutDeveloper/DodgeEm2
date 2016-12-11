package com.knowledgehut.developments.dodgeem2.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.knowledgehut.developments.dodgeem2.Camera.OrthoCamera;
import com.knowledgehut.developments.dodgeem2.DodgeEm2;
import com.knowledgehut.developments.dodgeem2.Entity.*;
import com.knowledgehut.developments.dodgeem2.Level;
import com.knowledgehut.developments.dodgeem2.ScrollingBackground;
import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.knowledgehut.developments.dodgeem2.DodgeEm2.*;



class GameScreen extends Screen implements InputProcessor {
    private OrthoCamera camera;
    private float GAME_SCALE_X, GAME_SCALE_Y;

    private Player player;
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private ArrayList<Item> fruits = new ArrayList<Item>();
    private ArrayList<Item> icons = new ArrayList<Item>();
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    private ArrayList<AnimatedItem> pellets = new ArrayList<AnimatedItem>();
    private Item galaxian;
    private Platform platform;
    private Paddle paddle;

    private Texture[] enemyTextures = new Texture[4];
    private Texture[] fruitTextures = new Texture[3];
    private ItemType[] fruitType = new ItemType[3];

    private ScrollingBackground background, foreground;
    private int counterToAddNewEnemy, counterToNewFruit, counterToNewPellet;
    private String scoreText, fruitText, enemiesText, timeText;
    private String cherryText, berryText, orangeText, galaxianText;
    private String bulletText, shieldText, levelText;
    private BitmapFont bmpFont, impactRed, impactSilver;

    private boolean playerCanFireBullets;
    private boolean playerInvincible;
    private boolean playerTouched;

    private long screenActive;
    private long fireStartTime, invincibleStartTime;
    private long startTime, scoreTime;
    private long gameTime, galaxianTime;
    private long platformMoveTime;

    private long FIRE_TIME_LIMIT = 10000;
    private int MAX_SPEED = 2;
    private int ADD_NEW_ENEMY_RATE = 0;
    private int offsetX;
    private int platformMethod;
    private Level levelManager;

    private int playerPointer;
    private int playLevel;

    private Music backgroundMusic, gameOver, slideUp, slideDown;
    private Sound explode, bulletSound, playerEats;
    private boolean musicOn, soundOn;
    private boolean startPlatformMechanics;
    private int platformRandom;
    private boolean pause;

    private Sprite text_complete;
    private long gameOverDelay;
    private int gameEndLoop = 0;

    GameScreen(int level){
        if(level != 0){
            playLevel = level;
        }
    }


    @Override
    public void create() {
        GAME_SCALE_X = (float)(Gdx.graphics.getWidth() )/ (float)(WIDTH);
        GAME_SCALE_Y = (float)(Gdx.graphics.getHeight())/ (float)(HEIGHT);

        camera = new OrthoCamera(WIDTH, HEIGHT);

        screenActive = System.currentTimeMillis();
        Gdx.input.setInputProcessor(this);

        SCORE = FRUIT_SCORE = ENEMIES_KILLED =0;
        CHERRY_SCORE = STRAWBERRY_SCORE = ORANGE_SCORE = GALAXIAN_SCORE = PELLET_SCORE = 0;
        ACHIEVED_TIME = "";
        counterToAddNewEnemy = 0;
        counterToNewFruit = 0;
        counterToNewPellet = 0;

        playerCanFireBullets = false;
        playerInvincible = true;
        scoreText = "Score:  ";
        fruitText = "Fruits: ";
        enemiesText = "Enemies:  ";
        timeText = "Time:  ";
        cherryText = ":";
        berryText = ":";
        orangeText = ":";
        galaxianText = ":";
        bulletText = "";
        shieldText = "";

        bmpFont = new BitmapFont(Gdx.files.internal("Fonts/impact_silver_small.fnt"), true);
        bmpFont.getData().setScale(GAME_SCALE_X);
        impactRed = new BitmapFont(Gdx.files.internal("Fonts/impact_red_large.fnt"), true);
        impactRed.getData().setScale(GAME_SCALE_X);
        impactSilver = new BitmapFont(Gdx.files.internal("Fonts/impact_silver_large.fnt"), true);
        impactSilver.getData().setScale(GAME_SCALE_X);

        background = new ScrollingBackground(new Texture(Gdx.files.internal("Images/BackdropBlackLittleSparkBlack_small.png")), 1);
        foreground = new ScrollingBackground(new Texture(Gdx.files.internal("Images/BackdropBlackLittleSparkTransparent_small.png")), 2);

        playerTouched = false;
        playerPointer = -1;
        offsetX = 0;
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

        musicOn = DodgeEm2.prefs.getBoolean("musicOn");
        soundOn = DodgeEm2.prefs.getBoolean("soundOn");

        if(musicOn){
            backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Sounds/halloween_hunting.mp3"));
            backgroundMusic.setLooping(true);
            backgroundMusic.play();
        }

        if(soundOn){
            explode = Gdx.audio.newSound(Gdx.files.internal("Sounds/explosion1.ogg"));
            playerEats = Gdx.audio.newSound(Gdx.files.internal("Sounds/pacman_eatghost.ogg"));
            bulletSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/fiveSound.ogg"));
            gameOver = Gdx.audio.newMusic(Gdx.files.internal("Sounds/pacman_game_over.mp3"));
            gameOver.setLooping(false);
            slideDown = Gdx.audio.newMusic(Gdx.files.internal("Sounds/slide_whistle_down.mp3"));
            slideDown.setLooping(false);
            slideUp = Gdx.audio.newMusic(Gdx.files.internal("Sounds/slide_whistle_up.mp3"));
            slideUp.setLooping(false);
        }

        icons.add(new Item(fruitTextures[0], new Vector2(10 * GAME_SCALE_X, 460 * GAME_SCALE_Y),
                new Vector2(0,0), 15,fruitType[0]));
        icons.add(new Item(fruitTextures[1], new Vector2(60 * GAME_SCALE_X, 460 * GAME_SCALE_Y),
                new Vector2(0,0), 15,fruitType[1]));
        icons.add(new Item(fruitTextures[2], new Vector2(110 * GAME_SCALE_X, 460 * GAME_SCALE_Y),
                new Vector2(0,0), 15,fruitType[2]));
        icons.add(new Item(new Texture(Gdx.files.internal("Images/galaxian.png")),
                new Vector2(160 * GAME_SCALE_X, 460 * GAME_SCALE_Y), new Vector2(0,0), 15, ItemType.GALAXIAN));

        platform = new Platform(new Texture(Gdx.files.internal("Images/metal_platform.png")),
                new Vector2(0, 422 * GAME_SCALE_Y), new Vector2(0,0));

        paddle = new Paddle(new Texture(Gdx.files.internal("Images/paddle.png")),
                new Vector2(player.getPosition().x, platform.getPosition().y));

        startTime = scoreTime = gameTime = galaxianTime = TimeUtils.millis();

        platformMoveTime = 0;
        startPlatformMechanics = false;
        pause = false;
        platformMethod = 0;


        Texture texture = new Texture(Gdx.files.internal("Images/complete_text.png"));
        text_complete = new Sprite(texture);
        text_complete.setPosition(0,HEIGHT/2);
        text_complete.setScale(texture.getWidth() * GAME_SCALE_X, texture.getHeight() * GAME_SCALE_X);

         if(GAME_MODE){
            levelManager = new Level(playLevel);
            levelText = "Level: "+ playLevel;
        }
    }

    @Override
    public void update() {

        if(GAME_MODE){
            if(levelManager.Success()){

                if(gameEndLoop == 0) {
                    if(musicOn) backgroundMusic.stop();
                    pause();

                    gameOverDelay = TimeUtils.millis();
                    gameEndLoop = 1;
                }
                if(TimeUtils.timeSinceMillis(gameOverDelay) > 2000){

                    ScreenManager.setScreen(new ArcadeScreen(playLevel, levelManager.Success()));
                }
            }
        }

        if(!pause) {

            float currentTime = TimeUtils.timeSinceMillis(gameTime) + Gdx.graphics.getDeltaTime();
            int seconds = (int) (currentTime / 1000) % 60;
            int minutes = (int) ((currentTime / 1000) / 60);

            timeText = (minutes + " : " + (seconds < 10 ? "0" : "") + seconds);

            counterToAddNewEnemy++;
            counterToNewFruit++;
            counterToNewPellet++;

            //platform movement only occurs on classic mode or on arcade level 30+
            if (!GAME_MODE || (GAME_MODE && playLevel >= 30)) {
                if (TimeUtils.timeSinceMillis(gameTime) > 30000) {
                    if (platformMoveTime == 0) {
                        platformMoveTime = TimeUtils.millis();
                        startPlatformMechanics = true;
                    }

                    //create a random generator to choose which platform method to evoke
                    if (startPlatformMechanics) {
                        if (TimeUtils.timeSinceMillis(gameTime) > 60000) {
                            platformMethod = random.nextInt(7) + 1;
                            startPlatformMechanics = false;
                        } else {
                            platformMethod = random.nextInt(4) + 1;
                            startPlatformMechanics = false;
                        }
                        platformRandom = random.nextInt(7000);
                    }

                    if (TimeUtils.timeSinceMillis(platformMoveTime) > (5000 + platformRandom) && !startPlatformMechanics) {//30000
                        switch (platformMethod) {
                            case 1:
                                platform.movePlatformHorizontal(platform.getPosition().x + (20 * GAME_SCALE_X));
                                break;
                            case 2:
                                platform.movePlatformHorizontal(platform.getPosition().x - (20 * GAME_SCALE_X));
                                break;
                            case 3:
                                if (soundOn) slideUp.play();
                                platform.movePlatform(platform.getPosition().y - (20 * GAME_SCALE_Y));
                                break;
                            case 4:
                                platform.shrinkPlatform(40 * GAME_SCALE_X);
                                break;
                            case 5:
                                platform.movePlatform(platform.getPosition().y - (30 * GAME_SCALE_Y));
                                platform.shrinkPlatform(40 * GAME_SCALE_X);
                                break;
                            case 6:
                                platform.movePlatform(platform.getPosition().y - (30 * GAME_SCALE_Y));
                                platform.movePlatformHorizontal(platform.getPosition().x + (20 * GAME_SCALE_X));
                                break;
                            case 7:
                                platform.movePlatform(platform.getPosition().y - (30 * GAME_SCALE_Y));
                                platform.movePlatformHorizontal(platform.getPosition().x - (20 * GAME_SCALE_X));
                                break;
                        }

                        platformMoveTime = TimeUtils.millis();

                        switch (platformMethod) {
                            case 1:
                                if (platform.hasPlatformFinishedMovingHorizontally()) {
                                    startPlatformMechanics = true;
                                }
                                break;
                            case 2:
                                if (platform.hasPlatformFinishedMovingHorizontally()) {
                                    startPlatformMechanics = true;
                                }
                                break;
                            case 3:
                                if (platform.hasPlatformFinishedMovingVertically()) {
                                    startPlatformMechanics = true;
                                }
                                break;
                            case 4:
                                if (platform.hasPlatformFinishedShrinking()) {
                                    startPlatformMechanics = true;
                                }
                                break;
                            case 5:
                                if (platform.hasPlatformFinishedShrinking()) {
                                    startPlatformMechanics = true;
                                }
                                break;
                            case 6:
                            case 7:
                                if (platform.hasPlatformFinishedMovingHorizontally()) {
                                    startPlatformMechanics = true;
                                }
                                break;
                        }
                    }
                }

            }

            if (TimeUtils.timeSinceMillis(scoreTime) > 10000) {
                SCORE += 20;
                scoreTime = TimeUtils.millis();
            }
        }

        if(!pause) {
            if (MAX_SPEED <= 5) {
                ADD_NEW_ENEMY_RATE = 100;
            } else if (MAX_SPEED > 5 && MAX_SPEED <= 10) {
                ADD_NEW_ENEMY_RATE = 50;
            } else if (MAX_SPEED > 11 && MAX_SPEED <= 14) {
                ADD_NEW_ENEMY_RATE = 36;
            } else if (MAX_SPEED > 15 && MAX_SPEED <= 20) {
                ADD_NEW_ENEMY_RATE = 25;
            } else if (MAX_SPEED > 20 && MAX_SPEED <= 25) {
                ADD_NEW_ENEMY_RATE = 5;
            }
            int MIN_SPEED = 1;

            if (TimeUtils.timeSinceMillis(startTime) > 30000) {
                MAX_SPEED += 2;
                startTime += 30000;
                counterToAddNewEnemy = 0;
            }

            if (counterToAddNewEnemy == ADD_NEW_ENEMY_RATE) {
                createNewEnemy(MIN_SPEED);
            }

            if (counterToNewFruit == 240) {
                createNewFruits(MIN_SPEED);
            }

            if (counterToNewPellet == 1200) {
                createNewPellet(MIN_SPEED);
            }

            if (TimeUtils.timeSinceMillis(galaxianTime) > 60000) {//60000
                createNewGalaxian(MIN_SPEED);
            }

            playerGone();
            enemyCollisions();
            fruitCollisions();
            pelletCollided();
            galaxianCollisions();
            bulletsCull();
            playerInvincibility();
            fireBullets();

            for (Bullet bullet : bullets) {
                bullet.update();
            }

            boolean moveDown = platform.update(422 * GAME_SCALE_Y, 180 * GAME_SCALE_Y);

            if (moveDown)
                if (soundOn) slideDown.play();

            paddle.update(player.getPosition(), platform.getPosition());
            player.update(paddle.getPosition().y - (43 * GAME_SCALE_X));

            playerMustFall();
        }

        camera.update();

        if(pause && (!GAME_MODE || (GAME_MODE && !levelManager.Success()))){
            ACHIEVED_TIME = timeText;
            if((soundOn) && !gameOver.isPlaying()){
                if(!GAME_MODE)ScreenManager.setScreen(new BlankScreen(2));
                if(GAME_MODE) ScreenManager.setScreen(new BlankScreen(4, playLevel));
            } else if(!soundOn){
                if(gameEndLoop == 0) {
                    gameOverDelay = TimeUtils.millis();
                    gameEndLoop = 1;
                }
                if(TimeUtils.timeSinceMillis(gameOverDelay) > 2000){
                    if(!GAME_MODE) ScreenManager.setScreen(new BlankScreen(2));
                    if(GAME_MODE) ScreenManager.setScreen(new BlankScreen(4, playLevel));
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        Gdx.gl.glClearColor( 0, 0, 0, 0 );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        spriteBatch.begin();
        background.updateAndRender(0.05f, spriteBatch);
        foreground.updateAndRender(0.1f, spriteBatch);

        bmpFont.draw(spriteBatch, scoreText + Integer.toString(SCORE), 10 * GAME_SCALE_X, 10 * GAME_SCALE_X);
        bmpFont.draw(spriteBatch, enemiesText + Integer.toString(ENEMIES_KILLED), 230 * GAME_SCALE_X, 10 * GAME_SCALE_X);
        bmpFont.draw(spriteBatch, timeText, 150 * GAME_SCALE_X, 10 * GAME_SCALE_X);

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

        if(GAME_MODE){
            if(levelManager.Success()){
                if(!text_complete.isFlipY())text_complete.flip(false,true);
                spriteBatch.draw(text_complete,0, (HEIGHT/2) * GAME_SCALE_X,
                        text_complete.getWidth()*GAME_SCALE_X,
                        text_complete.getHeight()*GAME_SCALE_X);
            }
        }

        impactRed.draw(spriteBatch, bulletText, 160 * GAME_SCALE_X, 250 * GAME_SCALE_X);
        impactSilver.draw(spriteBatch, shieldText, 160 * GAME_SCALE_X, 200 * GAME_SCALE_X);
        bmpFont.draw(spriteBatch, fruitText + Integer.toString(FRUIT_SCORE), 10 * GAME_SCALE_X, 440 * GAME_SCALE_Y);

        for (Item icon : icons) {
            icon.render(spriteBatch);
        }
        bmpFont.draw(spriteBatch, cherryText + Integer.toString(CHERRY_SCORE), 30 * GAME_SCALE_X, 460 * GAME_SCALE_Y);
        bmpFont.draw(spriteBatch, berryText + Integer.toString(STRAWBERRY_SCORE), 80 * GAME_SCALE_X, 460 * GAME_SCALE_Y);
        bmpFont.draw(spriteBatch, orangeText + Integer.toString(ORANGE_SCORE), 130 * GAME_SCALE_X, 460 * GAME_SCALE_Y);
        bmpFont.draw(spriteBatch, galaxianText + Integer.toString(GALAXIAN_SCORE), 180 * GAME_SCALE_X, 460 * GAME_SCALE_Y);

        if(GAME_MODE){
            bmpFont.draw(spriteBatch, levelText, 250 * GAME_SCALE_X, 440 * GAME_SCALE_Y);
        }
        platform.render(spriteBatch);
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

        if(musicOn){
            backgroundMusic.dispose();
        }

        if(soundOn) {
            bulletSound.dispose();
            explode.dispose();
            playerEats.dispose();
            gameOver.dispose();
            slideDown.dispose();
            slideUp.dispose();
        }
    }

    @Override
    public void pause() {
        for (Enemy enemy : enemies) {
            enemy.setPause(true);
        }

        for (Item fruit : fruits) {
            fruit.setPause(true);
        }

        for (AnimatedItem pellet : pellets) {
            pellet.setPause(true);
        }

        for (Bullet bullet : bullets) {
            bullet.setPause(true);
        }

        player.setPause(true);
        if(galaxian !=null)galaxian.setPause(true);
        paddle.setPause(true);
        platform.setPause(true);
        pause = true;
    }

    @Override
    public void resume() {
        for (Enemy enemy : enemies) {
            enemy.setPause(false);
        }

        for (Item fruit : fruits) {
            fruit.setPause(false);
        }

        for (AnimatedItem pellet : pellets) {
            pellet.setPause(false);
        }

        for (Bullet bullet : bullets) {
            bullet.setPause(false);
        }

        bulletText = "";
        shieldText = "";

        player.setPause(false);
        if(galaxian !=null)galaxian.setPause(false);
        paddle.setPause(false);
        platform.setPause(false);
        pause = false;
    }

    private boolean playerMustFall(){
        boolean fall = false;

        if((platform.getPosition().x + platform.getWidth()) <= paddle.getPosition().x &&
                (paddle.getPosition().x > 0 && paddle.getPosition().x < WIDTH * GAME_SCALE_X )){
            fall = true;
            paddle.fall(10 * GAME_SCALE_Y);
        } else if(platform.getPosition().x > (paddle.getPosition().x + paddle.getWidth()) &&
                (paddle.getPosition().x > 0 && paddle.getPosition().x < WIDTH * GAME_SCALE_X )){
            fall = true;
            paddle.fall(10 * GAME_SCALE_Y);
        }

        return fall;
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
        if(!pause) {
            //if (TimeUtils.timeSinceMillis(screenActive) > delayTime)
                if (playerCanFireBullets) {
                    if (TimeUtils.timeSinceMillis(fireStartTime) < FIRE_TIME_LIMIT) {
                        bullets.add(new Bullet(new Texture(Gdx.files.internal("Images/bullet.png")),
                                new Vector2(player.getCircle().x, player.getPosition().y), new Vector2(0f, -3f)));
                        if (soundOn) bulletSound.play();
                    }
                }

            if (playerPointer == -1) {
                if (screenX >= paddle.getPosition().x && screenX <= paddle.getPosition().x + paddle.getScale()
                        && screenY >= paddle.getPosition().y && screenY <= paddle.getPosition().y + paddle.getScale()) {
                    playerTouched = true;
                    playerPointer = pointer;
                    offsetX = (int) (screenX - paddle.getPosition().x);
                }
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(pointer == playerPointer){
            playerTouched = false;
            playerPointer = -1;
            offsetX = 0;
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        long delayTime = 1000;
        if(TimeUtils.timeSinceMillis(screenActive) > delayTime)
            if(!pause) {
                if (playerTouched && playerPointer == pointer) player.movePlayer(screenX - offsetX);
            }
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

    private void createNewFruits(int MIN_SPEED){
        counterToNewFruit = 0;
        int randomX = random.nextInt((WIDTH - 60) + 20) + 20;
        int no = random.nextInt(fruitTextures.length);
        float randomSpeed = (float) (MIN_SPEED + random.nextInt(6));
        for (Enemy enemy : enemies) {
            if (randomX >= enemy.getPosition().x && randomX <= enemy.getPosition().x + enemy.getScaledSize()
                    && randomSpeed == enemy.getVelocityY()) {
                //change randomSpeed
                do {
                    randomSpeed = (float) (MIN_SPEED + random.nextInt(6));
                }
                while (randomSpeed != enemy.getVelocityY());

                break;
            }
        }
        fruits.add(new Item(fruitTextures[no],
                new Vector2(randomX * GAME_SCALE_X, 0),
                new Vector2(0, randomSpeed), 25, fruitType[no]));
    }

    private void createNewEnemy(int MIN_SPEED){
        counterToAddNewEnemy = 0;
        int randomX = random.nextInt((WIDTH - 60) + 20) + 20;

        int MIN_SIZE = 25;
        int MAX_SIZE = 60;
        int FRAME_SIZE = 5;

        float randomSize = (float) (random.nextInt((MAX_SIZE - MIN_SIZE) + 1) + MIN_SIZE);
        float randomSpeed = (float) (MIN_SPEED + random.nextInt(MAX_SPEED));

        int no = random.nextInt(enemyTextures.length);
        enemies.add(new Enemy(enemyTextures[no],
                new Vector2(randomX * GAME_SCALE_X, 0),
                new Vector2(0, randomSpeed), FRAME_SIZE, randomSize));
    }

    private void createNewPellet(int MIN_SPEED){
        counterToNewPellet = 0;
        int randomX = random.nextInt((WIDTH - 60) + 20) + 20;
        float randomSpeed = (float) (MIN_SPEED + random.nextInt(6));

        pellets.add(new AnimatedItem(new Texture(Gdx.files.internal("Images/pellet_anim.png")),
                new Vector2(randomX * GAME_SCALE_X, 0),
                new Vector2(0, randomSpeed), 5, 28));
    }

    private void createNewGalaxian(int MIN_SPEED){
        int randomX = random.nextInt((WIDTH - 60) + 20) + 20;
        float randomSpeed = (float) (MIN_SPEED + random.nextInt(6));

        galaxian = new Item(new Texture(Gdx.files.internal("Images/galaxian.png")),
                new Vector2(randomX * GAME_SCALE_X, 0), new Vector2(0, randomSpeed), 30, ItemType.GALAXIAN);
        galaxianTime = TimeUtils.millis();
    }

    private void pelletCollided(){
        if (!pellets.isEmpty()) {
            for (int i = pellets.size() - 1; i >= 0; i--) {

                if (player.hasCollided(pellets.get(i).getCircle())) {
                    if (soundOn) playerEats.play();
                    playerCanFireBullets = true;
                    fireStartTime = TimeUtils.millis();
                    player.changePlayerTexture("Images/bomber_pacman.png");
                    pellets.remove(i);
                } else if (pellets.get(i).getPosition().y > HEIGHT * GAME_SCALE_Y) {
                    pellets.remove(i);
                }

                if (i < pellets.size()) pellets.get(i).update();
            }
        }
    }

    private void enemyCollisions(){
        if (!enemies.isEmpty()) {
            for (int i = enemies.size() - 1; i >= 0; i--) {
                enemies.get(i).update();

                if (!playerInvincible && player.hasCollided(enemies.get(i).getCircle())) {
                    if (enemies.get(i).getKillMode()) {
                        if (soundOn) gameOver.play();
                        if (musicOn) backgroundMusic.stop();
                        pause();
                    }
                }

                if (enemies.get(i).isDestroy()) {
                    enemies.remove(i);
                } else {
                    for (int b = bullets.size() - 1; b >= 0; b--) {
                        if (enemies.get(i).bulletHitCollision(bullets.get(b).getCircle())) {
                            if (soundOn) explode.play();
                            enemies.get(i).setEffect();
                            ENEMIES_KILLED += 1;
                            SCORE += 2000;
                            bullets.remove(b);
                        }
                    }
                }
            }
        }
    }

    private void fruitCollisions(){
        if (!fruits.isEmpty()) {
            for (int i = fruits.size() - 1; i >= 0; i--) {
                fruits.get(i).update();

                if (player.hasCollided(fruits.get(i).getCircle())) {
                    if (soundOn) playerEats.play();
                    player.setSparkleEffect();
                    FRUIT_SCORE += 1;
                    switch (fruits.get(i).getItemType()) {
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
                } else if (fruits.get(i).getPosition().y > HEIGHT * GAME_SCALE_Y) {
                    fruits.remove(i);
                }
            }
        }
    }

    private void galaxianCollisions(){
        if (galaxian != null) {
            galaxian.update();
            if (player.hasCollided(galaxian.getCircle())) {
                if (soundOn) playerEats.play();
                galaxian.setPosition(new Vector2(galaxian.getPosition().x * GAME_SCALE_X, 500 * GAME_SCALE_Y));
                SCORE += 3000;
                GALAXIAN_SCORE++;
                playerInvincible = true;
                invincibleStartTime = TimeUtils.millis();
            }
            if (galaxian.getPosition().y > HEIGHT * GAME_SCALE_X) {
                galaxian.dispose();
            }
        }
    }

    private void playerInvincibility(){
        if (playerInvincible) {
            long INVINCIBILITY_TIME = 20000;
            float shieldTest = INVINCIBILITY_TIME - TimeUtils.timeSinceMillis(invincibleStartTime);

            if (shieldTest < 3000) {
                float shieldTimer = shieldTest + Gdx.graphics.getDeltaTime();
                int shieldSeconds = (int) (shieldTimer / 1000) % 60;
                shieldText = Integer.toString(shieldSeconds + 1);
            }
            if (TimeUtils.timeSinceMillis(invincibleStartTime) > INVINCIBILITY_TIME) {
                playerInvincible = false;
                player.hideShield();
                shieldText = "";
            } else if (TimeUtils.timeSinceMillis(invincibleStartTime) < INVINCIBILITY_TIME) {
                player.showShield();
            }
        }
    }

    private void fireBullets(){
        if (playerCanFireBullets) {
            float timerTest = FIRE_TIME_LIMIT - TimeUtils.timeSinceMillis(fireStartTime);
            if (timerTest < 3000) {
                float bulletTimer = timerTest + Gdx.graphics.getDeltaTime();
                int bulletSeconds = (int) (bulletTimer / 1000) % 60;
                bulletText = Integer.toString(bulletSeconds + 1);
            }
            if (TimeUtils.timeSinceMillis(fireStartTime) > FIRE_TIME_LIMIT) {
                playerCanFireBullets = false;
                player.changePlayerTexture("Images/pacman.png");
                bulletText = "";
            }
        }

    }

    private void bulletsCull(){
        if (!bullets.isEmpty()) {
            for (int i = bullets.size() - 1; i >= 0; i--) {
                if (bullets.get(i).isFinished()) {
                    bullets.remove(i);
                }
            }
        }
    }

    private void playerGone(){
        if (player.getPosition().y > HEIGHT * GAME_SCALE_Y) {
            if (soundOn) gameOver.play();
            if (musicOn) backgroundMusic.stop();
            pause();
        }
    }
}


