package com.leohabrom.minecraft.plugins.badapplenew.commands;

import com.leohabrom.minecraft.plugins.badapplenew.BadAppleNew;
import com.leohabrom.minecraft.plugins.badapplenew.video.MinecraftVideo;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class StartCommand implements CommandExecutor {
    private MinecraftVideo minecraftVideo;
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        int scale = 100;
        boolean sheep = false;
        if (minecraftVideo == null) minecraftVideo = new MinecraftVideo();
        if (strings.length == 1) {
            switch (strings[0]) {
                case "0.5":
                case "50":
                    scale = 50; sheep = false; break;
                case "0.25":
                case "25":
                    scale = 25; sheep = false; break;
                case "sheep":
                    sheep = true;
                default:
            }
        }
        minecraftVideo.setScale(scale);
        minecraftVideo.setSheep(sheep);
        BadAppleNew.getInstance().video.start();
        BadAppleNew.getInstance().audio.start();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(BadAppleNew.getInstance(),minecraftVideo, 1, 1);
        return false;
    }
}

