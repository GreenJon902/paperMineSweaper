package com.greenjon902.paperminesweaper;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class GameGUI implements Listener {
	private static final HashMap<Player, GameGUI> games = new HashMap<>();

	private final Player player;
	private final Game game;
	private final Inventory inventory;

	public GameGUI(Player player, int bombs) {
		this.player = player;
		game = new Game(9, 6, bombs);
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
					case 'f' -> item = new ItemStack(Material.RED_BANNER);
					default -> {
						item = new ItemStack(Material.PLAYER_HEAD);
						SkullMeta im = (SkullMeta) item.getItemMeta();
						im.displayName(Component.text(rendered[x][y]));

						final PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
						profile.getProperties().add(new ProfileProperty("textures", switch (rendered[x][y]) {
							case '0' -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjQ1ODFkMzk1NWU5YWNkNTEzZDI4ZGQzMjI1N2FlNTFmZjdmZDZkZjA1YjVmNGI5MjFmMWRlYWU0OWIyMTcyIn19fQ==";
							case '1' -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQ2NWNlODNmMWFhNWI2ZTg0ZjliMjMzNTk1MTQwZDViNmJlY2ViNjJiNmQwYzY3ZDFhMWQ4MzYyNWZmZCJ9fX0=";
							case '2' -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGQ1NGQxZjhmYmY5MWIxZTdmNTVmMWJkYjI1ZTJlMzNiYWY2ZjQ2YWQ4YWZiZTA4ZmZlNzU3ZDMwNzVlMyJ9fX0=";
							case '3' -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjFlNGVhNTliNTRjYzk5NDE2YmM5ZjYyNDU0OGRkYWMyYTM4ZWVhNmEyZGJmNmU0Y2NkODNjZWM3YWM5NjkifX19";
							case '4' -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGI1MjdiMjRiNWQyYmNkYzc1NmY5OTVkMzRlYWU1NzlkNzQxNGIwYTVmMjZjNGZmYTRhNTU4ZWNhZjZiNyJ9fX0=";
							case '5' -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODRjOGMzNzEwZGEyNTU5YTI5MWFkYzM5NjI5ZTljY2VhMzFjYTlkM2QzNTg2YmZlYTZlNmUwNjEyNGIzYyJ9fX0=";
							case '6' -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTIxMTNjNjA0YTIyYjIyNGZiZDM1OTdmOTA0YTdmOTIyN2E3YzFhZTUzNDM5Yzk2OTk0YmZhMjNiNTJlYiJ9fX0=";
							case '7' -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjRiZGU3OWY4NGZjNWYzZjFmYmM1YmMwMTA3MTA2NmJkMjBjZDI2M2ExNjU0ZDY0ZDYwZDg0MjQ4YmE5Y2QifX19";
							case '8' -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjJlZTEzNzFkOGYwZjVhOGI3NTljMjkxODYzZDcwNGFkYzQyMWFkNTE5ZjE3NDYyYjg3NzA0ZGJmMWM3OGE0In19fQ==";
							case '9' -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDM3OGYyZWQ3NzNjZDZiMjU1MTgxOTIxOGJmZjg3YzM3NGE0YjdkNmYzYjJjMjM2Nzg3ZWE3OTM2N2JmNmQxYyJ9fX0=";
							default -> throw new IllegalStateException("Unexpected value: " + rendered[x][y]);
						}));

						im.setPlayerProfile(profile);
						item.setItemMeta(im);
					}
				}

				ItemMeta im = item.getItemMeta();
				if (Objects.nonNull(im)) {
					im.displayName(Component.empty());
					im.setCustomModelData((int) rendered[x][y]);
					item.setItemMeta(im);
				}

				int i = x + y * game.width();
				inventory.setItem(i, item);

			}
		}
	}

	public static boolean playerClicked(Player player, int i, String title, boolean is_flag) {
		if (games.containsKey(player) && Objects.equals(title, "Minesweaper")) {
			if (is_flag) {
				games.get(player).flag(i);
			}
			else {
				games.get(player).slotClicked(i);
			}
			return true;
		}
		return false;
	}

	private void slotClicked(int i) {
		int x = i % game.width();
		int y = (int) Math.floor((double) i / game.width());
		boolean is_bomb = game.reveal(x, y);
		redraw();

		if (is_bomb || game.is_won()) {

			String title = is_bomb ? "You looses!!" : "You Win!!";
			String subtitle = is_bomb ? "Get gud, bozo" : "Now go touch grass";
			int color = is_bomb ? 0xff0000 : 0x00ff00;

			games.remove(player);
			Bukkit.getScheduler().runTaskLater(PaperMineSweaper.getPlugin(PaperMineSweaper.class),
					() -> {
						player.closeInventory();
						player.showTitle(Title.title(Component.text(title).color(TextColor.color(color)),
								Component.text(subtitle)));
					}, 60);
		}
	}

	private void flag(int i) {
		int x = i % game.width();
		int y = (int) Math.floor((double) i / game.width());

		game.toggle_flag(x, y);
		redraw();
	}
}
