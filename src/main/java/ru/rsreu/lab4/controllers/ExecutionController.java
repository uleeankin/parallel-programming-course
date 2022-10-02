package ru.rsreu.lab4.controllers;

import ru.rsreu.lab4.commands.CommandManager;

public class ExecutionController {

    private final CommandManager commander = new CommandManager();
    private final InputController inputController = new InputController();


    public ExecutionController() {

    }

    public boolean execute(String inputCommand) {
        Long taskId;
        switch (this.inputController.getCommand(inputCommand)) {
            case START:
                taskId = this.inputController.getId(inputCommand);
                if (!this.inputController.isCorrectId(taskId)) {
                    break;
                }
                String threadName = commander.start(taskId);
                System.out.printf("Task with id %s was started\n", threadName);
                return true;
            case STOP:
                taskId = this.inputController.getId(inputCommand);
                if (!this.inputController.isCorrectId(taskId)) {
                    break;
                }
                commander.stop(taskId);
                return true;
            case AWAIT:
                taskId = this.inputController.getId(inputCommand);
                if (!this.inputController.isCorrectId(taskId)) {
                    break;
                }
                commander.await(taskId);
                return true;
            case EXIT:
                return commander.exit();
            case UNKNOWN:
                System.out.println("Unknown command");
        }

        return true;
    }


}
