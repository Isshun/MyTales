package org.smallbox.tales.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
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
    private final int _width;
    private final int _height;
    private TextureModel _texture;

    public ItemModel(String id, String name, String file, int width, int height) {
        _id = id;
        _name = name;
        _file = file;
        _width = width;
        _height = height;
    }

    public TextureModel getTexture() {
        if (_texture == null) {
            _texture = Game.textures.getTexture("items/tree", _file);
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
        batch.draw(texture.getTexture(), x, y, 0, 0, _width * Settings.TILE_SIZE, _height * Settings.TILE_SIZE);
    }

    @Override
    public void onDraw(SpriteCache cache, int x, int y) {
        TextureModel texture = getTexture();
        cache.add(texture.getTexture(), x, y, 0, 0, _width * Settings.TILE_SIZE, _height * Settings.TILE_SIZE);
    }

    public void drawIcon(SpriteBatch batch, int x, int y) {
        TextureModel texture = getTexture();
        float scale = 1f / Math.max(_width, _height);
        batch.draw(texture.getTexture(),
                (float)x, (float)y, // Position
                0f, 0f, // Origins
                (float)(_width * Settings.TILE_SIZE), (float)(_height * Settings.TILE_SIZE),  // Size
                scale, scale, // Scale
                0f, // Rotation
                0, 0, // Source
                _width * Settings.TILE_SIZE, _height * Settings.TILE_SIZE,  //Source size
                false, false); // Flip
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
