package com.company;




import java.awt.*;
import java.awt.image.BufferedImage;




public class PointTransformationService {
    public BufferedImage getAddedImage(int parseInt, BufferedImage imageJPG) {

        for (int y =0; y< imageJPG.getHeight();y++)
        {
            for (int x=0;x< imageJPG.getWidth();x++)
            {
                Color c = new Color(imageJPG.getRGB(x,y));
                int r = c.getRed()+parseInt;
                if(r>255) r=255;
                int g = c.getGreen()+parseInt;
                if(g>255) g=255;
                int b = c.getBlue()+parseInt;
                if(b>255) b=255;

                int color =(r << 16) | (g << 8) | b;
                imageJPG.setRGB(x,y,color);
            }
        }
        return imageJPG;
    }
    public BufferedImage getSubtracted(int parseInt, BufferedImage imageJPG) {

        for (int y =0; y< imageJPG.getHeight();y++)
        {
            for (int x=0;x< imageJPG.getWidth();x++)
            {
                Color c = new Color(imageJPG.getRGB(x,y));
                int r = c.getRed()-parseInt;
                if(r<0) r=0;
                int g = c.getGreen()-parseInt;
                if(r<0) r=0;
                int b = c.getBlue()-parseInt;
                if(r<0) r=0;

                int color =(r << 16) | (g << 8) | b;
                imageJPG.setRGB(x,y,color);
            }
        }
        return imageJPG;
    }

    public BufferedImage getMultiplied(float value, BufferedImage imageJPG) {
        for (int y =0; y< imageJPG.getHeight();y++)
        {
            for (int x=0;x< imageJPG.getWidth();x++)
            {
                Color c = new Color(imageJPG.getRGB(x,y));
                int r = c.getRed()*(int)value;
                if(r>255) r=255;
                int g = c.getGreen()*(int)value;
                if(r>255) r=255;
                int b = c.getBlue()*(int)value;
                if(r>255) r=255;

                int color =(r << 16) | (g << 8) | b;
                imageJPG.setRGB(x,y,color);
            }
        }
        return imageJPG;
    }
    public BufferedImage getDivided(int value, BufferedImage imageJPG) {
        if(value ==0) throw new IllegalArgumentException("Dzielenie przez zero, kmiocie");
        for (int y =0; y< imageJPG.getHeight();y++)
        {
            for (int x=0;x< imageJPG.getWidth();x++)
            {
                Color c = new Color(imageJPG.getRGB(x,y));
                int r = c.getRed()/value;
                if(r<0) r=0;
                int g = c.getGreen()/value;
                if(r<0) r=0;
                int b = c.getBlue()/value;
                if(r<0) r=0;

                int color =(r << 16) | (g << 8) | b;
                imageJPG.setRGB(x,y,color);
            }
        }
        return imageJPG;
    }

    public BufferedImage getGrayImage(BufferedImage imageJPG) {
        for (int y =0; y< imageJPG.getHeight();y++)
        {
            for (int x=0;x< imageJPG.getWidth();x++)
            {
                int rgb = imageJPG.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb & 0xFF);

                double rr = Math.pow(r / 255.0, 2.2);
                double gg = Math.pow(g / 255.0, 2.2);
                double bb = Math.pow(b / 255.0, 2.2);


                double lum = 0.2126 * rr + 0.7152 * gg + 0.0722 * bb;

                int grayLevel = (int) (255.0 * Math.pow(lum, 1.0 / 2.2));
                int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;
                imageJPG.setRGB(x, y, gray);
            }
        }
        return imageJPG;
    }
}
