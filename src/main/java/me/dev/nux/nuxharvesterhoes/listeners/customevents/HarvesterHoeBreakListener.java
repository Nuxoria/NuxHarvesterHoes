package me.dev.nux.nuxharvesterhoes.listeners.customevents;

import me.dev.nux.nuxharvesterhoes.events.HarvesterHoeBreakEvent;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.HarvesterHoe;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.types.HarvesterHoeType;
import me.dev.nux.nuxharvesterhoes.utils.HarvesterHoeMechanics;
import me.dev.nux.nuxharvesterhoes.utils.NBTUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HarvesterHoeBreakListener implements Listener {

    @EventHandler
    public void onHarvesterHoeBreak(HarvesterHoeBreakEvent e) {

        Player p = e.getPlayer();

        Location sugarCaneLoc = e.getSugarCaneBroken().getLocation();

        HarvesterHoe harvesterHoe = e.getHarvesterHoe();

        HarvesterHoeMechanics.breakAndCollectStalk(sugarCaneLoc, p, harvesterHoe);

    }

}
