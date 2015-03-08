package org.smallbox.tales.model;

import com.badlogic.gdx.graphics.Texture;
import org.smallbox.tales.Game;
import org.smallbox.tales.engine.model.TextureModel;

/**
 * Created by Alex on 25/11/2014.
 */
public class GroundItemModel {
    private final String _name;
    private final String _filename;
    private final Texture _texture;

    public GroundItemModel(String id, String name, String filename) {
        _name = name;
        _filename = filename;
        _texture = Game.textures.getTexture("grounds", filename);
    }

    public String getName() {
        return _name;
    }

    public String getFilename() {
        return _filename;
    }

    public Texture getTexture() {
        return _texture;
    }
}
