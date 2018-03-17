package handler;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import javax.imageio.ImageIO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Random;

/*
A class to listen events and maps
 */

public class Tools {

	public static final String propertiesFile = "/items/recipes.dll";
    private static HashMap<String, BufferedImage> images = new HashMap<>();
    private static Random random = new Random();
	
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
		for (int i = 0; i < num; i++) {
			images[i] = image.getSubimage(i * width, 0, width, image.getHeight());
        }
		return images;
	}
    
    public static BufferedImage getImage(String file) {
        BufferedImage image = null;
		if (images.containsKey(file)) image = images.get(file);
		else {
	        try {
	            image = ImageIO.read(Tools.class.getResourceAsStream(file));
	            images.put(file, image);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
        return image;
    }

    public static BufferedImage blur(BufferedImage image) {
    	int radius = 11;
        int size = radius * 2 + 1;
        float weight = 1.0f / (size * size);
        float[] data = new float[size * size];

        for (int i = 0; i < data.length; i++) {
            data[i] = weight;
        }

        Kernel kernel = new Kernel(size, size, data);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null);
        //tbi is BufferedImage
        BufferedImage i = op.filter(image, null);
    	return i;
    }
    
    public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
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

    public static void writeToFile(String file, Object[] data) {

    }
    
    /* ----------------------------- Maths & Conversions ----------------------------- */
   
   public static int[] StringArrayToIntArray(String[] strings) {
       int[] ints = new int[strings.length];
       for (int i = 0; i < strings.length; i++) {
           ints[i] = Integer.parseInt(strings[i]);
       }
       return ints;
   }
   
   public static double getSecs(double time) {
	   return time * 60;
   }
   
   public static double round(double value, int precision,boolean up) {
	   int scale = (int) Math.pow(10, precision);
	   if(up){
	       return (double) Math.ceil(value * scale) / scale;
	   }else{
	      return (double) Math.floor(value * scale) / scale;
	   }
   }

   public static int getRandom(int min, int max) {
       return random.nextInt(max-min) + min;
   }
    
}
