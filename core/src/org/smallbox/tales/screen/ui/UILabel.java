package org.smallbox.tales.screen.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.smallbox.tales.Game;
import org.smallbox.tales.Settings;

/**
 * Created by Alex on 06/11/2014.
 */
public class UILabel extends UITouchModel {
    private String _text;
    private int _color;

    public UILabel(String text, int x, int y) {
        super(x, y, 80, 20);
        _text = text;
    }

    public void setColor(int color) {
        _color = color;
    }

    @Override
    protected void onDraw(SpriteBatch batch) {
        if (_color == 0) {
            Game.font.draw(batch, _text, _x, Settings.SCREEN_HEIGHT - _y);
        } else {
            Game.fontYellow.draw(batch, _text, _x, Settings.SCREEN_HEIGHT - _y);
        }
    }

    public void setText(String text) {
        _text = text;
    }
}
