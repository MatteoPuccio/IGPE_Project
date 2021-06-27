package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/*
 * Default screen implementa l'interfaccia screen e fa da superclasse agli altri Screen 
 */

public abstract class DefaultScreen implements Screen {

	protected SpriteBatch batch;
	protected Stage stage;
	protected Viewport viewport;
	protected TextureAtlas atlas;
	protected Skin skin;
	protected BitmapFont titleFont;
	protected LabelStyle titleStyle;
	protected Table mainTable;
	protected BitmapFont generalFont;
	
	private float r;
	private float g;
	private float b;
		
	public DefaultScreen(float r, float g, float b) {
		//la skin usata è una delle default di libgdx con alcune modifiche come il font 
		atlas = new TextureAtlas("skin/skin.atlas");
	    skin = new Skin(Gdx.files.internal("skin/skin.json"), atlas);
	    skin.getFont("boldFont").getData().setScale(2f,2f);
		
	    titleFont = new BitmapFont(Gdx.files.internal("skin/AncientModernTales.fnt"));
	    titleFont.getData().scale(0.7f);
	    
	    generalFont = new BitmapFont(Gdx.files.internal("UI/a_goblin_appears.fnt"), Gdx.files.internal("UI/a_goblin_appears.png"), false);
	    generalFont.getData().setScale(0.35f, 0.35f);
	    titleStyle = new LabelStyle(titleFont, Color.BLACK);
	    
		batch = new SpriteBatch();
		
        viewport = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        viewport.apply();
        
        //colore di sfondo dello screen
        this.r = r;
        this.g = g;
        this.b = b;
	}
	
	//chiamato quando questo schermo diventa lo schermo corrente di GameMain
	public void show() {
		stage = new Stage(viewport,batch);
		mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();
        mainTable.setSize(stage.getWidth(), stage.getHeight());
        
        initMainTable();
        
        stage.addActor(mainTable);
		Gdx.input.setInputProcessor(stage);
	}
	
	//aggiunge i vari attori a mainTable
	protected abstract void initMainTable();

	@Override
	public final void render(float delta) {
		Gdx.gl.glClearColor(r, g, b, 1f);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    	
    	draw(delta);

        stage.act();
        stage.draw();	
	}
	
	protected void draw(float delta) {}
	
	@Override
	//chiamato quando viene cambiato schermo
	public void hide() {
		mainTable.clear();
		stage.clear();
		stage.dispose();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		viewport.apply();
	}
	
	@Override
	public void dispose() {
		skin.dispose();
		atlas.dispose();
		batch.dispose();
		if(stage != null) {
			stage.clear();
			stage.dispose();
		}
		titleFont.dispose();
		generalFont.dispose();
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}
}
