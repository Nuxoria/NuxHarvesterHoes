package me.dev.nux.nuxharvesterhoes.customfiles;

import me.dev.nux.nuxharvesterhoes.NuxHarvesterHoes;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class CustomFile {

    private File file;
    private YamlConfiguration configuration;

    private String name;

    private NuxHarvesterHoes plugin = NuxHarvesterHoes.getInstance();

    public static ArrayList<CustomFile> allFiles;

    public CustomFile(String name) {

        this.name = name;

        loadConfig();

        if (allFiles == null) {
            allFiles = new ArrayList<>();
            allFiles.add(this);
        } else {
            allFiles.add(this);
        }

    }

    public void loadConfig() {

        file = new File(plugin.getDataFolder(), name+".yml");

        if (!file.exists())
            plugin.saveResource(name+".yml", false);

        configuration = new YamlConfiguration();

        try {
            configuration.load(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

    }

    public void save() {

        try {
            configuration.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public YamlConfiguration getConfiguration() {
        return configuration;
    }

    public List<String> getSectionValues(ConfigurationSection section) {

        List<String> sectionValues = new ArrayList<>();

        for (String str : section.getKeys(false)) {
            sectionValues.add(str);
        }

        return sectionValues;

    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }
}

