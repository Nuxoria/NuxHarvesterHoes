package me.dev.nux.nuxharvesterhoes.utils;

import com.google.gson.Gson;
import javafx.util.Pair;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NBTUtils {

    public static net.minecraft.server.v1_8_R3.ItemStack getNMSStack(ItemStack itemStack) {

        return CraftItemStack.asNMSCopy(itemStack);

    }

    public static boolean hasKey(ItemStack itemStack, String key) {

        if (itemStack == null) return false;
        if (!itemStack.hasItemMeta()) return false;

        net.minecraft.server.v1_8_R3.ItemStack nmsStack = getNMSStack(itemStack);

        if (nmsStack.hasTag() && nmsStack.getTag().hasKey(key)) {

            return true;

        }

        return false;

    }

    public static String getValue(ItemStack itemStack, String key) {

        net.minecraft.server.v1_8_R3.ItemStack nmsStack = getNMSStack(itemStack);

        return nmsStack.getTag().getString(key);

    }

    public static ItemStack setNBTData(ItemStack itemStack, Pair<String, String> tagPair) {

        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);

        NBTTagCompound nmsTag = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();

        nmsTag.set(tagPair.getKey(), new NBTTagString(tagPair.getValue()));

        nmsStack.setTag(nmsTag);

        return CraftItemStack.asBukkitCopy(nmsStack);

    }

}
