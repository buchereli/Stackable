package com.ebucher.stackables.score;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ebucher.stackables.global.G;
import com.ebucher.stackables.next.NextBlocks;
import com.ebucher.stackables.stacks.StackManager;

/**
 * Created by buche on 6/9/2017.
 */

public class ScoreBoard {

    private static final int BOTTOM = NextBlocks.HEIGHT + StackManager.HEIGHT;
    private static final int HEIGHT = G.HEIGHT - BOTTOM; //G.BLOCK_HEIGHT * 2;
    private static BitmapFont font;

    private static Texture line;
    private static Texture bg;
    private static int score;

    private static GlyphLayout scoreLayout;


    public ScoreBoard() {
        line = new Texture("line.png");
        bg = new Texture("bg.png");

        scoreLayout = new GlyphLayout();
        font = new BitmapFont(Gdx.files.internal("arvo-regular.fnt"));
        for (float scale = 4f; scale > .5f; scale -= .5f) {
            font.getData().setScale(scale);
            scoreLayout.setText(font, score + "");
            if (scoreLayout.height < HEIGHT - G.MARGIN * 2)
                break;
        }
        Color grey = new Color(1f, .4f, 0f, 1f);
        font.setColor(grey);

        score = 0;
    }

    public static void addPoints(int points) {
        score += points;
    }

    public static void render(SpriteBatch sb) {
        sb.draw(bg, 0, BOTTOM, G.WIDTH, HEIGHT);
        sb.draw(line, 0, BOTTOM, G.WIDTH, G.MARGIN);
        scoreLayout.setText(font, score + "");
        float scoreX = (G.WIDTH - scoreLayout.width) / 2;
        float scoreY = BOTTOM + (G.HEIGHT - BOTTOM + scoreLayout.height) / 2;
        font.draw(sb, scoreLayout, scoreX, scoreY);
    }

    public static void dispose() {
        line.dispose();
        bg.dispose();
        font.dispose();
    }

}
