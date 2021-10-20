package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Canva extends JPanel {
    private final PointTransformationService pointTransformationService;
    private final FiltersService filtersService;


    private final JTextField pathFile = new JTextField();
    private final JTextField value = new JTextField();
    private final JButton addButton = new JButton();
    private final JButton subtractButton = new JButton();
    private final JButton multiplyButton = new JButton();
    private final JButton divideButton = new JButton();
    private final JButton brightButton = new JButton();
    private final JButton toGrayButton = new JButton();
    private final JButton readButton = new JButton();
    private final JButton boxBlurButton = new JButton();
    private final JButton medianFilterButton = new JButton();
    private final JButton sobelFilterButton = new JButton();
    private final JButton sharpenerButton = new JButton();
    private final JButton gaussButton = new JButton();
    private BufferedImage imageJPG;
    private final JPGService jpgService = new JPGService();

    Canva(
    ) {
        pointTransformationService = new PointTransformationService();
        filtersService= new FiltersService();
        this.setLayout(null);
        setButtons();
    }

    private void setButtons() {

        readButton.addActionListener(e -> readFile(pathFile.getText()));
        readButton.setText("Read");
        readButton.setBounds(30, 530, 100, 25);
        pathFile.setBounds(30, 500, 100, 25);
        value.setBounds(150, 500, 100, 25);

        addButton.setBounds(280, 500, 45, 25);
        addButton.addActionListener(e -> addValue(Integer.parseInt(value.getText()), imageJPG));

        subtractButton.setBounds(335, 500, 45, 25);
        subtractButton.addActionListener(e -> subtractValue(Integer.parseInt(value.getText()), imageJPG));
        multiplyButton.setBounds(410, 500, 45, 25);
        multiplyButton.addActionListener(e -> multiplyValue(Float.parseFloat(value.getText()), imageJPG));

        divideButton.setBounds(465, 500, 45, 25);
        divideButton.addActionListener(e -> divideValue(Integer.parseInt(value.getText()), imageJPG));

        brightButton.setBounds(530, 500, 100, 25);
        toGrayButton.setBounds(660, 500, 100, 25);
        toGrayButton.addActionListener(e -> toGrayImage(imageJPG));

        boxBlurButton.setBounds(150, 530, 100, 25);
        boxBlurButton.addActionListener(e -> boxBlur( imageJPG));
        medianFilterButton.setBounds(280, 530, 100, 25);
        medianFilterButton.addActionListener(e -> medianFilter( imageJPG));
        sobelFilterButton.setBounds(410, 530, 100, 25);
        sobelFilterButton.addActionListener(e -> sobelFilter( imageJPG));
        sharpenerButton.setBounds(530, 530, 100, 25);
        sharpenerButton.addActionListener(e -> highPassFilter(Integer.parseInt(value.getText()), imageJPG));
        gaussButton.setBounds(660, 530, 100, 25);
        gaussButton.addActionListener(e -> gaussFilter(imageJPG));





        addButton.setText("+");
        subtractButton.setText("-");
        multiplyButton.setText("*");
        divideButton.setText("/");
        brightButton.setText("Jasnosc");
        toGrayButton.setText("Szary");

        boxBlurButton.setText("Avg");
        medianFilterButton.setText("Median");
        sobelFilterButton.setText("Sobel");
        sharpenerButton.setText("Sharp");
        gaussButton.setText("Gauss");

        this.add(pathFile);
        this.add(value);
        this.add(addButton);
        this.add(subtractButton);
        this.add(multiplyButton);
        this.add(divideButton);
        this.add(brightButton);
        this.add(toGrayButton);
        this.add(readButton);
        this.add(boxBlurButton);
        this.add(medianFilterButton);
        this.add(sobelFilterButton);
        this.add(sharpenerButton);
        this.add(gaussButton);



    }

    private void gaussFilter(BufferedImage imageJPG) {
        BufferedImage image = filtersService.getGaussianFilteredImage(imageJPG);
        NewWindow newWindow = new NewWindow(image);
    }

    private void highPassFilter(int sharpness ,BufferedImage imageJPG) {
        BufferedImage image = filtersService.getSharpenedImage(sharpness,imageJPG);
        NewWindow newWindow = new NewWindow(image);
    }

    private void sobelFilter(BufferedImage imageJPG) {
        BufferedImage image = filtersService.getSobelEdgeImage(imageJPG);
        NewWindow newWindow = new NewWindow(image);
    }

    private void medianFilter(BufferedImage imageJPG) {
        BufferedImage image = filtersService.getMedianFiltered(imageJPG);
        NewWindow newWindow = new NewWindow(image);
    }

    private void boxBlur(BufferedImage imageJPG) {
        BufferedImage image = filtersService.getBoxBlurredImage(imageJPG);
        NewWindow newWindow = new NewWindow(image);
    }

    private void toGrayImage(BufferedImage imageJPG) {
        BufferedImage image = pointTransformationService.getGrayImage(imageJPG);
        NewWindow newWindow = new NewWindow(image);
    }

    private void addValue(int value, BufferedImage imageJPG) {
        BufferedImage image = pointTransformationService.getAddedImage(value, imageJPG);
        NewWindow newWindow = new NewWindow(image);
    }

    private void subtractValue(int value, BufferedImage imageJPG) {
        BufferedImage image = pointTransformationService.getSubtracted(value, imageJPG);
        NewWindow newWindow = new NewWindow(image);
    }

    private void multiplyValue(float value, BufferedImage imageJPG) {
        BufferedImage image = pointTransformationService.getMultiplied(value, imageJPG);
        NewWindow newWindow = new NewWindow(image);
    }

    private void divideValue(int value, BufferedImage imageJPG) {
        BufferedImage image = pointTransformationService.getDivided(value, imageJPG);
        NewWindow newWindow = new NewWindow(image);
    }

    private void readFile(String path) {
        imageJPG = jpgService.readJPG(path);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imageJPG, 0, 0, this);
        repaint();
    }
}
