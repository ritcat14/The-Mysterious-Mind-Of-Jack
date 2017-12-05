package handler;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import javax.imageio.ImageIO;

import java.io.BufferedReader;
import java.io.InputStreamReader;


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
    
    public static BufferedImage getScreenShot(Component component) {
    	BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_RGB);
    	// call the Component's paint method, using
    	// the Graphics object of the image.
    	component.paint(image.getGraphics()); // alternately use .printAll(..)
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
   
   public static int getSecs(int time) {
	   return time * 120;
   }
    
}
