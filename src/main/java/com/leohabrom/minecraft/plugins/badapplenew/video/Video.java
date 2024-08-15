package com.leohabrom.minecraft.plugins.badapplenew.video;

import com.leohabrom.minecraft.plugins.badapplenew.BadAppleNew;
import org.bukkit.Bukkit;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Video {
    private boolean[][] frame = new boolean[480][360];
    private boolean run;
    private List<List<String>> images;
    private Thread thread;
    private int count;

    public boolean[][] getFrame() {
        return frame;
    }

    public Video() {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(BadAppleNew.getInstance().getResource("frames.txt")));
            String line;
            while ((line = reader.readLine())!= null) {
                sb.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int count = 0;
        images = new ArrayList<>();
        for (String image : sb.toString().split(";")) {
            System.out.println(count++);
            images.add(Arrays.stream(image.split(System.lineSeparator())).collect(Collectors.toList()));
        }
    }

    private void update() {
        System.out.println(count);
        if (count < images.size()) {
            int y = 0;
            for (String line : images.get(count)) {
                int x = 0;
                String[] pixels = line.split(" ");
                boolean white = true;
                for (String length : pixels) {
                    int j = Integer.parseInt(length);
                    for (int i = x; i < j + x; i++) {
                        frame[i][y] = white;
                    }
                    white = !white;
                    x += j;
                }
                y++;
            }
        } else frame = new boolean[480][360];
        if (count >= 6700) {run = false; BadAppleNew.getInstance().audio.stop();}
        count++;
    }

    public void stop() {
        run = false;
    }

    public void start() {
        if(run) run = false;
        thread = null;
        count = 0;
        frame = new boolean[480][360];
        run = true;
        thread = new Thread(() -> {
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
        });
        thread.start();
    }
}
