package me.dev.nux.nuxharvesterhoes.listeners;

import me.dev.nux.nuxharvesterhoes.gui.HarvesterHoeGui;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.HarvesterHoe;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.types.HarvesterHoeType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {

        Player p = e.getPlayer();

        ItemStack itemStack = e.getItem();

        if (HarvesterHoe.isHarvesterHoe(itemStack)) {

            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

                e.setCancelled(true);

                HarvesterHoe harvesterHoe = HarvesterHoe.getHarvesterHoe(itemStack);

                if (harvesterHoe.getType() == HarvesterHoeType.PREMIUM)
                    new HarvesterHoeGui(p, harvesterHoe);

            }

        }

    }

}
