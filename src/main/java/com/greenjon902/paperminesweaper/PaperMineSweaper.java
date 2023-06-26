package com.greenjon902.paperminesweaper;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperMineSweaper extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("/minesweaper").setExecutor((commandSender, command, s, strings) -> {
            if (commandSender instanceof Player && commandSender.hasPermission("minesweaper")) {
                //new GUI((Player) commandSender);
            } else {
                commandSender.sendMessage("Ye no, you can't do that");
            }
            return true;
        });

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
