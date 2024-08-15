package com.leohabrom.minecraft.plugins.badapplenew.video;

import com.leohabrom.minecraft.plugins.badapplenew.BadAppleNew;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Audio {
    Clip clip = null;
    public Audio() {
        try {
            File audio = new File(BadAppleNew.getInstance().getDataFolder(), "badapple.wav");
            if (!audio.exists()) {
                InputStream in = BadAppleNew.getInstance().getResource("badapple/badapple.wav");
                try (FileOutputStream out = new FileOutputStream(audio)) {
                    //copy stream
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                }
            }
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(audio));
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void start() {
        if (clip.isRunning()) clip.stop();
        clip.setMicrosecondPosition(0);
        clip.start();
    }
    public void stop() {
        clip.stop();
        clip.setMicrosecondPosition(0);
    }
}
