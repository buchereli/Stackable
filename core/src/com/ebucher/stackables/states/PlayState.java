package com.ebucher.stackables.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ebucher.stackables.global.G;
import com.ebucher.stackables.next.NextBlocks;
import com.ebucher.stackables.score.ScoreBoard;
import com.ebucher.stackables.stacks.BlockTextures;
import com.ebucher.stackables.stacks.StackManager;

/**
 * Created by buche on 6/8/2017.
 */

public class PlayState extends State implements InputProcessor {
    private Texture bg;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false);
        G.init((int) cam.viewportWidth, (int) cam.viewportHeight);

        bg = new Texture("bg.png");

        NextBlocks.set();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();

        StackManager.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();

        // Draw background
        sb.draw(bg, cam.position.x - cam.viewportWidth / 2, cam.position.y - cam.viewportHeight / 2);

        // Draw next blocks
        NextBlocks.render(sb, (int) cam.position.x);

        // Draw block stacks
        StackManager.render(sb);

        // Draw score board
        ScoreBoard.render(sb);

        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        BlockTextures.dispose();
        ScoreBoard.dispose();
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
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 touchEvent = new Vector2(screenX, cam.viewportHeight - screenY);
        if (touchEvent.y < NextBlocks.HEIGHT) {
            NextBlocks.swap();
        } else {
            StackManager.placeBlocks(touchEvent);
            NextBlocks.set();
        }

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
