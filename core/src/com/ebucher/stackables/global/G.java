package com.ebucher.stackables.global;

/**
 * Created by buche on 6/9/2017.
 */

public class G {

    public static int BLOCK_HEIGHT, BLOCK_WIDTH;
    public static int MARGIN;
    public static int CENTER, HEIGHT, WIDTH;

    public static void init(int width, int height) {
        MARGIN = width / 100;
        BLOCK_HEIGHT = width / 8;
        BLOCK_WIDTH = width / 2 - MARGIN;
        CENTER = width / 2;
        HEIGHT = height;
        WIDTH = width;
    }
}
