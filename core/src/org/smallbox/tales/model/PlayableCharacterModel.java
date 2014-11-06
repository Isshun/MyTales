package org.smallbox.tales.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.smallbox.tales.Game;

/**
 * Created by Alex on 02/11/2014.
 */
public class PlayableCharacterModel extends CharacterModel {
    private Texture _texture;

    public PlayableCharacterModel(String name) {
        _name = name;
        _texture = new Texture("resources/player.png");
        _maxVelocityX = 10;
        _strength = 10;
    }

    public String getName() {
        return _name;
    }

    public void draw(SpriteBatch batch, int x, int y) {
        Game.batch.draw(_texture, x + _posX, y + _posY);
        Game.font.draw(batch, _name, x + _posX, y + _posY + 180);
    }

    public void move(float value) {
        _velocityX = (int) (_maxVelocityX * value);
    }

    @Override
    protected void onUpdate() {
        _posX += _velocityX;
        _posY += _velocityY;
    }
}
