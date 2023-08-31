package me.dev.nux.nuxharvesterhoes.customfiles.playerexperience;

import me.dev.nux.nuxharvesterhoes.customfiles.CustomFile;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.features.Feature;
import me.dev.nux.nuxharvesterhoes.player.PlayerExperience;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class PlayerExperienceFile extends CustomFile {
    public PlayerExperienceFile(String name) {
        super(name);
    }

    public void setPlayerExperience(Player player) {

        PlayerExperience playerExperience = new PlayerExperience(player.getUniqueId(), 0.0f);
        getConfiguration().set(playerExperience.getPlayerUUID().toString(), playerExperience.getExperience());

        save();

    }

    public boolean containsPlayerExperience(Player player) {
        return getConfiguration().contains(player.getUniqueId().toString());
    }

    public double getPlayerExperience(Player player) {
        return getConfiguration().getDouble(player.getUniqueId().toString());
    }

    public void addExperience(Player player, double experience) {
        double newExperience = experience + getPlayerExperience(player);
        getConfiguration().set(player.getUniqueId().toString(), newExperience);
        save();
    }

    public int getLevelPoints(Player player) {
        return getConfiguration().getConfigurationSection(player.getName()).getInt("points");
    }

    public void addLevelPoints(Player player, int points) {
        int currentPoints = getLevelPoints(player);
        getConfiguration().getConfigurationSection(player.getName()).set("points", currentPoints+points);
        save();
    }

    public void removePoints(Player player, int points) {
        int currentPoints = getLevelPoints(player);
        getConfiguration().getConfigurationSection(player.getName()).set("points", currentPoints-points);
        save();
    }

    public boolean hasEnoughPoints(Player player, Feature feature) {

        return getLevelPoints(player) >= feature.getUpgradeCost();

    }

}
