package org.innopolis.devops.group4;

public class App {

    private final String message = "Hello World!";

    public App() {}

    public static void main(String[] args) {
        System.out.println(new App().getMessage());
    }

    public String getMessage() {
        return message;
    }
}
