package com.ebucher.stackables.stacks;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ebucher.stackables.global.G;
import com.ebucher.stackables.next.NextBlocks;
import com.ebucher.stackables.score.ScoreBoard;

/**
 * Created by buche on 6/9/2017.
 */

public class StackManager {

    public static final int HEIGHT = Block.BLOCK_HEIGHT * 10;

    private static com.ebucher.stackables.stacks.BlockStack leftStack, rightStack;

    static {
        leftStack = new com.ebucher.stackables.stacks.BlockStack(0);
        rightStack = new com.ebucher.stackables.stacks.BlockStack(G.CENTER + G.MARGIN);
    }

    public static void update(float dt) {
        leftStack.update(dt);
        rightStack.update(dt);

        int points = 0;
        points += leftStack.scoreStack();
        points += rightStack.scoreStack();
        ScoreBoard.addPoints(points);
    }

    public static void render(SpriteBatch sb){
        leftStack.render(sb);
        rightStack.render(sb);
    }

    public static void placeBlocks(Vector2 touchEvent){
        touchEvent.y -= NextBlocks.HEIGHT;
        leftStack.placeBlock(touchEvent, NextBlocks.left());
        rightStack.placeBlock(touchEvent, NextBlocks.right());
    }

}
