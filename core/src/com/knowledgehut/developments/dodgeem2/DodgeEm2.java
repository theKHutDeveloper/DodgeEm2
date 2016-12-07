package com.knowledgehut.developments.dodgeem2;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.knowledgehut.developments.dodgeem2.Camera.OrthoCamera;
import com.knowledgehut.developments.dodgeem2.Screens.BlankScreen;
import com.knowledgehut.developments.dodgeem2.Screens.ScreenManager;

public class DodgeEm2 extends ApplicationAdapter {
	public static final int WIDTH = 320, HEIGHT = 480;
    public static int SCORE, FRUIT_SCORE;

    public static int ENEMIES_KILLED = 0;
    public enum ItemType {CHERRY, STRAWBERRY, ORANGE, GALAXIAN}
    public static int CHERRY_SCORE, STRAWBERRY_SCORE, ORANGE_SCORE, GALAXIAN_SCORE, PELLET_SCORE = 0;
    public static boolean GAME_MODE = false;

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private OrthoCamera camera;

    public static Preferences prefs;

	
	@Override
	public void create () {
        prefs = Gdx.app.getPreferences("DodgeEm Preferences");
        if(prefs.contains("musicOn")){
            prefs.getBoolean("musicOn", true);
        } else{
            prefs.putBoolean("musicOn", true);
        }

        if(prefs.contains("level")){
            prefs.getInteger("level", 1);
        } else {
            prefs.putInteger("level", 1);
        }

        if(prefs.contains("soundOn")) {
            prefs.getBoolean("soundOn", true);
        } else {
            prefs.putBoolean("soundOn", true);
        }
        prefs.flush();

		batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        camera = new OrthoCamera(DodgeEm2.WIDTH, DodgeEm2.HEIGHT);
        ScreenManager.setScreen(new BlankScreen(1));

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        if(ScreenManager.getCurrentScreen() != null){
            ScreenManager.getCurrentScreen().update();
        }

        if(ScreenManager.getCurrentScreen() != null){
            ScreenManager.getCurrentScreen().render(batch, shapeRenderer);
        }
	}
	
	@Override
	public void dispose () {
        if(ScreenManager.getCurrentScreen() != null){
            ScreenManager.getCurrentScreen().dispose();
        }
        shapeRenderer.dispose();
		batch.dispose();
	}

    @Override
    public void resize(int width, int height){
        camera.setToOrtho(true, width, height);

        if(ScreenManager.getCurrentScreen() != null){
            ScreenManager.getCurrentScreen().resize(width, height);
        }
    }

    @Override
    public void pause(){
        if(ScreenManager.getCurrentScreen() != null){
            ScreenManager.getCurrentScreen().pause();
        }
    }


    @Override
    public void resume(){
        if(ScreenManager.getCurrentScreen() != null){
            ScreenManager.getCurrentScreen().resume();
        }
    }
}
