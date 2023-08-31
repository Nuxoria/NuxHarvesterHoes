package me.dev.nux.nuxharvesterhoes.harvesterhoes.features.types;

import me.dev.nux.nuxharvesterhoes.NuxHarvesterHoes;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.features.Feature;
import me.dev.nux.nuxharvesterhoes.utils.PlayerUtils;
import org.bukkit.entity.Player;

public class AutoSell extends Feature {

    public AutoSell(String name, int level) {
        super(name, level, 8, FeatureType.AUTO_SELL);
        this.maxLevel = 1;
    }

    public void perform(Player p, int blocksBrokenAmount) {

        int sugarCaneCost = NuxHarvesterHoes.getInstance().getConfig().getInt("sugar-cane-cost");
        NuxHarvesterHoes.getEcon().depositPlayer(p,
                sugarCaneCost * blocksBrokenAmount);
        PlayerUtils.sendString(p, NuxHarvesterHoes.getInstance().getMessagesFile().getSold(blocksBrokenAmount, sugarCaneCost * blocksBrokenAmount));

    }

}
