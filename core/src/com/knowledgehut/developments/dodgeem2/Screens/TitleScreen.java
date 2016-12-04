package com.knowledgehut.developments.dodgeem2.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.knowledgehut.developments.dodgeem2.Camera.OrthoCamera;
import com.knowledgehut.developments.dodgeem2.DodgeEm2;

import static com.knowledgehut.developments.dodgeem2.DodgeEm2.HEIGHT;
import static com.knowledgehut.developments.dodgeem2.DodgeEm2.WIDTH;

public class TitleScreen extends Screen{

    private OrthoCamera camera;
    private Stage stage;

    TitleScreen(){

        camera = new OrthoCamera(DodgeEm2.WIDTH, DodgeEm2.HEIGHT);

        stage = new Stage(new FitViewport(WIDTH,HEIGHT, camera));
    }

    @Override
    public void create() {
        Gdx.input.setInputProcessor(stage);

        Image background = new Image(new Texture(Gdx.files.internal("Images/bkgnd_small.png")));

        Image title = new Image(new Texture(Gdx.files.internal("Images/title.png")));
        title.setPosition(40, 260);

        Label label;
        Label.LabelStyle fontStyle;
        BitmapFont bmpFont = new BitmapFont(Gdx.files.internal("Fonts/impact_gold_small.fnt"), false);

        fontStyle = new Label.LabelStyle();
        fontStyle.font = bmpFont;

        String message = "Touch screen to begin";
        label = new Label(message, fontStyle);
        label.setPosition(50, 160);

        stage.addActor(background);
        stage.addActor(title);
        stage.addActor(label);

        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stage.addAction(Actions.sequence(
                        Actions.fadeOut(1f),
                        Actions.run(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        ScreenManager.setScreen(new MenuScreen());
                                    }
                                })));
                return true;
            }
        });
    }

    @Override
    public  void update(){
        camera.update();
        stage.act();
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        Gdx.gl.glClearColor( 0, 0, 0, 0 );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.resize(DodgeEm2.WIDTH, DodgeEm2.HEIGHT);
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
