package me.dev.nux.nuxharvesterhoes.listeners;

import me.dev.nux.nuxharvesterhoes.player.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {

        Player p = (Player) e.getPlayer();

        if (PlayerDataManager.getPlayerDataHashMap().containsKey(p) &&
        PlayerDataManager.getPlayerData(p).getOpenGui() != null) {

            if (PlayerDataManager.getPlayerData(p).getOpenGui().getInventory().equals(e.getInventory())) {
                PlayerDataManager.getPlayerData(p).setOpenGui(null);
            }

        }

    }

}
