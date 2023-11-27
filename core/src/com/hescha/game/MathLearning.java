package com.hescha.game;

import com.badlogic.gdx.Game;
import com.hescha.game.screen.MainScreen;

public class MathLearning extends Game {

    @Override
    public void create() {
        setScreen(new MainScreen(this));
    }
}
