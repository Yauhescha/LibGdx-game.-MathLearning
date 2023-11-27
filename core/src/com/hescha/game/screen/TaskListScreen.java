package com.hescha.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.hescha.game.model.Task;

import java.util.stream.Collectors;

public class TaskListScreen extends ScreenAdapter {

    private Stage stage;
    private Skin skin;
    private final Game game;
    private java.util.List<Task> selectedTopic;
    private List<String> taskListWidget;
    private ScrollPane scrollPane;

    public TaskListScreen(final Game game, java.util.List<Task> selectedTopic) {
        this.game = game;
        this.selectedTopic = selectedTopic;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin/style.json")); // Your UI skin file

        createTaskListUI();
    }

    private void createTaskListUI() {
        taskListWidget = new List<>(skin);
        String[] strings = selectedTopic.stream()
                .map(Task::getTitle)
                .toArray(String[]::new);
        taskListWidget.setItems(strings);
        taskListWidget.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int selectedIndex = taskListWidget.getSelectedIndex();
                game.setScreen(new TaskDetailScreen(game, selectedTopic.get(selectedIndex)));
            }
        });

        scrollPane = new ScrollPane(taskListWidget, skin);
        scrollPane.setFadeScrollBars(false);

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Navigate back to TopicListScreen or another appropriate screen
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(new Label("Tasks for Topic: " + selectedTopic, skin)).colspan(2).padBottom(10);
        table.row();
        table.add(scrollPane).width(400).height(300).colspan(2).padBottom(20);
        table.row();
        table.add(backButton).width(200);

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

