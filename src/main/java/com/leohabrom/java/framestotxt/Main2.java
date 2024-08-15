package com.leohabrom.java.framestotxt;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Panel2 panel = new Panel2();
        frame.add(panel);
        frame.setSize(500,400);
        frame.setVisible(true);
        panel.start();
    }
}

class Panel2 extends JPanel {
    Thread thread;
    boolean run;
    int count = 0;
    BufferedImage image = null;
    List<List<String>> images;

    public Panel2() {

        image = new BufferedImage(480,360,1);


        File dir = new File("res/txt");
        images = new ArrayList<>();
        for(File file : dir.listFiles()) {
            List<String> strings = new ArrayList<>();
            System.out.println(file.getName());
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    strings.add(line);
                }
                images.add(strings);
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }




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
                        oldTime = oldTime == 0 ? System.currentTimeMillis() : oldTime + timePerFrameRounded;
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
    }

    private void update() {
        System.out.println(count);
        if (count < images.size()) {
            int y = 0;
            for (String line : images.get(count)) {
                int x =0;
                String[] pixels = line.split(" ");
                boolean white = true;
                for (String length : pixels) {
                    int j = Integer.parseInt(length);
                    for (int i = x; i < j + x; i++) {
                        image.setRGB(i, y, white ? Color.white.getRGB() : Color.BLACK.getRGB());
                    }
                    white = !white;
                    x += j;
                }
                y++;
            }
        } else image = null;
        if (count >= 6600) System.exit(0);
        count++;
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
        g2.fillRect(0, 0, 480, 360);
        g2.drawImage(image, 0, 0, 480, 360, null);
    }
}
