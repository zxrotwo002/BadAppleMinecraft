package com.leohabrom.minecraft.plugins.badapplenew.commands;

import com.leohabrom.minecraft.plugins.badapplenew.BadAppleNew;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Arrays;

public class ColorCommand implements CommandExecutor {
    private BadAppleNew badAppleNew = BadAppleNew.getInstance();
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Bukkit.broadcast(Component.text(Arrays.toString(strings)));
        if (strings.length != 2) return false;
        switch (strings[0]) {
            case "green" -> badAppleNew.setBlack(Material.GREEN_CONCRETE);
            case "red" -> badAppleNew.setBlack(Material.RED_CONCRETE);
            case "yellow" -> badAppleNew.setBlack(Material.YELLOW_CONCRETE);
            case "blue" -> badAppleNew.setBlack(Material.BLUE_CONCRETE);
            case "white" -> badAppleNew.setBlack(Material.WHITE_CONCRETE);
            default -> badAppleNew.setBlack(Material.BLACK_CONCRETE);
        }
        switch (strings[1]) {
            case "green" -> badAppleNew.setWhite(Material.GREEN_CONCRETE);
            case "red" -> badAppleNew.setWhite(Material.RED_CONCRETE);
            case "yellow" -> badAppleNew.setWhite(Material.YELLOW_CONCRETE);
            case "blue" -> badAppleNew.setWhite(Material.BLUE_CONCRETE);
            case "black" -> badAppleNew.setWhite(Material.BLACK_CONCRETE);
            default -> badAppleNew.setWhite(Material.WHITE_CONCRETE);
        }
        Bukkit.broadcast(Component.text(badAppleNew.getBlack().getBlockTranslationKey()).append(Component.text(badAppleNew.getWhite().getBlockTranslationKey())));
        return true;
    }
}
