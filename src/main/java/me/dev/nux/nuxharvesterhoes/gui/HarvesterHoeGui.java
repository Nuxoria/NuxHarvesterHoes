package me.dev.nux.nuxharvesterhoes.gui;

import javafx.util.Pair;
import me.dev.nux.nuxharvesterhoes.NuxHarvesterHoes;
import me.dev.nux.nuxharvesterhoes.customfiles.playerexperience.PlayerExperienceFile;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.HarvesterHoe;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.features.Feature;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.features.types.*;
import me.dev.nux.nuxharvesterhoes.player.PlayerData;
import me.dev.nux.nuxharvesterhoes.player.PlayerDataManager;
import me.dev.nux.nuxharvesterhoes.player.PlayerExperience;
import me.dev.nux.nuxharvesterhoes.utils.GuiUtils;
import me.dev.nux.nuxharvesterhoes.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class HarvesterHoeGui {

    private static NuxHarvesterHoes plugin = NuxHarvesterHoes.getInstance();

    private Inventory inventory;

    private HarvesterHoe harvesterHoe;

    private static List<Pair<ItemStack, Integer>> guiItems;

    public HarvesterHoeGui(Player player, HarvesterHoe harvesterHoe) {

        Inventory inventory = Bukkit.createInventory(null, 27, plugin.getGuiFile().getTitle(harvesterHoe.getDisplayName()));

        List<Pair<ItemStack, Integer>> guiItems = GuiUtils.createGuiItems(
                plugin.getGuiFile().getConfiguration().getConfigurationSection("features"),
                harvesterHoe);

        this.guiItems = guiItems;

        GuiUtils.setGuiItems(guiItems, inventory);

        GuiUtils.fill(inventory);

        this.inventory = inventory;
        this.harvesterHoe = harvesterHoe;

        PlayerDataManager.getPlayerDataHashMap().put(player, new PlayerData(player));
        PlayerDataManager.getPlayerData(player).setOpenGui(this);
        player.openInventory(inventory);

    }

    public Inventory getInventory() {
        return inventory;
    }

    public HarvesterHoe getHarvesterHoe() {
        return harvesterHoe;
    }

    public static void handleClick(HarvesterHoe harvesterHoe, ItemStack clickedItem, Player p, Inventory inventory) {

        FeatureType featureType = GuiUtils.getClickedFeatureType(clickedItem);

        PlayerExperienceFile xpFile = plugin.getPlayerExperienceFile();

        if (featureType == null) return;

        switch (featureType) {

            case XP_BOOSTER:
            {

                if (harvesterHoe.hasFeature(FeatureType.XP_BOOSTER)) {

                    XpBoost xpBoost = (XpBoost) harvesterHoe.getFeature(FeatureType.XP_BOOSTER);

                    if (xpBoost.getLevel() == xpBoost.maxLevel) {
                        p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                        return;
                    }

                    if (xpFile.hasEnoughPoints(p, xpBoost)) {

                        harvesterHoe.upgradeFeature(xpBoost, p);
                        if (harvesterHoe.getFeature(FeatureType.XP_BOOSTER).getLevel() == harvesterHoe.getFeature(FeatureType.XP_BOOSTER).maxLevel)
                            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
                        else
                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.0f);

                        ItemStack newIS = upgradeItemLore(clickedItem, harvesterHoe, xpBoost, plugin.getGuiFile().getFeatureSection(xpBoost));
                        updateItem(newIS, xpBoost, inventory);

                        xpFile.removePoints(p, xpBoost.getUpgradeCost());

                    } else {
                        PlayerUtils.sendString(p, plugin.getMessagesFile().getNotEnoughPoints());
                    }

                } else {

                    XpBoost xpBoost = new XpBoost("xp-boost", 1);

                    if (xpFile.hasEnoughPoints(p, xpBoost)) {

                        harvesterHoe.setFeature(xpBoost, p);
                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.0f);

                        ItemStack newIS = upgradeItemLore(clickedItem, harvesterHoe, xpBoost, plugin.getGuiFile().getFeatureSection(xpBoost));
                        updateItem(newIS, xpBoost, inventory);

                        xpFile.removePoints(p, xpBoost.getUpgradeCost());

                    } else {
                        PlayerUtils.sendString(p, plugin.getMessagesFile().getNotEnoughPoints());
                    }

                }

                break;
            }

            case DOUBLE_DROPS:
            {

                if (harvesterHoe.hasFeature(FeatureType.DOUBLE_DROPS)) {

                    DoubleDrops doubleDrops = (DoubleDrops) harvesterHoe.getFeature(FeatureType.DOUBLE_DROPS);

                    if (doubleDrops.getLevel() == doubleDrops.maxLevel) {
                        p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                        return;
                    }

                    // IMPLEMENT SOMETHING LIKE THAT, A SYSTEM THAT CHECKS IF YOU HAVE ENOUGH LEVEL POINTS TO BUY AN UPGRADE
                    //if (plugin.getPlayerExperienceFile().getLevelPoints(p) >= doubleDrops.getUpgradeCost());

                    if (xpFile.hasEnoughPoints(p, doubleDrops)) {

                        harvesterHoe.upgradeFeature(doubleDrops, p);

                        if (harvesterHoe.getFeature(FeatureType.DOUBLE_DROPS).getLevel() == harvesterHoe.getFeature(FeatureType.DOUBLE_DROPS).maxLevel)
                            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
                        else
                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.0f);

                        ItemStack newIS = upgradeItemLore(clickedItem, harvesterHoe, doubleDrops, plugin.getGuiFile().getFeatureSection(doubleDrops));
                        updateItem(newIS, doubleDrops, inventory);

                        xpFile.removePoints(p, doubleDrops.getUpgradeCost());

                    } else {

                        PlayerUtils.sendString(p, plugin.getMessagesFile().getNotEnoughPoints());

                    }

                } else {

                    DoubleDrops doubleDrops = new DoubleDrops("double-drops", 1);

                    if (xpFile.hasEnoughPoints(p, doubleDrops)) {

                        harvesterHoe.setFeature(doubleDrops, p);
                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.0f);

                        ItemStack newIS = upgradeItemLore(clickedItem, harvesterHoe, doubleDrops, plugin.getGuiFile().getFeatureSection(doubleDrops));
                        updateItem(newIS, doubleDrops, inventory);

                        xpFile.removePoints(p, doubleDrops.getUpgradeCost());

                    } else {
                        PlayerUtils.sendString(p, plugin.getMessagesFile().getNotEnoughPoints());
                    }

                }


                break;
            }
            case AUTO_SELL:
            {

                if (harvesterHoe.hasFeature(FeatureType.AUTO_SELL)) {
                    p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                    return;
                } else {

                    AutoSell autoSell = new AutoSell("auto-sell", 1);

                    if (xpFile.hasEnoughPoints(p, autoSell)) {

                        harvesterHoe.setFeature(autoSell, p);
                        p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);

                        ItemStack newIS = upgradeItemLore(clickedItem, harvesterHoe, autoSell, plugin.getGuiFile().getFeatureSection(autoSell));
                        updateItem(newIS, autoSell, inventory);

                        xpFile.removePoints(p, autoSell.getUpgradeCost());

                    } else {
                        PlayerUtils.sendString(p, plugin.getMessagesFile().getNotEnoughPoints());
                    }

                }

                break;
            }

            case TREASURE_HUNTER:
            {

                if (harvesterHoe.hasFeature(FeatureType.TREASURE_HUNTER)) {

                    TreasureHunter treasureHunter = (TreasureHunter) harvesterHoe.getFeature(FeatureType.TREASURE_HUNTER);

                    if (treasureHunter.getLevel() == treasureHunter.maxLevel) {
                        p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                        return;
                    }

                    if (xpFile.hasEnoughPoints(p, treasureHunter)) {

                        harvesterHoe.upgradeFeature(treasureHunter, p);
                        if (harvesterHoe.getFeature(FeatureType.TREASURE_HUNTER).getLevel() == harvesterHoe.getFeature(FeatureType.TREASURE_HUNTER).maxLevel)
                            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
                        else
                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.0f);

                        ItemStack newIS = upgradeItemLore(clickedItem, harvesterHoe, treasureHunter, plugin.getGuiFile().getFeatureSection(treasureHunter));
                        updateItem(newIS, treasureHunter, inventory);

                        xpFile.removePoints(p, treasureHunter.getUpgradeCost());

                    } else {
                        PlayerUtils.sendString(p, plugin.getMessagesFile().getNotEnoughPoints());
                    }

                } else {

                    TreasureHunter treasureHunter = new TreasureHunter("treasure-hunter", 1);

                    if (xpFile.hasEnoughPoints(p, treasureHunter)) {

                        harvesterHoe.setFeature(treasureHunter, p);
                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.0f);

                        ItemStack newIS = upgradeItemLore(clickedItem, harvesterHoe, treasureHunter, plugin.getGuiFile().getFeatureSection(treasureHunter));
                        updateItem(newIS, treasureHunter, inventory);

                        xpFile.removePoints(p, treasureHunter.getUpgradeCost());

                    } else {
                        PlayerUtils.sendString(p, plugin.getMessagesFile().getNotEnoughPoints());
                    }

                }


                break;
            }

        }

    }

    private static ItemStack upgradeItemLore(ItemStack itemStack, HarvesterHoe harvesterHoe, Feature feature, ConfigurationSection configurationSection) {

        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> newLore = plugin.getGuiFile().getLore(harvesterHoe, feature, configurationSection);
        itemMeta.setLore(newLore);

        itemStack.setItemMeta(itemMeta);

        return itemStack;

    }

    private static void updateItem(ItemStack itemStack, Feature feature, Inventory inventory) {
        int slot = plugin.getGuiFile().getFeatureSection(feature).getInt("slot");
        inventory.setItem(slot, itemStack);
    }

}
