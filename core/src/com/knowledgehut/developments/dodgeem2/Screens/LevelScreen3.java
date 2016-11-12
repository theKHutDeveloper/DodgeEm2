package com.knowledgehut.developments.dodgeem2.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import com.knowledgehut.developments.dodgeem2.Camera.OrthoCamera;
import com.knowledgehut.developments.dodgeem2.DodgeEm2;
import com.knowledgehut.developments.dodgeem2.Entity.Button;

import java.util.ArrayList;

import static com.badlogic.gdx.Input.Buttons.LEFT;
import static com.knowledgehut.developments.dodgeem2.DodgeEm2.WIDTH;

class LevelScreen3 extends Screen implements InputProcessor{
    private OrthoCamera camera;
    private Sprite dialog;
    private ArrayList<Button> buttons = new ArrayList<Button>();
    private Sprite next, marker_on, marker_off, marker_off2, play, previous;
    private Sprite background;
    private long screenActive;
    private float bkgScale;

    @Override
    public void create() {
        camera = new OrthoCamera(DodgeEm2.WIDTH, DodgeEm2.HEIGHT);
        screenActive = System.currentTimeMillis();

        int accessLevel = DodgeEm2.prefs.getInteger("level");

        float GAME_SCALE_X = (float) (Gdx.graphics.getWidth()) / (float) (WIDTH);

        Gdx.input.setInputProcessor(this);

        Texture texture = new Texture(Gdx.files.internal("Images/title_background.png"));
        bkgScale = texture.getWidth() * GAME_SCALE_X;
        background = new Sprite(texture);

        for(int i = 41; i < 61; i++){
            if (accessLevel < i ) {
                buttons.add(new Button(new Sprite(new Texture(Gdx.files.internal("Images/locked.png"))),true));
            } else {
                buttons.add(new Button(
                        new Sprite(new Texture(Gdx.files.internal("Images/button_normal_"+i + ".png"))), false)
                );
            }
        }

        for (Button button : buttons) {
            button.getSprite().setSize(button.getSprite().getWidth() * GAME_SCALE_X,
                    button.getSprite().getHeight() * GAME_SCALE_X);
            button.getSprite().flip(false, true);
            button.getSprite().scale(-0.15f);
        }

        int x = (int)(25 * GAME_SCALE_X);
        int y = (int)(145 * GAME_SCALE_X);

        for(int i = 0; i < 20; i++){
            buttons.get(i).getSprite().setPosition(x, y);
            x += (int)(55 * GAME_SCALE_X);
            if(i == 4 || i == 9 || i == 14){
                x = (int)(25 * GAME_SCALE_X);
                y += (int)(50 * GAME_SCALE_X);
            }
        }

        dialog = new Sprite(new Texture(Gdx.files.internal("Images/dialogbox.png")));
        dialog.setSize(dialog.getWidth() * GAME_SCALE_X, dialog.getHeight() * GAME_SCALE_X);
        dialog.flip(false, true);
        dialog.setPosition(10 * GAME_SCALE_X, 50 * GAME_SCALE_X);

        play = new Sprite(new Texture(Gdx.files.internal("Images/play_disabled.png")));
        play.setSize(play.getWidth() * GAME_SCALE_X, play.getHeight() * GAME_SCALE_X);
        play.flip(false, true);
        play.setPosition(80 * GAME_SCALE_X,385 * GAME_SCALE_X);

        marker_on = new Sprite(new Texture(Gdx.files.internal("Images/marker_on.png")));
        marker_on.setSize(marker_on.getWidth() * GAME_SCALE_X, marker_on.getHeight() * GAME_SCALE_X);
        marker_on.flip(false, true);
        marker_on.setPosition(177 * GAME_SCALE_X,355 * GAME_SCALE_X);

        marker_off = new Sprite(new Texture(Gdx.files.internal("Images/marker_off.png")));
        marker_off.setSize(marker_off.getWidth() * GAME_SCALE_X, marker_off.getHeight() * GAME_SCALE_X);
        marker_off.flip(false, true);
        marker_off.setPosition(117 * GAME_SCALE_X,355 * GAME_SCALE_X);

        marker_off2 = new Sprite(new Texture(Gdx.files.internal("Images/marker_off.png")));
        marker_off2.setSize(marker_off2.getWidth() * GAME_SCALE_X, marker_off2.getHeight() * GAME_SCALE_X);
        marker_off2.flip(false, true);
        marker_off2.setPosition(147 * GAME_SCALE_X,355 * GAME_SCALE_X);

        next = new Sprite(new Texture(Gdx.files.internal("Images/next.png")));
        next.setSize(next.getWidth() * GAME_SCALE_X, next.getHeight() * GAME_SCALE_X);
        next.flip(false, true);
        next.setPosition(205 * GAME_SCALE_X,348 * GAME_SCALE_X);

        previous = new Sprite(new Texture(Gdx.files.internal("Images/previous.png")));
        previous.setSize(previous.getWidth() * GAME_SCALE_X, previous.getHeight() * GAME_SCALE_X);
        previous.flip(false, true);
        previous.scale(-0.15f);
        previous.setPosition(80 * GAME_SCALE_X, 348 * GAME_SCALE_X);
    }

    @Override
    public void update() {
        camera.update();
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();

        spriteBatch.draw(background, 0, 0, bkgScale, bkgScale);

        dialog.draw(spriteBatch);
        for (Button button : buttons) {
            button.getSprite().draw(spriteBatch);
        }
        play.draw(spriteBatch);
        next.draw(spriteBatch);
        previous.draw(spriteBatch);
        marker_on.draw(spriteBatch);
        marker_off.draw(spriteBatch);
        marker_off2.draw(spriteBatch);

        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.resize(DodgeEm2.WIDTH, DodgeEm2.HEIGHT);
    }

    @Override
    public void dispose() {
        for (Button button : buttons) {
            button.getSprite().getTexture().dispose();
        }
        background.getTexture().dispose();
        dialog.getTexture().dispose();
        next.getTexture().dispose();
        play.getTexture().dispose();
        previous.getTexture().dispose();
        marker_off.getTexture().dispose();
        marker_on.getTexture().dispose();
        marker_off2.getTexture().dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

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
        long delayTime = 1000;
        if(TimeUtils.timeSinceMillis(screenActive) > delayTime) {
            for (Button b : buttons) {
                if(!b.isLocked()) {
                    if (screenX >= b.getSprite().getX() &&
                            screenX <= b.getSprite().getX() + b.getSprite().getWidth() &&
                            screenY >= b.getSprite().getY() &&
                            screenY <= b.getSprite().getY() + b.getSprite().getHeight()) {
                        System.out.println("A button has been pressed");
                        break;
                    }
                }
            }

            if (button == LEFT && screenX >= previous.getX() && screenX <= previous.getX() + previous.getWidth()
                    && screenY >= previous.getY() && screenY <= previous.getY() + previous.getHeight()) {
                System.out.println("A button has been pressed");
                ScreenManager.setScreen(new LevelScreen2());

            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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
}
