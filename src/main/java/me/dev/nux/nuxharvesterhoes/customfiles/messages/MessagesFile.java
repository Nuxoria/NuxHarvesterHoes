package me.dev.nux.nuxharvesterhoes.customfiles.messages;

import me.dev.nux.nuxharvesterhoes.customfiles.CustomFile;
import me.dev.nux.nuxharvesterhoes.harvesterhoes.types.HarvesterHoeType;
import me.dev.nux.nuxharvesterhoes.utils.TextUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MessagesFile extends CustomFile {

    public MessagesFile(String name) {
        super(name);
    }

    public List<String> getSectionMessages(ConfigurationSection section) {

        List<String> allSectionMessages = new ArrayList<>();

        for (String message : allSectionMessages) {
            allSectionMessages.add(message);
        }

        return allSectionMessages;

    }

    public List<String> getWrongUsage() {

        return TextUtils.colorizeList(getConfiguration().getConfigurationSection("error-messages")
                .getStringList("wrong-usage"));

    }

    public String getGiveSuccess(Player player, HarvesterHoeType harvesterHoeType) {
        return TextUtils.colorizeString(getConfiguration().getConfigurationSection("success-messages")
                .getString("give-success").replace("%PLAYER%", player.getName()).replace("%HOETYPE%", harvesterHoeType.toString()));
    }

    public String getReceiveSuccess(HarvesterHoeType harvesterHoeType) {
        return TextUtils.colorizeString(getConfiguration().getConfigurationSection("success-messages")
                .getString("receive-success").replace("%HOETYPE%", harvesterHoeType.toString()));
    }

    public String getLevelUpSuccess(int level) {
        return getConfiguration().getConfigurationSection("success-messages")
                .getString("level-up-success").replace("%LEVEL%", ""+level);
    }

    public String getSold(int blocksBroken, int cash) {
        return getConfiguration().getConfigurationSection("success-messages")
                .getString("sold-sugar-cane").replace("%BLOCKSBROKENAMOUNT%", ""+blocksBroken).replace("%CASH%", ""+cash);
    }

    public String getLevelPointsMsg(int levelPoints) {
        return getConfiguration().getConfigurationSection("other")
                .getString("level-points-message").replace("%POINTS%", ""+levelPoints);
    }

    public String getXpMsg(double xp) {
        return getConfiguration().getConfigurationSection("other")
                .getString("xp-message").replace("%XP%", ""+xp);
    }

    public String getNotEnoughPoints() {
        return getConfiguration().getConfigurationSection("error-messages")
                .getString("not-enough-points");
    }

}
