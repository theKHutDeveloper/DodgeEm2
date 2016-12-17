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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.knowledgehut.developments.dodgeem2.Camera.OrthoCamera;
import com.knowledgehut.developments.dodgeem2.DodgeEm2;
import com.knowledgehut.developments.dodgeem2.SaveData;

import java.io.IOException;
import java.util.ArrayList;

class LevelDescription extends Screen{

    private Stage stage;
    private OrthoCamera camera;
    private int chosenLevel;


    LevelDescription(int chosenLevel){
        this.chosenLevel = chosenLevel;
    }

    @Override
    public void create() {
        camera = new OrthoCamera(DodgeEm2.WIDTH, DodgeEm2.HEIGHT);
        stage = new Stage(new FitViewport(DodgeEm2.WIDTH, DodgeEm2.HEIGHT, camera));

        Gdx.input.setInputProcessor(stage);

        Texture texture = new Texture(Gdx.files.internal("Images/bkgnd_small.png"));
        Image background = new Image(texture);

        texture = new Texture(Gdx.files.internal("Images/quest description.png"));
        Image dialog = new Image(texture);
        dialog.setPosition(30 , 230 );

        //==========================Play Button=============================================
        TextureRegion textureRegion[][] = TextureRegion.split(
                new Texture(Gdx.files.internal("Images/playbuttons.png")), 159, 51);

        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(textureRegion[0][0]);
        TextureRegionDrawable textureRegionDrawable1 = new TextureRegionDrawable(textureRegion[0][1]);

        ImageButton playButton = new ImageButton(textureRegionDrawable,textureRegionDrawable1);
        playButton.setPosition(165 , 210 );
        playButton.setSize(playButton.getWidth() , playButton.getHeight() );
        playButton.setTransform(true);
        playButton.setScale(0.7f);
        playButton.addListener(new ClickListener(){
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                stage.addAction(Actions.sequence(
                        Actions.fadeOut(1f),
                        Actions.run(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        DodgeEm2.GAME_MODE = true;
                                        ScreenManager.setScreen(new GameScreen(chosenLevel));

                                    }
                                })));
            }
        });

        //==========================Back Button=============================================
        textureRegion = TextureRegion.split(
                new Texture(Gdx.files.internal("Images/backbuttons.png")), 159, 51);

        textureRegionDrawable = new TextureRegionDrawable(textureRegion[0][0]);
        textureRegionDrawable1 = new TextureRegionDrawable(textureRegion[0][1]);

        ImageButton backButton = new ImageButton(textureRegionDrawable,textureRegionDrawable1);
        backButton.setPosition(50 , 210 );

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

        BitmapFont bmpFont = new BitmapFont(Gdx.files.internal("Fonts/impact_silver_small.fnt"), false);

        Label label;
        Label.LabelStyle fontStyle;
        fontStyle = new Label.LabelStyle();
        fontStyle.font = bmpFont;


        String message = "Level " + chosenLevel;
        label = new Label(message, fontStyle);
        label.setPosition(150 , 385 );

        stage.addActor(background);
        stage.addActor(dialog);
        stage.addActor(playButton);
        stage.addActor(backButton);
        stage.addActor(label);

        try {
            int x = 90;
            int y = 340;

            ArrayList<Label>info = new ArrayList<Label>();
            SaveData saveData = new SaveData();
            SaveData.GameText gameText = saveData.getGameData(Gdx.files.internal("Data/gamedata.txt"), chosenLevel);
            for(int i = 0; i < gameText.getGoals().size(); i++){
                info.add(new Label(gameText.getGoals().get(i), fontStyle));
                info.get(i).setPosition(x , y );
                stage.addActor(info.get(i));
                y = y - 20;

                Image marker_on = new Image(new Texture(Gdx.files.internal("Images/marker_on.png")));
                marker_on.setSize(marker_on.getWidth() , marker_on.getHeight() );
                marker_on.setPosition((x - 30) , (y + 18) );
                stage.addActor(marker_on);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            int x1 = 60;
            int x = 105;
            int y1 = 140;
            int y = 152;

            ArrayList<Label> scores = new ArrayList<Label>();

            SaveData saveData = new SaveData();
            ArrayList<SaveData.ArcadeScores> arcadeScores =
                    saveData.findArcadeScoresFromFile(Gdx.files.local("Data/arcade_save.txt"), chosenLevel);

            if(!arcadeScores.isEmpty()){
                for(int i = 0; i < arcadeScores.size(); i++){
                    texture = new Texture(Gdx.files.internal("Images/score_holder.png"));
                    Image temp = new Image(texture);
                    temp.setPosition(x1 , y1 );
                    stage.addActor(temp);

                    scores.add(new Label(arcadeScores.get(i).getName() + " - " + arcadeScores.get(i).getScore(), fontStyle));
                    scores.get(i).setPosition(x, y);
                    stage.addActor(scores.get(i));
                    y = y - 40;
                    y1 = y1 - 40;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
