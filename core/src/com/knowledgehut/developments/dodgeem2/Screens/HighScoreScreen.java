package com.knowledgehut.developments.dodgeem2.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.knowledgehut.developments.dodgeem2.Camera.OrthoCamera;
import com.knowledgehut.developments.dodgeem2.DodgeEm2;
import com.knowledgehut.developments.dodgeem2.SaveData;


import java.util.ArrayList;

import static com.knowledgehut.developments.dodgeem2.DodgeEm2.HEIGHT;
import static com.knowledgehut.developments.dodgeem2.DodgeEm2.WIDTH;


class HighScoreScreen extends Screen{

    private OrthoCamera camera;
    private Stage stage;

    @Override
    public void create() {
        camera = new OrthoCamera(DodgeEm2.WIDTH, DodgeEm2.HEIGHT);
        stage = new Stage(new FitViewport(WIDTH, HEIGHT, camera));
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("Skins/skin/uiskin.json"));

        final EmptyTrashDialog trashDialog = new EmptyTrashDialog("Empty Highscores", skin);

        Texture texture = new Texture(Gdx.files.internal("Images/bkgnd_small.png"));
        Image background = new Image(texture);

        texture = new Texture(Gdx.files.internal("Images/score_panel.png"));
        Image dialog = new Image(texture);
        dialog.setPosition(15 , 80);

        BitmapFont bmpFont = new BitmapFont(Gdx.files.internal("Fonts/impact_silver_med.fnt"), false);

        Label header;
        Label.LabelStyle fontStyle;
        fontStyle = new Label.LabelStyle();
        fontStyle.font = bmpFont;

        String message = "HighScores";
        header = new Label(message, fontStyle);
        header.setPosition(90, 430);

        BitmapFont font = new BitmapFont(Gdx.files.internal("Fonts/impact_silver_small.fnt"), false);
        Label.LabelStyle style;
        style = new Label.LabelStyle();
        style.font = font;


        TextureRegion textureRegion[][] = TextureRegion.split(
                new Texture(Gdx.files.internal("Images/backbuttons.png")), 159, 51);

        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(textureRegion[0][0]);
        TextureRegionDrawable textureRegionDrawable1 = new TextureRegionDrawable(textureRegion[0][1]);

        ImageButton backButton = new ImageButton(textureRegionDrawable,textureRegionDrawable1);
        backButton.setPosition(50 , 70 );

        backButton.setSize(backButton.getWidth() , backButton.getHeight() );
        backButton.setTransform(true);
        backButton.setScale(0.7f);
        backButton.addListener(new ClickListener(){
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

        textureRegion = TextureRegion.split(new Texture(Gdx.files.internal("Images/trash.png")),
                50, 51);
        textureRegionDrawable = new TextureRegionDrawable(textureRegion[0][0]);
        textureRegionDrawable1 = new TextureRegionDrawable(textureRegion[0][1]);

        ImageButton trash = new ImageButton(textureRegionDrawable, textureRegionDrawable1);
        trash.setPosition(230, 70);
        trash.setSize(trash.getWidth(), trash.getHeight());
        trash.setTransform(true);
        trash.setScale(0.7f);
        trash.addListener(new ClickListener(){
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                //get Dialog
                try {
                    SaveData saveData = new SaveData();
                    ArrayList<SaveData.HighScores> highScores = saveData.returnSortedJson(Gdx.files.internal("Data/save.txt"));
                    //if(highScores != null) {
                        int list = highScores.size();

                        if (list > 0) {
                            trashDialog.show(stage);
                        }
                   // }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        stage.addActor(background);
        stage.addActor(dialog);
        stage.addActor(header);
        stage.addActor(backButton);
        stage.addActor(trash);

        try {
            int name_x = 60;
            int y = 370;
            int score_x = 210;


            ArrayList<Label>scores = new ArrayList<Label>();
            ArrayList<Label> names = new ArrayList<Label>();

            SaveData saveData = new SaveData();
            ArrayList<SaveData.HighScores> highScores = saveData.returnSortedJson(Gdx.files.internal("Data/save.txt"));

                int count = 0;
                for (SaveData.HighScores score : highScores) {
                    names.add(new Label(score.getName(), style));
                    scores.add(new Label(Integer.toString(score.getScore()), style));
                    names.get(count).setPosition(name_x, y);
                    scores.get(count).setPosition(score_x, y);
                    stage.addActor(names.get(count));
                    stage.addActor(scores.get(count));

                    y = y - 20;
                    count++;
                }

        } catch (Exception e) {
            e.printStackTrace();
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
        camera.resize(DodgeEm2.WIDTH,DodgeEm2.HEIGHT);
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



    public class EmptyTrashDialog extends Dialog {

        EmptyTrashDialog(String title, Skin skin) {
            super(title, skin);
        }

        @Override
        protected void result(Object object) {
            if(object == "true"){
                System.out.println("Clear highscore list");
                SaveData saveData = new SaveData();
                saveData.clearHighScores(Gdx.files.local("Data/save.txt"));
                ScreenManager.setScreen(new BlankScreen(3));
            }
        }

        {
            text("Empty your highscore list?");
            button("Yes", "true");
            button("No", "false");
        }
    }
}
