package ImagesToChar.src.java.edu.school21.printer.logic;

import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Logic {
    public static void testFunc(String filePath, String whiteSymbol, String blackSymbol) throws IOException {
        BufferedImage image = ImageIO.read(new File(filePath));
        int height = image.getHeight();
        int wight = image.getWidth();
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < height; i++){
            for(int j = 0; j < wight; j++){
                if(image.getRGB(j,i) == Color.WHITE.getRGB()){
                    str.append(".");
                } else {
                    str.append("0");
                }
            }
            str.append("\n");
        }
        System.out.println(str);

    }
}
