package com.ebucher.stackables.next;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ebucher.stackables.global.G;
import com.ebucher.stackables.stacks.BlockTextures;

import java.util.Random;

/**
 * Created by buche on 6/9/2017.
 */

public class NextBlocks {

    private static int left, right;
    private static Random random;
    private static final int HORIZONTAL_MARGIN = G.MARGIN * 2;
    private static final int VERTICAL_MARGIN = G.MARGIN * 4;
    public static final int HEIGHT = VERTICAL_MARGIN * 2 + G.BLOCK_HEIGHT;

    static {
        random = new Random();
        set();
    }

    public static void set() {
        left = random.nextInt(4);
        right = random.nextInt(4);
    }

    public static void swap() {
        int temp = left;
        left = right;
        right = temp;
    }

    public static int left(){
        return left;
    }

    public static int right(){
        return right;
    }

    public static void render(SpriteBatch sb, int center) {
        // left
        sb.draw(BlockTextures.blockTextures.get(left), HORIZONTAL_MARGIN, VERTICAL_MARGIN,
                G.BLOCK_WIDTH - HORIZONTAL_MARGIN * 2, G.BLOCK_HEIGHT);
        // right
        sb.draw(BlockTextures.blockTextures.get(right), center + HORIZONTAL_MARGIN, VERTICAL_MARGIN,
                G.BLOCK_WIDTH - HORIZONTAL_MARGIN * 2, G.BLOCK_HEIGHT);

    }
}
