package com.ebucher.stackables.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.utils.Array;
import com.ebucher.stackables.StackablesMain;
import com.ebucher.stackables.sprites.Block;

import java.util.Random;

/**
 * Created by buche on 6/8/2017.
 */

public class PlayState extends State {
    public static final int MARGIN = 5;

    private Texture bg;
    private Array<Block> left, right;
    private Block nextLeft, nextRight;
    private Random random;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false);

        bg = new Texture("bg.png");

        random = new Random();

        left = new Array<Block>();
        right = new Array<Block>();
        nextLeft = new Block((int) cam.viewportHeight, random.nextInt(4), (int) cam.viewportWidth / 8);
        nextRight = new Block((int) cam.viewportHeight, random.nextInt(4), (int) cam.viewportWidth / 8);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            left.add(nextLeft);
            right.add(nextRight);
            nextLeft = new Block((int) cam.viewportHeight, random.nextInt(4), (int) cam.viewportWidth / 8);
            nextRight = new Block((int) cam.viewportHeight, random.nextInt(4), (int) cam.viewportWidth / 8);
        }
    }

    @Override
    public void update(float dt) {
        handleInput();

        for (int i = 0; i < left.size; i++)
            left.get(i).update(dt, i);

        for (int i = 0; i < right.size; i++)
            right.get(i).update(dt, i);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();

        // Draw background
        sb.draw(bg, cam.position.x - cam.viewportWidth / 2, cam.position.y - cam.viewportHeight / 2);

        // Draw next blocks
        sb.draw(nextLeft.getTexture(), MARGIN * 2, MARGIN * 4, cam.viewportWidth / 2 - MARGIN * 5, cam.viewportWidth / 8);
        sb.draw(nextRight.getTexture(), cam.position.x + MARGIN*3, MARGIN * 4, cam.viewportWidth / 2 - MARGIN * 5, cam.viewportWidth / 8);

        // Draw block stacks
        for (Block block : left)
            sb.draw(block.getTexture(), -MARGIN, block.getY(), cam.viewportWidth / 2, cam.viewportWidth / 8);
        for (Block block : right)
            sb.draw(block.getTexture(), cam.position.x + MARGIN, block.getY(), cam.viewportWidth / 2, cam.viewportWidth / 8);

        sb.end();
    }

    @Override
    public void dispose() {
        Block.dispose();
    }
}
