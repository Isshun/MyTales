package org.smallbox.tales.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Alex on 06/11/2014.
 */
public abstract class MapObjectModel {

    public void draw(SpriteBatch batch, int x, int y) {
        onDraw(batch, x, y);
    }

    protected abstract void onDraw(SpriteBatch batch, int x, int y);
}
