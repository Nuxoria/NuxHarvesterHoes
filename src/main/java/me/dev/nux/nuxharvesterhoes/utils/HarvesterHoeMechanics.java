package me.dev.nux.nuxharvesterhoes.utils;

import me.dev.nux.nuxharvesterhoes.NuxHarvesterHoes;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.HarvesterHoe;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.features.Feature;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.features.types.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.swing.plaf.SplitPaneUI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HarvesterHoeMechanics {

    private static NuxHarvesterHoes plugin = NuxHarvesterHoes.getInstance();

    private HarvesterHoeMechanics() {

    }

    public static void breakAndCollectStalk(Location sugarCaneLoc, Player player, HarvesterHoe harvesterHoe) {

        Block initialBlock = sugarCaneLoc.getBlock();

        List<Block> toBreakBlocks = new ArrayList<>();

        while (sugarCaneLoc.getBlock().getType() == Material.SUGAR_CANE_BLOCK) {
            toBreakBlocks.add(sugarCaneLoc.getBlock());
            sugarCaneLoc = sugarCaneLoc.add(0, 1, 0);
        }

        Collections.reverse(toBreakBlocks);

        if (harvesterHoe.hasFeature(FeatureType.DOUBLE_DROPS)) {
            DoubleDrops doubleDrops = (DoubleDrops) harvesterHoe.getFeature(FeatureType.DOUBLE_DROPS);
            doubleDrops.perform(player, toBreakBlocks.size(), harvesterHoe);
        } else {

            for (Block b : toBreakBlocks) {
                harvesterHoe.increaseCaneBroken(player);
                if (!harvesterHoe.hasFeature(FeatureType.AUTO_SELL))
                    player.getInventory().addItem(new ItemStack(Material.SUGAR_CANE));
            }

        }

        if (harvesterHoe.hasFeature(FeatureType.AUTO_SELL)) {
            if (!harvesterHoe.hasFeature(FeatureType.DOUBLE_DROPS)) {
                AutoSell autoSell = (AutoSell) harvesterHoe.getFeature(FeatureType.AUTO_SELL);
                autoSell.perform(player, toBreakBlocks.size());
            }
        }

        if (harvesterHoe.hasFeature(FeatureType.XP_BOOSTER)) {
            if (!harvesterHoe.hasFeature(FeatureType.AUTO_SELL)) {
                XpBoost xpBoost = (XpBoost) harvesterHoe.getFeature(FeatureType.XP_BOOSTER);
                xpBoost.perform(player, toBreakBlocks.size());
            }
        } else {
            plugin.getPlayerExperienceFile().addExperience(player, toBreakBlocks.size());
        }

        if (harvesterHoe.hasFeature(FeatureType.TREASURE_HUNTER)) {
            TreasureHunter treasureHunter = (TreasureHunter) harvesterHoe.getFeature(FeatureType.TREASURE_HUNTER);
            treasureHunter.peform(player, harvesterHoe, initialBlock);
        }

        for (Block b : toBreakBlocks) {
            b.setType(Material.AIR);
        }

        // CHECK FOR LEVEL UP
        harvesterHoe.checkForLevelUp(player, harvesterHoe);

    }

}
