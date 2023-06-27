package com.greenjon902.paperminesweaper;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperMineSweaper extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("minesweaper").setExecutor((commandSender, command, label, args) -> {
            if (commandSender instanceof Player && commandSender.hasPermission("minesweaper")) {
                int bombs = 10;
                if (args.length != 0) {
                    try {
                        bombs = Integer.parseInt(args[0]);
                    } catch (NumberFormatException ignored) {}
                }
                new GameGUI((Player) commandSender, bombs);
            } else {
                commandSender.sendMessage(Component.text("Ye no, you can't do that"));
            }
            return true;
        });

        Bukkit.getServer().getPluginManager().registerEvents(new Listener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
