package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class FiltersService {
    public BufferedImage getBoxBlurredImage(BufferedImage imageJPG) {

        BufferedImage newImage = new BufferedImage(imageJPG.getWidth(),imageJPG.getHeight(),BufferedImage.TYPE_INT_BGR);
        for (int y =1; y< imageJPG.getHeight()-1;y++)
        {
            for (int x=1;x< imageJPG.getWidth()-1;x++)
            {
                Color prevXPrevY = new Color(imageJPG.getRGB(x-1,y-1));
                Color prevXThisY = new Color(imageJPG.getRGB(x-1,y));
                Color prevXNextY = new Color(imageJPG.getRGB(x-1,y+1));
                Color thisXPrevY = new Color(imageJPG.getRGB(x,y-1));
                Color thisXThisY = new Color(imageJPG.getRGB(x,y));
                Color thisXNextY = new Color(imageJPG.getRGB(x,y+1));
                Color nextXPrevY = new Color(imageJPG.getRGB(x+1,y-1));
                Color nextXThisY = new Color(imageJPG.getRGB(x+1,y));
                Color nextXNextY = new Color(imageJPG.getRGB(x+1,y+1));

                int r = (prevXPrevY.getRed()+prevXThisY.getRed()+prevXNextY.getRed()+
                        thisXPrevY.getRed()+thisXThisY.getRed()+thisXNextY.getRed()+
                        nextXPrevY.getRed()+nextXThisY.getRed()+nextXNextY.getRed())/9;
                int g = (prevXPrevY.getGreen()+prevXThisY.getGreen()+prevXNextY.getGreen()+
                        thisXPrevY.getGreen()+thisXThisY.getGreen()+thisXNextY.getGreen()+
                        nextXPrevY.getGreen()+nextXThisY.getGreen()+nextXNextY.getGreen())/9;
                int b = (prevXPrevY.getBlue()+prevXThisY.getBlue()+prevXNextY.getBlue()+
                        thisXPrevY.getBlue()+thisXThisY.getBlue()+thisXNextY.getBlue()+
                        nextXPrevY.getBlue()+nextXThisY.getBlue()+nextXNextY.getBlue())/9;


                int color =(r << 16) | (g << 8) | b;
                newImage.setRGB(x,y,color);
            }
        }
        return newImage;
    }

    public BufferedImage getMedianFiltered(BufferedImage imageJPG) {
        BufferedImage newImage = new BufferedImage(imageJPG.getWidth(),imageJPG.getHeight(),BufferedImage.TYPE_INT_BGR);
        for (int y =1; y< imageJPG.getHeight()-1;y++)
        {
            for (int x=1;x< imageJPG.getWidth()-1;x++)
            {
                Color prevXPrevY = new Color(imageJPG.getRGB(x-1,y-1));
                Color prevXThisY = new Color(imageJPG.getRGB(x-1,y));
                Color prevXNextY = new Color(imageJPG.getRGB(x-1,y+1));
                Color thisXPrevY = new Color(imageJPG.getRGB(x,y-1));
                Color thisXThisY = new Color(imageJPG.getRGB(x,y));
                Color thisXNextY = new Color(imageJPG.getRGB(x,y+1));
                Color nextXPrevY = new Color(imageJPG.getRGB(x+1,y-1));
                Color nextXThisY = new Color(imageJPG.getRGB(x+1,y));
                Color nextXNextY = new Color(imageJPG.getRGB(x+1,y+1));

                int r = median(prevXPrevY.getRed(),prevXThisY.getRed(),prevXNextY.getRed(),
                        thisXPrevY.getRed(),thisXThisY.getRed(),thisXNextY.getRed(),
                        nextXPrevY.getRed(),nextXThisY.getRed(),nextXNextY.getRed());
                int g = median(prevXPrevY.getGreen(),prevXThisY.getGreen(),prevXNextY.getGreen(),
                        thisXPrevY.getGreen(),thisXThisY.getGreen(),thisXNextY.getGreen(),
                        nextXPrevY.getGreen(),nextXThisY.getGreen(),nextXNextY.getGreen());
                int b = median(prevXPrevY.getBlue(),prevXThisY.getBlue(),prevXNextY.getBlue(),
                        thisXPrevY.getBlue(),thisXThisY.getBlue(),thisXNextY.getBlue(),
                        nextXPrevY.getBlue(),nextXThisY.getBlue(),nextXNextY.getBlue());


                int color =(r << 16) | (g << 8) | b;
                newImage.setRGB(x,y,color);
            }
        }
        return newImage;
    }

    private int median(int prevXPrevY,
                       int prevXThisY,
                       int prevXNextY,
                       int thisXPrevY,
                       int thisXThisY,
                       int thisXNextY,
                       int nextXPrevY,
                       int nextXthisY,
                       int nextXnextY) {
        int[] array= new int[]{prevXNextY,prevXPrevY,prevXThisY,nextXnextY,nextXthisY,nextXPrevY,thisXNextY,thisXPrevY,thisXThisY};
        Arrays.sort(array);


        return array[4];
    }
}
