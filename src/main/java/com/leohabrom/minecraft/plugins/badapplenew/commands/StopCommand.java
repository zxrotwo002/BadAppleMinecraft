package com.leohabrom.minecraft.plugins.badapplenew.commands;

import com.leohabrom.minecraft.plugins.badapplenew.BadAppleNew;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class StopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        BadAppleNew.getInstance().video.stop();
        BadAppleNew.getInstance().audio.stop();
        return false;
    }
}
