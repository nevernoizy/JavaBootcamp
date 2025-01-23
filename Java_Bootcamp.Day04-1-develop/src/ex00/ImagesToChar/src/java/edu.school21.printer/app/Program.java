package ImagesToChar.src.java.edu.school21.printer.app;

import ImagesToChar.src.java.edu.school21.printer.logic.Logic;

import java.io.IOException;

public class Program {
    public static void main(String[] args) {
        try {
            //String filepath = "/Users/vickonan/java/day04/Java_Bootcamp.Day04-1/src/ex00/ImagesToChar/it.bmp";
            Logic.testFunc(args[2], args[0], args[1]);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());;
        }
    }
}
