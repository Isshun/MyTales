package org.smallbox.tales.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;

/**
 * Created by Alex on 06/11/2014.
 */
public abstract class MapObjectModel {

    public void draw(SpriteBatch batch, int x, int y) {
        onDraw(batch, x, y);
    }

    public void draw(SpriteCache cache, int x, int y) {
        onDraw(cache, x, y);
    }

    protected abstract void onDraw(SpriteBatch batch, int x, int y);

    protected abstract void onDraw(SpriteCache cache, int x, int y);
}
