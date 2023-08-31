package me.dev.nux.nuxharvesterhoes.customfiles.jsonfiles;

import me.dev.nux.nuxharvesterhoes.NuxHarvesterHoes;
import me.dev.nux.nuxharvesterhoes.customfiles.CustomJsonFile;
import me.dev.nux.nuxharvesterhoes.player.PlayerExperience;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;
import java.util.List;

public class XPJsonFile extends CustomJsonFile {

    private NuxHarvesterHoes plugin = NuxHarvesterHoes.getInstance();

    public XPJsonFile(String fileName) {
        super(fileName);
    }

    public boolean containsPlayer(Player player) {

        List<PlayerExperience> playerExperiences;
        try {
            playerExperiences = readJson();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (playerExperiences.isEmpty()) return false;

        for (PlayerExperience playerExperience : playerExperiences) {

            if (playerExperience.getPlayerUUID().equals(player.getUniqueId()))
                return true;

        }

        return false;

    }

}
