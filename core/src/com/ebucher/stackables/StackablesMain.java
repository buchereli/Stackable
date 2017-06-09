package com.ebucher.stackables;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ebucher.stackables.states.GameStateManager;
import com.ebucher.stackables.states.PlayState;

public class StackablesMain extends ApplicationAdapter {
    private SpriteBatch batch;
    private GameStateManager gsm;

    @Override
    public void create() {
        gsm = new GameStateManager();
        batch = new SpriteBatch();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        gsm.push(new PlayState(gsm));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}