package com.ebucher.stackables.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.ebucher.stackables.StackablesMain;
import com.ebucher.stackables.global.G;
import com.ebucher.stackables.sprites.Block;
import com.ebucher.stackables.sprites.BlockStack;
import com.ebucher.stackables.sprites.BlockTextures;
import com.ebucher.stackables.sprites.NextBlocks;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by buche on 6/8/2017.
 */

public class PlayState extends State implements InputProcessor {
    private Texture bg;
    private BlockStack leftStack, rightStack;
    private int score;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false);
        G.init((int) cam.viewportWidth, (int) cam.viewportHeight);

        bg = new Texture("bg.png");

        leftStack = new BlockStack(0);
        rightStack = new BlockStack((int) cam.position.x + G.MARGIN);
        NextBlocks.set();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();

        leftStack.update(dt);
        rightStack.update(dt);
        int tempScore = score;
        score += leftStack.scoreStack();
        score += rightStack.scoreStack();
        if(tempScore != score)
            System.out.println(score);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();

        // Draw background
        sb.draw(bg, cam.position.x - cam.viewportWidth / 2, cam.position.y - cam.viewportHeight / 2);

        // Draw next blocks
        NextBlocks.render(sb, (int) cam.position.x);

        // Draw block stacks
        leftStack.render(sb);
        rightStack.render(sb);

        sb.end();
    }

    @Override
    public void dispose() {
        BlockTextures.dispose();
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
            leftStack.placeBlock(touchEvent, NextBlocks.left());
            rightStack.placeBlock(touchEvent, NextBlocks.right());

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
