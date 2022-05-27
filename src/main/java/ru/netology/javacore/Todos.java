package ru.netology.javacore;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Todos {
    private String type;
    private String task;

    private List<String> todos = new ArrayList<>();

    public String getType(){
        return type;
    }
    public String getTask(){
        return task;
    }

    public void addTask(String task) {
        todos.add(task);
    }

    public void removeTask(String task) {
        todos.remove(task);
    }

    public String getAllTasks() {
        return todos.stream()
                .sorted()
                .collect(Collectors.joining(" "));
    }
}
