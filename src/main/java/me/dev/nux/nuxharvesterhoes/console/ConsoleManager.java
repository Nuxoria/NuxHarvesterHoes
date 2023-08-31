package me.dev.nux.nuxharvesterhoes.console;

import org.bukkit.Bukkit;

public class ConsoleManager {

    public static void sendMessage(String message) {

        Bukkit.getConsoleSender().sendMessage(message);

    }

}
