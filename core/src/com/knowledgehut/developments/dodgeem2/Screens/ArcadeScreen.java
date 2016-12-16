package com.knowledgehut.developments.dodgeem2.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.knowledgehut.developments.dodgeem2.Camera.OrthoCamera;
import com.knowledgehut.developments.dodgeem2.DodgeEm2;
import com.knowledgehut.developments.dodgeem2.SaveData;

import java.io.IOException;
import java.util.ArrayList;

import static com.knowledgehut.developments.dodgeem2.DodgeEm2.HEIGHT;
import static com.knowledgehut.developments.dodgeem2.DodgeEm2.SCORE;
import static com.knowledgehut.developments.dodgeem2.DodgeEm2.WIDTH;


public class ArcadeScreen extends Screen {
    private OrthoCamera camera;
    private Stage stage;
    private int chosenLevel, newLevel;
    private boolean success;
    private Skin skin;
    private ScoreDialog topScore;
    private SaveData saveData;
    private boolean showDialog = false;

    ArcadeScreen(int chosenLevel, boolean won){
        this.chosenLevel = chosenLevel;
        this.success = won;

        camera = new OrthoCamera(DodgeEm2.WIDTH, DodgeEm2.HEIGHT);
        stage = new Stage(new FitViewport(WIDTH, HEIGHT, camera));
        skin = new Skin(Gdx.files.internal("Skins/skin/uiskin.json"));
        topScore = new ScoreDialog("New Highscore!!!", skin);

        Gdx.input.setInputProcessor(stage);

        try {
            saveData = new SaveData();
            ArrayList<SaveData.ArcadeScores> arcadeScores =
                    saveData.returnSortedArcadeJson(Gdx.files.local("Data/arcade_save.txt"), chosenLevel);

            showDialog = arcadeScores.isEmpty() && SCORE > 0 || SCORE > 0 &&
                    (SCORE > arcadeScores.get(arcadeScores.size() - 1).getScore() || arcadeScores.size() < 3);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create() {

        Image img = new Image(new Texture(Gdx.files.internal("Images/bkgnd_small.png")));
        stage.addActor(img);

        Texture texture = new Texture(Gdx.files.internal("Images/dialog.png"));
        Image dialog = new Image(texture);
        dialog.scaleBy(0f, -0.4f);
        dialog.setPosition(20 , 180 );
        stage.addActor(dialog);

        Label label;
        Label.LabelStyle fontStyle;
        BitmapFont bmpFont = new BitmapFont(Gdx.files.internal("Fonts/impact_silver_small.fnt"), false);
        fontStyle = new Label.LabelStyle();
        fontStyle.font = bmpFont;

        String text;
        String status;
        String score = "You scored "+ SCORE;

        TextureRegion textureRegion[][] = TextureRegion.split(
                new Texture(Gdx.files.internal("Images/menubutton.png")), 159, 51);

        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(textureRegion[0][0]);
        TextureRegionDrawable textureRegionDrawable1 = new TextureRegionDrawable(textureRegion[0][1]);

        ImageButton menuButton = new ImageButton(textureRegionDrawable,textureRegionDrawable1);
        menuButton.setPosition(210 , 170 );
        menuButton.setSize(menuButton.getWidth() , menuButton.getHeight() );
        menuButton.setTransform(true);
        menuButton.setScale(0.5f);
        menuButton.addListener(new ClickListener(){
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
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
        });

        textureRegion = TextureRegion.split(
                new Texture(Gdx.files.internal("Images/levelbutton.png")), 159, 51);

        textureRegionDrawable = new TextureRegionDrawable(textureRegion[0][0]);
        textureRegionDrawable1 = new TextureRegionDrawable(textureRegion[0][1]);

        ImageButton levelButton = new ImageButton(textureRegionDrawable,textureRegionDrawable1);
        levelButton.setPosition(120 , 170 );

        levelButton.setSize(levelButton.getWidth() , levelButton.getHeight() );
        levelButton.setTransform(true);
        levelButton.setScale(0.5f);
        levelButton.addListener(new ClickListener(){
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                stage.addAction(Actions.sequence(
                        Actions.fadeOut(1f),
                        Actions.run(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        if(!success) action(chosenLevel);
                                        if(success && newLevel != -1){
                                            action(newLevel);
                                        }
                                        if(success && newLevel == -1){
                                            action(chosenLevel);
                                        }
                                    }
                                })));
            }
        });

        textureRegion = TextureRegion.split(
                new Texture(Gdx.files.internal("Images/nextbutton.png")), 159, 51);

