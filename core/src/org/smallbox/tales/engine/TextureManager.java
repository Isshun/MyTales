package org.smallbox.tales.engine;

import com.badlogic.gdx.graphics.Texture;
import org.smallbox.tales.engine.model.TextureModel;

import java.util.*;

/**
 * Created by Alex on 06/11/2014.
 */
public class TextureManager {
    private Map<String, TextureModel> _textures;

    public TextureManager() {
        _textures = new HashMap<String, TextureModel>();
    }

    public Texture getTexture(String path, String name) {
        if (!_textures.containsKey(path + "/" + name)) {
            _textures.put(path + "/" + name, new TextureModel(path, name));
        }

        TextureModel texture = _textures.get(path + "/" + name);
        if (texture == null) {
            return null;
        }

        return texture.getTexture();
    }

    public Texture getTexture(String name) {
        if (!_textures.containsKey(name)) {
            _textures.put(name, new TextureModel(name));
        }

        TextureModel texture = _textures.get(name);
        if (texture == null) {
            return null;
        }

        return texture.getTexture();
    }
}
