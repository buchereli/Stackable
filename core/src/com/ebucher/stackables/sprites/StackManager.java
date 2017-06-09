package com.ebucher.stackables.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ebucher.stackables.global.G;

/**
 * Created by buche on 6/9/2017.
 */

public class StackManager {

    private static BlockStack leftStack, rightStack;
    private static int score;

    static {
        leftStack = new BlockStack(0);
        rightStack = new BlockStack(G.CENTER + G.MARGIN);

        score = 0;
    }

    public static void update(float dt) {
        leftStack.update(dt);
        rightStack.update(dt);

        int tempScore = score;

        score += leftStack.scoreStack();
        score += rightStack.scoreStack();

        if (tempScore != score)
            System.out.println(score);
    }

    public static void render(SpriteBatch sb){
        leftStack.render(sb);
        rightStack.render(sb);
    }

    public static void placeBlocks(Vector2 touchEvent){
        leftStack.placeBlock(touchEvent, NextBlocks.left());
        rightStack.placeBlock(touchEvent, NextBlocks.right());
    }

}
