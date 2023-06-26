package com.greenjon902.paperminesweaper;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class GameGUI implements Listener {
	private static final HashMap<Player, GameGUI> games = new HashMap<>();

	private final Player player;
	private final Game game;
	private final Inventory inventory;

	public GameGUI(Player player) {
		this.player = player;
		game = new Game(9, 6);
		inventory = Bukkit.getServer().createInventory(null, 54, Component.text("Kit Selector"));
		player.openInventory(inventory);

		redraw();
	}

	public void redraw() {
		char[][] rendered = game.render();
		for (int x=0; x<game.width(); x++) {
			for (int y=0; y<game.height(); y++) {

				ItemStack item = switch (rendered[x][y]) {
					case 'b' -> new ItemStack(Material.TNT);
					case '#' -> new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
					case ' ' -> new ItemStack(Material.AIR);
					default -> throw new IllegalStateException("Unexpected value: " + rendered[x][y]);
				};
				int i = x + y * game.width();
				inventory.setItem(i, item);

			}
		}
	}

	public static void playerClicked(Player player, int i) {
		games.get(player).slotClicked(i);
	}

	private void slotClicked(int i) {
		int x = i % game.width();
		int y = (int) Math.floor((double) i / game.width());
		boolean is_bomb = game.reveal(x, y);
		redraw();

		if (is_bomb) {
			Bukkit.getScheduler().runTaskLater(PaperMineSweaper.getPlugin(PaperMineSweaper.class),
					() -> player.setHealth(0), 60);
		}
	}
}
