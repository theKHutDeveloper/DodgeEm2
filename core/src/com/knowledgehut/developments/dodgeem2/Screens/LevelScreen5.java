package com.knowledgehut.developments.dodgeem2.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.knowledgehut.developments.dodgeem2.Camera.OrthoCamera;
import com.knowledgehut.developments.dodgeem2.DodgeEm2;
import com.knowledgehut.developments.dodgeem2.Entity.Button;

import java.util.ArrayList;

import static com.knowledgehut.developments.dodgeem2.DodgeEm2.HEIGHT;
import static com.knowledgehut.developments.dodgeem2.DodgeEm2.WIDTH;

class LevelScreen5 extends Screen{

    private OrthoCamera camera;
    private ArrayList<Button> buttons = new ArrayList<Button>();
    private Stage stage;



    LevelScreen5(int direction){
        camera = new OrthoCamera(DodgeEm2.WIDTH, DodgeEm2.HEIGHT);
        stage = new Stage(new FitViewport(WIDTH, HEIGHT, camera));
        if(direction == 1) {
            stage.addAction(Actions.moveTo(-stage.getWidth(), 0));
        }

        if(direction == 2){
            stage.addAction(Actions.moveTo(-stage.getWidth(), 0));
        }
        stage.addAction(Actions.visible(false));
        stage.act();
    }

    @Override
    public void create() {

        int accessLevel = DodgeEm2.prefs.getInteger("level");

        Gdx.input.setInputProcessor(stage);

        Texture texture = new Texture(Gdx.files.internal("Images/bkgnd_small.png"));
        Image background = new Image(texture);

        for(int i = 49; i < 61; i++){
            if (accessLevel < i ) {
                TextureRegion textureRegion[][] = TextureRegion.split(
                        new Texture(Gdx.files.internal("Images/locked.png")), 50, 51);
                TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(textureRegion[0][0]);
                buttons.add(new Button(new ImageButton(textureRegionDrawable),true));
            } else {
                TextureRegion textureRegion[][] = TextureRegion.split(
                        new Texture(Gdx.files.internal("Images/button_normal_"+i + ".png")), 50, 51);

                TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(textureRegion[0][0]);
                TextureRegionDrawable textureRegionDrawable1 = new TextureRegionDrawable(textureRegion[0][1]);

                buttons.add(new Button(new ImageButton(textureRegionDrawable,textureRegionDrawable1), false));
            }
        }

        for (Button button : buttons) {
            button.getImage().setSize(button.getImage().getWidth() , button.getImage().getHeight() );
        }

        int x = 37;
        int y = 282;

        for(int i = 0; i < 12; i++){
            buttons.get(i).getImage().setPosition(x, y);
            x += 65;
            if(i == 3 || i == 7){
                x = 37;
                y -= 60;
            }
        }
        Image dialog = new Image(new Texture(Gdx.files.internal("Images/dialog.png")));
        dialog.setSize(dialog.getWidth() , dialog.getHeight() );
        dialog.setPosition(20 , 120 );

        Image text = new Image(new Texture(Gdx.files.internal("Images/select level.png")));
        text.setSize(text.getWidth() , text.getHeight() );
        text.setPosition(20 , 400 );

        TextureRegion textureRegion[][] = TextureRegion.split(
                new Texture(Gdx.files.internal("Images/previous.png")), 31, 31);
        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(textureRegion[0][0]);
        TextureRegionDrawable textureRegionDrawable1 = new TextureRegionDrawable(textureRegion[0][1]);
        ImageButton previous = new ImageButton(textureRegionDrawable, textureRegionDrawable1);
        previous.setSize(previous.getWidth() , previous.getHeight() );
        previous.setPosition(70 , 110 );
        previous.addListener(new ClickListener(){
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                stage.addAction(Actions.sequence(Actions.moveTo(-stage.getWidth(), 0, .5f),
                        Actions.run(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        ScreenManager.setScreen(new LevelScreen4(2));
                                    }
                                }
                        )));
            }
        });

        Image marker_on = new Image(new Texture(Gdx.files.internal("Images/marker_on.png")));
        marker_on.setSize(marker_on.getWidth() , marker_on.getHeight() );
        marker_on.setPosition(194 , 112 );

        Image marker_off4 = new Image(new Texture(Gdx.files.internal("Images/marker_off.png")));
        marker_off4.setSize(marker_off4.getWidth() , marker_off4.getHeight() );
        marker_off4.setPosition(125 , 112 );

        Image marker_off = new Image(new Texture(Gdx.files.internal("Images/marker_off.png")));
        marker_off.setSize(marker_off.getWidth() , marker_off.getHeight() );
        marker_off.setPosition(148 , 112 );

        Image marker_off2 = new Image(new Texture(Gdx.files.internal("Images/marker_off.png")));
        marker_off2.setSize(marker_off2.getWidth() , marker_off2.getHeight() );
        marker_off2.setPosition(171 , 112 );

        Image marker_off3 = new Image(new Texture(Gdx.files.internal("Images/marker_off.png")));
        marker_off3.setSize(marker_off3.getWidth() , marker_off3.getHeight() );
        marker_off3.setPosition(102 , 112 );

        stage.addActor(background);
        stage.addActor(dialog);
        stage.addActor(previous);
        stage.addActor(marker_off);
        stage.addActor(marker_off4);
        stage.addActor(marker_off3);
        stage.addActor(marker_off2);
        stage.addActor(marker_on);
        stage.addActor(text);

        for (Button button : buttons) {
            stage.addActor(button.getImage());
        }

        for (int i = 0; i < buttons.size(); i++) {
            if (!buttons.get(i).isLocked()) {
                Button button = buttons.get(i);
                final int finalI = i + 49;
                button.getImage().addListener(new ChangeListener() {

                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        stage.addAction(Actions.sequence(
                                Actions.fadeOut(1f),
                                Actions.run(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                ScreenManager.setScreen(new LevelDescription(finalI));
                                            }
                                        })));
                    }
                });
            }
        }

        stage.addAction(Actions.sequence(
                Actions.visible(true),
                Actions.moveTo(0,0, 1f)));

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
