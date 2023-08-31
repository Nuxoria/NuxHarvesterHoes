package me.dev.nux.nuxharvesterhoes.listeners;

import me.dev.nux.nuxharvesterhoes.NuxHarvesterHoes;
import me.dev.nux.nuxharvesterhoes.gui.HarvesterHoeGui;
import me.dev.nux.nuxharvesterhoes.player.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    private static NuxHarvesterHoes plugin = NuxHarvesterHoes.getInstance();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();

        if (PlayerDataManager.getPlayerDataHashMap().containsKey(p) &&
        PlayerDataManager.getPlayerData(p).getOpenGui() != null) {

            if (e.getClickedInventory() == null) return;

            if (e.getClickedInventory().equals(p.getInventory()))
                e.setCancelled(true);

            if (PlayerDataManager.getPlayerData(p).getOpenGui().getInventory().equals(e.getClickedInventory())) {

                e.setCancelled(true);

                ItemStack itemStack = e.getCurrentItem();

                HarvesterHoeGui.handleClick(PlayerDataManager.getPlayerData(p).getOpenGui().getHarvesterHoe(), itemStack, p,
                        PlayerDataManager.getPlayerData(p).getOpenGui().getInventory());

            }

        }

    }

}
