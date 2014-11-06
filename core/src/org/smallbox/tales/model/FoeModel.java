package org.smallbox.tales.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.smallbox.tales.Game;

/**
 * Created by Alex on 02/11/2014.
 */
public class FoeModel extends CharacterModel {
    private Texture _texture;
    private String name;
    private int _beforeUpdate;

    public FoeModel() {
        _posX = 400;
    }

    public void draw(SpriteBatch batch, int x, int y) {
        Game.batch.draw(_texture, x + _posX, y + _posY);
        Game.font.draw(batch, "Foe1", x + _posX + 100, y + _posY + 150);
    }

    @Override
    protected void onUpdate() {
        if (--_beforeUpdate < 0) {
            if (_target != null) {
                if (_posX < _target.getPosX() - 80) {
                    _velocityX = _maxVelocityX;
                }
                if (_posX > _target.getPosX() + 80) {
                    _velocityX = -_maxVelocityX;
                }
            }
            _beforeUpdate = 60;
        }

        _posX += _velocityX;
        _velocityX *= 0.1;
    }

    public enum Type { FOE_1 };

    public static FoeModel newInstance(Type type) {
        FoeModel foe = new FoeModel();

        foe._texture = new Texture("resources/foe.png");

        foe._name = "foe1";
        foe._health = 100;
        foe._maxHealth = 100;
        foe._resist = 0.5;
        foe._strength = 10;

        return foe;
    }
}
