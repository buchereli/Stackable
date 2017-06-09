package com.ebucher.stackables.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ebucher.stackables.global.G;

import java.util.ArrayList;

/**
 * Created by buche on 6/9/2017.
 */

public class BlockStack {

    private ArrayList<Block> stack;
    private int x;

    public BlockStack(int x) {
        stack = new ArrayList<Block>();
        this.x = x;
    }

    public void render(SpriteBatch sb) {
        for (Block block : stack)
            sb.draw(block.getTexture(), x, block.getY(), G.BLOCK_WIDTH, G.BLOCK_HEIGHT);
    }

    public void update(float dt) {
        for (int i = 0; i < stack.size(); i++)
            stack.get(i).update(dt, i);
    }

    public int scoreStack() {
        int count = 0;
        int score = 0;
        int id = -1;
        ArrayList<Integer> indexes = new ArrayList<Integer>();

        for (int i = 0; i < stack.size(); i++) {
            if (id != stack.get(i).getID() || !stack.get(i).settled(i)) {
                if (count >= 3) {
                    if(count == 3)
                        score += 3;
                    if(count == 4)
                        score += 5;
                    while (count > 0) {
                        indexes.add(i - count);
                        count--;
                    }
                }
                if (!stack.get(i).settled(i))
                    break;
                id = stack.get(i).getID();
                count = 1;
            } else
                count++;
        }

        if (count >= 3)
            while (count > 0) {
                indexes.add(stack.size() - count);
                count--;
            }

        removeAll(indexes);

        return score;
    }

    private void removeAll(ArrayList<Integer> indexes) {
        for (int i = stack.size() - 1; i >= 0; i--)
            if (indexes.contains(i))
                stack.remove(i);
    }

    public void placeBlock(Vector2 touchEvent, int id) {
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

        stack.add(new Block((int) touchEvent.y + G.BLOCK_HEIGHT / 2, id));
    }

}
