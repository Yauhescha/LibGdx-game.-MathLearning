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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.graphics.Texture;
import com.hescha.game.model.Task;

public class TaskDetailScreen extends ScreenAdapter {

    private Stage stage;
    private Skin skin;
    private final Game game;
    private Task selectedTask;

    public TaskDetailScreen(final Game game, Task selectedTask) {
        this.game = game;
        this.selectedTask = selectedTask;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin/style.json")); // Your UI skin file

        createTaskDetailUI();
    }

    private void createTaskDetailUI() {
        Label taskTitle = new Label(selectedTask.getTitle(), skin);
        Label taskDescription = new Label(selectedTask.getDescription(), skin);
        taskDescription.setWrap(true);

        TextField answerInput = new TextField("", skin);
        answerInput.setMessageText("Enter answer here...");

        TextButton submitButton = new TextButton("Submit", skin);
        submitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String userAnswer = answerInput.getText();
                if (userAnswer.equals(selectedTask.getAnswer())) {
                    String awardImagePath = "badlogic.jpg"; // Define the path to your award image
                    game.setScreen(new CongratulationsScreen(game, awardImagePath));
                } else {
                    // Handle incorrect answer (optional)
                }
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(taskTitle).padBottom(10);
        table.row();
        table.add(taskDescription).fillX().padBottom(20);
        table.row();

        if (selectedTask.getImage() != null && !selectedTask.getImage().isEmpty()) {
            Texture taskImage = new Texture(Gdx.files.internal(selectedTask.getImage()));
            Image image = new Image(taskImage);
            table.add(image).padBottom(20);
            table.row();
        }

        table.add(answerInput).width(300).padBottom(10);
        table.row();
        table.add(submitButton).width(200).padTop(10);

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

