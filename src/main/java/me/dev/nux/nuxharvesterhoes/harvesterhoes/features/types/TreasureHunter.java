package me.dev.nux.nuxharvesterhoes.harvesterhoes.features.types;

import javafx.util.Pair;
import jdk.nashorn.internal.ir.BlockStatement;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.HarvesterHoe;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.features.Feature;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TreasureHunter extends Feature {
    public TreasureHunter(String name, int level) {
        super(name, level, 4, FeatureType.TREASURE_HUNTER);
    }

    public void peform(Player p,  HarvesterHoe harvesterHoe, Block blockBroken) {

        Random random = new Random();
        double chance = random.nextDouble();

        int level = harvesterHoe.getFeature(FeatureType.TREASURE_HUNTER).getLevel();
        double winChance = level*0.001;

        System.out.println(chance);
        System.out.println("WinChance:" + winChance);

        if (chance < winChance) {

            List<ItemStack> rewards = initRewards();

            Random random2 = new Random();
            int chance2 = random2.nextInt(rewards.size());

            p.getInventory().addItem(rewards.get(chance2));
        }

    }

    private List<ItemStack> initRewards() {

        List<ItemStack> rewards = new ArrayList<>();

        List<Material> diamondArmor = new ArrayList<>(Arrays.asList(
                Material.DIAMOND_HELMET,
                Material.DIAMOND_CHESTPLATE,
                Material.DIAMOND_LEGGINGS,
                Material.DIAMOND_BOOTS
        ));

        List<Material> diamondSwords = new ArrayList<>(Arrays.asList(
                Material.DIAMOND_SWORD
        ));

        List<Pair<Enchantment, Integer>> armorEnchantments = new ArrayList<>(Arrays.asList(
                new Pair<>(Enchantment.PROTECTION_ENVIRONMENTAL, 4),
                new Pair<>(Enchantment.DURABILITY, 3)
        ));

        List<Pair<Enchantment, Integer>> swordEnchantments = new ArrayList<>(Arrays.asList(
                new Pair<>(Enchantment.DAMAGE_ALL, 5),
                new Pair<>(Enchantment.DURABILITY, 3)
        ));

        List<EntityType> spawnerTypes = new ArrayList<>(Arrays.asList(
                EntityType.BLAZE,
                EntityType.SKELETON,
                EntityType.ZOMBIE
        ));

        for (Material material : diamondArmor) {

            ItemStack armorPiece = new ItemStack(material);
            ItemMeta armorMeta = armorPiece.getItemMeta();

            for (Pair<Enchantment, Integer> enchPair : armorEnchantments) {
                armorMeta.addEnchant(enchPair.getKey(), enchPair.getValue(), true);
            }

            armorPiece.setItemMeta(armorMeta);

            rewards.add(armorPiece);

        }

        for (Material material : diamondSwords) {

            ItemStack swordStack = new ItemStack(material);
            ItemMeta swordMeta = swordStack.getItemMeta();

            for (Pair<Enchantment, Integer> enchPair : swordEnchantments) {
                swordMeta.addEnchant(enchPair.getKey(), enchPair.getValue(), true);
            }

            swordStack.setItemMeta(swordMeta);

            rewards.add(swordStack);

        }

        for (EntityType entityType : spawnerTypes) {

            ItemStack spawner = new ItemStack(Material.MOB_SPAWNER);
            BlockStateMeta bsm = (BlockStateMeta) spawner.getItemMeta();
            bsm.setDisplayName(entityType.getName()+" Spawner");
            CreatureSpawner creatureSpawner = (CreatureSpawner) bsm.getBlockState();

            creatureSpawner.setSpawnedType(entityType);
            bsm.setBlockState(creatureSpawner);
            spawner.setItemMeta(bsm);

            rewards.add(spawner);

        }

        return rewards;


    }

}
