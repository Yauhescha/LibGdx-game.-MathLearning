package com.hescha.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.graphics.Texture;

public class CongratulationsScreen extends ScreenAdapter {

    private Stage stage;
    private Skin skin;
    private final Game game;
    private String awardImagePath;

    public CongratulationsScreen(final Game game, String awardImagePath) {
        this.game = game;
        this.awardImagePath = awardImagePath;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin/style.json")); // Your UI skin file

        createCongratulationsUI();
    }

    private void createCongratulationsUI() {
        Label titleLabel = new Label("Congratulations!", skin);

        Image awardImage = new Image(new Texture(Gdx.files.internal(awardImagePath)));

        TextButton nextTaskButton = new TextButton("Next Task", skin);
        nextTaskButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Navigate to the next task or screen
            }
        });

        TextButton returnButton = new TextButton("Return to Main Menu", skin);
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainScreen(game)); // Navigate back to MainScreen
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(titleLabel).padBottom(20);
        table.row();
        table.add(awardImage).size(150, 150).padBottom(20);
        table.row();
        table.add(nextTaskButton).width(200).padBottom(10);
        table.row();
        table.add(returnButton).width(200);

        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}

