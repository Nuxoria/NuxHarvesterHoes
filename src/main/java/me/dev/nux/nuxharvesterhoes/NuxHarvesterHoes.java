package me.dev.nux.nuxharvesterhoes;

import me.dev.nux.nuxharvesterhoes.commands.NuxHarvesterHoesCommand;
import me.dev.nux.nuxharvesterhoes.console.ConsoleManager;
import me.dev.nux.nuxharvesterhoes.customfiles.gui.GuiFile;
import me.dev.nux.nuxharvesterhoes.customfiles.harvesterhoes.HarvesterHoesFile;
import me.dev.nux.nuxharvesterhoes.customfiles.messages.MessagesFile;
import me.dev.nux.nuxharvesterhoes.customfiles.playerexperience.PlayerExperienceFile;
import me.dev.nux.nuxharvesterhoes.listeners.*;
import me.dev.nux.nuxharvesterhoes.listeners.customevents.HarvesterHoeBreakListener;
import me.dev.nux.nuxharvesterhoes.player.PlayerDataManager;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class NuxHarvesterHoes extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;

    private static NuxHarvesterHoes instance;

    private MessagesFile messagesFile;
    private HarvesterHoesFile harvesterHoesFile;

    private GuiFile guiFile;
    private static PlayerDataManager playerDataManager;

    private PlayerExperienceFile playerExperienceFile;

    @Override
    public void onEnable() {

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        //setupChat();

        instance = this;
        messagesFile = new MessagesFile("messages");
        harvesterHoesFile = new HarvesterHoesFile("harvester-hoes");
        guiFile = new GuiFile("gui");
        playerDataManager = new PlayerDataManager();
        playerExperienceFile = new PlayerExperienceFile("player-experience");

        ConsoleManager.sendMessage(ChatColor.GREEN + "[NuxHarvesterHoes Successfully Enabled!");

        getCommand("nuxharvesterhoes").setExecutor(new NuxHarvesterHoesCommand());
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryCloseListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new HarvesterHoeBreakListener(), this);

    }

    @Override
    public void onDisable() {
        log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public static NuxHarvesterHoes getInstance() {
        return instance;
    }

    public MessagesFile getMessagesFile() {
        return messagesFile;
    }

    public HarvesterHoesFile getHarvesterHoesFile() {
        return harvesterHoesFile;
    }

    public GuiFile getGuiFile() {
        return guiFile;
    }

    public static PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public PlayerExperienceFile getPlayerExperienceFile() {
        return playerExperienceFile;
    }

    public static Economy getEcon() {
        return econ;
    }

    public static Permission getPerms() {
        return perms;
    }

    public static Chat getChat() {
        return chat;
    }
}
