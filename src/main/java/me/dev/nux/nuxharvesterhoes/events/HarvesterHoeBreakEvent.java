package me.dev.nux.nuxharvesterhoes.events;

import me.dev.nux.nuxharvesterhoes.harvesterhoes.HarvesterHoe;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class HarvesterHoeBreakEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private HarvesterHoe harvesterHoe;
    private Player player;

    private Block blockBroken;

    private boolean cancelled = false;

    public HarvesterHoeBreakEvent(HarvesterHoe harvesterHoe, Player player, Block block) {
        this.harvesterHoe = harvesterHoe;
        this.player = player;
        this.blockBroken = block;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HarvesterHoe getHarvesterHoe() {
        return harvesterHoe;
    }

    public Player getPlayer() {
        return player;
    }

    public Block getSugarCaneBroken() {
        return blockBroken;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
