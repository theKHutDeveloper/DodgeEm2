package com.knowledgehut.developments.dodgeem2.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.knowledgehut.developments.dodgeem2.Camera.OrthoCamera;
import com.knowledgehut.developments.dodgeem2.DodgeEm2;
import com.knowledgehut.developments.dodgeem2.SaveData;

import java.io.IOException;
import java.util.ArrayList;

import static com.knowledgehut.developments.dodgeem2.DodgeEm2.*;


class FinScreen extends Screen {
    private OrthoCamera camera;
    private Stage stage;
    private Skin skin;
    private boolean showDialog = false;
    private ScoreDialog topScore;
    private SaveData saveData;

    FinScreen(){
        camera = new OrthoCamera(DodgeEm2.WIDTH, DodgeEm2.HEIGHT);
        stage = new Stage(new FitViewport(WIDTH, HEIGHT, camera));

        skin = new Skin(Gdx.files.internal("Skins/skin/uiskin.json"));
        topScore = new ScoreDialog("New Highscore!!!", skin);

        Gdx.input.setInputProcessor(stage);

        try {
            saveData = new SaveData();
            ArrayList<SaveData.HighScores> highScores = saveData.returnSortedJson(Gdx.files.internal("Data/save.txt"));

            showDialog = highScores.isEmpty() && SCORE > 0 || SCORE > 0 &&
                    (SCORE > highScores.get(highScores.size() - 1).getScore() || highScores.size() < 10);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create() {

        Image img = new Image(new Texture(Gdx.files.internal("Images/bkgnd_small.png")));
        stage.addActor(img);

        Label label;
        Label.LabelStyle fontStyle;
        BitmapFont bmpFont = new BitmapFont(Gdx.files.internal("Fonts/impact_gold_small.fnt"), false);
        BitmapFont bmpFont2 = new BitmapFont(Gdx.files.internal("Fonts/impact_silver_small.fnt"), false);

        fontStyle = new Label.LabelStyle();
        fontStyle.font = bmpFont;

        String scoreText1 = "You scored:  " + SCORE + "  points ";
        label = new Label(scoreText1, fontStyle);
        label.setPosition(WIDTH / 2 - label.getWidth() / 2, 410);
        stage.addActor(label);

        String scoreText2 = "Fruits collected:  " + FRUIT_SCORE;
        fontStyle.font = bmpFont2;
        label = new Label(scoreText2, fontStyle);
        label.setPosition(90, 380);
        stage.addActor(label);

        String scoreText4 = "Enemies destroyed:  " + ENEMIES_KILLED;
        label = new Label(scoreText4, fontStyle);
        label.setPosition(90, 350);
        stage.addActor(label);


        if (showDialog) {
            topScore.setWidth(254);
            topScore.setHeight(122);
            topScore.setKeepWithinStage(false);
            topScore.setPosition(33, -200);
            topScore.addAction(Actions.moveTo(33, 179, 2f));
            stage.addActor(topScore);
        }
        else
            {
                String message2 = "Touch screen to replay";
                label = new Label(message2, fontStyle);
                label.setPosition(80, 150);
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
    }


    @Override
    public void update() {
        camera.update();
        stage.act();
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
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

    public class ScoreDialog extends Dialog{
        TextField textField;
        ScoreDialog(String title, Skin skin) {
            super(title, skin);
        }

        {
            textField = new TextField("ABC", skin);
            textField.setPosition(-5,40);
            this.getButtonTable().addActor(textField);

        }

        {
            button("Save", "saved");
            button("Cancel", "cancelled");
        }
        @Override
        protected void result(Object object) {
            if(object == "saved") {
                try {
                    saveData.writeJsonToFile(Gdx.files.local("Data/save.txt"), textField.getText(),
                            SCORE);
                    stage.addAction(Actions.sequence(
                            Actions.fadeOut(1f),
                            Actions.run(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            ScreenManager.setScreen(new HighScoreScreen());
                                        }
                                    })));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(object == "cancelled") {
                stage.addAction(Actions.sequence(
                        Actions.fadeOut(1f),
                        Actions.run(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        ScreenManager.setScreen(new MenuScreen());
                                    }
                                })));
            }
        }
    }
}
