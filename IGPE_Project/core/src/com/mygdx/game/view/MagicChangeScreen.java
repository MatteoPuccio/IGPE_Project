package com.mygdx.game.view;

import javax.sql.rowset.FilteredRowSet;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameMain;
import com.mygdx.game.model.GameModel;

public class MagicChangeScreen extends DefaultScreen {
	
	private Label changeMagicPromptLabel;
	private Label yourMagicsLabel;
	
	private Image newMagic;
	private ImageButton firstMagic;
	private ImageButton secondMagic;
	
	private int newMagicId;
	private int firstMagicId;
	private int secondMagicId;
	
	private TextButton keepMagic;
	private TextButton test;
	private GameView gameView;
	private Viewport gamePort;
	
	public MagicChangeScreen(GameView gameView) {
		super(0.259f, 0.157f, 0.208f);
		this.gameView = gameView;
		
		changeMagicPromptLabel = new Label("Would you like to change one of your magics to this one?", skin);
		yourMagicsLabel = new Label("Your active magics", skin);
		
		keepMagic = new TextButton("I WOULD LIKE TO KEEP MY MAGICS", skin);
		test = new TextButton("Test",skin);
		gamePort = gameView.getGamePort();
	}

	@Override
	protected void initMainTable() {
		
		newMagic = new Image(gameView.getAnimationFrame(newMagicId));
		
		firstMagic = new ImageButton(new SpriteDrawable(new Sprite(gameView.getAnimationFrame(firstMagicId))));
		secondMagic = new ImageButton(new SpriteDrawable(new Sprite(gameView.getAnimationFrame(secondMagicId))));
		
		newMagic.setScaling(Scaling.fit);
		
		mainTable.add(changeMagicPromptLabel).colspan(2).padTop(100);
		mainTable.row();
		mainTable.invalidateHierarchy();
		
		mainTable.add(newMagic).colspan(2);
		mainTable.row();
		
		mainTable.add(yourMagicsLabel).colspan(2);
		mainTable.row();
		
		firstMagic.addListener(new ClickListener() {
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				
				GameModel.getInstance().getCharacter().swapFirstMagic();
				GameMain.getInstance().start();
				return true;
			}
		});
		mainTable.add(firstMagic).colspan(1).padLeft(300);
		
		secondMagic.addListener(new ClickListener() {
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				GameModel.getInstance().getCharacter().swapSecondMagic();
				GameMain.getInstance().start();
				return true;
			}
		});
		mainTable.add(secondMagic).colspan(1).padRight(300);
		
		mainTable.row();
		
		keepMagic.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				GameMain.getInstance().start();
				return true;
			}
		});
		mainTable.add(keepMagic).colspan(2);
		
		mainTable.setTransform(true);
		mainTable.setOrigin(mainTable.getPrefWidth() / 1.5f, mainTable.getPrefHeight() / 1.5f);
		mainTable.setScale(1.5f);
	}
	
	@Override
	protected void draw(float delta) {
		gameView.render(delta, false);
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
		gamePort.update(width, height);
		gamePort.apply();
	}
	
	public void setNewMagicId(int newMagicId) {
		this.newMagicId = newMagicId;
	}
	
	public void setFirstMagicId(int firstMagicId) {
		this.firstMagicId = firstMagicId;
	}
	public void setSecondMagicId(int secondMagicId) {
		this.secondMagicId = secondMagicId;
	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

}
