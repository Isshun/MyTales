package org.smallbox.tales.screen;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import org.smallbox.tales.Game;
import org.smallbox.tales.model.AreaModel;
import org.smallbox.tales.model.GroundModel;

/**
 * Created by Alex on 05/11/2014.
 */
public class MapScreen extends BaseScreen {

    private AreaModel[][][] _areas;

    private ControllerListener _controllerListener;

    @Override
    protected void onCreate() {
        _areas = new AreaModel[5][10][10];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                _areas[0][x][y] = new GroundModel("grounds", "37.png", 6);
            }
        }

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
                return false;
            }

            @Override
            public boolean axisMoved(Controller controller, int axisCode, float value) {
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

    @Override
    protected void onUpdate() {

    }

    protected void onRefresh() {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                _areas[0][x][y].draw(Game.batch, x, y);
            }
        }
    }

    public ControllerListener getControllerListener() {
        return _controllerListener;
    }

}
