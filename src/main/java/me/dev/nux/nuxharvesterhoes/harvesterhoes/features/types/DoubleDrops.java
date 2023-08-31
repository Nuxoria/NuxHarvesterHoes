package me.dev.nux.nuxharvesterhoes.harvesterhoes.features.types;

import me.dev.nux.nuxharvesterhoes.NuxHarvesterHoes;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.HarvesterHoe;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.features.Feature;
import me.dev.nux.nuxharvesterhoes.utils.PlayerUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DoubleDrops extends Feature {
    public DoubleDrops(String name, int level) {
        super(name, level, 3, FeatureType.DOUBLE_DROPS);
    }

    public void perform(Player p, int blocksBrokenAmount, HarvesterHoe harvesterHoe) {

        int times = getLevel() == 8 || getLevel() == 9 || getLevel() == 10 ? getLevel() : getLevel() + 1;

        ItemStack sugarCane = new ItemStack(Material.SUGAR_CANE, blocksBrokenAmount * times);

        for (int i = 0; i < (blocksBrokenAmount * times); ++i) {
            harvesterHoe.increaseCaneBroken(p);
        }

        if (harvesterHoe.hasFeature(FeatureType.AUTO_SELL)) {
            int sugarCaneCost = NuxHarvesterHoes.getInstance().getConfig().getInt("sugar-cane-cost");
            NuxHarvesterHoes.getEcon().depositPlayer(p,
                    sugarCaneCost * (blocksBrokenAmount * times));
            PlayerUtils.sendString(p, NuxHarvesterHoes.getInstance().getMessagesFile().getSold(sugarCane.getAmount(), sugarCaneCost * (blocksBrokenAmount * times)));
        } else {
            p.getInventory().addItem(sugarCane);
        }

        if (harvesterHoe.hasFeature(FeatureType.XP_BOOSTER)) {

            XpBoost xpBoost = (XpBoost) harvesterHoe.getFeature(FeatureType.XP_BOOSTER);
            xpBoost.perform(p, blocksBrokenAmount * times);

        }

    }

}
