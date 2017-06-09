package com.ebucher.stackables.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.utils.Array;
import com.ebucher.stackables.StackablesMain;
import com.ebucher.stackables.sprites.Block;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by buche on 6/8/2017.
 */

public class PlayState extends State implements InputProcessor {
    public static final int MARGIN = 5;

    private Texture bg;
    private ArrayList<Block> left, right;
    private Block nextLeft, nextRight;
    private Random random;
    private boolean flip;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false);

        bg = new Texture("bg.png");

        random = new Random();

        left = new ArrayList<Block>();
        right = new ArrayList<Block>();
        nextLeft = new Block((int) cam.viewportHeight, random.nextInt(4), (int) cam.viewportWidth / 8);
        nextRight = new Block((int) cam.viewportHeight, random.nextInt(4), (int) cam.viewportWidth / 8);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();

        for (int i = 0; i < left.size(); i++)
            left.get(i).update(dt, i);

        for (int i = 0; i < right.size(); i++)
            right.get(i).update(dt, i);

        removeStacks(left);
        removeStacks(right);
    }

    public void removeStacks(ArrayList<Block> stack) {
        int count = 0;
        int id = -1;
        ArrayList<Integer> idsToRemove = new ArrayList<Integer>();

        for (int i = 0; i < stack.size(); i++) {
            if (id != stack.get(i).getID() || !stack.get(i).settled(i)) {
                if (count >= 3)
                    while (count > 0) {
                        idsToRemove.add(i - count);
                        count--;
                    }
                if (!stack.get(i).settled(i))
                    break;
                id = stack.get(i).getID();
                count = 1;
            } else
                count++;
        }

        if (count >= 3)
            while (count > 0) {
                idsToRemove.add(stack.size() - count);
                count--;
            }

        for (int i = stack.size() - 1; i >= 0; i--)
            if (idsToRemove.contains(i))
                stack.remove(i);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();

        // Draw background
        sb.draw(bg, cam.position.x - cam.viewportWidth / 2, cam.position.y - cam.viewportHeight / 2);

        // Draw next blocks

        sb.draw(flip ? nextRight.getTexture() : nextLeft.getTexture(),
                MARGIN * 2, MARGIN * 4, cam.viewportWidth / 2 - MARGIN * 5, cam.viewportWidth / 8);
        sb.draw(flip ? nextLeft.getTexture() : nextRight.getTexture(),
                cam.position.x + MARGIN * 3, MARGIN * 4, cam.viewportWidth / 2 - MARGIN * 5, cam.viewportWidth / 8);

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
        float my = cam.viewportHeight - screenY;

        if (my < cam.viewportWidth / 8 + MARGIN * 4) {
            flip = !flip;
        } else {
            boolean placed = false;
            for (int i = 0; i < left.size(); i++) {
                float distance = left.get(i).getY() + left.get(i).getHeight() / 2 - my;
                System.out.println(distance);
                if (distance <= left.get(i).getHeight() && distance > 0) {
                    left.add(i, (flip ? nextRight : nextLeft).setY(-1));
                    placed = true;
                    break;
                }
            }
            if (!placed)
                left.add((flip ? nextRight : nextLeft).setY((int) my));

            placed = false;
            for (int i = 0; i < right.size(); i++) {
                float distance = right.get(i).getY() + right.get(i).getHeight() / 2 - my;
                if (distance <= right.get(i).getHeight() && distance > 0) {
                    right.add(i, (flip ? nextLeft : nextRight).setY(-1));
                    placed = true;
                    break;
                }
            }
            if (!placed)
                right.add((flip ? nextLeft : nextRight).setY((int) my));

            nextLeft = new Block((int) cam.viewportHeight, random.nextInt(4), (int) cam.viewportWidth / 8);
            nextRight = new Block((int) cam.viewportHeight, random.nextInt(4), (int) cam.viewportWidth / 8);
            flip = false;
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
