package com.ebucher.stackables.stacks;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ebucher.stackables.global.G;
import com.ebucher.stackables.next.NextBlocks;

import java.util.ArrayList;

/**
 * Created by buche on 6/9/2017.
 */

class BlockStack {

    private ArrayList<Block> stack;
    private int x;

    BlockStack(int x) {
        stack = new ArrayList<Block>();
        this.x = x;
    }

    void render(SpriteBatch sb, float shiftAbove) {
        for (Block block : stack) {
            // Set draw color to blocks alpha
            sb.setColor(1, 1, 1, block.getAlpha());

            // If block if above touch then shift it up by margin
            if (shiftAbove != -1 && block.getY() + G.BLOCK_HEIGHT / 2 > shiftAbove)
                sb.draw(block.getTexture(), x, block.getY() + NextBlocks.HEIGHT + G.MARGIN * 2
                        , G.BLOCK_WIDTH, G.BLOCK_HEIGHT);
            else
                sb.draw(block.getTexture(), x, block.getY() + NextBlocks.HEIGHT,
                        G.BLOCK_WIDTH, G.BLOCK_HEIGHT);
        }

        // Reset draw alpha to 1
        sb.setColor(1, 1, 1, 1f);
    }

    void update(float dt) {
        for (int i = 0; i < stack.size(); i++)
            stack.get(i).update(dt, i);

        removeCompleted();
    }

    int scoreStack() {
        int count = 0;
        int score = 0;
        int id = -1;
        for (int i = 0; i < stack.size(); i++) {
            if (id != stack.get(i).getID() || !stack.get(i).settled(i) || stack.get(i).fading()) {
                if (count >= 3) {
                    score += scoreCount(count);
                    while (count > 0) {
                        stack.get(i - count).fade();
                        count--;
                    }
                }
                if (!stack.get(i).settled(i) || stack.get(i).fading()) {
                    count = 0;
                    continue;
                }
                id = stack.get(i).getID();
                count = 1;
            } else
                count++;
        }

        if (count >= 3) {
            score += scoreCount(count);
            while (count > 0) {
                stack.get(stack.size() - count).fade();
                count--;
            }
        }

        return score;
    }

    private int scoreCount(int count) {
        if (count == 3)
            return 3;
        if (count == 4)
            return 5;

        return 0;
    }

    private void removeCompleted() {
//        stack.removeIf(b -> b.getAlpha() <= 0);
        for (int i = stack.size() - 1; i >= 0; i--)
            if (stack.get(i).getAlpha() <= 0)
                stack.remove(i);
    }

    void placeBlock(Vector2 touchEvent, int id) {
        for (int i = 0; i < stack.size(); i++) {
            float distance = stack.get(i).getY() + G.BLOCK_HEIGHT / 2 - touchEvent.y;
            if (stack.get(i).settled(i)) {
                // If we are adding bellow a settled block
                if (distance <= G.BLOCK_HEIGHT && distance >= 0) {
                    stack.add(i, new Block(-1, id));
                    return;
                }
            } else {
                // If we are adding bellow a non settled block
                if (distance > 0) {
                    if (distance <= G.BLOCK_HEIGHT) {
                        // Overlaps
                        stack.add(i, new Block(stack.get(i).getY() - G.BLOCK_HEIGHT, id));
                        stack.get(i + 1).setVel(0);
                        return;
                    } else {
                        // No overlap
                        stack.add(i, new Block((int) touchEvent.y - G.BLOCK_HEIGHT / 2, id));
                        // TODO should slow top block not speed up bottom block
                        stack.get(i).setVel(stack.get(i + 1).getVel());
                        return;
                    }
                }
                // If we are adding above a non settled block with overlap
                if (distance > -1 * G.BLOCK_HEIGHT / 2) {
                    stack.add(i + 1, new Block(stack.get(i).getY() + G.BLOCK_HEIGHT, id));
                    return;
                }
            }
        }

        stack.add(new Block((int) touchEvent.y - G.BLOCK_HEIGHT / 2, id));
    }

    boolean isEmpty() {
        return stack.isEmpty();
    }

    // If stack is over MAX_STACK and no blocks are fading then gameOver = true
    boolean gameOver() {
        if (stack.size() > StackManager.MAX_STACK) {
            for (Block block : stack)
                if (block.fading())
                    return false;
            return true;
        }
        return false;
    }
}
