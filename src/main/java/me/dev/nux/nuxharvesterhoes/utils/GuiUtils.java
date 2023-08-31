package me.dev.nux.nuxharvesterhoes.utils;

import javafx.util.Pair;
import me.dev.nux.nuxharvesterhoes.NuxHarvesterHoes;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.HarvesterHoe;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.features.Feature;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.features.types.FeatureType;
import me.dev.nux.nuxharvesterhoes.items.ItemBuilder;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiUtils {

    private static NuxHarvesterHoes plugin = NuxHarvesterHoes.getInstance();

    public static List<Pair<ItemStack, Integer>> createGuiItems(ConfigurationSection section, HarvesterHoe harvesterHoe) {

        List<Pair<ItemStack, Integer>> guiItems = new ArrayList<>();

        for (String featureName : section.getKeys(false)) {

            ConfigurationSection featureSection = section.getConfigurationSection(featureName);
            ItemBuilder itemBuilder = new ItemBuilder(featureSection);

            List<Pair<String, NBTTagString>> tagList = new ArrayList<>();
            tagList.add(new Pair<>("gui:"+featureName, new NBTTagString("1")));

            ItemStack guiItem = itemBuilder.buildItemStack(tagList);
            ItemMeta guiItemMeta = guiItem.getItemMeta();

            Feature feature = Feature.asFeature(featureName);

            List<String> newLore = plugin.getGuiFile().getLore(harvesterHoe, feature, featureSection);
            guiItemMeta.setLore(newLore);
            guiItem.setItemMeta(guiItemMeta);

            int slot = featureSection.getInt("slot");

            guiItems.add(new Pair<>(guiItem, slot));

        }

        return guiItems;

    }

    public static void setGuiItems(List<Pair<ItemStack, Integer>> guiItems, Inventory inventory) {

        for (Pair<ItemStack, Integer> guiItemPair : guiItems) {

            inventory.setItem(guiItemPair.getValue(), guiItemPair.getKey());

        }

    }


    public static FeatureType getClickedFeatureType(ItemStack itemStack) {

        if (itemStack == null || !itemStack.hasItemMeta()) return null;

        for (FeatureType type : FeatureType.values()) {

            if (NBTUtils.hasKey(itemStack, type.getGuiKey()))
                return type;

        }

        return null;

    }

    public static void fill(Inventory inventory) {

        ConfigurationSection configurationSection = plugin.getGuiFile().getFill();

        if (configurationSection.get("item") == null) return;

        ItemBuilder itemBuilder = new ItemBuilder(configurationSection);
        List<Pair<String, NBTTagString>> emptyTag = new ArrayList<>();
        ItemStack itemStack = itemBuilder.buildItemStack(emptyTag);

        for (int i = 0; i < inventory.getSize(); ++i) {

            if (inventory.getItem(i) == null)
                inventory.setItem(i, itemStack);

        }

    }


}
