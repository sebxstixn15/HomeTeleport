package at.sebdev.homeTeleporter.playerManager;

import at.sebdev.homeTeleporter.model.NameCoordinate;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class PlayerManager {
    private static PlayerManager instance;

    public static synchronized PlayerManager getInstance() {
        if (instance == null) {
            instance = new PlayerManager();
        }
        return instance;
    }


    public boolean save(Player player, Location location, String name) throws IOException {
        File file = new File("plugins/HomeTeleporter/players/" + player.getUniqueId() + ".yml");
        boolean created = file.createNewFile();

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (created) {
            List<NameCoordinate> list = new ArrayList<>();
            config.set("Player-name", player.getName());
            saveHomes(player.getUniqueId(), list);
            config.save(file);
        }

        List<NameCoordinate> nameCoordinateList = loadHomes(player.getUniqueId());
        System.out.println(nameCoordinateList);
        if (!nameCoordinateList.isEmpty()) {
            if (getAllHomeNames(player.getUniqueId()).contains(name)) {
                player.sendMessage(ChatColor.RED + "cannot insert duplicate names please choose another name");
                return false;
            }
        }

        nameCoordinateList.add(new NameCoordinate(name, location.getBlockX(), location.getBlockY(), location.getBlockZ()));

        saveHomes(player.getUniqueId(), nameCoordinateList);

        player.sendMessage(ChatColor.GREEN + "successfully inserted Location with name: " + name);
        return true;
    }

    public boolean teleport(Player player, String name) throws IOException {
        File file = new File("plugins/HomeTeleporter/players/" + player.getUniqueId() + ".yml");
        boolean created = file.createNewFile();

        if (created) {
            player.sendMessage(ChatColor.RED + "no homes are set yet");
            file.delete();
            return false;
        }

        List<NameCoordinate> nameCoordinateList = loadHomes(player.getUniqueId());
        System.out.println(nameCoordinateList);
        for (NameCoordinate nameCoordinate : nameCoordinateList) {
            if (nameCoordinate.getName().equals(name)) {
                player.teleport(new Location(player.getWorld(), nameCoordinate.getX(), nameCoordinate.getY(), nameCoordinate.getZ()));
                player.sendMessage(ChatColor.GREEN + "successfully teleported to: " + name);
                return true;
            }
        }

        player.sendMessage(ChatColor.RED + "Could not found any homes with name: " + name);
        return true;
    }

    public boolean remove(Player player, String name) throws IOException {
        File file = new File("plugins/HomeTeleporter/players/" + player.getUniqueId() + ".yml");
        boolean created = file.createNewFile();

        if (created) {
            player.sendMessage(ChatColor.RED + "no homes are available to remove");
            file.delete();
            return false;
        }

        List<NameCoordinate> nameCoordinateList = loadHomes(player.getUniqueId());
        System.out.println(nameCoordinateList);
        for (int i = 0; nameCoordinateList.size() > i; i++) {
            NameCoordinate nameCoordinate = nameCoordinateList.get(i);
            if (nameCoordinate.getName().equals(name)) {
                nameCoordinateList.remove(i);
                saveHomes(player.getUniqueId(), nameCoordinateList);
                player.sendMessage(ChatColor.GREEN + "successfully removed: " + name);
                return true;
            }
        }

        player.sendMessage(ChatColor.RED + "Could not found any homes with name: " + name);
        return true;
    }

    public boolean rename(Player player, String old_name, String new_name) throws IOException {
        File file = new File("plugins/HomeTeleporter/players/" + player.getUniqueId() + ".yml");
        boolean created = file.createNewFile();

        if (created) {
            player.sendMessage(ChatColor.RED + "no homes are set yet");
            file.delete();
            return false;
        }

        List<NameCoordinate> nameCoordinateList = loadHomes(player.getUniqueId());
        System.out.println(nameCoordinateList);

        if (getAllHomeNames(player.getUniqueId()).contains(new_name)) {
            player.sendMessage(ChatColor.RED + "cannot rename " + old_name + " to " + new_name + " because it already exists");
            return true;
        }

        for (int i = 0; nameCoordinateList.size() > i; i++) {
            NameCoordinate nameCoordinate = nameCoordinateList.get(i);
            if (nameCoordinate.getName().equals(old_name)) {
                nameCoordinateList.add(new NameCoordinate(new_name, nameCoordinate.getX(), nameCoordinate.getY(), nameCoordinate.getZ()));
                nameCoordinateList.remove(i);
                player.sendMessage(ChatColor.GREEN + "successfully renamed: " + old_name + " to " + new_name);
                saveHomes(player.getUniqueId(), nameCoordinateList);
                return true;
            }
        }

        player.sendMessage(ChatColor.RED + "Could not found any homes with name: " + old_name);
        return true;
    }


    private void saveHomes(UUID uuid, List<NameCoordinate> homes) {
        File file = new File("plugins/HomeTeleporter/players/" + uuid + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        List<Map<String, Object>> homeList = new ArrayList<>();

        for (NameCoordinate home : homes) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", home.getName());
            map.put("x", home.getX());
            map.put("y", home.getY());
            map.put("z", home.getZ());
            homeList.add(map);
        }

        config.set("homes", homeList);

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<NameCoordinate> loadHomes(UUID uuid) {
        File file = new File("plugins/HomeTeleporter/players/" + uuid + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        List<NameCoordinate> homes = new ArrayList<>();

        List<Map<?, ?>> homeList = config.getMapList("homes");
        for (Map<?, ?> map : homeList) {
            String name = (String) map.get("name");
            int x = ((Number) map.get("x")).intValue();
            int y = ((Number) map.get("y")).intValue();
            int z = ((Number) map.get("z")).intValue();

            homes.add(new NameCoordinate(name, x, y, z));
        }

        return homes;
    }

    public List<String> getAllHomeNames(UUID uuid) {
        File file = new File("plugins/HomeTeleporter/players/" + uuid + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        List<NameCoordinate> homes = new ArrayList<>();

        List<Map<?, ?>> homeList = config.getMapList("homes");
        for (Map<?, ?> map : homeList) {
            String name = (String) map.get("name");
            int x = ((Number) map.get("x")).intValue();
            int y = ((Number) map.get("y")).intValue();
            int z = ((Number) map.get("z")).intValue();

            homes.add(new NameCoordinate(name, x, y, z));
        }
        List<String> names = new ArrayList<>();
        for (NameCoordinate home : homes) {
            names.add(home.getName());
        }

        return names;
    }
}
