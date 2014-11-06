package org.smallbox.tales.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Alex on 06/11/2014.
 */
public class GroundModel extends AreaModel {
    public final static int CENTER = 6;

    public final static int TOP = 7;
    public final static int BOTTOM = 9;
    public final static int LEFT = 10;
    public final static int RIGHT = 8;

    public final static int TOP_LEFT = 11;
    public final static int TOP_RIGHT = 12;
    public final static int BOTTOM_LEFT = 14;
    public final static int BOTTOM_RIGHT = 13;

    public GroundModel(String path, String name, int index) {
        super(path, name, index);
    }

    public GroundModel(TextureModel texture, int index) {
        super(texture, index);
    }

    @Override
    public void draw(SpriteBatch batch, int x, int y) {
        switch (_index) {
            default: batch.draw(_texture.getTexture(), x, y, 0 % 2, 0 / 2, 32, 32); break;
            case CENTER: batch.draw(_texture.getTexture(), x, y, 16, 48, 32, 32); break;
            case TOP: batch.draw(_texture.getTexture(), x, y, 16, 32, 32, 32); break;
            case RIGHT: batch.draw(_texture.getTexture(), x, y, 32, 48, 32, 32); break;
            case BOTTOM: batch.draw(_texture.getTexture(), x, y, 16, 64, 32, 32); break;
            case LEFT: batch.draw(_texture.getTexture(), x, y, 0, 48, 32, 32); break;
            case TOP_LEFT: batch.draw(_texture.getTexture(), x, y, 0, 32, 32, 32); break;
            case TOP_RIGHT: batch.draw(_texture.getTexture(), x, y, 32, 32, 32, 32); break;
            case BOTTOM_RIGHT: batch.draw(_texture.getTexture(), x, y, 32, 64, 32, 32); break;
            case BOTTOM_LEFT: batch.draw(_texture.getTexture(), x, y, 0, 64, 32, 32); break;
        }
    }

}