        textureRegionDrawable = new TextureRegionDrawable(textureRegion[0][0]);
        textureRegionDrawable1 = new TextureRegionDrawable(textureRegion[0][1]);

        ImageButton nextButton = new ImageButton(textureRegionDrawable,textureRegionDrawable1);
        nextButton.setPosition(30 , 170 );

        nextButton.setSize(nextButton.getWidth() , nextButton.getHeight() );
        nextButton.setTransform(true);
        nextButton.setScale(0.5f);
        nextButton.addListener(new ClickListener(){
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                stage.addAction(Actions.sequence(
                        Actions.fadeOut(1f),
                        Actions.run(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        ScreenManager.setScreen(new LevelDescription(newLevel));

                                    }
                                })));
            }
        });

        textureRegion = TextureRegion.split(
                new Texture(Gdx.files.internal("Images/replay.png")), 159, 51);

        textureRegionDrawable = new TextureRegionDrawable(textureRegion[0][0]);
        textureRegionDrawable1 = new TextureRegionDrawable(textureRegion[0][1]);

        ImageButton replayButton = new ImageButton(textureRegionDrawable,textureRegionDrawable1);
        replayButton.setPosition(30 , 170 );

        replayButton.setSize(replayButton.getWidth() , replayButton.getHeight() );
        replayButton.setTransform(true);
        replayButton.setScale(0.5f);
        replayButton.addListener(new ClickListener(){
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                stage.addAction(Actions.sequence(
                        Actions.fadeOut(1f),
                        Actions.run(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        ScreenManager.setScreen(new GameScreen(chosenLevel));
                                    }
                                })));
            }
        });

        if(success){
            status = "Good going!";
            text = "You completed this mission";

            if(DodgeEm2.prefs.getInteger("level") <= chosenLevel && chosenLevel < 60) {
                newLevel = chosenLevel + 1;
                DodgeEm2.prefs.putInteger("level", newLevel);
                DodgeEm2.prefs.flush();
                stage.addActor(nextButton);
            } else if(chosenLevel == 60){
                stage.addActor(replayButton);
                newLevel = -1;
            } else {
                stage.addActor(nextButton);
                newLevel = chosenLevel + 1;
            }


        } else {
            status = "Bad luck!";
            text = "You did not complete the mission ";
            stage.addActor(replayButton);
        }

        label = new Label(status, fontStyle);
        label.setPosition(WIDTH / 2 - label.getWidth() / 2, 300);
        stage.addActor(label);
        label = new Label(text, fontStyle);
        label.setPosition(WIDTH / 2 - label.getWidth() / 2, 280);
        stage.addActor(label);
        label = new Label(score, fontStyle);
        label.setPosition(WIDTH / 2 - label.getWidth() / 2, 240);
        stage.addActor(label);

        stage.addActor(menuButton);
        stage.addActor(levelButton);

        if (success & showDialog) {
            topScore.setWidth(254);
            topScore.setHeight(122);
            topScore.setKeepWithinStage(false);
            topScore.setPosition(33, -200);
            topScore.addAction(Actions.moveTo(33, 179, 2f));
            stage.addActor(topScore);
            System.out.println(SCORE);
        }
    }

    @Override
    public void update() {
        stage.act();
        camera.update();
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.resize(DodgeEm2.WIDTH, DodgeEm2.HEIGHT);
    }

    private void action(int level){

        if(level >= 1 && level <= 12){
            ScreenManager.setScreen(new LevelScreen(2));
        } else if (level >= 13 && level <= 24) {
            ScreenManager.setScreen(new LevelScreen2(2));
        } else if (level >= 25 && level <= 36) {
            ScreenManager.setScreen(new LevelScreen3(2));
        } else if (level >= 37 && level <= 48) {
            ScreenManager.setScreen(new LevelScreen4(2));
        } else if (level >= 49 && level <= 60) {
            ScreenManager.setScreen(new LevelScreen5(2));
        }
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


    public class ScoreDialog extends Dialog {
        TextField textField;

        ScoreDialog(String title, Skin skin) {
            super(title, skin);
        }

        {
            textField = new TextField("", skin);
            textField.setPosition(-5, 40);
            this.getButtonTable().addActor(textField);

        }

        {
            button("Save", "saved");
            button("Cancel", "cancelled");
        }

        @Override
        protected void result(Object object) {
            if (object == "saved") {
                try {
                    saveData.writeArcadeScoreToFile(Gdx.files.local("Data/arcade_save.txt"),
                            chosenLevel, textField.getText(), SCORE);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
