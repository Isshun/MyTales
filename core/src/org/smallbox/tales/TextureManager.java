package org.smallbox.tales;

import com.badlogic.gdx.graphics.Texture;
import org.smallbox.tales.model.TextureModel;

import java.util.*;

/**
 * Created by Alex on 06/11/2014.
 */
public class TextureManager {
    private Map<String, TextureModel> _textures;

    public TextureManager() {
        _textures = new HashMap<String, TextureModel>();
        _textures.put("grounds/1.png", new TextureModel("grounds", "1.png"));
        _textures.put("grounds/2.png", new TextureModel("grounds", "2.png"));
        _textures.put("grounds/3.png", new TextureModel("grounds", "3.png"));
        _textures.put("grounds/4.png", new TextureModel("grounds", "4.png"));
        _textures.put("grounds/5.png", new TextureModel("grounds", "5.png"));
        _textures.put("grounds/6.png", new TextureModel("grounds", "6.png"));
        _textures.put("grounds/7.png", new TextureModel("grounds", "7.png"));
        _textures.put("grounds/8.png", new TextureModel("grounds", "8.png"));
        _textures.put("grounds/9.png", new TextureModel("grounds", "9.png"));
        _textures.put("grounds/10.png", new TextureModel("grounds", "10.png"));
        _textures.put("grounds/11.png", new TextureModel("grounds", "11.png"));
        _textures.put("grounds/12.png", new TextureModel("grounds", "12.png"));
        _textures.put("grounds/13.png", new TextureModel("grounds", "13.png"));
        _textures.put("grounds/14.png", new TextureModel("grounds", "14.png"));
        _textures.put("grounds/15.png", new TextureModel("grounds", "15.png"));
        _textures.put("grounds/16.png", new TextureModel("grounds", "16.png"));
        _textures.put("grounds/17.png", new TextureModel("grounds", "17.png"));
        _textures.put("grounds/18.png", new TextureModel("grounds", "18.png"));
        _textures.put("grounds/19.png", new TextureModel("grounds", "19.png"));
        _textures.put("grounds/20.png", new TextureModel("grounds", "20.png"));
        _textures.put("grounds/21.png", new TextureModel("grounds", "21.png"));
        _textures.put("grounds/22.png", new TextureModel("grounds", "22.png"));
        _textures.put("grounds/23.png", new TextureModel("grounds", "23.png"));
        _textures.put("grounds/24.png", new TextureModel("grounds", "24.png"));
        _textures.put("grounds/25.png", new TextureModel("grounds", "25.png"));
        _textures.put("grounds/26.png", new TextureModel("grounds", "26.png"));
        _textures.put("grounds/27.png", new TextureModel("grounds", "27.png"));
        _textures.put("grounds/28.png", new TextureModel("grounds", "28.png"));
        _textures.put("grounds/29.png", new TextureModel("grounds", "29.png"));
        _textures.put("grounds/30.png", new TextureModel("grounds", "30.png"));
        _textures.put("grounds/31.png", new TextureModel("grounds", "31.png"));
        _textures.put("grounds/32.png", new TextureModel("grounds", "32.png"));
        _textures.put("grounds/33.png", new TextureModel("grounds", "33.png"));
        _textures.put("grounds/34.png", new TextureModel("grounds", "34.png"));
        _textures.put("grounds/35.png", new TextureModel("grounds", "35.png"));
        _textures.put("grounds/36.png", new TextureModel("grounds", "36.png"));
        _textures.put("grounds/37.png", new TextureModel("grounds", "37.png"));
        _textures.put("grounds/38.png", new TextureModel("grounds", "38.png"));
        _textures.put("grounds/39.png", new TextureModel("grounds", "39.png"));
        _textures.put("grounds/40.png", new TextureModel("grounds", "40.png"));
        _textures.put("grounds/41.png", new TextureModel("grounds", "41.png"));
        _textures.put("grounds/42.png", new TextureModel("grounds", "42.png"));

        _textures.put("items/container.png", new TextureModel("items", "container.png"));
    }

    public Collection<TextureModel> getAll() {
        return _textures.values();
    }

    public TextureModel getTexture(String path, String name) {
        if (!_textures.containsKey(path + "/" + name)) {
            _textures.put(path + "/" + name, new TextureModel(path, name));
        }
        return _textures.get(path + "/" + name);
    }

    public TextureModel getTexture(String name) {
        if (!_textures.containsKey(name)) {
            _textures.put(name, new TextureModel(name));
        }
        return _textures.get(name);
    }
}
