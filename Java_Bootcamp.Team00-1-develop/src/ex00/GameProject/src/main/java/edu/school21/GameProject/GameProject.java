package edu.school21.GameProject;

import com.beust.jcommander.JCommander;

public class GameProject {
    public static void main(String[] args) {
        Args commandLineArgs = new Args();
        JCommander jCommander = JCommander.newBuilder().addObject(commandLineArgs).build();
        jCommander.parse(args);
        PropertiesClass propertiesClass = new PropertiesClass();
        if(commandLineArgs.getSize()* commandLineArgs.getSize() <= commandLineArgs.getEnemiesCount()
        + commandLineArgs.getWallsCount() + 2){
            System.out.println("wrong input data");
            System.exit(0);
        }
        FieldClass fieldClass =
                new FieldClass(
                        commandLineArgs.getEnemiesCount(),
                        commandLineArgs.getWallsCount(),
                        commandLineArgs.getSize(),
                        propertiesClass);
        if(commandLineArgs.getProfile().equals("production")) {
            fieldClass.mainFunc();
        } else if(commandLineArgs.getProfile().equals("dev")){
            fieldClass.devMainFunc();
        }
    }
}
