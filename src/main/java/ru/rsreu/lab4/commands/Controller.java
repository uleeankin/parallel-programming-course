package ru.rsreu.lab4.commands;

public class Controller {

    private Commander commander = new Commander();
    private String command;
    private int n = 0;

    public Controller() {

    }

    public void control(String inputCommand) {
        this.getCommand(inputCommand);
        switch (this.command) {
            case "start":
                commander.start(this.n);
                break;
            case "stop":
                commander.stop(this.n);
                break;
            case "await":
                commander.await(this.n);
                break;
            case "exit":
                commander.exit();
                break;
        }
    }

    private void getCommand(String inputCommand) {
        String[] input = inputCommand.split(" ");
        this.command = input[0];
        if (input.length == 2) {
            this.n = Integer.parseInt(input[1]);
        }
    }
}
