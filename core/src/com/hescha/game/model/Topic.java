package com.hescha.game.model;

import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class Topic {
    private String topic;
    private Task[] tasks;

    public List<Task> getListedTasks() {
        return Arrays.asList(tasks);
    }
}