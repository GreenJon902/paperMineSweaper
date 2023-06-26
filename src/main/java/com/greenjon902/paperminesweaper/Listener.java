package com.greenjon902.paperminesweaper;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class Listener implements org.bukkit.event.Listener {
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		String title = event.getView().getTitle();
		int i = event.getSlot();

		GameGUI.playerClicked(player, i, title);
	}
}
