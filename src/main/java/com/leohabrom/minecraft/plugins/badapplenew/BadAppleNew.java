package com.leohabrom.minecraft.plugins.badapplenew;

import com.leohabrom.minecraft.plugins.badapplenew.commands.StartCommand;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class BadAppleNew extends JavaPlugin {

    static BadAppleNew instance;

    public static BadAppleNew getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        Objects.requireNonNull(getCommand("start")).setExecutor(new StartCommand());
    }

    @Override
    public void onDisable() {
        instance = null;
        // Plugin shutdown logic
    }
}
