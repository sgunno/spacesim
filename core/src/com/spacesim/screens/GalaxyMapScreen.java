package com.spacesim.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.spacesim.Planet;
import com.spacesim.SpaceSim;

public class GalaxyMapScreen implements Screen, InputProcessor {

	SpaceSim game;
	ShapeRenderer shapeRndr;
	Planet chosenPlanet;
	
	private SpriteBatch batch;
    private BitmapFont font;
	
	public GalaxyMapScreen(SpaceSim game) {
		this.game = game;
		this.shapeRndr = new ShapeRenderer();
		Gdx.input.setInputProcessor(this);
		
		batch = new SpriteBatch();    
        font = new BitmapFont();
        font.setColor(Color.RED);
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        shapeRndr.begin(ShapeType.Filled);
        shapeRndr.setColor(1, 1, 1, 1);
        for(Planet p : this.game.galaxy.planets) {
        	if(chosenPlanet != null && p.name.equals(chosenPlanet.name) && p.position.equals(chosenPlanet.position)) {
        		shapeRndr.setColor(1, 0, 0, 1);
        		Vector2 newp = new Vector2(p.position).scl(600);
            	shapeRndr.point(newp.x, newp.y, 0);
            	shapeRndr.setColor(1, 1, 1, 1);
        	} else {
        		Vector2 newp = new Vector2(p.position).scl(600);
        		shapeRndr.point(newp.x, newp.y, 0);
        	}
        }
        shapeRndr.end();

        if(chosenPlanet == null) {
        	batch.begin();
        	font.draw(batch, "Please select a planet to travel to.", 600, 600, 150, 10, true);
        	batch.end();
        } else {
        	int costToTravel = (int)Math.floor(chosenPlanet.position.dst(this.game.ship.position) * 10.0f);
        	batch.begin();
        	font.draw(batch, String.format("Click or press enter to travel to the planet.\nPlanet Name: %s\nTravel Costs.\nFood: %d\nWater: %d", chosenPlanet.name, costToTravel, costToTravel), 600, 600, 150, 10, true);
        	batch.end();
        }
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		batch.dispose();
        font.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.ENTER:
			// TODO travel to planet
			break;
		}
		return true;
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
		System.out.println("X: " + screenX);
		System.out.println("Y: " + screenY);
		screenY = 600 - screenY;
		for(Planet p : this.game.galaxy.planets) {
        	Vector2 newp = new Vector2(p.position).scl(600);
        	if((screenX >= (newp.x - 5) && screenX <= (newp.x + 5)) && (screenY >= (newp.y - 5) && screenY <= (newp.y + 5))) {
        		System.out.println("Name: " + p.name);
        		System.out.println("X: " + newp.x + ", Y: " + newp.y);
        		chosenPlanet = p;
        	}
        }
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}