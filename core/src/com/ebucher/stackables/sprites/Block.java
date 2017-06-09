package com.ebucher.stackables.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ebucher.stackables.global.G;

/**
 * Created by buche on 6/8/2017.
 */

public class Block {
    private static final float GRAVITY = -25;
    private static final float FADE_RATE = -2f;
    private static final int BLOCK_HEIGHT = G.BLOCK_HEIGHT;
    private static final int NEXT_BLOCKS_HEIGHT = NextBlocks.HEIGHT;

    private int id;
    private int y;
    private float vel;
    private boolean fade;
    private float alpha;

    Block(int y, int id) {
        this.y = y;
        this.id = id;
        this.vel = 0;
        this.fade = false;
        this.alpha = 1.0f;
    }

    void update(float dt, int intendedPos) {
        if (fade)
            alpha += FADE_RATE * dt;
        else {
            if (y != intendedPos * BLOCK_HEIGHT + NEXT_BLOCKS_HEIGHT) {
                vel += GRAVITY * dt;
                y += vel;
            }

            if (y < intendedPos * BLOCK_HEIGHT + NEXT_BLOCKS_HEIGHT) {
                y = intendedPos * BLOCK_HEIGHT + NEXT_BLOCKS_HEIGHT;
                vel = 0;
            }
        }
    }

    void fade() {
        fade = true;
    }

    boolean fading() {
        return fade;
    }

    float getAlpha() {
        return alpha;
    }

    void setVel(float vel) {
        this.vel = vel;
    }

    float getVel() {
        return vel;
    }

    int getY() {
        return y;
    }

    boolean settled(int intendedPos) {
        return (y == intendedPos * BLOCK_HEIGHT + NEXT_BLOCKS_HEIGHT);
    }

    TextureRegion getTexture() {
        return BlockTextures.blockTextures.get(id);
    }

    int getID() {
        return id;
    }

    Block setY(int y) {
        this.y = y;
        return this;
    }
}
