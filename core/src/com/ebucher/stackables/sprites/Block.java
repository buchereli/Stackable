package com.ebucher.stackables.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ebucher.stackables.global.G;
import com.ebucher.stackables.states.PlayState;

import java.util.HashMap;

/**
 * Created by buche on 6/8/2017.
 */

public class Block {
    private int bottom;
    private static final float GRAVITY = -25;
    private static final int height = G.BLOCK_HEIGHT;
    private int id;
    private int y;
    private float vel;

    public Block(int y, int id) {
        this.y = y;
        this.id = id;
        this.bottom = height + G.MARGIN * 8;
    }

    public void update(float dt, int intendedPos) {
        if (y != intendedPos * height + bottom) {
            vel += GRAVITY * dt;
            y += vel;
        }

        if (y < intendedPos * height + bottom) {
            y = intendedPos * height + bottom;
            vel = 0;
        }
    }

    public void setVel(float vel) {
        this.vel = vel;
    }

    public float getVel() {
        return vel;
    }

    public int getY() {
        return y;
    }

    public boolean settled(int intendedPos) {
        return (y == intendedPos * height + bottom);
    }

    public TextureRegion getTexture() {
        return BlockTextures.blockTextures.get(id);
    }

    public int getID() {
        return id;
    }

    public Block setY(int y) {
        this.y = y;
        return this;
    }
}
