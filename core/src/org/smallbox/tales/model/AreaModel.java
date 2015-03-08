package org.smallbox.tales.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import org.smallbox.tales.Game;
import org.smallbox.tales.Settings;
import org.smallbox.tales.engine.model.TextureModel;

/**
 * Created by Alex on 06/11/2014.
 */
public class AreaModel extends MapObjectModel {
    private String _path;
    private String _name;
    protected Texture _texture;
    protected int _index;

    public AreaModel(String path, String name, int index) {
        _texture = Game.textures.getTexture(path, name);
        _index = index;
        _path = path;
        _name = name;
    }

    public AreaModel(Texture texture, int index) {
        _texture = texture;
        _index = index;
    }

    @Override
    public void onDraw(SpriteBatch batch, int x, int y) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void onDraw(SpriteCache cache, int x, int y) {
        cache.add(_texture, x, y, 0, 0, Settings.TILE_SIZE, Settings.TILE_SIZE);
        //throw new RuntimeException("Not implemented");
    }

    public String getName() {
        return _name;
    }

    public String getPath() {
        return _path;
    }

    public int getIndex() {
        return _index;
    }
}
