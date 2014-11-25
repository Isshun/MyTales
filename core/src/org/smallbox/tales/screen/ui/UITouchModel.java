package org.smallbox.tales.screen.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Alex on 06/11/2014.
 */
public abstract class UITouchModel {
    protected int _x;
    protected int _y;
    protected int _width;
    protected int _height;
    private OnClickListener _onClickListener;
    private OnTouchListener _onTouchListener;
    private OnClickListener _onRightClickListener;

    public UITouchModel(int x, int y, int width, int height) {
        _x = x;
        _y = y;
        _width = width;
        _height = height;
    }

    public void draw(SpriteBatch batch) {
        onDraw(batch);
    }

    protected abstract void onDraw(SpriteBatch batch);

    public boolean isTouched(int x, int y) {
        return (x >= _x && x <= _x + _width && y >= _y && y <= _y + _height);
    }

    public boolean hasClickListener() {
        return _onClickListener != null;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        _onClickListener = onClickListener;
    }

    public OnClickListener getClickListener() {
        return _onClickListener;
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    public void setPosition(int x, int y) {
        _x = x;
        _y = y;
    }

    public int getHeight() {
        return _height;
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        _onTouchListener = onTouchListener;
    }

    public boolean hasTouchListener() {
        return _onTouchListener != null;
    }

    public OnTouchListener getTouchListener() {
        return _onTouchListener;
    }

    public void setOnRightClickListener(OnClickListener onClickListener) {
        _onRightClickListener = onClickListener;
    }

    public boolean hasRightClickListener() {
        return _onRightClickListener != null;
    }

    public OnClickListener getRightClickListener() {
        return _onRightClickListener;
    }
}
