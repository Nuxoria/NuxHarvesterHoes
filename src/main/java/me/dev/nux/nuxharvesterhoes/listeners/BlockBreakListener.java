package me.dev.nux.nuxharvesterhoes.listeners;

import me.dev.nux.nuxharvesterhoes.events.HarvesterHoeBreakEvent;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.HarvesterHoe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        if (e.getBlock().getType() == Material.SUGAR_CANE_BLOCK) {

            Player p = e.getPlayer();

            if (HarvesterHoe.isHarvesterHoe(p.getItemInHand())) {

                e.setCancelled(true);

                HarvesterHoe harvesterHoe = HarvesterHoe.getHarvesterHoe(e.getPlayer().getItemInHand());

                HarvesterHoeBreakEvent harvesterHoeBreakEvent = new HarvesterHoeBreakEvent(harvesterHoe, p, e.getBlock());

                if (!harvesterHoeBreakEvent.isCancelled())
                    Bukkit.getPluginManager().callEvent(harvesterHoeBreakEvent);

            }

        }

    }

}
