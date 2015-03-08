package org.smallbox.tales.engine.ui;

/**
 * Created by Alex on 06/11/2014.
 */
public interface OnTouchListener {
    void onTouchDown(int x, int y, int pointer, int button);
    void onTouchUp(int x, int y, int pointer, int button);
    void onClick(UITouchModel item);
}
