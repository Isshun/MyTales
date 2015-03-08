package org.smallbox.tales.engine.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.smallbox.tales.Settings;
import org.smallbox.tales.model.ItemModel;
import org.smallbox.tales.engine.model.TextureModel;

/**
 * Created by Alex on 06/11/2014.
 */
public class UIImage extends UITouchModel {
    private ItemModel _item;
    private Texture _texture;
    private final int _offsetX;
    private final int _offsetY;

    public UIImage(ItemModel item, int x, int y) {
        super(Settings.TILE_SIZE, Settings.TILE_SIZE);
        _offsetX = x;
        _offsetY = y;
        _item = item;
    }

    public UIImage(Texture texture, int x, int y) {
        super(Settings.TILE_SIZE, Settings.TILE_SIZE);
        _offsetX = x;
        _offsetY = y;
        _texture = texture;
    }

    public UIImage() {
        super(Settings.TILE_SIZE, Settings.TILE_SIZE);
        _offsetX = 0;
        _offsetY = 0;
    }

    public UIImage(int x, int y, int width, int height) {
        super(width, height);
        _offsetX = x;
        _offsetY = y;
    }

    @Override
    protected void onDraw(SpriteBatch batch) {
        if (_item != null) {
            _item.drawIcon(batch, _x, Settings.SCREEN_HEIGHT - _height - _y);
        } else if (_texture != null) {
            batch.draw(_texture, _x, Settings.SCREEN_HEIGHT - _height - _y);
        }
    }

    public void setTexture(Texture texture) {
        _texture = texture;
    }
}
