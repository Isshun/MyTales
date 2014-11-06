package org.smallbox.tales.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.controllers.ControllerListener;
import org.smallbox.tales.Game;
import org.smallbox.tales.Settings;
import org.smallbox.tales.model.GroundModel;
import org.smallbox.tales.model.ItemModel;
import org.smallbox.tales.model.MapModel;
import org.smallbox.tales.model.factory.MapFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 05/11/2014.
 */
public abstract class BaseScreen implements Screen {
    protected InputProcessor  _inputAdapter;
    private boolean _hasBeenCreated = false;

    private List<UITouchModel> _uiItems;

    private OnKeyListener _onKeyListener;
    private OnTouchListener _onTouchListener;

    public BaseScreen() {
        _uiItems = new ArrayList<UITouchModel>();

        _inputAdapter = new InputProcessor() {

            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                Gdx.app.log("", "key: " + keycode);

                if (_onKeyListener != null) {
                    _onKeyListener.onKeyUp(keycode);
                }

                return true;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            public boolean touchDown (int x, int y, int pointer, int button) {
                for (UITouchModel item: _uiItems) {
                    if (item.hasTouchListener() && item.isTouched(x, y)) {
                        item.getTouchListener().onTouchDown(x - item.getX(), y - item.getY(), pointer, button);
                        return true;
                    }
                }

                if (_onTouchListener != null) {
                    _onTouchListener.onTouchDown(x, y, pointer, button);
                }

                return true;
            }

            public boolean touchUp (int x, int y, int pointer, int button) {
                Gdx.app.log("", "touch: " + x + "x" + y);

                for (UITouchModel item: _uiItems) {
                    if ((item.hasClickListener() || item.hasTouchListener()) && item.isTouched(x, y)) {
                        if (item.hasClickListener() && item.isTouched(x, y)) {
                            item.getClickListener().onClick(x - item.getX(), y - item.getY());
                        }
                        if (item.hasTouchListener() && item.isTouched(x, y)) {
                            item.getTouchListener().onTouchUp(x - item.getX(), y - item.getY(), pointer, button);
                        }
                        return true;
                    }
                }

                if (_onTouchListener != null) {
                    _onTouchListener.onTouchUp(x, y, pointer, button);
                }

                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        };


//        _inputAdapter = new InputAdapter() {
//            public boolean touchDown (int x, int y, int pointer, int button) {
//                return true;
//            }
//
//            public boolean touchUp (int x, int y, int pointer, int button) {
//                return true;
//            }
//        };
    }

    protected void setOnKeyListener(OnKeyListener onKeyListener) {
        _onKeyListener = onKeyListener;
    }

    protected void setOnTouchListener(OnTouchListener onTouchListener) {
        _onTouchListener = onTouchListener;
    }

    protected void addUIItem(UITouchModel item) {
        _uiItems.add(item);
    }

    protected void removeUIItems(List<UITouchModel> items) {
        _uiItems.removeAll(items);
    }

    public void update() {
        onUpdate();
    }

    @Override
    public void render(float delta) {
        if (!_hasBeenCreated) {
            onCreate();
            _hasBeenCreated = true;
        }
        onRefresh();

        for (UITouchModel item: _uiItems) {
            item.draw(Game.batch);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {

    }

    protected abstract void onUpdate();

    protected abstract void onCreate();

    protected abstract void onRefresh();

    public abstract ControllerListener getControllerListener();

    public InputProcessor getInputAdapter() {
        return _inputAdapter;
    }
}
