package me.dev.nux.nuxharvesterhoes.harvesterhoes;

import javafx.util.Pair;
import me.dev.nux.nuxharvesterhoes.NuxHarvesterHoes;
import me.dev.nux.nuxharvesterhoes.customfiles.harvesterhoes.HarvesterHoesFile;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.features.Feature;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.features.types.*;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.types.HarvesterHoeType;
import me.dev.nux.nuxharvesterhoes.utils.NBTUtils;
import me.dev.nux.nuxharvesterhoes.utils.PlayerUtils;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class HarvesterHoe {

    private static NuxHarvesterHoes plugin = NuxHarvesterHoes.getInstance();

    private String displayName;
    private List<String> lore;
    private HarvesterHoeType type;

    private ItemStack itemStack;

    private ItemMeta itemMeta;

    private List<Feature> allFeatures;

    private int level;

    public HarvesterHoe(String displayName, List<String> lore, HarvesterHoeType type, ItemStack itemStack, int level) {
        this.displayName = displayName;
        this.lore = lore;
        this.type = type;
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
        this.level = level;
        allFeatures = new ArrayList<>();
        initialiseAllFeatures();
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
    }
    public HarvesterHoeType getType() {
        return type;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ItemMeta getItemMeta() {
        return itemMeta;
    }

    public int getLevel() {
        return Integer.parseInt(NBTUtils.getValue(itemStack, "level"));
    }

    public void update(Player player) {
        player.setItemInHand(itemStack);
    }

    public static HarvesterHoeType stringToType(String type) {

        for (HarvesterHoeType harvesterHoeType : HarvesterHoeType.values()) {
            if (harvesterHoeType.toString().equalsIgnoreCase(type))
                return harvesterHoeType;
        }

        return null;

    }

    public static List<Pair<String, NBTTagString>> getInitialNBTData(HarvesterHoeType type) {

        List<Pair<String, NBTTagString>> nbtData = new ArrayList<>();

        nbtData.add(new Pair<>("nuxharvesterhoes", new NBTTagString("1")));
        nbtData.add(new Pair<>("type", new NBTTagString(type.name())));
        nbtData.add(new Pair<>("level", new NBTTagString("1")));
        nbtData.add(new Pair<>("levelUpCost", new NBTTagString("20000")));
        nbtData.add(new Pair<>("caneBroken", new NBTTagString("0")));

        return nbtData;

    }

    public static boolean isHarvesterHoe(ItemStack itemStack) {
        return NBTUtils.hasKey(itemStack, "nuxharvesterhoes");
    }

    public static HarvesterHoe getHarvesterHoe(ItemStack itemStack) {

        HarvesterHoesFile file = plugin.getHarvesterHoesFile();

        HarvesterHoeType type = HarvesterHoeType.valueOf(NBTUtils.getValue(itemStack, "type").toUpperCase());

        int level = Integer.valueOf(NBTUtils.getValue(itemStack, "level"));

        ConfigurationSection section = file.getHoeSection(type);

        return new HarvesterHoe(file.getHoeName(section), file.getHoeLore(section), type, itemStack, level);

    }

    public boolean hasFeature(FeatureType featureType) {

        return NBTUtils.hasKey(itemStack, featureType.getItemKey());

    }

    public Feature getFeature (FeatureType featureType) {

        switch (featureType) {

            case DOUBLE_DROPS:
            {
                return new DoubleDrops("double-drops", Integer.parseInt(NBTUtils.getValue(itemStack, featureType.getItemKey())));
            }
            case XP_BOOSTER:
            {
                return new XpBoost("xp-boost", Integer.parseInt(NBTUtils.getValue(itemStack, featureType.getItemKey())));
            }
            case AUTO_SELL:
                return new AutoSell("auto-sell", Integer.parseInt(NBTUtils.getValue(itemStack, featureType.getItemKey())));
            case TREASURE_HUNTER:
                return new TreasureHunter("treasure-hunter", Integer.parseInt(NBTUtils.getValue(itemStack, featureType.getItemKey())));
            default:
            {
                return new Feature(featureType.toString().toLowerCase(), Integer.parseInt(NBTUtils.getValue(itemStack, featureType.getItemKey())), 0, featureType);
            }

        }

    }

    public void setFeature(Feature feature, Player player) {

        this.itemStack = NBTUtils.setNBTData(itemStack, new Pair<>(feature.getType().getItemKey(), String.valueOf(feature.getLevel())));
        itemMeta = itemStack.getItemMeta();
        update(player);

    }

    public void upgradeFeature(Feature feature, Player player) {

        int currentLevel = Integer.parseInt(NBTUtils.getValue(itemStack, feature.getType().getItemKey()));
        int nextLevel = currentLevel + 1;
        feature.setLevel(nextLevel);
        setFeature(feature, player);

    }

    public int getCaneBroken() {
        return Integer.parseInt(NBTUtils.getValue(itemStack, "caneBroken"));
    }

    public static int getCaneBroken(ItemStack itemStack) {
        if (isHarvesterHoe(itemStack))
            return Integer.parseInt(NBTUtils.getValue(itemStack, "caneBroken"));
        return -1;
    }

    public static int getLevel(ItemStack itemStack) {
        if (isHarvesterHoe(itemStack))
            return Integer.parseInt(NBTUtils.getValue(itemStack, "level"));
        return -1;
    }

    public void increaseCaneBroken(Player player) {

        int caneBroken = getCaneBroken();
        caneBroken += 1;

        this.itemStack = NBTUtils.setNBTData(itemStack, new Pair<>("caneBroken", String.valueOf(caneBroken)));

        this.itemMeta = itemStack.getItemMeta();

        ConfigurationSection section = plugin.getHarvesterHoesFile().getHoeSection(getType());

        List<String> updatedLore = plugin.getHarvesterHoesFile().getHoeLore(itemStack, section);
        this.itemMeta.setLore(updatedLore);
        itemStack.setItemMeta(itemMeta);

        update(player);

    }

    public void initialiseAllFeatures() {

        for (FeatureType featureType : FeatureType.values()) {

            if (hasFeature(featureType)) {
                allFeatures.add(getFeature(featureType));
            }

        }

    }

    public List<Feature> getAllFeatures() {
        return allFeatures;
    }

    public int getLevelUpCost() {
        return Integer.parseInt(NBTUtils.getValue(itemStack, "levelUpCost"));
    }

    public void setLevelUpCost(int newCost) {
        itemStack = NBTUtils.setNBTData(itemStack, new Pair<>("levelUpCost", ""+newCost));
        itemMeta = itemStack.getItemMeta();
    }

    public void checkForLevelUp(Player player, HarvesterHoe harvesterHoe) {

        if (harvesterHoe.getType() == HarvesterHoeType.BASIC) return;

        double caneBroken = harvesterHoe.getCaneBroken();

        if (caneBroken >= getLevelUpCost()) {

            int currentLevel = getLevel();
            int nextLevel = currentLevel+1;

            this.itemStack = NBTUtils.setNBTData(itemStack, new Pair<>("level", ""+nextLevel));

            this.itemMeta = itemStack.getItemMeta();

            setLevelUpCost(getLevelUpCost() + (getLevelUpCost() + 5000));

            ConfigurationSection section = plugin.getHarvesterHoesFile().getHoeSection(getType());

            List<String> updatedLore = plugin.getHarvesterHoesFile().getHoeLore(itemStack, section);

            itemMeta.setLore(updatedLore);
            itemStack.setItemMeta(itemMeta);

            PlayerUtils.sendString(player, plugin.getMessagesFile().getLevelUpSuccess(getLevel()));

            plugin.getPlayerExperienceFile().addLevelPoints(player, 1);

            update(player);

        }

    }

}
