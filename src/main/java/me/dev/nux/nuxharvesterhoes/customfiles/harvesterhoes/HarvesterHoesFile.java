package me.dev.nux.nuxharvesterhoes.customfiles.harvesterhoes;

import me.dev.nux.nuxharvesterhoes.customfiles.CustomFile;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.HarvesterHoe;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.types.HarvesterHoeType;
import me.dev.nux.nuxharvesterhoes.utils.NBTUtils;
import me.dev.nux.nuxharvesterhoes.utils.TextUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class HarvesterHoesFile extends CustomFile {
    public HarvesterHoesFile(String name) {
        super(name);
    }

    public ConfigurationSection getHoeSection(HarvesterHoeType hoeType) {

        return getConfiguration().getConfigurationSection("harvester-hoes."+hoeType.getSectionName());

    }

    public String getHoeName(ConfigurationSection section) {
        return section.getString("name");
    }

    public List<String> getHoeLore(ConfigurationSection section) {
        return section.getStringList("lore");
    }

    public List<String> getHoeLore(ItemStack itemStack, ConfigurationSection section) {

        List<String> lore = section.getStringList("lore");
        List<String> translatedLore = new ArrayList<>();

        for (String str : lore) {

            if (str.contains("%AMOUNT%")) {
                str = str.replace("%AMOUNT%", "" + HarvesterHoe.getCaneBroken(itemStack));
            }
            if (str.contains("%LEVEL%")) {
                str = str.replace("%LEVEL%", "" + HarvesterHoe.getLevel(itemStack));
            }

            translatedLore.add(str);

        }

        return TextUtils.colorizeList(translatedLore);

    }

}
