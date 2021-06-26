package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameMain;
import com.mygdx.game.constants.SoundConstants;
import com.mygdx.game.view.animations.Animation;
import com.mygdx.game.view.audio.SoundHandler;
import com.mygdx.game.view.audio.Sounds;

public class TitleScreen extends DefaultScreen{

    private Animation titleScreenAnimation;
    private Array<TextureRegion> titleScreenFrames;
    private BitmapFont titleFont;
    private LabelStyle titleStyle;
	
	public TitleScreen() {
	    
		super(0, 0, 0);
		
	    titleFont = new BitmapFont(Gdx.files.internal("skin/AncientModernTales.fnt"));
	    titleFont.getData().scale(0.8f);
	    titleStyle = new LabelStyle(titleFont, Color.WHITE);
	    
	    //crea animazione per il title screen 
	    titleScreenFrames = new Array<TextureRegion>();
	    for(int i = 0; i < 6; ++i) 
	    	titleScreenFrames.add(new TextureRegion(new Texture("title_screen/title-screen" + i + ".png")));
	    titleScreenAnimation = new Animation(titleScreenFrames, 6, 0.5f);
        
	}
	
	@Override
	protected void initMainTable() {
		
        Label title = new Label("No Way To Go But Down",titleStyle);
        title.setColor(new Color(Color.BLACK));
        TextButton playButton = new TextButton("PLAY", skin);
        TextButton exitButton = new TextButton("EXIT", skin);
        TextButton optionButton = new TextButton("OPTIONS", skin);
        
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
        		SoundHandler.getInstance().addSoundToQueue(SoundConstants.MENU_CONFIRM);
            	GameMain.getInstance().start();
            }
        });
        
        optionButton.addListener(new ClickListener(){
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		SoundHandler.getInstance().addSoundToQueue(SoundConstants.MENU_CONFIRM);
        		GameMain.getInstance().options();
        	}
        });
        
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        
        mainTable.add(title).pad(0,0,100,0);
        mainTable.row();
        mainTable.add(playButton).growX().pad(20, 300, 20, 300);
        mainTable.row();
        mainTable.add(optionButton).growX().pad(20, 300, 20, 300);
        mainTable.row();
        mainTable.add(exitButton).growX().pad(20, 300, 20, 300);
        mainTable.row();
	}
	
	@Override
	protected void draw(float delta) {
		 batch.begin();
	        titleScreenAnimation.update(delta);
			batch.draw(titleScreenAnimation.getFrame(), 0,0);
			batch.end();
	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}