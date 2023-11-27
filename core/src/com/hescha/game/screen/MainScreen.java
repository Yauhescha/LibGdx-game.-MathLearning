package com.hescha.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.hescha.game.model.Task;
import com.hescha.game.model.Topic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class MainScreen extends ScreenAdapter {

    private Stage stage;
    private Skin skin;
    private SelectBox<String> classSelectBox;
    public static HashMap<String, List<Topic>> classDataTopics;
    public static HashMap<String, List<Task>> classDataTasks;
    private final Game game;

    public MainScreen(final Game game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin/style.json"));

        classDataTopics = new HashMap<>();
        classDataTasks = new HashMap<>();
        loadClassData();

        createClassSelectionUI();
    }

    private void loadClassData() {
        Json json = new Json();
        for (int i = 1; i <= 1; i++) {
//        for (int i = 1; i <= 11; i++) {
            FileHandle fileHandle = Gdx.files.internal(i + "/data.json");
            if (fileHandle.exists()) {
                Topic[] topics = json.fromJson(Topic[].class, fileHandle);
                classDataTopics.putIfAbsent(i + "", Arrays.asList(topics));
                for (Topic topic : topics) {
                    String topic1 = topic.getTopic();
                    Task[] tasks = topic.getTasks();
                    List<Task> value = Arrays.asList(tasks);
                    classDataTasks.putIfAbsent(topic1, value);
                }
            }
        }
    }

    private void createClassSelectionUI() {
        classSelectBox = new SelectBox<>(skin);
        String[] newItems = classDataTopics.keySet().toArray(new String[0]);
        classSelectBox.setItems(newItems);
        classSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selectedClass = classSelectBox.getSelected();
                game.setScreen(new TopicListScreen(game,  classDataTopics.get(selectedClass)));
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(classSelectBox).padTop(10).row();

        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);
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

