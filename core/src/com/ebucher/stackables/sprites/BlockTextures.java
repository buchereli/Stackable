package com.ebucher.stackables.sprites;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

/**
 * Created by buche on 6/9/2017.
 */

public class BlockTextures {

    public static HashMap<Integer, Texture> blockTextures;

    static {
        blockTextures = new HashMap<Integer, Texture>();
        blockTextures.put(0, new Texture("block_dark_blue.png"));
        blockTextures.put(1, new Texture("block_dark_orange.png"));
        blockTextures.put(2, new Texture("block_light_blue.png"));
        blockTextures.put(3, new Texture("block_light_orange.png"));
    }

    public static void dispose() {
        for (Texture texture : blockTextures.values())
            texture.dispose();
    }

}
