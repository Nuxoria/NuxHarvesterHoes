package me.dev.nux.nuxharvesterhoes.harvesterhoes.features.types;

import me.dev.nux.nuxharvesterhoes.NuxHarvesterHoes;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.features.Feature;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class XpBoost extends Feature {
    public XpBoost(String name, int level) {
        super(name, level, 2, FeatureType.XP_BOOSTER);
    }

    public void perform(Player p, int blocksBroken) {

        double multiplier = (1 + (0.05 * getLevel()));

        double xp = blocksBroken * multiplier;

        NuxHarvesterHoes.getInstance().getPlayerExperienceFile().addExperience(p, xp);

        p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);

    }
}
