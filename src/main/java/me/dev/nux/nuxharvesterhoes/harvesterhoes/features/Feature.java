package me.dev.nux.nuxharvesterhoes.harvesterhoes.features;

import me.dev.nux.nuxharvesterhoes.harvesterhoes.HarvesterHoe;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.features.types.*;
import me.dev.nux.nuxharvesterhoes.utils.PlayerUtils;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

public class Feature {

    private String name;
    private int level;

    private int levelCost;

    private FeatureType type;

    public int maxLevel = 10;

    public Feature(String name, int level, int levelCost, FeatureType featureType) {
        this.name = name;
        this.level = level;
        this.type = featureType;
        this.levelCost = levelCost;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public FeatureType getType() {
        return type;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevelCost() {
        return levelCost;
    }

    public int getUpgradeCost() {
        return levelCost;
    }

    public static Feature asFeature(String featureName) {

        switch (featureName) {

            case "double-drops":
            {
                return new DoubleDrops("double-drops", 0);
            }
            case "xp-boost":
            {
                return new XpBoost("xp-boost", 0);
            }
            case "auto-sell":
            {
                return new AutoSell("auto-sell", 0);
            }
            case "treasure-hunter":
            {
                return new TreasureHunter("treasure-hunter", 0);
            }
            default:
            {
                return null;
            }

        }

    }

}
