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

    public static final int MAX_STACK = 10;
    public static final int HEIGHT = Block.BLOCK_HEIGHT * MAX_STACK;

    private static BlockStack leftStack, rightStack;
    private static float shiftAbove;

    public StackManager() {
        leftStack = new BlockStack(0);
        rightStack = new BlockStack(G.CENTER + G.MARGIN);
        shiftAbove = -1;
    }

    public static void update(float dt) {
        leftStack.update(dt);
        rightStack.update(dt);

        int points = 0;
        points += leftStack.scoreStack();
        points += rightStack.scoreStack();
        ScoreBoard.addPoints(points);
    }

    public static boolean gameOver() {
        return (leftStack.gameOver() || rightStack.gameOver());
    }

    public static void render(SpriteBatch sb) {
        leftStack.render(sb, shiftAbove);
        rightStack.render(sb, shiftAbove);
    }

    public static void placeBlocks(Vector2 touchEvent) {
        touchEvent.y -= NextBlocks.HEIGHT;
        leftStack.placeBlock(touchEvent, NextBlocks.left());
        rightStack.placeBlock(touchEvent, NextBlocks.right());
    }

    public static void setShiftAbove(float y) {
        shiftAbove = y;
    }
}
