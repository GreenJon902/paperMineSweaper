package com.greenjon902.paperminesweaper;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Objects;

public class GameGUI implements Listener {
	private static final HashMap<Player, GameGUI> games = new HashMap<>();

	private final Player player;
	private final Game game;
	private final Inventory inventory;

	public GameGUI(Player player) {
		this.player = player;
		game = new Game(9, 6);
		inventory = Bukkit.getServer().createInventory(null, 54, Component.text("Minesweaper"));
		player.openInventory(inventory);

		games.put(player, this);

		redraw();
	}

	public void redraw() {
		char[][] rendered = game.render();
		for (int x=0; x<game.width(); x++) {
			for (int y=0; y<game.height(); y++) {
				ItemStack item;
				switch (rendered[x][y]) {
					case 'b' -> item = new ItemStack(Material.TNT);
					case '#' -> item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
					case ' ' -> item = new ItemStack(Material.AIR);
					default -> {
						item = new ItemStack(Material.PLAYER_HEAD);
						ItemMeta im = item.getItemMeta();
						im.displayName(Component.text(rendered[x][y]));
						item.setItemMeta(im);
					}
				}
				int i = x + y * game.width();
				inventory.setItem(i, item);

			}
		}
	}

	public static void playerClicked(Player player, int i, String title) {
		if (games.containsKey(player) && Objects.equals(title, "Minesweaper")) {
			games.get(player).slotClicked(i);
		}
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
