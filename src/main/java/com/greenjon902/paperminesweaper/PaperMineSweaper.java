package com.greenjon902.paperminesweaper;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperMineSweaper extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("/minesweaper").setExecutor((commandSender, command, s, strings) -> {
            if (commandSender instanceof Player && commandSender.hasPermission("minesweaper")) {
                new GameGUI((Player) commandSender);
            } else {
                commandSender.sendMessage("Ye no, you can't do that");
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
