package org.smallbox.tales.engine.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Alex on 06/11/2014.
 */
public class TextureModel {
    protected String _path;
    protected String _name;
    protected Texture _texture;

    public TextureModel(String path, String name) {
        _path = path;
        _name = name;
        _texture = new Texture("resources/" + path + "/" + name);
    }

    public TextureModel(String name) {
        _name = name;
        _texture = new Texture("resources/" + name);
    }

    public String getName() {
        return _name;
    }

    public Texture getTexture() {
        return _texture;
    }

    public String getPath() {
        return _path;
    }
}
