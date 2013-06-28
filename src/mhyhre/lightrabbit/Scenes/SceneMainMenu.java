/*
 * Copyright (C) 2013 Andrew Tulay
 * @mail: mhyhre@gmail.com
 * 
 * This work is licensed under a Creative Commons 
 * Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * You may obtain a copy of the License at
 *
 *		http://creativecommons.org/licenses/by-nc-nd/3.0/legalcode
 *
 */

package mhyhre.lightrabbit.Scenes;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

public class SceneMainMenu extends MhyhreScene {

	public Background mBackGround;

	public Text mCaptionItem1; // New Game
	public Text mCaptionItem2; // About
	public Text mCaptionItem3; // Exit

	private Sprite mSpriteItem1;
	private Sprite mSpriteItem2;
	private Sprite mSpriteItem3;

	

	
	
	
	
	
	
	
	
	
	
	
	
	public SceneMainMenu() {

		mBackGround = new Background(0.2f, 0.6f, 0.7f);
		setBackground(mBackGround);
		setBackgroundEnabled(true);

		// Text captions
		String TextItem1 = "Play";
		String TextItem3 = "About";
		String TextItem4 = "Exit";

		// Creating text
		mCaptionItem1 = new Text(0, 0, MainActivity.Res.getFont("Pixel"), TextItem1,MainActivity.Me.getVertexBufferObjectManager());
		mCaptionItem2 = new Text(0, 0, MainActivity.Res.getFont("Pixel"), TextItem3,MainActivity.Me.getVertexBufferObjectManager());
		mCaptionItem3 = new Text(0, 0, MainActivity.Res.getFont("Pixel"), TextItem4,MainActivity.Me.getVertexBufferObjectManager());

		// Creating sprites
		mSpriteItem1 = new Sprite(0, 0, MainActivity.Res.getTextureRegion("Button1"), MainActivity.Me.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
		
					Log.i(MainActivity.DebugID, "Main Menu [ New game ] button");
					MainActivity.mVibrator.vibrate(30);	
					
					
					SceneRoot.SetState(SceneStates.NewGame);
				}
				return true;
			}
		};
		
		mSpriteItem2 = new Sprite(0, 0, MainActivity.Res.getTextureRegion("Button1"), MainActivity.Me.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
		
					Log.i(MainActivity.DebugID, "Main Menu [ Options ] button");
					MainActivity.mVibrator.vibrate(30);
					SceneRoot.SetState(SceneStates.About);
				}
				return true;
			}
		};
			
		mSpriteItem3 = new Sprite(0, 0, MainActivity.Res.getTextureRegion("Button1"), MainActivity.Me.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
		
					Log.i(MainActivity.DebugID, "Main Menu [ Exit ] button");
					MainActivity.mVibrator.vibrate(30);
					SceneRoot.SetState(SceneStates.Exit);
				}
				return true;
			}
		};
		
		
		// Calculating positions
		float OffsetY = MainActivity.getHeight()/5.0f;
		float OffsetX = (MainActivity.getHalfWidth()) - (mSpriteItem1.getWidth()/2.0f);
		
		mSpriteItem1.setPosition(OffsetX, OffsetY*2);
		mSpriteItem2.setPosition(OffsetX, OffsetY*3);
		mSpriteItem3.setPosition(OffsetX, OffsetY*4);
		
		float tx  = mSpriteItem1.getX()+mSpriteItem1.getWidth()/2;
		float ty1 = mSpriteItem1.getY()+mSpriteItem1.getHeight()/2;
		float ty2 = mSpriteItem2.getY()+mSpriteItem2.getHeight()/2;
		float ty3 = mSpriteItem3.getY()+mSpriteItem3.getHeight()/2;
		
		mCaptionItem1.setPosition(tx-mCaptionItem1.getWidth()/2, ty1-mCaptionItem1.getHeight()/2);
		mCaptionItem2.setPosition(tx-mCaptionItem2.getWidth()/2, ty2-mCaptionItem2.getHeight()/2);
		mCaptionItem3.setPosition(tx-mCaptionItem3.getWidth()/2, ty3-mCaptionItem3.getHeight()/2);

		
		// Attaching
		
		attachChild(mSpriteItem1);
		attachChild(mSpriteItem2);
		attachChild(mSpriteItem3);

		attachChild(mCaptionItem1);
		attachChild(mCaptionItem2);
		attachChild(mCaptionItem3);
		
		// Register Touch Listeners
		registerTouchArea(mSpriteItem1);
		registerTouchArea(mSpriteItem2);
		registerTouchArea(mSpriteItem3);
		

	}

	@Override
	public boolean onSceneTouchEvent(final TouchEvent pSceneTouchEvent) {

		return super.onSceneTouchEvent(pSceneTouchEvent);
	}

}
