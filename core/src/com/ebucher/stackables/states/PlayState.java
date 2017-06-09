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

    private Texture bg;
    private Array<Block> left, right;
    private Random random;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false);

        bg = new Texture("bg.png");

        left = new Array<Block>();
        right = new Array<Block>();

        random = new Random();
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            left.add(new Block((int) cam.viewportHeight, random.nextInt(4), (int) cam.viewportWidth / 8));
            right.add(new Block((int) cam.viewportHeight, random.nextInt(4), (int) cam.viewportWidth / 8));
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
        sb.draw(bg, cam.position.x - cam.viewportWidth / 2, cam.position.y - cam.viewportHeight / 2);
        for (Block block : left)
            sb.draw(block.getTexture(), -10, block.getY(), cam.viewportWidth / 2, cam.viewportWidth / 8);
        for (Block block : right)
            sb.draw(block.getTexture(), cam.position.x+10, block.getY(), cam.viewportWidth / 2, cam.viewportWidth / 8);
        sb.end();
    }

    @Override
    public void dispose() {
        Block.dispose();
    }
}
