package me.dev.nux.nuxharvesterhoes.harvesterhoes;

import javafx.util.Pair;
import me.dev.nux.nuxharvesterhoes.NuxHarvesterHoes;
import me.dev.nux.nuxharvesterhoes.customfiles.harvesterhoes.HarvesterHoesFile;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.types.HarvesterHoeType;
import me.dev.nux.nuxharvesterhoes.items.ItemBuilder;
import me.dev.nux.nuxharvesterhoes.utils.ItemUtils;
import me.dev.nux.nuxharvesterhoes.utils.NBTUtils;
import me.dev.nux.nuxharvesterhoes.utils.TextUtils;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.BiConsumer;

public class HarvesterHoeBuilder extends ItemBuilder {

    private HarvesterHoeType harvesterHoeType;

    private ConfigurationSection section;

    public HarvesterHoeBuilder(ConfigurationSection section, HarvesterHoeType type) {
        super(section);

        this.harvesterHoeType = type;
        this.section = section;

    }

    public HarvesterHoe buildHarvesterHoe(List<Pair<String, NBTTagString>> tags) {

        getItemStack().setItemMeta(getItemMeta());

        ItemStack is = getItemStack();
        ItemMeta isMeta = is.getItemMeta();

        is = applyNBTData(tags);

        setItemStack(is);

        List<String> lore = NuxHarvesterHoes.getInstance().getHarvesterHoesFile().getHoeLore(is, section);

        isMeta.setLore(lore);
        is.setItemMeta(isMeta);

        is = applyNBTData(tags);

        return new HarvesterHoe(getItemMeta().getDisplayName(), lore, harvesterHoeType, is, 1);

    }

}
