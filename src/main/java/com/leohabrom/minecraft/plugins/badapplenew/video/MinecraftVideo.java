package com.leohabrom.minecraft.plugins.badapplenew.video;

import com.leohabrom.minecraft.plugins.badapplenew.BadAppleNew;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Sheep;
import org.bukkit.util.BoundingBox;

import java.util.Collection;
import java.util.Objects;

public class MinecraftVideo implements Runnable {
    private int scale = 100;
    private boolean[][] array = new boolean[480][360];
    private boolean sheep = false;

    public void setScale(int scale) {
        this.scale = scale;
    }

    public void setSheep(boolean sheep) {
        this.sheep = sheep;
    }

    @Override
    public void run() {
        boolean[][] newArray = BadAppleNew.getInstance().video.getFrame();
        if (sheep) {
            Collection<Entity> entities = Objects.requireNonNull(Bukkit.getServer().getWorld("world")).getNearbyEntities(new BoundingBox(-200, 300, -200, -1, 303, -1));
            for (int i = 0; i < 120; i++) {
                for (int j = 0; j < 90; j++) {
                    boolean val = newArray[i * 4][j * 4];
                    if (val != array[i * 4][j * 4]) {
                        array[i * 4][j * 4] = val;
                        for (Entity e : entities) {
                            if (e instanceof Sheep) {
                                Sheep sheep1 = (Sheep) e;
                                if (sheep1.getLocation().getBlock().equals(sheep1.getWorld().getBlockAt(-180 + i,301, -140 + j))) {
                                    sheep1.setColor(val ? DyeColor.WHITE : DyeColor.BLACK);
                                }
                            }
                        }
                    }
                }
            }

        } else {
            int div = (int) (100.0 / scale);
            for (int i = 0; i < 480 / div; i++) {
                for (int j = 0; j < 360 / div; j++) {
                    boolean val = newArray[i * div][j * div];
                    if (val != array[i * div][j * div]) {
                        array[i * div][j * div] = val;
                        Objects.requireNonNull(Bukkit.getServer().getWorld("world")).getBlockAt(i, 300, j).setType(val ? BadAppleNew.getInstance().getWhite() : BadAppleNew.getInstance().getBlack());
                    }
                }
            }
        }
    }
}
