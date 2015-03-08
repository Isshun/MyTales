package org.smallbox.tales.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import org.smallbox.tales.engine.model.TilesetModel;
import org.smallbox.tales.model.MapObjectModel;

/**
 * Created by Alex on 27/11/2014.
 */
public class TilesetAreaModel extends MapObjectModel {

    private final TilesetModel _tileset;
    private final int _x;
    private final int _y;
    private final int _width;
    private final int _height;

    public TilesetAreaModel(TilesetModel tileset, int x, int y, int width, int height) {
        _tileset = tileset;
        _x = x;
        _y = y;
        _width = width;
        _height = height;
    }

    @Override
    protected void onDraw(SpriteBatch batch, int x, int y) {
        //batch.draw(_tileset.getTexture(), x, y, _tileset.getOffsetX() + _x, _tileset.getOffsetY() + _y, _width, _height);
        batch.draw(_tileset.getTexture(), x, y, _x, _y, _width, _height);
    }

    @Override
    protected void onDraw(SpriteCache cache, int x, int y) {

    }

    @Override
    public String getName() {
        return null;
    }
}
