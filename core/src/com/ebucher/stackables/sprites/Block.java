package com.ebucher.stackables.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ebucher.stackables.states.PlayState;

import java.util.HashMap;

/**
 * Created by buche on 6/8/2017.
 */

public class Block {
    private static HashMap<Integer, Texture> blockTextures;
    private int bottom;
    private static final float GRAVITY = -25;
    private int id;
    private int y;
    private float vel;
    private int height;

    static {
        blockTextures = new HashMap<Integer, Texture>();
        blockTextures.put(0, new Texture("block_dark_blue.png"));
        blockTextures.put(1, new Texture("block_dark_orange.png"));
        blockTextures.put(2, new Texture("block_light_blue.png"));
        blockTextures.put(3, new Texture("block_light_orange.png"));
    }

    public Block(int y, int id, int height) {
        this.y = y;
        this.id = id;
        this.height = height;
        this.bottom = height + PlayState.MARGIN * 8;
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

    public int getY() {
        return y;
    }

    public boolean settled(int intendedPos) {
        return (y == intendedPos * height + bottom);
    }

    public Texture getTexture() {
        return blockTextures.get(id);
    }

    public static void dispose() {
        for (Texture texture : blockTextures.values())
            texture.dispose();
    }

    public int getID() {
        return id;
    }

    public int getHeight() {
        return height;
    }

    public Block setY(int y) {
        this.y = y;
        return this;
    }
}
