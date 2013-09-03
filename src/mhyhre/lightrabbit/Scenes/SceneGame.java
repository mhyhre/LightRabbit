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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mhyhre.lightrabbit.GameState;
import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;

import org.andengine.entity.primitive.Polygon;
import org.andengine.entity.primitive.Vector2;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import android.util.Log;

public class SceneGame extends MhyhreScene {

	private Background mBackground;

	private static GameState mode = GameState.Ready;
	private boolean loaded = false;
	
	private Polygon waterPolygon;
	private List<Vector2> waterCoordinates;
	float[] vertexX1;
	float[] vertexY1;
	
	private final int waterResolution = 17;

	Sprite spriteNext;

	// Resources
	public static final String uiAtlasName = "User_Interface";

	public static ArrayList<ITextureRegion> TextureRegions;

	public SceneGame() {

		mBackground = new Background(0.40f, 0.88f, 0.99f);
		setBackground(mBackground);
		setBackgroundEnabled(true);

		CreateTextureRegions();
		
		
		// Water coordinates list
		waterCoordinates = new ArrayList<Vector2>();
		waterCoordinates.add(new Vector2(MainActivity.getWidth(), MainActivity.getHeight()));
		waterCoordinates.add(new Vector2(0, MainActivity.getHeight()));

		Random rand = new Random();
		
		for(int i=0; i < waterResolution; i++){
			
			waterCoordinates.add(new Vector2(i*10, MainActivity.getHalfHeight() - rand.nextInt(100) ));
		}

		// Separate coordinates
		vertexX1 = new float[waterCoordinates.size()];
		vertexY1 = new float[waterCoordinates.size()];

		for (int i = 0; i < waterCoordinates.size(); i++) {
			vertexX1[i] = waterCoordinates.get(i).x;
			vertexY1[i] = waterCoordinates.get(i).y;
		}

		waterPolygon = new Polygon(0, 0, vertexX1, vertexY1,
				MainActivity.Me.getVertexBufferObjectManager());
		waterPolygon.setColor(0.50f, 0.75f, 1);
		this.attachChild(waterPolygon);
		

		spriteNext = new Sprite(MainActivity.getWidth()
				- (10 + TextureRegions.get(3).getWidth()), 10,
				TextureRegions.get(3),
				MainActivity.Me.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {

					MainActivity.vibrate(30);

					switch (mode) {

					case Ready:
						setGameState(GameState.Memorize);
						break;

					case Memorize:
						setGameState(GameState.Recollect);
						break;

					case Recollect:
						setGameState(GameState.Result);
						break;

					case Result:
						setGameState(GameState.Ready);
						break;

					case Loss:
						SceneRoot.SetState(SceneStates.MainMenu);
						break;
					}
				}
				return true;
			}
		};
		spriteNext.setVisible(true);
		attachChild(spriteNext);

		loaded = true;

		setGameState(GameState.Ready);
		
		Log.i(MainActivity.DebugID, "Scene game created");
	}

	private static void CreateTextureRegions() {

		TextureRegions = new ArrayList<ITextureRegion>();
		BitmapTextureAtlas atlas = MainActivity.Res
				.getTextureAtlas(uiAtlasName);
		TextureRegions.add(TextureRegionFactory.extractFromTexture(atlas, 0, 0,
				310, 70, false));
		TextureRegions.add(TextureRegionFactory.extractFromTexture(atlas, 325,
				0, 45, 70, false));

		TextureRegions.add(TextureRegionFactory.extractFromTexture(atlas, 0,
				70, 74, 74, false));
		TextureRegions.add(TextureRegionFactory.extractFromTexture(atlas, 80,
				70, 74, 74, false));

		TextureRegions.add(TextureRegionFactory.extractFromTexture(atlas, 160,
				70, 74, 74, false));

		TextureRegions.add(TextureRegionFactory.extractFromTexture(atlas, 390,
				0, 120, 384, false));
		TextureRegions.add(TextureRegionFactory.extractFromTexture(atlas, 460,
				0, 4, 384, false));
	}

	@Override
	public boolean onSceneTouchEvent(final TouchEvent pSceneTouchEvent) {

		if (loaded == true) {

			switch (mode) {

			case Ready:

				break;

			case Memorize:

				break;

			default:
				break;
			}
		}

		return super.onSceneTouchEvent(pSceneTouchEvent);
	}
	
	private static float tick = 0;

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {

		tick+=0.02f;
		
		if(tick > Math.PI*2.0f)
			tick = 0.0f;

		
		float step = MainActivity.getWidth() / (waterResolution-1);
		
		for (int i = 2; i < waterCoordinates.size(); i++) {
			
			float waveAngle = (float) ((i*2/Math.PI) + tick);
			
			float vertexHeight = (float) (MainActivity.getHalfHeight() + 30*Math.sin( waveAngle ));
			
			vertexX1[i] = (i-2) * step;
			vertexY1[i] = vertexHeight;
		}
		
		waterPolygon.updateVertices(vertexX1, vertexY1);

		//waterPolygon.updateVertices(vertexX1, vertexY1);
		
		
		/*
		for(int i=0; i < waterCoordinates.size(); i++){

			
			waterCoordinates.get(i).set(i*increment, (float) vertexHeight);
			
			//Log.i(MainActivity.DebugID, "Updated " + i);
		}

		for (int i = 0; i < vertexX1.length-1; i+=2) {

			//float vertexHeight = (float) (MainActivity.getHalfHeight() + 100*Math.sin( (i+tick) / (Math.PI*20) ));

			vertexX1[i] = i*10.0f;//waterCoordinates.get(i).x;
			vertexY1[i] = i*10.0f;//vertexHeight;//waterCoordinates.get(i).y;
			
			//vertexX1[i+1] = 0.0f;//waterCoordinates.get(i).x;
			//vertexY1[i+1] = MainActivity.getWidth();//vertexHeight;//waterCoordinates.get(i).y;
		}
*/


		super.onManagedUpdate(pSecondsElapsed);
	}

	public void setGameState(GameState mode) {

		if (loaded == false) {
			return;
		}

		Log.i(MainActivity.DebugID, "SceneGame::setGameState: " + mode.name());

		SceneGame.mode = mode;

		switch (mode) {

		case Ready:
			break;

		case Memorize:
			break;

		default:
			break;
		}
	}

	public static GameState getMode() {
		return mode;
	}

}
