package me.dev.nux.nuxharvesterhoes.player;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerDataManager {

    private static HashMap<Player, PlayerData> playerDataHashMap;

    public PlayerDataManager() {

        playerDataHashMap = new HashMap<>();

    }

    public static void setPlayer(Player player) {
        playerDataHashMap.put(player, new PlayerData(player));
    }

    public static PlayerData getPlayerData(Player player) {
        return playerDataHashMap.get(player);
    }

    public static HashMap<Player, PlayerData> getPlayerDataHashMap() {
        return playerDataHashMap;
    }

}
