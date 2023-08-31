package me.dev.nux.nuxharvesterhoes.harvesterhoes.types;

import me.dev.nux.nuxharvesterhoes.harvesterhoes.HarvesterHoe;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PremiumHarvesterHoe extends HarvesterHoe {

    public PremiumHarvesterHoe(String displayName, List<String> lore, HarvesterHoeType type, ItemStack itemStack, int level) {
        super(displayName, lore, type, itemStack, level);
    }

}
