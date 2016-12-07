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

import static com.knowledgehut.developments.dodgeem2.DodgeEm2.HEIGHT;
import static com.knowledgehut.developments.dodgeem2.DodgeEm2.SCORE;
import static com.knowledgehut.developments.dodgeem2.DodgeEm2.WIDTH;


public class ArcadeScreen extends Screen {
    private OrthoCamera camera;
    private Stage stage;
    private int chosenLevel;
    private boolean success;

    ArcadeScreen(int chosenLevel, boolean won){
        this.chosenLevel = chosenLevel;
        this.success = won;
    }

    @Override
    public void create() {

        Skin skin = new Skin(Gdx.files.internal("Skins/skin/uiskin.json"));

        /*final SuccessDialog successDialog = new SuccessDialog("Mission Complete", skin);
        final FailedDialog failedDialog = new FailedDialog("Mission Failed", skin);
*/
        camera = new OrthoCamera(DodgeEm2.WIDTH, DodgeEm2.HEIGHT);
        stage = new Stage(new FitViewport(WIDTH, HEIGHT, camera));
        Gdx.input.setInputProcessor(stage);

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
                                        if(chosenLevel >= 1 && chosenLevel <= 12){
                                            ScreenManager.setScreen(new LevelScreen(2));
                                        } else if(chosenLevel >= 13 && chosenLevel <= 24){
                                            ScreenManager.setScreen(new LevelScreen2(2));
                                        } else if(chosenLevel >= 25 && chosenLevel <= 36){
                                            ScreenManager.setScreen(new LevelScreen3(2));
                                        } else if(chosenLevel >= 37 && chosenLevel <= 48){
                                            ScreenManager.setScreen(new LevelScreen4(2));
                                        } else if(chosenLevel >= 49 && chosenLevel <= 60){
                                            ScreenManager.setScreen(new LevelScreen5(2));
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
                                        ScreenManager.setScreen(new LevelDescription(chosenLevel));

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
            stage.addActor(nextButton);
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

    private void action(){

        if(chosenLevel >= 1 && chosenLevel <= 12){
            ScreenManager.setScreen(new LevelScreen(2));
        } else if (chosenLevel >= 13 && chosenLevel <= 24) {
            ScreenManager.setScreen(new LevelScreen2(2));
        } else if (chosenLevel >= 25 && chosenLevel <= 36) {
            ScreenManager.setScreen(new LevelScreen3(2));
        } else if (chosenLevel >= 37 && chosenLevel <= 48) {
            ScreenManager.setScreen(new LevelScreen4(2));
        } else if (chosenLevel >= 49 && chosenLevel <= 60) {
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

    public class SuccessDialog extends Dialog {

        SuccessDialog(String title, Skin skin) {
            super(title, skin);
        }

        @Override
        protected void result(Object object) {
            if(object == "next"){
                ScreenManager.setScreen(new LevelDescription(chosenLevel));
            }
            else if(object == "level") {
                action();
            }
            else if (object == "menu") {
                ScreenManager.setScreen(new MenuScreen());
            }
        }

        {
            text("Good going! You completed this mission");
            button("Next Level", "next");
            button("Levels", "level");
            button("Menu", "menu");
        }
    }

    public class FailedDialog extends Dialog {
        FailedDialog(String title, Skin skin) {
            super(title, skin);
        }

        @Override
        protected void result(Object object) {
            if(object == "replay"){
                ScreenManager.setScreen(new GameScreen(chosenLevel));
            }
            else if(object == "level") {
                action();
            }
            else if (object == "menu") {
                ScreenManager.setScreen(new MenuScreen());
            }
        }

        {
            text("Bad luck! You did not complete the mission");
            button("Replay", "replay");
            button("Levels", "level");
            button("Menu", "menu");
        }
    }
}
