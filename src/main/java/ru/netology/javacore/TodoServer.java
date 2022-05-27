package ru.netology.javacore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TodoServer {

    int port;
    private final Todos todos;

    public TodoServer(int port, Todos todos) {
        this.port = port;
        this.todos = todos;
    }

    public void start() throws IOException {
        System.out.println("Starting server at " + port + "...");
        try (ServerSocket serverSocket = new ServerSocket(port)){
            while (true) {
                try (
                        Socket clientSocket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
                ) {
                    System.out.println("New connection accepted");
                    out.println("Введите строку типа: \"type\": \"ADD\", \"task\": \"Название задачи\"");
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    Todos request = gson.fromJson(in.readLine(), Todos.class);
                    switch(request.getType()){
                        case "ADD": todos.addTask(request.getTask());
                            out.println(todos.getAllTasks());
                            continue;
                        case "REMOVE": todos.removeTask(request.getTask());
                            out.println(todos.getAllTasks());
                    }
                }
            }
        }
    }
}