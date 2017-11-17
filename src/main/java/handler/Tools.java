package handler;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;


public class Tools {
	
    /* ------------------------------- Image Handling -------------------------------- */
   
	public static BufferedImage[] splitImage(String imageFile, int num, int width) {
		BufferedImage[] images = new BufferedImage[num];
		BufferedImage image = getImage(imageFile);
		for (int i = 0; i < images.length; i++) {
			images[i] = image.getSubimage(i * width, 0, width, image.getHeight());
        }
		return images;
	}
	
	public static BufferedImage[] splitImage(BufferedImage image, int num, int width) {
		BufferedImage[] images = new BufferedImage[num];
		for (int i = 0; i < images.length; i++) {
			images[i] = image.getSubimage(i * width, 0, width, image.getHeight());
        }
		return images;
	}
    
    public static BufferedImage getImage(String file) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(Tools.class.getResourceAsStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    
    /* -------------------------------- File Handling -------------------------------- */
    
    public static String[] getData(String file) {
        String line;
        int lineNum = getLineNum(file);
        String[] data = new String[lineNum];
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Tools.class.getResourceAsStream(file)));
			int i = -1;
            while ((line = bufferedReader.readLine()) != null) {
              i++;
              if (i == lineNum) break;
              data[i] = line;
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    
    public static int getLineNum(String file) {
        int num = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Tools.class.getResourceAsStream(file)));
            while (bufferedReader.readLine() != null) num++;
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return num;
    }
    
    /* ----------------------------- Maths & Conversions ----------------------------- */
   
   public static int[] StringArrayToIntArray(String[] strings) {
       int[] ints = new int[strings.length];
       for (int i = 0; i < strings.length; i++) {
           ints[i] = Integer.parseInt(strings[i]);
       }
       return ints;
   }
    
}
