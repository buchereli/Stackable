package com.ebucher.stackables.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by buche on 6/9/2017.
 */

public class BlockTextures {

    private static final int NUMBER_OF_BLOCKS = 4;
    public static Array<TextureRegion> blockTextures;

    static {
        blockTextures = new Array<TextureRegion>(NUMBER_OF_BLOCKS);
        TextureRegion region = new TextureRegion(new Texture("blocks.png"));
        int blockWidth = region.getRegionWidth();
        int blockHeight = region.getRegionHeight() / NUMBER_OF_BLOCKS;
        for (int i = 0; i < NUMBER_OF_BLOCKS; i++)
            blockTextures.add(new TextureRegion(region, 0, i * blockHeight, blockWidth, blockHeight));

    }
}
