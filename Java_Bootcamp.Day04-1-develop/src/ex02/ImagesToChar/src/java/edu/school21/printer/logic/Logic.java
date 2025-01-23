package ImagesToChar.src.java.edu.school21.printer.logic;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Logic {
    public static void testFunc(String filePath, String whiteSymbol, String blackSymbol) throws IOException {
        BufferedImage image = ImageIO.read(new File(filePath));
        int height = image.getHeight();
        int wight = image.getWidth();
        StringBuilder str = new StringBuilder();


        ColoredPrinter printer = new ColoredPrinter.Builder(1,false)
                .foreground(Ansi.FColor.WHITE).background(Ansi.BColor.BLUE)   //setting format
                .build();

        for(int i = 0; i < height; i++){
            for(int j = 0; j < wight; j++){
                if(image.getRGB(j,i) == Color.WHITE.getRGB()){
                    printer.print(" ", Ansi.Attribute.NONE,
                            Ansi.FColor.NONE, Ansi.BColor.valueOf(whiteSymbol));
                } else {
                    printer.print(" ", Ansi.Attribute.NONE,
                            Ansi.FColor.NONE, Ansi.BColor.valueOf(blackSymbol));
                }
            }
            printer.print(" ", Ansi.Attribute.NONE,
                    Ansi.FColor.NONE, Ansi.BColor.valueOf("BLACK"));
            System.out.println();
        }
        printer.clear();
    }
}