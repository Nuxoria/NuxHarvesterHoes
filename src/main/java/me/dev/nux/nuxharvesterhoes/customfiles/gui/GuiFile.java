package me.dev.nux.nuxharvesterhoes.customfiles.gui;

import me.dev.nux.nuxharvesterhoes.customfiles.CustomFile;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.HarvesterHoe;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.features.Feature;
import me.dev.nux.nuxharvesterhoes.utils.TextUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuiFile extends CustomFile {

    public GuiFile(String name) {
        super(name);

    }

    public String getTitle(String hoeDisplayName) {

        return TextUtils.colorizeString(getConfiguration().getString("title").replace("%HOENAME%", hoeDisplayName));

    }

    public List<String> getLore(HarvesterHoe harvesterHoe, Feature feature, ConfigurationSection section) {

        List<String> rawLore = section.getStringList("lore");
        List<String> translatedLore = new ArrayList<>();

        for (String line : rawLore) {

            if (line.contains("%LEVEL%")) {
                if (harvesterHoe.hasFeature(feature.getType())) {
                    boolean isMaxLevel = harvesterHoe.getFeature(feature.getType()).getLevel() == feature.maxLevel;
                    line = line.replace("%LEVEL%",
                            isMaxLevel ? harvesterHoe.getFeature(feature.getType()).getLevel() + " &c&l(MAX)" : ""+harvesterHoe.getFeature(feature.getType()).getLevel());
                } else {
                    line = "";
                }
            }
            if (line.contains("%COST%")) {
                if (harvesterHoe.hasFeature(feature.getType())) {
                    boolean isMaxLevel = harvesterHoe.getFeature(feature.getType()).getLevel() == feature.maxLevel;
                    if (!isMaxLevel)
                        line = line.replace("%COST%", ""+feature.getUpgradeCost());
                    else
                        line = "";
                } else {
                    line = "";
                }
            }

            if (!line.equals(""))
                translatedLore.add(TextUtils.colorizeString(line));

        }

        return translatedLore;

    }

    public ConfigurationSection getFill() {
        return getConfiguration().getConfigurationSection("fill");
    }

    public ConfigurationSection getFeatureSection(Feature feature) {
        return getConfiguration().getConfigurationSection("features."+feature.getName());
    }

}
