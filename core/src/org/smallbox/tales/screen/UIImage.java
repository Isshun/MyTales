package org.smallbox.tales.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.smallbox.tales.Settings;
import org.smallbox.tales.model.ItemModel;
import org.smallbox.tales.model.TextureModel;

/**
 * Created by Alex on 06/11/2014.
 */
public class UIImage extends UITouchModel {
    private ItemModel _item;
    private TextureModel _texture;

    public UIImage(ItemModel item, int x, int y) {
        super(x, y, Settings.TILE_SIZE, Settings.TILE_SIZE);
        _item = item;
    }

    public UIImage(TextureModel texture, int x, int y) {
        super(x, y, Settings.TILE_SIZE, Settings.TILE_SIZE);
        _texture = texture;
    }

    public UIImage(int x, int y) {
        super(x, y, Settings.TILE_SIZE, Settings.TILE_SIZE);
    }

    @Override
    protected void onDraw(SpriteBatch batch) {
        if (_item != null) {
            _item.draw(batch, _x, Settings.SCREEN_HEIGHT - _height - _y);
        } else if (_texture != null) {
            batch.draw(_texture.getTexture(), _x, Settings.SCREEN_HEIGHT - _height - _y);
        }
    }

    public void setTexture(TextureModel texture) {
        _texture = texture;
    }
}
