package com.knowledgehut.developments.dodgeem2.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.knowledgehut.developments.dodgeem2.Camera.OrthoCamera;
import com.knowledgehut.developments.dodgeem2.DodgeEm2;

import java.util.ArrayList;

import static com.badlogic.gdx.Input.Buttons.LEFT;
import static com.knowledgehut.developments.dodgeem2.DodgeEm2.WIDTH;


class LevelScreen extends Screen implements InputProcessor{

    private OrthoCamera camera;
    private ArrayList<Sprite> buttons = new ArrayList<Sprite>();
    private Sprite next;

    @Override
    public void create() {

        camera = new OrthoCamera(DodgeEm2.WIDTH, DodgeEm2.HEIGHT);
        Gdx.input.setInputProcessor(this);
        float GAME_SCALE_X = (float) (Gdx.graphics.getWidth()) / (float) (WIDTH);

        for(int i = 1; i < 31; i++){
            buttons.add(new Sprite(new Texture(Gdx.files.internal("Images/button_normal_"+i + ".png"))));
        }

        for (Sprite button : buttons) {
            button.setSize(button.getWidth() * GAME_SCALE_X, button.getHeight() * GAME_SCALE_X);
            button.flip(false, true);
            button.scale(-0.15f);
        }

        int x = (int)(45 * GAME_SCALE_X);
        int y = (int)(70 * GAME_SCALE_X);
        for(int i = 0; i < 30; i++){
            buttons.get(i).setPosition(x, y);
            x += (int)(45 * GAME_SCALE_X);
            if(i == 4 || i == 9 || i == 14 || i == 19 || i == 24){
                x = (int)(45 * GAME_SCALE_X);
                y += (int)(55 * GAME_SCALE_X);
            }
        }

        next = new Sprite(new Texture(Gdx.files.internal("Images/right_normal.png")));
        next.setSize(next.getWidth() * GAME_SCALE_X, next.getHeight() * GAME_SCALE_X);
        next.flip(false, true);
        next.scale(-0.15f);
        next.setPosition(250 * GAME_SCALE_X,420 * GAME_SCALE_X);
    }

    @Override
    public void update() {
        camera.update();

    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();

        for (Sprite button : buttons) {
            button.draw(spriteBatch);
        }
        next.draw(spriteBatch);

        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.resize(DodgeEm2.WIDTH, DodgeEm2.HEIGHT);
    }

    @Override
    public void dispose() {
        for (Sprite button : buttons) {
            button.getTexture().dispose();
        }

        next.getTexture().dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.RIGHT) {
            ScreenManager.setScreen(new LevelScreen2());
        }
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
        for (Sprite b : buttons) {
            if (button == LEFT && screenX >= b.getX() && screenX <= b.getX() + b.getWidth()
                    && screenY >= b.getY() && screenY <= b.getY() + b.getHeight()) {
                System.out.println("A button has been pressed");
                break;
            }
        }

        if (button == LEFT && screenX >= next.getX() && screenX <= next.getX() + next.getWidth()
                && screenY >= next.getY() && screenY <= next.getY() + next.getHeight()) {
            System.out.println("A button has been pressed");
            ScreenManager.setScreen(new LevelScreen2());

        }
        return false;
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

//TODO: Two screens with 30 buttons on each screen
//TODO: arrow forward and back on the screens