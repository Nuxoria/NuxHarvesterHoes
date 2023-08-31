package me.dev.nux.nuxharvesterhoes.utils;

import org.bukkit.entity.Player;

import java.util.List;

public class PlayerUtils {

    public static void sendStringList(Player p, List<String> listToSend) {

        for (String message : listToSend) {

            p.sendMessage(message);

        }

    }

    public static void sendString(Player p, String string) {
        p.sendMessage(TextUtils.colorizeString(string));
    }

}
