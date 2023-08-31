package me.dev.nux.nuxharvesterhoes.harvesterhoes.types;

import me.dev.nux.nuxharvesterhoes.harvesterhoes.HarvesterHoe;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BasicHarvesterHoe extends HarvesterHoe {

    public BasicHarvesterHoe(String displayName, List<String> lore, HarvesterHoeType type, ItemStack itemStack, int level) {
        super(displayName, lore, type, itemStack, level);
    }

}
