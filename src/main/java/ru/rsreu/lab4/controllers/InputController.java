package ru.rsreu.lab4.controllers;

import ru.rsreu.lab4.commands.CommandType;

public class InputController {

    public CommandType getCommand(String inputCommand) {
        String[] input = inputCommand.split(" ");
        switch (input[0]) {
            case "start":
                return CommandType.START;
            case "stop":
                return CommandType.STOP;
            case "await":
                return CommandType.AWAIT;
            case "exit":
                return CommandType.EXIT;
            default:
                return CommandType.UNKNOWN;
        }
    }

    public Long getId(String input) {
        try {
            return Long.parseLong(this.parseId(input));
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    private String parseId(String input) {
        try {
             return input.split(" ")[1];
        } catch (ArrayIndexOutOfBoundsException exception) {
            return "";
        }
    }

    public boolean isCorrectId(Long id) {
        if (id == null) {
            System.out.println("Incorrect input id");
            return false;
        }
        return true;
    }
}
