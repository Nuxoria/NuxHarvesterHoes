package me.dev.nux.nuxharvesterhoes.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class TextUtils {

    public static List<String> colorizeList(List<String> list) {

        List<String> colorizedList = new ArrayList<>();

        for (String str : list) {

            colorizedList.add(ChatColor.translateAlternateColorCodes('&', str));

        }

        return colorizedList;

    }

    public static String colorizeString(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
