package org.smallbox.tales.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.math.Vector3;
import org.smallbox.tales.Game;
import org.smallbox.tales.Settings;
import org.smallbox.tales.model.CharacterModel;
import org.smallbox.tales.model.MapModel;
import org.smallbox.tales.engine.ui.OnKeyListener;

/**
 * Created by Alex on 05/11/2014.
 */
public class GameMapScreen extends BaseScreen {

    private CharacterModel _player;
    private final MapModel _map;

    private ControllerListener _controllerListener;

    public GameMapScreen(MapModel map) {
        _map = map;
    }

    @Override
    protected void onCreate() {
        _cache = new SpriteCache();

        _cache.beginCache();
        for (int z = 0; z < 5; z++) {
            for (int x = 0; x < 100; x++) {
                for (int y = 0; y < 100; y++) {
                    if (_map.hasObject(z, x, y)) {
                        _map.getObject(z, x, y).draw(_cache, x * Settings.TILE_SIZE, y * Settings.TILE_SIZE);
                    }
                }
            }
        }
        _cacheId = _cache.endCache();

        _player = new CharacterModel() {
            @Override
            protected void onUpdate() {

            }
        };

        if (_map.getMusic() != null) {
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("musics/" + _map.getMusic()));
            sound.play(1.0f);
        }

        setOnKeyListener(new OnKeyListener() {
            @Override
            public void onKeyUp(int keycode) {
                switch (keycode) {
                    case Input.Keys.ESCAPE: Game.getInstance().loadScreen(new EditorMapChooserScreen()); break;
                }
            }

            @Override
            public void onKeyDown(int keycode) {

            }
        });

        setController(new ControllerListener() {
            @Override
            public void connected(Controller controller) {

            }

            @Override
            public void disconnected(Controller controller) {

            }

            @Override
            public boolean buttonDown(Controller controller, int buttonCode) {
                return false;
            }

            @Override
            public boolean buttonUp(Controller controller, int buttonCode) {
                return false;
            }

            @Override
            public boolean axisMoved(Controller controller, int axisCode, float value) {
                switch (axisCode) {
                    case 0: _player.setVelocityY(value < 0.2 && value > -0.2 ? 0 : -value); return true;
                    case 1: _player.setVelocityX(value < 0.2 && value > -0.2 ? 0 : value); return true;
                }

                return false;
            }

            @Override
            public boolean povMoved(Controller controller, int povCode, PovDirection value) {
                return false;
            }

            @Override
            public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
                return false;
            }

            @Override
            public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
                return false;
            }

            @Override
            public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
                return false;
            }
        });
    }

    private void setController(ControllerListener controllerListener) {
        Game.getInstance().setController(controllerListener);
    }

    @Override
    protected void onUpdate() {
        if (_player != null) {
            _player.update(_map);
            _cache.getProjectionMatrix().setToOrtho2D(- Settings.SCREEN_WIDTH / 2 + _player.getPosX(), - Settings.SCREEN_HEIGHT / 2 + _player.getPosY(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    protected void onRefresh() {
        if (_player != null) {
            _player.draw(Game.batch, Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
        }
    }

    public ControllerListener getControllerListener() {
        return _controllerListener;
    }

}
