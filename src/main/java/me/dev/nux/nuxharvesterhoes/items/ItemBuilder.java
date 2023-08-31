package me.dev.nux.nuxharvesterhoes.items;

import javafx.util.Pair;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.types.HarvesterHoeType;
import me.dev.nux.nuxharvesterhoes.utils.TextUtils;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.BiConsumer;

public class ItemBuilder {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder(ConfigurationSection section) {

        if (!section.contains("id"))
            itemStack = new ItemStack(Material.valueOf(String.valueOf(section.get("item"))));
        else
            itemStack = new ItemStack(Material.valueOf(String.valueOf(section.get("item"))), 1, (short)section.getInt("id"));

        itemMeta = itemStack.getItemMeta();

        if (section.contains("name"))
            applyName.accept(itemMeta, section.getString("name"));
        if (section.contains("lore"))
            applyLore.accept(itemMeta, section.getStringList("lore"));

    }

    public ItemStack applyNBTData(List<Pair<String, NBTTagString>> tags) {

        if (tags.isEmpty()) return itemStack;

        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(getItemStack());
        NBTTagCompound tag = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();

        for (Pair<String, NBTTagString> pair : tags) {
            tag.set(pair.getKey(), pair.getValue());
        }

        nmsStack.setTag(tag);

        return CraftItemStack.asBukkitCopy(nmsStack);

    }

    public ItemStack buildItemStack(List<Pair<String, NBTTagString>> tags) {

        itemStack.setItemMeta(itemMeta);
        return applyNBTData(tags);

    }

    public BiConsumer<ItemMeta, String> applyName = (itemMeta, name)
            -> itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

    public BiConsumer<ItemMeta, List<String>> applyLore = (itemMeta, lore)
            -> itemMeta.setLore(TextUtils.colorizeList(lore));

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ItemMeta getItemMeta() {
        return itemMeta;
    }

    public void setItemStack(ItemStack itemStack) {this.itemStack = itemStack;}
    public void setItemMeta(ItemMeta itemMeta) {this.itemMeta = itemMeta;}

}
