package me.dev.nux.nuxharvesterhoes.player;

import me.dev.nux.nuxharvesterhoes.gui.HarvesterHoeGui;
import org.bukkit.entity.Player;

public class PlayerData {

    private Player player;
    private HarvesterHoeGui openGui;

    public PlayerData(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public HarvesterHoeGui getOpenGui() {
        return openGui;
    }

    public void setOpenGui(HarvesterHoeGui openGui) {
        this.openGui = openGui;
    }
}
