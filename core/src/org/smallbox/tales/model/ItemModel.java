package org.smallbox.tales.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.smallbox.tales.Game;
import org.smallbox.tales.Settings;
import org.smallbox.tales.TextureManager;

/**
 * Created by Alex on 06/11/2014.
 */
public class ItemModel extends MapObjectModel {
    private final String _id;
    private final String _name;
    private final String _file;
    private final int _x;
    private final int _y;
    private final int _width;
    private final int _height;
    private TextureModel _texture;

    public ItemModel(String id, String name, String file, int x, int y, int width, int height) {
        _id = id;
        _name = name;
        _file = file;
        _x = x;
        _y = y;
        _width = width;
        _height = height;
    }

    public TextureModel getTexture() {
        if (_texture == null) {
            _texture = Game.textures.getTexture("items", _file);
        }
        return _texture;
    }

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }

    @Override
    public void onDraw(SpriteBatch batch, int x, int y) {
        TextureModel texture = getTexture();

        batch.draw(texture.getTexture(), x, y, _x * Settings.TILE_SIZE, _y * Settings.TILE_SIZE, _width * Settings.TILE_SIZE, _height * Settings.TILE_SIZE);
    }

    public String getName() {
        return _name;
    }

    public String getPath() {
        return "";
    }

    public String getId() {
        return _id;
    }
}
