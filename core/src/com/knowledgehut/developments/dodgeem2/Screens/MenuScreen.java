package com.knowledgehut.developments.dodgeem2.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.knowledgehut.developments.dodgeem2.Camera.OrthoCamera;
import com.knowledgehut.developments.dodgeem2.DodgeEm2;

import static com.knowledgehut.developments.dodgeem2.DodgeEm2.GAME_MODE;
import static com.knowledgehut.developments.dodgeem2.DodgeEm2.HEIGHT;
import static com.knowledgehut.developments.dodgeem2.DodgeEm2.WIDTH;

class MenuScreen extends Screen{
    private OrthoCamera camera;
    private Stage stage;


      MenuScreen(){
         camera = new OrthoCamera(WIDTH, HEIGHT);
         stage = new Stage(new FitViewport(WIDTH,HEIGHT, camera));
         stage.addAction(Actions.visible(false));
         stage.act();
         //stage.addAction(Actions.moveTo(stage.getWidth()*2, 0));


    }
    @Override
    public void create() {
        Gdx.input.setInputProcessor(stage);

        Image img = new Image(new Texture(Gdx.files.internal("Images/bkgnd_small.png")));

        TextureRegion textureRegion[][] = TextureRegion.split(
                new Texture(Gdx.files.internal("Images/sound.png")), 40, 41);
        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(textureRegion[0][0]);
        TextureRegionDrawable textureRegionDrawable1 = new TextureRegionDrawable(textureRegion[0][1]);
        ImageButton sound = new ImageButton(textureRegionDrawable,textureRegionDrawable1);
        sound.getStyle().imageChecked = sound.getStyle().imageDown;
        if(!DodgeEm2.prefs.getBoolean("soundOn")){
            sound.setChecked(true);
        }
        sound.addListener(new ClickListener(){
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                boolean soundOn = DodgeEm2.prefs.getBoolean("soundOn");
                soundOn = !soundOn;

                DodgeEm2.prefs.putBoolean("soundOn", soundOn);
                DodgeEm2.prefs.flush();
            }
        });

        textureRegion = TextureRegion.split(
                new Texture(Gdx.files.internal("Images/music.png")), 40, 41);
        textureRegionDrawable = new TextureRegionDrawable(textureRegion[0][0]);
        textureRegionDrawable1 = new TextureRegionDrawable(textureRegion[0][1]);
        ImageButton music = new ImageButton(textureRegionDrawable,textureRegionDrawable1);
        music.getStyle().imageChecked = music.getStyle().imageDown;

        if(!DodgeEm2.prefs.getBoolean("musicOn")){
            music.setChecked(true);
        }
        music.addListener(new ClickListener(){
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                boolean musicOn = DodgeEm2.prefs.getBoolean("musicOn");
                musicOn = !musicOn;

                DodgeEm2.prefs.putBoolean("musicOn", musicOn);
                DodgeEm2.prefs.flush();
            }
        });

        textureRegion = TextureRegion.split(
                new Texture(Gdx.files.internal("Images/highscore.png")), 40, 41);
        textureRegionDrawable = new TextureRegionDrawable(textureRegion[0][0]);
        textureRegionDrawable1 = new TextureRegionDrawable(textureRegion[0][1]);
        ImageButton highscore = new ImageButton(textureRegionDrawable,textureRegionDrawable1);
        highscore.addListener(new ClickListener(){
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                stage.addAction(Actions.sequence(Actions.parallel(Actions.fadeOut(.5f),
                        Actions.moveBy(-stage.getWidth(), 0, .5f)),
                        Actions.run(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        ScreenManager.setScreen(new HighScoreScreen());
                                    }
                                })));
            }
        });


        textureRegion = TextureRegion.split(
                new Texture(Gdx.files.internal("Images/arcade.png")), 200, 35);
        textureRegionDrawable = new TextureRegionDrawable(textureRegion[0][0]);
        textureRegionDrawable1 = new TextureRegionDrawable(textureRegion[0][1]);
        ImageButton arcade = new ImageButton(textureRegionDrawable,textureRegionDrawable1);
        arcade.addListener(new ClickListener(){
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                ScreenManager.setScreen(new LevelScreen(1));
            }
        });

        textureRegion = TextureRegion.split(
                new Texture(Gdx.files.internal("Images/classic.png")), 200, 35);
        textureRegionDrawable = new TextureRegionDrawable(textureRegion[0][0]);
        textureRegionDrawable1 = new TextureRegionDrawable(textureRegion[0][1]);
        ImageButton classic = new ImageButton(textureRegionDrawable,textureRegionDrawable1);
        classic.addListener(new ClickListener(){
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                GAME_MODE = false;
                ScreenManager.setScreen(new GameScreen(0));
            }
        });

        arcade.setPosition(60 , 210);
        classic.setPosition(60, 260);
        sound.setPosition(70, 100);
        music.setPosition(140, 100);
        highscore.setPosition(210, 100);

        Label label;
        Label.LabelStyle fontStyle;
        BitmapFont bmpFont = new BitmapFont(Gdx.files.internal("Fonts/largeFont.fnt"), false);

        fontStyle = new Label.LabelStyle();
        fontStyle.font = bmpFont;
        String message = "Choose GAME MODE";
        label = new Label(message, fontStyle);
        label.setPosition(50, 390);

        stage.addActor(img);
        stage.addActor(arcade);
        stage.addActor(classic);
        stage.addActor(sound);
        stage.addActor(music);
        stage.addActor(highscore);
        stage.addActor(label);

        stage.addAction(Actions.sequence(
                Actions.alpha(0f),
                Actions.visible(true),
                Actions.fadeIn(2f)
                ));
    }

    @Override
    public void update() {
        camera.update();
        stage.act();
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.resize(WIDTH, HEIGHT);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
