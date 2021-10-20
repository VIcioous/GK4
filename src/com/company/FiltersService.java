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
    public BufferedImage getSobelEdgeImage(BufferedImage imageJPG) {
        int[][] edgeColors = new int[imageJPG.getHeight()][imageJPG.getWidth()];
        int maxGradient = -1;
        BufferedImage newImage = new BufferedImage(imageJPG.getWidth(),imageJPG.getHeight(),BufferedImage.TYPE_INT_BGR);
        for (int y =1; y< imageJPG.getHeight()-1;y++)
        {
            for (int x=1;x< imageJPG.getWidth()-1;x++)
            {
                int prevXPrevY = getGrayScale(imageJPG.getRGB(x-1,y-1));
                int prevXThisY = getGrayScale(imageJPG.getRGB(x-1,y));
                int prevXNextY = getGrayScale(imageJPG.getRGB(x-1,y+1));
                int thisXPrevY = getGrayScale(imageJPG.getRGB(x,y-1));
                int thisXNextY = getGrayScale(imageJPG.getRGB(x,y+1));
                int nextXPrevY = getGrayScale(imageJPG.getRGB(x+1,y-1));
                int nextXThisY = getGrayScale(imageJPG.getRGB(x+1,y));
                int nextXNextY = getGrayScale(imageJPG.getRGB(x+1,y+1));

                int gx = ((-1* prevXNextY) +(-2* prevXThisY) +(-1* prevXPrevY))
                        +(( nextXNextY) +(2* nextXThisY) +(1* nextXPrevY));
                int gy= ((-1* nextXPrevY) +(-2* thisXPrevY) +(-1* prevXPrevY))
                        +(( prevXNextY) +(2* thisXNextY) +(1* nextXNextY));

                double gval = Math.sqrt((gx * gx) + (gy * gy));
                int g = (int) gval;


                if(maxGradient < g) {
                    maxGradient = g;
                }

                edgeColors[y][x] = g;
            }
        }
        double scale = 255.0 / maxGradient;
        for (int i = 1; i< imageJPG.getHeight() - 1; i++) {
            for (int j = 1; j < imageJPG.getWidth() - 1; j++) {
                int edgeColor = edgeColors[i][j];
                edgeColor = (int) (edgeColor * scale);
                edgeColor = 0xff000000 | (edgeColor << 16) | (edgeColor << 8) | edgeColor;

                newImage.setRGB(j, i, edgeColor);
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

    private int  getGrayScale(int rgb) {
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = (rgb) & 0xff;


        int gray = (int)(0.2126 * r + 0.7152 * g + 0.0722 * b);


        return gray;
    }

    public BufferedImage getSharpenedImage(int sharpness,BufferedImage imageJPG) {
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

                int r = (sharpness* thisXThisY.getRed()) - prevXPrevY.getRed() -prevXThisY.getRed() - prevXNextY.getRed()
                        - thisXPrevY.getRed()-thisXNextY.getRed()- nextXPrevY.getRed()- nextXThisY.getRed()- nextXNextY.getRed();
                if(r<0) r=0;
                else
                {
                    r = r/9;
                }

                int g = (sharpness* thisXThisY.getGreen()) - prevXPrevY.getGreen() -prevXThisY.getGreen() - prevXNextY.getGreen()
                        - thisXPrevY.getGreen()-thisXNextY.getGreen()- nextXPrevY.getGreen()- nextXThisY.getGreen()- nextXNextY.getGreen();
                if(g<0) g=0;
                else
                {
                    g = g/9;
                }
                int b = (sharpness* thisXThisY.getBlue()) - prevXPrevY.getBlue() -prevXThisY.getBlue() - prevXNextY.getBlue()
                        - thisXPrevY.getBlue()-thisXNextY.getBlue()- nextXPrevY.getBlue()- nextXThisY.getBlue()- nextXNextY.getBlue();
                if(b<0) b=0;
                else
                {
                    b = b/9;
                }


                int color =(r << 16) | (g << 8) | b;
                newImage.setRGB(x,y,color);
            }
        }
        return newImage;
    }
    public BufferedImage getGaussianFilteredImage(BufferedImage imageJPG) {

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

                int r = (prevXPrevY.getRed()+2*prevXThisY.getRed()+prevXNextY.getRed()+
                        2*thisXPrevY.getRed()+4*thisXThisY.getRed()+2*thisXNextY.getRed()+
                        nextXPrevY.getRed()+2*nextXThisY.getRed()+nextXNextY.getRed())/16;
                int g = (prevXPrevY.getGreen()+2*prevXThisY.getGreen()+prevXNextY.getGreen()+
                        2*thisXPrevY.getGreen()+4*thisXThisY.getGreen()+2*thisXNextY.getGreen()+
                        nextXPrevY.getGreen()+2*nextXThisY.getGreen()+nextXNextY.getGreen())/16;
                int b = (prevXPrevY.getBlue()+2*prevXThisY.getBlue()+prevXNextY.getBlue()+
                        2*thisXPrevY.getBlue()+4*thisXThisY.getBlue()+2*thisXNextY.getBlue()+
                        nextXPrevY.getBlue()+2*nextXThisY.getBlue()+nextXNextY.getBlue())/16;


                int color =(r << 16) | (g << 8) | b;
                newImage.setRGB(x,y,color);
            }
        }
        return newImage;
    }
}
