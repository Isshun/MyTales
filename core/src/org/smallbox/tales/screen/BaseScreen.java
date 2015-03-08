package org.smallbox.tales.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import org.smallbox.tales.Game;
import org.smallbox.tales.Settings;
import org.smallbox.tales.engine.ui.OnKeyListener;
import org.smallbox.tales.engine.ui.OnTouchListener;
import org.smallbox.tales.engine.ui.UITouchModel;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Alex on 05/11/2014.
 */
public abstract class BaseScreen implements Screen {
    protected SpriteCache _cache;
    protected int _cacheId;
    protected InputProcessor  _inputAdapter;

    private List<UITouchModel> _uiItems;

    private OnKeyListener _onKeyListener;
    private OnTouchListener _onTouchListener;
    private boolean _hasBeenCreated;

    protected BaseScreen() {
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
                        item.getTouchListener().onTouchDown(x - item.getX(), (Settings.SCREEN_HEIGHT - y) - (Settings.SCREEN_HEIGHT - item.getHeight() - item.getY()), pointer, button);
                        //item.getTouchListener().onTouchDown(x - item.getX(), y - item.getY(), pointer, button);
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

                ListIterator li = _uiItems.listIterator(_uiItems.size());
                while (li.hasPrevious()) {
                    UITouchModel item = (UITouchModel)li.previous();

                    if (button == 0 && (item.hasClickListener() || item.hasTouchListener()) && item.isTouched(x, y)) {
                        if (item.hasClickListener()) {
                            item.getClickListener().onClick(x - item.getX(), (Settings.SCREEN_HEIGHT - y) - (Settings.SCREEN_HEIGHT - item.getHeight() - item.getY()));
                            //item.getClickListener().onClick(x - item.getX(), y - item.getY());
                        }
                        if (item.hasTouchListener()) {
                            item.getTouchListener().onTouchUp(x - item.getX(), (Settings.SCREEN_HEIGHT - y) - (Settings.SCREEN_HEIGHT - item.getHeight() - item.getY()), pointer, button);
                            //item.getTouchListener().onTouchUp(x - item.getX(), y - item.getY(), pointer, button);
                        }
                        return true;
                    }

                    if (button == 1 && item.hasRightClickListener() && item.isTouched(x, y)) {
                        item.getRightClickListener().onClick(x - item.getX(), (Settings.SCREEN_HEIGHT - y) - (Settings.SCREEN_HEIGHT - item.getHeight() - item.getY()));
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
        if (item == null) {
            throw new RuntimeException("Cannot add null item to UI Items");
        }
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
        if (_hasBeenCreated) {
            if (_cache != null) {
                _cache.begin();
                _cache.draw(_cacheId);
                _cache.end();
            }

            Game.batch.begin();
//            Game.batch.disableBlending();
//            backgroundSprite.draw(batch);
//            batch.enableBlending();

            onRefresh();

            for (UITouchModel item: _uiItems) {
                item.draw(Game.batch);
            }
            Game.batch.end();
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

    public void create() {
        onCreate();
        _hasBeenCreated = true;
    }
}
