package com.leohabrom.java.framestotxt;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Panel panel = new Panel();
        frame.add(panel);
        frame.setSize(500,400);
        frame.setVisible(true);
        panel.start();
    }
}

class Panel extends JPanel{
    Thread thread;
    boolean run;
    int count = 0;
    BufferedImage image = null;
    List<BufferedImage> images;

    public Panel() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                long oldTime = 0;
                int fps = 30;
                double timePerFrame = 1000.0 / fps;
                long timePerFrameRounded = Math.round(Math.floor(timePerFrame));
                double extraTime = 0;
                while (run) {
                    if (((System.currentTimeMillis() - oldTime) > timePerFrameRounded)) {
                        oldTime =  oldTime == 0 ? System.currentTimeMillis() : oldTime + timePerFrameRounded;
                        extraTime += timePerFrame - timePerFrameRounded;
                        if (extraTime >= 1) {
                            extraTime -= 1;
                            oldTime += 1;
                        }
                        update();
                    }
                }
            }
        });
        images = new ArrayList<>();
        for (File file : new File("res/frames").listFiles()) {
            File txt = new File(file.getParentFile().getParentFile(), "txt/"+ file.getName().replace(".jpg",".txt"));
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(txt));
                StringBuilder sb = new StringBuilder();
                BufferedImage image = ImageIO.read(file);
                for (int i = 0; i < image.getHeight(); i++) {
                    List<Integer> pixels = new ArrayList<>();
                    int count = 0;
                    boolean isWhite = true;
                    for (int j = 0; j < image.getWidth(); j++) {
                        Color color = new Color(image.getRGB(j,i));
                        if (color.getRed() != 0 && color.getRed() != 255) {
                            if (color.getRed() < 50) image.setRGB(j,i,Color.BLACK.getRGB());
                            else image.setRGB(j,i,Color.WHITE.getRGB());
                        }
                        color = new Color(image.getRGB(j,i));
                        if (color.getRed() == 255) {
                            if (isWhite) count++;
                            else {
                                pixels.add(count);
                                isWhite = true;
                                count = 1;
                            }
                        }
                        else {
                            if (!isWhite) count++;
                            else {
                                pixels.add(count);
                                isWhite = false;
                                count = 1;
                            }
                        }
                    }
                    pixels.add(count);
                    for (Integer integer : pixels) {
                        sb.append(integer).append(" ");
                    }
                    sb.append(System.lineSeparator());
                }
                writer.write(sb.toString());
                writer.close();
                System.out.println(file.getName());
                images.add(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(images.size());
    }

    private void update() {
        if (count < images.size()) {
            image = images.get(count);
            count ++;
        } else  image = null;
        System.out.println(count);
        repaint();
    }

    public void start() {
        run = true;
        thread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        g2.setColor(Color.white);
        g2.fillRect(0,0,480,360);
        g2.drawImage(image, 0,0,480,360,null);
    }
}
