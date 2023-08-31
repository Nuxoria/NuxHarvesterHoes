package me.dev.nux.nuxharvesterhoes.player;

import me.dev.nux.nuxharvesterhoes.NuxHarvesterHoes;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class PlayerExperience {

    private static NuxHarvesterHoes plugin = NuxHarvesterHoes.getInstance();

    private UUID playerUUID;
    private float experience;

    public PlayerExperience(UUID playerUUID, float experience) {
        this.playerUUID = playerUUID;
        this.experience = experience;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public float getExperience() {
        return experience;
    }
}
