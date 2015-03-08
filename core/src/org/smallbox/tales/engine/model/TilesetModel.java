package org.smallbox.tales.engine.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import org.smallbox.tales.Game;
import org.smallbox.tales.Settings;
import org.smallbox.tales.model.MapObjectModel;

/**
 * Created by Alex on 06/11/2014.
 */
public class TilesetModel {
    private final String _id;
    private final String _name;
    private final String _file;
    private final int _width;
    private final int _height;
    private final int _offsetX;
    private final int _offsetY;
    private final String _path;
    private Texture _texture;

    public TilesetModel(String id, String path, String name, String file, int width, int height, int offsetX, int offsetY) {
        _id = id;
        _path = path;
        _name = name;
        _file = file;
        _width = width;
        _height = height;
        _offsetX = offsetX;
        _offsetY = offsetY;
    }

    public Texture getTexture() {
        if (_texture == null) {
            _texture = Game.textures.getTexture(_path, _file);
        }
        return _texture;
    }

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
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

    public int getOffsetX() {
        return _offsetX;
    }

    public int getOffsetY() {
        return _offsetY;
    }
}
