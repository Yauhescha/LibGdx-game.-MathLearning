package com.hescha.game.model;

import lombok.Data;

@Data
public class Task {
    private String title;
    private String description;
    private String image; // Optional
    private String answer;
}
