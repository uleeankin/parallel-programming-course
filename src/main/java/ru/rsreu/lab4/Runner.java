package ru.rsreu.lab4;

import ru.rsreu.lab4.controllers.ExecutionController;

import java.util.Scanner;

public class Runner {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        ExecutionController controller = new ExecutionController();

        boolean execution = true;

        while (execution) {
            execution = controller.execute(in.nextLine());
        }

        in.close();
    }
}
