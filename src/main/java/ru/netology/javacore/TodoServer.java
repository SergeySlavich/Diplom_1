package ru.netology.javacore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class TodoServer {

    int port;
    private Todos todos;

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
                    Map request = gson.fromJson(in.readLine(), Map.class);
                    switch(request.get("type").toString()){
                        case "ADD": todos.addTask(request.get("task").toString());
                            out.println(todos.getAllTasks());
                            continue;
                        case "REMOVE": todos.removeTask(request.get("task").toString());
                            out.println(todos.getAllTasks());
                    }
                }
            }
        }
    }
}