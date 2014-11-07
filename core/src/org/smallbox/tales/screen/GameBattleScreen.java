package org.smallbox.tales.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import org.smallbox.tales.Game;
import org.smallbox.tales.Settings;
import org.smallbox.tales.model.BattleModel;
import org.smallbox.tales.model.FoeModel;
import org.smallbox.tales.model.PlayableCharacterModel;

/**
 * Created by Alex on 02/11/2014.
 */
public class GameBattleScreen implements Screen {
    private BattleModel _battle;

    private Texture _btA, _btB, _btC, _btD;
    private ControllerListener _controllerListener;
    private PlayableCharacterModel _player;

    public GameBattleScreen() {
        _battle = BattleModel.newInstance();
        _btA = new Texture("resources/bt_a.png");
        _btB = new Texture("resources/bt_a.png");
        _btC = new Texture("resources/bt_a.png");
        _btD = new Texture("resources/bt_a.png");

        _player = _battle.getCharacters().get(0);
        _player.setTarget(_battle.getFoes().get(0));

        _battle.getFoes().get(0).setTarget(_player);

        _controllerListener = new ControllerListener() {
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
                if (buttonCode == Settings.BUTTON_HIT) {
                    _player.hit();
                }
                return false;
            }

            @Override
            public boolean axisMoved(Controller controller, int axisCode, float value) {
                if (Math.abs(value) < 0.2) {
                    value = 0;
                }
                if (axisCode == 1) {
                    _player.move(value);
                    Gdx.app.log("mytales", "code: " + axisCode + ", value: " + value);
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
        };
    }

    public void update() {
        _battle.update();
    }

    @Override
    public void render(float delta) {
        int offsetY = 0;
        for (FoeModel foe: _battle.getFoes()) {
            foe.draw(Game.batch, 0, 240);
            Game.font.draw(Game.batch, foe.getName() + " (" + foe.getHealth() + "/" + foe.getHealthMax() + ")", 400, 200 - offsetY);

            offsetY += 20;
        }

        int offsetX = 20;
        for (PlayableCharacterModel player: _battle.getCharacters()) {
            player.draw(Game.batch, 0, 240);

            String health = "";
            for (int i = 0; i < player.getHealthPc(); i+=10) {
                health += "*";
            }
            Game.font.draw(Game.batch, health, offsetX, 180);

            String mana = "";
            for (int i = 0; i < player.getManaPc(); i+=10) {
                mana += "*";
            }
            Game.font.draw(Game.batch, mana, offsetX, 160);

            Game.batch.draw(_btA, offsetX + 40, 100);
            Game.batch.draw(_btB, offsetX, 60);
            Game.batch.draw(_btC, offsetX + 80, 60);
            Game.batch.draw(_btD, offsetX + 40, 20);

            offsetX += 180;
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

    public ControllerListener getControllerListener() {
        return _controllerListener;
    }
}
