package ru.rsreu.lab5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Input file names for execution: ");
        List<String> fileNames = Arrays.asList(in.nextLine().split(" "));
        in.close();
        //List<String> fileNames = Arrays.asList(args);

        List<WhitespacePosition> result = new FileReader().readFiles(fileNames);
        if (result == null) {
            System.out.println("Whitespaces not found");
        } else {
            result.forEach(x -> System.out.println(x.toString()));
        }

    }
}
