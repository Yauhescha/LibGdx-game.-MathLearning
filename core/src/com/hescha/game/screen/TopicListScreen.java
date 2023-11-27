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
import com.hescha.game.model.Topic;

import java.util.Map;
import java.util.stream.Collectors;

public class TopicListScreen extends ScreenAdapter {

    private Stage stage;
    private Skin skin;
    private final Game game;
    private java.util.List<Topic> selectedClassTopicList;
    private List<String> topicList;
    private ScrollPane scrollPane;

    public TopicListScreen(final Game game, java.util.List<Topic> selectedClassTopicList) {
        this.game = game;
        this.selectedClassTopicList = selectedClassTopicList;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin/style.json"));

        createTopicListUI();
    }

    private void createTopicListUI() {
        Map<String, java.util.List<Task>> topicTaskMap = selectedClassTopicList.stream()
                .collect(Collectors.toMap(
                        Topic::getTopic,
                        Topic::getListedTasks,
                        (tasks, tasks2) -> tasks2
                ));
        topicList = new List<>(skin);
        topicList.setItems(topicTaskMap.keySet().toArray(new String[0]));
        topicList.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selectedTopic = topicList.getSelected();

                game.setScreen(new TaskListScreen(game, topicTaskMap.get(selectedTopic)));
            }
        });

        scrollPane = new ScrollPane(topicList, skin);
        scrollPane.setFadeScrollBars(false);

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainScreen(game));
            }
        });

        Table table = new Table();
        table.setFillParent(true);
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

