package me.dev.nux.nuxharvesterhoes.commands;

import me.dev.nux.nuxharvesterhoes.NuxHarvesterHoes;
import me.dev.nux.nuxharvesterhoes.customfiles.CustomFile;
import me.dev.nux.nuxharvesterhoes.customfiles.messages.MessagesFile;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.HarvesterHoe;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.HarvesterHoeBuilder;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.types.HarvesterHoeType;
import me.dev.nux.nuxharvesterhoes.utils.ItemUtils;
import me.dev.nux.nuxharvesterhoes.utils.PlayerUtils;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

public class NuxHarvesterHoesCommand implements CommandExecutor {

    private NuxHarvesterHoes plugin = NuxHarvesterHoes.getInstance();

    MessagesFile messages = plugin.getMessagesFile();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {

            Player p = (Player) sender;

            if (args.length == 0) {
                PlayerUtils.sendStringList(p, messages.getWrongUsage());
                return false;
            }

            if (args.length == 3 && args[0].equalsIgnoreCase("give")) {

                Player receiver = Bukkit.getPlayer(args[1]);

                if (receiver != null) {

                    if (receiver.isOnline()) {

                        String typeString = args[2].toUpperCase();

                        if (EnumUtils.isValidEnum(HarvesterHoeType.class, typeString)) {

                            HarvesterHoeType type = HarvesterHoeType.valueOf(args[2].toUpperCase());

                            ConfigurationSection section = plugin.getHarvesterHoesFile().getHoeSection(type);

                            HarvesterHoeBuilder harvesterHoeBuilder = new HarvesterHoeBuilder(section, type);
                            HarvesterHoe harvesterHoe = harvesterHoeBuilder.buildHarvesterHoe(HarvesterHoe.getInitialNBTData(type));

                            receiver.getInventory().addItem(harvesterHoe.getItemStack());

                            PlayerUtils.sendString(receiver, plugin.getMessagesFile().getReceiveSuccess(type));
                            PlayerUtils.sendString(p, plugin.getMessagesFile().getGiveSuccess(receiver, type));

                        } else {
                            // NOT VALID ENUM
                        }

                    } else {
                        // PLAYER IS NOT ONLINE
                    }
                } else {
                    // PLAYER DOESN'T EXIST
                }
            } else if (args.length == 1 && args[0].equalsIgnoreCase("levelpoints")) {

                int levelPoints = plugin.getPlayerExperienceFile().getLevelPoints(p);
                PlayerUtils.sendString(p, plugin.getMessagesFile().getLevelPointsMsg(levelPoints));

            } else if (args.length == 1 && args[0].equalsIgnoreCase("xp")) {

                double xp = plugin.getPlayerExperienceFile().getPlayerExperience(p);
                PlayerUtils.sendString(p, plugin.getMessagesFile().getXpMsg(xp));

            } else if (args.length == 2 && args[0].equalsIgnoreCase("reload")) {

                String fileName = args[1];

                for (CustomFile customFile : CustomFile.allFiles) {

                    if (customFile.getName().equalsIgnoreCase(fileName)) {
                        customFile.save();
                        PlayerUtils.sendString(p, "&a"+customFile.getName()+".yml successfully reloaded.");
                        return true;
                    }

                }

                PlayerUtils.sendString(p, "&c"+fileName.toUpperCase()+" is an invalid configuration name.");
                return false;

            } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {

                for (CustomFile customFile : CustomFile.allFiles) {

                    customFile.save();

                }

                plugin.reloadConfig();

                PlayerUtils.sendString(p, "&aAll configuration files successfully reloaded!");

                return true;

            } else {
                PlayerUtils.sendStringList(p, plugin.getMessagesFile().getWrongUsage());
            }

        }

        return false;
    }
}
