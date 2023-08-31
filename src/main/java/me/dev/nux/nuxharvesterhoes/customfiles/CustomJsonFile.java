package me.dev.nux.nuxharvesterhoes.customfiles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.dev.nux.nuxharvesterhoes.NuxHarvesterHoes;
import me.dev.nux.nuxharvesterhoes.player.PlayerExperience;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CustomJsonFile {

    private NuxHarvesterHoes plugin = NuxHarvesterHoes.getInstance();

    private String fileName;

    private File file;

    private JSONObject json;
    private JSONParser jsonParser = new JSONParser();

    public CustomJsonFile(String fileName) {

        this.fileName = fileName;

        loadJson();

    }

    public void loadJson() {

        File file = new File(plugin.getDataFolder(), fileName + ".json");

        plugin.getDataFolder().mkdir();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Failed to load " + fileName + ".json");
            }

            this.file = file;
        }

        try {
            Writer fileWriter = new FileWriter(file, false);
            Gson gson = new Gson();
            gson.toJson(new ArrayList<PlayerExperience>(), fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void writeJson(PlayerExperience dataToWrite) {

        try {

            List<PlayerExperience> playerExperiences = readJson();
            playerExperiences.add(dataToWrite);

            Writer fileWriter = new FileWriter(file, false);
            Gson gson = new Gson();
            gson.toJson(playerExperiences, fileWriter);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public List<PlayerExperience> readJson() throws FileNotFoundException {

        Gson gson = new Gson();
        if (file.exists()) {

            Reader reader = new FileReader(file);
            List<PlayerExperience> playerExperiences = Arrays.asList(gson.fromJson(reader, PlayerExperience[].class));
            return playerExperiences;

        }

        return new ArrayList<>();

    }

}
