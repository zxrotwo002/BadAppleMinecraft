package com.leohabrom.minecraft.plugins.badapplenew;

import com.leohabrom.minecraft.plugins.badapplenew.commands.ColorCommand;
import com.leohabrom.minecraft.plugins.badapplenew.commands.StartCommand;
import com.leohabrom.minecraft.plugins.badapplenew.commands.StopCommand;
import com.leohabrom.minecraft.plugins.badapplenew.video.Audio;
import com.leohabrom.minecraft.plugins.badapplenew.video.Video;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class BadAppleNew extends JavaPlugin {

    static BadAppleNew instance;

    public Video video;
    public Audio audio;
    private Material black = Material.BLACK_CONCRETE;
    private Material white = Material.WHITE_CONCRETE;

    public void setBlack(Material black) {
        this.black = black;
    }

    public void setWhite(Material white) {
        this.white = white;
    }

    public Material getBlack() {
        return black;
    }

    public Material getWhite() {
        return white;
    }

    public static BadAppleNew getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        video = new Video();
        audio = new Audio();
        // Plugin startup logic
        Objects.requireNonNull(getCommand("start")).setExecutor(new StartCommand());
        Objects.requireNonNull(getCommand("cancel")).setExecutor(new StopCommand());
        Objects.requireNonNull(getCommand("color")).setExecutor(new ColorCommand());
    }

    @Override
    public void onDisable() {
        instance = null;
        // Plugin shutdown logic
    }
}
