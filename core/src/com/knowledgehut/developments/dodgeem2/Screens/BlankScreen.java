package com.knowledgehut.developments.dodgeem2.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.knowledgehut.developments.dodgeem2.Camera.OrthoCamera;
import com.knowledgehut.developments.dodgeem2.DodgeEm2;

public class BlankScreen extends Screen{
    private OrthoCamera camera;
    private Stage stage;
    private int priorScreen;

    public BlankScreen(int cameFrom){
        priorScreen = cameFrom;
    }
    @Override
    public void create() {
        camera = new OrthoCamera(DodgeEm2.WIDTH, DodgeEm2.HEIGHT);
        stage = new Stage(new FitViewport(DodgeEm2.WIDTH, DodgeEm2.HEIGHT));

        Gdx.input.setInputProcessor(stage);

        if(priorScreen == 1) {
            stage.addAction(Actions.run(
                    new Runnable() {
                        @Override
                        public void run() {
                            ScreenManager.setScreen(new TitleScreen());
                        }
                    }));
        } else if(priorScreen == 2){
            stage.addAction(Actions.run(
                    new Runnable() {
                        @Override
                        public void run() {
                            ScreenManager.setScreen(new FinScreen());
                        }
                    }));
        } else if(priorScreen == 3){
            stage.addAction(Actions.run(
                    new Runnable() {
                        @Override
                        public void run() {
                            ScreenManager.setScreen(new HighScoreScreen());
                        }
                    }));
        } else if(priorScreen == 4){
            stage.addAction(Actions.run(
                    new Runnable() {
                        @Override
                        public void run() {
                            ScreenManager.setScreen(new ArcadeScreen());
                        }
                    }));
        }
    }

    @Override
    public void update() {
        stage.act();
        camera.update();
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
