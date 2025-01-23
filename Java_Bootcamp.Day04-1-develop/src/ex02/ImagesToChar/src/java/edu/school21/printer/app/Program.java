package ImagesToChar.src.java.edu.school21.printer.app;

import ImagesToChar.src.java.edu.school21.printer.logic.Logic;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class Program {

    //int pattern;
    @Parameter(
            names = "--white",
            description = "whiteColor",
            required = true
    )
    private String whiteColor;
    @Parameter(
            names = "--black",
            description = "blackColor",
            required = true
    )
    private String blackColor;
    @Parameter(
            names = "--filepath",
            description = "filepath",
            required = true
    )
    private String filepath;
    public static void main(String[] args) {
        Program jArgs = new Program();
        JCommander programCmd = JCommander.newBuilder()
                .addObject(jArgs)
                .build();
        programCmd.parse(args);
        try {
            //String filepath = "/Users/vickonan/java/day04/Java_Bootcamp.Day04-1/src/ex00/ImagesToChar/it.bmp";
            //Logic.testFunc(args[2], args[0], args[1]);
            //String currentDir = System.getProperty("user.dir");
            //File directory = new File(currentDir,"src/resources/it.bmp");
            //System.out.println(directory.getAbsolutePath());
            //Logic.testFunc(args[2], args[0], args[1]);
            Logic.testFunc(jArgs.filepath, jArgs.whiteColor, jArgs.blackColor);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());;
        }
    }
}