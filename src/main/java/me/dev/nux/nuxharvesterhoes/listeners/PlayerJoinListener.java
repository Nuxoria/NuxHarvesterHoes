package me.dev.nux.nuxharvesterhoes.listeners;

import me.dev.nux.nuxharvesterhoes.NuxHarvesterHoes;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private static NuxHarvesterHoes plugin = NuxHarvesterHoes.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        if (!plugin.getPlayerExperienceFile().containsPlayerExperience(e.getPlayer())) {

            plugin.getPlayerExperienceFile().setPlayerExperience(e.getPlayer());

        }

        if (!plugin.getPlayerExperienceFile().getConfiguration().contains(e.getPlayer().getName())) {

            ConfigurationSection section = plugin.getPlayerExperienceFile().getConfiguration().createSection(e.getPlayer().getName());
            section.createSection("xp");
            section.createSection("points");
            section.set("xp", plugin.getPlayerExperienceFile().getPlayerExperience(e.getPlayer()));
            section.set("points", 0);
            plugin.getPlayerExperienceFile().save();

        }

    }

}
